package comics

import org.mongodb.scala.{MongoCollection, Observable}
import org.mongodb.scala.model.Filters.equal

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}

class Dao[T](collection: MongoCollection[T]) {

  // we make getResults private, since it's not a functionality anyone should use
  // ComicDao for.
  private def getResults[A](obs: Observable[A]): Seq[A] = {
    Await.result(obs.toFuture(), Duration(10, SECONDS))
  }

  /** Retrieve all Comics */
  def getAll() : Seq[T] = getResults(collection.find())

  /** Retrieve comics by title */
  def getByTitle(title: String) : Seq[T] = {
    getResults(collection.find(equal("title", title)))
  }
}
