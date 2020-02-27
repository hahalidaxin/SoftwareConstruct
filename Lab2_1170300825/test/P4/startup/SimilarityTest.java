package P4.startup;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import P4.interval.IntervalConflictException;
import P4.interval.IntervalSet;
import P4.interval.MultiIntervalSet;

public class SimilarityTest {
	private String FILENAME="src/P4/startup/sleepy-kitties.txt";
	private Similarity sim;
	@Test public void SimilarityTest() {
		// TODO Auto-generated constructor stub
//		Set<String> newSet=new HashSet<String>();
//		newSet.add("123");
//		newSet.add("456");
//		Iterator<String>iterator=newSet.iterator();
//		while(iterator.hasNext()) {
//			System.out.println(iterator.next());
//		}
		System.out.println("测试Similarity");
		try {
			sim=new Similarity(new File(FILENAME));
			MultiIntervalSet<String> mulita=new MultiIntervalSet<>();
			MultiIntervalSet<String> mulitb=new MultiIntervalSet<>();
			mulita.insert(0, 1, "happy");
			mulita.insert(2, 4, "happy");
			mulita.insert(1, 2, "sad");
			
			
			mulitb.insert(1, 2, "sad");
			mulitb.insert(2, 3, "meh");
			mulitb.insert(3, 4, "happy");
			
			System.out.println(sim.similarity​(mulita, mulitb));
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IntervalConflictException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
//	测试CaculatePieceWiseMatching
	@Test public void testCaculatePieceWiseMatching() {
//		System.out.println(Similarity.rangeContain(1, 4, 3, 4));
//		IntervalSet<Integer> ita=IntervalSet.empty();
//		IntervalSet<Integer> itb=IntervalSet.empty();
//		try {
//			ita.insert(0, 1, 0);
//			ita.insert(3, 8, 1);
//			ita.insert(10, 15, 2);
//			ita.insert(15, 18, 3);
//			ita.insert(19, 20, 4);
//			itb.insert(1, 3, 0);
//			itb.insert(4, 7, 1);
//			itb.insert(10, 15, 2);
//			itb.insert(16, 20, 3);
//			System.out.println(Similarity.caculatePieceWiseMatching(ita, itb));
//		}catch (IntervalConflictException e) {
//			e.printStackTrace();
//			// TODO: handle exception
//		}
	}
}
