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
	
	private static Random rand = new Random();
	private static Clock clock;
	
	public CRDTUtility(Clock clock, long siteIdCopy) {
		
		CRDTUtility.clock = clock;
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
	
	protected static Boolean randomBool() {
		return rand.nextBoolean();
	}
	
	protected static SequenceAtom genStartAtom(long siteId) {

		Position pMin = ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMin());
		AtomIdentifier atom = ComponentGenerator.genAtomIdentifier(pMin, clock.counter);
		clock.increment();
		return ComponentGenerator.genSequenceAtom(atom);
	}
	
	public static SequenceAtom genStopAtom(long siteId) {

		Position pMax = ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMax());
		AtomIdentifier atom = ComponentGenerator.genAtomIdentifier(pMax, clock.counter);
		clock.increment();
		return ComponentGenerator.genSequenceAtom(atom);
	}
	
	public static SequenceAtom genSequenceAtom(String message, Position p) {
		
		AtomIdentifier atom = ComponentGenerator.genAtomIdentifier(p, clock.counter);
		clock.increment();
		return ComponentGenerator.genSequenceAtom(atom, message);
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
	
	private static int comparePosition(Position p, Position q) {
		return 0;
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


