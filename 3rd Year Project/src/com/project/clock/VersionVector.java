package com.project.clock;

import java.util.ArrayList;
import java.util.List;

import com.project.datatypes.SequenceAtom;
import com.project.datatypes.VVPair;

public class VersionVector {

	public static List<VVPair> vv;
	private static long siteId;
	
	public static void init(long siteId) {
	
		vv = new ArrayList<VVPair>();
		vv.add(new VVPair(siteId, 0));
		
		VersionVector.siteId = siteId;
	}
	
	public void VVAdd(long siteId, int clock) throws Exception {
		
		if(sanityCheck(clock) == -1)		
			throw new Exception("VV clock seet to invalid value!");			
		else 	
			vv.add(new VVPair(siteId, clock));
	}
	
	private static int sanityCheck(int check) {	
		
		if(0 <= check && check <= Integer.MAX_VALUE)	
			return check;	
		return -1;
	}
	
	public static boolean increment() {
		
		Boolean found = false;
		
		for(VVPair tuple : vv) {
			if(tuple.uuid == siteId) {
				tuple.clock++;
				found = true;
			}
		}
		
		return found;
	}
	
	public static List<VVPair> copy(List<VVPair> clone){
		
		List<VVPair> arr = new ArrayList<VVPair>();
		
		for(VVPair i : clone) {
			arr.add(i.clone());
		}
		
		return arr;
	}
	
	public static List<VVPair> copy(){
		
		List<VVPair> arr = new ArrayList<VVPair>();
		
		for(VVPair i : vv) {
			arr.add(i.clone());
		}
		
		return arr;
	}
		
	public static boolean sync(List<VVPair> toCompare) {
		
		List<VVPair> newVV = new ArrayList<VVPair>();		
		int size = Math.max(vv.size(), toCompare.size());
		
		for(int i = 0; i < size; i++) {

			VVPair a;
			VVPair b;
			
			try{
				a = vv.get(i);
			} catch(IndexOutOfBoundsException e) {
				a = new VVPair(0L, 0);
			}
			
			try{
				b = toCompare.get(i);
			} catch(IndexOutOfBoundsException e) {
				b = new VVPair(0L, 0);
			}
			
			newVV.add(max(a, b));
		}
		
		vv = newVV;		
		return true;
	}
	
	private static VVPair max(VVPair a, VVPair b) {
		
		if(a.clock > b.clock)
			return a;
		return b;
	}
	
	@Override
	public String toString() {
		
		String s = "";
		
		if(!(vv.size() == 0)) {
			for(VVPair pair : vv) {			
				s+= pair.toString() + ", ";
			}
			return s.substring(0, s.length()-2);
		}
		
		return "Empty Sequence";		
		
	}
}
