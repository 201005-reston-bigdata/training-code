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
- Do we need to declare type for our variables in Scala? always?
- What are integer types in Scala?
- Decimal types?
- What is the size of an Int? a Double? (the default integer and decimal types)
- What is the difference between val and var?
- Why do we make use of programming paradigms like Functional Programming and Object Oriented Programming?
- What are the 4 pillars of OOP: Abstraction, Encapsulation, Inheritance, Polymorphism? (a bit on each)
- What are classes? Objects?
- What is the significance of the Object class in Scala?
- What does it mean that functions are first class citizens in Scala?
- What is a pure function?
- What is a side effect?
- What's the difference between an expression and a statement?
- Is if-else an expression or a statement in Scala?
- What's the difference between a while loop and a do-while loop?
- How does String interpolation work in Scala?
- How do operators work in Scala?  Where is "+" defined?
- What are the ways we could provide default values for a field?
- How do we define an auxiliary constructor?
- What is an enumeration?
- What is a REPL?
- How does the Scala REPL store the results of evaluated expressions?
