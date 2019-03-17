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
}
