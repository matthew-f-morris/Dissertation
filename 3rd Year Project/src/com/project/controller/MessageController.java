package com.project.controller;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.project.crdt.LogootDocument;
import com.project.network.MessageSender;
import com.project.network.Node;
import com.project.utils.AddressMessage;
import com.project.utils.Message;
import com.project.utils.PeerData;

public class MessageController {

	public Node node;
	
	public ConcurrentHashMap<Long, PeerData> peers;
	public MessageSender sender;
	//private MessageReciever reciever;
	public LinkedList<Message> messagesRecieved;
	public LinkedList<Message> messagesToSend;
	public ConcurrentLinkedQueue<AddressMessage> toResend;
	private CRDTController crdt;
	
	private long uuid = 0L;
	
	public MessageController(Node node, ConcurrentHashMap<Long, PeerData> peers, long uuid) {

		this.node = node;
		this.peers = peers;
		this.uuid = uuid;

		setMessagesToSend(new LinkedList<Message>());
		toResend = new ConcurrentLinkedQueue<AddressMessage>();
		
		crdt = new CRDTController(uuid);
	}

	public void queueToSend(String message) {
		
		Message crdtMessage = crdt.handleMessage(message, node.nodeInfo);
		
		getMessagesToSend().add(crdtMessage);
		System.out.println("[MESSAGE CONTROLLER] Message Queued To Send: " + message);
	}

	public void addToRecieved(Message message) {
		
		messagesRecieved.add(message);
		crdt.addMessage(message);
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
	
	public void sendLeaveMessage() {
	
		Message leaveMessage = new Message(true);
		
		for(PeerData peer : peers.values()) {
			if(!(peer.getUuid() == node.nodeInfo.getUuid())){		
				sender.sendLeaveNetworkMessage(peer.getAddress(), leaveMessage);
			}
		}
		
		System.out.println("[MESSAGE CONTROLLER] Leave Message Sent");
	}
	
	public void removePeer(long uuid) {
		node.removePeer(uuid);
	}

	public LinkedList<Message> getMessagesToSend() {
		return messagesToSend;
	}

	public void setMessagesToSend(LinkedList<Message> messagesToSend) {
		this.messagesToSend = messagesToSend;
	}
}
