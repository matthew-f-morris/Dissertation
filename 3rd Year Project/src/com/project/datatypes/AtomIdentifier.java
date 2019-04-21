package com.project.datatypes;

import java.io.Serializable;
import java.util.List;

public class AtomIdentifier implements Serializable {

	private static final long serialVersionUID = 20L;
	public Position position;
	public List<VVPair> clock;

	public AtomIdentifier(List<VVPair> clock) {		
		position = new Position();
		this.clock = clock;
	}
	
	public AtomIdentifier(Position position, List<VVPair> vv) {
		this.position = position;
		this.clock = vv;
	}
	
	@Override
	public String toString() {		
		return "[" + position.toString() + ", " + clock + "]";
	}
}