package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.Thread.ResenderManager;
import com.project.network.Node;

class ResenderManagerTest {

	private static Node node = new Node();
	private static ResenderManager test = new ResenderManager(node.getMessageController());
	
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
