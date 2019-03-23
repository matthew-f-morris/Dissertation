package com.project.datatypes;

import java.util.ArrayList;

public class Sequence {
	
	public ArrayList<SequenceAtom> arr;
	
	public Sequence() {
		
		arr = new ArrayList<>();
	}
	
	public Sequence(SequenceAtom seqAtom) {		
		arr = new ArrayList<SequenceAtom>();
		arr.add(seqAtom);
	}
}

	
//	
//	public void add(SequenceAtom seqAtom) {
//		
//		sequence.add(seqAtom);
//	}
//}

//these are just the datatypes, will need to edit