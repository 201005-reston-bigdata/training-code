package comics

import com.mongodb.client.model.UpdateOptions
import org.bson.BsonNull
import org.mongodb.scala.model.Filters
import org.mongodb.scala.{MongoClient, MongoCollection, Observable}

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}
// make sure to start with imports in your own code
import org.mongodb.scala.bson.codecs.Macros._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}

import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.model.Projections._

object DemoRunner extends App {

  //Mongo scala boilerplate for localhost:
  // include classOf[T] for all of your classes
  val codecRegistry = fromRegistries(fromProviders(classOf[Comic]), MongoClient.DEFAULT_CODEC_REGISTRY)
  val client = MongoClient()
  val db = client.getDatabase("comicdb").withCodecRegistry(codecRegistry)
  val collection : MongoCollection[Comic] = db.getCollection("comics")

  // UpdateOptions object that has upsert set to true, for convenience
  val upsertTrue :UpdateOptions = (new UpdateOptions()).upsert(true)

  //helper functions for access and printing, to get us started + skip the Observable data type
  def getResults[T](obs: Observable[T]): Seq[T] = {
    Await.result(obs.toFuture(), Duration(10, SECONDS))
  }

  def printResults[T](obs: Observable[T]): Unit = {
    getResults(obs).foreach(println(_))
  }

//  delete all documents with the field title
  printResults(collection.deleteMany(Filters.exists("title")))

//   specifying None when inserting means the field is null in BSON in Mongo
  printResults(collection.insertMany(List(
    Comic("Strangers in Paradise", Some(1993)),
    Comic("Archie Comics", Some(1942)),
    Comic("Scooby Apocalypse", Some(2016)),
    Comic("Eightball", Some(1989)),
    Comic("Ms. Tree", Some(1981)),
    Comic("Adam's Comic", None)
  )))

  //first filter: find all Documents with title = scala comic!
  printResults(collection.find(Filters.equal("title", "Eightball")))

  printResults(collection.find(Filters.gt("year", 2000)))

  println("no field year:")
  // this will get documents without the field "year"
  printResults(collection.find(Filters.exists("year", false)))
  println("with field year:")
  // this will get documents with the field "year"
  printResults(collection.find(Filters.exists("year", true)))
  println("with non-null field year:")
  // this will get documents that have a non-null value for the field "year"
  printResults(collection.find(Filters.notEqual("year", new BsonNull())))

  println("combine conditions with and or or")
  println("year between 1970 and 1990:")
  printResults(collection.find(and(gt("year", 1970), lt("year", 1990))))
  println("year after 1990 or title Eightball")
  printResults(collection.find(or(gt("year", 1990), equal("title", "Eightball"))))

  // We can update values using collection.updateOne or
  printResults(collection.updateOne(equal("title", "Adam's Comic"),
    set("title", "Best comic!")
  ))

  printResults(collection.updateOne(gt("year", 1000),
    set("title", "New comic! Upsert?"),
    upsertTrue
  ))

//  printResults(collection.updateMany(gt("year", 1980),
//    combine(set("title", "borg comic!"), set("year", 2222))
//  ))

  // we can also rely on Scala machinery to make changes by using replaceOne
  // replacing an object will change all its fields to the new object's fields
  // but it won't change the _id

  val archieComic : Comic = getResults(collection.find(equal("title", "Archie Comics")))(0)

  println(archieComic)

  // when this executes, we modify the archieComic document to have Replacement title and year 1998
  printResults(collection.replaceOne(equal("title", "Archie Comics"),
    archieComic.copy(title = "Replacement title", year = Some(1998))
  ))

  // when this executes, we modify the archieComic document to have Re-Replacement title and year 1942
  //another useful option for this flow:
  printResults(collection.replaceOne(equal("_id", archieComic._id),
    archieComic.copy(title = "Re-Replacement title!")
  ))

  printResults(collection.find().sort(ascending("year")))

  printResults(collection.find().sort(descending("title")))

  println("Can use regex to match strings as a filter:")
  // can use regex to jsut match pieces of a string
  // can also do fancier things: "^E" for strings that start with E
  // "\\s" to match a whitespace character
  printResults(collection.find(regex("title", "comic")))

  println("Can limit the number of results we retrieve: 3 Comics : ")
  printResults(collection.find().limit(3))

  println("Earliest 3 comics:")
  printResults(collection.find().sort(ascending("year")).limit(3))

  println("Earliest 3 comics that have a year:")
  printResults(collection
    .find(notEqual("year", new BsonNull()))
    .sort(ascending("year"))
    .limit(3)
  )

  // Projections let us only retrieve some properties from the document
  // only some key-value pairs
  // Note that this happens when we're getting the documents from Mongo, which
  // means it happens before they are turned into Scala case classes.
  printResults(collection.find().projection(include("title")))

  // When using case classes, it makes the most sense to include fields for the
  // case class and exclude fields not used by the case class.
  printResults(collection.find().projection(include("title", "year")))

  // With projects can can also excludeId, though that will break
  // in Scala using case classes.

  //printResults(collection.find().projection(excludeId()))

  client.close()

}
