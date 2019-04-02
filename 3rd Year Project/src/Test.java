import com.project.controller.CRDTController;
import com.project.crdt.LogootDocument;
import com.project.utils.MsgGen;

public class Test {

	private static long site = 4L;	
	
	public static void main(String[] args) throws Exception {
		
		LogootDocument doc = new LogootDocument(site);
		
		doc.modify(true);
		
		for(int i = 0; i < 100000; i++) {
			doc.addMessage(MsgGen.getMsg(), MsgGen.getSite());
		}
		
		doc.printInfo();
		CRDTController.printDoc(doc);
	}	
}
