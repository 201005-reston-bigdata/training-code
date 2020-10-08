def f1() : Unit = {
  println("begin f1")
  println("end f1")
}

def f2() : Unit = {
  println("begin f2")
  f1()
  println("end f2")
}

def f3() : Unit = {
  println("begin f3")
  f2()
  println("end f3")
}

f3()
