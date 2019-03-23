package com.project.crdt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.project.clock.Clock;
import com.project.datatypes.AtomIdentifier;
import com.project.datatypes.Identifier;
import com.project.datatypes.Position;
import com.project.datatypes.SequenceAtom;

public class CRDTUtility {
	
	static Random rand = new Random();
	private static Clock clock;
	private static ComponentGenerator maker;
	
	public CRDTUtility(Clock clock, ComponentGenerator maker) {
		CRDTUtility.clock = clock;
		CRDTUtility.maker = maker;
	}
		
	public static Position generateLinePosition(Position posP, Position posQ, long siteId) throws Exception {
				
		int index = -1;
		int interval = 0;
		
		while(interval < 1) {			
			index++;
			interval = prefix(posQ, index) - prefix(posP, index);
		}
		
		int r = prefix(posP, index);
		return constructPosition(r, posP, posQ, siteId);
	}
	
	private static Position constructPosition(int index, Position posP, Position posQ, long oldId) throws Exception {

		Position position = null;
		
		if(index > posP.ids.size() || index > posQ.ids.size()) {			
			int result = prefix(posP, index) - prefix(posQ, index);
			
		}
		
		return position;
		
		//if(posP.ids.size())
		
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
	}

	private static int prefix(Position pos, int index) {
		
		if(pos.ids.size() < index)
			return 0;
		else {
			return pos.ids.get(index).position;
		}
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
			return 1;
		else if(posComparison == -1 && siteComparison == -1)
			return -1;		
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
		
		Identifier min = maker.genIdentifierMin(siteId);
		Position pMin = maker.genPosition(min);
		AtomIdentifier atom = maker.genAtomIdentifier(pMin, clock.counter);
		clock.increment();
		return maker.genSequenceAtom(atom);
	}
	
	public static SequenceAtom genStopAtom(long siteId) {
		
		Identifier max = maker.genIdentifierMax(siteId);
		Position pMax = maker.genPosition(max);
		AtomIdentifier atom = maker.genAtomIdentifier(pMax, clock.counter);
		clock.increment();
		return maker.genSequenceAtom(atom);
	}
	
	public static SequenceAtom genSequenceAtom(String message, Position p) {
		
		AtomIdentifier atom = maker.genAtomIdentifier(p, clock.counter);
		clock.increment();
		return maker.genSequenceAtom(atom, message);
	}
}


