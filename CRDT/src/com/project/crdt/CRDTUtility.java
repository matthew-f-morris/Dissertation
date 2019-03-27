package com.project.crdt;

import java.util.List;
import java.util.ArrayList;
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
	
	public CRDTUtility(Clock clock, ComponentGenerator maker, long siteIdCopy) {
		
		CRDTUtility.clock = clock;
		CRDTUtility.maker = maker;	
	}
	
	public static SequenceAtom generate(String message, Position p, Position q, long siteId) throws Exception {	
		
		//return maker.genSequenceAtom((maker.genAtomIdentifier(generateLinePosition(p, q, siteId), clock.counter)), message);
		Position pos = new Position(generateLinePosition(p, q, siteId));		
		return maker.genSequenceAtom((maker.genAtomIdentifier(pos, clock.counter)), message);
	}
		
	private static List<Identifier> generateLinePosition(Position posP, Position posQ, long siteId) throws Exception {
					
		//takes in a position p and position q
		//checks to see if the number of id's are zero
		//if they are set them to the relative min and max positions
		//means if that one position is longer than the other, the algorithm
		//can compare the positions properly
				
		if(posP.ids.size() == 0) {
			posP = maker.genPosition(maker.genIdentifierMin());
		}
		
		if(posQ.ids.size() == 0) {
			posQ = maker.genPosition(maker.genIdentifierMax());
		}
		
		//compares the identifiers in the 1st position
		//if -1 (ie q is greater than p for both site and position)
		
		List<Identifier> build = new ArrayList<Identifier>(); 
		
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
					
					posP.ids = posP.ids.subList(1, posP.ids.size());
					posQ.ids = posQ.ids.subList(1, posQ.ids.size());
					
					build.addAll(generateLinePosition(posP, posQ, siteId));
					return build;
				}
			
			case 0:
				
				build.add(posP.ids.get(0));
				
				posP.ids.subList(1, posP.ids.size());
				posQ.ids.subList(1, posQ.ids.size());
				
				build.addAll(generateLinePosition(posP, posQ, siteId));
				return build;
			
			case 1:				
				
				throw new Exception("Position Q was less than Position P!");
		}
		
		return build;
	}
		
	protected static int prefix(Position pos) {
		return pos.ids.get(0).position;
	}
		
	//compares the identifiers
	//returns -1 if the position and site of q (the position higher up the sequence) is greater than the position and site of p
	//returns 1 if the position and site of p is greater than the position of and site of q
	
	protected static int compareIdentifier(Identifier p, Identifier q) {
		
		if(p.position > q.position)
			return 1;
		if(p.position < q.position)
			return -1;
		if(p.siteId > q.siteId)
			return 1;
		if(p.siteId < q.siteId)
			return -1;
		return 0;
	}
		
	protected static int randomInt(int min, int max) {
		return rand.nextInt(max - min - 1) + min + 1;
	}
	
	protected static SequenceAtom genStartAtom(long siteId) {

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
		
		doc.arr.add(doc.arr.size() - 1, atom);
		
//		int length = doc.arr.size();
//
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
}

////	P has a greater identifier but a lower site Id
//
////	[
////		p [[[[27908, 6746016110688904592]], 2], YAA],
////		q [[[[19836, 7988293892253055526]], 3], Hi],
////	]

////	P has a lower identifier (correct) but a higher siteId
//
////	[
////		p [[[[27908, 8746016110688904592]], 2], YAA],
////		q [[[[29836, 7988293892253055526]], 3], Hi],
////	]

////	Either site or identifier are not equal
//
////	[
////		p [[[[27908, 6746016110688904592]], 2], YAA],
////		q [[[[29836, 7988293892253055526]], 3], Hi],
////	]	


