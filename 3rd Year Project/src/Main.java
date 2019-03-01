import java.util.Scanner;

import com.project.network.Node;
import com.project.utils.Message;

public class Main {
	
	static Scanner scanner;
	
	public static void main (String[] args) {
		
		scanner = new Scanner(System.in);
		
		Node node = new Node();
		node.initialise();	
		node.queueToSend(new Message("TO ALL!"));
		
		while(true) {
			
			String text = scanner.nextLine();
			node.queueToSend(new Message(text));
			
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
