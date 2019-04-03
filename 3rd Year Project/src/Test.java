import com.project.controller.DocumentController;
import com.project.crdt.LogootDocument;
import com.project.utils.MsgGen;

public class Test {

	private static long site = 4L;	
	
	public static void main(String[] args) throws Exception {
		
		LogootDocument doc = new LogootDocument(site);
		
		doc.modify(false);
		doc.setLseq(true);
		
		for(int i = 0; i < 10; i++) {
			doc.addMessage(MsgGen.getMsg(), MsgGen.getSite());
		}
		
		doc.printInfo();
		DocumentController.printDoc(doc);
	}	
}
