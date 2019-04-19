package com.project.datatypes;

import java.io.Serializable;

public class SequenceAtom implements Serializable {

	private static final long serialVersionUID = 10L;
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
	
	@Override
	public String toString() {
		return "[" + atomId.toString() + ", " + message + "],";
	}
}
