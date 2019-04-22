import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.project.clock.VersionVector;
import com.project.crdt.CGen;
import com.project.datatypes.VVPair;
import com.project.network.Node;
import com.project.utils.CRDTUtility;
import com.project.utils.MsgGen;

public class Tester {
	
	public static Node node;
	public static Scanner scanner;
	
	public static void main(String[] args) {
		
		VersionVector.init(0L, 0);
		
		VVPair p = new VVPair(0L, 6);
		VVPair q = new VVPair(4L, 8);
		
		List<VVPair> a = new ArrayList<VVPair>();
		
		a.add(p);
		a.add(q);
				
		Collections.sort(a);
		
		try {
			VersionVector.add(4L, 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		System.out.println("A: " + a.toString());
//		System.out.println("B: " + VersionVector.vv);
		
		VersionVector.sync(a);
		
		System.out.println("A: " + a.toString());
		System.out.println("B: " + VersionVector.vv);
	}
}
   