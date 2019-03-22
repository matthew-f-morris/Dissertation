package com.project.datatypes;

public class Identifier {
	
	public int position;
	public String siteId;
	
	public Identifier(int position, String siteId) {
		this.position = position;
		this.siteId = siteId;
	}
}






//	public Identifier(DocumentNode atom, String siteId) throws Exception {
//		
//		this.siteId = siteId;
//		
//		if(atom == DocumentNode.MIN) {
//			
//			this.position = Short.MIN_VALUE;			
//		} else if(atom == DocumentNode.MAX) {
//			
//			this.position = Short.MAX_VALUE;
//		} else {
//			
//			throw new Exception("Invalid Identifier Construction");
//		}
//	}
//	
//	public int getPosition() {
//		return position;
//	}
//	
//	public String getIdentifier() {
//		return siteId;
//	}
//}
