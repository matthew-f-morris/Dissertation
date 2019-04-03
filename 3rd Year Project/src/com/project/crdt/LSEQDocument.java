package com.project.crdt;

import com.project.datatypes.LSEQSequence;
import com.project.utils.CRDTUtility;

public class LSEQDocument {

	private long siteId;
	private LSEQSequence document;
	
	public LSEQDocument(long siteId) {
		
		this.siteId = siteId;
		document = new LSEQSequence();
		initDocument();
	}

	private void initDocument() {
		
		ComponentGenerator.genLSEQidBegin();
		ComponentGenerator.genLSEQidEnd();
		
	}	
}
