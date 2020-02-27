package P2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Person {
	private String nameString;
	private static Set<String> nameSet =new HashSet<String>();
	
	/** Person类的构造方法  **/
	public Person(String nameString) throws SameNameException {
		if(nameSet.contains(nameString)) 
			throw new SameNameException();
		this.nameString = nameString;
		this.nameSet.add(nameString);
	}
	/**	获取当前Person的名字**/
	public String getName() {
		return nameString;
	}
}

class SameNameException extends Exception {
	
}
