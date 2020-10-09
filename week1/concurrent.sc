import scala.concurrent.duration.{Duration, MILLISECONDS, SECONDS}
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
// To start with Futures, we just use Future { //functionality goes here }

// Just listening to the errors for this line:
implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

val myImmediateReturnFuture = Future {
  // Future waits for 1 second before returning 100
  Thread.sleep(1000)
  100
}

val myFutureReliesOnOtherFuture = Future {
  Await.ready(myImmediateReturnFuture, Duration(10, SECONDS))
  200
}

myFutureReliesOnOtherFuture.onComplete {
  case Success(value) => println(value)
  case Failure(e) => e.printStackTrace()
}

// I like this probably the most for handling the results:
// specify a function that will run after the Future completes.
myImmediateReturnFuture.onComplete {
  case Success(value) => println(s"Future completed : $value")
  case Failure(exception) => println(exception)
}

println("doing other things")
println("on the main thread")
println("while our future runs")

//Thread.sleep to wait for our Future to be done is quite bad in almost every situation

//Await.ready will only wait until the Future is done, but it does block the main Thread
// Await.ready says wait until myImmediateReturnFuture is ready, or 1 second
Await.ready(myImmediateReturnFuture, Duration(10000, MILLISECONDS))

println(myImmediateReturnFuture)

List("red", "green", "orange", "blue").foreach(s => {
  val sFuture = Future {
    Thread.sleep((Math.random()*10000).toInt)
    s"$s completed!"
  }
  sFuture.onComplete {
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
  }
})

// We're just going to keep the main thread alive for awhile, this typically isn't
// necessary in real code.
Thread.sleep(10000)
