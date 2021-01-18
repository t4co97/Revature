package com.revature.journalapp.model

import java.sql.ResultSet

case class Journal(entryId : Int, entryDate : String, entry : String ){

}

object Journal {
    /**
      * 
      *
      * @param rs
      * @return
      */

    def fromResultSet(rs : ResultSet) : Journal = {
        apply(
            rs.getInt("entry_id"),
            rs.getString("entry_date"),
            rs.getString("entry")
        )
    }
}