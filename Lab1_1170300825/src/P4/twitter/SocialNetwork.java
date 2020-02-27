/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {
	public static final int SHARETAGS_THRESHOLD=2;
	public static final int UNINFULENCETAG_THRESHOLD=3;
    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
//    	猜测谁会follow谁
//    	1st given evidence：如果A@B 则认为A可能 follow B
    	Map<String, Set<String>> SocialNetwork = new HashMap<String, Set<String>>();
        List<Tweet> socialtweet = new ArrayList<Tweet>(tweets);
        for (Tweet tweet: socialtweet){
            Set<String> blank = new HashSet<String>();
            String authorName=tweet.getAuthor().toLowerCase();
//		根据发表的tweet 提取所有@-mentions的username -- author may follow @username
            Set<String> matchstring = Extract.getMentionedUsers(Arrays.asList(tweet));
            if(!SocialNetwork.containsKey(authorName)) {
            	SocialNetwork.put(authorName, new HashSet<String>());
            }
            SocialNetwork.get(authorName).addAll(matchstring);
            for (String author: matchstring){
                if(!author.equals(tweet.getAuthor())){
                    SocialNetwork.get(tweet.getAuthor().toLowerCase()).add(author.toLowerCase());
                }
            }
        }
        
//        2ed given evidence：如果AB具有多个hashTag重叠或者 共享有一个热点很低的hashTag 那么可以认为AB相互影响
//		策略对结果的影响不大，如果基于更加真实的数据可能效果更明显
        Map<String, Set<String>> usersTagsMap=Extract.getMetionedHashTags(tweets);
        Map<String, Integer> tagInfluenceCountMap=new HashMap<String, Integer>();
        
//       获得tag的出现次数作为每条tag的热度
        for(String username:usersTagsMap.keySet()) {
        	for(String tagString:usersTagsMap.get(username)) {
        		if(!tagInfluenceCountMap.containsKey(tagString)) {
        			tagInfluenceCountMap.put(tagString, 1);
        		} else {
        			tagInfluenceCountMap.put(tagString,tagInfluenceCountMap.get(tagString)+1);
        		}
        	}
        }
        	
        List<String> userList=new ArrayList<String>(usersTagsMap.keySet());
        for(int i=0;i<userList.size();i++) {
        	String userA=userList.get(i);
        	for(int j=i+1;j<userList.size();j++) {
        		String userB=userList.get(j);
        		Set<String> userATagSet=usersTagsMap.get(userA);
        		Set<String> userBTagSet=usersTagsMap.get(userB);
//        		if(userATagSet.size()>1)
//        			System.out.println(userA+":"+userATagSet.size()+userB+" :"+userBTagSet.size());
//        		获得交集
        		userATagSet.retainAll(userBTagSet);
//        		if(userATagSet.size()>1)
//        			System.out.println("jiao:"+userATagSet.size());
        		for(String tag:userATagSet) {
        			if(tagInfluenceCountMap.get(tag)<=UNINFULENCETAG_THRESHOLD) {
        				if(!SocialNetwork.containsKey(userA)) {
            				SocialNetwork.put(userA, new HashSet<String>());
            			}
            			if(!SocialNetwork.containsKey(userB)) {
            				SocialNetwork.put(userB, new HashSet<String>());
            			}
        				Set<String> aFollowerSet=SocialNetwork.get(userA);
            			Set<String> bFollowerSet=SocialNetwork.get(userB);
            			aFollowerSet.add(userB);
            			bFollowerSet.add(userA);
            			SocialNetwork.put(userA,aFollowerSet);
            			SocialNetwork.put(userB,bFollowerSet);
            			break;
        			}
        		}
        		if(userATagSet.size()>=SHARETAGS_THRESHOLD) {
        			if(!SocialNetwork.containsKey(userA)) {
        				SocialNetwork.put(userA, new HashSet<String>());
        			}
        			if(!SocialNetwork.containsKey(userB)) {
        				SocialNetwork.put(userB, new HashSet<String>());
        			}
        			Set<String> aFollowerSet=SocialNetwork.get(userA);
        			Set<String> bFollowerSet=SocialNetwork.get(userB);
        			aFollowerSet.add(userB);
        			bFollowerSet.add(userA);
        			SocialNetwork.put(userA,aFollowerSet);
        			SocialNetwork.put(userB,bFollowerSet);
        		}
        	}
        }
        
        
        return SocialNetwork;            
//        throw new RuntimeException("not implemented");
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
//    	将社交网络中的user按照影响力递减排序
    	List<String> InfluenceList = new ArrayList<String>();
        Map<String, Integer> influencers = new HashMap<String, Integer>();
        int influenceCount;
//        根据folloGraph获得所有user的follower数目
        for (Set<String> follows : followsGraph.values()){
            for(String i: follows){
                if(influencers.containsKey(i)){
                    influenceCount = influencers.get(i) + 1;
                    influencers.put(i.toLowerCase(), influenceCount);
                }
                else{
                    influencers.put(i.toLowerCase(), 1);
                }
            }
        }
//        调用Array.sort() 并重写comparator进行排序
        InfluenceList = new ArrayList<String>(influencers.keySet());
        InfluenceList.sort(new Comparator<Object>() {
        	public int compare(Object o1,Object o2) {
        		int fl1=influencers.get((String)o1);
        		int fl2=influencers.get((String)o2);
        		if(fl1>fl2) {
        			return -1;
        		} else if(fl1<fl2) {
        			return 1;
        		}
        		return 0;
        	}
		});
//        sort test
//        for(String username:InfluenceList) {
//        	System.out.println(username+": "+influencers.get(username));
//        }
        return InfluenceList;
//        throw new RuntimeException("not implemented");
    }

}
