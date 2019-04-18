package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.Thread.ThreadManager;
import com.project.controller.MessageController;
import com.project.network.Node;

class NodeTest3 {

	static Node node = new Node();
	static ThreadManager manager = node.getThreadManager();
	static MessageController ctrl = node.getMessageController();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		node.initThreads();
	}

	@Test
	@DisplayName("Test Adding New Peer")
	void testAddPeer() throws UnknownHostException {
		
		node.addPeer(999L, "Dave", InetAddress.getLocalHost(), 2020);
		
		assertAll("Node Properties",
			() -> assertTrue(!manager.checkStopped(), "Threads started up when they shouldnt have"),
			() -> assertTrue(node.getPeers().size() == 2),
			() -> assertTrue(node.isJoined(), "Failed To Join")
		);				
	}
}
