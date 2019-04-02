package com.project.controller;

import com.project.crdt.LogootDocument;
import com.project.datatypes.SequenceAtom;
import com.project.utils.Message;
import com.project.utils.PeerData;

public class CRDTController {

	private long siteId;
	private LogootDocument doc;
	
	public CRDTController(long siteId) {
		this.siteId = siteId;
		doc = new LogootDocument(siteId);
		doc.initDocument();
		doc.modify(true);
	}

	public Message handleMessage(String message, PeerData nodeInfo) {
		
		try {
			SequenceAtom atom = doc.addMessage(message, siteId);
			return genMessage(nodeInfo, atom);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void addMessage(Message message) {
		doc.insertIntoDocument(message.getAtom());
	}
	
	public Message genMessage(PeerData nodeInfo, SequenceAtom atom) {		
		return new Message(nodeInfo, atom);
	}
}
