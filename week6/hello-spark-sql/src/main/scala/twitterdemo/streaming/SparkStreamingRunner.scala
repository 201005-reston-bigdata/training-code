package twitterdemo.streaming

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Duration, StreamingContext}

object SparkStreamingRunner {
  def main(args: Array[String]): Unit = {
    //Structured Streaming used DataFrames and DataSets
    // Spark Streaming uses DStreams, which are similar to RDDs

    // TO interact with Spark Streaming, we create a StreamingContext that
    // produces DStreams in the same way SparkContext produces RDDs

    // It's worth explicitly mentioning that these APIs aren't unified --
    // DStreams and Structured Streaming both use RDDs under the hood
    // but Structured Streaming doesn't make use of DStreams.

    // We're going to use TwitterUtils from apache.bahir
    // These are built using Twitter4j, which is a library for interacting with
    // the Twitter 1.1 API

    // We will need to do some configuration that Twitter4j needs for auth
    val eMap = System.getenv()
    System.setProperty("twitter4j.oauth.consumerKey", eMap.get("CONSUMER_KEY"))
    System.setProperty("twitter4j.oauth.consumerSecret", eMap.get("CONSUMER_SECRET"))
    System.setProperty("twitter4j.oauth.accessToken", eMap.get("ACCESS_TOKEN"))
    System.setProperty("twitter4j.oauth.accessTokenSecret", eMap.get("ACCESS_TOKEN_SECRET"))

    val sparkConf = new SparkConf().setAppName("Twitter Streaming DStream").setMaster("local[4]")
    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")
    // StreamingContext in lieu of SparkContext
    // We're specifying 10 second duration batches, meaning each second we'll produce an RDD and run our analysis
    val ssc = new StreamingContext(sc, Duration(10000))
    // This is a DStream, its a stream of RDDs
    val stream = TwitterUtils.createStream(ssc, None)

    val hashTags = stream.flatMap(status => {status.getText.split("\\s").filter(_.startsWith("#"))})

    //get the top hashTag count per 60 seconds:
    val topCounts60 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Duration(60000))
      .map( {case (topic, count) => (count, topic)} ) // swap order so we can sort by key
      .transform(_.sortByKey(false)) //sort by key

    //On our stream we can take actions, like forEachRDD:
    topCounts60.foreachRDD(rdd => {
      rdd.take(10).foreach(println)
      println("-----------------------")
    })


    //To actually run the above, we call .start() on our StreamingContext, then .awaitTermination
    // to keep the main thread alive

    ssc.start()
    //waits for termination -- won't actually end so we'll have to kill it.
    ssc.awaitTermination()


  }
}
