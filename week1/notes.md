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

