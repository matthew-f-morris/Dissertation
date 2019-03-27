package com.project.crdt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.project.clock.Clock;
import com.project.datatypes.Identifier;
import com.project.datatypes.Position;
import com.project.datatypes.SequenceAtom;

public class LogootCRDT {
	
	static Random rand = new Random();
	
	private static Clock clock;
	private static ComponentGenerator maker;
	//private static CRDTUtility util;
	
	public LogootCRDT(Clock clock, ComponentGenerator maker, long siteIdCopy) {
		
		LogootCRDT.clock = clock;
		LogootCRDT.maker = maker;
		//LogootCRDT.util = util;
	}
	
	public static SequenceAtom generate(String message, Position p, Position q, long siteId) throws Exception {	
		
		//return maker.genSequenceAtom((maker.genAtomIdentifier(generateLinePosition(p, q, siteId), clock.counter)), message);
		Position pos = new Position(generateLinePosition(p, q, siteId));		
		return maker.genSequenceAtom((maker.genAtomIdentifier(pos, clock.counter)), message);
	}
		
	private static List<Identifier> generateLinePosition(Position posP, Position posQ, long siteId) throws Exception {
					
		//takes in a position p and position q
		//checks to see if the number of id's are zero
		//if they are set them to the relative min and max positions
		//means if that one position is longer than the other, the algorithm
		//can compare the positions properly
				
		if(posP.ids.size() == 0) {
			posP = maker.genPosition(maker.genIdentifierMin());
		}
		
		if(posQ.ids.size() == 0) {
			posQ = maker.genPosition(maker.genIdentifierMax());
		}
		
		//compares the identifiers in the 1st position
		//if -1 (ie q is greater than p for both site and position)
		
		List<Identifier> build = new ArrayList<Identifier>(); 
		
		switch(CRDTUtility.compareIdentifier(posP.ids.get(0), posQ.ids.get(0))) {
			
			case -1:
								
				int interval = CRDTUtility.prefix(posQ) - CRDTUtility.prefix(posP);
				
				if(interval > 1) {

					build.add(maker.genIdentifier(CRDTUtility.randomInt(posP.ids.get(0).position, posQ.ids.get(0).position), siteId));
					return build;
				}
				
				else if(interval == 1 && siteId > posP.ids.get(0).siteId) {
					
					build.add(maker.genIdentifier(posP.ids.get(0).position, siteId));
					return build;
					
				} else {
					
					build.add(posP.ids.get(0));
					
					posP.ids = posP.ids.subList(1, posP.ids.size());
					posQ.ids = posQ.ids.subList(1, posQ.ids.size());
					
					build.addAll(generateLinePosition(posP, posQ, siteId));
					return build;
				}
			
			case 0:
				
				build.add(posP.ids.get(0));
				
				posP.ids.subList(1, posP.ids.size());
				posQ.ids.subList(1, posQ.ids.size());
				
				build.addAll(generateLinePosition(posP, posQ, siteId));
				return build;
			
			case 1:				
				
				throw new Exception("Position Q was less than Position P!");
		}
		
		return build;
	}
}
