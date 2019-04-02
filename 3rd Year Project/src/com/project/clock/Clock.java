package com.project.clock;

public class Clock {
	
	//Local logical clock simply keeps track of the number of messages sent
	//Increments by one every time a message is sent
	
	public static int counter = 0;
	
	public Clock() {	
		counter = 0;
	}
	
	public Clock(int set) throws Exception {
		
		//sanity check to make sure a clock isnt initialised with a negative numebr
		
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
	
	//makes sure integer is not negative or too large
	
	private static int sanityCheck(int check) {	
		
		if(0 <= check && check <= Integer.MAX_VALUE)	
			return check;	
		return -1;
	}
}
