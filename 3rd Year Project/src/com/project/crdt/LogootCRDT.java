package com.project.crdt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.clock.Clock;
import com.project.datatypes.Identifier;
import com.project.datatypes.Position;
import com.project.datatypes.SequenceAtom;
import com.project.utils.CRDTUtility;
import com.project.utils.NodeQuerier;

//The main algorithm for the logoot crdt implementation

public class LogootCRDT {
			
	//method called that generates the sequence atom to be added to the document
	
	private static int boundary;
	private static Map<Integer, Boolean> strategy = new HashMap<Integer, Boolean>();
	private static int depth = -1;
	private static Boolean force = false;
	private static Boolean boundaryPlus = false;
	
	public LogootCRDT(int boundary) {
		LogootCRDT.boundary = boundary;
	}
	
	public static SequenceAtom generate(String message, Position p, Position q, long siteId, Boolean modify, Boolean setLseq, Boolean force, Boolean boundaryPlus) throws Exception {	

		LogootCRDT.force = force;
		LogootCRDT.boundaryPlus = boundaryPlus;
		
		Position pos = null;
		
		if(setLseq)
			pos = new Position(generateLinePositionLseq(p, q, siteId, modify, setLseq, depth));		
		else
			pos = new Position(generateLinePosition(p, q, siteId, modify));
	
		return CGen.genSequenceAtom(CGen.genAtomIdentifier(pos, Clock.counter), message);
	}

	//generates the line position for the new atom, this method is recursive

	private static List<Identifier> generateLinePosition(Position posP, Position posQ, long siteId, Boolean modify) throws Exception {
		
		//this method calculates the proper Identifier (list of positions) that means the new sequence atom can be added between position p and q
				
		if(posP.ids.size() == 0) {
			posP = CGen.genPosition(CGen.genIdentifierMin());
		}
		
		if(posQ.ids.size() == 0) {
			posQ = CGen.genPosition(CGen.genIdentifierMax());
		}

		//compares the identifiers in the 1st position
		//if -1 (ie q is greater than p for both site and position)
		
		List<Identifier> build = new ArrayList<Identifier>(); 
		
		switch(CRDTUtility.compareIdentifier(posP.ids.get(0), posQ.ids.get(0))) {
			
			case -1:
				
				int interval = 0;
				
				interval = CRDTUtility.prefix(posQ) - CRDTUtility.prefix(posP);
				
				if(interval > 1) {

					if(modify)
						build.add(CGen.genIdentifier(posP.ids.get(0).position + NodeQuerier.currentPeerNumber() , siteId));					
					else	
						build.add(CGen.genIdentifier(CRDTUtility.randomInt(posP.ids.get(0).position, posQ.ids.get(0).position), siteId));
					
					return build;
				}
				
				else if(interval == 1 && siteId > posP.ids.get(0).siteId) {
						
					build.add(CGen.genIdentifier(posP.ids.get(0).position, siteId));
					
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
	
	private static List<Identifier> generateLinePositionLseq(Position posP, Position posQ, long siteId, Boolean modify, Boolean setLseq, int depth) throws Exception {
		
		//this method calculates the proper Identifier (list of positions) that means the new sequence atom can be added between position p and q
		depth++;
		
//		System.out.println("Position P BEFORE: " + posP.toString());
//		System.out.println("Position P SIZE: " + posP.ids.size());
//		System.out.println("Position Q BEFORE: " + posQ.toString());
//		System.out.println("Position Q SIZE: " + posQ.ids.size());
		
		if(posP.ids.size() == 0) {
			posP = CGen.genPosition(CGen.genIdentifierLseq(CRDTUtility.base(depth - 1), 0));
			//System.out.println("Position P NEW: " + posP.toString());
		}
		
		if(posQ.ids.size() == 0) {
			posQ = CGen.genPosition(CGen.genIdentifierLseq(CRDTUtility.base(depth), 0));
			//ystem.out.println("Position Q NEW: " + posQ.toString());
		}

		//compares the identifiers in the 1st position
		//if -1 (ie q is greater than p for both site and position)
		
		List<Identifier> build = new ArrayList<Identifier>(); 
		
		switch(CRDTUtility.compareIdentifier(posP.ids.get(0), posQ.ids.get(0))) {
			
			case -1:

				int	interval = CRDTUtility.prefix(posQ) - CRDTUtility.prefix(posP) - 1;
				
				if(interval > 1) {

					build.add(CGen.genIdentifierLseq(alloc(Math.min(boundary, interval), posP.ids.get(0).position, posQ.ids.get(0).position, depth), siteId));
					return build;
				}
				
//				else if(interval == 1 && siteId > posP.ids.get(0).siteId) {
//					
//					//build.add(CGen.genIdentifier(posP.ids.get(0).position, siteId));	
//					build.add(CGen.genIdentifierLseq(alloc(Math.min(boundary, interval), posP.ids.get(0).position, posQ.ids.get(0).position, depth), siteId));						
//					return build;
//				}
				
				else {
					
					build.add(posP.ids.get(0));
					
					posP.ids = posP.ids.subList(1, posP.ids.size());
					posQ.ids = posQ.ids.subList(1, posQ.ids.size());
					
					build.addAll(generateLinePositionLseq(posP, posQ, siteId, modify, setLseq, depth));
					return build;
				}
			
			case 0:
				
				build.add(posP.ids.get(0));
				
				posP.ids.subList(1, posP.ids.size());
				posQ.ids.subList(1, posQ.ids.size());
				
				build.addAll(generateLinePositionLseq(posP, posQ, siteId, modify, setLseq, depth));
				return build;
			
			case 1:				
				
				throw new Exception("Position Q was less than Position P!");
		}
		
		return build;
	}
	
	private static int alloc(int step, int posP, int posQ, int depth) {
		
		int id = 0;
		int addVal = 0;
		int subVal = 0;
		
//		System.out.println("\nStep: " + step);
//		System.out.println("Pos: " + posP);		
//		System.out.println("Depth: " + depth);
//		System.out.println("Base at Depth: " + CRDTUtility.base(depth));
//		System.out.println("Boundary+: " + strategy.get(depth));
		
		if(!strategy.containsKey(depth)) {
			
			if(force) {
				strategy.put(depth, boundaryPlus);
			
			} else {
				Boolean rand = CRDTUtility.randomBool();
				strategy.put(depth, rand);
			}
		}
		
		if(strategy.get(depth)){	//boundary+
			
			addVal = CRDTUtility.randomInt(0, step) + 1;			
			id = posP + addVal;
			
		} else {	//boundary-
			
			subVal = CRDTUtility.randomInt(0, step) + 1;
			id = posQ - subVal;
		}

		return id;
	}
	
	public static Map<Integer, Boolean> getStrategy(){
		return strategy;
	}
	
	public static void setBoundary(int boundary) {
		LogootCRDT.boundary = boundary;
	}
	
	public static int getBoundary() {
		return boundary;
	}
}

















//	private static List<Identifier> generateLinePositionX(Position posP, Position posQ, long siteId, Boolean modify, Boolean setLseq) throws Exception {
//		
////this method calculates the proper Identifier (list of positions) that means the new sequence atom can be added between position p and q
//	
//if(posP.ids.size() == 0) {
//posP = CGen.genPosition(CGen.genIdentifierMin());
//}
//
//if(posQ.ids.size() == 0) {
//posQ = CGen.genPosition(CGen.genIdentifierMax());
//}
//
////compares the identifiers in the 1st position
////if -1 (ie q is greater than p for both site and position)
//
//List<Identifier> build = new ArrayList<Identifier>(); 
//
//switch(CRDTUtility.compareIdentifier(posP.ids.get(0), posQ.ids.get(0))) {
//
//case -1:
//					
//	int interval = CRDTUtility.prefix(posQ) - CRDTUtility.prefix(posP);
//	
//	if(interval > 1) {
//
//		if(modify)
//			build.add(CGen.genIdentifier(posP.ids.get(0).position + 1, siteId));
//		else 
//			build.add(CGen.genIdentifier(CRDTUtility.randomInt(posP.ids.get(0).position, posQ.ids.get(0).position), siteId));
//		return build;
//	}
//	
//	else if(interval == 1 && siteId > posP.ids.get(0).siteId) {
//		
//		build.add(CGen.genIdentifier(posP.ids.get(0).position, siteId));
//		return build;
//		
//	} else {
//		
//		build.add(posP.ids.get(0));
//		
//		posP.ids = posP.ids.subList(1, posP.ids.size());
//		posQ.ids = posQ.ids.subList(1, posQ.ids.size());
//		
//		build.addAll(generateLinePosition(posP, posQ, siteId, modify, setLseq));
//		return build;
//	}
//
//case 0:
//	
//	build.add(posP.ids.get(0));
//	
//	posP.ids.subList(1, posP.ids.size());
//	posQ.ids.subList(1, posQ.ids.size());
//	
//	build.addAll(generateLinePosition(posP, posQ, siteId, modify, setLseq));
//	return build;
//
//case 1:				
//	
//	throw new Exception("Position Q was less than Position P!");
//}
//
//return build;
//}
//}