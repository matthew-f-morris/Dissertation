package com.project.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.project.controller.MessageController;
import com.project.utils.Message;

public class MessageReciever {
	
	private boolean isRunning = true;
	private static final int commPort = 50010;
	private MessageController controller = null;
	
	private Thread threadServerSocket;
	private ClientServerSocket serversocket;
	private ServerSocket servSocket;
	
	
	public MessageReciever(MessageController controller) {
		
		try {
			
			servSocket = new ServerSocket(commPort);
			
		} catch (IOException e) {
			
			System.out.println(e);
			e.printStackTrace();
		}
		
		this.controller = controller;
		initialize();
	}
	
	public void initialize() {
		
		isRunning = true;
		
		if(servSocket.isClosed()) {
			try {
				servSocket = new ServerSocket(commPort);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		serversocket = new ClientServerSocket(servSocket);
		
		threadServerSocket = new Thread(serversocket);
		threadServerSocket.setName("[MESSAGE RECIEVER] SERVER SOCKET");
		threadServerSocket.start();
	}
	
	public void shutdown() {
		
		isRunning = false;
		
		try {
			servSocket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			threadServerSocket.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class ClientServerSocket extends Thread {
		
		ServerSocket servSocket;
		
		public ClientServerSocket(ServerSocket servSocket) {
			
			System.out.println("[MESSAGE RECIEVER] Message Listener Running...");
			this.servSocket = servSocket;			
			
		}
		
		public void run() {
			
			while(isRunning) {
				
				try {
					
					Socket socket = servSocket.accept();
					new ClientHandler(socket).start();
					
				} catch (IOException e) {
					System.out.println("[MESSAGE RECIEVER] Message Reciever Thread Ended");
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
			
			String tempAddress = clientSocket.getRemoteSocketAddress().toString();
			System.out.println("[MESSAGE RECIEVER] Socket connection from: " + tempAddress);
			
			try {

				ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
				Message message = (Message) ois.readObject();
				
				if(!message.leaveNetwork()) {
					
					controller.addToRecieved(message);
					System.out.println("[MESSAGE RECIEVER] Message Recieved: " + message.getText());
				
				} else {
					
					String uuid = message.getPeerData().getUuid();
					controller.removePeer(uuid);					
				}
					
			} catch (IOException | ClassNotFoundException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}
}
