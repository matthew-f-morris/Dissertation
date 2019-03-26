package com.project.datatypes;

public class Identifier {
	
	public int position;
	public long siteId;
	
	public Identifier(int position, long siteId) {
		this.position = position;
		this.siteId = siteId;
	}
	
	@Override 
	public Identifier clone() {
		return new Identifier(this.position, this.siteId);
	}
	
	@Override
	public String toString() {		
		return "[" + this.position + ", " + this.siteId + "]";
	}
}
