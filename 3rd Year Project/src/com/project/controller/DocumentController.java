package com.project.controller;

import java.util.Arrays;

import com.project.crdt.LogootCRDT;
import com.project.crdt.LogootDocument;
import com.project.datatypes.SequenceAtom;
import com.project.utils.CRDTFileGen;
import com.project.utils.Message;
import com.project.utils.PeerData;

//Simple class that exposes the necessary methods to the message controller

public class DocumentController {

	private static long siteId;
	private static LogootDocument doc;
	
	public static void init(long siteId) {
		
		DocumentController.siteId = siteId;
		doc = new LogootDocument(siteId);
		doc.modify(true);
		doc.setLseq(false);		
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
			doc.addMessage(message, site);
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
	
	private static Message genMessage(PeerData nodeInfo, SequenceAtom atom) {		
		return new Message(nodeInfo, atom);
	}
	
	public static int getDocSize() {
		return doc.docSize();
	}
	
	public static void printDoc(String str, Boolean printDocSnippet) {
		
		CRDTFileGen.start(doc.getStringList(), str);
		
		if(printDocSnippet) {
			doc.printDocSnippet(10);
		}
	}
	
	public static void printDoc(LogootDocument doc) {
		CRDTFileGen.start(doc.getStringList(), "");
	}
	
	public static void printDocStats() {
		doc.printInfo();
	}
	
	public static Boolean modifyDoc(Boolean mod) {
		
		if(doc.getSetLseq()) {
			setLseq(false);
		}
		
		doc.clear();
		doc.modify(mod);
		
		return doc.getModify();
	}
	
	public static Boolean setLseq(Boolean set) {
		
		if(doc.getModify()) {
			modifyDoc(false);
		}
		
		doc.clearLSEQ();
		doc.setLseq(set);
		
		return doc.getSetLseq();
	}
	
	public static void lseqForce(Boolean force, Boolean boundaryPlus) {
		doc.lseqForce(force, boundaryPlus);		
	}
	
	public static void printStrategy() {
		System.out.println("--- STRATEGY ---");
		System.out.println(Arrays.asList(LogootCRDT.getStrategy()));
		System.out.println("----------------");
	}
}
