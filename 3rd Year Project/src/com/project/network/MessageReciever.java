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
	}
	
	public void shutdown() {
		
		if(isRunning)
			isRunning = false;
		
		try {
			threadServerSocket.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class ClientServerSocket extends Thread {
		
		public ClientServerSocket() {
			
			System.out.println("[MESSAGE RECIEVER] Message Listener Running...");
			
			try {
				
				servSocket = new ServerSocket(commPort);
				
			} catch (IOException e) {
				
				System.out.println(e);
				e.printStackTrace();
			}
		}
		
		public void run() {
			
			while(isRunning) {
				
				try {
					
					Socket socket = servSocket.accept();
					ClientHandler ch = new ClientHandler(socket);
					ch.start();
					ch.join();
					
				} catch (IOException | InterruptedException e) {
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
