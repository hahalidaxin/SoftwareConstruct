package P2;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import P1.graph.ConcreteVerticesGraph;

public class FriendshipGraph extends ConcreteVerticesGraph<Person>{
//	private Map<String,Integer> namePool;
	
	/** FriendshipGraph类的构造方法  **/
	public FriendshipGraph() {
//		namePool = new HashMap<String, Integer>();
	}
	/**	添加Person **/
	public void addVertex(Person newPerson) {
//		if(namePool.containsKey(newPerson.getName())) {
//			System.out.println("ERROR：不满足名字Unique的前提");
//		} else {
//			namePool.put(newPerson.getName(), 1);
//		}
		this.add(newPerson);
	}
	/**	添加一条有向边**/
	public void addEdge(Person pa,Person pb) {
		this.set(pa, pb, 1);
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
			Map<Person, Integer> neighborList = this.targets(topPerson);
			for(Person ps:neighborList.keySet()) if(!distantMap.containsKey(ps)){
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
