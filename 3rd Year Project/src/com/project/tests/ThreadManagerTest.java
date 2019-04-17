package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.Thread.ThreadManager;
import com.project.network.Node;

class ThreadManagerTest {

	public static Node node = new Node();
	public static ThreadManager manager = node.getThreadManager();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
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
	@DisplayName("Test Thread Manager Generates Threads")
	void testThreadManagerGen() {
		assertAll("Threads",
			() -> assertNotNull(manager.getBroadcastListener(), "Thread Not Created"),
			() -> assertNotNull(manager.getBroadcast(), "Thread Not Created"),
			() -> assertNotNull(manager.getReciever(), "Thread Not Created"),
			() -> assertNotNull(manager.getSender(), "Thread Not Created"),
			() -> assertNotNull(manager.getResender(), "Thread Not Created"),
			() -> assertNotNull(manager.getController(), "Thread Not Created")
		);
	}
	
	@Test
	@DisplayName("Test Thread Manager Starts Threads")
	void testThreadManagerStart() {
		assertAll("Started",
			() -> assertTrue(manager.getBroadcast().)
		);
	}
}
