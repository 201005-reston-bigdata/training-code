package hellosparksql

import org.apache.spark.sql.{SparkSession, functions}

object Runner {
  def main(args: Array[String]) = {
    //SparkSession is the entry point for Spark applications using Spark SQL
    //  It's new in Spark 2.0 and it unifies earlier disparate entrypoints:
    //  SparkContext, SqlContext and HiveContext
    // Prior to Spark 2 you used a SparkContext for your RDDs, a SqlContext for your
    // Spark SQL, and a HiveContext for your HiveQL and interactions with Hive tables

    //Another difference is that SparkSession provides support for multiple sessions
    // in the same runtime, whereas we only ever wanted one SparkContext per JVM

    val spark = SparkSession.builder()
      .appName("Hello Spark SQL")
      .master("local[4]")
      .getOrCreate()
    //We always want to add this import, it enables some syntax and some
    // automatic code generation.  If you have mysterious errors in what was
    // working code, double check to make sure we have this.
    //Notably the $ syntax to select columns in DataFrames requires this
    import spark.implicits._

    //We can access our familar SparkContext if we like
    spark.sparkContext.setLogLevel("WARN")

    //From the Spark Session we can create a DataFrame.  We're going to read from
    // a Json file here.  We can also produce DataFrames from RDDs, parquet files,
    // Hive tables, SQL tables, ... Spark input formats generally.
    // We're having Spark infer the schema for our JSON file here, and enabling reading multiline JSON
    val df = spark.read.option("multiline", "true").json("people.json")

    df.show()

    df.printSchema()

    //select columns by string name
    df.select("name").show()
    df.select("name", "age").show()

    //use $ syntax to select columns
    df.select($"name", $"age").show()

    //The $ lets us use columns in expressions:
    df.select($"name", $"age" + 10).show()

    //The other way, without the $ to select columns is to use df("columnname")
    df.select(df("name"), df("age") + 20).show()
    // the $ and df("colname") produce an object of type Column which has functionality and can
    // be used in expressions.  Just passing a string to select retrieve a column by name with
    // no additional functionality.

    //access nested fields with .
    df.select("name.first", "name.last").show()

    // run some demo functionality
    df.groupBy("eyeColor").count().show()

    df.filter($"age" > 30).show()

    //Many useful built in functions
    // some are "scalar" functions that operate on one value
    // others are "aggregate" functions that operate on groups of values
    df.select(functions.exp($"age")).show() //exponentiation
    df.select(functions.round($"age", -1)).show() //round to nearest 10

    //Average age by eye color for people with first name of length < 6:
    val demoQuery = df.filter(functions.length($"name.first") < 6)
      .groupBy("eyeColor")
      .agg(functions.avg("age"))

    functions

    //This will make it run and print a result
    demoQuery.show()
    //This won't make it run, it will explain catalyst's plan.  Somewhat similar
    // to toDebugString for RDDs
    demoQuery.explain(true)

    //On that rdd note: access underlying rdds easily
    println(demoQuery.rdd.toDebugString)

    //To note about this output:
    // The catalyst optimizer adds a Project into our optimized logical plan, taking
    // advantage of that fact that our demoQuery doesn't actually use all the columns
    // Also, stages are represented by *(stageno) in the physical plan, as opposed
    // to stages represented by indentation in the debugString for an RDD.

    //let use some DataSets, which are strongly typed.  We'll create case classes
    // for the data we want to use
    // We can easily create DataSets from DataFrames using "as"
    val ds = df.as[Person]

    //Instead of having Rows inside our DataSet, which would make it a DataFrame, we have
    // Person objects instead that we've defined
    // Strong typing saves us from runtime errors by checking type at compile time
    ds.filter(person => person.name.first.length < 6).show()

    val demoQuery2 = ds.filter(_.name.first.length < 6)
      .map(person => s"${person.name.first} ${person.name.last}")

    demoQuery2.select(demoQuery2("value").alias("Full name")).show()

    demoQuery2.explain()

    //Things to note: the optimizer cant add a Projection here because it's constructing Person objects
    // rather than generic rows.  On the other hand, Spark can efficiently serialize and deserialize
    // case classes, since it has more information about their structure.
    //Adam's suspicion is that DataFrame would be faster here, because of the Projection savings

    //We can also write SQL queries, to be run on RDDs, files, hive tables, generally Spark input formats
    //To use SQL functionality in Spark SQL we create temporary views from the DataSet we want to query
    // This creates a dataset of Names and creates a temporary view called "names"
    spark.createDataset(List(Name("adam", "king"), Name("jeff", "goldblum")))
      .createOrReplaceTempView("names")

    //Now that we have the view, we can refer to it in a SQL query:
    println(spark.sql("SELECT * FROM names").rdd.toDebugString)

    //Notably, the result of spark.sql here is a DataFrame.  Each line in the resultset is a Row
    // in the output dataframe.
    // SQL, DataFrames, DataSets can be used interchangeably


  }

  case class Person(_id: String, address: String, age: Long, eyeColor: String, index: Long, name: Name, phone: String ) {}

  case class Name(first: String, last:String) {}


}
