package P4.interval;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Tests for RepListIntervalSet.
 * 
 * This class runs the IntervalSetTest tests against RepListIntervalSet, as
 * well as tests for that particular implementation.
 * 
 * Tests against the IntervalSet spec should be in IntervalSetTest.
 */

public class RepListIntervalSetTest extends IntervalSetTest {
	/*
	 * Provide a RepListIntervalSet for tests in IntervalSetTest.
	 */
	
	private RepListIntervalSet<String> thisSet = new RepListIntervalSet<>();
	
	@Override
	public IntervalSet<String> emptyInstance() {
		return new RepListIntervalSet<>();
	}

	/*
	 * Testing RepListIntervalSet...
	 */

	// Testing strategy for RepListIntervalSet.toString()
	// TODO
	
	
//	------  TEST insert -----------
	
	
//	���������ص�Exception
	@Test public void testOverlapConfilct() {
		System.out.println("[���������ص�Exception]");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(2, 4, "B");
		}catch(IntervalConflictException e) {
			System.out.println(" IntervalConflictException");
		}
	}
	
//	����label���Ƴ�ͻ
	@Test public void testLabelOverLapConflict() {
		System.out.println("[����label���Ƴ�ͻ] ");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(2, 4, "A");
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
	
//	�������䲻��������
	@Test public void testRangeException() {
		System.out.println("[�������䲻��������]");
		try {
			thisSet.insert(3, 1, "B");
		} catch (IntervalConflictException e) {
			
		} catch (AssertionError e) {
			System.out.println(" AssertException");
			// TODO: handle exception
		}
	}

	
//	------  TEST remove -----------
	
//	����ɾ�� ɾ��û�еı�ǩ
	@Test public void testRemove() {
		System.out.println("[����ɾ�� ɾ��û�еı�ǩ] ");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(1, 3, "A");
			thisSet.insert(4, 5, "B");
			System.out.println(thisSet.remove("A"));
			System.out.println(thisSet.remove("C"));
		}catch(IntervalConflictException e) {
			System.out.println("IntervalConflictException");
		}
	}
	
//	------  TEST start and end -----------
//	����start��end ���Բ����ڵ�label
	@Test public void testStartandEnd() {
		System.out.println("[���Բ�����label��start]");
		try {
			
			thisSet.insert(1, 3, "A");
			thisSet.insert(1, 3, "A");
			thisSet.insert(4, 5, "B");
			System.out.println(thisSet.start("A"));
			System.out.println(thisSet.end("B"));
			System.out.println(thisSet.start("C"));
		}catch(IntervalConflictException e) {
//			System.out.println("[���Բ����ڵ�label] IntervalConflictException");
		}catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException");
		}
	}
	
	
	// TODO tests for RepListIntervalSet.toString()
	@Test public void testToString() {
		System.out.println("[����toString]");
		try {
			thisSet.insert(1, 2, "A");
			thisSet.insert(2, 3, "B");
			System.out.println(thisSet.toString());
		} catch (IntervalConflictException e) {
			// TODO: handle exception
		}
	}
}
