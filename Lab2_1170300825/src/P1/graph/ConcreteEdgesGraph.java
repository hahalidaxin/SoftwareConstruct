/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.platform.commons.util.ToStringBuilder;



/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    // Abstraction function:
    //   AF(vertices,edges)=��vertices�еĵ�Ϊ���㣬edges�еı�Ϊ�ߵ�����ͼ
    // Representation invariant:
    //   �������<=�����������ı���
	
    private final Set<L> vertices = new HashSet<>();
//    AF : AF(vertices) = {vertex,|vertex����vertices}
//    RI �� vertices[i]!=null
    private final List<Edge<L>> edges = new ArrayList<>();
//    AF : AF(edges) = {<edges[i].source,edges[i].target,edges[i].weight> | 0<=i<=edges.length()}
//    RI : edges[i].source!=null && edges[i].target!=null &&edges[i].weight>0
//    && edges.source in vertices && edges.target in vertices
//    && (int)Math.ceil(Math.sqrt(2 * sizeOfEdges) + 0.5) <= sizeOfVertices
    
    
    // Safety from rep exposure:
    //   ��Ҫ����ֱ�ӱ�¶�ӿ�-ʹ��private+getter&setter
    //   ���ؽ�� ���ⷵ������ڲ�������(����private)
    //	   ���ղ��� ��������ⲿ���������  
    //	 Collections.unmodifiableList() ��listת��Ϊ���ɱ� set,add,remove��mutators����throw Exception(At Runtime)
    
//    vertices���������µĶ���
    
    
    // TODO constructor
    
    public ConcreteEdgesGraph() {
		// TODO Auto-generated constructor stub
	}
    public List<Edge<L>> getEdges() {
    	return this.edges;
    }
    
    // TODO checkRep
    private void checkRep() {
    	final int sizeOfEdges = edges.size();
        final int sizeOfVertices = vertices.size();
//		������ȫͼ������Ƿ�������С��������ϵ
        int minNumberOfVertices = 
                sizeOfEdges == 0 ? 0 : (int)Math.ceil(Math.sqrt(2 * sizeOfEdges) + 0.5);
        
        GraphException.assertTrue(sizeOfVertices >= minNumberOfVertices, "checkRep: �ڵ������ڱ������Ƶ���С�ڵ���");
    }
    
    
    @Override public boolean add(L vertex) {
    	if(vertex == null) return false;
    	boolean ans = this.vertices.add(vertex);
    	checkRep();
    	return ans;
//        throw new RuntimeException("not implemented");
    }
    
    private int getEdgeFromList(L sourceL,L targetL) {
    	int edgeSize = edges.size();
    	for(int ite=0;ite<edgeSize;ite++) {
    		Edge<L> edge = this.edges.get(ite);
    		if(edge.getSource().equals(sourceL)
    				&& edge.getTarget().equals(targetL)) {
    			return ite;
    		}
    	}
    	return -1;
    }
    
    @Override public int set(L source, L target, int weight) {
    	
    	GraphException.assertTrue(weight >= 0, "������weight�Ǹ�");
    	
    	int indexInEdges = this.getEdgeFromList(source, target);
    	int previousWeight=0;
    	if(weight>0) {
    		Edge<L> newEdge = new Edge<L>(source, target, weight);
    		if(indexInEdges==-1) {
    			this.vertices.add(source);
    			this.vertices.add(target);
    			this.edges.add(newEdge);
    			previousWeight = 0;
    		} else {
    			previousWeight = this.edges.get(indexInEdges).getWeight();
    			this.edges.set(indexInEdges, newEdge);
    		}
    	} else if(weight==0) {
    		if(indexInEdges==-1) return 0;
//    		GraphException.assertTrue(indexInEdges!=-1, "ɾ����Edge������");
    		previousWeight = this.edges.get(indexInEdges).getWeight();
    		this.edges.remove(indexInEdges);
//    		�������ı���source ����target�޹� ��ɾ��
    		
    		this.vertices.removeIf(v->v.equals(source)
    				&&edges.stream().filter(e->e.judgeVertex(source)!=-1).count()==0);
    		this.vertices.removeIf(v->v.equals(target)
    				&& edges.stream().filter(e->e.judgeVertex(target)!=-1).count()==0);
    	}
    	checkRep();
    	return previousWeight;
    }
    
    @Override public boolean remove(L vertex) {
    	if(!this.vertices.contains(vertex)) {
    		throw new GraphException("ɾ���Ľڵ㲻����");
    	}
//    	lambda���ʽ����Predicate<T>
    	this.edges.removeIf((Edge<L> edge)-> edge.getSource().equals(vertex) 
    			|| edge.getTarget().equals(vertex));
    	this.vertices.remove(vertex);
    	this.vertices.removeIf(v->
				edges.stream().filter(e->e.judgeVertex(v)!=-1).count()==0);
    	return true;
//        throw new RuntimeException("not implemented");
    }
    
    @Override public Set<L> vertices() {
    	return new HashSet<>(this.vertices);
//        throw new RuntimeException("not implemented");
    }
    
    
//    jdk8 �е��漼���ɣ�
//    streamʹ��
//    edge->edge.getTarget().equals() ��filterҪ����Predicate<L>��Predicate��һ������ʽ�ӿڣ��ʿ��Դ���lambda���ʽʵ������
//    Edge::getSource ˫ð������ ����::������   ��Ӧ���� Function<T,R>
//    predicate��Funciton���Ǻ���ʽ�ӿڣ�predicate����boolean Function���ؽ������
    
    @Override public Map<L, Integer> sources(L target) {
//        throw new RuntimeException("not implemented");
    	return edges.stream()
    			.filter(edge->edge.getTarget().equals(target))
    			.collect(Collectors.toMap(Edge::getSource, Edge::getWeight));
    }
    
    @Override public Map<L, Integer> targets(L source) {
    	return edges.stream()
    			.filter(edge->edge.getSource().equals(source))
    			.collect(Collectors.toMap(Edge::getTarget, Edge::getWeight));
    }
    
    @Override public String toString() {
    	return String.format("ConcreteEdgesGraph<L> with %d vertices and %d edges", 
    			vertices.size(),edges.size());
    }
    
    // TODO toString()
    
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    private L source;
    private L target;
    private int weight;
    
    // Abstraction function:
    //   AF(source,target,weight)=source��target��һ����
    // Representation invariant:
    //   source,target!=null weigth>0
    // Safety from rep exposure:
    //   ��������immutable�����迼��
    
	
	public Edge(L sourceL,L targetL,int weight) {
		this.source = sourceL;
		this.target = targetL;
		this.weight = weight;
		checkRep();
	}
	
	private void checkRep() {
		assert this.source != null;
		assert this.target != null;
		assert this.weight > 0;
	}
	
	
//	getter
	
	public L getSource() {
		return source;
	}


	public L getTarget() {
		return target;
	}


	public int getWeight() {
		return weight;
	}

	/**
	 * �ж�Edge�Ƿ����ָ���ڵ�vertex
	 * @param vertex
	 * @return 1����Դ�� 2�����յ㣬-1��������
	 */
	public int judgeVertex(L vertex) {
		if(this.getSource().equals(vertex)) {
			return 1;
		}else if(this.getTarget().equals(vertex)) {
			return 2;
		}
		return -1;
	}
	
	@Override public String toString() {
		return String.format("Edge from %s to %s , weight is %d",
				this.getSource().toString(),this.getTarget().toString(),this.getWeight());
	}
	
	@Override public boolean equals(Object tmpEdge) {
		if(tmpEdge instanceof Edge) {
			
			Edge<?> bEdge = (Edge<?>) tmpEdge;
			if(this.getSource().equals(bEdge.getSource()) && 
					this.getTarget().equals(bEdge.getTarget()) &&
					this.getWeight() == bEdge.getWeight())
				return true;
			else return false;
		} else {
			return false;
		}
	}
	
	@Override public int hashCode() {
		final int hashFac = 31;
		return this.getSource().hashCode()*hashFac
				+this.getTarget().hashCode()*hashFac
				+this.getWeight();
	}

}
