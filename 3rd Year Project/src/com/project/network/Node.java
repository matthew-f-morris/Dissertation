package com.project.network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.project.controller.MessageController;
import com.project.controller.ViewController;
import com.project.utils.Message;

import javafx.application.Platform;

public class Node {
	
	private String username = "";
	private static String password = "dissertation";
	
    public PeerData nodeInfo;
    private ConcurrentHashMap<String, PeerData> peers;
	
	private static InetAddress localhost;
	private static int commPort = 50010;
	private boolean joined = false;

    private PeerDiscovery peerDiscoverer;
    private MessageController msgController;
    private ViewController viewControl;
    
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
    	
    	nodeInfo = new PeerData(UUID.randomUUID().toString(), hostname, localhost, Node.commPort);
    	
    }
    
    public void initialise() {  
    	
    	System.out.println("[NODE] Initialising Node...");
    	
    	peers = new ConcurrentHashMap<String, PeerData>();
    	peers.put(nodeInfo.getUuid(), nodeInfo);

    	peerDiscoverer = new PeerDiscovery(this);    	
    	msgController = new MessageController(this, peers, nodeInfo.getUuid());    	
    	
    	joined = true;
    }
    
	public void addPeer(String uuid, String hostname, InetAddress address, int port) {
		
		if(!peers.containsKey(uuid)) {
			
			PeerData peer = new PeerData(uuid, hostname, address, port);
    		this.peers.put(uuid, peer);
    		viewControl.addNode(address.toString());
    		
    		System.out.println("[NODE] " + timeStamp + " -  Added New Peer: " + address.getHostName() + ", " + address.getHostAddress());
		}
    }
	
	public void removePeer(String uuid) {
		peers.remove(uuid);
	}
	
	public void queueToSend(Message message) {		
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
	
	private void setUsername(String username) {
		
		if(!username.isEmpty()) {
			this.username = username;
			System.out.println("Username: " + this.username);
		}
	}
	
	public void confirmLogin(String username, String password) {
		
		if(password.equals(Node.password)) {
			this.setUsername(username);
		}
	}

	public void addViewController(ViewController viewControl) {
		this.viewControl = viewControl;		
	}
		
	public void shutdown() {
		
		peerDiscoverer.shutdown();
		msgController.shutdown();
		System.out.println("\n --- NODE SHUTDOWN ---\n");
		Platform.exit();
		System.exit(0);
	}
	
	public void leaveNetwork() {
		
		if(!joined) {			
			System.err.println("Node Not Joined To Network");
		} else {
			
			peerDiscoverer.leaveNetwork();
			msgController.leaveNetwork();
			joined = false;
			System.out.println("\n --- NODE LEFT NETWORK ---\n");
		}
	}
	
	public void joinNetwork() {
		
		if(joined) {			
			System.err.println("Node Already Joined To Network");
		} else {
		
			System.out.println("\n --- NODE JOINING NETWORK ---\n");
			peerDiscoverer.startDiscovery();
			msgController.startup();
			joined = true;
		}
	}
}
