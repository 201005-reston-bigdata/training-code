package runner

import runner.Dog.{APPROPRIATE_DOG_COLORS, dogPopulation}

class Dog(color: String) {
  if (!APPROPRIATE_DOG_COLORS.contains(color)) {
    throw new InappropriateDogColorException(s"${color} is not a valid color for Dog")
  }
  // add 1 to the dog Population for each Dog that is created
  dogPopulation += 1
}

// singleton companion object
object Dog {
  val APPROPRIATE_DOG_COLORS = Set("Black", "Brown", "Gray")
  var dogPopulation = 0
}
