package com.project.crdt;

import java.util.Arrays;

import com.project.clock.Clock;
import com.project.datatypes.Sequence;

public class LogootDocument {
	
	private String siteId;
	public Sequence document;
	Clock clock = new Clock();
	CRDTUtilityFunctions utility = new CRDTUtilityFunctions(clock, new ComponentGenerator(siteId));
	
	public LogootDocument(String siteId) {
		
		this.siteId = siteId;
		document = new Sequence();
	}
	
	public void initDocument() {
		
		document.sequenceArray.add(CRDTUtilityFunctions.genStartAtom());
		document.sequenceArray.add(CRDTUtilityFunctions.genStopAtom());
	}
	
	public void print() {
		// make print function
	}
}
