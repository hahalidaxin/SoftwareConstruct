package P4.interval;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;

/**
 * A mutable set of labeled intervals, where each label is associated with one
 * or more non-overlapping half-open intervals [start, end). Neither intervals
 * with the same label nor with different labels may overlap. 
 * 
 * Labels are of immutable type L and must implement the Object contract: they are 
 * compared for equality using Object.equals(java.lang.Object). 
 * 
 * For example, { * "A"=[[0,10)], "B"=[[20,30)] } is a multi-interval set where 
 * the labels are Strings "A" and "B". We could add "A"=[10,20) to that set to obtain 
 * {"A"=[[0,10),[10,20)], "B"=[[20,30)] }.
 * 
 * PS2 instructions: this is a required ADT class. You may not change the
 * specifications or add new public methods. You must use IntervalSet in your
 * rep, but otherwise the implementation of this class is up to you.
 * 
 * @param <L> type of labels in this set, must be immutable
 */

public class MultiIntervalSet<L> {
	
//	将重叠集合划分为若干个不重叠集合
	private ArrayList<IntervalSet<L>> intervalSetList;
	
	/**
	 * Create an empty multi-interval set.
	 */
	public MultiIntervalSet() {
		this.intervalSetList = new ArrayList<IntervalSet<L>>();
//		throw new RuntimeException("not implemented");
	}

	/**
	 * Create a new multi-interval set containing the given labeled intervals.
	 * 
	 * @param initial initial contents of the new set
	 */
	public MultiIntervalSet(IntervalSet<L> initial) {
		intervalSetList = new ArrayList<>(Arrays.asList(initial));
//		throw new RuntimeException("not implemented");
	}
	
	/**
	 * 判断value是否处于 [start,end)区间内部
	 * 
	 * @param value
	 * @param start
	 * @param end
	 * @return 判断value是否处于 [start,end) 区间内部
	 */
	private boolean valueAtRange(long value,long start,long end) {
		return value>=start && value<end;
	}
	

	/**
	 * Add a labeled interval (if not present) to this set, if it does not conflict with existing intervals.
	 * 
	 * Labeled intervals conflict if:
	 * 		they have the same label with different, overlapping intervals, or
	 *		they have different labels with overlapping intervals.
	 * 
	 * For example, if this set is { "A"=[[0,10),[20,30)] },
	 *		insert("A"=[0,10)) has no effect
	 *		insert("B"=[10,20)) adds "B"=[[10,20)]
	 *		insert("C"=[20,30)) throws IntervalConflictException
	 *
	 *
	 * @param start low end of the interval, inclusive
	 * @param end high end of the interval, exclusive, must be greater than start
	 * @param label label to add
	 * @throws IntervalConflictException if label is already in this set and associated 
	 * 									 with an interval other than [start,end) that 
	 * 									 overlaps [start,end), or if an interval in this 
	 * 									 set with a different label overlaps [start,end)
	 */
	public void insert(long start, long end, L label) throws IntervalConflictException {
		assert start<end:"start<end";
		
		int indexOfInsertLabel = -1;
		boolean existEqRangeFlag=false;
		for(int i=0;i<intervalSetList.size();i++) {
			IntervalSet<L> itSet = intervalSetList.get(i);
			if(!itSet.labels().contains(label)) {
				indexOfInsertLabel = i;
			}
			
			for(L edLabel:itSet.labels()) {
				Long edStart = itSet.start(edLabel);
				Long edEnd = itSet.end(edLabel);
				
//				不论Label是否相同 检查区间是否重叠
				if(valueAtRange(start, edStart, edEnd)
						||valueAtRange(edStart, start, end)) {
					throw new IntervalConflictException("[EXCEPTION] label不同且区间重叠");
				}
				
				if(label.equals(edLabel) 
						&& edStart==start &&edEnd==end) {
					existEqRangeFlag = true;
				}
			}
		}
		
		if(!existEqRangeFlag) {
//			如果不存在 Label相同且区间相同 则可以插入
			if(indexOfInsertLabel==-1) {
//				此时需要新建IntervalSet进行插入
				IntervalSet<L> newSet=IntervalSet.empty();
				newSet.insert(start, end, label);
				intervalSetList.add(newSet);
			} else {
				intervalSetList.get(indexOfInsertLabel).insert(start, end, label);
			}
		}
		
//		throw new RuntimeException("not implemented");
	}
	
	/**
	 * Remove all intervals of the given label from this set, if any.
	 * 
	 * @param label label to remove
	 * @return true if this set contained label, and false otherwise
	 */
	public boolean removeAll(L label) {
		boolean ansFlag = false;
		for(IntervalSet<L> itSet:intervalSetList) {
			ansFlag = ansFlag | itSet.remove(label);
		}
		return ansFlag;
//		throw new RuntimeException("not implemented");
	}
	
	/**
	 * Get the labels in this set.
	 * 
	 * @return the labels in this set
	 */
	public Set<L> labels() {
		Set<L> ansSet = new HashSet<>();
		for(IntervalSet<L> itSet:intervalSetList) {
			ansSet.addAll(itSet.labels());
		}
		return ansSet;
//		throw new RuntimeException("not implemented");
	}
	
	/**
	 * Get all the intervals in this set associated with a given label. The returned set has 
	 * Integer labels that act as indices: label 0 is associated with the lowest interval, 
	 * 1 the next, and so on, for all the intervals in this set that have the provided label.
	 * 
	 * For example, if this set is { "A"=[[0,10),[20,30)], "B"=[[10,20)] },
	 * 		intervals("A") returns { 0=[0,10), 1=[20,30) }

	 * @param label the label
	 * @return a new interval set that associates integer indices with the in-order intervals 
	 * 		   of label in this set
	 * @throws NoSuchElementException if label is not in this set
	 */

	public IntervalSet<Integer> intervals(L label) throws NoSuchElementException{
		
		IntervalSet<Integer> ansIntervalSet = IntervalSet.empty();
		ArrayList<Turple> itList = new ArrayList<>();
		for(IntervalSet<L> itSet:intervalSetList) {
			if(itSet.labels().contains(label)) {
				itList.add(new Turple(itSet.start(label),itSet.end(label)));
			}
		}
		if(itList.size()==0) 
			throw new NoSuchElementException();
		
		itList.sort( (Object o1,Object o2)->{
			Turple a = (Turple)o1; 
			Turple b=(Turple)o2;
			if(a.getStart()<b.getStart()) {
				return -1;
			} else if(a.getStart()>b.getStart()) {
				return 1;
			}
			return 0;
		});
		
		try {
			for(int i=0;i<itList.size();i++) {
				ansIntervalSet.insert(itList.get(i).getStart(), itList.get(i).getEnd(), i);
			}
		} catch (IntervalConflictException e) {
			e.printStackTrace();
		}
		return ansIntervalSet;
		
//		throw new RuntimeException("not implemented");
	}

	@Override
	public String toString() {
		return String.format("MutiIntervalSet with %d IntervalSets", intervalSetList.size());
//		throw new RuntimeException("not implemented");
	}
}


class Turple {
	private long start,end;
	
	 public Turple(long start,long end) {
		this.setStart(start);
		this.setEnd(end);
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

}
