package com.revature.journalapp

import scala.io.BufferedSource
import scala.io.Source
import java.io.File
import scala.util.matching.Regex

  object FileUtil {

    /**
      * @param filename
      * @param sep 
      * @return
      */
    def getTextContent(filename: String, sep: String = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"): Array[String] = {
        var openedFile : BufferedSource = null 
        var data: Array[String] = null
        try{
            openedFile = Source.fromFile(filename)
            for(line <- openedFile.getLines()){
              data = line.split(sep).map(_.trim) 
            }
        } finally{
            if (openedFile != null) openedFile.close()
        }
        
        data
    }
    
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