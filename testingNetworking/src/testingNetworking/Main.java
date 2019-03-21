package testingNetworking;

import java.util.Random;

public class Main {
	
	public static void main(String[] args) {
		
		Random rand = new Random();
		
		for(int i = 0; i < 100; i++) {			
			int low = 1;
			int high = 10;
			int random = rand.nextInt(high - low) + low;
			
			System.out.println("Random number = " + random);
		}
	}
}
