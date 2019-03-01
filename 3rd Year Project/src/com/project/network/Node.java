package com.project.network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.UUID;

import com.project.utils.Message;

public class Node {
	
    public PeerData nodeInfo;
    private Hashtable<String, PeerData> peers;
	
	private static InetAddress localhost;
	private static int commPort = 50010;

    private PeerDiscovery peerDiscoverer;
    private MessageController msgController;
    
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
    	
    	peers = new Hashtable<String, PeerData>();
    	peers.put(nodeInfo.getUuid(), nodeInfo);

    	peerDiscoverer = new PeerDiscovery(this);    	
    	msgController = new MessageController(peers);    	
    }
    
	public void addPeer(String uuid, String hostname, InetAddress address, int port) {
		
		if(!peers.containsKey(uuid)) {
			
			PeerData peer = new PeerData(uuid, hostname, address, port);
    		this.peers.put(uuid, peer);
    		
    		System.out.println("Added New Peer: " + address.getHostName() + ", " + address.getHostAddress());
		}
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
}
