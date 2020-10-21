package comics

import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.MongoClient
import org.mongodb.scala.bson.codecs.Macros._

object DaoRunner extends App {

  val client = MongoClient()
  val codecRegistry = fromRegistries(fromProviders(classOf[Comic]), MongoClient.DEFAULT_CODEC_REGISTRY)
  val db = client.getDatabase("comicdb").withCodecRegistry(codecRegistry)
  val comicDao = new Dao[Comic](db.getCollection("comics"))

  println(comicDao.getAll())

  println(comicDao.getByTitle("Scooby Apocalypse"))

 // println(comicDao.deleteByTitle("Scooby Apocalypse"))

}
