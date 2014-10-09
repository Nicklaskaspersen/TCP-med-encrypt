package shared;

import java.io.Serializable;

public class Use implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private double amount;
	private boolean acceptedUse;
	private double balance;
	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public boolean getAcceptedUse() {
		return acceptedUse;
	}
	public void setAcceptedUse(boolean tooMuch) {
		this.acceptedUse = tooMuch;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
