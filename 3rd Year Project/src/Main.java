
import java.util.Scanner;

import com.project.controller.CRDTController;
import com.project.network.Node;
import com.project.utils.Message;
import com.project.utils.MsgGen;

public class Main {

	static Scanner scanner;
	static Node node;

	public static void main(String[] args) {		
		
		scanner = new Scanner(System.in);
		
		node = new Node();
		node.queueToSend("TO ALL!");
		
//		InputScanner input = new InputScanner();
//		Thread inputThread = new Thread(input);
//		inputThread.setName("INPUT SCANNER");
//		inputThread.start();
		
		CRDTController.modifyDoc(true);
		
		for(int i = 0; i < 30000; i++) {		
			node.bypass(MsgGen.getMsg(), MsgGen.getSite());
		}
		
		CRDTController.printDocStats();
		
		CRDTController.clear();
		CRDTController.modifyDoc(false);
		
		for(int i = 0; i < 1000; i++) {		
			node.bypass(MsgGen.getMsg(), MsgGen.getSite());
		}
		
		CRDTController.printDocStats();		
		node.shutdown();
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
