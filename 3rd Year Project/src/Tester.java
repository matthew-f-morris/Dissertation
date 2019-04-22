import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.project.crdt.CGen;
import com.project.datatypes.VVPair;
import com.project.network.Node;
import com.project.utils.CRDTUtility;
import com.project.utils.MsgGen;

public class Tester {
	
	public static Node node;
	public static Scanner scanner;
	
	public static void main(String[] args) {
		
		node = new Node();
		node.initThreads();
		startScanner();
		//node.bypass(MsgGen.getMsg(), MsgGen.getSite());
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
					node.printData();
				} else if(text.equals("PRINT_VV")){
					node.viewVersionVector();
				} else {
					node.queueToSend(text);
				}
			}
		}
	}
}
   