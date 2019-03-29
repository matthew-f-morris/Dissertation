package com.project.crdt;

import com.project.clock.Clock;
import com.project.datatypes.Position;
import com.project.datatypes.Sequence;
import com.project.datatypes.SequenceAtom;

public class LogootDocument {
	
	private long siteId;
	private Sequence document;
	
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
		
		for(SequenceAtom at : document.arr) {
			System.out.println(at.toString());
		}
	}
	
	public void addMessage(String message, long site) throws Exception {
		
		Position posP = document.arr.get(document.arr.size() - 2).atomId.position;
		Position posQ = document.arr.get(document.arr.size() - 1).atomId.position;
		
		SequenceAtom atom = LogootCRDT.generate(message, new Position(posP.copy()), new Position(posQ.copy()), site);
		Clock.increment();
		CRDTUtility.addToSequence(document, atom);
	}
	
	private void print(SequenceAtom atom) {
		
		System.out.println(atom.toString());
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
//Position posQ = document.arr.get(i + 1).atomId.position;
