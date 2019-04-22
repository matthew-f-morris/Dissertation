package com.project.datatypes;

import java.io.Serializable;

public class VVPair implements Serializable, Comparable<VVPair> {

	private static final long serialVersionUID = 9090L;
	public long uuid;
	public int clock;
	
	public VVPair(long uuid, int clock) {
		this.uuid = uuid;
		this.clock = clock;
	}
	
	public VVPair clone() {
		return new VVPair(this.uuid, this.clock);
	}
	
	@Override
	public String toString() {
		return "[" + this.uuid + ", " + this.clock + "]";
	}

	@Override
	public int compareTo(VVPair o) {
		
		//allows version vector List<VVPair> to be sorted into ascending order according to siteId
		
		return Long.compare(uuid, o.uuid);
	}
}
