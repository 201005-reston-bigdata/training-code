// We left off talking data types
// 2 more to mention: String and Char for String and Character
// Chars use '' and are unicode (2 bytes unsigned ints under the hood)
// Strings are arrays of Chars and use ""
// All your String methods will return a new String, they won't change
// the existing String.
val myString :String = "hello world"
val myChar : Char = 'b'

myString.charAt(4)

// String concatenation can be done with +
myString + " more string"
//myString = myString + " more string"

val fruit1 = "apple"
val fruit2 = "pear"
val fruit3 = "orange"

// We can also do String interpolation, which can be easier
// to read
println(s"Fruits: ${fruit1}, ${fruit2}")

// Inside the curly braces we can put any expression
println(s"1 + 2 + 3 = ${1+2+3}")

// declare a function using def, works in REPL and in classes
// myFunc takes an Int x and returns an Int
def myFunc(x: Int): Int = x * 3

println(s"myFunc applied to 3 gives us: ${myFunc(3)}")

// we've mentioned that everything in Scala is an object.
// let's use autocomplete to see the methods on myString
// + is a method on Strings
myString.+(" more string")
// We can use spaces instead of . and ()
myString charAt 4

// Not only are all of our data types objects in Scala,
// but also all of our operators are just methods on
// those objects.  So Strings define their own +,
// Ints define their own +, and our data types can define
// their own +, if we want.  Any method can we written
// with spaces instead of . and () to look like an operator

// Scala classes!  much more concise than classes in Java / other langs
// Scala classes allow you to construct Objects of a new type

// make a Person with firstname and lastname
class Person(var firstName: String, val lastName: String)

// easy class definition that just stores some data, we create instances
// of our Person class using the new keyword:
var myPerson = new Person("Adam", "King")

println(myPerson)
println(s"First Name: ${myPerson.firstName}")

// val makes fields read only, so they can't be changed
myPerson.firstName = "Jeff" // this works b/c var
//myPerson.lastName = "Goldblum" // this fails b/c val

// we define our constructor by default we we declare a class
// we can add/modify objects of our class using {}
class DetailedPerson(var firstName: String, val lastName: String) {
  println("constructor begins")
  // we can add more fields that don't directly come from params:
  var age = 0
  // we can add access modifiers here if we like (we'll see those a bit later)

  //we can def methods for our Objects here
  def sayHello(): String = s"${firstName} says hello"
}

var adam = new DetailedPerson("Adam", "King")
adam.age = 27
adam.sayHello()

// in both of these cases, println sees that it needs to print an Object
// and uses the toString method on that Object.  Both Person and DetailedPerson
// are treated as just Objects in this context.  Polymorphism!
println(myPerson)
println(adam)

// Another class, for some practice
// The constructor we create here is called the primary constructor
// adding the = 0 here for sourness makes 0 the default value for that parameter
class Fruit(var color: String, var tastiness: Int,
            var sourness: Int = 0, var numSeeds : Int = 4) {
  println("*primary* constructor begins")

  //we can define other constructors (*auxiliary* constructors), similar to
  // overloading in Java
  def this(color: String) = {
    // our auxiliary constructors make use of the primary constructor:
    // this refers to this class -- Fruit
    this(color, 5) //default tastiness of 5
    println("*auxiliary* constructor")
  }

  def rot() = {
    this.color = "brown"
    this.tastiness = 0
  }

  // every single object has a toString method, that it gets from the Object
  // class, we can change the default toString by *overriding*
  override def toString: String = s"Fruit is ${color} with ${numSeeds} seeds"

}

val apple = new Fruit("red", 30)
val pear = new Fruit("green", 25)

apple.toString
apple.rot()
apple.toString

val banana = new Fruit("yellow")

// specify parameter names and use whatever order you like
val lime = new Fruit(numSeeds = 8,
    sourness = 20, tastiness = 20, color="green")

// or go in order, overwriting default params as needed
val lemon = new Fruit("yellow", 20, 20, 10)

println(lime)
println(lemon)


