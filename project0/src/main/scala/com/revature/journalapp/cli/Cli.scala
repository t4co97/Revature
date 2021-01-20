package com.revature.journalapp

import scala.io.StdIn
import scala.util.matching.Regex
import java.io.FileNotFoundException
import com.revature.journalapp.utils
import com.revature.journalapp.dao.JournalDao
import com.revature.journalapp.model.Journal
import java.io.File
import java.sql.Date

  class Cli {
    var journal: Seq[Journal] = null
    var entryId = 0
    var eCaught = false

    val commandArgPattern : Regex = "(\\w+)\\s*(.*)".r


    def printWelcome(): Unit = {
        println()
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
    
    def printPreviousEntries(orderedBy: Int): Unit = {
      journal = JournalDao.getAll(orderedBy)
      for(i <- 0 to (journal.length-1)){
        println()
        println(s"Entry ID: ${journal(i).entryId}")
        println(s"Entry Date: ${journal(i).entryDate}")
        println(s"Entry: ${journal(i).entry}")
        if(i == (journal.length-1)){
          println()
        }
      }
    }

    def searchID(): Unit = {
      println()
      println("Please Type ID Number")
      eCaught = false
      try{
        entryId = StdIn.readLine().toInt
      }catch{
        case n: NumberFormatException =>
        println()
        println("PLEASE USE ID NUMBER WHEN SEARCHING")
        println()
        eCaught = true
      }finally{
        if(eCaught){
          return
        }
        journal = JournalDao.search(entryId)
        if(journal.isEmpty){
          println()
          println("NO ENTRY WITH THAT ID")
          println()
          return
        }
        for(i <- 0 to (journal.length-1)){
          println()
          println(s"Entry ID: ${journal(i).entryId}")
          println(s"Entry Date: ${journal(i).entryDate}")
          println(s"Entry: ${journal(i).entry}")
          println()  
        }
      }
    }

    def searchDate(): Unit = {
      var entryDate:Date = null
      eCaught = false
      println()
      println("Please Type Date: Format(YYYY-MM-DD)")
      try{
        entryDate = java.sql.Date.valueOf(StdIn.readLine())
      }catch{
        case e: Exception =>
        println()
        println("PLEASE USE CORRECT DATE FORMAT")
        println()
        eCaught = true
      }finally{
        if(eCaught){
          return
        }
        journal = JournalDao.search(entryDate)
        if(journal.isEmpty){
          println()
          println("NO ENTRY WITH THAT DATE")
          println()
          return
        }
        for(i <- 0 to (journal.length-1)){
          println()
          println(s"Entry ID: ${journal(i).entryId}")
          println(s"Entry Date: ${journal(i).entryDate}")
          println(s"Entry: ${journal(i).entry}")
          if(i == (journal.length-1)){
          println()
          }  
        }
      }
    }
    
    def makeNewEntry(): Unit  = {
      println()
      println("Choose CSV File to Upload: Format(date,entry)")

      val filename = StdIn.readLine();

      if (new java.io.File(filename).isFile()){
        if(!filename.contains(".csv")){
          println()
          println("FILE NOT A CSV")
          println()
          return
        }
        if(JournalDao.addEntry(FileUtil.getTextContent(filename))){
          println()
          println("Added Entry")
          println()
        }else{
          println()
          println("Failed to Add Entry!")
          println()
        }
      }
      else{
        println()
        println("FILE DOES NOT EXIST")
        println(s"""Found Top Level Files:
            ${FileUtil.getTopLevelFiles.mkString(", ")}""")
        println()
      }
    }

    def editEntry(): Unit ={
      printPreviousEntries(0)
      println("Choose Entry to Edit: USE ENTRY ID TO CHOOSE")
      var entryData: (Int, Int, String, String) = null
      var entryEdit: Int = 0
      eCaught = false
      try{
        entryId = StdIn.readLine().toInt
      }catch{
        case n: NumberFormatException =>
        println()
        println("PLEASE USE ID NUMBER WHEN PICKING")
        println()
        eCaught = true
      }finally{
        if(eCaught){
          return
        }
        var loop: Boolean = true
        
        while(loop){
          println()
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
            case commandArgPattern(cmd, arg) => {
            println()
            println(s"""Failed to parse command: $cmd""")
            }  
            case _ => {
              println()
              println("Failed to parse any input")
              println()
            }
          
          }
        }
        println("Choose File to Replace Current Entry")
              val filename = StdIn.readLine();
              if (new java.io.File(filename).isFile()){
                if(!filename.contains(".csv")){
                  println()
                  println("FILE NOT A CSV")
                  println()
                  return
                }
                var data = FileUtil.getTextContent(filename)
                entryData = (entryId, entryEdit, data(0), data(1))
                if(JournalDao.editEntry(entryData)){
                  println()
                  println("Updated Entry")
                  println()
                }else{
                  println()
                  println("Failed to Update Entry!")
                  println()
                }
              }
              else{
                println()
                println("FILE DOES NOT EXIST")
                println(s"""Found top level files:
                    ${FileUtil.getTopLevelFiles.mkString(", ")}""")
              }        
        }   
    }
    
    def deleteEntry():Unit = {
      printPreviousEntries(0)
      println("Choose Entry to Delete: Use entry id to choose")
      eCaught = false
      try{
        entryId = StdIn.readLine().toInt
      }catch{
        case n: NumberFormatException =>
        println()
        println("PLEASE USE ID NUMBER WHEN PICKING")
        println()
        eCaught =true
      }finally{
        if(eCaught){
          return
        }
        println()
        println(s"ARE YOU SURE YOU WANT TO DELETE ENTRY $entryId y or n")
        if(StdIn.readLine.equalsIgnoreCase("y")){
          if(JournalDao.delteEntry(entryId)){
            println()
            println("Successfully Deleted")
            println()
          }else{
            println()
            println("Failed to Delete")
            println()
          } 
        }else {
          println()
          println("Deletion Stopped")
          println()
          return
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
            printPreviousEntries(0)
          }
          case commandArgPattern(cmd, arg)if cmd.equalsIgnoreCase("o") => {
            printPreviousEntries(1)
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
            println()
            println(s"""Failed to parse command: $cmd"""")
            println()
          }
          case _ => {
            println()
            println("Failed to parse any input")
            println()
          }
        }
      }
      println("Thank you for using your journal app, goodbye")
    }
  }