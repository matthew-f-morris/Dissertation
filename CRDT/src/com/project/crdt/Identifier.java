package com.project.crdt;

import java.util.UUID;

public class Identifier {
	
	public short position;
	public String siteId;
	
	public Identifier(short position, String siteId) {
		this.position = position;
		this.siteId = siteId;
	}
	
	public Identifier(DocumentNode atom, String siteId) throws Exception {
		
		this.siteId = siteId;
		
		if(atom == DocumentNode.MIN) {
			
			this.position = Short.MIN_VALUE;			
		} else if(atom == DocumentNode.MAX) {
			
			this.position = Short.MAX_VALUE;
		} else {
			
			throw new Exception("Invalid Identifier Construction");
		}
	}
	
	public short getPosition() {
		return position;
	}
	
	public String getIdentifier() {
		return siteId;
	}
}
