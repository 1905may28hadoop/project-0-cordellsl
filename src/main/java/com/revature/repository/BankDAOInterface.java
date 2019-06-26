package com.revature.repository;

import com.revature.model.BankAccount;

public interface BankDAOInterface {
	
	// Login
	// View balance
	// Deposit 
	// Withdraw
	// -- View transaction history
	
	void Login(BankAccount acct);
	
	void adjust(BankAccount acct, String query);
	
	void viewBal(BankAccount acct);

}
