package kafka

import org.apache.spark.sql.SparkSession

object KafkaRunner {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Kafka Consumer")
      .master("local[4]")
      .getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    import spark.implicits._

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "important-events")
      .load()

    df
      .select("value")
      .writeStream
      .outputMode("append")
      .format("console")
      .start()
      .awaitTermination()

  }
}
