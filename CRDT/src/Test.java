import java.util.UUID;

import com.project.crdt.LogootDocument;

public class Test {

	public static void main(String[] args) throws Exception {
	
//		long site1 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
//		long site2 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
//		long site3 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
//		long site4 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
//		long site5 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
//		long site6 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		
		long site1 = 1L;
		long site2 = 2L;
		long site3 = 3L;
		long site4 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		long site5 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		long site6 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		
		//System.out.println(site1);
		
		LogootDocument doc = new LogootDocument(site1);
		
		doc.initDocument();
		doc.addMessage("Hello, world from site 1!", site1);
		doc.addMessage("I came from site 2!", site2);
		doc.addMessage("Hello from site 3!", site3);
//		doc.addMessage("How are you?", site4);
//		doc.addMessage("How are you?", site5);
//		doc.addMessage("How are you?", site6);
//		doc.addMessage("How are you?", site4);
//		doc.addMessage("How are you?", site5);
//		doc.addMessage("How are you?", site6);
//		doc.addMessage("How are you?", site4);
//		doc.addMessage("How are you?", site5);
//		doc.addMessage("How are you?", site6);
//		doc.addMessage("How are you?", site4);
//		doc.addMessage("How are you?", site5);
//		doc.addMessage("How are you?", site6);
//		doc.addMessage("How are you?", site4);
//		doc.addMessage("How are you?", site5);
//		doc.addMessage("How are you?", site6);
//		doc.addMessage("How are you?", site4);
//		doc.addMessage("How are you?", site5);
//		doc.addMessage("How are you?", site6);
//		doc.addMessage("How are you?", site4);
//		doc.addMessage("How are you?", site5);
//		doc.addMessage("How are you?", site6);
//		doc.addMessage("How are you?", site4);
//		doc.addMessage("How are you?", site5);
//		doc.addMessage("How are you?", site6);
		doc.print();
		
	}	
}
