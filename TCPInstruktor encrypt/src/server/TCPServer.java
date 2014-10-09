package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import shared.Deposit;
import shared.Encrypt;
import shared.Login;
import shared.SerializableData;
import shared.Use;

class TCPServer {
	
	Database database = new Database();
		public void runServer() {
			try {
				int serverport = 6789;
				ServerSocket listenSocket = new ServerSocket(serverport);
				System.out.println("Server Running");
				while (true) {
					Socket clientSocket = listenSocket.accept();
					System.out.println("Client Connected");
					MultiServer s = new MultiServer(clientSocket);
				}
			} catch (IOException e) {
				System.out.println("Listen :" + e.getMessage());
			}

		}
	
	class MultiServer extends Thread {
		Socket clientSocket;
		
		
		public MultiServer(Socket cSocket) {
			clientSocket = cSocket;
			this.start();
		}

		public void run() {
			try {
				System.out.println("Waiting to receive a sentence from client.");

				boolean userAuth = false;
				while(!userAuth){
				Login loginReq = (Login) Encrypt.decrypt(clientSocket.getInputStream());

				if(database.authenticateUser(loginReq.getUsername(), loginReq.getPin())){
					loginReq.setUserAuthenticated(true);
					userAuth = true;
					loginReq.setBalance(database.getBalance(loginReq.getUsername()));
					Encrypt.encrypt(clientSocket.getOutputStream(), loginReq);
					System.out.println(loginReq.getUsername() + " Authenticated");
				}
				}

				Object k = Encrypt.decrypt(clientSocket.getInputStream());
				System.out.println("Object received");
				Deposit deposit = new Deposit();
				Use use = new Use();
				System.out.println(k.getClass());
				if (k.getClass() == Deposit.class){
					System.out.println("Trold");
					deposit = (Deposit) k;
					database.deposit(deposit.getUsername(), deposit.getAmount());
					deposit.setBalance(database.getBalance(deposit.getUsername()));
					System.out.println(deposit.getAmount() + " inserted into " + deposit.getUsername() + " account.");
					Encrypt.encrypt(clientSocket.getOutputStream(), deposit);
				}
				
				if(k.getClass().equals(Use.class)){
					System.out.println("use");
					use = (Use) k;
					if(database.debit(use.getUsername(), use.getAmount())){
					use.setAcceptedUse(true);
					use.setBalance(database.getBalance(use.getUsername()));
					Encrypt.encrypt(clientSocket.getOutputStream(), use);
					}
					else {
						use.setAcceptedUse(false);
						Encrypt.encrypt(clientSocket.getOutputStream(), use);
					}
				}

			} catch (EOFException e) {
				System.out.println("EOF:" + e.getMessage());
			} catch (IOException e) {
				System.out.println("IO:" + e.getMessage());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e) {
					/* close failed */
				}
			}
		}

	}
}