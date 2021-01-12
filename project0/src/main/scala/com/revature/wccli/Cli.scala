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

      def printWelcome(): Unit = {
         println("Welcome to your journal")
    }
    
      def printOptions(): Unit = {
          println("Previous Entries")
          println("Make New Entry")
          println("Edit Entry")
          println("Delete Entry")
          println("Exit")
      }

      def printDeleteOptions(): Unit = {
          println("Which entry would you like to delete?")
      }
      
      def printPreviousEntries(): Unit = {
        try{
          println(FileUtil.getPreviousEntries())
        } catch{
              case fnfe: FileNotFoundException => {
                println(s"Failed to find file: ${fnfe.getMessage}")
                println(s"""Found top level files:
                ${FileUtil.getTopLevelFiles.mkString(", ")}""")
              }
        }
      }

      def makeNewEntry(arg: String) = {
      
      }

      def editEntry() = {

      }

      def deleteEntry(){

      }
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
           
            case commandArgPattern(cmd, arg)if cmd.equalsIgnoreCase("p") => {
              printPreviousEntries()
            }
            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("make") => {

            }
            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("edit") => {

            }
            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("delete") => {
              
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
        println("Thank you for using your journal app, goodbye")
      }
        

      
  }