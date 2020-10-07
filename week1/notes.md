### Week 1 notes

## Java Installation -- what is Java + what is installing?

When we install Java, we have some options.  Java code (.java files) goes through a multiple step compilation process. (ask me about compilation later).  First your .java files are turned into .class files (bytecode).  Then those .class are actually run on something called the JVM and compiled into *machine code*.  There are, broadly, 3 different tools we have to write + run Java : JDK, JRE, JVM.  The JDK is the Java Development Kit, used to compile and run Java.  The JDK does it all.  The JRE contains the JVM and is used to just run bytecode, it doesn't let us write and compile java code.  The JRE is used when we just need to run a java (or Scala) program.  When we're installing Java, we choose JDK or JRE.  A confusing point is that you can get both from OpenJDK.  We want a JDK because we want compilation tools.  You can tell if you have a JRE if the "java" command works.  You can tell if you have a JDK if the "javac" command works.

When we install Java, we save the JDK on our local machine.  Just having the JDK on our machine doesn't necessarily mean we'll be able to use java and javac commands in the shell.  The missing link here is something called PATH, an environment variable.  When we type commands into our shell, the shell doesn't immediately know whether the command we want exists or what to do.  All of the commands we use aren't built directly into the shell.  Instead, our shell finds those commands by searching all the locations stored in PATH.  All our shell is doing when we type "java" is searching all the directories listed in PATH and then running the first file it finds with the name "java".  SO, if you type java or javac in the shell and get command not found, first restart the shell, then if the problem persist, check your PATH.  The OpenJDK installer should give you the option to add java to your path, so do check that while installing.

## Scala Installation

For now, we're just going to use Scala in our IDE (IntelliJ).  IDE is Integrated Development Environment, a program that's useful for developers because it lets us write, run, debug, lint, generate, autocomplete , ... code!  Scala is built using sbt, the scala build tool.  We'll use sbt inside intelliJ.

## REPL

One of the differences/advantages of Scala over Java is the REPL.  REPL stands for Read Eval Print Loop, the REPL provides an interactive command line for your Scala runtime.  This near-instant feedback is very useful when exploring a new API or exploring datasets.  We'll use the REPL occasionally, since it exists everywhere.  In IntelliJ we'll most often use worksheets instead, since they add some features and save progress.  The REPL saves the results from our expressions in values res#, with the # starting at 1 and icnrementing.  We can access
values returned earlier in our REPL session using these names.

## Scala Features

- High level language (abstracts away the hardware, runs on the JVM)
- Statically typed, meaning variables can't change types
- Has a type inference system, so type does not always need to be declared.  Cuts down on the Java repetition
- Compiles down to .class files that run on the JVM
- Lets us easily use Java libraries in our Scala code.
- Supports OOP Paradigm: Abstraction, Encapsulation, Inheritance, Polymorphism, Classes + Objects:
  - In Scala we can create classes that are like blueprints used to create objects.  We create objects that are instances of those classes.
  - If you're coming from Java: In Scala we don't have static members (no state or behaviour on class itself), instead we have a Singleton object that works with each class.
  - OOP organizes our code into objects which have state and behaviour.  The interaction of those objects specifies how our application will run.
- Supports FP Paradigm: Functions as first class citizens
  - Something being a "first class citizen" of a language means that it can be passed into functions, returned from functions, and stored in variables and data structures.
  - This means in Scala (and other FP langs), functions can be returned from other functions, functions can be passed into functions as args, functions can be stored in data structures, ...
  - FP organizes our code into *pure functions* that each only take an input and produce an output.  Our application works via calling and composing those functions.
- Both of these programming paradigms are ways to organize a codebase that grows large over time and has multiple people working on it.

## OOP Basics

In OOP you create your application by defining classes and instantiating objects from those classes.  We create classes using the "class" keyword and create objects using the "new" keyword + a constructor.  The class lets us define state + behaviour (fields + methods) for objects.
There are the classic OOP examples of Dog classes that define name, age, color fields and have methods like bark, run, ...
More realistically, our OOP applications will have classes like JsonDeserializer or DbConnectionUtil that help us to deserialize JSON and connect to a Database.
The appeal of using classes and objects is that it lets us compartimentalize our code and makes our application more modular.  If we put all of our logic for reading and writing JSON into one class, then we don't need to write that logic anywhere else in our application, AND we can reuse that JsonDeserializer class across applications.  We have 4 pillars of OOP: Abstraction, Encapsulation, Polymorphism, Inheritance:
- Abstraction : hiding irrelevant detail (often implementation) and presenting only relevant details.  Abstraction means we can use objects that we don't understand the guts of.
- Encapsulation : "Data hiding", encapsulation means that Objects take control/responsibility for their own state, and protect their state from outside interference.
- Polymorphism : "Many Forms", an Object can take an appropriate from based on the context and a method can take an appropriate form based on the context.  An example is that we might have many different types of Objects that are all written to file in the same way.  Another example is both Person and DetailedPerson objects are printed the same way -- using toString.
- Inheritance : Classes can have superclasses that they inherit from and subclasses that inherit from them.  Inheritance lets us share state + behaviour both from superclasses to subclasses.  The subclass will have the fields + methods of the superclass.  We also say "Parent" and "Child" classes for super and subclasses.

In Java/Scala every single Class inherits from the Object class.  This means any state + behaviour in the Object class is shared with every single Object.  A punchline: every single Object has a toString method.  Object polymorphism means that every single Object can be treated as an instance of Object.

## FP Basics

In Functional Programming, we write our applications as pure functions and the application functions by applying and composing those functions.  To apply a function is to use it, for example the function we pass to map is applied to each element in the collection.  The compose two functions is to apply them in sequence, to put them together.  Composing f ang g would give you a new function defined as h(x) => f(g(x)).  Instead of called map twice in our REPL demo, to first addOne, then to double, we could *compose* the addOne and double functions and call map just once with the result of the composition.

A pure function is a function that takes only arguments and produces only a return value.  It shouldn't read any information for elsewhere, and it shouldn't do anything other than returning.  No printing, no writing to disk, no accessing fields on external objects, ... JUST taking an input and producing an output.  One way you know you have a pure function is if the function *always* returns the same value if given the same input.  Effects that a function has other than returning are called *side effects* and should be avoided in pure functions.

2 main advantages of pure functions : a pure function is easy to reason about because we don't need to consider anything outside of the function.  Also, pure functions are very easy to test.  All you need to do is give them an input and verify the output.

Typically in FP, we keep track of no mutable state in our application.  For us, we'll fuzz this a bit and keep limited mutable state.  Any time we're using map, reduce, filter, fold those functions will produce new values rather than modifying existing arrays.  Keeping no mutable state in your applciation also makes your application easier to reason about because you no longer have to worry about how that state is evolving through time.

Functions as first class citizens enable *higher order functions*.  A higher order function is just a function that takes another function as an argument and/or returns a function.  map, reduce, fitler, fold are higher order functions.

A Lambda is a function defined inline.  In Scala, we use (params) => {body}.  We have shorthand that lets us skip () for one parameter functions, and we have the _ shorthand that lets us skip most of the lambda expression.  Example: _ > 4 is almost (type diff) the same as (x: Int) => x > 4

Recursion is when a function calls itself.  In order to effectively write recursive functions, we need to follow some rules.  We'll need for our recursive function to change something about each call -- your recursive function should never call itself in exactly the same way that it was called.  This is called a "recursive step".  We also need some condition where the function won't call itself, instead it just returns a value.  This is called a "base case" or "exit condition".  We'll see an example after lunch.

In Scala, a return type of "Unit" means that the function doesn't return.  It's similar to void in Java.  Nothing is also for functions that don't return anything.  The difference is that Unit is for functions that don't return anything and are run for their side effects.  Nothing is the return for functions that should never return, meaning they always throw an Exception during execution and never successfully return.  We'll see Unit much more often than we'll see Nothing.

Scala:
def myCompose(f : Int => Int, g : Int => Int): Int => Int = {
   (x: Int) => f(g(x))
}
Without type declarations:
def myCompose(f, g) = {
    x => f(g(x))
}

Three higher order functions that are common + good to be familiar with:
- map : when we call map on a collection, we pass it a function and map produces a new collection by applying that function to each element of the prior collection.  All functions passed to map should be pure.  If you want to instead have some side effects for each element in a collection, use forEach instead.
- filter : when we call filter on a collection, we pass it a function that returns a boolean, and filter produces a new collection containing only the elements which produce true.
- reduce : when we call reduce on a collection, we pass reduce a function that takes 2 parameters and returns one result.  Reduce calls that function repeatedly, making use of the prior return and each element of the collection in turn.  Calling reduce on a collection will result in producing a single value, hence the name reduce.  Example with generic function: List(1,2,3,4).reduceLeft(f) wouldfirst evaluate f(1,2), then evaluate f(f(1,2), 3), then f(f(f(1,2), 3), 4).  Lets see a sum in the REPL





