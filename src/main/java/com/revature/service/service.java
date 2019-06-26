package com.revature.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Receives calls from the controller class 
// Going to do it's job by using 'data access' class

// This is the class to test with JUnit 

public class service {
	
	private static Connection conn = null;

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		try {
			// Use util.Properties to grab DB login info
			Properties properties = new Properties();
			// Ensure we can always find file
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			properties.load(loader.getResourceAsStream("DBinfo.properties"));
			// Grab the info
			String url = properties.getProperty("url");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");
			// Now connect to the DB
			conn = DriverManager.getConnection(url, username, password);
			//System.out.println("-- Connected to DB :) --");
		} catch(IOException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
