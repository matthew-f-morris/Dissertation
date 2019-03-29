import com.project.crdt.LogootDocument;
import com.project.util.MsgGen;

public class Test {

	private static long site = 4L;	
	
	public static void main(String[] args) throws Exception {
		
		LogootDocument doc = new LogootDocument(site);
		
		for(int i = 0; i < 100; i++) {
			doc.addMessage(MsgGen.getMsg(), MsgGen.getSite());
		}
		
		doc.print();
	}	
}
