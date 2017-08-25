package com.herokuapp.blogdf.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
   private static Connection connection;
   
   private ConnectionFactory(){};
   
   public static Connection getConnection() throws SQLException {
    	if (connection == null) {
    		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
    		connection = DriverManager.getConnection(
    		          "jdbc:mysql://mysql796.umbler.com:41890/db_blogdesafio?useTimezone=true&serverTimezone=UTC", 
    		          "user_blogdesafio", "blogdesafio");
//        		
//        		connection = DriverManager.getConnection(
//      		          "jdbc:mysql://localhost:3307/db_blogdesafio?useTimezone=true&serverTimezone=UTC", 
//      		          "root", "usbw");  
    	}
    		
    	return connection;
        	

    }
}
