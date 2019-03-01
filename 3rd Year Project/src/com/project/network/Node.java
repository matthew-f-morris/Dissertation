package com.project.network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

import com.project.utils.Message;

public class Node {
	
    public PeerData nodeInfo;
    private Hashtable<String, PeerData> peers;
	
	private static InetAddress localhost;
	private static int commPort = 50010;
	private static int numberOfBroadcasts = 10;
	private static int broadcastInterval = 4000;

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
    	
    	System.out.println("[NODE] Initialising Node...\n");
    	
    	peers = new Hashtable<String, PeerData>();
    	peers.put(nodeInfo.getUuid(), nodeInfo);

    	peerDiscoverer = new PeerDiscovery(this, broadcastInterval);
    	peerDiscoverer.startDiscovery(Node.numberOfBroadcasts);
    	
    	msgController = new MessageController(this);    	
    }
    
	public void addPeer(String uuid, String hostname, InetAddress address, int port) {
		
		if(!peers.containsKey(uuid)) {
			
			PeerData peer = new PeerData(uuid, hostname, address, port);
    		this.peers.put(uuid, peer);
    		
    		try {
				System.out.println("Added New Peer: " + InetAddress.getLocalHost().toString());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
    }
	
	public void queueToSend(Message message) {		
		msgController.queueToSend(message);
	}
	
	public void getNewMessage() {
		Message temp = msgController.getNewMessage();
		temp.getNumber();
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
