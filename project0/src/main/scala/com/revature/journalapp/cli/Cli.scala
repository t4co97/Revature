package com.revature.journalapp

import scala.io.StdIn
import scala.util.matching.Regex
import java.io.FileNotFoundException
import com.revature.journalapp.utils
import com.revature.journalapp.dao.JournalDao

  class Cli {
    

    val commandArgPattern : Regex = "(\\w+)\\s*(.*)".r


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
      
      def printPreviousEntries(): Unit = {
        JournalDao.getAll().foreach(println)
      }
      def makeNewEntry(arg: String) = {
      
      }

      def editEntry() = {

      }

      def menu() : Unit = {
        printWelcome()
        var continueMenuLoop = true
        while (continueMenuLoop) {
        
          printOptions()
        
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