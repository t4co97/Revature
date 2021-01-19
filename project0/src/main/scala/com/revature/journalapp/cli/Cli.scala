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
        val journal = JournalDao.getAll()
        for(i <- 0 to (journal.length-1)){
        println(s"Entry ID: ${journal(i).entryId}")
        println(s"Entry Date: ${journal(i).entryDate}")
        println(s"Entry: ${journal(i).entry}")
        println()  
        }
       
        
       
        
      }
      
      def makeNewEntry(): Unit  = {
        println("Choose File to Upload")

        val filename = StdIn.readLine();

        if (new java.io.File(filename).isFile()){
          if(JournalDao.addEntry(FileUtil.getTextContent(filename))){
            println("Added Entry")
          }else{
            println("Failed to Add Entry!")
          }
        }
        else{
          println("FILE DOES NOT EXIST")
          println(s"""Found top level files:
              ${FileUtil.getTopLevelFiles.mkString(", ")}""")
        }
      }

      def editEntry(): Unit ={
        JournalDao.getAll().foreach(println)
        println("Choose Entry to Edit: Use entry id to choose")
        val entryId = StdIn.readLine()
        println("Change Date, Entry, or Both?")
        
        val input = StdIn.readLine()
        input match{
            case commandArgPattern(cmd, arg)if cmd.equalsIgnoreCase("date") => {
              editDate()
            }
            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("entry") => {
              editOnlyEntry()
            }
            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("both") => {
              editEverything()
            }
          }
      }
      
      def editDate(): Unit = {

      }

      def editOnlyEntry(): Unit = {

      }

      def editEverything(): Unit = {
        println("Choose File to Replace Current Entry")

        val filename = StdIn.readLine();

        if (new java.io.File(filename).isFile()){
          if(JournalDao.addEntry(FileUtil.getTextContent(filename))){
            println("Added Entry")
          }else{
            println("Failed to Add Entry!")
          }
        }
        else{
          println("FILE DOES NOT EXIST")
          println(s"""Found top level files:
              ${FileUtil.getTopLevelFiles.mkString(", ")}""")
        }
      }

      def deleteEntry():Unit = {
        printPreviousEntries()
        println("Choose Entry to Delete: Use entry id to choose")
        var entryId: Int = 0
        try{
          entryId = StdIn.readLine().toInt
          println(s"ARE YOU SURE YOU WANT TO DELETE ENTRY $entryId y or n")
        if(StdIn.readLine.equalsIgnoreCase("y")){
          if(JournalDao.delteEntry(entryId)){
            println("Successfully Deleted")
          }else{
            println("Failed to Delete")
          } 
        }else {
        
        }
        }catch{
          case n: NumberFormatException =>
          println("PLEASE USE ID NUMBER WHEN PICKING")
        }    
        
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