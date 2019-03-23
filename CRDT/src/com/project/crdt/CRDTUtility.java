package com.project.crdt;

import java.util.ArrayList;
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
		
	public static ArrayList<Position> generateLinePositions(Position posP, Position posQ, int numberLines, long siteId) throws Exception {
		
		ArrayList<Position> positions = new ArrayList<Position>();
		
		int index = -1;
		int interval = 0;
		
		while(interval < numberLines) {			
			index++;
			interval = prefix(posQ, index) - prefix(posP, index);
		}
		
		int step = interval / numberLines;
		int r = prefix(posP, index);
		
		for(int i = 0; i < numberLines; i++) {			
			
			int random = rand.nextInt(step - 1) + 1;
			
			positions.add(constructPosition(r, random, posP, posQ, siteId));
			r = r + step;			
		}
		
		return positions;
	}
	
//	public static Position generateLinePosition(Position posP, Position posQ, String siteId) {
//		
//		int interval = 0;
//		int index = -1;
//		
//		while(interval < 1) {
//			index++;
//			interval = prefix(posQ, index) - prefix(posP, index);
//			System.out.println("Interval: " + interval + "\n");			
//		}
//		
//		int r = prefix(posP, index);
//		return constructPosition(r, posP, posQ, siteId);		
//	}
	
	private static Position constructPosition(int r, int random, Position posP, Position posQ, long oldId) throws Exception {
		
		//integer r = random value
		//posp and pos q are the two arrays of identifiers for the sequence atoms which we want to put a new sequence atom between		
		
				
		
		//if(random)
		
		System.out.println("R: " + r);
		
		Position position = new Position();
		
		for(int j = 0; j < posP.ids.size(); j++) {
			
			long siteId = oldId;
			int pos = j;
			
			if(j == posP.ids.size()) {
				siteId = oldId;
			} else if(r == posP.ids.get(j).position) {
				siteId = posP.ids.get(j).siteId;
			} else if(r == posQ.ids.get(j).position) {
				siteId = posQ.ids.get(j).siteId;
			} else {
				siteId = oldId;
			}
			
//			if(j < posP.ids.size()) {
//				pos = r;
//			}
			
			Identifier id = maker.genIdentifier(r, siteId);
			position.ids.add(id);
		}		
		
		return position;
	}

	private static int prefix(Position pos, int index) {
		
		if(pos.ids.size() < index)
			return 0;
		else {
			return pos.ids.get(index).position;
		}
	}
	
	//returns 1 if A > B, -1 if A < B, 0 if equal
	
	private static int compareIdentifiers(Identifier a, Identifier b) {
		
		if(a.position > b.position)
			return 1;
		else if(b.position > a.position)
			return -1;
		//else if()
		
		return 0;
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







/*
// *	Compare atom identifiers, returns 1 if x > y, returns -1 if y > x,
// * 	returns 0 if x == y 
// */
//
//public static int compareAtomIdentifiers(AtomIdentifier x, AtomIdentifier y) {
//	System.out.println("ur mum");
//	return comparePos(x.position, y.position);
//}
//
//public static int comparePosition(Position posx, Position posy) {
//	// implement 
//}
//
///*
// *	Implementation of generateLinePositions(p, q, N, s) from paper
// */


