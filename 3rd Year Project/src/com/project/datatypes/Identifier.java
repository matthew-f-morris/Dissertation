package com.project.datatypes;

import java.io.Serializable;

public class Identifier implements Serializable {

	private static final long serialVersionUID = 30L;
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
