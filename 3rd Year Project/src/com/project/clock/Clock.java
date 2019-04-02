package com.project.clock;

public class Clock {
	
	public static int counter = 0;
	
	public Clock() {	
		counter = 0;
	}
	
	public Clock(int set) throws Exception {
		
		if(sanityCheck(set) == -1) {			
			throw new Exception("Clock set to invalid value!");			
		} else {			
			counter = set;
		}
	}
	
	public static int increment() {		
		counter++;
		return counter;
	}
	
	private static int sanityCheck(int check) {	
		
		if(0 <= check && check <= Integer.MAX_VALUE)	
			return check;	
		return -1;
	}
}