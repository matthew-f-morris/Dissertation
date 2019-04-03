package com.project.datatypes;

import java.util.ArrayList;
import java.util.List;

public class LSEQSequence {
	
	public List<LSEQid> arr;
	
	public LSEQSequence() {
		arr = new ArrayList<LSEQid>();
	}
	
	public LSEQSequence(LSEQid id){
		arr = new ArrayList<LSEQid>();
		arr.add(id);
	}
	
	@Override
	public String toString() {
		
		String s = "";
		
		for(LSEQid id : arr) {			
			s+= id.toString() + ", ";
		}
		
		return s.substring(0, s.length()-2);
	}
}
