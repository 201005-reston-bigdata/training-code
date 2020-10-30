package hello

import org.apache.spark.{SparkConf, SparkContext}

// This is our driver program, used to initialize and collect results of spark app
// We shouldn't extend App to write this, instead define a main function
object Runner {
  def main(args: Array[String]) = {
    // typically we would to take command line arguments for configuration
    // we're going to skip that and just hardcode them in here
    val appName = "Hello Spark"

    //master is where your spark job is going to run.
    // we can specify the URL of a YARN ApplicationsManager to send the job
    // to a YARN cluster, or similar for other clusters.
    //local means the job will run locally.  the [2] means it should use 2
    // executors.  You can specify a different number there or [*] to have
    // a number of executors equal to your number of cores.
    val master = "local[2]"

    //set up a configuration
    val conf = new SparkConf().setAppName(appName).setMaster(master)
    //build a SparkContext using that configuration
    val sc = new SparkContext(conf)

    // helloDemo(sc)

    // fileDemo(sc)

  }

  def fileDemo(sc: SparkContext) = {
    //yet another way to create an RDD: from file
    val distributedFile = sc.textFile("somelines.txt")

    //this will get us an RDD containing lines in the file, Rdd[String]
    // map is a transformation (lazy, produces RDD)
    val lineLengths = distributedFile.map(_.length)

    //reduce is an action, actually causes evaluation
    println(s"Total line lengths: ${lineLengths.reduce(_ + _)}")

  }

  def helloDemo(sc: SparkContext) = {
    val data = List(1,2,3,4,5)

    //1 way of creating an RDD: parallelize existing collection
    val distributedData = sc.parallelize(data)

    //another way to create an RDD: transform an existing RDD
    val dataPlusTwo = distributedData.map(_+2)

    // transformation, creates a new RDD
    val sampledData = dataPlusTwo.sample(false, 0.3, 34L)

    //all we've done up to this point is specify RDDs.  They're lazily executed,
    // so they won't actually run until we perform an *action*
    // let's use the collect() action, which brings the complete RDD
    // back to the driver program, and print the RDD
    println(sampledData.collect().mkString(" "))
  }


}
