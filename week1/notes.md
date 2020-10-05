### Week 1 notes

## Java Installation -- what is Java + what is installing?

When we install Java, we have some options.  Java code (.java files) goes through a multiple step compilation process. (ask me about compilation later).  First your .java files are turned into .class files (bytecode).  Then those .class are actually run on something called the JVM and compiled into *machine code*.  There are, broadly, 3 different tools we have to write + run Java : JDK, JRE, JVM.  The JDK is the Java Development Kit, used to compile and run Java.  The JDK does it all.  The JRE contains the JVM and is used to just run bytecode, it doesn't let us write and compile java code.  The JRE is used when we just need to run a java (or Scala) program.  When we're installing Java, we choose JDK or JRE.  A confusing point is that you can get both from OpenJDK.  We want a JDK because we want compilation tools.  You can tell if you have a JRE if the "java" command works.  You can tell if you have a JDK if the "javac" command works.

When we install Java, we save the JDK on our local machine.  Just having the JDK on our machine doesn't necessarily mean we'll be able to use java and javac commands in the shell.  The missing link here is something called PATH, an environment variable.  When we type commands into our shell, the shell doesn't immediately know whether the command we want exists or what to do.  All of the commands we use aren't built directly into the shell.  Instead, our shell finds those commands by searching all the locations stored in PATH.  All our shell is doing when we type "java" is searching all the directories listed in PATH and then running the first file it finds with the name "java".  SO, if you type java or javac in the shell and get command not found, first restart the shell, then if the problem persist, check your PATH.  The OpenJDK installer should give you the option to add java to your path, so do check that while installing.

## Scala Installation

For now, we're just going to use Scala in our IDE (IntelliJ).  IDE is Integrated Development Environment, a program that's useful for developers because it lets us write, run, debug, lint, generate, autocomplete , ... code!  Scala is built using sbt, the scala build tool.  We'll use sbt inside intelliJ. ... continued tomorrow!

