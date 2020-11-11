package wikiseries

import org.apache.spark.{SparkConf, SparkContext}

object Runner {
  def main(args: Array[String]) = {
    val appName = "wikiseries"

    // For FINAL S3 version:
        val conf = new SparkConf().setAppName(appName)
        val sc = new SparkContext(conf)
        val inputClickstream = "s3://rev-big-data/clickstream-enwiki-2020-09.tsv"

    // For LOCAL testing:
//    val conf = new SparkConf().setAppName(appName).setMaster("local[8]")
//    val sc = new SparkContext(conf)
//    val inputClickstream = "clickstream-enwiki-2020-09.tsv"

    //shuffleJoinPageviewFraction(sc, inputClickstream)

    broadcastJoinPageviewFraction(sc, inputClickstream)

  }

  def broadcastJoinPageviewFraction(sc: SparkContext, file: String) = {
    // broadcast joining is significantly faster when it is possible.
    // We're going to broadcast the smaller dataset to be joined across
    // the cluster, which means we won't need to shuffle.  We're going
    // to arrive at the same values, but we're using a different and more efficient
    // process.

    val splitRecords = sc.textFile(file, 1000)
      .map(_.split("\t"))

    // We still need pageviews, but instead of leaving them as an RDD
    // We collect them to the driver and broadcast
    val estimatedPageviewsMap = splitRecords.map(arr => (arr(1), arr(3).toDouble))
      .reduceByKey(_ + _)
      .filter({case (page, estViews) => estViews > 10000})
      .collectAsMap()

    println(estimatedPageviewsMap.keySet.size)

    val epmBroadcast = sc.broadcast(estimatedPageviewsMap)

    val estimatedFraction = splitRecords.filter(_(2) == "link")
      .filter(arr => epmBroadcast.value.contains(arr(0)))
      .map(arr => (arr(0), (arr(1), arr(3).toDouble/epmBroadcast.value(arr(0)))))
      .cache()
//      .take(10)
//      .foreach(println)

    println(estimatedFraction.toDebugString)

    var seriesFractionByLastPage = estimatedFraction.map(
      {case (fromPage, (toPage, frac)) => (toPage, (List(fromPage, toPage), frac))}
    )

    val ITERATIONS = 3

    for(i <- 1 to ITERATIONS) {
      seriesFractionByLastPage = seriesFractionByLastPage
        .join(estimatedFraction) //definitely want to get this small enough we can broadcast it
        .map(
          { case (fromPage, ((series, seriesFrac), (addedPage, addedPageFrac))) =>
            (addedPage, (series :+ addedPage, seriesFrac * addedPageFrac))
          }
        )
    }

    println("50 series of pages with associated estimated fraction of users:")
    seriesFractionByLastPage.take(50).foreach(println)


  }

  def shuffleJoinPageviewFraction(sc: SparkContext, file: String) = {

    val splitRecords = sc.textFile(file)
      .map(_.split("\t"))

    //generate some estimated pageviews using clickstream data
    // this will just involve summing up all the ways that users arrived
    // at a given page.
    //all we need is target page and number
    val estimatedPageviews = splitRecords.map(arr => (arr(1), arr(3).toDouble))
      .reduceByKey(_ + _)

    //next lets generate fractions for each page.  This will be the internal
    // links coming out of each page
    val estimatedFraction = splitRecords.filter(_(2) == "link")
      .map(arr => (arr(0), (arr(1), arr(3).toDouble)))
      //This is going to shuffle our RDDs so matching records from across each RDD can be joined
      .join(estimatedPageviews)
      .map({case (key, combinedValues) => (key, (combinedValues._1._1, combinedValues._1._2 / combinedValues._2 ))})
//      .take(10)
//      .foreach(println)

    println(estimatedFraction.toDebugString)

    //our join above takes an RDD with (String, (String, Int)) and an RDD with (String, Int)
    // and produces an RDD with (String, ((String, Int), Int))

//    (Danish_1st_Division,((List_of_Danish_football_champions,31),5180))
//    (Danish_1st_Division,((Silkeborg_IF,19),5180))
//    (Danish_1st_Division,((2019–20_Danish_1st_Division,49),5180))
  }

}
