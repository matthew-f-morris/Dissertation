package com.project.datatypes;

import java.io.Serializable;

public class AtomIdentifier implements Serializable {

	private static final long serialVersionUID = 20L;
	public Position position;
	public int clock;

	public AtomIdentifier(int clock) {		
		position = new Position();
		this.clock = clock;
	}
	
	public AtomIdentifier(Position position, int clock) {
		this.position = position;
		this.clock = clock;
	}
	
	@Override
	public String toString() {		
		return "[" + position.toString() + ", " + clock + "]";
	}
}