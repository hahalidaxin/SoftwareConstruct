/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   TODO
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    @Test
    //covers graph contains multiple vertices,
    //       target exists in graph,
    //       target has multiple sources
    public void testSourcesMultipleSources(){
        Graph<String> graph = emptyInstance();
        
        //create edges to test sources
        final String target = "vertex";
        final String vertex1 = "vertex1";
        final String vertex2 = "vertex2";
        final String vertex3 = "vertex3";
        final int weight = 1;
        
        graph.set(vertex1, target, weight);
        graph.set(vertex2, target, weight);
        graph.add(vertex3);
        
        Map<String, Integer> sources = graph.sources(target);
        
        assertNotEquals("Expected graph to have sources", Collections.emptyMap(), sources);
        assertEquals("Expected 2 sources to target", 2, sources.keySet().size());
        assertTrue("Expected sources to contain vertex1, vertex2", 
                Arrays.asList(vertex1, vertex2).containsAll(sources.keySet()));
        }
    
}
