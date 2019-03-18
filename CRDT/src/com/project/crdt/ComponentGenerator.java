package com.project.crdt;

import com.project.clock.Clock;

public class ComponentGenerator {
	
	public static final int MAX_POSITION = 32767;	
	private Clock clock;
	private String siteId;
	
	public ComponentGenerator(Clock clock, String siteId) {
		this.clock = clock;
		this.siteId = siteId;
	}
	
	private int checkValue(int x) throws Exception {
		
		if(x < 1 || x >= MAX_POSITION) {
			System.err.println("Invalid Integer Value");
			return -1;
		} else
			return x;
	}
	
	public Identifier genIdentifier(int p) throws Exception {
		
		if(checkValue(p) == -1) {
			throw new Exception("Identifier Generation Failed!");
		} else		
			return new Identifier(p, siteId);
	}
	
	public Identifier genIdentifierMax() {
		return new Identifier(MAX_POSITION, siteId);
	}
	
	public Identifier genIdentifierMin() {
		return new Identifier(0, siteId);
	}
	
	public Position genPosition() {
		return new Position();
	}
	
	public Position genPosition(Identifier identifier) {
		return new Position(identifier);
	}
	
	public AtomIdentifier genAtomIdentifier(int clock) {
		return new AtomIdentifier(clock);
	}
	
	public AtomIdentifier genAtomIdentifier(Position position, int clock) {
		return new AtomIdentifier(position, clock);
	}
	
	public SequenceAtom genSequenceAtom(AtomIdentifier atomId) {
		return new SequenceAtom(atomId);
	}
	
	public SequenceAtom genSequenceAtom(AtomIdentifier atomId, String message) {
		return new SequenceAtom(atomId, message);
	}
	
	public Sequence genSequence() {
		return new Sequence();
	}
	
	public Sequence genSequence(SequenceAtom seqAtom) {
		return new Sequence(seqAtom);
	}
}	
