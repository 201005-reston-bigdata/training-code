## Hive

Hive is a popular tool that provides a SQL-like way of interacting with files stored in HDFS.  It allows us to query data that we have stored on HDFS, outside of Hive. It also lets us centralize our data inside a data warehouse inside Hive.  Both of these options are handy, and we can mix them.  If we produces TBs of output from a MapReduce and want to query it, we can create an *external* table in Hive on that data and query the output on HDFS.  If we have big data that we're going to be frequently querying, we can load it into Hive and get optimized queries.

### Hive Partitions

Hive partitions separate and organize the data in a table.  We organize by a column or columns in the table, and we should pick columns that we frequently query on (similar to our choice of index).  We should also pick column(s) that are relatively low cardinality -- we want to have a reasonable amount of data in each partition (at least 1GB is some guidance).  Each partition will get its own directory inside the hive table's files in /user/hive/warehouse.

Partitioning is very important to efficient big data processing because it lets us skip analyzing all of the data in our table.  Instead, we can analyze only the relevant partitions.  We need to pick good partitions so we can achieve these efficiency gains on most of our queries.

A common way to partition your data is first by year, then month, then day, then hour, stopping once your partitions are of reasonable size.  For example, if you have 1GB per day of data, partition year + month + day.  Then it becomes efficient to query based on any subset of time.
Partitioning by time is also nice because you can add new partitions to your table without worrying about editing existing partitions.

### Hive Buckets

Bucketing also creates subsets of your data, but it has a different motivation.  When we bucket, we choose column(s) that are high cardinality and not particularly important to our analysis, and divide each partition based on those columns.  This lets us split each partition into a number of smaller, ideally representative datasets.

If we choose low cardinality column(s) for our buckets, we run the risk of having *skewed* buckets, where some buckets are very large and others are very small.

### Hive Metastore

Hive stores all its data, both managed and external, in HDFS.  All of the information Hive has about its storage of tables (columns, tablesnames, all metadata) is contained in a regular RDBMS called the metastore.  We're using a Derby metastore, but in a real deployment your metastore would be a database running on another machine.

## Spark

Framework for big/fast data processing, making extensive use of in-memory processing.  Largely the successor to Hadoop and the Hadoop Ecosystem.  It's not as if there's no reason to use other tools, but Spark is popular and useful.  One of the main reasons Spark is the successor to Hadoop is that the framework is general enough to reproduce analyses that required specialized tools in Hadoop.

Running Spark:
- local mode : this runs spark as a standalone process, v useful for testing/learning.  This is similar to running MapReduce as a standalone process.
- cluster mode : this runs spark on a cluster, always done in production.  We have a few options:
  - Mesos was the original cluster manager for Spark and its still available.  We won't use this
  - Kubernetes (K8s) can be used to run spark on a cluster.  This is new and fancy, we won't use it either
  - YARN, Spark can run on a YARN cluster.  We will use this, our pseudodistributed YARN cluster can run spark applications.  In Spark we have this driver program that communicates with executors across the cluster.  We can run this on Yarn by having our driver communicate with an ApplicationMaster running our Job on the cluster. Or we can have the driver program run inside the ApplicationMaster, when we submit our Job to the YARN cluster.  Executors will run inside containers managed by YARN.

In memory processing : hold the data in memory instead of writing it/reading it between steps in your process.

Cluster Computing: Running a computation across a cluster -- what YARN does.

Working Set: a dataset stored in memory so that a series of analyses/processes can efficiently run on it.

Central abstraction of Spark is the RDD: Resilient Distributed Dataset.  An RDD is a read-only collection of objects partitioned across a set of machines, that can be rebuilt if a partition is lost.
Partition here is something like an inputsplit in Hadoop.  In fact, sometimes partitions in Spark are referred to as "splits".

"acyclic" just means without cycles.  MapReduce specifies a series of steps on input data to produce output data.  Spark *can* just do MapReduce, but it also lets us run iterative processes, where the data flow has a repeated cycle.  Useful in ML algorithms and for interactive data processing on the REPL.

"lineage" just refers to the history of transformations on an RDD, this lets us easily reproduce lost partitions.

RDDs are lazy + ephemeral, what does this mean :
ephemeral just means that our RDDs will, by default, be removed from memory once we're done using them.  This is just normal behaviour for objects in memory, we typically get rid of objects in memory once we're done with them to free up space.  In Spark, we mention this explicitly because we can .cache (or .persist) our RDDs to make them *not* ephemeral.
lazy is like lazy loading or lazy evaluation that we've mentioned previously.  Just creating an RDD, from for example a file, doesn't actually cause the data in the file to be read.  Calling .map on an RDD doesn't actually cause the map (and function you pass it) to occur.
After creating an RDD we can do two broad categories of operations on it:
- Transformations.  These produce another RDD, normally by using a function we provide.  Since these produce another RDD, they are lazy as well.
- Actions.  These produce some result from the prior processing on the RDD, returning it to the driver program or causing some side effects (forEach).  RDDs are only actually run once an action occurs on them.
So we create an RDD, specify transformations on it, and eventually call an action on it.  Once the action is called, the RDD is actually produced in order to retrieve the results of the action.

Ways to construct an RDD:
- from file : we get a dataset with the content of the file (uses Hadoop InputFormats + other options)
- parallelize scala collection : we get a dataset with the content of the scala collection.
- transform another RDD : we get a dataset produced by our transformation called on the prior RDD
- change persistence of another RDD with .cache, .save, .persist. : we get the cached version of the prior dataset, which enables efficient processing.

The paper uses the term "parallel operation" -- this is an *action*, something that causes your RDD to actually be evaluated to produce some result or have some effect.  Examples:
- reduce : aggregate dataset to value, return to driver
- collect : return entire dataset to driver
- foreach : causes a side effect for each element in the collection

Shared variables:
- Broadcast variables are useful to pass global read-only values to each worker/task efficiently.
- Accumulators exist to deal with each worker having its own scope, its own local runtime.  If we were to declare var counter=0, then each worker would have their own counter and we'd have trouble getting an application-wide counter.  Instead we specify an accumulator that each worker/task can "add to", that exists application-wide.

caching/persisting and running out of memory:
- caching or persisting an RDD can dramatically speed up processing.
- caching or persisting an RDD that isn't reused is just going to make your application run slower.
- cache is a suggestion -- if there isn't enough memory then the processing will continue by recomputing the RDD as necessary.
- We can specify how / what level to cache/persist our RDDs:
  - MEMORY_ONLY : default for cache.  attempt to store RDD in memory.  If there isn't enoughy memory, recompute as necessary.
  - MEMORY_ONLY_SER : attempt to store RDD in memory *serialized*.  This will make your RDD use less memory but require more compute power for the serialization/deserialization process.
  - MEMORY_AND_DISK : attempt to store RDD in memory, but if there isn't enough memory then store to disk.
  - MEMORY_AND_DISK_SER : the same as MEMORY_AND_DISK, except it stores the RDD serialized.















