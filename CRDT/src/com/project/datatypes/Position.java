package com.project.datatypes;

import java.util.ArrayList;

public class Position {
	
	public ArrayList<Identifier> ids;
	
	public Position(Identifier identifier) {		
		ids = new ArrayList<Identifier>();
		ids.add(identifier);
	}
	
	public Position() {		
		ids = new ArrayList<Identifier>();
	}

	public Position(ArrayList<Identifier> identifiers) {
		this.ids = identifiers;
	}
	
	public ArrayList<Identifier> copy(){
		ArrayList<Identifier> arr = new ArrayList<Identifier>();
		for(Identifier i : ids) {
			arr.add(i.clone());
		}
		
		return arr;
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
