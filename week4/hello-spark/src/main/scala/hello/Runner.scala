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
    val master = "local[4]"

    //set up a configuration
    val conf = new SparkConf().setAppName(appName).setMaster(master)
    //build a SparkContext using that configuration
    val sc = new SparkContext(conf)

    // helloDemo(sc)

    //fileDemo(sc)

    //closureDemo(sc)

    mapReduceWordCountDemo(sc)

  }

  def mapReduceWordCountDemo(sc: SparkContext) = {

    val mrRdd = sc.textFile("somelines.txt")
      .flatMap(_.split("\\s"))
      .filter(_.length > 0)
      .map((_, 1))
      //something important happens at this step (the shuffle!)
      .reduceByKey(_ + _)
      //that important thing happens here too (shuffle again!)
      .sortBy({case (k,v) => v})
      //.repartition(10) just for demo, make more partitions, shuffles as well

    mrRdd
      .take(10) //take is the action
      .foreach(println)

    println(mrRdd.toDebugString)
    // on any RDD we can call toDebugString and see lineage


    // This does the same thing as our wordcount Mapreduce, and in general we
    // can do MapReduce jobs using spark like this.  It's simpler and cleaner
    // in many cases.
    // Since we're doing MapReduce here, we have our shuffle.  managing when
    // shuffles happen is key to writing efficient Spark applications.  The
    // shuffle in spark has the same downsides as it does in MapReduce, it uses
    // Map and Reduce operations under to achieve the shuffle.  It doesn't
    // sort by default.  Refresher on downsides: network traffic, disk I/O
    // we're doing 2 shuffles above, we should look to combine them.

    // when we were sorting by key we probably could combine them sorting by
    // value I'm less sure we can... hmm! maybe if we sort first.

    // Some operations that cause shuffles:
    // repartition and coalesce (these change the number of partitions)
    // groupByKey, reduceByKey, other byKey methods
    // joins will cause shuffles (connecting items in different datasets
    //  based on references, foreign keys)

    // Our RDDs need to be resilient, so we need to be able to recompute
    // them if there is a failure.
    // Every partition past our shuffle may depend on ALL the partitions prior
    // to the shuffle.
    // this causes a problem, because to recreate partitions past the shuffle
    // we would to rerun basically the entire job up to the shuffle.  Obviously,
    // Spark doesn't do this.  Instead, each shuffle writes output to disk
    // so that in the case of failure we can recompute based on the disk output
    // All of the disk output that happens with a shuffle is preserved
    // until the RDD is no longer used.

    // Can we write the shuffle output to RAM instead of disk
    // and preserve it for the entire lifetime of the RDD in memory? not sure

//    sc.parallelize(List("AK", "MS", "RI", "NY"))
//      .map(("region-TODO", _))
//      .filter({case (region,state) => region.equals("Northeast")})
    // we now have an RDD of states in the northeast, partitioned across cluster for processing

    //sort by line length
//    sc.textFile("somelines.txt")
//      .sortBy(_.length)
//      .collect()
//      .foreach(println)

  }

  def closureDemo(sc: SparkContext) = {
    //We define operations to be on an RDD across a cluster, these operations
    // are broken up into tasks.
    // Sometimes these tasks rely on values in memory.
    // the *closure* of a task is all the variables and methods that
    // the executor (the thing that runs the task) needs to complete the task
    // Spark handles this for us and sends along the serialized closure with
    // the task.  This is good, but it can be a pitfall.  What we write as one
    // variable/value inside our driver will become multiple values in memory
    // distributed across the cluster alongside the tasks.

    // 4 examples here, using values and variables with the "naive" approach
    // and also using Spark's tools for val/var across the cluster
    val listRdd = sc.parallelize(List(1,3,5,7,9))

    // compute a sum across cluster, bad version:
    // this mutable sum variable will be passed along with tasks, each task
    // will get its own copy, they'll never be aggregated together/returned to driver
    var sum = 0

    //foreach is an action
    listRdd.foreach(sum += _)

    //behaviour is undefined:
    println(s"bad sum: $sum")

    // compute a sum across cluster, good version:
    // Spark provides us *accumulators* to produce aggregate values
    // across the cluster.  Similar to counters in MapReduce.  We can define
    // our own with custom definitions of "aggregate"
    val sumAccumulator = sc.longAccumulator("good sum")
    listRdd.foreach(sumAccumulator.add(_))

    println(s"good sum: ${sumAccumulator.value}")

    // filter a list based on a fixed cutoff across a cluster, OK version:

    // this fixed value will be passed as part of the closure of your tasks
    // it will work just fine
    // it's inefficient though, because it will be passed with every task
    // mostly matters if the value is large
    val cutoff = 5

    println(s"OK list: ${listRdd.filter(_ > cutoff).collect().mkString(" ")}")

    // we can use a *broadcast variable* to efficiently transfer a fixed value
    // to machines all across the cluster, which can then use it in all their tasks
    val cutoffBroadcast = sc.broadcast(5)

    println(s"good list: ${listRdd.filter(_ > cutoffBroadcast.value).collect().mkString(" ")}")

    // final caveat for accumulators.  Be careful to use them in actions rather
    // than transformations.  If used in a transformation, they might be run
    // multiple times.

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
