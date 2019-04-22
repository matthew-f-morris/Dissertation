import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.project.crdt.CGen;
import com.project.datatypes.VVPair;
import com.project.utils.CRDTUtility;

public class Tester {
	
	public static void main(String[] args) {
		
		VVPair a = new VVPair(1L, 4);
		VVPair b = new VVPair(2L, 3);
		VVPair c = new VVPair(3L, 0);
		
		List<VVPair> p = new ArrayList<VVPair>();
		List<VVPair> q = new ArrayList<VVPair>();
		
		p.add(a);
		q.add(b);
		
		q.add(a);
		p.add(b);
		
		p.add(new VVPair(4L, 7));
		q.add(new VVPair(4L, 6));
		
		Collections.sort(p);
		Collections.sort(q);
		
		System.out.println(p.toString());
		System.out.println(q.toString());
		
		System.out.println(CRDTUtility.compareVector(p, q));
	}
}
   