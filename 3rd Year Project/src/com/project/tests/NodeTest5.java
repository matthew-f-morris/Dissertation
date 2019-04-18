package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.Thread.ThreadManager;
import com.project.controller.MessageController;
import com.project.network.Node;

class NodeTest5 {

	static Node node = new Node();
	static ThreadManager manager = node.getThreadManager();
	static MessageController ctrl = node.getMessageController();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		node.initThreads();
	}

	@Test
	@DisplayName("Test Rejoin")
	void testRejoin() throws InterruptedException {
		
		node.leaveNetwork();		
		Thread.sleep(1000);		
		node.joinNetwork();
		
		assertAll("Node Properties",
			() -> assertTrue(manager.checkRunning(), "Threads have not stopped like they should have"),
			() -> assertTrue(node.isJoined(), "Failed To leave network")
		);	
	}
}
