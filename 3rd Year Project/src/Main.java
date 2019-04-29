import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import com.project.controller.DocumentController;
import com.project.network.Node;
import com.project.utils.MsgGen;

public class Main {

	static Scanner scanner;
	static Node node;
	private static Boolean redirect = false;
	private static int pause = 1000;

	public static void main(String[] args) {		
			
		if(redirect) {
			
			PrintStream out;	
			
			try {				
				out = new PrintStream(new FileOutputStream(new File("test_data.txt")));
				System.setOut(out);				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		node = new Node();
		node.joinNetwork();
		startScanner();
		
		if(redirect) {
			System.setOut(System.out);
			System.out.println(" // Ended Redirect //  ");
		}		
	}
	
	private static void test(int exponent) {
		
		for(int i = 1; i < Math.pow(2, exponent); i=i*2) {
			
			System.out.println("NEXT TEST, I = " + i + "");
			
			runBasic("Basic", i, true, false);			
			testWait(5000);
			
			runModified("Modified", i, true, true);
			testWait(5000);
			
			//toFile, force, force boundary+ or boundary-
			runLSEQ("LSEQ", i, true, false, false);
			testWait(5000);
			
			runLSEQ("LSEQ force boundary -", i, true, true, false);
			testWait(5000);
			
			runLSEQ("LSEQ force boundary +", i, true, true, true);
			
		}		
	}
		
	private static void testLAN(int i) throws InterruptedException {
		
		runBasicLAN("LAN Basic", i, true, false);
		testWait(5000);
		
//		runModifiedLAN("LAN Modified", i, true, true);
//		testWait(5000);
//		
//		//toFile, force, force boundary+ or boundary-
//		runLSEQLAN("LAN LSEQ", i, true, false, false);
//		testWait(5000);
//		
//		runLSEQLAN("LAN LSEQ force boundary -", i, true, true, false);
//		testWait(5000);
//		
//		runLSEQLAN("LAN LSEQ force boundary +", i, true, true, true);
//		testWait(5000);	
	}
	
	private static void runBasic(String title, int num, Boolean toFile, Boolean modify) {
		
		DocumentController.modifyDoc(modify);
		
		for(int i = 0; i < num; i++) {		
			node.bypass(MsgGen.getMsg(), MsgGen.getSite());
		}		
		
		DocumentController.printDocStats();	
		
		if(toFile)
			DocumentController.printDoc(title, true);
	}
		
	private static void runModified(String title, int num, Boolean toFile, Boolean modify) {
		
		DocumentController.modifyDoc(modify);
		
		for(int i = 0; i < num; i++) {		
			node.bypass(MsgGen.getMsg(), MsgGen.getSite());
		}
		
		DocumentController.printDocStats();
		
		if(toFile)
			DocumentController.printDoc(title, true);
	}
	
	private static void runLSEQ(String title, int num, Boolean toFile, Boolean force, Boolean boundaryPlus) {
		
		DocumentController.lseqForce(force, boundaryPlus);
		DocumentController.setLseq(true);
		
		for(int i = 0; i < num; i++) {		
			node.bypass(MsgGen.getMsg(), MsgGen.getSite());
		}
		
		DocumentController.printDocStats();	
		
		if(toFile)
			DocumentController.printDoc(title, true);
		
		DocumentController.printStrategy();
	}
	
	private static void runBasicLAN(String title, int num, Boolean toFile, Boolean modify) throws InterruptedException {
		
		DocumentController.modifyDoc(modify);
		
		for(int i = 0; i < num; i++) {		
			node.queueToSend(MsgGen.getMsg());
			Thread.sleep(pause);
		}		
		
		DocumentController.printDocStats();	
		
		if(toFile)
			DocumentController.printDoc(title, true);
		
	}
	
	private static void runModifiedLAN(String title, int num, Boolean toFile, Boolean modify) throws InterruptedException {
		
		DocumentController.modifyDoc(modify);
		
		for(int i = 0; i < num; i++) {		
			node.queueToSend(MsgGen.getMsg());
			Thread.sleep(pause);
		}
		
		DocumentController.printDocStats();
		
		if(toFile)
			DocumentController.printDoc(title, true);
	}
	
	private static void runLSEQLAN(String title, int num, Boolean toFile, Boolean force, Boolean boundaryPlus) throws InterruptedException {
		
		DocumentController.lseqForce(force, boundaryPlus);
		DocumentController.setLseq(true);
		
		for(int i = 0; i < num; i++) {		
			node.queueToSend(MsgGen.getMsg());
			Thread.sleep(pause);
		}
		
		DocumentController.printDocStats();	
		
		if(toFile)
			DocumentController.printDoc(title, true);
		
		DocumentController.printStrategy();
	}
	
	private static void testWait(int time) {
		
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void startScanner() {
		
		scanner = new Scanner(System.in);
		InputScanner input = new InputScanner();
		Thread inputThread = new Thread(input);
		inputThread.setName("INPUT SCANNER");
		inputThread.start();
	}
	
	static class InputScanner extends Thread {
		
		boolean isRunning = true;
		
		public void run() {
			
			while(isRunning) {	
				
				String text = scanner.nextLine();
				
				if(text.equals("LEAVE")) {
					node.leaveNetwork();
				} else if(text.equals("JOIN")){
					node.joinNetwork();
				} else if(text.equals("SHUTDOWN")) {
					node.shutdown();
					isRunning = false;
				} else if(text.equals("PRINT_CRDT")){
					node.print(true);
				} else if(text.equals("PRINT_PEERS")){
					node.viewPeers();
				} else if(text.equals("PRINT_DATA")){
					node.viewPeers();
				} else {
					node.queueToSend(text);
				}
			}
		}
	}
}

/*
 * 
 * Maybe consider programming a TCP backup
 * 
 * For each new broadcasted peerdata thing received, attempt to connect using TCP and sending peerdata in case
 * they didn't receive the data 
 * 
 * 
 * 
 * 
 * 
 */
