package com.revature.exception;

public class NoAccount extends Exception{

	public NoAccount() {}
	
	public void print() {
		System.out.println("An account with that username does not exist. Please try again.");
	}
	
}
