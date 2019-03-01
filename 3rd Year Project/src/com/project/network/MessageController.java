package com.project.network;

import java.util.LinkedList;

import com.project.utils.Message;

public class MessageController {
	
	private Node node;
	private MessageSender sender;
	private MessageReciever reciever;
	private LinkedList<Message> messagesRecieved;
	private LinkedList<Message> messagesToSend;
	
	public MessageController(Node node) {
		
		this.node = node;		
		sender = new MessageSender(this);
		reciever = new MessageReciever(this);
		messagesRecieved = new LinkedList<Message>();
		messagesToSend = new LinkedList<Message>();
	}
	
	public void queueToSend(Message message) {
		messagesToSend.add(message);
	}
	
	public void sendMessage(PeerData data, Message message) { 		
		sender.sendMessage(data.getAddress(), message);
	}

	public void addToRecieved(Message message) {
		System.out.println("[MESSAGE CONTROLLER] Message Number: " + message.getNumber());
	}	
		
	private boolean checkEmptySend() {
		if(messagesToSend.size() <= 0 ) {
			return true;
		} else return false;
	}
	
	private boolean checkEmptyRecieved() {
		if(messagesRecieved.size() <= 0 ) {
			return true;
		} else return false;
	}
	
	public Message getNewMessage() {
		
		if(checkEmptyRecieved()) {
			return new Message(-1);
		} else {
			return messagesRecieved.removeFirst();
		}
	}
	
	public void peekSend() {		
		
		if(checkEmptySend()) {
			System.out.println("[MESSAGE CONTROLLER] Messages to send is empty!");
			
		} else {
			
			for(Message msg : messagesToSend) {
				System.out.println("[MESSAGE CONTROLLER] Messaages To Send: " + msg.getNumber());
			}
			
			System.out.println("[MESSAGE CONTROLLER] No. of messages to send: " + messagesToSend.size());
		}		
	}
	
	public void peekRecieved() {		
		
		if(checkEmptyRecieved()) {
			System.out.println("[MESSAGE CONTROLLER] Recieved Messages is empty!");
			
		} else {
			
			for(Message msg : messagesRecieved) {
				System.out.print("[MESSAGE CONTROLLER] Messaages Recieved: " + msg.getNumber());
			}
			
			System.out.println("[MESSAGE CONTROLLER] No. of messages to send: " + messagesRecieved.size());
		}		
	}	
}
