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
	CRDTUtility utility = new CRDTUtility(clock, new ComponentGenerator());
	
	public LogootDocument(long siteId) {
		
		this.siteId = siteId;
		document = new Sequence();
	}
	
	public void initDocument() {
		
		document.sequenceArray.add(CRDTUtility.genStartAtom(siteId));
		document.sequenceArray.add(CRDTUtility.genStopAtom(siteId));
	}
	
	public void print() {
				
		for(SequenceAtom atom : document.sequenceArray) {

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
		
		Position posP = document.sequenceArray.get(document.sequenceArray.size() - 2).atomId.position;
		Position posQ = document.sequenceArray.get(document.sequenceArray.size() - 1).atomId.position;
		
		ArrayList<Position> newPos = CRDTUtility.generateLinePositions(posP, posQ, 1, site);
		
		for(Position pos : newPos) {
			document.sequenceArray.add(document.sequenceArray.size() - 1, CRDTUtility.genSequenceAtom(message, pos));
		}
	}
	
	private void orderDocument() {
		
		//iterate over position list and compare first integer and then site identifier to see if integer is equal
	}
}
