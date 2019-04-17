package com.project.datatypes;

public class AtomIdentifier {

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