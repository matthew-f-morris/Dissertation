package com.project.Thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.project.controller.MessageController;
import com.project.utils.CommunicationInfo;
import com.project.utils.Message;

public class MessageRecieverManager implements Manager {

	private MessageController controller;
	
	private Thread threadServerSocket;
	private ClientServerSocket serversocket;
	
	public MessageRecieverManager() {
	
	}

	public void start() {
		
		serversocket = new ClientServerSocket();		
		threadServerSocket = new Thread(serversocket);
		threadServerSocket.setName("[MESSAGE RECIEVER] SERVER SOCKET");
		threadServerSocket.start();		
	}

	public void stop() {
		
		serversocket.stopThread();
		
		try {
			threadServerSocket.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	class ClientServerSocket extends Thread implements ThreadStop {
		
		private ServerSocket servSocket;
		private boolean isRunning = ThreadStop.isRunning;
		
		public ClientServerSocket() {
			
			System.out.println("[MESSAGE RECIEVER] Message Listener Running...");
			
			try {
				this.servSocket = new ServerSocket(CommunicationInfo.commPort);
			} catch (IOException e) {
				e.printStackTrace();
			}			
			
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

		public void stopThread() {
			
			isRunning = false;
			
			try {
				servSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
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
