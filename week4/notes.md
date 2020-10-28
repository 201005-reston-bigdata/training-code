## Hive

Hive is a popular tool that provides a SQL-like way of interacting with files stored in HDFS.  It allows us to query data that we have stored on HDFS, outside of Hive. It also lets us centralize our data inside a data warehouse inside Hive.  Both of these options are handy, and we can mix them.  If we produces TBs of output from a MapReduce and want to query it, we can create an *external* table in Hive on that data and query the output on HDFS.  If we have big data that we're going to be frequently querying, we can load it into Hive and get optimized queries.

## Hive Partitions

Hive partitions separate and organize the data in a table.  We organize by a column or columns in the table, and we should pick columns that we frequently query on (similar to our choice of index).  We should also pick column(s) that are relatively low cardinality -- we want to have a reasonable amount of data in each partition (at least 1GB is some guidance).  Each partition will get its own directory inside the hive table's files in /user/hive/warehouse.

Partitioning is very important to efficient big data processing because it lets us skip analyzing all of the data in our table.  Instead, we can analyze only the relevant partitions.  We need to pick good partitions so we can achieve these efficiency gains on most of our queries.

A common way to partition your data is first by year, then month, then day, then hour, stopping once your partitions are of reasonable size.  For example, if you have 1GB per day of data, partition year + month + day.  Then it becomes efficient to query based on any subset of time.
Partitioning by time is also nice because you can add new partitions to your table without worrying about editing existing partitions.

## Hive Buckets

Bucketing also creates subsets of your data, but it has a different motivation.  When we bucket, we choose column(s) that are high cardinality and not particularly important to our analysis, and divide each partition based on those columns.  This lets us split each partition into a number of smaller, ideally representative datasets.

If we choose low cardinality column(s) for our buckets, we run the risk of having *skewed* buckets, where some buckets are very large and others are very small.

## Hive Metastore

Hive stores all its data, both managed and external, in HDFS.  All of the information Hive has about its storage of tables (columns, tablesnames, all metadata) is contained in a regular RDBMS called the metastore.  We're using a Derby metastore, but in a real deployment your metastore would be a database running on another machine.

