package com.revature.controller;

import java.util.Scanner;

import com.revature.model.BankAccount;
import com.revature.repository.BankDAOImpl;
import com.revature.repository.BankDAOInterface;
import com.revature.exception.NoAccount;
import com.revature.exception.OutOfMoney;

// Your connection between the console and the bank account 

// Switch statement for what to do "login", "logout", "view balance", "withdraw", "deposit", ---"view transactions" 

// Calling to service layer 

public class controller {
	
	private static String username;
	private static String password;
	static BankDAOInterface dao = new BankDAOImpl(); 
	static Scanner scan = new Scanner(System.in); // Only one Scanner needed for whole App 
	
	public static void menu() { // main menu of App 
		System.out.println("------------------------------------------\n");
		System.out.println("What would you like to do?");
		System.out.println("1. Login ");
		System.out.println("2. Close App ");
		
		switch(scan.nextLine()) { // use .nextLine() in case use enters letters 
		case "1": 
			Login(); // call Login() function if they want to login
			break;
		case "2":
			scan.close(); // close scanner because closing the App 
			System.out.println("Goodbye!");
			break;
		default:
			System.out.println("Not valid choice. Please try again. ");
			menu(); // re-calls the menu() if user enters invalid choice 
			break;
		}
	}
	
	public static void Login() {
		System.out.println("------------------------------------------\n");
		System.out.println("Please enter login information: ");
		System.out.println("Username: ");
		setUsername(scan.nextLine()); // grab username from user 
												
		BankAccount acct = new BankAccount(username); // one and only BankAccount object needed for App 
		dao.Login(acct); // Login(..) function grabs the rest of the users info 
						 // from the SQL DB
		try { // Check if user account exists 
			if(acct.getFirst().equals("")) { // if a password is not grabbed through DAO, the account doesn't exist 
				throw new NoAccount();  // throw Exception 
			}
		} catch(NoAccount e) {
			e.print();
			Login(); // retry Login() if username isn't found
			return; // for when order of events comes back, closes
		}
		
		int count = 0; // variable for the while-loop
		do {
			System.out.println("Password pls: ");
			setPassword(scan.next());
			if(password.equals(acct.getPass())) { // check scanner response against password grabbed from SQL
				System.out.println("Logging in...");
				menu2(acct); // if it matches, pass on obj and move on to next menu
				count = 1; // closes while loop
			} else {
				System.out.println("Incorrect password. Try again!");
			}
		} while(count == 0);
	}
	
	public static void menu2(BankAccount acct) {
		System.out.println("------------------------------------------\n");
		System.out.println("What would you like to do today " + acct.getFirst() + "?\n"); // Addresses user by first name :) 
		System.out.println("1. View accounts ");
		System.out.println("2. Make a transaction ");
		System.out.println("3. Exit App ");
		switch(scan.next()) {
		case "1": 
			dao.viewBal(acct);
			break;
		case "2":
			System.out.println("------------------------------------------\n");
			String withOrDep = "";
			System.out.println("What would you like to to?");
			System.out.println("1. Withdraw"); // decide what type of transaction
			System.out.println("2. Deposit");  // mostly determines if we'll add/subtract from account 
			switch(scan.next()) {
			case "1":
				withOrDep = "withdraw";
				break;
			case "2":
				withOrDep = "deposit";
				break;
			default: // for inappropriate response 
				System.out.println("Try again!");
				menu2(acct); // recall function we're in and start over 
				break;
			}
			System.out.println("------------------------------------------\n");
			String account = "" ; // variable for which column in table to pull from 
			if(withOrDep.equals("withdraw")) {System.out.println("From which account?");} // just different grammar 
			else if(withOrDep.equals("deposit") ) {System.out.println("To which account?");} // different grammar 
			System.out.println("1. Checking");
			System.out.println("2. Savings");
			switch(scan.next()) {
			case "1":
				account = "CHECKING";
				break;
			case "2":
				account = "SAVING";
				break;
			default:
				System.out.println("Try again!");
				menu2(acct); // recall menu2 from the beginning for inappropriate response 
				break;
			}
			System.out.println("------------------------------------------\n");
			double amount = 0; // initialize int that will be deposit/withdraw amount 
			if(withOrDep.equals("withdraw")) {
				boolean check = false; // boolean for while-loop
				while(!check) { // while-loop to stay in this section if they withdraw too much
					try {
						System.out.println("How much would you like to withdraw?");
						amount = scan.nextDouble();
						if(account.equals("CHECKING")) {
							if(amount > acct.getChecking()) { // check if 'amount' is more money than they have
								throw new OutOfMoney();
							}
							if(amount <= acct.getChecking()) { check = true; }
						}
						if(account.equals("SAVING")) {
							if(amount > acct.getSaving()) {
								throw new OutOfMoney();
							}
							if(amount <= acct.getSaving()) { check = true; }
						}
					} catch(OutOfMoney e) {
						System.out.println("You don't have that much money!");
						check = false;
					}
				}
			}
			else if(withOrDep.equals("deposit")) {
				System.out.println("How much would you like to deposit?");
				amount = scan.nextDouble();
			}
			Double newBal = (double) 0;
			if(withOrDep.equals("withdraw") && account.equals("CHECKING")) {
				newBal = acct.getChecking() - amount;
				acct.setChecking(newBal);
			}
			else if(withOrDep.equals("deposit") && account.equals("CHECKING")) {
				newBal = acct.getChecking() + amount;
				acct.setChecking(newBal);
			}
			else if(withOrDep.equals("withdraw") && account.equals("SAVING")) {
				newBal = acct.getSaving() - amount;
				acct.setSaving(newBal);
			}
			else if(withOrDep.equals("deposit") && account.equals("SAVING")) {
				newBal = acct.getSaving() + amount;
				acct.setSaving(newBal);
			}
			String query = "UPDATE ACCOUNTINFO SET " + account + "=" + newBal + " WHERE ID = " + acct.getId();
			dao.adjust(acct, query);;
			break;
		case "3":
			scan.close();
			System.out.println("Have a nice day!");
			break;
		default:
			System.out.println("Not an option!");
			menu2(acct);
			break;
		}
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		controller.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		controller.password = password;
	}
}
