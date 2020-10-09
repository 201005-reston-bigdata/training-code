package runner

// The most straightforward way to write an entry point to your Scala app
// is to write an object that extends App
// We can write a main method instead if you like.
object Runner extends App {
  println("Begin execution")

  println(new Dog("Brown"))

  try {
    println(new Dog("Blue"))
  } catch {
    case e: InappropriateDogColorException => System.err.println(e.getMessage)
  } finally {
    println(Dog.dogPopulation)
  }

  println("Runner done")
}
