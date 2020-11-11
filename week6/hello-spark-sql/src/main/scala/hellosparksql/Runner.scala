package hellosparksql

import org.apache.spark.sql.SparkSession

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
  }
}
