package com.project.controller;

import com.project.crdt.LogootDocument;
import com.project.datatypes.SequenceAtom;
import com.project.utils.CRDTFileGen;
import com.project.utils.Message;
import com.project.utils.PeerData;

//Simple class that exposes the necessary methods to the message controller

public class CRDTController {

	private long siteId;
	private static LogootDocument doc;
	
	public CRDTController(long siteId) {
		this.siteId = siteId;
		doc = new LogootDocument(siteId);
		doc.modify(true);
	}
	
	//allows the message controller to add a new message to the document using the CRDT facilities

	public Message handleMessage(String message, PeerData nodeInfo) {
		
		try {
			SequenceAtom atom = doc.addMessage(message, siteId);
			return genMessage(nodeInfo, atom);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public void handleBypassMessage(String message, PeerData info, long site) {
		
		try {
			doc.addMessage(message, siteId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//allows the message controller to add any sequence atoms recieved from other sites
	
	public void addMessage(Message message) {
		doc.insertIntoDocument(message.getAtom());
	}
	
	public Message genMessage(PeerData nodeInfo, SequenceAtom atom) {		
		return new Message(nodeInfo, atom);
	}
	
	public void printDocument() {
		doc.printInfo();
	}
	
	public static int getDocSize() {
		return doc.docSize();
	}
	
	public static void printDoc() {
		CRDTFileGen.start(doc.getStringList());
	}
	
	public static void printDoc(LogootDocument doc) {
		CRDTFileGen.start(doc.getStringList());
	}
}
