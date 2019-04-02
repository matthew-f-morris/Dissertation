package com.project.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CRDTFileGen {
	
	private static Thread thread;
	
	public static void start(ArrayList<String> str) {
		
		thread = new Thread(new FileHandler(str));
		thread.start();
	}
	
	static class FileHandler implements Runnable {
		
		ArrayList<String> array;
		
		public FileHandler(ArrayList<String> array) {
			this.array = array;
		}

		public void run() {
						
			FileWriter writer = null;
			
			try {
				writer = new FileWriter("output.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Writing document to file...");
			
			for(String str: array) {
			  try {
				writer.write(str);
			  } catch (IOException e) {
				e.printStackTrace();
			  }
			}			
			
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Finished writing to file!");
		}
	}
}

