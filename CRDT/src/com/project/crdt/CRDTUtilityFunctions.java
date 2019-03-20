package com.project.crdt;

import java.util.ArrayList;

public final class CRDTUtilityFunctions {
	
	private CRDTUtilityFunctions() {}
	
	/*
	 *	Compare atom identifiers, returns 1 if x > y, returns -1 if y > x,
	 * 	returns 0 if x == y 
	 */
	
	public static int compareAtomIdentifiers(AtomIdentifier x, AtomIdentifier y) {
		System.out.println("ur mum");
		return comparePos(x.position, y.position);
	}
	
	public static int comparePosition(Position posx, Position posy) {
		// implement 
	}
	
	public static void generateLinePositions(Position posP, Position posQ, int numberLines, String siteId) {
		
		ArrayList<Position> positions = new ArrayList<Position>();
		int index = 0;
		int interval = 0;
		
		while(interval < numberLines) {
			
			index++;
			interval = prefix(posQ, index) - prefix(posP, index);
		}
		
		int step = interval / numberLines;
		int r = prefix(posP, index);
		
		for(int i = 0; i < numberLines; i++) {
			positions.add(constructPosition(r, posP, posQ, siteId));
		}
	}
	
	public static Position constructPosition(int r, Position posP, Position posQ, String oldId) {
		
		Position position = new Position();
		
		for(int j = 0; j < r; j++) {
			
			String siteId;
			int pos = j;
			
			if(j == r - 1) {
				siteId = oldId;
			} else if(j == posP.ids.get(j).position) {
				siteId = posP.ids.get(j).siteId;
			} else if(j == posQ.ids.get(j).position) {
				siteId = posQ.ids.get(j).siteId;
			} else {
				siteId = oldId;
			}
			
			Identifier id = new Identifier(pos, siteId);
			position.ids.add(id);
		}
				
		return new Position();
	}

	public static int prefix(Position pos, int index) {
		
		if(pos.ids.size() < i)
			return 0;
		else {
			pos.ids.get(index)
		}
	}
}

//The function prefix(p,i) returns a number in base MAXINT which each digits is pi.pos the integers of the first ith identifiers of p (filled with 0 if |p|<  i).