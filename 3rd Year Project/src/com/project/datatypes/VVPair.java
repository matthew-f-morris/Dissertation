package com.project.datatypes;

public class VVPair {

	public long uuid;
	public int clock;
	
	public VVPair(long uuid, int clock) {
		this.uuid = uuid;
		this.clock = clock;
	}
	
	public VVPair clone() {
		return new VVPair(this.uuid, this.clock);
	}
}
