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
    
    // Testing strategy : ��ӱߵ㣬����toString

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
    
    // Testing strategy : ����toString
    
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
//    	���Աߵ�ɾ�� 
//    	ɾ��(Bob,Alice) ֮����Ҫɾ����Alice
    	System.out.println(graph.set("Bob", "Alice", 0));
    	expection.remove("Alice");
    	assertEquals("test remove:vertices", expection, new HashSet<>(graph.vertices()));
//    	���Աߵ��޸�
    	System.out.println(graph.set("Bob", "MOM", 11));
    	
//  	���ԱߵķǸ���
    	graph.set("Bob", "Alice", -1);
//		ɾ���ı߲�����
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
