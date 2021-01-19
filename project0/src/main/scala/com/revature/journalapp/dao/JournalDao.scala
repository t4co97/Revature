package com.revature.journalapp.dao

import com.revature.journalapp.model.Journal
import com.revature.journalapp.utils.ConnectionUtil
import scala.util.Using
import scala.collection.mutable.ArrayBuffer



/**
  * @return
  */
object JournalDao {
    def getAll(): Seq[Journal] = {
    val conn = ConnectionUtil.getConnection();
    Using.Manager { use =>
        val stmt = use(conn.prepareStatement("SELECT * FROM project0.journal;"))
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
        insertstmt.setDate(1, java.sql.Date.valueOf(entry(0)))
        insertstmt.setString(2, entry(1))    
        insertstmt.execute()
            
        insertstmt.getUpdateCount() > 0
        }.getOrElse(false)
    }
    
    def editEntry(entry : Array[String]): Boolean = {
        val conn = ConnectionUtil.getConnection();
        Using.Manager { use =>
        val stmt = use(conn.prepareStatement("UPDATE project0.journal SET entry_date = ?, entry = ? WHERE entry_id = ?;"))
        stmt.setString(1, entry(0))
        stmt.setString(2, entry(1))  
        stmt.setString(2, entry(1))   
        stmt.execute()
            
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