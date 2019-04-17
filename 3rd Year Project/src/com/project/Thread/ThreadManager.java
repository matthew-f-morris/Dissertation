package com.project.Thread;

import com.project.controller.MessageController;
import com.project.network.Node;

public class ThreadManager {

	private BroadcastListenerManager broadcastListener;
	private BroadcastManager broadcast;
	private MessageRecieverManager reciever;
	private SenderCheckerManager sender;
	private ResenderManager resender;
	private MessageController controller;
	
	public ThreadManager(Node node, MessageController controller) {
		
		this.controller = controller;
		
		broadcastListener = new BroadcastListenerManager(node);
		broadcast = new BroadcastManager(node);
		reciever = new MessageRecieverManager(controller);
		sender = new SenderCheckerManager(controller);
		resender = new ResenderManager(controller);
	}
	
	public void joinNetwork() {
	
		broadcastListener.start();
		broadcast.start();
		reciever.start();
		sender.start();
		resender.start();
	}
	
	public void leaveNetwork() {
		
		broadcastListener.stop();
		broadcast.stop();
		reciever.stop();
		sender.stop();
		resender.stop();
		
		controller.sendLeaveMessage();
	}
	
	public Boolean checkStopped() {
		
		//Write methods getting the state of each thread from each individual manager
		
		return true;
	}
	
	public Boolean checkRunning() {
		
		//Write methods getting the state of each thread from each individual manager
		
		return true;
	}
	
	public BroadcastListenerManager getBroadcastListener() {
		return broadcastListener;
	}

	public BroadcastManager getBroadcast() {
		return broadcast;
	}

	public MessageRecieverManager getReciever() {
		return reciever;
	}

	public SenderCheckerManager getSender() {
		return sender;
	}

	public ResenderManager getResender() {
		return resender;
	}

	public MessageController getController() {
		return controller;
	}
}
