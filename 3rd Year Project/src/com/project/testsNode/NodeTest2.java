package com.project.testsNode;

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

class NodeTest2 {
	static Node node = new Node();
	static ThreadManager manager = node.getThreadManager();
	static MessageController ctrl = node.getMessageController();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		node.initThreads();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test Send Message")
	void testQueueToSend() {
		
		node.queueToSend("Test Message");
		
		assertAll("Node Properties",
			() -> assertTrue(!manager.checkStopped(), "Threads started up when they shouldnt have"),
			() -> assertTrue(ctrl.messagesRecieved.size() == 0, "Recieved Messages should be empty"),
			() -> assertTrue(ctrl.messagesToSend.size() == 1, "Messages to send should be empty"),
			() -> assertTrue(node.isJoined(), "Failed To Join"),
			() -> assertTrue(ctrl.messagesToSend.getFirst().getText().equals("Test Message"), "Failed to queue messages correctly")
		);	
	}
}
