package com.project.datatypes;

import java.util.ArrayList;
import java.util.List;

public class LSEQid {
	
	public List<Integer> listIds;
	
	public LSEQid() {
		listIds = new ArrayList<Integer>();
	}
	
	public LSEQid(List<Integer> ids) {
		this.listIds = ids;
	}
	
	public List<Integer> copy(){		
		return new ArrayList<Integer>(listIds);
	}
	
	@Override
	public String toString() {
		
		String s = "";
		
		for(int i : listIds) {			
			s+= i + ", ";
		}
		
		return s.substring(0, s.length()-2);
	}
}
