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

class ThreadManagerTest3 {

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
	@DisplayName("Test Thread Manager Restarts Thread")
	void testThreadRestart() {		
		
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
		
		manager.joinNetwork();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue(manager.checkRunning(), "1 or more threads did not successfully restart");
		
		manager.leaveNetwork();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
