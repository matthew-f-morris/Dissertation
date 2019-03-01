package com.project.network;

import java.util.Hashtable;
import java.util.LinkedList;

import com.project.utils.Message;

public class MessageController {

	private Hashtable<String, PeerData> peers;
	private MessageSender sender;
	private MessageReciever reciever;
	private LinkedList<Message> messagesRecieved;
	private LinkedList<Message> messagesToSend;
	private final int sendInterval = 1000;

	private boolean isRunning = true;
	private Thread threadMessageController;
	private SenderChecker senderChecker;

	public MessageController(Hashtable<String, PeerData> peers) {

		this.peers = peers;
		sender = new MessageSender(this);
		reciever = new MessageReciever(this);
		messagesRecieved = new LinkedList<Message>();
		messagesToSend = new LinkedList<Message>();

		initializeThreads();
	}

	private void initializeThreads() {
		senderChecker = new SenderChecker();
		threadMessageController = new Thread(senderChecker);
		threadMessageController.start();
	}

	public void queueToSend(Message message) {
		messagesToSend.add(message);
		System.out.println("[MESSAGE CONTROLLER] Message Queued To Send: " + message.getText());
	}

	public void addToRecieved(Message message) {

		messagesRecieved.add(message);
		System.out.println("[MESSAGE CONTROLLER] Message Recieved: " + message.getText());
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

	public Message getNewMessage() {

		if (checkEmptyRecieved()) {
			return new Message(null);
		} else {
			return messagesRecieved.removeFirst();
		}
	}

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
						sender.sendMessage(peer.getAddress(), toSend);
					}
				}
			}

			System.out.println("[MESSAGE CONTROLLER] SenderChecker Thread terminated...");
		}
	}
}
