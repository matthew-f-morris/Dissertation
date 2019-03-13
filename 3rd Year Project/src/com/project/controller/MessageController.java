package com.project.controller;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.project.network.MessageReciever;
import com.project.network.MessageSender;
import com.project.network.Node;
import com.project.network.PeerData;
import com.project.utils.AddressMessage;
import com.project.utils.Message;

public class MessageController {

	private Node node;
	
	private ConcurrentHashMap<String, PeerData> peers;
	private MessageSender sender;
	private MessageReciever reciever;
	private LinkedList<Message> messagesRecieved;
	private LinkedList<Message> messagesToSend;
	private ConcurrentLinkedQueue<AddressMessage> toResend;
	private final int sendInterval = 1000;
	private final int resendInterval = 5000;

	private boolean isRunning = true;
	private Thread threadSender;
	private SenderChecker senderChecker;
	
	private Thread threadResender;
	private Resender resender;
	
	private String uuid = null;
	
	public MessageController(Node node, ConcurrentHashMap<String, PeerData> peers, String uuid) {

		this.node = node;
		this.peers = peers;
		this.uuid = uuid;
		sender = new MessageSender(this);
		reciever = new MessageReciever(this);
		messagesRecieved = new LinkedList<Message>();
		messagesToSend = new LinkedList<Message>();
		toResend = new ConcurrentLinkedQueue<AddressMessage>();

		initializeThreads();
	}

	private void initializeThreads() {
		
		senderChecker = new SenderChecker();
		threadSender = new Thread(senderChecker);
		threadSender.start();
		
		resender = new Resender();
		threadResender = new Thread(resender);
		threadResender.start();
	}

	public void queueToSend(Message message) {
		
		messagesToSend.add(message);
		System.out.println("[MESSAGE CONTROLLER] Message Queued To Send: " + message.getText());
	}

	public void addToRecieved(Message message) {
		
		messagesRecieved.add(message);		
	}

	private boolean checkEmptySend() {

		if (messagesToSend.size() <= 0) {
			return true;
		} else
			return false;
	}

	private boolean checkEmptyRecieved() {

		if (messagesRecieved.size() <= 0) {
			return true;
		} else
			return false;
	}

//	public Message getNewMessage() {
//
//		if (checkEmptyRecieved()) {
//			return new Message(null);
//		} else {
//			return messagesRecieved.removeFirst();
//		}
//	}
	
	public void peekSend() {

		if (checkEmptySend()) {
			System.out.println("[MESSAGE CONTROLLER] Messages to send is empty!");

		} else {

			for (Message msg : messagesToSend) {
				System.out.println("[MESSAGE CONTROLLER] Messaages To Send: " + msg.getText());
			}

			System.out.println("[MESSAGE CONTROLLER] No. of messages to send: " + messagesToSend.size());
		}
	}

	public void peekRecieved() {

		if (checkEmptyRecieved()) {
			System.out.println("[MESSAGE CONTROLLER] Recieved Messages is empty!");

		} else {

			for (Message msg : messagesRecieved) {
				System.out.print("[MESSAGE CONTROLLER] Messaages Recieved: " + msg.getText());
			}

			System.out.println("[MESSAGE CONTROLLER] No. of messages to send: " + messagesRecieved.size());
		}
	}
	
	public void shutdown() {
		
		reciever.shutdown();
		
		if(isRunning)
			isRunning = false;
		
		try {
			threadSender.join();
			threadResender.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void leaveNetwork() {
	
		Message leaveMessage = new Message(true);
		
		for(PeerData peer : peers.values()) {
			if(!(peer.getUuid().equals(node.nodeInfo.getUuid()))){		
				sender.sendLeaveNetworkMessage(peer.getAddress(), leaveMessage);
			}
		}
		
		isRunning = false;
	}
	
	public void startup() {
		isRunning = true;
		initializeThreads();
	}
	
	public void removePeer(String uuid) {
		node.removePeer(uuid);
	}

	class SenderChecker extends Thread {

		public void run() {

			System.out.println("[MESSAGE CONTROLLER] Sender Checker Thread started...");
			
			while (isRunning) {

				try {
					Thread.sleep(sendInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (messagesToSend.size() >= 1) {
					Message toSend = messagesToSend.removeLast();
					for (PeerData peer : peers.values()) {						
						if(!(peer.getUuid().equals(node.nodeInfo.getUuid()))){							
							boolean sent = sender.sendMessage(peer.getAddress(), toSend);
							
							if(!sent) {
								toResend.add(new AddressMessage(peer.getAddress(), toSend));
							}
						}						
					}
				}
			}

			System.out.println("[MESSAGE CONTROLLER] Sender Checker Thread terminated...");
		}
	}
	
	class Resender extends Thread {
		
		public void run() {
			
			System.out.println("[MESSAGE CONTROLLER] Resender Thread started...");
			
			while (isRunning) {

				try {
					Thread.sleep(resendInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (toResend.size() >= 1) {
					
					System.out.println("Number of messages to resend: " + toResend.size());
					AddressMessage resend = toResend.poll();
					
					boolean resent = sender.sendMessage(resend.getAddress(), resend.getMessage());					
					if(!resent) {
						toResend.add(resend);
					}
				}
			}		
		}
	}
}
