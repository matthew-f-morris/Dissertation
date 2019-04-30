package com.project.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CRDTFileGen {
	
	private static Thread thread;
	
	public static void start(ArrayList<String> str, String name) {
		
		thread = new Thread(new FileHandler(str, name));
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static class FileHandler implements Runnable {
		
		ArrayList<String> array;
		String name = "";
		
		public FileHandler(ArrayList<String> array, String name) {
			this.array = array;
			this.name = name;
		}

		public void run() {
						
			FileWriter writer = null;
			
			try {
				writer = new FileWriter(name + ".txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Writing document to file...");
			
			for(String str: array) {
			  try {
				writer.write(str + "\r\n");
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

