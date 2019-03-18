package com.project.crdt;

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
}


//
//public Clock getClock() {
//	return clock;
//}
//
//public Position getPosition() {
//	return position;
//}