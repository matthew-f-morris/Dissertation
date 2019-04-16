package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.project.clock.Clock;

 public class ClockTest {

	private Clock clock = null;

	@BeforeEach
	void setUp() throws Exception {
		clock = null;
	}

	@Test
	@DisplayName("Clock Basic Test")
	void testClock() {
		
		Clock clock = new Clock();
		assertNotNull(clock);
	}
	
	@Test
	@DisplayName("Clock Basic Initialization")
	void testClockInitialize() {
		
		clock = new Clock();
		assertEquals(0, Clock.counter);
	}
	
	@ParameterizedTest
	@DisplayName("Clock Test Correct Integer Initialization")
	@ValueSource(ints = { 4, 50, 1000, 100000, 123913})
	void testClockSet(int num) {
		
		try {
			clock = new Clock(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(num, Clock.counter);
	}
	
	@Test
	@DisplayName("Clock Test Throws Exception")
	void testClockBadSet() {
						
		assertThrows(Exception.class, () -> {			
			Clock clock = new Clock(-1);			
		});
	}
	
	@Test
	@DisplayName("Clock Test Increment")
	void testCounterIncrement() {
		
		Clock clock = new Clock();
		
		for(int i = 0; i < 1000; i++) {
			Clock.increment();
		}
		
		assertEquals(1000, Clock.counter);
	}
}
