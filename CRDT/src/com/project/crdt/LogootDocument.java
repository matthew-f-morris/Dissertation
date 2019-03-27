package com.project.crdt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import com.project.clock.Clock;
import com.project.datatypes.Identifier;
import com.project.datatypes.Position;
import com.project.datatypes.Sequence;
import com.project.datatypes.SequenceAtom;

public class LogootDocument {
	
	private long siteId;
	private Sequence document;
	Clock clock = new Clock();
	CRDTUtility utility = new CRDTUtility(clock, new ComponentGenerator(), siteId);
	
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
				
		System.out.println("\nDOCUMENT: \n");
		
		for(SequenceAtom at : document.arr) {
			System.out.println(at.toString());
		}
	}
	
	public void addMessage(String message, long site) throws Exception {
		
		Position posP = document.arr.get(document.arr.size() - 2).atomId.position;
		Position posQ = document.arr.get(document.arr.size() - 1).atomId.position;
		
		SequenceAtom atom = CRDTUtility.generate(message, new Position(posP.copy()), new Position(posQ.copy()), site);
		clock.increment();
		
		CRDTUtility.addToSequence(document, atom);
	}
	
	private void print(SequenceAtom atom) {
		
		System.out.println(atom.toString());
	}
}
