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
	
	/** FriendshipGraph类的构造方法  **/
	public FriendshipGraph() {
		persons = new ArrayList<>();
		namePool = new HashMap<String, Integer>();
	}
	/**	添加Person **/
	public void addVertex(Person newPerson) {
		if(namePool.containsKey(newPerson.getName())) {
			System.out.println("ERROR：不满足名字Unique的前提");
		} else {
			namePool.put(newPerson.getName(), 1);
		}
		persons.add(newPerson);
	}
	/**	添加一条有向边**/
	public void addEdge(Person pa,Person pb) {
		pa.addNeighbor(pb);
	}
	/**	使用BFS算法获得从stPerson到edPerson的单源最短路径 **/
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
