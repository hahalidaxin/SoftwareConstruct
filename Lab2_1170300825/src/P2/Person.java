package P2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Person {
	private String nameString;
	private static Set<String> nameSet =new HashSet<String>();
	
	/** Person��Ĺ��췽��  **/
	public Person(String nameString) throws SameNameException {
		if(nameSet.contains(nameString)) 
			throw new SameNameException();
		this.nameString = nameString;
		this.nameSet.add(nameString);
	}
	/**	��ȡ��ǰPerson������**/
	public String getName() {
		return nameString;
	}
}

class SameNameException extends Exception {
	
}
