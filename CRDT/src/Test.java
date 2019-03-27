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
		
		long site = 4L;	
		long lower = 1L;
		long upper = 20L;
		
		LogootDocument doc = new LogootDocument(site);
		
		for(int i = 0; i < 100; i++) {
			long rand = lower + (long) (Math.random() * (upper - lower));
			doc.addMessage(new String("Message : " + i), rand);
		}
		
		doc.print();
		
	}	
}
