package testingNetworking;

import java.util.ArrayList;

public class Main{
	
	public static void main(String[] args) {

		ArrayList<Integer> idP = new ArrayList<Integer>();
		
		idP.add(1);
		
		System.out.println("Size: " + idP.size());
		
		ArrayList<Integer> id2 = new ArrayList<Integer>(idP.subList(1, idP.size())); 
		
		System.out.println("Size: " + id2.size());
		
		
		ArrayList<Integer> id3 = new ArrayList<Integer>(id2.subList(1, id2.size())); 
		
		System.out.println("Size: " + id3.size());	
		
	}
}
