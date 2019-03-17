package com.project.crdt;

import java.util.UUID;

public class Identifier {
	
	private int position;
	private String identifier;
	
	public Identifier(int position, String identifier) {
		this.position = position;
		this.identifier = identifier;
	}
	
	public Identifier(SequenceAtom atom, String identifier) throws Exception {
		
		this.identifier = identifier;
		
		if(atom == SequenceAtom.MIN) {
			
			this.position = Short.MIN_VALUE;			
		} else if(atom == SequenceAtom.MAX) {
			
			this.position = Short.MAX_VALUE;
		} else {
			
			throw new Exception("Invalid Identifier Construction");
		}
	}
}
