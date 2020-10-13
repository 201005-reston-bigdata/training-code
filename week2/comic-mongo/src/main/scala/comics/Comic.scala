package comics

import org.bson.types.ObjectId

//Any class that represents a Mongo document should be similar to this:
// it needs an _id field and an apply function that generates that field.
case class Comic(_id: ObjectId, title: String, year: Option[Int]) {}

object Comic {
  def apply(title:String, year:Option[Int]) : Comic = Comic(new ObjectId(), title, year)
}
