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

class ThreadManagerTest2 {

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
	@DisplayName("Test Thread Manager Stops Threads")
	void testThreadManagerStop() {
		manager.joinNetwork();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		manager.leaveNetwork();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue(manager.checkStopped(), "1 or more threads is still running");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
