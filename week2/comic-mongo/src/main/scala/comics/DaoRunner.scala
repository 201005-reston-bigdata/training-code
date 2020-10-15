package comics

import org.mongodb.scala.MongoClient

object DaoRunner extends App {

  val client = MongoClient()
  val comicDao = new ComicDao[Comic](client)

  println(comicDao.getAll())

  println(comicDao.getByTitle("Scooby Apocalypse"))

  println(comicDao.deleteByTitle("Scooby Apocalypse"))

}
