package wccli

import java.io.FileNotFoundException

import scala.io.StdIn
import scala.util.matching.Regex

/** CLI that interacts with the user in WCCLI program */
class Cli {

  // Regular Expressions are a very handy tool that lets us pattern match strings
  // and extract pieces of strings that match patterns.
  // For this app, I'll give us some regex to use.  Check regex cheatsheets / google
  // to find others.

  /** commandArgPattern is regex that we get us a command and arguments to that
   * command from user input */
  val commandArgPattern : Regex = "(\\w+)\\s*(.*)".r

  def printWelcome() : Unit = {
    println("Welcome to WCCLI")
  }

  def printOptions() : Unit = {
    println("file [filename] : wordcount the contents of a file")
    println("exit : close WCCLI")
    println("please enter an option.")
  }

  /** Runs the menu prompting + listening for user input */
  def menu():Unit = {
    printWelcome()
    var continueMenuLoop = true;

    // This loop here will repeatedly prompt, listen, run code, and repeat
    while(continueMenuLoop) {
      printOptions()
      // get user input with StdIn.readLine, read directly from StdIn
      StdIn.readLine() match {
        case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("file") => {
          try {
            FileUtil.getTextContent(arg)
              .getOrElse("No Content") //if None, wc No Content instead
              .replaceAll("\\p{Punct}", "") //remove all punctuation
              .split("\\s+") // split string into words
              .groupMapReduce(w => w)(w => 1)(_ + _) //
              .foreach { case (word, count) => println(s"$word: $count")}
          } catch {
            case fnf : FileNotFoundException => println(s"Failed to find file $arg")
          }
        }
        case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("exit") => continueMenuLoop = false
        case notRecognized => println(s"$notRecognized not a recognized command")
      }
    }
  }

}
