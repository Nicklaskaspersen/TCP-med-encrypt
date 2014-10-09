package server;

public class Account {
	
	private String username;
	private String pin;
	private double balance;
	
	
	public Account(String username, String pin, double balance){
		this.username = username;
		this.pin = pin;
		this.balance = balance;
	}


	 public boolean validatePass( String userPass )
	   {
	      if ( userPass.equals(pin))
	         return true;
	      else
	         return false;
	   }
	
	
	public String getUsername() {
		return username;
	}


	public String getPin() {
		return pin;
	}


	public double getBalance() {
		return balance;
	}
	
	public void credit(double amount){
		balance += amount;
	}
	
	public void debit (double amount){
		balance -= amount;
	}
	
	

}
