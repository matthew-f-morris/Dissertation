package com.project.datatypes;

import java.util.ArrayList;

public class Sequence {
	
	public ArrayList<SequenceAtom> sequenceArray;
	
	public Sequence() {
		
		sequenceArray = new ArrayList<>();
	}
	
	public Sequence(SequenceAtom seqAtom) {		
		sequenceArray = new ArrayList<SequenceAtom>();
		sequenceArray.add(seqAtom);
	}
}

	
//	
//	public void add(SequenceAtom seqAtom) {
//		
//		sequence.add(seqAtom);
//	}
//}

//these are just the datatypes, will need to edit