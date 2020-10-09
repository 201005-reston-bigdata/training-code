import scala.annotation.tailrec
// Recursion is when a function calls itself.  The classic recursive function to
// start with is factorial.  A factorial is just the product of numbers 1 to n
// ex: factorial(5) = 5 * 4 * 3 * 2 * 1 = 120 = 5!
// We can write a recursive solution here because 5! = 5 * 4! and 4! = 4 * 3! ...

def myFactorial(x: Int): Int = {
  // recursive step and base case, outlined above
  println(s"x: ${x}")
  if (x < 1) 1 else x * myFactorial(x-1)
}

myFactorial(5)

// the other classic recursive example is fibonacci, we can come back here
// later if we'd like


@tailrec
val myRecurse : Int => Int = (x:Int) => {
  x match {
    case 0 => 1
    case _ => myRecurse(x-1)
  }
}

myRecurse(30)