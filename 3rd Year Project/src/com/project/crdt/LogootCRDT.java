package com.project.crdt;

import java.util.ArrayList;
import java.util.List;

import com.project.clock.Clock;
import com.project.datatypes.Identifier;
import com.project.datatypes.Position;
import com.project.datatypes.SequenceAtom;
import com.project.utils.CRDTUtility;

//The main algorithm for the logoot crdt implementation

public class LogootCRDT {
		
	//method called that generates the sequence atom to be added to the document
	
	public static SequenceAtom generate(String message, Position p, Position q, long siteId, Boolean modify) throws Exception {	
		
		Position pos = new Position(generateLinePosition(p, q, siteId, modify));		
		return ComponentGenerator.genSequenceAtom((ComponentGenerator.genAtomIdentifier(pos, Clock.counter)), message);
	}
	
	//generates the line position for the new atom, this method is recursive
		
	private static List<Identifier> generateLinePosition(Position posP, Position posQ, long siteId, Boolean modify) throws Exception {
					
		//this method calculates the proper Identifier (list of positions) that means the new sequence atom can be added between position p and q
				
		if(posP.ids.size() == 0) {
			posP = ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMin());
		}
		
		if(posQ.ids.size() == 0) {
			posQ = ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMax());
		}
		
		//compares the identifiers in the 1st position
		//if -1 (ie q is greater than p for both site and position)
		
		List<Identifier> build = new ArrayList<Identifier>(); 
		
		switch(CRDTUtility.compareIdentifier(posP.ids.get(0), posQ.ids.get(0))) {
			
			case -1:
								
				int interval = prefix(posQ) - prefix(posP);
				
				if(interval > 1) {

					if(modify)
						build.add(ComponentGenerator.genIdentifier(posP.ids.get(0).position + 1, siteId));
					else 
						build.add(ComponentGenerator.genIdentifier(CRDTUtility.randomInt(posP.ids.get(0).position, posQ.ids.get(0).position), siteId));
					return build;
				}
				
				else if(interval == 1 && siteId > posP.ids.get(0).siteId) {
					
					build.add(ComponentGenerator.genIdentifier(posP.ids.get(0).position, siteId));
					return build;
					
				} else {
					
					build.add(posP.ids.get(0));
					
					posP.ids = posP.ids.subList(1, posP.ids.size());
					posQ.ids = posQ.ids.subList(1, posQ.ids.size());
					
					build.addAll(generateLinePosition(posP, posQ, siteId, modify));
					return build;
				}
			
			case 0:
				
				build.add(posP.ids.get(0));
				
				posP.ids.subList(1, posP.ids.size());
				posQ.ids.subList(1, posQ.ids.size());
				
				build.addAll(generateLinePosition(posP, posQ, siteId, modify));
				return build;
			
			case 1:				
				
				throw new Exception("Position Q was less than Position P!");
		}
		
		return build;
	}
	
	private static int prefix(Position pos) {
		return pos.ids.get(0).position;
	}
}