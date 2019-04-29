package com.project.testsThreads;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.Thread.BroadcastListenerManager;
import com.project.Thread.BroadcastManager;
import com.project.network.Node;

class BroadcastManagerTest {

	private static Node node = new Node();
	private static BroadcastManager test = new BroadcastManager(node);
	
	@Test
	@DisplayName("Test Gen Broadcast Manager")
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
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test.stop();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stops = !test.threadState();
		
		System.out.println("Starts: " + starts);
		System.out.println("Stops: " + stops);
		
		assertEquals(true, starts && stops);
	}
}
