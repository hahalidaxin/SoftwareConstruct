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
//	���������ص�Exception
	@Test public void testOverlapConfilct() {
		System.out.println("[���������ص�Exception]");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(2, 4, "B");
		}catch(IntervalConflictException e) {
			System.out.println("IntervalConflictException");
		}
	}
	
//	����label������ͬ ���䲻��ͻ
	@Test public void testLabelOverLapConflict() {
		System.out.println("[����label������ͬ ���䲻��ͻ] ");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(4, 5, "A");
			thisSet.insert(10, 11, "A");
		}catch(IntervalConflictException e) {
			System.out.println("IntervalConflictException");
		}
	}
	
//	����label������ͬ ������ͬ
	@Test public void testLabelOverlapButNameEqual() {
		System.out.println("[����label������ͬ ������ͬ] ");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(1, 3, "A");
		}catch(IntervalConflictException e) {
			System.out.println("IntervalConflictException");
		}
	}
	
//	����label������ͬ �����ͻ
	@Test public void testLabelOverlapAndNameEqual() {
		System.out.println("[����label������ͬ �����ͻ]");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(2, 4, "A");
		}catch(IntervalConflictException e) {
			System.out.println("IntervalConflictException");
		}
	}
	
	
//	�������䲻��������
	@Test public void testRangeException() {
		System.out.println("[�������䲻��������] ");
		try {
			thisSet.insert(3, 1, "B");
		} catch (IntervalConflictException e) {
			
		} catch (AssertionError e) {
			System.out.println("AssertException");
			// TODO: handle exception
		}
	}

	
//	------  TEST removeAll -----------
	
//	����ɾ�� ɾ��û�еı�ǩ
	@Test public void testRemove() {
		System.out.println("[����ɾ��û�е�Label]");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(4, 5, "A");
			thisSet.insert(4, 5, "B");
			System.out.println("ɾ��û�е�C");
			System.out.println(thisSet.removeAll("C"));
		}catch(IntervalConflictException e) {
			System.out.println("IntervalConflictException");
		}
	}
//	����ɾ�����еı�ǩ
	@Test public void testRemoveHave() {
		System.out.println("[����ɾ��ԭ���е�Label]");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(4, 5, "A");
			System.out.println("ɾ��ԭ�е�A");
			System.out.println(thisSet.removeAll("A"));
			assert thisSet.intervals("A").labels().size()==0:"ɾ��֮��A���������Ϊ0";
		}catch(IntervalConflictException e) {
			System.out.println("cc IntervalConflictException");
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			System.out.println("intervals NoSuchElementException");
		}
	}
	

//	------  TEST labels -----------
	
//	����Label
	@Test public void testLabels() {
		System.out.println("[����labels]");
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
	
//	����interval
	@Test public void testInterval() {
		System.out.println("[����Interval]");
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
		System.out.println("[����ToString]");
		try {
			thisSet.insert(1, 2, "A");
			thisSet.insert(2, 3, "B");
			System.out.println(thisSet.toString());
		} catch (IntervalConflictException e) {
			// TODO: handle exception
		}
	}
}
