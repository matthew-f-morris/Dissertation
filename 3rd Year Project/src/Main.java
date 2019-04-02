
import java.util.Scanner;

import com.project.network.Node;
import com.project.utils.Message;

public class Main {

	static Scanner scanner;
	static Node node;

	public static void main(String[] args) {		
		
		scanner = new Scanner(System.in);
		
		node = new Node();
		node.queueToSend(new Message(node.nodeInfo, "TO ALL!"));
		
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
				} else {
					node.queueToSend(new Message(node.nodeInfo, text));
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
