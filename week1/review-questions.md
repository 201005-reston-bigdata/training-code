- What is Git?

Git is a version control system (VCS) for source code.  It tracks changes to source code and provides support for branching and distributed development.  Its efficient and very fast and scalable.  Its also available on most operating systems, including linux, windows and macos.  

- What about GitHub? How are they related?

GitHub is a source code repository, along with storing the actual source code and Git histories, it provides extended functionality through desktop clients and plugins, search functions, issue tracking and an organized way to track and commit source code change suggestions (through pull requests).  It also provides GitHub Actions which can be used to build continuous integration and continuous deployment by providing pipelines for testing, releasing and deploying software automatically.  Other examples of source code repositories are GitLab and SourceForge.

- What command moves changes from GitHub to our local repo?

`git add .` will move changed files from the CWD (current working directory) to the staging area (aka index).  It will not move files that match the filespecs contained in the .gitignore file. 

`git commit -m "message"` will take a snapshot of the repositories changes, including files added to the staging area.

- What command moves changes from our local repo to GitHub?

`git push` will upload the changes from the local repository to GitHub (aka the remote repository)

- What two commands are used in sequence to save changes to our local repo?

```
git add .
git commit -m "some fancy commit message"
```

- What is BASH?

BASH stands for Bourne Again SHell and it is a command line interface (CLI) for Unix/Linux based operating systems.  It is a command processor that contains a set of low-level functions for interacting with the computer.  It can also be extended by adding software to it. It provides scripting and scheduling functionality to automate individual and sets of commands.  

- What does the PATH environment variable do?

The PATH environment variable on Windows and Unix/Linux operating systems is a list of directories where executable files (commands) are located.  When the user types a command and hits enter, the operating system will check each of the directories in the PATH environment list and see if that entered command resides in any of them.  The PATH environment can be edited to add new commands or remove deleted/deprecated commands.  On many systems it is necessary to reboot or reset the computer after altering a PATH to allow the system to place the new PATH in memory.  

- What is the JDK and what does it let us do?

The JDK (or Java Development Kit) allows us to develop Java software.  It contains the JRE, the java interpreter, the javac compiler, an archiver (jar) and a documentation generator (javadoc) along with other tools and libraries that are necessary to create Java applications.  The JDK will convert our programs into bytecode format that the JRE can run.

- What is the JRE and what does it let us do?

The Java Runtime Environment (or JRE) runs on top of the computer operating system and provides class libraries and other resources Java apps need.  It combines the compiled code created by the JDK with libraries to run the program.  The JRE is made up of the ClassLoader, Bytecode verifier and Interpreter. A more complete list is available here: https://www.ibm.com/cloud/learn/jre

- What's the name for the code written in .class files?

The .class files are created by the javac Java compiler and contains Java bytecode that can be loaded and run on the JVM.

- How does Scala relate to Java?

Scala is compiled into Java bytecode, the low-level language understood and executed on the JVM.  Scala provides "language interoperability" with Java, meaning code libraries can be shared.  Scala is Object Oriented like Java, but is built for functional programming rather than Object Oriented programming.  

- What is sbt (high level)?

sbt is a software tool for building Scala and Java projects.  It helps organize and efficiently compile, test and deploy projects.  It helps track libraries and dependencies as well as assist in debugging projects.

- How does Scala relate to the JRE and JVM?

Scala programs are translated into Java bytecode which is loaded into the JRE along with the classes and libraries it requires.  The JVM is installed along witht he JRE and the JVM interprets the bytecode.  The JVM does the heavy lifting when it comes with interacting with different operating systems and computers. A useful illustration is available at https://getkt.com/blog/difference-between-jdk-jre-and-jvm-explained-java/

---

- Is Scala statically or dynamically typed?

Scala is statically typed.  Every piece of data is an object, every object has a type.  Static typing means the type is checked at compilation.  Using the wrong type in an expression will cause a compilation (rather than runtime error).  This results in more robust and trustworthy code.

- Do we need to declare type for our variables in Scala? always?

Scala provides type inference, which means that Scala will try and guess the type of the data your are assigning to your variable.  We do not always have to declare the type of our variables.  

- What are integer types in Scala?

The integer types available in Scala are:
  - Byte (8 bits) (-2^7 to 2^7-1, inclusive)
  - Short (16 bits) (-2^15 to 2^15-1, inclusive)
  - Int (32 bits) (-2^31 to 2^31-1, inclusive) *default*
  - Long (64 bits) (-2^63 to 2^63-1, inclusive)
  - BigInt 

- Decimal types?

The decimal types available in Scala are:
  - Double (32 bits) (IEEE 754 single-precision float) *default*
  - Float (64 bits) (IEEE 754 double-precision float) 
  - BigDecimal
  
- What is the size of an Int? a Double? (the default integer and decimal types)
  - Int (32 bits) (-2^31 to 2^31-1, inclusive) *default*
  - Double (32 bits) (IEEE 754 single-precision float) *default*

- What is the difference between val and var?

A variable initialized with the `val` keyword will be *immutable*.  A variable with `var` will be *mutable*. 

- Why do we make use of programming paradigms like Functional Programming and Object Oriented Programming?

We use these programming paradigms to organize a code base that grows larger over time, and has many people working on it.  It creates a consistent way to build software.  It helps to manage complexity in a large body of code.

- What are the 4 pillars of OOP: Abstraction, Encapsulation, Inheritance, Polymorphism? (a bit on each)

Abstraction is the act of hiding complexity from the end-user, by providing a front-end interface for the user to interact with the object or program, without having to know the details of what is going behind the scenes.  

Encapsulation 

- What are classes? Objects?

A class is user-defined blueprint to create an object.  A class can contain fields and methods and is identified with a name (identifier). An object is an instance of class, when it is created a place in memory is referenced for it and the class constructor is called.

- What is the significance of the Object class in Scala?


- What does it mean that functions are first class citizens in Scala?

This means that functions will support all operations available to other entities (like variables).  They can be assigned to a variable, passing them as arguments (parameters) and return them from functions.  An example is *map* which takes a list and function as an argument and returns a list after applying the function.

- What is a pure function?

A pure function does not cause any side effects.  It does not access external objects, for example changing variables or data, print, or output to disk, it simply returns a value consistently.  Given the same input, it will always return the same output. Pure functions will not break anything outside the function, guarantees order of execution, makes unit testing easier, making caching easer, makes the code self-contained and easier to understand, with no unexpected side-effects.

- What is a side effect?

A side effect is a change in the state of variables, printing or outputting to display or filesystem, or has another observable effect besides returning a value to the invoker of the function.

- What's the difference between an expression and a statement?

 An expression is a unit of code that returns a value.  A statement is a unit of code that doesn't return a value.  Multiple expressions can be combined with curly braces to create an *expression block*. A common statement is println() and value and variable definitions.  A good reference available at https://www.oreilly.com/library/view/learning-scala/9781449368814/ch03.html

- Is if-else an expression or a statement in Scala?

The if-else construct is an expression in Scala because it always returns a result.

- What's the difference between a while loop and a do-while loop?

The while loop will only execute if the a condition is met, where a do-while loop is guaranteed to execute at least once even if ithe condition is false.

- How does String interpolation work in Scala?
- How do operators work in Scala?  Where is "+" defined?

- What are the ways we could provide default values for a field?



- How do we define an auxiliary constructor?

An auxillary constructor takes care of cases where a function is not provided with all of the arguments (parameters) it requires to initialize.  Auxillary constructors can be made for different cases where certain parameters are left out, or for all-other cases so that the object can be initialized even if it doesn't receive all the values it requires.

- What is an enumeration?

An enumeration is a limited set of values for a particular case.  We can use enumerations to limit the choices that can be sent to a function to prevent input values that are outside of what values are expected.

- What is a REPL?

REPL stands for Read-Evaluate-Print-Loop and is a command line tool that can be used to try out Scala code.  To enter the Scala REPL, use the command `scala` or open a Scala worksheet in Intellij.  You will be able to write Scala expressions and see the output.  The REPL will automatically assign variables for the output of expressions. Use :help for list of commands, :quit to exit.

- How does the Scala REPL store the results of evaluated expressions?

The REPL will store the results (return values) of evaluated expressions in a variable that is available for reuse.