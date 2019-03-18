package com.project.crdt;

public final class CRDTUtilityFunctions {
	
	private CRDTUtilityFunctions() {}
	
	/*
	 *	Compare atom identifiers, returns 1 if x > y, returns -1 if y > x,
	 * 	returns 0 if x == y 
	 */
	
	public static int compareAtomIdentifiers(AtomIdentifier x, AtomIdentifier y) {
		return comparePos(x.position, y.position);
	}
	
	public static int comparePosition(Position posx, Position posy) {
		// implement 
	}
}
