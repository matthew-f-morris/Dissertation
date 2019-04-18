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
	
	@Override
	public String toString() {
		
		String s = "";
		
		if(!(arr.size() == 0)) {
			for(SequenceAtom seqAtom : arr) {			
				s+= seqAtom.toString() + ", ";
			}
			return s.substring(0, s.length()-2);
		}
		
		return "Empty Sequence";		
	}
}

	
//	
//	public void add(SequenceAtom seqAtom) {
//		
//		sequence.add(seqAtom);
//	}
//}

//these are just the datatypes, will need to edit