package com.project.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.project.utils.Message;

public class MessageSender {

	private static final int commPort = 50010;
	private MessageController controller = null;
	
	public MessageSender(MessageController controller) {
		this.controller = controller;
	}
	
	public void sendMessage(InetAddress address, Message message) {
		
		System.out.println("[MESSAGE SENDER] Sending Message...");					
		new RecipientHandler(address, message).start();
		
	}
	
	class RecipientHandler extends Thread {
		
		//start one of these to send a message to a client!
		
		Message message;
		InetAddress address;
		
		public RecipientHandler(InetAddress address, Message message) {
			
			this.message = message;
			this.address = address;
		}		
		
		public void run() {
			
			try {
				
				System.out.println("DEBUG| " + address.getHostAddress());
				Socket socket = new Socket(address, commPort);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				
				oos.writeObject(message);
				socket.close();
				
			} catch (IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}	
}
