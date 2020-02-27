package P4.startup;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Set;

import P4.interval.IntervalConflictException;
import P4.interval.IntervalSet;
import P4.interval.MultiIntervalSet;

/**
 * A [TODO mutable?] measure of similarity between multi-interval sets of
 * Strings. 
 * 
 * An instance of Similarity uses a client-provided definition of label
 * similarities, where 0 is least similar and 1 is most similar.
 * 
 * Given two multi-interval sets, let min be the minimum start of any of their
 * intervals, and let max be the maximum end. The similarity between the two
 * sets is the ratio: 
 * 		(sum of piecewise-matching between the sets) / (max - min)
 * 
 * The amount of piecewise-matching for any unit interval [i, i+1) is:
 * 		0 if neither set has a label on that interval 
 * 		0 if only one set has a label on that interval 
 * 		otherwise, the similarity between the labels as defined for this Similarity instance 
 * 
 * For example, suppose you have multi-interval sets that use labels "happy", "sad", and "meh"; 
 * and similarity between labels is defined as:
 * 
 * 		1 if both are "happy", both "sad", or both "meh" 
 * 		0.5 if one is "meh" and the other is "happy" or "sad" 
 * 		0 otherwise 
 * 
 * Then the similarity between these two sets: 
 * 		{ "happy" = [[0, 1), [2,4)], "sad" = [[1,2)] } 
 * 		{ "sad" = [[1, 2)], "meh" = [[2,3)], "happy" = [[3,4)] } 
 * 
 * would be: (0 + 1 + 0.5 + 1) / (4 - 0) = 0.625
 * 
 * PS2 instructions: this is a required ADT class, and you MUST NOT weaken the
 * required specifications. However you MAY strengthen the specifications and
 * you MAY add additional methods.
 */

public class Similarity {
	
	private ArrayList<Triple> simRulesArrayList;
	
	/**
	 * Create a new Similarity where similarity between labels is defined in the given file. 
	 * Each line of similarities must contain exactly three pieces, separated by one or more spaces. 
	 * The first two pieces give a pair of strings, and the third piece gives the decimal similarity 
	 * between them, in a format allowed by Double.valueOf(String), between 0 and 1 inclusive. 
	 * 
	 * Similarity between labels is symmetric, so the order of strings in the pair is irrelevant. 
	 * A pair may not appear more than once. The similarity between all other pairs of strings is 0. 
	 * This format cannot define non-zero similarity for strings that contain newlines or spaces, 
	 * or for the empty string.
	 * 
	 * For example, the following file defines the similarity function used in the example at the top of this class:

 	 * happy happy 1
 	 * sad   sad   1
 	 * meh   meh   1
 	 * meh   happy 0.5
 	 * meh   sad   0.5

	 * @param similarities label similarity definition as described above
	 * @throws IOException if the similarity file cannot be found or read
	 */
	public Similarity(File similarities) throws IOException {
		simRulesArrayList = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(similarities));
		String stringLine=reader.readLine();
		while(stringLine!=null) {
			String[] splitLine=stringLine.split("\\s");
//			System.out.println(splitLine[0]+splitLine[1]+splitLine[2]);
			simRulesArrayList.add(new Triple(splitLine[0], splitLine[1], Double.valueOf(splitLine[2])));
			if(!splitLine[0].equals(splitLine[1])) {
				simRulesArrayList.add(new Triple(splitLine[1], splitLine[0], Double.valueOf(splitLine[2])));
			}
			stringLine = reader.readLine();
		}
//		throw new RuntimeException("not implemented");
	}
	
	/**
	 * 判断value是否处于 [start,end)区间内部
	 * 
	 * @param value
	 * @param start
	 * @param end
	 * @return 判断value是否处于 [start,end) 区间内部
	 */
	public static boolean valueAtRange(long value,long start,long end) {
		return value>=start && value<end;
	}
	
	public static int rangeContain(long stA,long edA,long stB,long edB) {
		if(valueAtRange(stA, stB, edB) && edA>=stB && edA<=edB) {
//			A被B包含
			return 1;
		}
		if(valueAtRange(stB, stA, edA) && edB>=stA && edB<=edA) {
//			B被A包含
			return 2;
		}
//		不存在包含关系
		return 0;
	}
	
	/**
	 * 	计算两个MutilIntervalSet的交集长度
	 * @param itA
	 * @param itB
	 * @return 交集长度
	 */
	public static Double caculatePieceWiseMatching(IntervalSet<Integer> itA,IntervalSet<Integer> itB) {
		int pa=0,pb=0;
		int paLen=itA.labels().size(),pbLen=itB.labels().size();
		Double matchingLenDouble = 0.0;
		while(true) {
			if(pa>=paLen || pb>=pbLen) {
				break;
			}
			long startA = itA.start(pa),startB = itB.start(pb);
			long endA = itA.end(pa),endB = itB.end(pb);
			int containType=rangeContain(startA, endA, startB, endB);
			if(containType==1) {
//				A被B包含
				matchingLenDouble += endA-startA;  
				pa++;
			} else if(containType==2) {
//				B被A包含
				matchingLenDouble += endB-startB;
				pb++;
			} else {
				if(valueAtRange(startA, startB, endB)) {
//					交集：startA位于B区间
					matchingLenDouble += endB-startA;
					pb++;
				} else if(valueAtRange(startB, startA, endA)) {
//					交集：startB位于A区间
					matchingLenDouble += endA-startB;
					pa++;
				} else {
					if(startA<startB) pa++;
					else pb++;
				}
			}
		}
		return matchingLenDouble;
	}
	
	/**
	 * Compute similarity between two multi-interval sets. Returns a value between 0 and 1 inclusive.
	 * 
	 * @param a non-empty multi-interval set of strings
	 * @param b non-empty multi-interval set of strings
	 * @return similarity between a and b as defined above
	 */
	public double similarity​(MultiIntervalSet<String> a, MultiIntervalSet<String> b) {
		Set<String> labelListA = a.labels();
		Set<String> labelListB = b.labels();
		Double ansDouble = 0.0;
		long minDouble=Long.MAX_VALUE,maxDouble=Long.MIN_VALUE;
		
		try {
			for(String label:labelListA) {
				IntervalSet<Integer> itSet=a.intervals(label);
				for(int i=0;i<itSet.labels().size();i++) {
					long st=itSet.start(i);
					long ed=itSet.end(i);
					if(st<minDouble) minDouble=st;
					if(ed>maxDouble) maxDouble=ed;
				}
			}
			for(String label:labelListB) {
				IntervalSet<Integer> itSet=b.intervals(label);
				for(int i=0;i<itSet.labels().size();i++) {
					long st=itSet.start(i);
					long ed=itSet.end(i);
					if(st<minDouble) minDouble=st;
					if(ed>maxDouble) maxDouble=ed;
				}
			}
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		for(Triple triple:simRulesArrayList) {
			String labelStart=triple.getStart();
			String labelEnd=triple.getEnd();
			Double labelValue=triple.getValue();
			if(labelListA.contains(labelStart)&&labelListB.contains(labelEnd)) {
				ansDouble += labelValue*caculatePieceWiseMatching(a.intervals(labelStart), b.intervals(labelEnd));
			}
		}
		
		return ansDouble/(maxDouble-minDouble);
//		throw new RuntimeException("not implemented");
	}
}

class Triple {
	private String start,end;
	private Double value;
	
	 public Triple(String start,String end,Double value) {
		this.setStart(start);
		this.setEnd(end);
		this.setValue(value);
	}

	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public String getEnd() {
		return end;
	}


	public void setEnd(String end) {
		this.end = end;
	}


	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	

}
