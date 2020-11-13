package twitterdemo.structured

import java.io.{BufferedReader, InputStreamReader, PrintWriter}
import java.nio.file.{Files, Paths}

import org.apache.http.client.config.{CookieSpecs, RequestConfig}
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.spark.sql.SparkSession

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object StructuredStreamingRunner {
  def main(args: Array[String]): Unit = {
    //We have some API keys, secrets, tokens from the Twitter API
    //We definitely do not want to hardcode these.
    //If you *must* hardcode these, then gitignore the files that contain them
    //One nice way to handle strings we want to keep secret, like API keys
    //is to pass them into your program as environment variables

    //We get access environment variables using System.getenv
    //We can set environment variables in our run config in IntelliJ

    val bearerToken = System.getenv("BEARER_TOKEN")
    println(s"Bearer token is : $bearerToken")
    //Run tweetStreamToDir in the background:
    Future {
      tweetStreamToDir(bearerToken)
    }

    val spark = SparkSession.builder()
      .appName("Hello Spark Structured Streaming")
      .master("local[4]") // when streaming some threads need to be Receivers that listen, so we need more than typical
      .getOrCreate()
    import spark.implicits._
    spark.sparkContext.setLogLevel("WARN")

    //When streaming, we can't infer the schema so let's create a static dataframe
    // and use its inferred schema.  Requires some files in twitterstream
    val staticDf = spark.read.json("twitterstream")

    spark.readStream.schema(staticDf.schema).json("twitterstream")
      .select("data.text")
      
      .writeStream
      .outputMode("append")
      .format("console")
      .start()
      .awaitTermination(60000)

  }

  def tweetStreamToDir(bearerToken: String, dirname:String="twitterstream", linesPerFile:Int=1000) = {
    val httpClient = HttpClients.custom.setDefaultRequestConfig(RequestConfig.custom.setCookieSpec(CookieSpecs.STANDARD).build).build
    val uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/sample/stream")
    val httpGet = new HttpGet(uriBuilder.build)
    httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken))
    val response = httpClient.execute(httpGet)
    val entity = response.getEntity
    if (null != entity) {
      val reader = new BufferedReader(new InputStreamReader(entity.getContent))
      var line = reader.readLine
      //initial filewriter, will be replaced with new filewriter every linesperfile
      var fileWriter = new PrintWriter(Paths.get("tweetstream.tmp").toFile)
      var lineNumber = 1 //track line number to know when to move to new file
      val millis = System.currentTimeMillis() //identify this job with millis
      while ( {
        line != null
      }) {
        if(lineNumber % linesPerFile == 0) {
          fileWriter.close()
          Files.move(
            Paths.get("tweetstream.tmp"),
            Paths.get(s"${dirname}/tweetstream-${millis}-${lineNumber/linesPerFile}"))
          fileWriter = new PrintWriter(Paths.get("tweetstream.tmp").toFile)
        }
        fileWriter.println(line)
        line = reader.readLine()
        lineNumber += 1
      }
    }
  }

}


