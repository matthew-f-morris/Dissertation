package com.project.crdt;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import com.project.clock.Clock;
import com.project.datatypes.Position;
import com.project.datatypes.Sequence;
import com.project.datatypes.SequenceAtom;
import com.project.utils.CRDTUtility;

//a document class that represents the chat by using an array of sequence atoms
//exposes methods that enable the crdt controller to add messages, print the state of the
//crdt, calculates some metrics and initialised the document 

public class LogootDocument {
	
	private long siteId;
	private Sequence document;
	private Boolean modify = false;
	private int printNo = 15;
	
	LogootCRDT logoot = new LogootCRDT();
	
	public LogootDocument(long siteId) {
		
		this.siteId = siteId;
		document = new Sequence();
		initDocument();
	}
	
	private void initDocument() {
		
		//creates the start and stop atoms that are necessary for the logoot document and algorithm to work
		
		document.arr.add(CRDTUtility.genStartAtom(siteId));
		document.arr.add(CRDTUtility.genStopAtom(siteId));
	}	
	
	public SequenceAtom addMessage(String message, long site) throws Exception {
		
		//adds a new message at the end of the document, this could be modified to add
		//a message at any position. However, for this use case, messages are only added
		//to the end of a document, because this is a chat... you cannae just add messages
		//to random parts of the document
		
		Position posP = document.arr.get(document.arr.size() - 2).atomId.position;
		Position posQ = document.arr.get(document.arr.size() - 1).atomId.position;
		
		SequenceAtom atom = LogootCRDT.generate(message, new Position(posP.copy()), new Position(posQ.copy()), site, modify);
		Clock.increment();
		CRDTUtility.insertSequenceAtom(document.arr, atom);
		
		return atom;
	}
	
	public boolean insertIntoDocument(SequenceAtom atom) {
		
		try {
			CRDTUtility.insertSequenceAtom(document.arr, atom);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	//The Logoot CRDT algorithm can be modified to minimise the number of positions in its identifiers
	//This takes advantage of the fact that the messages are always added at the end of a document, therefor
	//Each identifier doesnt need to leave space before it to add more identifiers, so it uses the least
	//Amount of space possible.
	
	public void modify(Boolean mod) {
		this.modify = mod;
	}
	
	public void clear() {
		document.arr.clear();
		initDocument();
	}
	
	//The following methods mearly calculate some metrics about the document and print the state of
	//The document
	
	private Position getMaxPosition() {
		
		ArrayList<SequenceAtom> seq = document.arr;
		
		int index = 0;
		int size = 0;
		
		for(int i = 1; i < seq.size() - 1; i++) {			
			int tempSize = seq.get(i).atomId.position.ids.size();
			if(tempSize >= size) {
				index = i;
				size = tempSize;
			}
		}
		
		return seq.get(index).atomId.position;
	}
	
	private int getSizeOfPos(Position p) {
		return p.ids.size();
	}
	
	private float getAverageIdLength() {

		int size = document.arr.size();
		int sum = 0;
		
		for(SequenceAtom at : document.arr) {
			sum += at.atomId.position.ids.size();
		}
		
		return (float) sum/size;
	}
	
	public int docSize() {
		return document.arr.size() - 2;
	}
	
	private void print() {
		
		System.out.println("--- START OF DOCUMENT ---\n");
		
		for(SequenceAtom at : document.arr) {
			System.out.println(at.toString());
		}
		
		System.out.println("\n---- END OF DOCUMENT ----\n\n");
	}
	
	public String getDocumentString() {
		return toString();
	}
	
	private void print(int lines) {
		
		ArrayList<SequenceAtom> seq = document.arr;
		
		System.out.println("--- START OF DOCUMENT ---\n");
		
		if(lines > seq.size())
			lines = seq.size();
		for(int i = seq.size() - lines; i < seq.size(); i++) {
			System.out.println(seq.get(i).toString());
		}
		
		System.out.println("\n---- END OF DOCUMENT ----\n");
	}
	
	public void printInfo() {
		
		int sizeMax = getSizeOfPos(getMaxPosition());
		
		if(sizeMax > 20)
			System.out.println("Unable to print document due to oversized ID's!\n");
		else 
			print(printNo);
		
		System.out.println("---- DATA ----\n");
		System.out.println("Doc Size (Excluding Start/Stop): " + docSize());
		
		if(sizeMax > 30)
			System.out.println("Max Position ID: ID is too big to print!");
		else		
			System.out.println("Max Position ID: " + getMaxPosition().toString());
		
		System.out.println("Max Position ID Size: " + getSizeOfPos(getMaxPosition()));
		System.out.println("Average Position ID size: " + getAverageIdLength());
		System.out.println("Size of Document in Bytes: " + 0);
		System.out.println("\n");
	}
	
	private void print(SequenceAtom atom) {
		System.out.println(atom.toString());
	}
	
	public ArrayList<String> getStringList() {

		ArrayList<String> strings = new ArrayList<String>();
		
		for(SequenceAtom atom : document.arr) {
			strings.add(atom.toString() + "\r\n");
		}
		
		return strings;
	}
}