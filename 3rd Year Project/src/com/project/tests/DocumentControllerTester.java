package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.controller.DocumentController;
import com.project.utils.Message;
import com.project.utils.PeerData;

class DocumentControllerTester {

	private static PeerData data;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		DocumentController.init(5L);
		data = new PeerData(1L, "hostname", InetAddress.getLocalHost(), 1000);
	}

	@Test
	@DisplayName("Test Handle Message")
	void testHandleMessage() {
		
		Message msg = DocumentController.handleMessage("Test Message", data);
		
		assertAll("msg Properties",
			() -> assertTrue(msg.getText().equals("Test Message")),
			() -> assertTrue(msg.getPeerData().getHostname().equals("hostname")),
			() -> assertTrue(msg.getAtom().message.equals("Test Message"))
		);
	}
	
	@Test
	@DisplayName("Test Doc Size")
	void testGetDocSize() {
		
		DocumentController.printDocStats();		
		assertTrue(DocumentController.getDocSize() == 0);
	}
	
	@Test
	@DisplayName("Test Modify")
	void testModifyDoc() {		
		assertTrue(DocumentController.modifyDoc(true));
	}
	

	@Test
	@DisplayName("Test Seq Lseq")
	void testSetLseq() {		
		assertTrue(DocumentController.setLseq(true));
	}
}
