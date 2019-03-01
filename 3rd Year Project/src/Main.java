import com.project.network.Node;
import com.project.utils.Message;

public class Main {
	
	public static void main (String[] args) {
		
		Node node = new Node();
		node.initialise();	
		node.queueToSend(new Message("TO ALL!"));
		//node.viewMessages("Send");
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
