### Hive
- What is Hive?
- Where is the default location of Hive's data in HDFS?
- What is an External table?
- What is a managed table?
- What is a Hive partition?
- Provide an example of a good column or set of columns to partition on.
- What's the benefit of partitioning?
- What does a partitioned table look like in HDFS?
- What is a Hive bucket?
- What does it mean to have data skew and why does this matter when bucketing?
- What does a bucketed table look like in HDFS?
- What is the Hive metastore?
- What is beeline?

### Hive Syntax questions: How do we....
- create a table?
- load data into a table?
- query data in a table?
- filter the records from a query?
- group records and find the count in each group?
- write the output of a query to HDFS?
- specify we're reading from a csv file?

### Spark : Cluster Computing with Working Sets
- What does Cluster Computing refer to?
- What is a Working Set?
- What does RDD stand for?
- What does it mean when we say an RDD is a collection of objects partitioned across a set of machines?
- Why do we say that MapReduce has an acyclic data flow?
- Explain the deficiency in using Hive for interactive analysis on datasets.  How does Spark alleviate this problem?
- What is the *lineage* of an RDD?
- RDDs are lazy and ephemeral.  What does this mean?
- What are the 4 ways provided to construct an RDD?
- What does it mean to transform an RDD?
- What does it mean to cache an RDD?
- What does it mean to perform a parallel operation on an RDD?
- Why does Spark need special tools for shared variables, instead of just declaring, for instance, var counter=0?
- What is a broadcast variable?
- What is an accumulator?
- How would the Logistic Regession example function differently if we left off the call to .cache() on the points parsed from file?