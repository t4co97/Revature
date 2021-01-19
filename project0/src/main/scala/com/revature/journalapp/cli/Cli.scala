package com.revature.journalapp

import scala.io.StdIn
import scala.util.matching.Regex
import java.io.FileNotFoundException
import com.revature.journalapp.utils
import com.revature.journalapp.dao.JournalDao
import com.revature.journalapp.model.Journal
import java.io.File
import scala.collection.mutable.ArrayBuffer
import java.sql.Date

  class Cli {
    

    val commandArgPattern : Regex = "(\\w+)\\s*(.*)".r


      def printWelcome(): Unit = {
         println("Welcome to Your Journal")
    }
    
      def printOptions(): Unit = {
          println("Previous Entries: p")
          println("Previous Entries Ordered by Date: o")
          println("Search by ID: s")
          println("Search by Date: sd")
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

      def entriesByDate(): Unit = {
        val journal = JournalDao.getAllbyDate()
        for(i <- 0 to (journal.length-1)){
        println(s"Entry ID: ${journal(i).entryId}")
        println(s"Entry Date: ${journal(i).entryDate}")
        println(s"Entry: ${journal(i).entry}")
        println()  
        }
      }

      def searchID(): Unit = {
        var entryId = 0
        var eCaught = false

        println("Please Type ID Number")
         try{
          entryId = StdIn.readLine().toInt
        }catch{
          case n: NumberFormatException =>
          println("PLEASE USE ID NUMBER WHEN SEARCHING")
          eCaught = true
        }finally{
          if(eCaught){
            return
          }
          val journal = JournalDao.searchByID(entryId)
          if(journal.isEmpty){
            println("NO ENTRY WITH THAT ID")
            return
          }
          for(i <- 0 to (journal.length-1)){
          println(s"Entry ID: ${journal(i).entryId}")
          println(s"Entry Date: ${journal(i).entryDate}")
          println(s"Entry: ${journal(i).entry}")
          println()  
          }
        }
      }

      def searchDate(): Unit = {
        var entryDate: Date = null
        var eCaught = false

        println("Please Type Date: Format(YYYY-MM-DD)")
         try{
          entryDate = java.sql.Date.valueOf(StdIn.readLine())
        }catch{
          case e: Exception =>
          println("PLEASE USE CORRECT DATE FORMAT")
          eCaught = true
        }finally{
          if(eCaught){
            return
          }
          val journal = JournalDao.searchByDate(entryDate)
          if(journal.isEmpty){
            println("NO ENTRY WITH THAT DATE")
            return
          }
          for(i <- 0 to (journal.length-1)){
          println(s"Entry ID: ${journal(i).entryId}")
          println(s"Entry Date: ${journal(i).entryDate}")
          println(s"Entry: ${journal(i).entry}")
          println()  
          }
        }
      }
      
      def makeNewEntry(): Unit  = {
        println("Choose CSV File to Upload: Format(date,entry)")

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
          println(s"""Found Top Level Files:
              ${FileUtil.getTopLevelFiles.mkString(", ")}""")
        }
      }

      def editEntry(): Unit ={
        printPreviousEntries()
        println("Choose Entry to Edit: USE ENTRY ID TO CHOOSE")
        var entryData: (Int, Int, String, String) = null
        var eCaught: Boolean = false
        var entryId:Int = 0
        var entryEdit: Int = 0
        try{
          entryId = StdIn.readLine().toInt
        }catch{
          case n: NumberFormatException =>
          println("PLEASE USE ID NUMBER WHEN PICKING")
          eCaught = true
        }finally{
          if(eCaught){
            return
          }
          var loop: Boolean = true
          
          while(loop){
          println("Change Date, Entry, or Both? or Exit")

          
          val input = StdIn.readLine()
          input match{
            case commandArgPattern(cmd, arg)if cmd.equalsIgnoreCase("date") => {
              entryEdit = 0
              loop = false
            }
            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("entry") => {
              entryEdit = 1
              loop = false
            }
            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("both") => {
              entryEdit = 2
              loop = false
            }
            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("exit") => {
              return
            }  
            case _ => {
              println("Failed to parse any input")
            }
         
            }
          }
          
          println("Choose File to Replace Current Entry")

                val filename = StdIn.readLine();

                if (new java.io.File(filename).isFile()){
                  var data = FileUtil.getTextContent(filename)
                  entryData = (entryId, entryEdit, data(0), data(1))
                  if(JournalDao.editEntry(entryData)){
                    println("Updated Entry")
                  }else{
                    println("Failed to Update Entry!")
                  }
                }
                else{
                  println("FILE DOES NOT EXIST")
                  println(s"""Found top level files:
                      ${FileUtil.getTopLevelFiles.mkString(", ")}""")
                }
              

          
          }
        
      }
      
     

      def deleteEntry():Unit = {
        var entryId: Int = 0
        var eCaught: Boolean = false

        printPreviousEntries()
        println("Choose Entry to Delete: Use entry id to choose")

        try{
          entryId = StdIn.readLine().toInt
        }catch{
          case n: NumberFormatException =>
          println("PLEASE USE ID NUMBER WHEN PICKING")
          eCaught =true
        }finally{
          if(eCaught){
            return
          }
          println(s"ARE YOU SURE YOU WANT TO DELETE ENTRY $entryId y or n")
          if(StdIn.readLine.equalsIgnoreCase("y")){
            if(JournalDao.delteEntry(entryId)){
              println("Successfully Deleted")
            }else{
              println("Failed to Delete")
            } 
          }else {
          
          }
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
            case commandArgPattern(cmd, arg)if cmd.equalsIgnoreCase("o") => {
              entriesByDate()
            }
            case commandArgPattern(cmd, arg)if cmd.equalsIgnoreCase("s") => {
              searchID()
            }
            case commandArgPattern(cmd, arg)if cmd.equalsIgnoreCase("sd") => {
              searchDate()
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