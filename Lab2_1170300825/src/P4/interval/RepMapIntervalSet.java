package P4.interval;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public class RepMapIntervalSet<L> implements IntervalSet<L> {

	/**
	 * e.g., { "A"=[0,10), "B"=[20,30) } is represented by
	 * 	startMap = { <"A", 0>, <"B", 20> }
	 *  endMap = { <"A", 10>, <"B", 30> }
	 */
	private final Map<L, Long> startMap = new HashMap<>();
	private final Map<L, Long> endMap = new HashMap<>();
	
    // Abstraction function:
    //   AF(startMap,endMap)={key=[startMap[key],endMap[key]) | key in startMap.keySet()}
    // Representation invariant:
    //   startMap.size()==endMap.size() && startMap[key]<endMap[key]
    // Safety from rep exposure:
	// Safety from rep exposure:
		//	需要避免直接暴露接口-使用private+getter&setter
	    //	返回结果 避免返回类的内部域引用(包括private)
	    //	   接收参数 避免接收外部对象的引用  
	    //	 Collections.unmodifiableList() 将list转化为不可变 set,add,remove（mutators）会throw Exception(At Runtime)
    
    // TODO constructor
	public RepMapIntervalSet() {
		// TODO Auto-generated constructor stub
	}
    
    // TODO checkRep
	public void checkRep() {
		assert startMap.size()==endMap.size() :"startMap.size==endMap.size";
		for(L label :startMap.keySet()) {
			assert endMap.containsKey(label):"endMap.containsKey in startMap";
			assert startMap.get(label)<endMap.get(label):"start<end";
		}
	}
	
	/**
	 * 判断value是否处于 [start,end)区间内部
	 * 
	 * @param value
	 * @param start
	 * @param end
	 * @return 判断value是否处于 [start,end) 区间内部
	 */
	private boolean isValueAtRange(long value,long start,long end) {
		return value>=start && value<end;
	}
	
	@Override
	public void insert(long start, long end, L label) throws IntervalConflictException  {
		assert start<end:"start<end";
		boolean insertFlag=true;
	
		for(Entry<L, Long> entry:startMap.entrySet()) {
			L entry_key = entry.getKey();
			Long entry_start= entry.getValue();
			Long entry_end = endMap.get(entry_key);
			if(entry_key.equals(label)) {
				if(start!=entry_start || end!=entry_end) {
					throw new IntervalConflictException("[Exception] label冲突：但是[start,end)不同");
				} else {
					insertFlag=false;
				}
			} else {
				if(isValueAtRange(start, entry_start, entry_end) 
						|| isValueAtRange(entry_start, start, end)) {
					throw new IntervalConflictException("[Exception] 区间冲突：包括两个重叠的区间");
				}
			}
		}
		if(insertFlag) {
			startMap.put(label, start);
			endMap.put(label, end);
		}
		checkRep();
//        throw new RuntimeException("not implemented");
	}
	
	@Override
	public Set<L> labels() {
		return startMap.keySet();
//        throw new RuntimeException("not implemented");
    }
	
	@Override
	public boolean remove(L label) {
		if(!startMap.containsKey(label)) {
			return false;
		} else {
			startMap.remove(label);
			endMap.remove(label);
			return true;
		}
//        throw new RuntimeException("not implemented");
    }
	
	@Override
	public long start(L label) throws NoSuchElementException {
		if(!startMap.containsKey(label)) {
			throw new NoSuchElementException();
		}
		return startMap.get(label);
//        throw new RuntimeException("not implemented");
    }
	
	@Override
	public long end(L label) throws NoSuchElementException {
		if(!endMap.containsKey(label)) {
			throw new NoSuchElementException();
		}
		return endMap.get(label);
//        throw new RuntimeException("not implemented");
    }
	
	@Override
	public String toString() {
		return String.format("RepMapIntervalSet with %d labels", endMap.size());
//        throw new RuntimeException("not implemented");
	}
}
