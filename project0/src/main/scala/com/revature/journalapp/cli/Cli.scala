package com.revature.journalapp

import scala.io.StdIn
import scala.util.matching.Regex
import java.io.FileNotFoundException
import com.revature.journalapp.utils
import com.revature.journalapp.dao.JournalDao
import com.revature.journalapp.model.Journal
import java.io.File

  class Cli {
    

    val commandArgPattern : Regex = "(\\w+)\\s*(.*)".r


      def printWelcome(): Unit = {
         println("Welcome to your journal")
    }
    
      def printOptions(): Unit = {
          println("Previous Entries: p")
          println("Make New Entry: n")
          println("Edit Entry: e")
          println("Delete Entry: d")
          println("Exit")
      }
      
      def printPreviousEntries(): Unit = {
        JournalDao.getAll().foreach(println)
      }
      def makeNewEntry(): Unit  = {
        println("Choose File to Upload")
        val filename = StdIn.readLine();
        if (new java.io.File(filename).isFile()){
          
        }
        else{
          println("File does not exist")
          println(s"""Found top level files:
              ${FileUtil.getTopLevelFiles.mkString(", ")}""")
        }
      }

      def editEntry() = {

      }
      
      def deleteEntry() = {

      }

      def menu() : Unit = {
        printWelcome()
        var continueMenuLoop = true
        while (continueMenuLoop) {
        
          printOptions()
        
          val input = StdIn.readLine()
          
          input match{
           
            case commandArgPattern(cmd, arg)if cmd.equalsIgnoreCase("p") => {
              printPreviousEntries()
            }
            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("n") => {
              makeNewEntry()
            }
            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("e") => {
              editEntry()
            }
            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("d") => {
              deleteEntry()
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