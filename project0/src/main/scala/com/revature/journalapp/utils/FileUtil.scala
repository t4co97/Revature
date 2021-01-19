package com.revature.journalapp

import scala.io.BufferedSource
import scala.io.Source
import java.io.File
import scala.util.matching.Regex

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
    def getTextContent(filename: String, sep: String = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"): Array[String] = {
        //we open files using source.fromfile, this will get us  a BufferedSource
        //Whenever we open files we want ot be sure to close them
        //When dealing with files we may run into exceptions so we use a try finally to close them
        var openedFile : BufferedSource = null // declare our openedfile var
        var data: Array[String] = null
        try{
            openedFile = Source.fromFile(filename)
            //Scala returns the last line of this try block, since there is nothing below the try-finally
            for(line <- openedFile.getLines()){
              data = line.split(sep).map(_.trim) 
            }
        } finally{
            //if the file was opened, close it
            if (openedFile != null) openedFile.close()
        }
        
        data
    }

    //val csv = io.Source.fromFile(filename)
        //  for(line <- csv.getLines()){
        //    val cols = line.split(",").map(_.trim)
      //      cols(0)
      //    }
       //   csv.close()
    
    /**
      * 
      * @return
      *
      * @return
      */
    def getTopLevelFiles(): Array[String] = {
        val currentDir = new File(".")
        currentDir.listFiles().filter(_.isFile()).map(_.getName())
    }
  }