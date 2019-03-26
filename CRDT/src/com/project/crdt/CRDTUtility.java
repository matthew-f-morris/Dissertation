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

	private static Clock clock;
	private static ComponentGenerator maker;
	private static Position min; 
	private static Position max;
	
	public CRDTUtility(Clock clock, ComponentGenerator maker, long siteIdCopy) {
		
		CRDTUtility.clock = clock;
		CRDTUtility.maker = maker;

		min = maker.genPosition(maker.genIdentifierMax());
		min = maker.genPosition(maker.genIdentifierMin());		
	}
	
	public static SequenceAtom generate(String message, Position p, Position q, long siteId, Position build) throws Exception {	
		
		//return maker.genSequenceAtom((maker.genAtomIdentifier(generateLinePosition(p, q, siteId), clock.counter)), message);
		Position pos = new Position(generateLinePosition(p, q, siteId));		
		return maker.genSequenceAtom((maker.genAtomIdentifier(pos, clock.counter)), message);
	}
		
	private static ArrayList<Identifier> generateLinePosition(Position posP, Position posQ, long siteId) throws Exception {
					
		//takes in a position p and position q
		//checks to see if the number of id's are zero
		//if they are set them to the relative min and max positions
		//means if that one position is longer than the other, the algorithm
		//can compare the positions properly
				
		if(posP.ids.size() == 0) {
			posP = min;
		}
		
		if(posQ.ids.size() == 0) {
			posQ = max;
		}
		
		//compares the identifiers in the 1st position
		//if -1 (ie q is greater than p for both site and position)
		
		ArrayList<Identifier> build = new ArrayList<Identifier>(); 
		
		switch(compareIdentifier(posP.ids.get(0), posQ.ids.get(0))) {
			
			case -1:
								
				int interval = prefix(posQ) - prefix(posP);
				
				if(interval > 1) {

					build.add(maker.genIdentifier(randomInt(posP.ids.get(0).position, posQ.ids.get(0).position), siteId));
					return build;
				}
				
				else if(interval == 1 && siteId > posP.ids.get(0).siteId) {
					
					build.add(maker.genIdentifier(posP.ids.get(0).position, siteId));
					return build;
					
				} else {
					
					build.add(posP.ids.get(0));
					
					posP.ids.subList(1, posP.ids.size());
					posQ.ids.subList(1, posQ.ids.size());
					
					build.addAll(generateLinePosition(posP, posQ, siteId));
					return build;
				}
			
			case 0:
				
				build.add(posP.ids.get(0));
				
				posP.ids.subList(1, posP.ids.size());
				posQ.ids.subList(1, posQ.ids.size());
				
				build.addAll(generateLinePosition(posP, posQ, siteId));
				return build;
				
//				ArrayList<Identifier> idP = new ArrayList<Identifier>(posP.ids.subList(1, posP.ids.size()));
//				ArrayList<Identifier> idQ = new ArrayList<Identifier>(posQ.ids.subList(1, posQ.ids.size()));
//				
//				posP.ids.addAll(idP);
//				posQ.ids.addAll(idQ);
//				
//				return generateLinePosition(posP, posQ, siteId, build);
			
			case 1:				
				
				throw new Exception("Position Q was less than Position P!");
		}
		
		return build;
	}
	
//	private static Position genLinePosition(Position tempP, Position tempQ, long siteId) throws Exception {
//		
//		
//		Position posP = new Position(tempP.copy());
//		Position posQ = new Position(tempQ.copy());
//				
////		if(sizeP != sizeQ)
////			if(sizeP > sizeQ)
////				posP.ids.add(imin);
////			else
////				posP.ids.add(imax);
//		Position newPos = posP.ids.get(0).position == 0 ? new Position() : new Position(tempP.ids);
//				
//		posP.ids.add(imin);
//		posQ.ids.add(imax);
//		
//		int sizeP = posP.ids.size();
//		int sizeQ = posQ.ids.size();
//		
////		printPosition(posP);
////		System.out.println();
////		printPosition(posQ);
////		System.out.println();
//			
//		loop: for(int i = 0; i < Math.min(sizeP, sizeQ); i++) {
//			
//			//System.out.println("MAX: " + Math.max(sizeP, sizeQ));
//			
//			switch(compareIdentifier(posP.ids.get(i), posQ.ids.get(i))) {
//			
//			case -1:			
//				int interval = prefix(posQ) - prefix(posP);
//				
//				if(interval > 1) {
//					newPos.ids.add(maker.genIdentifier(randomInt(posP.ids.get(i).position, posQ.ids.get(i).position), siteId));
//					break loop;
//				}
//					
//				if(interval == 1 && siteId > posP.ids.get(i).siteId) {
//					newPos.ids.add(maker.genIdentifier(posP.ids.get(i).position, siteId));
//					break loop;
//				}
//				
//				newPos.ids.add(posP.ids.get(i));
//				continue;
//				
////				else
//					
////					ArrayList<Identifier> idP = new ArrayList<Identifier>(posP.ids.subList(1, posP.ids.size()));
////					ArrayList<Identifier> idQ = new ArrayList<Identifier>(posQ.ids.subList(1, posQ.ids.size()));
////					
////					posP.ids.addAll(idP);
////					posQ.ids.addAll(idQ);
////					
////					return generateLinePosition(posP, posQ, siteId);
//						
//			case 1:
//				
//				newPos.ids.add(posP.ids.get(i));
//				continue;
//			
//			case 0:				
//				
//				throw new Exception("Position Q was less than Position P!");
//			}
//		}
//		
//		return newPos;
		
		
		
//		if(posP.ids.size() == 0)
//			posP = min;
//		
//		if(posQ.ids.size() == 0)
//			posQ = max;
//		
//		switch(compareIdentifier(posP.ids.get(0), posQ.ids.get(0))) {
//		
//		case 1:
//			
//			//this is good, means there might be a case where you can put a position between the two!
//			int interval = prefix(posQ) - prefix(posP);			
//			
//			if(interval > 1)
//				
//				//make new identifier [rand(between p.id.get(0).position and )]
//				//add it to newP, use this site id
//				
//				newPos.ids.add(maker.genIdentifier(randomInt(posP.ids.get(0).position, posQ.ids.get(0).position), siteId));
//			
//			else if(interval == 1 && siteId > posP.ids.get(0).siteId)
//				
//				//if the interval == 1 but the current site has a higher site id, use the interval of the lower position and the site id. 
//				
//				newPos.ids.add(maker.genIdentifier(posP.ids.get(0).position, siteId));
//			
//			else {
//				
//				//try again with the next pair of identifiers in the list
//				
////				ArrayList<Identifier> idP = new ArrayList<Identifier>(posP.ids.subList(1, posP.ids.size()));
////				ArrayList<Identifier> idQ = new ArrayList<Identifier>(posQ.ids.subList(1, posQ.ids.size()));
////				
////				posP.ids.addAll(idP);
////				posQ.ids.addAll(idQ);
////				
////				return generateLinePosition(posP, posQ, siteId);
//				
//				
//				
//			}
//			
//		case 0:
//			
//			
//			
//		case -1:
//			
//			// #Fail
//			System.out.println("CONGRATULATIONS YOU WIN NOTHING, GOOD DAY!");
//			throw new Exception("Q was less than P");
//		}
//		
//		return new Position();
//	}
	
	private static int prefix(Position pos) {
		return pos.ids.get(0).position;
	}
	
//	private static int comparePosition(Position p, Position q) {
//		
//		int lenP = p.ids.size();
//		int lenQ = q.ids.size();
//		
//		if(lenP == 0 && lenQ == 0)
//			return 0;		
//		if(lenP == 0)		
//			return -1;		
//		if(lenQ == 0)
//			return 1;
//		
//		int result = compareIdentifier(p.ids.get(0), q.ids.get(0));
//		
//		if(result == 1)
//			return 1;
//		if(result == -1)
//			return -1;
//		if(result == 0);
//		
//		ArrayList<Identifier> idP = new ArrayList<Identifier>(p.ids.subList(1, p.ids.size())); 
//		ArrayList<Identifier> idQ = new ArrayList<Identifier>(q.ids.subList(1, q.ids.size())); 
//				
//			return comparePosition(maker.genPosition(idP), maker.genPosition(idQ));	
//	}
	
	//compares the identifiers
	//returns -1 if the position and site of q (the position higher up the sequence) is greater than the position and site of p
	//returns 1 if the position and site of p is greater than the position of and site of q
	
	private static int compareIdentifier(Identifier p, Identifier q) {
		
		if(p.position > q.position)
			return 1;
		if(p.position < q.position)
			return -1;
		if(p.siteId > q.siteId)
			return 1;
		if(p.siteId < q.siteId)
			return -1;
		return 0;
		
//		int posComparison = compareIdentifierPositions(p.position, q.position);
//		int siteComparison = compareIdentifierSites(p.position, q.position);;
//		
//		if(posComparison == 1 && siteComparison == 1)
//			return 1;
//		
//			// 	this is incorrect, Q > P for both site and position THIS IS CORRECT!!!!!!!!!
//			
//		else if(posComparison == -1 && siteComparison == -1)
//			return -1;
//		
//		else if(posComparison == -1 && siteComparison == 1)
//			
//			//P has a greater identifier but a lower site Id
//			
//			//	[
//			//		p [[[[27908, 6746016110688904592]], 2], YAA],
//			//		q [[[[19836, 7988293892253055526]], 3], Hi],
//			//	]
//			return -1;
//		
//		else if(posComparison == 1 && siteComparison == -1)
//			
//			//P has a lower identifier (correct) but a higher siteId
//			
//			//	[
//			//		p [[[[27908, 8746016110688904592]], 2], YAA],
//			//		q [[[[29836, 7988293892253055526]], 3], Hi],
//			//	]
//			
//			return 1;
//		
//		// something has gone horribly wrong
//		return 0;
//			
//			//	Either site or identifier are not equal
//		
//			//	[
//			//		p [[[[27908, 6746016110688904592]], 2], YAA],
//			//		q [[[[29836, 7988293892253055526]], 3], Hi],
//			//	]		
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
		return rand.nextInt(max - min - 1) + min + 1;
	}
	
	public static SequenceAtom genStartAtom(long siteId) {

		Position pMin = maker.genPosition(maker.genIdentifierMin());
		AtomIdentifier atom = maker.genAtomIdentifier(pMin, clock.counter);
		clock.increment();
		return maker.genSequenceAtom(atom);
	}
	
	public static SequenceAtom genStopAtom(long siteId) {

		Position pMax = maker.genPosition(maker.genIdentifierMax());
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
		
//		int length = doc.arr.size();
		
		doc.arr.add(doc.arr.size() - 1, atom);
		
//		for(int i = 0; i < length; i++) {
//			
//			int comparePrev = comparePosition(atom.atomId.position, doc.arr.get(i).atomId.position);
//			int compareNext = comparePosition(atom.atomId.position, doc.arr.get(i+1).atomId.position);
//			
//			if(comparePrev == 1 && compareNext == -1) {
//				doc.arr.add(i + 1, atom);
//			} else if(comparePrev == 1 && compareNext == 1) {
//				continue;
//			} else if((comparePrev == -1 && compareNext == 1) || (comparePrev == -1 && compareNext == -1)) {
//				throw new Error("Document is not ordered properly!");
//			} 
//		}
	}
	
	public static void printIdentifier(Identifier ident) {
		
		System.out.println(ident);
	}
	
	public static void printPosition(Position pos) {
		
		System.out.println(pos);
	}
	
	public static void printAtomIdentifier(AtomIdentifier atom) {
		
		System.out.println(atom);
	}
	
	public static void printSequenceAtom(SequenceAtom seqAtom) {
		
		System.out.println(seqAtom);
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
}


