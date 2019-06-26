package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import com.revature.controller.controller;
import com.revature.model.BankAccount;
import com.revature.service.CloseStreams;
import com.revature.service.service;

public class BankDAOImpl implements BankDAOInterface {
	
	DecimalFormat deci = new DecimalFormat("#.00");

	@Override
	public void Login(BankAccount acct) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PreparedStatement stmt2 = null;
		ResultSet rs2 = null;
		Connection conn = service.getConnection();
		try {
			stmt = conn.prepareStatement("SELECT * FROM USERINFO");
			stmt.execute();
			rs = stmt.getResultSet();
			while(rs.next()) {
				if(rs.getString("USERNAME").equals(acct.getUsername())) {
					acct.setPass(rs.getString("PASS"));
					acct.setFirst(rs.getString("FIRSTNAME"));
					acct.setId(rs.getInt("ID"));
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			CloseStreams.close(stmt);
			CloseStreams.close(rs);
		}
		try {
			stmt2 = conn.prepareStatement("SELECT * FROM ACCOUNTINFO WHERE ID = ?");
			stmt2.setInt(1, acct.getId());
			stmt2.execute();
			rs2 = stmt2.getResultSet();
			if(rs2.next()) {
				acct.setChecking(rs2.getDouble("checking"));
				acct.setSaving(rs2.getDouble("saving"));
			} 
			//else {System.out.println("Did not get get account amounts.");}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			CloseStreams.close(stmt2);
			CloseStreams.close(rs2);
		}
		CloseStreams.close(conn);
	}

	@Override
	public void adjust(BankAccount acct, String query) {
		PreparedStatement stmt = null;
		try (Connection conn = service.getConnection()) {
			stmt = conn.prepareStatement(query);
			stmt.execute();
			System.out.println("Transaction complete!!");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			CloseStreams.close(stmt);
		}
		System.out.println("------------------------------------------");
		viewBal(acct); 
	}

	@Override
	public void viewBal(BankAccount acct) {
		System.out.println("------------------------------------------\n");
		System.out.println("Account totals : ");
		System.out.println("Checking:  $" + deci.format(acct.getChecking()));
		System.out.println("Savings:  $" + deci.format(acct.getSaving()) + "\n");
		System.out.println("------------------------------------------");
		controller.menu2(acct); 
	}
}
