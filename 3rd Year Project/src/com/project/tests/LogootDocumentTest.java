package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.crdt.LogootDocument;

class LogootDocumentTest {

	private static LogootDocument doc = new LogootDocument(5L);

	@Test
	@DisplayName("Test Doc Init Correctly")
	void testInit() {
		
		doc.clear();
		assertAll("Document Properties",
			() -> assertTrue(!doc.getModify(), "Initialized Incorrectly"),
			() -> assertTrue(!doc.getSetLseq(), "Initialized Incorrectly"),
			() -> assertTrue(doc.getSequence().arr.size() == 2)
		);
	}
	
	@Test
	@DisplayName("Test Add Message")
	void testAddMessage() throws Exception {
		
		doc.addMessage("New Message", 10L);
		
		assertAll("Document Properties",
			() -> assertTrue(!doc.getModify(), "Initialized Incorrectly"),
			() -> assertTrue(!doc.getSetLseq(), "Initialized Incorrectly"),
			() -> assertTrue(doc.getSequence().arr.size() == 3),
			() -> assertTrue(doc.getSequence().arr.get(1).message.equals("New Message")),
			() -> assertTrue(doc.getSequence().arr.get(1).atomId.position.ids.get(0).siteId == 10)
		);
	}
	
	@Test
	@DisplayName("Test LSEQ Doc Init Correctly")
	void testInitLseq() {
		
		doc.clearLSEQ();
		
		assertAll("Document Properties",
			() -> assertTrue(!doc.getModify(), "Initialized Incorrectly"),
			() -> assertTrue(doc.getSetLseq(), "Initialized Incorrectly"),
			() -> assertTrue(doc.getSequence().arr.size() == 2)
		);
	}
	
	@Test
	@DisplayName("Test Add Message LSEQ")
	void testAddMessageLseq() throws Exception {
		
		doc.clearLSEQ();
		doc.addMessage("New Message", 10L);
		
		assertAll("Document Properties",
			() -> assertTrue(!doc.getModify(), "Initialized Incorrectly"),
			() -> assertTrue(doc.getSetLseq(), "Initialized Incorrectly"),
			() -> assertTrue(doc.getSequence().arr.size() == 3),
			() -> assertTrue(doc.getSequence().arr.get(1).message.equals("New Message")),
			() -> assertTrue(doc.getSequence().arr.get(1).atomId.position.ids.get(0).siteId == 10)
		);
	}
}
