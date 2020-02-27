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
    //   AF(vertices) = �㼯Ϊvertices��ͼ��vertex�д洢��Ӧ����ߵĹ�ϵ
    // Representation invariant:
    //   
    // Safety from rep exposure:
    //   vertices��getter���½�����
    
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
    	GraphException.assertTrue(vertex!=null, "vertex����Ϊnull");
    	
    	boolean ans=this.vertices.add(new Vertex<L>(vertex));
    	checkRep();
    	return ans;
//        throw new RuntimeException("not implemented");
    }
    
    @Override public int set(L source, L target, int weight) {
    	GraphException.assertTrue(source!=null, "source��Ϊnull");
    	GraphException.assertTrue(target!=null, "target��Ϊnull");
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
    	
//    	ɾ������ڵ�
    	this.vertices.removeIf(
    			v->v.getSources().size()==0 && v.getTargets().size()==0);
    	
    	checkRep();
    	return previousWeight;
//        throw new RuntimeException("not implemented");
    }
    
    @Override public boolean remove(L vertex) {
    	GraphException.assertTrue(vertex!=null, "vertex��Ϊnull");
    	
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
    	GraphException.assertTrue(target!=null, "target����Ϊnull");
    	
    	int indexOfTarget = this.getIndexOfVertex(target);
    	if(indexOfTarget==-1) {
    		return new HashMap<L, Integer>();
    	}else {
    		return this.vertices.get(indexOfTarget).getSources();
    	}
//        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<L, Integer> targets(L source) {
    	GraphException.assertTrue(source!=null, "source����Ϊnull");
    	
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
//	ά����labelΪ�յ�ı�
    private Map<L, Integer> sources;
//	ά����labelΪ���ı�
    private Map<L, Integer> targets;
    
    // Abstraction function:
    //   AF(label,sources,targets)=��־λlabel��������ߣ���Ȩ��λsources�����ߣ���Ȩ��Ϊtargets�Ľڵ�
    // Representation invariant:
    //   label!=null
    // Safety from rep exposure:
    //   ����getter��setterʹ���½�����ķ�������й¶
    
    // TODO constructor
    public Vertex(L label) {
    	GraphException.assertTrue(label!=null, "label��Ӧ��Ϊnull");
    	
    	this.label=label;
    	this.sources = new HashMap<>();
    	this.targets = new HashMap<>();
    }
    
    // TODO checkRep
    private void checkRep() {
//    	final Set<L> sourceLabels = sources.keySet();
//        final Set<L> targetLabels = targets.keySet();
//        
        GraphException.assertTrue(label!=null,"label����Ϊnull");
//        GraphException.assertTrue(!targetLabels.contains(this.label),"checkRep: ���ܳ����Ի�");
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
	 * ����һ�����ߵ�weight���޸ĵĹ�����graph�ӿ���������
	 * @param source ����Դ��
	 * @param weight �ߵ�Ȩֵ
	 * @return ͬgraph�е�����
	 */
    public int setSouce(L source,int weight) {
    	GraphException.assertTrue(source!=null, "source����Ϊnull");
    	GraphException.assertTrue(weight>=0, "weightӦ��>=0");
    	
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
     * ɾ��һ���ߵ�Դ��
     * @param source Դ��
     * @return ���û�з���0�����򷵻�ɾ���ĵ��ӦȨֵ
     */
    public int removeSource(L source) {
    	GraphException.assertTrue(source!=null, "source����Ϊnull");
    	
    	Integer previousVal = this.getSources().remove(source);
    	checkRep();
    	return previousVal==null? 0:previousVal;
    }
    
    /**
     * ����getSouce�еĶ���
     * @param target
     * @param weight
     * @return
     */
	public int setTarget(L target,int weight) {
		GraphException.assertTrue(target!=null, "target����Ϊnull");
		GraphException.assertTrue(weight>=0, "weightӦ��>=0");
    	
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
	 * ����removeSource�еĶ���
	 * @param target
	 * @return
	 */
	
    public int removeTarget(L target) {
    	GraphException.assertTrue(target!=null, "target����Ϊnull");
    	
    	Integer previousVal = this.getTargets().remove(target);
    	checkRep();
    	return previousVal==null? 0:previousVal;
    }
    
    /**
     * ɾ��һ���ڵ�vertex����Ҫͬʱ����ɾ�����ߺ�ȥ��
     * @param vertex
     * @return ɾ�����
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
