package com.project.crdt;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.project.datatypes.LSEQid;

public class LSEQCRDT {
	
	//true = boundary+
	//false = boundary-
	
	private static int boundary = 10;
	private static Map<Integer, Boolean> strategy = new HashMap<Integer, Boolean>();
	private static ComponentGenerator maker;	
	
	public LSEQCRDT(ComponentGenerator maker) {
		LSEQCRDT.maker = maker;		
	}
	
	//insert algorithm 1 here (allocation function)
	
	public static LSEQid alloc(LSEQid p, LSEQid q) {
		
		int depth = -1;
		int interval = 0;
		
		while(interval < 1) {
			depth++;
			interval = prefix(q, depth) - prefix(p, depth) - 1;
		}
		
		int step = Math.min(boundary, interval);
		
		if(!strategy.containsKey(depth)) {
			Boolean rand = CRDTUtility.randomBool();
			strategy.put(depth, rand);
		}
		
		return new LSEQid();
	} 
	
	private static int prefix(LSEQid id, int depth) {
		
		ArrayList<Integer> idCopy = new ArrayList<Integer>();
		
		for(int i = 0; i < depth; i++) {
			if(i < id.listIds.size()) {
				idCopy.add(id.listIds.get(i));
			} else {
				idCopy.add(base(i));
			}
		}
		
		return idCopy.get(depth);
	} 
	
	private static int base(int cpt) {
		return 0;
	}
}
