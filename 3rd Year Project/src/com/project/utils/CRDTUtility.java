package com.project.utils;

import java.util.ArrayList;
import java.util.Random;

import com.project.clock.Clock;
import com.project.crdt.CGen;
import com.project.datatypes.AtomIdentifier;
import com.project.datatypes.Identifier;
import com.project.datatypes.Position;
import com.project.datatypes.SequenceAtom;

public class CRDTUtility {
	
	private static Random rand = new Random();
		
	//compares the identifiers
	//returns -1 if the position and site of q (the position higher up the sequence) is greater than the position and site of p
	//returns 1 if the position and site of p is greater than the position of and site of q
	
	public static int compareIdentifier(Identifier p, Identifier q) {
		
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
		
	public static int randomInt(int min, int max) {
		return rand.nextInt(max - min - 1) + min + 1;
	}
	
	public static Boolean randomBool() {
		return rand.nextBoolean();
	}
	
	public static SequenceAtom genStartAtom() {

		Position pMin = CGen.genPosition(CGen.genIdentifierMin());
		AtomIdentifier atom = CGen.genAtomIdentifier(pMin, Clock.counter);
		Clock.increment();
		return CGen.genSequenceAtom(atom);
	}
	
	public static SequenceAtom genAtom(int p, long siteId) throws Exception {
		
		Position pMin = CGen.genPosition(CGen.genIdentifier(p, siteId));
		AtomIdentifier atom = CGen.genAtomIdentifier(pMin, Clock.counter);
		Clock.increment();
		return CGen.genSequenceAtom(atom);
	}
	
	public static SequenceAtom genStopAtom() {

		Position pMax = CGen.genPosition(CGen.genIdentifierMax());
		AtomIdentifier atom = CGen.genAtomIdentifier(pMax, Clock.counter);
		Clock.increment();
		return CGen.genSequenceAtom(atom);
	}
	
	public static SequenceAtom genStartAtomLseq() {
		
		Position pMin = CGen.genPosition(CGen.genIdentifierMinLseq());
		AtomIdentifier atom = CGen.genAtomIdentifier(pMin, Clock.counter);
		Clock.increment();
		return CGen.genSequenceAtom(atom);
	}
	
	public static SequenceAtom genStopAtomLseq() {
		
		Position pMin = CGen.genPosition(CGen.genIdentifierMaxLseq());
		AtomIdentifier atom = CGen.genAtomIdentifier(pMin, Clock.counter);
		Clock.increment();
		return CGen.genSequenceAtom(atom);
	}
	
	public static SequenceAtom genSequenceAtom(String message, Position p) {
		
		AtomIdentifier atom = CGen.genAtomIdentifier(p, Clock.counter);
		Clock.increment();
		return CGen.genSequenceAtom(atom, message);

	}
	
	public static void insertSequenceAtom(ArrayList<SequenceAtom> array, SequenceAtom add) throws Exception {

		int size = array.size();
		
		for(int i = size - 1; i > 0; i--) {
			
			Position pos = add.atomId.position;
			Position prev = array.get(i - 1).atomId.position;
			Position next = array.get(i).atomId.position;
			
			int l = comparePosition(prev, pos);
			int u = comparePosition(pos, next);
			
			if(l == -1 && u == -1)
				insert(array, add, i);
			else if(l == -1 && u == 1)
				continue;
			else if(l == 1)
				throw new Exception("The document atoms are not in a valid order!");
		}
	}
	
	public static int comparePosition(Position p, Position q) {

		//return 0 if equal, retur 1 if q is greater than p, return -1 if p is greater than q
		
		int lenP = p.ids.size();
		int lenQ = q.ids.size();
		
		if(lenP == 0 && lenQ == 0)
			return 0;
		if(lenP == 0)
			return -1;
		if(lenQ == 0)
			return 1;
		
		switch(compareIdentifier(p.ids.get(0), q.ids.get(0))){
			case 1:
				return 1;
			case -1:
				return -1;
			case 0:
				return comparePosition(new Position(p.copy(1)), new Position(q.copy(1)));
		}
		
		return 0;
	}
	
	public static int prefix(Position pos) {
		return pos.ids.get(0).position;
	}
		
	public static int base(int cpt) {
		return (int) 32 * (int) Math.pow(2, cpt);
	}
	
	private static ArrayList<SequenceAtom> insert(ArrayList<SequenceAtom> array, SequenceAtom atom, int index) {
		
		array.add(index, atom);
		return array;
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


