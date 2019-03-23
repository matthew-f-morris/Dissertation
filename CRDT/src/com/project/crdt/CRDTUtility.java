package com.project.crdt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.project.clock.Clock;
import com.project.datatypes.AtomIdentifier;
import com.project.datatypes.Identifier;
import com.project.datatypes.Position;
import com.project.datatypes.Sequence;
import com.project.datatypes.SequenceAtom;

public class CRDTUtility {
	
	static Random rand = new Random();
	private long siteIdCopy;
	private static Clock clock;
	private static ComponentGenerator maker;
	private static Position min; 
	private static Position max; 
	
	public CRDTUtility(Clock clock, ComponentGenerator maker, long siteIdCopy) {
		
		CRDTUtility.clock = clock;
		CRDTUtility.maker = maker;

		min = maker.genPosition(maker.genIdentifierMax(siteIdCopy));
		min = maker.genPosition(maker.genIdentifierMin(siteIdCopy));		
	}
	
	public static SequenceAtom generate(String message, Position p, Position q, long siteId) throws Exception {
		
		return maker.genSequenceAtom((maker.genAtomIdentifier(generateLinePosition(p, q, siteId), clock.counter)), message);
	}
		
	private static Position generateLinePosition(Position posP, Position posQ, long siteId) throws Exception {
						
		if(posP.ids.size() == 0) {
			posP = min;
		}
		
		if(posQ.ids.size() == 0) {
			posQ = max;
		}
		
		switch(compareIdentifier(posP.ids.get(0), posQ.ids.get(0))) {
			
			case -1:
				int interval = prefix(posQ) - prefix(posP);
				
				if(interval > 1)
					return maker.genPosition(maker.genIdentifier(randomInt(posP.ids.get(0).position, posQ.ids.get(0).position), siteId));
				
				else if(interval == 1 && siteId > posP.ids.get(0).siteId)
					return maker.genPosition(maker.genIdentifier(posP.ids.get(0).position, siteId));
				
				else {
					
					ArrayList<Identifier> idP = new ArrayList<Identifier>(posP.ids.subList(1, posP.ids.size()));
					ArrayList<Identifier> idQ = new ArrayList<Identifier>(posQ.ids.subList(1, posQ.ids.size()));
					
					posP.ids.addAll(idP);
					posQ.ids.addAll(idQ);
					
					return generateLinePosition(posP, posQ, siteId);
				}
			
			case 0:
				ArrayList<Identifier> idP = new ArrayList<Identifier>(posP.ids.subList(1, posP.ids.size()));
				ArrayList<Identifier> idQ = new ArrayList<Identifier>(posQ.ids.subList(1, posQ.ids.size()));
				
				posP.ids.addAll(idP);
				posQ.ids.addAll(idQ);
				
				return generateLinePosition(posP, posQ, siteId);
			
			case 1:				
				throw new Exception("Position Q was less than Position P!");
		}
		
		return null;
	}
	
//	private static Position constructPosition(int index, Position posP, Position posQ, long oldId) throws Exception {
//
//		Position position = null;
//		
//		if(index > posP.ids.size() || index > posQ.ids.size()) {			
//			int result = prefix(posP, index) - prefix(posQ, index);
//			
//		}
//		
//		return position;
//		
//		//if(posP.ids.size())
//		
//		r += random;		
//		Position position = new Position();
//		
//		for(int j = 0; j < posP.ids.size(); j++) {
//			
//			long siteId = oldId;
//			int pos = j;
//			
//			if(j == posP.ids.size()) {
//				siteId = oldId;
//			} else if(r == posP.ids.get(j).position) {
//				siteId = posP.ids.get(j).siteId;
//			} else if(r == posQ.ids.get(j).position) {
//				siteId = posQ.ids.get(j).siteId;
//			} else {
//				siteId = oldId;
//			}
//			
//			if(j < posP.ids.size()) {
//				pos = r;
//			}
//			
//			Identifier id = maker.genIdentifier(r, siteId);
//			position.ids.add(id);
//		}		
//		
//		return position;
//	}
//
//	private static int prefix(Position pos, int index) {
//		
//		if(pos.ids.size() < index)
//			return 0;
//		else {
//			return pos.ids.get(index).position;
//		}
//	}
	
	private static int prefix(Position pos) {
		return pos.ids.get(0).position;
	}
	
	private static int comparePosition(Position p, Position q) {
		
		int lenP = p.ids.size();
		int lenQ = q.ids.size();
		
		if(lenP == 0 && lenQ == 0)
			return 0;		
		if(lenP == 0)		
			return -1;		
		if(lenQ == 0)
			return 1;
		
		int result = compareIdentifier(p.ids.get(0), q.ids.get(0));
		
		if(result == 1)
			return 1;
		if(result == -1)
			return -1;
		if(result == 0);
		
		ArrayList<Identifier> idP = new ArrayList<Identifier>(p.ids.subList(1, p.ids.size())); 
		ArrayList<Identifier> idQ = new ArrayList<Identifier>(q.ids.subList(1, q.ids.size())); 
				
			return comparePosition(maker.genPosition(idP), maker.genPosition(idQ));	
	}
	
	private static int compareIdentifier(Identifier p, Identifier q) {
		
		int posComparison = compareIdentifierPositions(p.position, q.position);
		int siteComparison = compareIdentifierSites(p.position, q.position);;
		
		if(posComparison == 1 && siteComparison == 1)
			return -1;
		else if(posComparison == -1 && siteComparison == -1)
			return 1;		
		return 0;
	}
		
	private static int compareIdentifierPositions(int a, int b) {
				
		if(a > b)
			return -1;
		else if(b > a)
			return 1;		
		return 0;
	}
	
	private static int compareIdentifierSites(long a, long b) {
		
		if(a > b)
			return -1;
		else if(b > a)
			return 1;
		return 0;
	}
	
	private static int randomInt(int min, int max) {
		return rand.nextInt(max - min -1) + min + 1;
	}
	
	public static SequenceAtom genStartAtom(long siteId) {

		Position pMin = maker.genPosition(maker.genIdentifierMin(siteId));
		AtomIdentifier atom = maker.genAtomIdentifier(pMin, clock.counter);
		clock.increment();
		return maker.genSequenceAtom(atom);
	}
	
	public static SequenceAtom genStopAtom(long siteId) {

		Position pMax = maker.genPosition(maker.genIdentifierMax(siteId));
		AtomIdentifier atom = maker.genAtomIdentifier(pMax, clock.counter);
		clock.increment();
		return maker.genSequenceAtom(atom);
	}
	
	public static SequenceAtom genSequenceAtom(String message, Position p) {
		
		AtomIdentifier atom = maker.genAtomIdentifier(p, clock.counter);
		clock.increment();
		return maker.genSequenceAtom(atom, message);
	}
	
	public static void addToSequence(Sequence doc, SequenceAtom atom) {
		
		int length = doc.arr.size();
		
		for(int i = 0; i < length; i++) {
			
			int comparePrev = comparePosition(atom.atomId.position, doc.arr.get(i).atomId.position);
			int compareNext = comparePosition(atom.atomId.position, doc.arr.get(i+1).atomId.position);
			
			if(comparePrev == 1 && compareNext == -1) {
				doc.arr.add(i + 1, atom);
			} else if(comparePrev == 1 && compareNext == 1) {
				continue;
			} else if((comparePrev == -1 && compareNext == 1) || (comparePrev == -1 && compareNext == -1)) {
				throw new Error("Document is not ordered properly!");
			} 
		}
	}
}


