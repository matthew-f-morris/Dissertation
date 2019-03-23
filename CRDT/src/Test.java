import java.util.UUID;

import com.project.crdt.LogootDocument;

public class Test {

	public static void main(String[] args) throws Exception {
	
		long site1 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		long site2 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		long site3 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		long site4 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		long site5 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		long site6 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		
		System.out.println(site1);
		
		LogootDocument doc = new LogootDocument(site1);
		
		doc.initDocument();
		doc.addMessage("YAA", site2);
		doc.addMessage("Hi", site2);
		doc.addMessage("Hello", site3);
		doc.addMessage("How are you?", site4);
		doc.addMessage("How are you?", site5);
		doc.addMessage("How are you?", site6);
		doc.addMessage("How are you?", site4);
		doc.addMessage("How are you?", site5);
		doc.addMessage("How are you?", site6);
		doc.addMessage("How are you?", site4);
		doc.addMessage("How are you?", site5);
		doc.addMessage("How are you?", site6);
		doc.addMessage("How are you?", site4);
		doc.addMessage("How are you?", site5);
		doc.addMessage("How are you?", site6);
		doc.addMessage("How are you?", site4);
		doc.addMessage("How are you?", site5);
		doc.addMessage("How are you?", site6);
		doc.addMessage("How are you?", site4);
		doc.addMessage("How are you?", site5);
		doc.addMessage("How are you?", site6);
		doc.addMessage("How are you?", site4);
		doc.addMessage("How are you?", site5);
		doc.addMessage("How are you?", site6);
		doc.addMessage("How are you?", site4);
		doc.addMessage("How are you?", site5);
		doc.addMessage("How are you?", site6);
		doc.print();
	}	
}
