package server;

import java.util.ArrayList;

public class Database {

	private ArrayList<Account> accountlist;
	
	public Database()
	   {
		   accountlist = new ArrayList<Account>();
			Account acc1 = new Account( "nick", "Nicklas12", 10);
			accountlist.add(acc1);
			Account acc2 = new Account( "miha", "Etellerandet12", 60);
			accountlist.add(acc2);
			Account acc3 = new Account( "varle", "Hej12", 200);
			accountlist.add(acc3);
			Account acc4 = new Account( "chris", "Lolleren12", 200);
			accountlist.add(acc4);

	   } 	
	
	  private Account getAccount( String username )
	   {
	      // loop through accounts searching for matching account number
	      for ( Account currentAccount : accountlist )
	      {
	         // return current account if match found
	         if ( currentAccount.getUsername().equals(username))
	            return currentAccount;
	      } // end for

	      return null; // if no matching account was found, return null
	   } 
	  
	  public boolean authenticateUser( String username, String password )
	   {
	      // attempt to retrieve the account with the account number
	      Account userAccount = getAccount( username );

	      // if account exists, return result of Account method validatePIN
	      if ( userAccount != null )
	         return userAccount.validatePass( password );
	      else
	         return false; // account number not found, so return false
	   }
	  
	  public double getBalance(String username){
		  return getAccount(username).getBalance();
	  }
	  
	  public void deposit(String username, double amount){
		  getAccount(username).credit(amount);
	  }
	  
	  public boolean debit(String username, double amount){
		
		  if (getAccount(username).getBalance() >= amount){
			  getAccount(username).debit(amount);
			  return true;
		  }
		  else
		  return false;
		  
	  }
	
}
