/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    public ConcreteEdgesGraph<String> graph=new ConcreteEdgesGraph<String>();
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Before
    public void beforeEveryTime() {
    	System.out.println();
    }
    

    
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy : 添加边点，调用toString

    // TODO tests for ConcreteEdgesGraph.toString()
    @Test public void toStringTest() {
    	System.out.println("[TEST toStringTest]");
    	graph.add("LonelyBOY");
    	graph.set("Bob", "Alice", 12);
    	graph.set("Bob", "MOM", 1);
    	graph.set("Bob", "Green", 5);
    	System.out.println(graph.toString());
    }
    /*
     * Testing Edge...
     */
    
    // Testing strategy : 测试toString
    
    @Test public void testClassEdge() {
    	graph.set("Bob", "MOM", 1);
    	System.out.println("[TEST testClassEdge]");
    	System.out.println(graph.getEdges().get(0).toString());
    }
    
    // TODO tests for operations of Edge
    @Test public void testOprationsOfEdge() {
    	Set<String> expection = new HashSet<>();
    
    	System.out.println("[TEST testOprationsOfEdge]");
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
    
    @Test public void testRemoveCenter() {
    	System.out.println(String.format("[TEST %s]",
    			Thread.currentThread().getStackTrace()[1].getMethodName()));
    	
    	graph.set("A", "B", 1);
    	graph.set("A", "C", 1);
    	graph.set("A", "D", 1);
    	graph.set("A", "E", 1);
    	graph.set("A", "F", 1);
    	graph.set("F", "G", 1);
    	graph.remove("A");
    	
    	System.out.println(graph.vertices().size());
    }
}
