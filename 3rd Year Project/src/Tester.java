import com.project.Thread.BroadcastManager;
import com.project.crdt.CGen;
import com.project.network.Node;
import com.project.utils.CRDTUtility;

public class Tester {
	
	public static void main(String[] args) {
		
		Node node = new Node();
		BroadcastManager test = new BroadcastManager(node);
		
		Boolean starts = false;
		Boolean stops = false;
		
		test.start();
		starts = test.threadState();
		
		test.stop();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stops = !test.threadState();
		
		System.out.println("Starts: " + starts);
		System.out.println("Stops: " + stops);
		
	}
}
