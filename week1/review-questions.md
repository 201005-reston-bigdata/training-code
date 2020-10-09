# Day 1

- What is Git?

Git is a version control system (VCS) for source code. It tracks changes to source code and provides support for branching and distributed development. Its efficient and very fast and scalable. Its also available on most operating systems, including linux, windows and macos.

- What about GitHub? How are they related?

GitHub is a source code repository, along with storing the actual source code and Git histories, it provides extended functionality through desktop clients and plugins, search functions, issue tracking and an organized way to track and commit source code change suggestions (through pull requests). It also provides GitHub Actions which can be used to build continuous integration and continuous deployment by providing pipelines for testing, releasing and deploying software automatically. Other examples of source code repositories are GitLab and SourceForge.

- What command moves changes from GitHub to our local repo?

`git add .` will move changed files from the CWD (current working directory) to the staging area (aka index). It will not move files that match the filespecs contained in the .gitignore file.

`git commit -m "message"` will take a snapshot of the repositories changes, including files added to the staging area.

- What command moves changes from our local repo to GitHub?

`git push` will upload the changes from the local repository to GitHub (aka the remote repository)

- What two commands are used in sequence to save changes to our local repo?

```
git add .
git commit -m "some fancy commit message"
```

- What is BASH?

BASH stands for Bourne Again SHell and it is a command line interface (CLI) for Unix/Linux based operating systems. It is a command processor that contains a set of low-level functions for interacting with the computer. It can also be extended by adding software to it. It provides scripting and scheduling functionality to automate individual and sets of commands.

- What does the PATH environment variable do?

The PATH environment variable on Windows and Unix/Linux operating systems is a list of directories where executable files (commands) are located. When the user types a command and hits enter, the operating system will check each of the directories in the PATH environment list and see if that entered command resides in any of them. The PATH environment can be edited to add new commands or remove deleted/deprecated commands. On many systems it is necessary to reboot or reset the computer after altering a PATH to allow the system to place the new PATH in memory.

- What is the JDK and what does it let us do?

The JDK (or Java Development Kit) allows us to develop Java software. It contains the JRE, the java interpreter, the javac compiler, an archiver (jar) and a documentation generator (javadoc) along with other tools and libraries that are necessary to create Java applications. The JDK will convert our programs into bytecode format that the JRE can run.

- What is the JRE and what does it let us do?

The Java Runtime Environment (or JRE) runs on top of the computer operating system and provides class libraries and other resources Java apps need. It combines the compiled code created by the JDK with libraries to run the program. The JRE is made up of the ClassLoader, Bytecode verifier and Interpreter. A more complete list is available here: https://www.ibm.com/cloud/learn/jre

- What's the name for the code written in .class files?

The .class files are created by the javac Java compiler and contains Java bytecode that can be loaded and run on the JVM.

- How does Scala relate to Java?

Scala is compiled into Java bytecode, the low-level language understood and executed on the JVM. Scala provides "language interoperability" with Java, meaning code libraries can be shared. Scala is Object Oriented like Java, but is built for functional programming rather than Object Oriented programming.

- What is sbt (high level)?

sbt is a software tool for building Scala and Java projects. It helps organize and efficiently compile, test and deploy projects. It helps track libraries and dependencies as well as assist in debugging projects.

- How does Scala relate to the JRE and JVM?

Scala programs are translated into Java bytecode which is loaded into the JRE along with the classes and libraries it requires. The JVM is installed along witht he JRE and the JVM interprets the bytecode. The JVM does the heavy lifting when it comes with interacting with different operating systems and computers. A useful illustration is available at https://getkt.com/blog/difference-between-jdk-jre-and-jvm-explained-java/

# Day 2

- Is Scala statically or dynamically typed?

Scala is statically typed. Every piece of data is an object, every object has a type. Static typing means the type is checked at compilation. Using the wrong type in an expression will cause a compilation (rather than runtime error). This results in more robust and trustworthy code.

- Do we need to declare type for our variables in Scala? always?

Scala provides type inference, which means that Scala will try and guess the type of the data your are assigning to your variable. We do not always have to declare the type of our variables.

- What are integer types in Scala?

The integer types available in Scala are:

- Byte (8 bits) (-2^7 to 2^7-1, inclusive)
- Short (16 bits) (-2^15 to 2^15-1, inclusive)
- Int (32 bits) (-2^31 to 2^31-1, inclusive) _default_
- Long (64 bits) (-2^63 to 2^63-1, inclusive)
- BigInt

- Decimal types?

The decimal types available in Scala are:

- Double (32 bits) (IEEE 754 single-precision float) _default_
- Float (64 bits) (IEEE 754 double-precision float)
- BigDecimal

- What is the size of an Int? a Double? (the default integer and decimal types)

  - Int (32 bits) (-2^31 to 2^31-1, inclusive) _default_
  - Double (32 bits) (IEEE 754 single-precision float) _default_

- What is the difference between val and var?

A variable initialized with the `val` keyword will be _immutable_. A variable with `var` will be _mutable_.

- Why do we make use of programming paradigms like Functional Programming and Object Oriented Programming?

We use these programming paradigms to organize a code base that grows larger over time, and has many people working on it. It creates a consistent way to build software. It helps to manage complexity in a large body of code.

- What are the 4 pillars of OOP: Abstraction, Encapsulation, Inheritance, Polymorphism? (a bit on each)

Abstraction is the act of hiding complexity from the end-user, by providing a front-end interface for the user to interact with the object or program, without having to know the details of what is going behind the scenes.

Encapsulation is the principle that Objects should take care of their own data. This means that the object projects its state (values) through the use of getters and setters so that they cannot be directly manipulated.

Inheritance is the strategy of using inherited Classes (the Superclass) to reuse code. In this way a child class can inherit the attributes and methods of its parent class.

Polymorphism or multiple forms, allows an object to take on different characteristics and respond in different ways depending on the parameters it is provided (method overloading/static polymorphism/compile-time). A different signature (set of parameters) will trigger different methods (ex: animal class, that is a parent to dog and cat subclasses. Depending on child class, speak() will output different strings.) Another form of polymorphism is runtime polymorphism (dynamic polymorphism) which involves multiple classes where methods will be different but have the same name and signature (set of parameters).

- What are classes? Objects?

A class is user-defined blueprint to create an object. A class can contain fields and methods and is identified with a name (identifier). An object is an instance of class, when it is created a place in memory is referenced for it and the class constructor is called.

- What is the significance of the Object class in Scala?

Everything in Scala is an object. There are no primitives, numbers are objects. Functions are objects.

- What does it mean that functions are first class citizens in Scala?

This means that functions will support all operations available to other entities (like variables). They can be assigned to a variable, passing them as arguments (parameters) and return them from functions. An example is _map_ which takes a list and function as an argument and returns a list after applying the function.

- What is a pure function?

A pure function does not cause any side effects. It does not access external objects, for example changing variables or data, print, or output to disk, it simply returns a value consistently. Given the same input, it will always return the same output. Pure functions will not break anything outside the function, guarantees order of execution, makes unit testing easier, making caching easer, makes the code self-contained and easier to understand, with no unexpected side-effects.

- What is a side effect?

A side effect is a change in the state of variables, printing or outputting to display or filesystem, or has another observable effect besides returning a value to the invoker of the function.

- What's the difference between an expression and a statement?

An expression is a unit of code that returns a value. A statement is a unit of code that doesn't return a value. Multiple expressions can be combined with curly braces to create an _expression block_. A common statement is println() and value and variable definitions. A good reference available at https://www.oreilly.com/library/view/learning-scala/9781449368814/ch03.html

- Is if-else an expression or a statement in Scala?

The if-else construct is an expression in Scala because it always returns a result.

- What's the difference between a while loop and a do-while loop?

The while loop will only execute if the a condition is met, where a do-while loop is guaranteed to execute at least once even if ithe condition is false.

- How does String interpolation work in Scala?

Strings can be interpolated so that variable references can be put inside the quotes of string literals and processed inline. There are three prepended symbols that provide different interpolation methods. They are `s`, `f` and `raw`. `s` is the String interpolator and when it is used:

```
var month = "October"
var date = 1
println(s"Today is $month $date") // Today is October 1
```

The variables will be output as Strings.

The `f` Interpolator variables must be referenced with a printf-style variable type reference. It is typesafe. The `raw` interpolator will output the literal string without escaping any literals (ex: \n)

- How do operators work in Scala? Where is "+" defined?

Operators are methods that are connected to objects. They are overriden from the base class and have specific functionality depending on the class of the object its attached to.

- What are the ways we could provide default values for a field?

We can define them in the primary Class constructor:

```
class Car(var color: String = "white", var doors: Int = 4)
```

We can also define them in auxillary class constructors:

```
class Car(var color: String, var doors: Int) {
  def this(color: String) = {
    this(color, 4)  // accepts color parameter and sets doors to default of "4"
  }
  def this(doors: Int) = {
    this("white", doors)
  }
  def this() = {
    this("white", 4) // sets both default parameters.
  }
}

```

- How do we define an auxiliary constructor?

An auxillary constructor takes care of cases where a function is not provided with all of the arguments (parameters) it requires to initialize. Auxillary constructors can be made for different cases where certain parameters are left out, or for all-other cases so that the object can be initialized even if it doesn't receive all the values it requires.

- What is an enumeration?

An enumeration is a limited set of values for a particular case. We can use enumerations to limit the choices that can be sent to a function to prevent input values that are outside of what values are expected.

- What is a REPL?

REPL stands for Read-Evaluate-Print-Loop and is a command line tool that can be used to try out Scala code. To enter the Scala REPL, use the command `scala` or open a Scala worksheet in Intellij. You will be able to write Scala expressions and see the output. The REPL will automatically assign variables for the output of expressions. Use :help for list of commands, :quit to exit.

- How does the Scala REPL store the results of evaluated expressions?

The REPL will store the results (return values) of evaluated expressions in a variable that is available for reuse.

# Day 3

- What is a higher order function?

A higher order function is a function that either takes a function as an argument or returns a function.

- What is function composition?

Function composition is the act of building more complex functions by combining simple functions. It encourages the factoring (breaking apart) of complex functions. The result of one function is passed into the next function as an argument. The result of the final function is the result of the whole.

- Why might we want to write an application using pure functions instead of impure functions?

Pure functions are predictible and easy to test, while impure functions can be unpredictible and have unexpected side effects. Impure functions also are harder to test because state may have been changed outside of the context of the function.

- Why might we want mutable state (OOP) in our application? Why might we want immutable state (FP)?
  - This question is open-ended, don't focus on finding the "right" answer
    Having immutable state (FP) will allow us to more easily run applications in parallel, and startup and stop services without having to retrieve or store a particular state. Mutable state is useful, though more difficult to manage, in cases where customization or "distinct identity" are used. Rather than create new instances of an object everytime a change is made, keeping the same object and modifying the attributes may be more efficient.
- What are some examples of side effects?

Some examples of side effects are: outputting to screen or filesystem (I/O) , modifying a mutable variable or data structure, throwing exceptions or errors.

- What is a Lambda?

A Lambda (or lambda function, lambda expression, anonymous function, or closure) is a nameless function written inline. Its an expression which can be used in place of a variable/value.

- How can we write Lambdas in Scala?

One parameter: val mySum = (x: Int) => x + x  
Two parameters (using type inference): val myName = (first: String, last: String) => first + " " + last
Two paraemters (w/o type inference): val myName: (String, String) => String = (first: String, last: String) => first + " " + last

- What does map do?

Map takes in an iterable data structure and a function (this can also be a lambda/anonymous function), and returns an iterable data structure made up of the result of applying the function to each element in the original. To forgo the returned collection, we can use foreach for situations where we need to create side effects but dont care about the return value.

- What does filter do?

Filter takes in an iterable and a function that evaluates each element in the collection and returns a boolean. It returns only the elements from the original collection that return a true value.

- What does reduce do?

Reduce takes in an iterable and a function that takes 2 parameters and applies the function to the pair, it then takes that output and uses it as one of the parameters for the next iteration, and using the next element of the original iterable as the other. It continues this until it goes through each element of the collection.

- What does it mean that a function returns Unit in Scala?

This means that the function is not expected to have a return value. These kinds of functions are run for their side effects.

- What is recursion?

Recursion is the defining of something in terms of itself. In programming recursive functions are functions which calls itself as a subfunction repeatedly. It is useful in solving problems whose solutions are smaller versions of itself, so by breaking the problem down into smaller and smaller pieces and then combining the results. Also called the divide-and-conquer method.

- What do we need to include in any effective recursive function?

To prevent a recursive function from calling itself infinitely there needs to be a base case (smallest division of the problem). The base case is the point at which the problem can no longer be divided and whose solution can be returned directly.

- What is parameterized typing?

Parameterized typing is where we say Array[Int] to make an int array or Array[String] to make a string array. Parameterized typing just means that the type is accepted as a parameter. (samuel owens)

- What is the Stack? What is stored on the Stack?
- What is the Heap? What is stored on the Heap?
- What is garbage collection?
- When is an object on the Heap eligible for garbage collection?
- How do methods executing on the stack interact with objects on the heap?
- Know the basics of the following Scala Collections: (mutable? indexed? when to use it?)
  - List
  - Set
  - Map
  - ArrayBuffer
  - Vector
- What is a generic?
  - the phrase "compile time type safety" is useful in this answer
- What is an Option?
- What is found inside an empty Option?
- How do we write an Option that contains a value?
- What are Exceptions?
- What are Errors?
- What is Throwable?
- How would we write code that handles a thrown Exception?
- How do we write code to throw an Exception?
