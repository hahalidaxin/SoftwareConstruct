package P3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Person {
	private String nameString;
	private List<Person> neighborList;
	private static Set<String> nameSet =new HashSet<String>();
	
	/** Person��Ĺ��췽��  **/
	public Person(String nameString) throws SameNameException {
		if(nameSet.contains(nameString)) 
			throw new SameNameException();
		this.nameString = nameString;
		this.nameSet.add(nameString);
		neighborList = new ArrayList<>();
	}
	/**	������ڽڵ� **/
	public void addNeighbor(Person pb) {
		neighborList.add(pb);
		
	}
	/**	��ȡ��ǰPerson������**/
	public String getName() {
		return nameString;
	}
	/**	������ڽڵ��б� **/
	public List<Person> getNeighList() {
		return this.neighborList;
	}
}

class SameNameException extends Exception {
	
}
