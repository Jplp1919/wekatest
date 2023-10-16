package com.mycompany.wekatest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;


public class ConnectionFactory {

   private final String DATABASE_URL = "jdbc:mysql://localhost:3306/newmodeldb?autoReconnect=true&useSSL=false";
    static final String USERNAME = "root";
    static final String PASSWORD = "root";
     public ConnectionFactory(){
      

     }
    public Connection establishConnection()  {
       try {
           return DriverManager.getConnection(DATABASE_URL,
                   USERNAME, PASSWORD);
       } catch (SQLException ex) {
           Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
       }
       
      return null;
    }
}
