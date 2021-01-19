package com.revature.journalapp.dao

import com.revature.journalapp.model.Journal
import com.revature.journalapp.utils.ConnectionUtil
import scala.util.Using
import scala.collection.mutable.ArrayBuffer
import java.sql.Date
import java.sql.PreparedStatement



/**
  * @return
  */
object JournalDao {
    def getAll(): Seq[Journal] = {
    val conn = ConnectionUtil.getConnection();
    Using.Manager { use =>
        val stmt = use(conn.prepareStatement("SELECT * FROM project0.journal ORDER BY entry_id;"))
        stmt.execute()
        val rs = use(stmt.getResultSet())
        val allEntries: ArrayBuffer[Journal] = ArrayBuffer()
        while (rs.next()) {
            allEntries.addOne(Journal.fromResultSet(rs))
        }
        allEntries.toList
        }.get
    }

    def getAllbyDate(): Seq[Journal] = {
    val conn = ConnectionUtil.getConnection();
    Using.Manager { use =>
        val stmt = use(conn.prepareStatement("SELECT * FROM project0.journal ORDER BY entry_date;"))
        stmt.execute()
        val rs = use(stmt.getResultSet())
        val allEntries: ArrayBuffer[Journal] = ArrayBuffer()
        while (rs.next()) {
            allEntries.addOne(Journal.fromResultSet(rs))
        }
        allEntries.toList
        }.get
    }

    def searchByID(entryID: Int): Seq[Journal] = {
    val conn = ConnectionUtil.getConnection();
    Using.Manager { use =>
        val stmt = use(conn.prepareStatement("SELECT * FROM project0.journal WHERE entry_id = ?;"))
        stmt.setInt(1, entryID)
        stmt.execute()
        val rs = use(stmt.getResultSet())
        val allEntries: ArrayBuffer[Journal] = ArrayBuffer()
        while (rs.next()) {
            allEntries.addOne(Journal.fromResultSet(rs))
        }
        allEntries.toList
        }.get
    }

    def searchByDate(entryDate: Date): Seq[Journal] = {
    val conn = ConnectionUtil.getConnection();
    Using.Manager { use =>
        val stmt = use(conn.prepareStatement("SELECT * FROM project0.journal WHERE entry_date = ?;"))
        stmt.setDate(1, entryDate)
        stmt.execute()
        val rs = use(stmt.getResultSet())
        val allEntries: ArrayBuffer[Journal] = ArrayBuffer()
        while (rs.next()) {
            allEntries.addOne(Journal.fromResultSet(rs))
        }
        allEntries.toList
        }.get
    }


    def addEntry(entry : Array[String]): Boolean = {
        val conn = ConnectionUtil.getConnection();
        Using.Manager { use =>
        val insertstmt = use(conn.prepareStatement("INSERT INTO project0.journal VALUES (DEFAULT, ?, ?);"))
        var entryDate: Date = null
        try{
        entryDate = java.sql.Date.valueOf(entry(0))
        insertstmt.setDate(1, entryDate)
        insertstmt.setString(2, entry(1))    
        insertstmt.execute()
        }catch{
            case e: Exception =>
            println("DATE NOT FORMATED CORRECTLY YYYY-MM-DD")
        }    
        insertstmt.getUpdateCount() > 0
        }.getOrElse(false)
    }
    
    def editEntry(entry : (Int, Int, String, String)): Boolean = {
        val conn = ConnectionUtil.getConnection();
        Using.Manager { use =>
        var stmt: PreparedStatement = use(conn.prepareStatement("UPDATE project0.journal SET entry_date = ?, entry = ? WHERE entry_id = ?;"))    
        var entryDate: Date = null
        try{
            entryDate = java.sql.Date.valueOf(entry._3)
        }catch{
            case e: Exception =>
            println("DATE NOT FORMATED CORRECTLY YYYY-MM-DD")
        }finally {
            if(entry._2 == 0){
                stmt = use(conn.prepareStatement("UPDATE project0.journal SET entry_date = ? WHERE entry_id = ?;"))
                stmt.setDate(1, entryDate)
                stmt.setInt(2, entry._1)
                stmt.execute()  
            }else if(entry._2 == 1){
                stmt = use(conn.prepareStatement("UPDATE project0.journal SET entry = ?  WHERE entry_id = ?;"))
                stmt.setString(1, entry._4)
                stmt.setInt(2, entry._1)    
                stmt.execute()
            }else{ 
                stmt.setDate(1, entryDate)
                stmt.setString(2, entry._4)
                stmt.setInt(3, entry._1)    
                stmt.execute()
            }
            
        }    
        stmt.getUpdateCount() > 0
        }.getOrElse(false)
    }


    

    def delteEntry(entry : Int): Boolean = {
        val conn = ConnectionUtil.getConnection();
        Using.Manager { use =>
        val stmt = use(conn.prepareStatement("DELETE FROM project0.journal WHERE entry_id = ?;"))
        stmt.setInt(1, entry)
        stmt.execute()
            
        stmt.getUpdateCount() > 0
        }.getOrElse(false)
    }
}