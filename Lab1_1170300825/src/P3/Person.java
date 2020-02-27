package P3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Person {
	private String nameString;
	private List<Person> neighborList;
	private static Set<String> nameSet =new HashSet<String>();
	
	/** Person类的构造方法  **/
	public Person(String nameString) throws SameNameException {
		if(nameSet.contains(nameString)) 
			throw new SameNameException();
		this.nameString = nameString;
		this.nameSet.add(nameString);
		neighborList = new ArrayList<>();
	}
	/**	添加相邻节点 **/
	public void addNeighbor(Person pb) {
		neighborList.add(pb);
		
	}
	/**	获取当前Person的名字**/
	public String getName() {
		return nameString;
	}
	/**	获得相邻节点列表 **/
	public List<Person> getNeighList() {
		return this.neighborList;
	}
}

class SameNameException extends Exception {
	
}
