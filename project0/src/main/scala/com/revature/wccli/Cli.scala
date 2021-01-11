package com.revature.wccli

import scala.io.StdIn
import scala.util.matching.Regex
import java.io.FileNotFoundException

  class Cli {
    
    /**
    * commandArgPattern is a regular expression (regex) that will help us
    * extract the command and argument from user input on the command line
    * 
    * Regex is a tool used for pattern matching strings.  Lots of languages and other tools
    * support regex.  It's good to learn at least the basic, but you can also just use this
    * code for your project if you like.
    */
    val commandArgPattern : Regex = "(\\w+)\\s*(.*)".r

    /**
      * Prints the commands avaible to the user
      */
      def printOptions(): Unit = {
          println("Commands available:")
          println("exit: exits word count uli ")
          println("echo [string to repeat] : repeats a string back to the user")
      }
      /**
        * this runs the menu, this is the entrypoint to the cli class
        * 
        * the menu will interact with the user on a loop and call other methods classes in order to achieve the result of hte users commands
        */
      def menu() : Unit = {
        printWelcome()
        var continueMenuLoop = true
        while (continueMenuLoop) {

        
          printOptions()
        
          // take user input using stdin.readline
          //read line is blocking which means that it pauses program execution while it waits for input this is fine for us be we do want to take note
          val input = StdIn.readLine()
          // Heres an example using our regex above, feel free to just follow along with similar commands and args
          input match{
           
            case commandArgPattern(cmd, arg)if cmd.equalsIgnoreCase("echo") => {
              println(arg)
            }
            case commandArgPattern(cmd, arg)if cmd.equalsIgnoreCase("exit") => {
              continueMenuLoop = false
            }
           
            case commandArgPattern(cmd, arg) => {
              println(s"""Failed to parse command: "$cmd" with arguments: "$arg"""")
            }
            case _ => {
              println("Failed to parse any input")
            }
          }
        }
        println("Thank you for using word count cli, goodbye")
      }
        

      
  }