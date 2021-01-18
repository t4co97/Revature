package com.revature.journalapp.utils

import java.sql.Connection
import java.sql.DriverManager

object ConnectionUtil {

  var conn: Connection = null;

  /** 
    * @return Connection
    */
  def getConnection(): Connection = {

    if (conn == null || conn.isClosed()) {
      classOf[org.postgresql.Driver].newInstance() 

      conn = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5000/tyler",
        "tyler",
        "nightmare"
      )
    }
    
    conn
  }

}