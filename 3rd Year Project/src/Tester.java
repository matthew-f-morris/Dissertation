import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.project.crdt.CGen;
import com.project.datatypes.VVPair;
import com.project.network.Node;
import com.project.utils.CRDTUtility;
import com.project.utils.MsgGen;

public class Tester {
	
	public static Node node;
	public static Scanner scanner;
	
	public static void main(String[] args) {
		
		VVPair p = new VVPair(0L, 6);
		VVPair q = new VVPair(1L, 7);
		VVPair x = new VVPair(2L, 9);
		
		List<VVPair> a = new ArrayList<VVPair>();
		List<VVPair> b = new ArrayList<VVPair>();
		
		a.add(p);
		a.add(q);
		
		b.add(q);
		b.add(p);
		b.add(x);
		
		Collections.sort(a);
		Collections.sort(b);
		
		System.out.println("A: " + a.toString());
		System.out.println("B: " + b.toString());
		
		System.out.println("Result: " + CRDTUtility.compareVector(b, a));
	}
}
   