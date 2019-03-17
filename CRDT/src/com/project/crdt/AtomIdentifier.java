package com.project.crdt;

import com.project.clock.Clock;

public class AtomIdentifier {

	public Position position;
	public Clock clock;
	//private int clock;
	
	public AtomIdentifier() {		
		position = new Position();
		clock = new Clock();
	}
	
	public Clock getClock() {
		return clock;
	}
	
	public Position getPosition() {
		return position;
	}
}
