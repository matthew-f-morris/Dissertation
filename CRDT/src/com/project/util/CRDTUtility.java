package com.project.util;

import java.util.ArrayList;
import java.util.Random;

import com.project.clock.Clock;
import com.project.crdt.ComponentGenerator;
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
	
	public static SequenceAtom genStartAtom(long siteId) {

		Position pMin = ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMin());
		AtomIdentifier atom = ComponentGenerator.genAtomIdentifier(pMin, Clock.counter);
		Clock.increment();
		return ComponentGenerator.genSequenceAtom(atom);
	}
	
	public static SequenceAtom genStopAtom(long siteId) {

		Position pMax = ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMax());
		AtomIdentifier atom = ComponentGenerator.genAtomIdentifier(pMax, Clock.counter);
		Clock.increment();
		return ComponentGenerator.genSequenceAtom(atom);
	}
	
	public static SequenceAtom genSequenceAtom(String message, Position p) {
		
		AtomIdentifier atom = ComponentGenerator.genAtomIdentifier(p, Clock.counter);
		Clock.increment();
		return ComponentGenerator.genSequenceAtom(atom, message);

	}
	
	public static ArrayList<SequenceAtom> insertSequenceAtom(ArrayList<SequenceAtom> array, SequenceAtom add) throws Exception {

		int size = array.size();
		
		for(int i = 0; i < size; i++) {
			
			Position pos = add.atomId.position;
			Position prev = array.get(i).atomId.position;
			Position next = array.get(i + 1).atomId.position;
			
			int l = comparePosition(pos, prev, 0);
			int u = comparePosition(pos, next, 0);
			
			if(l == 1 && u == -1)
				return insert(array, add, i + 1);
			else if(l == 1 && u == 1)
				continue;
			else if(l == -1)
				throw new Exception("The document atoms are not in a valid order!");
			else return array;
		}
		
		return null;
	}
	
	private static int comparePosition(Position p, Position q, int x) {
		
		int i = x;
		//return 0 if equal, retur 1 if q is greater than p, return -1 if p is greater than q
		
		int lenP = p.ids.size();
		int lenQ = q.ids.size();
		
		if(lenP == 0 && lenQ == 0)
			return 0;
		if(lenP == 0)
			return -1;
		if(lenQ == 0)
			return 1;
		
		switch(compareIdentifier(p.ids.get(i), q.ids.get(i))){
			case 1:
				return 1;
			case -1:
				return -1;
			case 0:
				return comparePosition(p, q, i++);
		}
		
		return 0;
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


