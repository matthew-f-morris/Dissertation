package com.project.controller;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.project.clock.VersionVector;
import com.project.network.MessageSender;
import com.project.network.Node;
import com.project.utils.AddressMessage;
import com.project.utils.Message;
import com.project.utils.PeerData;

/*
 * Main controller class that enables a node to send messages to other nodes
 * 		
 * 		- Stores messages to be sent and recieved messages
 * 		- Sends messages out using the message sender
 * 		- Recieves messages from the reciever
 * 		- Sends messages to the CRDTController to handle adding to the CRDT and document
 */

public class MessageController {

	public Node node;
	
	public ConcurrentHashMap<Long, PeerData> peers;
	public MessageSender sender;
	public LinkedList<Message> messagesRecieved = new LinkedList<Message>();
	public LinkedList<Message> messagesToSend = new LinkedList<Message>();
	public ConcurrentLinkedQueue<AddressMessage> toResend = new ConcurrentLinkedQueue<AddressMessage>();
//	private long uuid = 0L;
	
	public MessageController(Node node, ConcurrentHashMap<Long, PeerData> peers, long uuid) {

		this.node = node;
		this.peers = peers;
//		this.uuid = uuid;
		
		sender = new MessageSender(this);
		DocumentController.init(uuid);
	}

	//adds messages to the 'To-Send' queue so to speak
	//inserts the message into the local crdt
	
	public void queueToSend(String message) {
		
		System.out.println("[MESSAGE CONTROLLER] Sending Message: " + message);
		Message crdtMessage = DocumentController.handleMessage(message, node.nodeInfo);
		messagesToSend.add(crdtMessage);
	}
	
	public void bypassSend(String str, long site) {		
		DocumentController.handleBypassMessage(str, node.nodeInfo, site);
	}
	
	//called when a message is recieved from the message reciever
	//adds the message to the recieved queue
	//adds the atom in the message to the local crdt document

	public void addToRecieved(Message message) {
		
		messagesRecieved.add(message);
		VersionVector.sync(message.getPeerData().getVectorClock());
		DocumentController.addMessage(message);
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
	
	//checks the number of messages in the 'To-Send' queue
	
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
	
	//checks the number of messages in the 'Received' queue

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
	
	//sends a message to the other replicas saying that a node has left the network
	
	public void sendLeaveMessage() {
	
		Message leaveMessage = new Message(node.nodeInfo, true);
		
		for(PeerData peer : peers.values()) {
			if(!(peer.getUuid() == node.nodeInfo.getUuid())){		
				sender.sendLeaveNetworkMessage(peer.getAddress(), leaveMessage);
			}
		}
		
		System.out.println("[MESSAGE CONTROLLER] Leave Message Sent");
	}
	
	//method called that removes a peer from the list of known peers, this occurs when a 'leave-message' is received
	
	public void removePeer(long uuid) {
		node.removePeer(uuid);
	}

	public void setMessagesToSend(LinkedList<Message> messagesToSend) {
		this.messagesToSend = messagesToSend;
	}
}
