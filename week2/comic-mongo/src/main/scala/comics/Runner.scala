package comics

import org.mongodb.scala.{MongoClient, MongoCollection, Observable}

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}
// make sure to start with imports in your own code
import org.mongodb.scala.bson.codecs.Macros._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}

object Runner extends App {

  //Mongo scala boilerplate for localhost:
  // include classOf[T] for all of your classes
  val codecRegistry = fromRegistries(fromProviders(classOf[Comic]), MongoClient.DEFAULT_CODEC_REGISTRY)
  val client = MongoClient()
  val db = client.getDatabase("comicdb").withCodecRegistry(codecRegistry)
  val collection : MongoCollection[Comic] = db.getCollection("comics")

  //helper functions for access and printing, to get us started + skip the Observable data type
  def getResults[T](obs: Observable[T]): Seq[T] = {
    Await.result(obs.toFuture(), Duration(10, SECONDS))
  }

  def printResults[T](obs: Observable[T]): Unit = {
    getResults(obs).foreach(println(_))
  }

  printResults(collection.find())

  printResults(collection.insertOne(Comic("scala comic!", Some(10000))))


}
