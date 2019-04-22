package com.project.crdt;

import java.util.ArrayList;

import com.project.clock.Clock;
import com.project.clock.VersionVector;
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
	private Boolean setLseq = false;
	private int printNo = 15;
	private long totalAddTime = 0L;
	private long totalInsertTime = 0L;
	private long lastInsertTime = 0L;
	private int boundary = 10;
	private Boolean force = false;
	private Boolean boundaryPlus = false;
	
	LogootCRDT logoot = new LogootCRDT(boundary);
	
	public LogootDocument(long siteId) {
		
		this.siteId = siteId;
		document = new Sequence();
		initDocument();
	}
	
	private void initDocument() {
		
		//creates the start and stop atoms that are necessary for the logoot document and algorithm to work
		document.arr.add(CRDTUtility.genStartAtom());
		document.arr.add(CRDTUtility.genStopAtom());
	}
	
	public void initDocumentLSEQ() {
				
		document.arr.add(CRDTUtility.genStartAtomLseq());
		document.arr.add(CRDTUtility.genStopAtomLseq());
	}
	
	public Sequence getSequence() {
		return document;
	}
	
	public SequenceAtom addMessage(String message, long site) throws Exception {
		
		//adds a new message at the end of the document, this could be modified to add
		//a message at any position. However, for this use case, messages are only added
		//to the end of a document, because this is a chat... you cannae just add messages
		//to random parts of the document
		
		Position posP = document.arr.get(document.arr.size() - 2).atomId.position;
		Position posQ = document.arr.get(document.arr.size() - 1).atomId.position;
		
		long startTime;
		long endTime;		
			
		startTime = System.nanoTime();		
		SequenceAtom atom = LogootCRDT.generate(message, new Position(posP.copy()), new Position(posQ.copy()), site, modify, setLseq, force, boundaryPlus);
		endTime = System.nanoTime();
		totalAddTime += endTime - startTime;

		startTime = System.nanoTime();
		boolean success = CRDTUtility.insertSequenceAtom(document.arr, atom);
		
		if(success) {
			VersionVector.increment();
		}
		
		endTime = System.nanoTime();
		lastInsertTime = endTime - startTime;
		totalInsertTime += endTime - startTime;
		
		return atom;
	}
	
	public boolean insertIntoDocument(SequenceAtom atom) {
		
		try {									
			boolean success = CRDTUtility.insertSequenceAtom(document.arr, atom);
			
			if(success)
				VersionVector.increment();
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
	
	public void setLseq(Boolean set) {
		this.setLseq = set;
	}
	
	public void lseqForce(Boolean force, Boolean boundaryPlus) {
		this.force = force;
		this.boundaryPlus = boundaryPlus;
	}
	
	public void clear() {
		document.arr.clear();
		initDocument();
		setLseq(false);
	}
	
	public void clearLSEQ() {
		document.arr.clear();
		initDocumentLSEQ();
		setLseq(true);
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
	
	public void printDocSnippet(int lines) {
		
		int sizeMax = getSizeOfPos(getMaxPosition());
		
		if(sizeMax > 20)
			
			System.out.println("\nUnable to print document due to oversized ID's!\n");
		
		else {
		
			ArrayList<SequenceAtom> seq = document.arr;
			
			System.out.println("\n--- START OF DOCUMENT ---\n");
			
			if(lines > seq.size())
				lines = seq.size();
			for(int i = seq.size() - lines; i < seq.size(); i++) {
				System.out.println(seq.get(i).toString());
			}
			
			System.out.println("\n---- END OF DOCUMENT ----\n");
		}
	}
	
	public void printInfo() {
		
		int sizeMax = getSizeOfPos(getMaxPosition());
		
		System.out.println("---- DATA ----");
		System.out.println("Doc Size (Excluding Start/Stop): " + docSize());
		
		if(sizeMax > 30)
			System.out.println("Max Position ID: ID is too big to print!");
		else		
			System.out.println("Max Position ID: " + getMaxPosition().toString());
		
		System.out.println("Max Position ID Size: " + getSizeOfPos(getMaxPosition()));
		System.out.println("Average Position ID size: " + getAverageIdLength());
		System.out.println("Size of Document in Bytes: " + 0);
		System.out.println("Total time taken to add messages (ms): " + (float) totalAddTime / 1000000);
		System.out.println("Average time taken to add messages (ms): " + (float) totalAddTime / (docSize() * 1000000));
		System.out.println("Total time taken to insert messages (ms): " + (float) totalInsertTime / 1000000);	
		System.out.println("Average time taken to insert messages (ms): " + (float) totalInsertTime / (docSize() * 1000000));
		System.out.println("Time taken for last addition (ms): " + (float) lastInsertTime / 1000000);
		System.out.println("");
	}
	
	public ArrayList<String> getStringList() {

		ArrayList<String> strings = new ArrayList<String>();
		
		for(SequenceAtom atom : document.arr) {
			strings.add(atom.toString() + "");
		}
		
		return strings;
	}
	
	public Boolean getModify() {
		return modify;
	}
	
	public Boolean getSetLseq() {
		return setLseq;
	}
}