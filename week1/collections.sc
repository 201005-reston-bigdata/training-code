import scala.collection.mutable.{ArrayBuffer, Map, Set}
// another option: wildcard import
//import scala.collection.mutable._

// Collections are way of storing multiple values.  We normally talk about the
// classes we use as Collections and say they are "backed by" data structures.

// It's possible to use Scala Collections without knowing much about what data
// structures are behind them, knowing about the data structures just lets you
// use collections more effectively.

// Let's start by talking about *Generics*.  Generics let us specify the type
// stored inside a collection.  We write generics in scala using [].
// More generally, Generics let us parameterize over type -- use a Type
// as a parameter.

// Let's with ArrayBuffer : ArrayBuffer of Ints
val ints = ArrayBuffer[Int]()
val strings = ArrayBuffer[String]()

// ArrayBuffer is mutable, we can modify its values and changes its length
// ArrayBuffer is indexed, it's like a mutable array.

// add values
ints += 1
ints += 4

// remove values
ints -= 1

//Add/remove multiple values
ints ++= List(4,5,6,7)

ints --= List(4,5)

// check methods on ints with autocomplete (or ctrl + click to head to class)
ints.append(33)
ints.prepend(44)
ints.appendAll(List(3,4,5,6))
ints.remove(3,3)
ints.trimEnd(2)
ints.insert(2, 60)
ints

// ArrayBuffer is implemented with arrays under the hood, so good for random
// access

// List is an immutable sequence.  It still stores elements in order like an
// ArrayBuffer, but the underlying data structure is different and it can't be
// modified.
val doubles = List[Double](3.3, 5.5, 7.7)

// any methods we call on a List, since it's immutable, will return a new List
// prepend a value
4.4 +: doubles
// append a value
doubles :+ 6.6

// prepend and append multiples with ++: and ++:

List(8.8,9.9) ++: doubles
doubles :++ List(2.343435, 5.1)

// The ':' goes on the side with the List

// Inserts a value into a new list at index 1
doubles.patch(1, List(6.6), 0)

// under the hood, Lists are singly-linked lists.  At the end of a List
// is an element pointing to Nil.  Because Lists are singly linked, it's
// very inefficient to append to them (but very efficient to prepend)
// Lists aren't good for random access in the way ArrayBuffers are.

// Lists are immutable and are often used in FP-style code.  Immutable here
// means the List cant change size and its elements cant be replaced.  However,
// if we have a List containing mutable objects, the contents of the objects
// *can* be changed.

class MutableFieldHaver(var field: String) {
  override def toString() = field
}

val mutableObjList = List(new MutableFieldHaver("one"), new MutableFieldHaver("two"))

mutableObjList.foreach((mfh: MutableFieldHaver) => {mfh.field = "modified field"})

println(mutableObjList)

// Our immutable List has its mutable objects' contents changed.  Typically
// we'll use Lists with immutable Objects.

// caveat 2 is less tricky, but worth pointing out:

var myList : List[Int] = List(1,2,3)
myList = List(4,5,6)
myList = List(7,8,9)

// If we want totally immutable data using Lists we need:
// - declare with val
// - use a List (it's immutable!)
// - use immutable objects inside the List

// Vector is an indexed, immutable sequence.  It's like ArrayBuffer in that it's
// an indexed sequence (uses arrays under the hood).  It's like List in that
// it's immutable.  Good for random access.

val longs = Vector[Long](2,5,7)

// since it's immutable, all modifications instead return a new Vector

//append
longs :+ 5L
longs ++ Vector(77L,88L)

//prepend
1L +: longs

// Map is a data structure that contains key, value pairs.  It's like a dictionary
// in some other languages.  It's also like a dictionary in real life, with
// words being keys and values being their definitions.  Maps don't need to
// contain Strings though, we can create key-value pairs with any data types.

// We'll what to use maps when you need to store some data that can be rapidly
// accessed (the data is the value, the way we access it is the key.

// Map can be either immutable or mutable.  Just import the appropriate version
// default is immutable

val states = Map(
  "AK" -> "Alaska",
  "IL" -> "Illinois",
  "KY" -> "Kentucky"
)

// We can use an immutable map and a mutable map using the fully qualified
// class name (that includes the packages)
val imMap = scala.collection.immutable.Map(
  "fish" -> "underwater animal"
)

val mMap = scala.collection.mutable.Map(
  "fish" -> "underwater animal"
)

// Will discuss Option later
//states.get("KY")

states("KY")
states("AK")

// add to a Map
states += ("AR" -> "Arkansas")

states("AR")

// remove by key
states -= "KY"

// Modify elements by just rewriting the value for a key
states("AK") = "Alaska, a really big state"

states("AK")

// Loop over the pairs in a Map using a for loop or a foreach
states.foreach {
  case(key, value) => println(s"${key} : ${value}")
}

// The default Map is a hashtable under the hood.  This means accessing values
// by key and checking whether the Map contains a key are both *very* fast.
// Keys should have a reasonable hashCode() function implemented, we can
// rely on autogeneration for the most part.  Keys should be immutable as well.

// A Set is a collection with no duplicate elements.  We can iterate over it
// but it doesn't have an index.  Some varieties of Sets have a guaranteed
// order, others do not.  Sets can be both immutable or mutable, the same as
// Maps.  Checking whether a value is contained in a Set is *very* fast.
// We can think of a Set as a Map without values (they are similar)

val shorts = Set[Short](1, 2, 3, 4)

shorts += 5
shorts ++= List(6,7)

// set cannot contain duplicates, so trying to add duplicates will do nothing:
shorts ++= List(1,3,7)

//we can use add to add elements, it will return true if successful and false
// otherwise
shorts.add(5) //false
shorts.add(9) //true

shorts.remove(3)

shorts

// The default Set is a hashtable under the hood.  We can also use SortedSet
// which is a search tree under the hood or LinkedHashSet which is both
// a hash table and a linked list.



// When writing FP style code in Scala, we often use Option, possibly contained
// in collections or returned from Map methods.

// states.get returns an Option[String], which will contain one of two things:
// one option is None, when the key doesn't exist in the Map
// the other is Some(value) with the value in the Map.
// Option is used as one way of handling missing data.
states.get("AK") match {
  case Some(v) => println(v)
  case None => println("not found in Map")
}

// let's demo getting states from abbreviations if they exist using some collections
val abbrevs = List("AK", "KY", "WA", "VA", "FL", "AR")
abbrevs.map {states.get(_) match {
  case Some(v) => v
  case None => "Not Found"
}}

// We might not want to use Not Found here, we can keep the Options around:
abbrevs.map {states.get(_)}

// We can use flatten to remove nested data structures like our list of Option
// here:
abbrevs.map {states.get(_)}.flatten

// and we can use flatMap which maps and then flattens afterwards.
abbrevs.flatMap {states.get(_)}

List(List(2,3,4), List(7,8,9)).flatten

// flatMap can be handy when chaining transformations/functions that produce
// Options (+ other places)