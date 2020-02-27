package P3;

import org.junit.jupiter.api.Test;

class FriendshipGraphTest {

	/**	文档中的标准测试 **/
	@Test
	void standardTest() {
		FriendshipGraph graph = new FriendshipGraph();
		try {
			Person rachel = new Person("Rachel");
			Person ross = new Person("Ross");
			Person ben = new Person("Ben");
			Person kramer = new Person("Kramer");
			graph.addVertex(rachel);
			graph.addVertex(ross);
			graph.addVertex(ben);
			graph.addVertex(kramer);
	//		graph.addEdge(rachel, ross);
			graph.addEdge(ross, rachel);
			graph.addEdge(ross, ben);
			graph.addEdge(ben, ross);
			System.out.println(graph.getDistance(rachel, ross));
			// should print 1
			System.out.println(graph.getDistance(rachel, ben));
			// should print 2
			System.out.println(graph.getDistance(rachel, rachel));
			// should print 0
			System.out.println(graph.getDistance(rachel, kramer));
			// should print -1
	//		Person kramer2 = new Person("Kramer");
			} catch (SameNameException e) {
				System.out.println("同名错误Exception");
				// TODO: handle exception
			}
		System.out.println("______");
		
//		fail("Not yet implemented");
	}
	
	/**	测试FriendshiGraph的function **/
	@Test
	void friendshipGraphTest() {
		try {
			FriendshipGraph graph = new FriendshipGraph();
			Person rachel = new Person("A");
			Person ross = new Person("B");
			Person ben = new Person("C");
	//		Person ben2 = new Person("Ben");
			Person kramer = new Person("D");
			graph.addVertex(rachel);
			graph.addVertex(ross);
			graph.addVertex(ben);
			graph.addVertex(kramer);
	//		graph.addVertex(ben2);
			graph.addEdge(ross, rachel);
			graph.addEdge(ross, ben);
			graph.addEdge(ben, ross);
		} catch (SameNameException e) {
			// TODO: handle exception
			System.out.println("同名错误Exception");
		}
		System.out.println("______");
	}
	
	/**	额外的测试  **/
	@Test
	void additionnalTest() {
		FriendshipGraph graph = new FriendshipGraph();
		try {
			Person rachel = new Person("E");
			Person ross = new Person("F");
			Person ben = new Person("G");
			Person kramer = new Person("H");
			graph.addVertex(rachel);
			graph.addVertex(ross);
			graph.addVertex(ben);
			graph.addVertex(kramer);
			graph.addEdge(rachel,ross);
			graph.addEdge(ross,ben);
			graph.addEdge(ben,kramer);
			graph.addEdge(kramer,ross);
			graph.addEdge(ben,ross);
			
			System.out.println(graph.getDistance(rachel,kramer));
			System.out.println(graph.getDistance(kramer,rachel));
			System.out.println(graph.getDistance(ross,kramer));
			System.out.println(graph.getDistance(ben,rachel));
			System.out.println(graph.getDistance(ben,kramer));
		} catch(SameNameException e) {
			System.out.println("同名错误Exception");
		}
		System.out.println("______");
	}
	
//	同名错误测试
	@Test void testSameNameException() {
		try {
			Person rachel = new Person("I");
			Person ross = new Person("J");
			Person ben = new Person("K");
			Person ben2 = new Person("K");
		} catch (SameNameException e) {
			// TODO: handle exception
			System.out.println("同名错误Exception");
		}
		System.out.println("______");
	}

}
