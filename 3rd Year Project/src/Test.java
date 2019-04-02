import com.project.crdt.LogootDocument;
import com.project.utils.MsgGen;

public class Test {

	private static long site = 4L;	
	
	public static void main(String[] args) throws Exception {
		
		LogootDocument doc = new LogootDocument(site);
		
//		for(int i = 0; i < 20000; i++) {
//			doc.addMessage(MsgGen.getMsg(), MsgGen.getSite());
//		}
//		
//		doc.printInfo();
		
		doc.modify(true);
		doc.clear();
		
		for(int i = 0; i < 10000; i++) {
			doc.addMessage(MsgGen.getMsg(), MsgGen.getSite());
		}

		doc.printInfo();
	}	
}
