import scala.collection.mutable.ArrayBuffer

// Enumerations (enums elsewhere) let us provide a fixed list of constant options
// The classic use case for enums is to replace Strings that represent some config
// If our program functions different on macos vs windows we might have a flag
val OS = "Mac"
//val OS = "Windows"
// The problem with just using Strings is that there may be multiple ways to
// write a string flag, which can confuse future devs and lead to mistakes
// for example, we might have:
// "windows", "microsoft windows", "windows 8", ...
// "MacOS", "mac", "Snow Leopard", ...
// We can use an enum instead so there are only 2 possible options

// enums in Scala look like this:
sealed trait OperatingSystem
case object Mac extends OperatingSystem
case object Windows extends OperatingSystem

// With this we can make a Pizza class, using enums:
sealed trait Topping
case object Cheese extends Topping
case object Pepperoni extends Topping
case object Sausage extends Topping
case object Mushrooms extends Topping
case object Onions extends Topping
case object Pineapple extends Topping
case object Ham extends Topping

sealed trait CrustSize
case object SmallCrustSize extends CrustSize
case object MediumCrustSize extends CrustSize
case object LargeCrustSize extends CrustSize

sealed trait CrustType
case object RegularCrustType extends CrustType
case object ThinCrustType extends CrustType
case object ThickCrustType extends CrustType

class Pizza (var crustSize : CrustSize = MediumCrustSize,
            var crustType : CrustType = RegularCrustType) {
  // we'll use an ArrayBuffer for our list of toppings
  // will see collections a bit later on, but this is a collection of things of
  // type Topping
  val toppings = ArrayBuffer[Topping]()

  // create methods to add and remove toppings
  def addTopping(t: Topping) = toppings += t
  def removeTopping(t: Topping) = toppings -= t
  def removeAllToppings() = toppings.clear()

  // add a toString so we can see what's going on with our pizza:
  override def toString() = {
    s"Crust: ${crustType} ${crustSize} with Toppings: ${toppings}"
  }
}

val hawaiian = new Pizza(LargeCrustSize)
hawaiian.addTopping(Cheese)
hawaiian.addTopping(Ham)
hawaiian.addTopping(Pineapple)

println(hawaiian)




