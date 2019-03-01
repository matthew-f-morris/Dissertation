import com.project.network.Node;
import com.project.utils.Message;

public class Main {
	
	public static void main (String[] args) {
		
		Node node = new Node();
		node.initialise();	
		System.out.println("CHECKPOINT 1");
		node.queueToSend(new Message("IronWorks!"));
		System.out.println("CHECKPOINT 2");
		node.viewMessages("Send");
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
