package com.project.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.project.utils.Message;

public class MessageReciever {
	
	private boolean isRunning = true;
	private static final int commPort = 50010;
	private ServerSocket servSocket = null;
	private MessageController controller = null;
	
	private Thread threadServerSocket;
	private ClientServerSocket serversocket;
	
	public MessageReciever(MessageController controller) {
		this.controller = controller;
		startRecieving();
	}
	
	public void startRecieving() {
		
		serversocket = new ClientServerSocket();
		if(threadServerSocket == null) {
			threadServerSocket = new Thread(serversocket);
			threadServerSocket.start();
		}
		
		//http://www.java2s.com/Code/Java/Network-Protocol/Startnewthreadforeachclient.htm
		//accept connection, start a thread to deal with it
		
		//while(isRunning) {
			
			
		//}		
	}
	
	class ClientServerSocket extends Thread {
		
		public ClientServerSocket() {
			
			System.out.println("[MESSAGE RECIEVER] Message Listener Running...");
			
			try {
				
				servSocket = new ServerSocket(commPort);
				
			} catch (IOException e1) {
				
				System.out.println(e1);
				e1.printStackTrace();
			}
		}
		
		public void run() {
			
			while(isRunning) {
				
				try {
					
					Socket socket = servSocket.accept();
					new ClientHandler(socket).start();
					
				} catch (IOException e) {
					System.out.println(e);
					e.printStackTrace();
				}
			}
		}
	}
	
	class ClientHandler extends Thread {

		Socket clientSocket;
		
		public ClientHandler(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		
		public void run() {
			
			//Recieve Messages and send them to the message controller
			//using controller reference in class above
			
			try {

				ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
				Message message = (Message) ois.readObject();
				
				controller.addToRecieved(message);
				
			} catch (IOException | ClassNotFoundException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}
}
