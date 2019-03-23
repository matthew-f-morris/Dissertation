package testingNetworking;

import java.util.Random;

public class Main{
	
	public static void main(String[] args) {

		Random rand = new Random();
		int max = 5;
		int min = 1;
		for(int i = 0; i < 100; i ++) {
			System.out.println(rand.nextInt(max - min -1) + min + 1);
		}
		
	}
}
