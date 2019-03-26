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
	public Sequence document;
	Clock clock = new Clock();
	CRDTUtility utility = new CRDTUtility(clock, new ComponentGenerator(), siteId);
	
	public LogootDocument(long siteId) {
		
		this.siteId = siteId;
		document = new Sequence();
	}
	
	public void initDocument() {
		
		document.arr.add(CRDTUtility.genStartAtom(siteId));
		document.arr.add(CRDTUtility.genStopAtom(siteId));
	}
	
	public void print() {
				
		for(SequenceAtom atom : document.arr) {

			System.out.print("Position Identifier: [");			
			for(int i = 0; i < atom.atomId.position.ids.size(); i++) {
				
				Identifier ident = atom.atomId.position.ids.get(i);				
				
				if(i < atom.atomId.position.ids.size()) {
					System.out.println("[[" + ident.position + ", " + ident.siteId + "], " + atom.atomId.clock + "], " + atom.message + "]");
				} else
					System.out.println("[[" + ident.position + ", " + ident.siteId + "]");				
			}
		}
	}
	
	public void addMessage(String message, long site) throws Exception {
		
		Position posP = document.arr.get(document.arr.size() - 2).atomId.position;
		Position posQ = document.arr.get(document.arr.size() - 1).atomId.position;
		
		Position build = new Position();
		
		SequenceAtom atom = CRDTUtility.generate(message, new Position(posP.copy()), new Position(posQ.copy()), site, build);
		clock.increment();
		
		CRDTUtility.printSequenceAtom(atom);		
		CRDTUtility.addToSequence(document, atom);
	}
	
	public void print(SequenceAtom atom) {
		
		System.out.print("[");
		for(Identifier i : atom.atomId.position.ids) {
			System.out.print(i.position + ", ");
		}
		System.out.println("]");
	}
}
