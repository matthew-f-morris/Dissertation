package com.project.utils;

import java.io.Serializable;

import com.project.datatypes.SequenceAtom;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private PeerData peerData;
	private boolean leaveNetwork = false;
	private SequenceAtom atom;
	
	public Message(PeerData peerData, SequenceAtom atom) {
		this.peerData = peerData;
		this.atom = atom;
	}
	
	public Message(PeerData data, boolean leaveNetwork) {
		
		if(!this.leaveNetwork) {
			this.leaveNetwork = true;
		}
		
		atom = null;
		peerData = data;
	}
		
	public String getText() {		
		return atom.message;
	}
	
	public boolean leaveNetwork() {
		return leaveNetwork;
	}
	
	public PeerData getPeerData() {
		return peerData;
	}
	
	public SequenceAtom getAtom() {
		return atom;
	}
}
