package com.project.utils;

import java.io.Serializable;

import com.project.network.PeerData;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	private PeerData peerData;
	private boolean leaveNetwork = false;
	private String text;
	
	public Message(PeerData peerData, String text) {
		this.text = text;
		this.peerData = peerData;
	}
	
	public Message(boolean leaveNetwork) {
		if(!this.leaveNetwork) {
			this.leaveNetwork = true;
		}
	}
		
	public String getText() {		
		return text;
	}
	
	public boolean leaveNetwork() {
		return leaveNetwork;
	}
	
	public PeerData getPeerData() {
		return peerData;
	}
}
