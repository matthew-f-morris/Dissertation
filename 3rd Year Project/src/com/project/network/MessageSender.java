package com.project.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

import com.project.Thread.ThreadStop;
import com.project.controller.MessageController;
import com.project.utils.Message;

public class MessageSender {

	private static final int commPort = 50010;
	private MessageController controller = null;
	
	public MessageSender(MessageController controller) {
		this.controller = controller;
	}
	
	public boolean sendMessage(InetAddress address, Message message) {
		
		System.out.println("[MESSAGE SENDER] Sending Message...");
		
		try {
			
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(address,  commPort), 1000);
			
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());			
			oos.writeObject(message);
			socket.close();
			
		} catch (IOException e) {
			System.out.println(e);
			return false;
		}
		
		return true;
	}
	
	public void sendLeaveNetworkMessage(InetAddress address, Message message) {		
		
		RecipientHandler handler = new RecipientHandler(address, message);
		handler.start();
		
		try {
			handler.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class RecipientHandler extends Thread implements ThreadStop {
		
		//start one of these to send a message to a client!
		
		Message message;
		InetAddress address;
		
		public RecipientHandler(InetAddress address, Message message) {
			
			this.message = message;
			this.address = address;
		}		
		
		public void run() {
			
			try {
				
				Socket socket = new Socket();
				socket.connect(new InetSocketAddress(address, commPort), 1000);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				
				oos.writeObject(message);
				socket.close();
				
			} catch (IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}

		@Override
		public void stopThread() {
			// TODO Auto-generated method stub
			
		}
	}
}
