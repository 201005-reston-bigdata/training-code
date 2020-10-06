val demoString = "hello world"

println(demoString)

// Comments in Scala are written like this
// There are two ways to declare variable/values in Scala: val and var
// val is for things that can't change -- values
// var is for things that can change -- variables

//x = "goodbye"

// type inference : y is an Int since we're assigning 44 to it.
var demoInt = 44

demoInt = 6

demoInt = demoInt + 3

// This fails since Scala is statically typed.
// y is an Int and cant take a String
//y = "hello world"

// If we want to declare types, we do it TypeScript style rather an Java style
var demoType : String = "goodbye"

demoType = "apples"

// fn is a function that takes an int and returns that int plus 5
// we call this a lambda, it's a function defined inline using an =>
val fn = (x: Int) => x + 5

fn(1)

// fn2 is a function that takes an int and returns that int plus 9
val fn2 = (x: Int) => fn(x) + 4

fn2(1)

// fn3 is a function that takes no args and returns fn2
// returning a function from a function
val fn3 = () => fn2

fn3()(10)

val fn4 = (x: Int) => (y:Int) => x + y
// what does fn4 do?
// it takes an Int, and it returns a function
// the function it returns takes an Int and returns a sum, which is an Int
// The sum is the argument passed to fn4 combined with the argument passed
// to the returned function.

val addTen = fn4(10) // fn4(10) returns a function that adds 10
addTen(2)

var addTwenty = fn4(20)
addTwenty(2)

//this adds 2 numbers:
fn4(3)(4) // gets us 7

// fn5 takes a function and returns the result of applying that function
// to the Int 3.  We specify that the function fn5 takes must take and return
// Int
val fn5 = (f: Int => Int) => f(3)
//val fn4 = (x: Int) => (y:Int) => x + y
fn5(addTen)

// fn4 vs fn5:
// fn4 takes an Int, returns a function
// fn5 takes a function, returns an Int
fn5(fn4(11))

println(res3)

// Let's head back to the basics from FP
// Control structures:

var myNumber = 5

if(myNumber > 5) {
  println("bigger than 5")
} else if (myNumber < 3) {
  println("less than 3")
} else {
  println("neither bigger than 5 nor less than 3")
}

if(true) {
  println("always prints")
} else {
  println("never prints")
}

// if else returns a value in scala so we can use it as the ternary operator
// ternary op in many languages: condition ? returnIfTrue : returnIfFalse

var condition = false
if (condition) "yes" else "no"

// Scala has a match expression, which can be used like switch (but also more)
myNumber = 11
val matchResult = myNumber match {
  case 5 => "five"
  case 10 => "ten"
  case 15 => "fifteen"
  case _ => "not 5 10 or 15"
}

println(matchResult)

// for loops
for (i <- 0 to 10) {
  println(i)
}

for (j <- 3 to 30 by 3) {
  println(j)
}

// while loops, loop until condition is false, might never run
while(myNumber < 20) {
  myNumber = myNumber + 1 // add 1 to myNumber
  println(myNumber)
}

while(false) {
  println("never prints")
}

// do while loops, do once then while loop
do {
  myNumber += 1
  println(myNumber)
} while (myNumber < 30)

do {
  println("runs once")
} while (false)

// List of fruits
val fruits = List("apple", "banana", "lime", "orange")

// loop through fruits
for (f <- fruits) {
  println(f)
}

// we can also *yield* at the end of our for loop to create a for-expression
// this is going to return some values from the expression
val evenNums = for (i <- 0 to 10) yield i*2

val fruitLengths = for (f <- fruits) yield f.length

// instead of doing something with the value inside of the loop
for(f <- fruits) {
  println(f.length)
}

// In our code, we have chunks of code that return some value, these
// are called expressions.  for-expressions with yield are expressions
// and if-else is an expression in Scala, since it returns a value.
// We also have chunks of code that do something rather than return a value
// these are called statements.
myNumber + 5 // returns a value -- get evaluated to something, is expression
println(myNumber) // does something rather than return, is statement
// for expressions with yield are expressions
// for loops are statements

// another way of seeing expressions vs statements:
// we run expressions for their result
// we run statements for their *side effects* -- the things they do other than
//  returning


// Some types in scala:
// first, coming from Java we have one big difference: there are no primitives
// in Scala.  EVERYTHING is an Object.
// Boolean : true, false
// Integral types:
// Byte : integer, 8 bits or 1 byte, contains values -128 to 127
// Short : integer, 16 bits or 2 bytes
// Int : integer, 32 bits or 4 bytes
// Long : integer, 64 bits or 8 bytes, contains values -9223372036854775808
//  to 9223372036854775807 (see Long.MaxValue and Long.MinValue)
// BigInt : integer, any size
// if we go out of bounds for any of the fixed size integers we'll overflow
// which means the number wraps around.  We should almost always avoid this,
// and BigInt makes it easy.
var myLongValue = Long.MaxValue + 10L
println(myLongValue)

var myByte : Byte = 127
var myOtherByte : Byte = 10
myByte = (myByte + myOtherByte).toByte
println(myByte)

// The default value for integral types is an Int:
val myDefaultIntegralType = 222

// Integer max value:
Math.pow(2, 31)

// Decimal types:
// Float : decimal, 4 bytes
// Double : decimal, 8 bytes
// BigDecimal : decimal, any precision
// Where the size of an integer specifies the max and min values,
// the size of a decimal value specifies the maximum precision of that value
// as a rule of thumb, Floats are precise out to 5 or 6 digits,
// doubles are precise out to 15 or 16 digits.
// Double is the default decimal value

val myDefaultDecimalType = 22.22


