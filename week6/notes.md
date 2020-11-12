### Joins

A join combines two different datasets by combining their records.  Whenever you join two datasets together, a common occurence across data processing and storage, you are take the records from one dataset and combine each record with 0 or more records from the other dataset to produce an output dataset containing these larger records.  Joins make your dataset "wider", each output record will contain more information.

Calling join on an RDD will combine the records in two RDDs.  .join expects that each RDD contains k,v pairs in a tuple.  The output will be an RDD containing a k,v pair with the same key and the values of both RDDs combined for the value.

If we join an RDD containing (String, Double) and an RDD containing (String, String) the output will be an RDD
 containing (String, (Double, String))
 
.join on our RDDs will join two records if their keys are equal, joins generally allow us to specify a join condition.

### Joining to create paths

our RDD contains:
(List_of_women_CEOs_of_Fortune_500_companies,(Veritiv,0.00239741816505302))
(Veritiv, (example_page, 0.001))

we want:
(List_of_..., (List(Veritiv, example_page), 0.00000239...)

Let's produce:
(last_page_in_series, (series, fraction))

TODO: remove -, main page?

Note the large amount of data produces per partition at the final stage -- repartitioning to a larger number of partiiton would be nice, as would filtering the dataset at each loop to reduce its size.

### Spark SQL

Spark SQL is built on top of Spark, but is still part of the apache spark project.  It started as a way to interact with big data using Spark by writing SQL instead of directly manipulating RDDs.  The motivation is similar to the motivation for Hive, and Spark SQL works well with Hive, in addition to working with other Spark datasources + existing RDDs.  This functionality where we write SQL and we have Spark SQL generate a logical plan, physical plan, and ultimatly execution using RDDs still exists and is still useful. This SQL functionality existed in Spark 1.0

The way Spark SQL works is by using an internal tool called the *catalyst optimizer*.  We write our SQL query, the catalyst optimizer turns it into an appropriate logical -> physical plan -> RDDs.  Catalyst will analyze our queries, produce a logical plan using the information it has about tables + views (Catalog), produce multiple physical plans, find the most efficient (by Cost Model) physical plan, and execute that plan using RDDs.  Catalyst has been around since Spark 1.0 and is constantly improving.  Having catalyst make your plans can avoid some pitfalls with RDDs.

Spark SQL is more commonly used than directly using RDDs, but we never have to trade one for the other, we can always access underlying RDDs if need be.  The way Spark SQL is used, though, is not most commonly by writing SQL queries.  Instead, we use Spark SQL by making use of two newer APIs: DataFrames and DataSets.  DataFrames and DataSets are both built using Spark SQL, so they both use the catalyst optimizer under the hood.  DataFrames are like tables in SQL or collections in mongo, they hold some data as records and they have named columns with datatypes.  DataSets are between DataFrames and RDDs, they contain a distributed collection of data that is strongly typed, so the content of a DataSet is typically a Scala case class.  These two things are similar, they're both objects that exist in Scala and represent distributed collections of data that are processed using the catalyst optimizer to convert down to RDDs.  In Spark 2.0+ they are in fact unified and a DataFrame is just a DataSet that contains generic Row objects instead of specific Scala case classes.

ex: DataSet\[Comic\] means a DataSet containing Comics, DataSet\[Person\] means a DataSet containing Persons, DataSet\[Row\] means a DataSet containing generic Rows, this is just a DataFrame.  We can also call DataFrames "untyped DataSets".

Why the difference?  First, DataFrames were released with Spark 1.3 as another way to make use of Spark SQL, without writing actual SQL/HiveQL.  DataSets were released with Spark 1.6 as an implementation of strongly typed DataFrames.  In Spark 2.0 they were brought together, before 2.0 they were separate parts of the API.  In addition to this historical difference, some optimizations are easier for Catalyst/spark to make happen automatically on one vs the other.  Also, DataFrames are similar to existing tools for data analysis called dataframes in R and Python's pandas, so data scientists comfortable with those tools/languages will find Spark's DataFrames more familiar.

We have 3 APIs for Spark SQL: SQL/HiveQL string queries, DataFrames, and DataSets.  All of them run on the catalyst optimizer under the hood which creates RDDs.  It's fine and even encouraged to use different APIs based on context and your familiarity.

### SparkContext, SqlContext, HiveContext, SparkSession

For our code, we're going to use SparkSession.  SparkSession is new in Spark 2.0 and supersedes all of SparkContext, SqlContext, and HiveContext.
In Spark 1.0, we had SparkContext that handled RDDs, SqlContext that handled Spark SQL (SQL statementes, DataFrames, DataSets), and HiveContext that handled reading/writing to Hive Tables.  SqlCOntext is sufficient to work with HDFS, it's just Hive itself we needed a HiveContext for.
When using SparkSession, we get the functionality of SqlContext and SparkCOntext out of the box.  To enable Hive support, we need to call .enableHiveSupport() on our SparkSession when building it, which will read hive configuration from the conf directory.

### Joins (continued)

If we join an RDD containing (String, Double) and an RDD containing (String, String) the output will be an RDD
containing (String, (Double, String)) example 2:
RDD 1: ("a", 1.1), ("b", 2.2), ("c", 3.3)
RDD 2: ("a", "alpha"), ("b", "beta"), ("c", "gamma")
product of join: ("a", (1.1, "alpha")), ("b", (2.2, "beta")), ("c", (3.3, "gamma"))

This is a specific example of a join, joins exist in many contexts.  Most notably in RDBMSs because your RDBMS stored "normalized" data.  One of the goals of normalizing your data is to never represent the same piece of data more than 1 time.  This means our RDBMSs will have many tables the frequently need to be joined together.

#### Types of Joins
What happens if we have:
RDD 1: ("a", 1.1), ("b", 2.2), ("d", 4.4)
RDD 2: ("a", "alpha"), ("b", "beta"), ("c", "gamma") ?

It depends on the type of join we're using.  There are 4 types: Inner, Left Outer, Right Outer, Full Outer.
If we use an inner join, then output:  ("a", (1.1, "alpha")), ("b", (2.2, "beta")))
If we use a left outer join, then output:  ("a", (1.1, "alpha")), ("b", (2.2, "beta")), ("d", (4.4, None)))
If we use a right outer join, then output:  ("a", (1.1, "alpha")), ("b", (2.2, "beta")), ("c", (None, "gamma")))
If we use a full outer join, then output:  ("a", (1.1, "alpha")), ("b", (2.2, "beta")), ("c", (None, "gamma")), ("d", (4.4, None))

When you don't the type of join specified, it's most often an inner join.

#### Join Conditions

Using RDDs .join method, the join condition is the keys in each RDD being equal.  Generally your join condition tells us when two records out of the pair of datasets should be joined together in the output.  When the join condition is true for a given pair of records, those two records are joined in the output.  When the join condition is false, they are not.  The join condition is evaluated for *every pair* of records.  So if we're joining a dataset with 10 records to a dataset with 20 records, the join condition will be evaluated for 10*20=200 pairs of records.

The typical use case of joining has the join condition evaluate to true for one or zero pairs of records.  That being said, we can have join conditions that evaluate to true based on any condition we prefer.  If your join condition is an inequality like > or <, we call that a *theta join*.  If you join condition returns true in all cases, we call that a *cartesian join*.






