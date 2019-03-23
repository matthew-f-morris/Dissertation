package com.project.datatypes;

public class SequenceAtom {
	
	public AtomIdentifier atomId;
	public String message;
	
	public SequenceAtom(AtomIdentifier atomId) {
		
		this.atomId = atomId;
		message = null;
	}
	
	public SequenceAtom(AtomIdentifier atomId, String message) {
		
		this.atomId = atomId;
		this.message = message;
	}
}
