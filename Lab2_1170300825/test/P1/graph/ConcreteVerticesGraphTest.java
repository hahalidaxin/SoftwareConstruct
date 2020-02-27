/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
	private ConcreteVerticesGraph<String> graph=new ConcreteVerticesGraph<>();
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    @Test public void testToString() {
    	System.out.println("[TEST toStringTest]");
    	graph.add("LonelyBOY");
    	graph.set("Bob", "Alice", 12);
    	graph.set("Bob", "MOM", 1);
    	graph.set("Bob", "Green", 5);
    	System.out.println(graph.toString());
    }
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   TODO
    
    @Test public void testClassVertex() {
    	System.out.println("[TEST testClassVertex]");
    	graph.add("LonelyBOY");
    	graph.set("Bob", "Alice", 12);
    	graph.set("Bob", "Dylan", 14);
    	graph.set("WAWA", "Bob", 11);
    	System.out.println(graph.getVertices().get(0).toString());
    }
    
    // TODO tests for operations of Vertex
    @Test public void testOprationsOfVertex() {
    	Set<String> expection = new HashSet<>();
    
    	System.out.println("[TEST testOprationsOfVertex]");
    	System.out.println(graph.set("Bob", "Alice", 12));
    	expection.add("Bob");
    	graph.set("Bob", "MOM", 13);
    	expection.add("MOM");
    	graph.set("Bob", "Green", 4);
    	expection.add("Green");
//    	测试边的删除 
//    	删除(Bob,Alice) 之后需要删除点Alice
    	System.out.println(graph.set("Bob", "Alice", 0));
    	expection.remove("Alice");
    	assertEquals("test remove:vertices", expection, new HashSet<>(graph.vertices()));
//    	测试边的修改
    	System.out.println(graph.set("Bob", "MOM", 11));
    	
//  	测试边的非负性
    	graph.set("Bob", "Alice", -1);
//		删除的边不存在
    	graph.set("AA", "BB", 0);
    }
    
}
