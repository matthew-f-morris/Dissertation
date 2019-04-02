package com.project.datatypes;

import java.util.ArrayList;
import java.util.List;

public class Position {
	
	public List<Identifier> ids;
	
	public Position(Identifier identifier) {		
		ids = new ArrayList<Identifier>();
		ids.add(identifier);
	}
	
	public Position() {		
		ids = new ArrayList<Identifier>();
	}

	public Position(List<Identifier> identifiers) {
		this.ids = identifiers;
	}
	
	public List<Identifier> copy(){
		
		List<Identifier> arr = new ArrayList<Identifier>();
		for(Identifier i : ids) {
			arr.add(i.clone());
		}
		
		return arr;
	}
	
	public List<Identifier> copy(int x){
		
		List<Identifier> arr = new ArrayList<Identifier>();
		for(Identifier i : ids) {
			arr.add(i.clone());
		}
		
		return arr.subList(x, arr.size());
	}
	
	@Override
	public String toString() {
		
		String s = "";
		
		for(Identifier i : ids) {			
			s+= i.toString() + ", ";
		}
		
		return s.substring(0, s.length()-2);
	}
}

//	
//	public Identifier getIdentifier(int index) {		
//		return position.get(index);
//	}
//	
//	public void setIdentifier(int index, Identifier identifier) {		
//		position.add(index, identifier);
//	}
//	
//	public Identifier getLast() {		
//		return position.get(position.size() -1);
//	}
//}
