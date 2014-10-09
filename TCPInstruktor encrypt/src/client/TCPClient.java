package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shared.Deposit;
import shared.Encrypt;
import shared.Login;
import shared.SerializableData;
import shared.Use;

class TCPClient 
{
	public static void main(String argv[]) throws Exception 
	{
		
		while(true){
		String pin;
		String username;
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		Scanner in = new Scanner(System.in);
		Socket serverSocket = new Socket("localhost", 6789);
		System.out.println("Connected to server.");
		Login login = new Login();
		
		while (!login.isUserAuthenticated()){
		System.out.println("Please input a username");
		username = inFromUser.readLine();
		login.setUsername(username);
		
		System.out.println("Please input your password");
		pin = inFromUser.readLine();
		if(ok(pin)){
		login.setPin(pin);
		
		Encrypt.encrypt(serverSocket.getOutputStream(), login);
		
		login = (Login) Encrypt.decrypt(serverSocket.getInputStream());
		}
		else System.out.println("Your password was incorrect. Remember it can only contain a-z letters and has to contain digits and be between 6-15 characters");
		}
		
		System.out.println("1. deposit bitcoins to your account.");
		System.out.println("2. spend bitcoins.");
		System.out.println("3. exit.");
		
		int k = in.nextInt();
		
		switch(k){
		case 1:
			System.out.println("Enter the amount you want to deposit.");
			double amount = in.nextDouble();
			Deposit dep = new Deposit();
			dep.setAmount(amount);
			dep.setUsername(login.getUsername());
			Encrypt.encrypt(serverSocket.getOutputStream(), dep);
			
			dep = (Deposit) Encrypt.decrypt(serverSocket.getInputStream());
			System.out.println("Your account now have: " + dep.getBalance());
			break;
		case 2:
			System.out.println("Enter the amount you want to deposit.");
			amount = in.nextDouble();
			Use use = new Use();
			use.setAmount(amount);
			use.setUsername(login.getUsername());
			Encrypt.encrypt(serverSocket.getOutputStream(), use);
			
			use = (Use) Encrypt.decrypt(serverSocket.getInputStream());
			if(use.getAcceptedUse()){
				System.out.println("The transaction was accepted.\nYour account now have: " + use.getBalance());
			}
			else{
				System.out.println("The transaction was not accepted because you have insufficient funds.");
			}
			break;
		case 3:
			return;
			
		}
		
		serverSocket.close();
		
		}
	}
	   static Pattern letter = Pattern.compile("[a-zA-z]");
	   static Pattern digit = Pattern.compile("[0-9]");
	   static Pattern length = Pattern.compile (".{6,15}");
	   
	   public final static boolean ok(String password) {
	      Matcher hasLetter = letter.matcher(password);
	      Matcher hasDigit = digit.matcher(password);
	      Matcher hasLength = length.matcher(password);
	      return hasLetter.find() && hasDigit.find() && hasLength.find();

	         }
}