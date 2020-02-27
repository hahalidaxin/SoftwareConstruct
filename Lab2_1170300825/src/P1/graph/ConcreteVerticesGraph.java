/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   AF(vertices) = 点集为vertices的图，vertex中存储相应单向边的关系
    // Representation invariant:
    //   
    // Safety from rep exposure:
    //   vertices的getter中新建对象
    
    // TODO constructor
    
    public ConcreteVerticesGraph() {
		// TODO Auto-generated constructor stub
	}
    
    // TODO checkRep
    private void checkRep() {
    	
    }
    
    
    
    public List<Vertex<L>> getVertices() {
		return new ArrayList<Vertex<L>>(vertices);
	}

	private int getIndexOfVertex(L Label) {
    	int verticesLen = this.vertices.size();
    	for(int i=0;i<verticesLen;i++) {
    		if(this.vertices.get(i).getLabel().equals(Label)) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    @Override public boolean add(L vertex) {
    	GraphException.assertTrue(vertex!=null, "vertex不能为null");
    	
    	boolean ans=this.vertices.add(new Vertex<L>(vertex));
    	checkRep();
    	return ans;
//        throw new RuntimeException("not implemented");
    }
    
    @Override public int set(L source, L target, int weight) {
    	GraphException.assertTrue(source!=null, "source不为null");
    	GraphException.assertTrue(target!=null, "target不为null");
    	GraphException.assertTrue(weight>=0, "weight>=0");
    	
    	int indexOfSource = getIndexOfVertex(source);
    	int indexOfTarget = getIndexOfVertex(target);

    	if(indexOfSource==-1) {
    		this.vertices.add(new Vertex<L>(source));
    		indexOfSource=getIndexOfVertex(source);
    	}
    	if(indexOfTarget==-1) {
    		this.vertices.add(new Vertex<L>(target));
    		indexOfTarget=getIndexOfVertex(target);
    	}
    	
    	int previousWeight;
    	this.vertices.get(indexOfSource).setTarget(target, weight);
    	previousWeight=this.vertices.get(indexOfTarget).setSouce(source, weight);
    	
//    	删除多余节点
    	this.vertices.removeIf(
    			v->v.getSources().size()==0 && v.getTargets().size()==0);
    	
    	checkRep();
    	return previousWeight;
//        throw new RuntimeException("not implemented");
    }
    
    @Override public boolean remove(L vertex) {
    	GraphException.assertTrue(vertex!=null, "vertex不为null");
    	
    	int indexOfVertex = getIndexOfVertex(vertex);
    	if(indexOfVertex==-1) {
    		return false;
    	}
    	
    	for(Vertex<L> v:this.vertices) {
    		v.remove(vertex);
    	}
    	this.vertices.remove(indexOfVertex);
    	this.vertices.removeIf(
    			v->v.getSources().size()==0 && v.getTargets().size()==0);
    	checkRep();
    	return true;
//        throw new RuntimeException("not implemented");
    }
    
    @Override public Set<L> vertices() {
            return vertices.stream()
                    .map(Vertex::getLabel)
                    .collect(Collectors.toSet());
       
//        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<L, Integer> sources(L target) {
    	GraphException.assertTrue(target!=null, "target不能为null");
    	
    	int indexOfTarget = this.getIndexOfVertex(target);
    	if(indexOfTarget==-1) {
    		return new HashMap<L, Integer>();
    	}else {
    		return this.vertices.get(indexOfTarget).getSources();
    	}
//        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<L, Integer> targets(L source) {
    	GraphException.assertTrue(source!=null, "source不能为null");
    	
    	int indexOfSource = this.getIndexOfVertex(source);
    	if(indexOfSource==-1) {
    		return new HashMap<L, Integer>();
    	}else {
    		return this.vertices.get(indexOfSource).getTargets();
    	}
//        throw new RuntimeException("not implemented");
    }
    
    // TODO toString()
    @Override public String toString() {
    	return String.format("ConcreteVerticesGraph<L> with %d vertices", 
    			this.vertices.size());
    }
    
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    
    // TODO fields
	private L label;
//	维护以label为终点的边
    private Map<L, Integer> sources;
//	维护以label为起点的边
    private Map<L, Integer> targets;
    
    // Abstraction function:
    //   AF(label,sources,targets)=标志位label，所有入边（带权）位sources，出边（带权）为targets的节点
    // Representation invariant:
    //   label!=null
    // Safety from rep exposure:
    //   对于getter和setter使用新建对象的方法避免泄露
    
    // TODO constructor
    public Vertex(L label) {
    	GraphException.assertTrue(label!=null, "label不应该为null");
    	
    	this.label=label;
    	this.sources = new HashMap<>();
    	this.targets = new HashMap<>();
    }
    
    // TODO checkRep
    private void checkRep() {
//    	final Set<L> sourceLabels = sources.keySet();
//        final Set<L> targetLabels = targets.keySet();
//        
        GraphException.assertTrue(label!=null,"label不能为null");
//        GraphException.assertTrue(!targetLabels.contains(this.label),"checkRep: 不能出现自环");
    }
    

//    getter and setter
    public L getLabel() {
		return label;
	}

	public void setLabel(L label) {
		this.label = label;
	}

	public Map<L, Integer> getSources() {
		return new HashMap<L,Integer> (sources);
	}

	public void setSources(Map<L, Integer> sources) {
		this.sources = new HashMap<L,Integer>(sources);
	}

	public Map<L, Integer> getTargets() {
		return new HashMap<L,Integer>(targets);
	}

	public void setTargets(Map<L, Integer> targets) {
		this.targets = new HashMap<L,Integer>(targets);
	}

	
	/**
	 * 设置一条来边的weight，修改的规则在graph接口中有声明
	 * @param source 来边源点
	 * @param weight 边的权值
	 * @return 同graph中的声明
	 */
    public int setSouce(L source,int weight) {
    	GraphException.assertTrue(source!=null, "source不能为null");
    	GraphException.assertTrue(weight>=0, "weight应该>=0");
    	
    	Integer previousWeight=0;
    	if(weight==0) {
    		previousWeight=this.removeSource(source);
    	}else if(weight>0){
    		previousWeight=this.getSources().put(source, weight);
    		previousWeight=previousWeight==null?0:previousWeight;
    	}
    	checkRep();
    	return previousWeight;
    }
    
    /**
     * 删除一来边的源点
     * @param source 源点
     * @return 如果没有返回0，否则返回删除的点对应权值
     */
    public int removeSource(L source) {
    	GraphException.assertTrue(source!=null, "source不能为null");
    	
    	Integer previousVal = this.getSources().remove(source);
    	checkRep();
    	return previousVal==null? 0:previousVal;
    }
    
    /**
     * 类似getSouce中的定义
     * @param target
     * @param weight
     * @return
     */
	public int setTarget(L target,int weight) {
		GraphException.assertTrue(target!=null, "target不能为null");
		GraphException.assertTrue(weight>=0, "weight应该>=0");
    	
    	Integer previousWeight=0;
    	if(weight==0) {
    		previousWeight=this.removeTarget(target);
    	}else if(weight>0){
    		previousWeight=this.getTargets().put(target, weight);
    		previousWeight=previousWeight==null?0:previousWeight;
    	}
    	checkRep();
    	return previousWeight;
    }
	
	/**
	 * 类似removeSource中的定义
	 * @param target
	 * @return
	 */
	
    public int removeTarget(L target) {
    	GraphException.assertTrue(target!=null, "target不能为null");
    	
    	Integer previousVal = this.getTargets().remove(target);
    	checkRep();
    	return previousVal==null? 0:previousVal;
    }
    
    /**
     * 删除一个节点vertex，需要同时尝试删除来边和去边
     * @param vertex
     * @return 删除结果
     */
    public int remove(final L vertex) {

        int sourcePrevWeight = removeSource(vertex);
        int targetPrevWeight = removeTarget(vertex);
        
        checkRep();
        return sourcePrevWeight == 0 ? targetPrevWeight : sourcePrevWeight;
    }
    
    // TODO toString()
	@Override public String toString() {
		return String.format("Vertex <%s> with %d sources and %d targets", 
				this.getLabel().toString(),this.getSources().size(),this.getTargets().size());
	}
    
}
