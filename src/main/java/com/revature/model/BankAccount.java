package com.revature.model;

//Class instances will exist here

public class BankAccount {
	
	private String username = "";
	private String First = "";
	private String Pass = "";
	private int id = 0; 
	private double Checking = 0;
	private double Saving = 0;
	
	public BankAccount(String username) {
		super();
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "BankAccount [username=" + username + ", First=" + First + ", Pass=" + Pass + ", id=" + id
				+ ", Checking=" + Checking + ", Saving=" + Saving + "]";
	}

	public String getFirst() {
		return First;
	}
	public void setFirst(String First) {
		this.First = First;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPass() {
		return Pass;
	}
	public void setPass(String pass) {
		this.Pass = pass;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getChecking() {
		return Checking;
	}
	public void setChecking(double checking) {
		this.Checking = checking;
	}
	public double getSaving() {
		return Saving;
	}
	public void setSaving(double saving) {
		this.Saving = saving;
	}
}
