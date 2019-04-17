import com.project.utils.CRDTUtility;

public class Tester {
	
	public static void main(String[] args) {
		
		for(int i = -2; i < 8; i++) {
			System.out.println(CRDTUtility.base(0));
		}
	}
}
