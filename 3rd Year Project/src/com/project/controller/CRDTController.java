package com.project.controller;

import com.project.crdt.LogootDocument;
import com.project.datatypes.SequenceAtom;
import com.project.utils.CRDTFileGen;
import com.project.utils.Message;
import com.project.utils.PeerData;

//Simple class that exposes the necessary methods to the message controller

public class CRDTController {

	private static long siteId;
	private static LogootDocument doc;
	
	public static void init(long siteId) {
		CRDTController.siteId = siteId;
		doc = new LogootDocument(siteId);
		doc.modify(true);
	}
	
	//allows the message controller to add a new message to the document using the CRDT facilities

	public static Message handleMessage(String message, PeerData nodeInfo) {
		
		try {
			SequenceAtom atom = doc.addMessage(message, siteId);
			return genMessage(nodeInfo, atom);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void handleBypassMessage(String message, PeerData info, long site) {
		
		try {
			doc.addMessage(message, siteId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void clear() {
		doc.clear();
	}
	
	//allows the message controller to add any sequence atoms recieved from other sites
	
	public static void addMessage(Message message) {
		doc.insertIntoDocument(message.getAtom());
	}
	
	public static Message genMessage(PeerData nodeInfo, SequenceAtom atom) {		
		return new Message(nodeInfo, atom);
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
	
	public static void printDocStats() {
		doc.printInfo();
	}
	
	public static void modifyDoc(Boolean mod) {
		doc.modify(mod);
	}
}
