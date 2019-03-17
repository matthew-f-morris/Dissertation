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

	public Node node;
	
	public ConcurrentHashMap<String, PeerData> peers;
	public MessageSender sender;
	private MessageReciever reciever;
	public LinkedList<Message> messagesRecieved;
	public LinkedList<Message> messagesToSend;
	public ConcurrentLinkedQueue<AddressMessage> toResend;

	private boolean isRunning = true;
	
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
		setMessagesToSend(new LinkedList<Message>());
		toResend = new ConcurrentLinkedQueue<AddressMessage>();

		initializeThreads();
	}

	private void initializeThreads() {
		
		isRunning = true;
		
		senderChecker = new SenderChecker();
		resender = new Resender();
		
		threadSender = new Thread(senderChecker);
		threadSender.setName("[MESSAGE CONTROLLER] SEND CHECKER");
		threadSender.start();

		threadResender = new Thread(resender);
		threadResender.setName("[MESSAGE CONTROLLER] RESENDER");
		threadResender.start();

	}

	public void queueToSend(Message message) {
		
		getMessagesToSend().add(message);
		System.out.println("[MESSAGE CONTROLLER] Message Queued To Send: " + message.getText());
	}

	public void addToRecieved(Message message) {
		
		messagesRecieved.add(message);		
	}

	private boolean checkEmptySend() {

		if (getMessagesToSend().size() <= 0) {
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

			for (Message msg : getMessagesToSend()) {
				System.out.println("[MESSAGE CONTROLLER] Messaages To Send: " + msg.getText());
			}

			System.out.println("[MESSAGE CONTROLLER] No. of messages to send: " + getMessagesToSend().size());
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
		
		leaveNetwork();		
	}
	
	public void leaveNetwork() {
	
		Message leaveMessage = new Message(true);
		
		for(PeerData peer : peers.values()) {
			if(!(peer.getUuid().equals(node.nodeInfo.getUuid()))){		
				sender.sendLeaveNetworkMessage(peer.getAddress(), leaveMessage);
			}
		}
		
		isRunning = false;
		
		try {
			
			threadSender.join();
			threadResender.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		reciever.shutdown();
		System.out.println("[MESSAGE CONTROLLER] ALL MESSAGING SYSTEMS SHUT DOWN");
	}
	
	public void startup() {
		
		isRunning = true;
		initializeThreads();
		reciever.initialize();
	}
	
	public void removePeer(String uuid) {
		node.removePeer(uuid);
	}

	public LinkedList<Message> getMessagesToSend() {
		return messagesToSend;
	}

	public void setMessagesToSend(LinkedList<Message> messagesToSend) {
		this.messagesToSend = messagesToSend;
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

				if (getMessagesToSend().size() >= 1) {
					Message toSend = getMessagesToSend().removeLast();
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

			System.out.println("[MESSAGE CONTROLLER] Sender Checker Thread Ended");
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
			
			System.out.println("[MESSAGE CONTROLLER] Resender Thread Ended");
		}
	}
}
