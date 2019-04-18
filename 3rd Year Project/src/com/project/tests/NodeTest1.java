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

class NodeTest1 {

	static Node node = new Node();
	static ThreadManager manager = node.getThreadManager();
	static MessageController ctrl = node.getMessageController();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		node.initThreads();
	}
	
	@Test
	@DisplayName("Test Node Initialize")
	void testInit() {
		
		assertAll("Node Properties",
			() -> assertTrue(manager.checkRunning(), "Threads Stoped when they shouldnt have"),
			() -> assertTrue(ctrl.messagesRecieved.size() == 0, "Recieved Messages should be empty"),
			() -> assertTrue(ctrl.messagesToSend.size() == 0, "Messages to send should be empty"),
			() -> assertTrue(node.isJoined(), "Failed To Join")
		);	
	}
}
