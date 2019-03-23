import com.project.crdt.LogootDocument;

public class Test {

	public static void main(String[] args) {
	
		String site = "site 1";
		LogootDocument doc = new LogootDocument(site);
		doc.initDocument();
		doc.addMessage("YAA", site);
		doc.addMessage("Hi", "site 2");
		doc.addMessage("Hello", "site 3");
		doc.addMessage("How are you?", "site 4");
		doc.addMessage("How are you?", "site 4");
		doc.addMessage("How are you?", "site 4");
		doc.addMessage("How are you?", "site 4");
		doc.addMessage("How are you?", "site 4");
		doc.addMessage("How are you?", "site 4");
		doc.addMessage("How are you?", "site 4");
		doc.addMessage("How are you?", "site 4");
		doc.addMessage("How are you?", "site 4");
//		doc.addMessage("How are you?", "site 4");
//		doc.addMessage("How are you?", "site 4");
//		doc.addMessage("How are you?", "site 4");
//		doc.addMessage("How are you?", "site 4");
//		doc.addMessage("How are you?", "site 4");
//		doc.addMessage("How are you?", "site 4");
//		doc.addMessage("How are you?", "site 4");
//		doc.addMessage("How are you?", "site 4");
//		doc.addMessage("How are you?", "site 4");
		doc.print();
	}	
}
