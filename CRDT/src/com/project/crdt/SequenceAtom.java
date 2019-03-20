package com.project.crdt;

public class SequenceAtom {
	
	public AtomIdentifier atomId;
	public String message;
	
	public SequenceAtom(AtomIdentifier atomId) {
		
		this.atomId = atomId;
		message = "DEFAULT MESSAGE: \nSite Counter - " + atomId.clock;
	}
	
	public SequenceAtom(AtomIdentifier atomId, String message) {
		
		this.atomId = atomId;
		this.message = message;
	}
}