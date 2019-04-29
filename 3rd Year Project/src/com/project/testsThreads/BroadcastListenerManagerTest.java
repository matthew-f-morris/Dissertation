package com.project.testsThreads;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.Thread.BroadcastListenerManager;
import com.project.network.Node;

class BroadcastListenerManagerTest {

	private static Node node = new Node();
	private static BroadcastListenerManager test = new BroadcastListenerManager(node);
	
	@Test
	@DisplayName("Test Gen Broadcast Listener Manager")
	void test() {
		assertNotNull(test);
	}
	
	@Test
	@DisplayName("Test Thread Start and Stop")
	void testStart() {
		
		Boolean starts = false;
		Boolean stops = false;
		
		test.start();
		starts = test.threadState();
		test.stop();
		stops = !test.threadState();
		
		assertEquals(true, starts && stops);
	}
}
