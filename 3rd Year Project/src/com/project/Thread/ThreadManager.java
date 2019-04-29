package com.project.Thread;

import java.util.List;
import java.util.ArrayList;

import com.project.controller.MessageController;
import com.project.network.Node;

public class ThreadManager {

	private BroadcastListenerManager broadcastListener;
	private BroadcastManager broadcast;
	private MessageRecieverManager reciever;
	private SenderCheckerManager sender;
	private ResenderManager resender;
	private MessageController controller;
	
	private List<Manager> subManagers = new ArrayList<Manager>();
	
	public ThreadManager(Node node, MessageController controller) {
		
		this.controller = controller;
		
		broadcastListener = new BroadcastListenerManager(node);
		broadcast = new BroadcastManager(node);
		reciever = new MessageRecieverManager(controller);
		sender = new SenderCheckerManager(controller);
		resender = new ResenderManager(controller);
		
		subManagers.add(broadcastListener);
		subManagers.add(broadcast);
		subManagers.add(reciever);
		subManagers.add(sender);
		subManagers.add(resender);
	}
	
	public void joinNetwork() {
	
		for(Manager m : subManagers) {
			m.start();
		}
		
		while(!checkRunning()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Boolean leaveNetwork() {
		
		for(Manager m : subManagers) {
			m.stop();
		}
		
		while(!checkStopped()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public Boolean checkStopped() {
				
		for(Manager m : subManagers) {
			if(m.threadState()) {
				return false;
			}
		}
		
		return true;
	}
	
	public Boolean checkRunning() {
		
		for(Manager m : subManagers) {
			if(!m.threadState()) {
				return false;
			}
		}
		
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
