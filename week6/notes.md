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


