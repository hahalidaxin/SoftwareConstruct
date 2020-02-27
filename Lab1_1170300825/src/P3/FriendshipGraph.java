package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class FriendshipGraph {
	private List<Person> persons;
	private Map<String,Integer> namePool;
	
	/** FriendshipGraph��Ĺ��췽��  **/
	public FriendshipGraph() {
		persons = new ArrayList<>();
		namePool = new HashMap<String, Integer>();
	}
	/**	���Person **/
	public void addVertex(Person newPerson) {
		if(namePool.containsKey(newPerson.getName())) {
			System.out.println("ERROR������������Unique��ǰ��");
		} else {
			namePool.put(newPerson.getName(), 1);
		}
		persons.add(newPerson);
	}
	/**	���һ�������**/
	public void addEdge(Person pa,Person pb) {
		pa.addNeighbor(pb);
	}
	/**	ʹ��BFS�㷨��ô�stPerson��edPerson�ĵ�Դ���·�� **/
	public int getDistance(Person stPerson,Person edPerson) {
		if(stPerson==edPerson) return 0;
		Queue<Person> queue = new LinkedBlockingQueue<>();
		Map<Person, Integer> distantMap = new HashMap<>();
		queue.offer(stPerson);
		distantMap.put(stPerson, 0);
		while(!queue.isEmpty()) {
			Person topPerson = queue.poll();
			int nowDis=distantMap.get(topPerson);
			List<Person> neighborList = topPerson.getNeighList();
			for(Person ps:neighborList) if(!distantMap.containsKey(ps)){
				distantMap.put(ps,nowDis+1);
				queue.offer(ps);
				if(ps==edPerson) {
					return distantMap.get(edPerson);
				}
			}
		}
		return -1;
	}
	
}
