package emr

import org.apache.spark.{SparkConf, SparkContext}

object Runner {
  def main(args: Array[String]) = {
    //1. change this string to be your app name
    //  AND change the name of the project in build.sbt from emrtemplate
    //  (sbt reload afterwards)
    val appName = "Your App Name"

    //2. use one of these configurations.  The uncommented one is for the final
    //  version to be packaged and sent to Adam
    //  The commented version is for testing locally

    // For FINAL S3 version:
    val conf = new SparkConf().setAppName(appName)
    val sc = new SparkContext(conf)
    val somelinesLocation = "s3://rev-big-data/somelines.txt"

    // For LOCAL testing:
//    val conf = new SparkConf().setAppName(appName).setMaster("local[2]")
//    val sc = new SparkContext(conf)
//    val somelinesLocation = "somelines.txt"


    //3. Here write your spark application.  The template here sums
    // the total line lengths.  Feel free to use somelines.txt or do something
    // else entirely.  Experiment a bit!
    // once you're done, make sure you're using the final s3 conf and sbt package to produce the jar
    val summedLineLengths = sc.textFile(somelinesLocation)
        .map(_.length)
        .reduce(_ + _)

    println(s"Summed line lengths: $summedLineLengths")


  }

}
