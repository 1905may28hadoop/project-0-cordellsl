package com.revature.controller;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.Test;
import com.revature.service.CloseStreams;
import com.revature.service.service;

public class ControllerTest {
	
	private static Logger log = Logger.getLogger(controller.class);
	
	// Tests reading SAVING value from a test account
	// Initialize 'test2' to '1', then change it to the ResultSet retrieved
	// This should equal 0 
	@Test
	public void DatabaseReading() {
		log.debug("Testing DataBase properly reads value of money in an account.");
		int test2 = 1; 
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		try (Connection conn = service.getConnection()) {
			stmt2 = conn.prepareStatement("SELECT SAVING FROM ACCOUNTINFO WHERE id = 66");
			stmt2.execute();
			rs = stmt2.getResultSet();
			while(rs.next()) {
				test2 = rs.getInt("SAVING");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			CloseStreams.close(stmt2);
			CloseStreams.close(rs);
		}
		assertEquals(0, test2);
	}
	
	
	@Test
	public void AccountExists() {
		log.debug("Checking that a users username can be found in the DataBase");
		boolean test = false;
		String account = "BigMoney";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try(Connection conn = service.getConnection()) {
			stmt = conn.prepareStatement("SELECT USERNAME FROM USERINFO");
			stmt.execute();
			rs = stmt.getResultSet();
			while(rs.next()) {
				if(rs.getString("USERNAME").equals(account)) {
					test = true;
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			CloseStreams.close(rs);
			CloseStreams.close(stmt);
		}
		assertTrue(test);
	}
	

}
