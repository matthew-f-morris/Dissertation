package com.project.crdt;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.project.datatypes.LSEQid;
import com.project.util.CRDTUtility;

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
		LSEQid id;
		
		while(interval < 1) {
			depth++;
			interval = prefix(q, depth).get(depth) - prefix(p, depth).get(depth) - 1;		
		}
		
		int step = Math.min(boundary, interval);
		
		if(!strategy.containsKey(depth)) {
			Boolean rand = CRDTUtility.randomBool();
			strategy.put(depth, rand);
		}
		
		if(strategy.get(depth)){
			//CHECK THIS THING
			int addVal = CRDTUtility.randomInt(0, step) + 1;
			id = maker.genLSEQid(prefix(p, depth));
			id.listIds.add(addVal);
			
		} else {
			//CHECK THIS THING ALSO
			int subVal = CRDTUtility.randomInt(0, step) + 1;
			id = maker.genLSEQid(prefix(q, depth));
			id.listIds.add(subVal);		
		}
		
		return new LSEQid();
	} 
	
	private static ArrayList<Integer> prefix(LSEQid id, int depth) {
		
		ArrayList<Integer> idCopy = new ArrayList<Integer>();
		
		for(int i = 0; i < depth; i++) {
			if(i < id.listIds.size()) {
				idCopy.add(id.listIds.get(i));
			} else {
				idCopy.add(base(i));
			}
		}
		
		return idCopy;
	} 
	
	private static int base(int cpt) {
		return (int) Math.pow(2, (5 + cpt));
	}
}
