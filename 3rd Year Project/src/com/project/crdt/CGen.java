package com.project.crdt;

import java.util.ArrayList;
import java.util.List;

import com.project.datatypes.AtomIdentifier;
import com.project.datatypes.Identifier;
import com.project.datatypes.Position;
import com.project.datatypes.Sequence;
import com.project.datatypes.SequenceAtom;
import com.project.utils.CRDTUtility;

//static class that means generating new datatypes for the crdt becomes less messy

public class CGen {
	
	public static final int MAX_POSITION = Short.MAX_VALUE;	
	public static final int ARITY = CRDTUtility.base(0);
	
	private static int checkValue(int x) throws Exception {
		
		if(x < 1 || x >= MAX_POSITION) {
			System.err.println("Invalid Integer Value: " + x);
			return -1;
		} else
			return x;
	}
	
	private static int checkValueLSEQ(int x) throws Exception {
		
		if(x < 1 || x >= Integer.MAX_VALUE) {
			System.err.println("Invalid Integer Value: " + x);
			return -1;
		} else
			return x;
	}
	
	public static Identifier genIdentifier(int p, long siteId) throws Exception {
		
		if(checkValue(p) == -1) {
			throw new Exception("Identifier Generation Failed!");
		} else		
			return new Identifier(p, siteId);
	}

	public static Identifier genIdentifierLseq(int p, long siteId) throws Exception {
		
		if(checkValueLSEQ(p) == -1) {
			throw new Exception("LSEQ Identifier Generation Failed!");
		} else		
			return new Identifier(p, siteId);
	}
	
	public static Identifier genIdentifierMax() {
		return new Identifier(MAX_POSITION - 1, 0);
	}
	
	public static Identifier genIdentifierMin() {
		return new Identifier(0, 0);
		//return new Identifier(MAX_POSITION - 2, siteId);
	}
	
	public static Identifier genIdentifierMinLseq() {
		return new Identifier(0, 0);
	}
	
	public static Identifier genIdentifierMaxLseq() {
		return new Identifier(ARITY - 1, 0);
	}
	
	public static Position genPosition() {
		return new Position();
	}
	
	public static Position genPosition(Identifier identifier) {
		return new Position(identifier);
	}
	
	public static Position genPosition(ArrayList<Identifier> identifier) {
		return new Position(identifier);
	}
	
	public static AtomIdentifier genAtomIdentifier(int clock) {
		return new AtomIdentifier(clock);
	}
	
	public static AtomIdentifier genAtomIdentifier(Position position, int clock) {
		return new AtomIdentifier(position, clock);
	}
	
	public static SequenceAtom genSequenceAtom(AtomIdentifier atomId) {
		return new SequenceAtom(atomId);
	}
	
	public static SequenceAtom genSequenceAtom(AtomIdentifier atomId, String message) {
		return new SequenceAtom(atomId, message);
	}
	
	public static Sequence genSequence() {
		return new Sequence();
	}
	
	public static Sequence genSequence(SequenceAtom seqAtom) {
		return new Sequence(seqAtom);
	}
}	
