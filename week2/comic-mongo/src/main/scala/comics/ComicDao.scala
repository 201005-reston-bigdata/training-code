package comics

import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.{MongoClient, MongoCollection, Observable}

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}
import org.mongodb.scala.bson.codecs.Macros._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.model.Projections._

/** A Data Access Object for Comics.  The goal of this class is to encapsulate
 * all the mongoDB related parts of retrieving Comics, so the rest of our
 * application doesn't have to concern itself with mongo. */
class ComicDao(mongoClient : MongoClient) {

  val codecRegistry = fromRegistries(fromProviders(classOf[Comic]), MongoClient.DEFAULT_CODEC_REGISTRY)
  val db = mongoClient.getDatabase("comicdb").withCodecRegistry(codecRegistry)
  val collection : MongoCollection[Comic] = db.getCollection("comics")




  // we make getResults private, since it's not a functionality anyone should use
  // ComicDao for.
  private def getResults[T](obs: Observable[T]): Seq[T] = {
    Await.result(obs.toFuture(), Duration(10, SECONDS))
  }

  /** Retrieve all Comics */
  def getAll() : Seq[Comic] = getResults(collection.find())

  /** Retrieve comics by title */
  def getByTitle(title: String) : Seq[Comic] = {
    getResults(collection.find(equal("title", title)))
  }

  /** Delete by title.  Returns true if deleted count is > 0, returns false otherwise.
   * This method is a little dangerous as is */
  def deleteByTitle(title: String) = {
    try {
      getResults(collection.deleteMany(equal("title", title)))(0)
        .getDeletedCount > 0
    } catch {
      case e: Exception => {
        e.printStackTrace() //could be better
        false
      }
    }
  }

}
