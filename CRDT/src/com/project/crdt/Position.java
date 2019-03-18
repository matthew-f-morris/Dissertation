package com.project.crdt;

import java.util.ArrayList;

public class Position {
	
	public ArrayList<Identifier> position;
	
	public Position(Identifier identifier) {		
		position = new ArrayList<Identifier>();
		position.add(identifier);
	}
	
	public Position() {		
		position = new ArrayList<Identifier>();
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
