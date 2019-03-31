package com.project.crdt;

import java.util.ArrayList;

import com.project.clock.Clock;
import com.project.datatypes.Position;
import com.project.datatypes.Sequence;
import com.project.datatypes.SequenceAtom;
import com.project.util.CRDTUtility;

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
	
	public void initDocument() {
		
		document.arr.add(CRDTUtility.genStartAtom(siteId));
		document.arr.add(CRDTUtility.genStopAtom(siteId));
	}
	
	public void print() {
		
		System.out.println("--- START OF DOCUMENT ---\n");
		
		for(SequenceAtom at : document.arr) {
			System.out.println(at.toString());
		}
		
		System.out.println("\n---- END OF DOCUMENT ----\n\n");
	}
	
	public void print(int lines) {
		
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
	
	public void addMessage(String message, long site) throws Exception {
		
		Position posP = document.arr.get(document.arr.size() - 2).atomId.position;
		Position posQ = document.arr.get(document.arr.size() - 1).atomId.position;
		
		SequenceAtom atom = LogootCRDT.generate(message, new Position(posP.copy()), new Position(posQ.copy()), site, modify);
		Clock.increment();
		CRDTUtility.insertSequenceAtom(document.arr, atom);
	}
	
//	private void print(SequenceAtom atom) {
//		System.out.println(atom.toString());
//	}
	
	public void modify(Boolean mod) {
		this.modify = mod;
	}
	
	public void clear() {
		document.arr.clear();
		initDocument();
	}
	
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
	private int docSize() {
		return document.arr.size() - 2;
	}
}


//int i;
//
//if(document.arr.size() == 2) {
//	i = 0;
//} else
//	i = CRDTUtility.randomInt(0, document.arr.size() - 1);		
//
//Position posP = document.arr.get(i).atomId.position;
//Position posQ = document.arr.get(i + 1).atomId.position;=
