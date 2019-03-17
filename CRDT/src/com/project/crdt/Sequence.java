package com.project.crdt;

import java.util.ArrayList;

public class Sequence {
	
	public ArrayList<SequenceAtom> sequence;
	
	public Sequence() {
		
		sequence = new ArrayList<>();
	}
	
	public Sequence(SequenceAtom seqAtom) {
		
		sequence = new ArrayList<SequenceAtom>();
		sequence.add(seqAtom);
	}
	
	public void add(SequenceAtom seqAtom) {
		
		sequence.add(seqAtom);
	}
}

//these are just the datatypes, will need to edit