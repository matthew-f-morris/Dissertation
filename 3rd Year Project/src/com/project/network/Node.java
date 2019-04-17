package com.project.network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.project.Thread.ThreadManager;
import com.project.controller.DocumentController;
import com.project.controller.MessageController;
import com.project.utils.CRDTFileGen;
import com.project.utils.CommunicationInfo;
import com.project.utils.MsgGen;
import com.project.utils.PeerData;

import javafx.application.Platform;

public class Node {

    public PeerData nodeInfo;
    private ConcurrentHashMap<Long, PeerData> peers;
	
	private static InetAddress localhost;
	private boolean joined = false;
	
    private ThreadManager manager;
    private MessageController msgController;
    
    public String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    
    public Node() {
    	    
    	String hostname = null;
    	
    	try {    	
    		
    		localhost = InetAddress.getLocalHost();
			hostname = localhost.getHostName();
			
		} catch (UnknownHostException e) {			
			System.out.println("Unable to get local host data!");
			e.printStackTrace();
		}
    	
    	System.out.println("[NODE] Initialising Node...");
    	
    	nodeInfo = new PeerData(Math.abs(UUID.randomUUID().getLeastSignificantBits()), hostname, localhost, CommunicationInfo.commPort);
  	    	
    	peers = new ConcurrentHashMap<Long, PeerData>();
    	peers.put(nodeInfo.getUuid(), nodeInfo);
   
    	msgController = new MessageController(this, peers, nodeInfo.getUuid());    	
     	
    	manager = new ThreadManager(this, msgController);
    	manager.joinNetwork();
    	
    	joined = true;   
    	    	
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
	public void addPeer(long uuid, String hostname, InetAddress address, int port) {
		
		if(!peers.containsKey(uuid)) {
			
			PeerData peer = new PeerData(uuid, hostname, address, port);
    		this.peers.put(uuid, peer);
    		
    		System.out.println("[NODE] " + timeStamp + " -  Added New Peer: " + address.getHostName() + ", " + address.getHostAddress());
		}
    }
	
	public void removePeer(long uuid) {
		
		if(peers.contains(uuid)) {
			peers.remove(uuid);
		} else {
			System.err.println("Peer does not exist!");
		}		
	}
	
	public void queueToSend(String message) {		
		msgController.queueToSend(message);
	}
	
	public void viewMessages(String text) {
		
		if(text.equals("Send")) {
			msgController.peekSend();
			
		} else {
			msgController.peekRecieved();
		}
	}
	
	public void viewMessages() {
		msgController.peekRecieved();
		msgController.peekSend();
	}
	
	public void viewPeers() {
		
		for(PeerData peer : this.peers.values()) {
			System.out.println("[NODE] Peer: " + peer.getAddress());
		}
		
		System.out.println("[NODE] Number of peers: " + peers.size());	
	}
	
//	private void setUsername(String username) {
//		
//		if(!username.isEmpty()) {
//			this.username = username;
//			System.out.println("Username: " + this.username);
//		}
//	}
//	
//	public void confirmLogin(String username, String password) {
//		
//		if(password.equals(Node.password)) {
//			this.setUsername(username);
//		}
//	}
		
	public void shutdown() {
		
		System.out.println(" --- NODE SHUTTING DOWN ---");
		manager.leaveNetwork();
		System.out.println(" --- NODE SHUTDOWN ---");
		Platform.exit();
		System.exit(0);
	}
	
	public void leaveNetwork() {
		
		if(!joined) {			
			System.err.println("Node Not Joined To Network");
		} else {
			
			manager.leaveNetwork();
			joined = false;
			System.out.println("\n --- NODE LEFT NETWORK ---\n");
		}
	}
	
	public void joinNetwork() {
		
		if(joined) {			
			System.err.println("Node Already Joined To Network");
		} else {
			
			manager.joinNetwork();
			System.out.println("\n --- NODE JOINING NETWORK ---\n");
			joined = true;
		}
	}
	
	public void print(Boolean printDocSnippet) {
		DocumentController.printDoc("", printDocSnippet);
	}
	
	public void bypass(String str, long site) {
		msgController.bypassSend(str, site);
	}
	
	public ThreadManager getThreadManager() {
		return manager;
	}
}
