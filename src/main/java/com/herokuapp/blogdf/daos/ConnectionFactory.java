package com.herokuapp.blogdf.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
   private static Connection connection;
   
   private ConnectionFactory(){};
   
   public static Connection getConnection() {
        try {
        	if (connection == null) {
        		try {
        		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        		} catch (SQLException e2) {
        			throw new RuntimeException(e2);
        		}
        		connection = DriverManager.getConnection(
        		          "jdbc:mysql://mysql796.umbler.com:41890/db_blogdesafio?useTimezone=true&serverTimezone=UTC", 
        		          "user_blogdesafio", "blogdesafio");   	
        	}
        		
        	return connection;
        	
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
