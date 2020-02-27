package P4.interval;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;


/**
 * Tests for instance methods of MultiIntervalSet.
 */

public class MultiIntervalSetTest {
	// Testing strategy
    // TODO
	
	/*
	 * Testing MultiIntervalSet...
	 */

	private MultiIntervalSet<String> thisSet = new MultiIntervalSet<String>();
	
	
//	------  TEST insert -----------
//	测试区间重叠Exception
	@Test public void testOverlapConfilct() {
		System.out.println("[测试区间重叠Exception]");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(2, 4, "B");
		}catch(IntervalConflictException e) {
			System.out.println("IntervalConflictException");
		}
	}
	
//	测试label名称相同 区间不冲突
	@Test public void testLabelOverLapConflict() {
		System.out.println("[测试label名称相同 区间不冲突] ");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(4, 5, "A");
			thisSet.insert(10, 11, "A");
		}catch(IntervalConflictException e) {
			System.out.println("IntervalConflictException");
		}
	}
	
//	测试label名称相同 区间相同
	@Test public void testLabelOverlapButNameEqual() {
		System.out.println("[测试label名称相同 区间相同] ");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(1, 3, "A");
		}catch(IntervalConflictException e) {
			System.out.println("IntervalConflictException");
		}
	}
	
//	测试label名称相同 区间冲突
	@Test public void testLabelOverlapAndNameEqual() {
		System.out.println("[测试label名称相同 区间冲突]");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(2, 4, "A");
		}catch(IntervalConflictException e) {
			System.out.println("IntervalConflictException");
		}
	}
	
	
//	测试区间不满足条件
	@Test public void testRangeException() {
		System.out.println("[测试区间不满足条件] ");
		try {
			thisSet.insert(3, 1, "B");
		} catch (IntervalConflictException e) {
			
		} catch (AssertionError e) {
			System.out.println("AssertException");
			// TODO: handle exception
		}
	}

	
//	------  TEST removeAll -----------
	
//	测试删除 删除没有的标签
	@Test public void testRemove() {
		System.out.println("[测试删除没有的Label]");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(4, 5, "A");
			thisSet.insert(4, 5, "B");
			System.out.println("删除没有的C");
			System.out.println(thisSet.removeAll("C"));
		}catch(IntervalConflictException e) {
			System.out.println("IntervalConflictException");
		}
	}
//	测试删除已有的标签
	@Test public void testRemoveHave() {
		System.out.println("[测试删除原来有的Label]");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(4, 5, "A");
			System.out.println("删除原有的A");
			System.out.println(thisSet.removeAll("A"));
			assert thisSet.intervals("A").labels().size()==0:"删除之后A的区间个数为0";
		}catch(IntervalConflictException e) {
			System.out.println("cc IntervalConflictException");
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			System.out.println("intervals NoSuchElementException");
		}
	}
	

//	------  TEST labels -----------
	
//	测试Label
	@Test public void testLabels() {
		System.out.println("[测试labels]");
		try {
			Set<String> expectSet = new HashSet<String>();
			thisSet.insert(1, 3, "A");
			thisSet.insert(4, 5, "A");
			thisSet.insert(6, 7, "B");
			thisSet.insert(10, 11, "D");
			
			expectSet.add("A");
			expectSet.add("B");
			expectSet.add("D");
			assertEquals(expectSet, thisSet.labels());
		}catch(IntervalConflictException e) {
			System.out.println("IntervalConflictException");
		}
	}
	
	
//	------  TEST intervals -----------
	
//	测试interval
	@Test public void testInterval() {
		System.out.println("[测试Interval]");
		try {
			IntervalSet<Integer> expectSet = IntervalSet.empty();
			expectSet.insert(1, 2, 0);
			expectSet.insert(4, 5, 1);
			expectSet.insert(10, 13, 2);
			thisSet.insert(10, 13, "A");
			thisSet.insert(4, 5, "A");
			thisSet.insert(1, 2, "A");
//			thisSet.insert(6, 7, "B");
//			thisSet.insert(10, 11, "D");
			for(int i=0;i<expectSet.labels().size();i++) {
				System.out.println(String.format("[%d,%d)", expectSet.start(i),expectSet.end(i)));
			}
//			assertEquals(expectSet, thisSet.intervals("A"));
		}catch(IntervalConflictException e) {
			System.out.println("IntervalConflictException");
		}
	}

//	------  TEST toString -----------
	// TODO tests for RepListIntervalSet.toString()
	@Test public void testToString() {
		System.out.println("[测试ToString]");
		try {
			thisSet.insert(1, 2, "A");
			thisSet.insert(2, 3, "B");
			System.out.println(thisSet.toString());
		} catch (IntervalConflictException e) {
			// TODO: handle exception
		}
	}
}
