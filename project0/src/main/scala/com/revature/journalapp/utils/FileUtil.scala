package com.revature.journalapp

import scala.io.BufferedSource
import scala.io.Source
import java.io.File


/**
  * Provides basic file functionality to other parts of our application
  */
  object FileUtil {

    /**
      * returns the text content of a file or the empty string
      * 
      * well introduce a tool in scala thats good for dealing with content that may or may not be successfully retrieved
      * 
      * @param filename
      * @param sep the seperator used between lines in returned text content of the file
      * @return the text content of a file, or the empty string
      */
    def getTextContent(filename: String, sep: String = " "): String = {
        //we open files using source.fromfile, this will get us  a BufferedSource
        //Whenever we open files we want ot be sure to close them
        //When dealing with files we may run into exceptions so we use a try finally to close them
        var openedFile : BufferedSource = null // declare our openedfile var
        try{
            openedFile = Source.fromFile(filename)
            //Scala returns the last line of this try block, since there is nothing below the try-finally
            openedFile.getLines().mkString(sep)
        } finally{
            //if the file was opened, close it
            if (openedFile != null) openedFile.close()
        }
    }

    def getPreviousEntries(sep: String = " "): String = {
        var openedFile : BufferedSource = null // declare our openedfile var
        try{
            openedFile = Source.fromFile("entryOne.csv")
            //Scala returns the last line of this try block, since there is nothing below the try-finally
            openedFile.getLines().mkString(sep)
        } finally{
            //if the file was opened, close it
            if (openedFile != null) openedFile.close()
        }
    }
    
    /**
      * 
      * @return
      *
      * @return
      */
    def getTopLevelFiles(): Array[String] = {
        val currentDir = new File(".")
        //were going to filter the list of java.io.files, only including files that are files, not directories
        // this will leave us with an array of java.io.files
        //we then map that arrray, turning each java.io.file into a string
        currentDir.listFiles()
            .filter((f: File) => {f.isFile()})
            .map((f: File) => {f.getName()})

            //shorthand version of the above
            // currentDir.listFiles().filter(_.isFile()).map(_.getName())
    }
  }