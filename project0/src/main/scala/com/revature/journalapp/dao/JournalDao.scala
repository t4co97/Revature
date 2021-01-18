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
        val stmt = use(conn.prepareStatement("SELECT * FROM project0.journal"))
        stmt.execute()
        val rs = use(stmt.getResultSet())
        // lets use an ArrayBuffer, we're adding one element at a time
        val allEntries: ArrayBuffer[Journal] = ArrayBuffer()
        while (rs.next()) {
            allEntries.addOne(Journal.fromResultSet(rs))
        }
        allEntries.toList
        }.get
    }
}