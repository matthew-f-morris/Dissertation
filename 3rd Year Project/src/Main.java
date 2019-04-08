
import java.util.Scanner;

import com.project.controller.DocumentController;
import com.project.network.Node;
import com.project.utils.CRDTUtility;
import com.project.utils.Message;
import com.project.utils.MsgGen;

public class Main {

	static Scanner scanner;
	static Node node;

	public static void main(String[] args) {		
				
		node = new Node();
		node.queueToSend("TO ALL!");
		
		//runBasic("Basic", 20, false);
		//runModified("Modified", 10, false);
		runLSEQ("LSEQ", 15, false);
		
		node.shutdown();
	}
	
	public static void runBasic(String title, int num, Boolean toFile) {
		
		DocumentController.modifyDoc(false);
		
		for(int i = 0; i < num; i++) {		
			node.bypass(MsgGen.getMsg(), MsgGen.getSite());
		}		
		
		DocumentController.printDocStats();	
		
		if(toFile)
			DocumentController.printDoc(title);
	}
	
	public static void runModified(String title, int num, Boolean toFile) {
		
		DocumentController.modifyDoc(true);
		
		for(int i = 0; i < num; i++) {		
			node.bypass(MsgGen.getMsg(), MsgGen.getSite());
		}
		
		DocumentController.printDocStats();
		
		if(toFile)
			DocumentController.printDoc(title);
	}
	
	public static void runLSEQ(String title, int num, Boolean toFile) {
		
		DocumentController.setLseq(true);
		DocumentController.printDocStats();
		
		for(int i = 0; i < num; i++) {		
			node.bypass("" + i, MsgGen.getSite());
			DocumentController.printDocStats();	
		}
		
		//DocumentController.printDocStats();	
		
		if(toFile)
			DocumentController.printDoc(title);
		
		DocumentController.printStrategy();
	}
	
	public static void startScanner() {
		
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
				} else if(text.equals("PRINT")){
					node.print();
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
