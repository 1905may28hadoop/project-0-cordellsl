package com.revature.service;

//import java.io.FileInputStream;
//import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Class for making sure connection to DB is closed when done
public class CloseStreams {
	
	public static void close(Connection resource) {
		if(resource != null) {
			try {
				resource.close();
				//System.out.println("DB closed for business!");
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(ResultSet resource) {
		if(resource != null) {
			try {
				resource.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(Statement resource) {
		if(resource != null) {
			try {
				resource.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
