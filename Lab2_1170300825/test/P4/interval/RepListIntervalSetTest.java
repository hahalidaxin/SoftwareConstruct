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
	
	
//	测试区间重叠Exception
	@Test public void testOverlapConfilct() {
		System.out.println("[测试区间重叠Exception]");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(2, 4, "B");
		}catch(IntervalConflictException e) {
			System.out.println(" IntervalConflictException");
		}
	}
	
//	测试label名称冲突
	@Test public void testLabelOverLapConflict() {
		System.out.println("[测试label名称冲突] ");
		try {
			thisSet.insert(1, 3, "A");
			thisSet.insert(2, 4, "A");
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
	
//	测试区间不满足条件
	@Test public void testRangeException() {
		System.out.println("[测试区间不满足条件]");
		try {
			thisSet.insert(3, 1, "B");
		} catch (IntervalConflictException e) {
			
		} catch (AssertionError e) {
			System.out.println(" AssertException");
			// TODO: handle exception
		}
	}

	
//	------  TEST remove -----------
	
//	测试删除 删除没有的标签
	@Test public void testRemove() {
		System.out.println("[测试删除 删除没有的标签] ");
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
//	测试start和end 测试不存在的label
	@Test public void testStartandEnd() {
		System.out.println("[测试不存在label的start]");
		try {
			
			thisSet.insert(1, 3, "A");
			thisSet.insert(1, 3, "A");
			thisSet.insert(4, 5, "B");
			System.out.println(thisSet.start("A"));
			System.out.println(thisSet.end("B"));
			System.out.println(thisSet.start("C"));
		}catch(IntervalConflictException e) {
//			System.out.println("[测试不存在的label] IntervalConflictException");
		}catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException");
		}
	}
	
	
	// TODO tests for RepListIntervalSet.toString()
	@Test public void testToString() {
		System.out.println("[测试toString]");
		try {
			thisSet.insert(1, 2, "A");
			thisSet.insert(2, 3, "B");
			System.out.println(thisSet.toString());
		} catch (IntervalConflictException e) {
			// TODO: handle exception
		}
	}
}
