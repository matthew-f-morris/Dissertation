package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.project.clock.Clock;

 public class ClockTest {

	private Clock clock;

	@BeforeEach
	void setUp() throws Exception {
		clock = null;
	}

	@Test
	void testClock() {
		
		Clock clock = new Clock();
		assertNotNull(clock);
	}
	
	@Test
	void testClockInitialize() {
		
		clock = new Clock();
		assertEquals(0, Clock.counter);
	}
	
	@Test
	void testClockSet() {
		
		try {
			clock = new Clock(56);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(56, Clock.counter);
	}
	
	@Test
	void testClockBadSet() {
						
		assertThrows(Exception.class, () -> {			
			Clock clock = new Clock(-1);			
		});
	}
	
	@Test
	void testCounterIncrement() {
		
		Clock clock = new Clock();
		
		for(int i = 0; i < 1000; i++) {
			Clock.increment();
		}
		
		assertEquals(1000, Clock.counter);
	}
}
