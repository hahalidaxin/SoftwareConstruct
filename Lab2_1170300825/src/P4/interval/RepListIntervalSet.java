package P4.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An implementation of IntervalSet.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */

public class RepListIntervalSet<L> implements IntervalSet<L> {
	
	/**
	 * e.g., { "A"=[0,10), "B"=[20,30) } is represented by
	 * 	labelList = <"A", "B">
	 *  valueList = <0, 10, 20, 30>
	 */
	
	private final List<L> labelList = new ArrayList<>();
	private final List<Long> valueList = new ArrayList<>();
	
    // Abstraction function:
    //   AF(labelList,valueList)={label[i]=[valueList[2*i],valueList[2*i+1]),0<=i<=labellist.size()}
    // Representation invariant:
    //   RI(labelList,valueList) = sizeOfLabel*2==sizeOfValue && value[2k]<value[2k+1]
    // Safety from rep exposure:
	//  ��Ҫ����ֱ�ӱ�¶�ӿ�-ʹ��private+getter&setter
    //   ���ؽ�� ���ⷵ������ڲ�������(����private)
    //	   ���ղ��� ��������ⲿ���������  
    //	 Collections.unmodifiableList() ��listת��Ϊ���ɱ� set,add,remove��mutators����throw Exception(At Runtime)

    
    // TODO constructor
	public RepListIntervalSet() {
		// TODO Auto-generated constructor stub
	}
    
    // TODO checkRep
	private void checkRep() {
		assert labelList.size()*2 == valueList.size() : "sizeOfLabel*2==sizeOfValue";
		for(int i=0;i<labelList.size();i++) {
			assert valueList.get(2*i)<valueList.get(2*i+1) : "value[2k]<value[2k+1]";
		}
	}
	
	/**
	 * �ж�value�Ƿ��� [start,end)�����ڲ�
	 * 
	 * @param value
	 * @param start
	 * @param end
	 * @return �ж�value�Ƿ��� [start,end) �����ڲ�
	 */
	private boolean ifValueAtRange(long value,long start,long end) {
		return value>=start && value<end;
	}
	
	@Override
	public void insert(long start, long end, L label) throws IntervalConflictException  {
		assert start<end : "start<end";
		boolean existFlag=false;
		for(int i=0;i<labelList.size();i++) {
			if(labelList.get(i).equals(label)) {
				if(valueList.get(2*i)!=start || valueList.get(2*i+1)!=end) {
					throw new IntervalConflictException("[Exception] label��ͻ������[start,end)��ͬ");
				} else {
					existFlag = true;
				}
			} else {
				if(ifValueAtRange(start, valueList.get(2*i), valueList.get(2*i+1))
						|| ifValueAtRange(valueList.get(2*i), start, end)) {
					throw new IntervalConflictException("[Exception] �����ͻ�����������ص�������");
				}
			}
		}
		if(!existFlag) {
			labelList.add(label);
			valueList.add(start);
			valueList.add(end);
		}
		checkRep();
//        throw new RuntimeException("not implemented");
	}
	
	@Override
	public Set<L> labels() {
		return labelList.stream().collect(Collectors.toSet());
//        throw new RuntimeException("not implemented");
    }
	
	@Override
	public boolean remove(L label) {
		if(!labelList.contains(label)) {
			return false;
		}
		int indexOfLabel=labelList.indexOf(label);
		labelList.remove(indexOfLabel);
		valueList.removeAll(Arrays.asList(valueList.get(2*indexOfLabel),valueList.get(2*indexOfLabel+1)));
		checkRep();
		return true;
		
//        throw new RuntimeException("not implemented");
    }
	
	@Override
	public long start(L label) throws NoSuchElementException {
		if(!labelList.contains(label))
			throw new NoSuchElementException();
		return valueList.get(2*labelList.indexOf(label));
//        throw new RuntimeException("not implemented");
    }
	
	@Override
	public long end(L label) throws NoSuchElementException {
		if(!labelList.contains(label))
			throw new NoSuchElementException();
		return valueList.get(2*labelList.indexOf(label)+1);
//        throw new RuntimeException("not implemented");
    }
	
	@Override
	public String toString() {
		return String.format("RepListIntervalSet with %d labels", labelList.size());
//        throw new RuntimeException("not implemented");
	}
}
