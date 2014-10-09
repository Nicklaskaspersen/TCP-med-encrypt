package shared;

import java.io.Serializable;

public class Login implements Serializable {
	
	private String username;
	private String pin;
	private boolean userAuthenticated;
	private double balance;
	
	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public boolean isUserAuthenticated() {
		return userAuthenticated;
	}
	public void setUserAuthenticated(boolean userAuthenticated) {
		this.userAuthenticated = userAuthenticated;
	}
	
	public String toString(){
		return "\nusername: " + username + "\npass: " + pin;
	}
	
	

}
