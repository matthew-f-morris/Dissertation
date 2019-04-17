package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;

import com.project.Thread.ThreadManager;
import com.project.network.Node;

import junit.runner.Version;

//sort out ordering for these things!

class ThreadManagerTest0 {

	public static Node node;
	public static ThreadManager manager;
	
	@BeforeEach
	void setUp() throws Exception {
		node = new Node();
		manager = node.getThreadManager();
	}
	
	@AfterEach
	void tearDown() throws Exception {
		node = null;
		manager = null;
	}
	
	@Test
	@DisplayName("Test Thread Manager Generates Threads")
	void testThreadManagerGen() {

		assertAll("Threads",
			() -> assertNotNull(manager.getBroadcastListener(), "Thread Not Created"),
			() -> assertNotNull(manager.getBroadcast(), "Thread Not Created"),
			() -> assertNotNull(manager.getReciever(), "Thread Not Created"),
			() -> assertNotNull(manager.getSender(), "Thread Not Created"),
			() -> assertNotNull(manager.getResender(), "Thread Not Created")
		);
	}	
}
