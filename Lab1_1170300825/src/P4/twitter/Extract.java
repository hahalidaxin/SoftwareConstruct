/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {
	
	//获取所有tweets中最早的发布时间
	public static Instant getEarliestTime(List<Tweet> tweets) {
        Instant earliestTime = Instant.MAX;
        for (Tweet tweet : tweets) {
            if (tweet.getTimestamp().isBefore(earliestTime)) {
                earliestTime = tweet.getTimestamp();
            }
        }
        return earliestTime;
    }
	//获取所有tweets中最晚的发布时间
    public static Instant getLatestTime(List<Tweet> tweets) {
        Instant latestTime = Instant.MIN;
        for (Tweet tweet : tweets) {
            if (tweet.getTimestamp().isAfter(latestTime)) {
                latestTime = tweet.getTimestamp();
            }
        }
        return latestTime;
    }

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
//    	通过所有tweets列表获得时间间隔
    	if (tweets.isEmpty()) {
            return new Timespan(Instant.now(), Instant.now());
        } else {
            return new Timespan(getEarliestTime(tweets), getLatestTime(tweets));
        }
//        throw new RuntimeException("not implemented");
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
//    	获取在tweets列表中所有被@到的username
    	Set<String> ansSet=new HashSet<String>();
//    	首先通过正则表达式匹配满足username命名的字符串，这样保证了username-mention之后不会有username中的字符
    	Pattern pattern = Pattern.compile("@([\\w,-]+)");
//    	String substring=" @u9ser     @lidaxin kfjdalj _2@daxin";
    	for(Tweet tweet:tweets) {
    		String substring=tweet.getText();
    		Matcher matcher = pattern.matcher(substring.toLowerCase());
        	while(matcher.find()){
//        		对匹配到的字符串进行验证 如果之前一个字符（有的话）不是username中的任意一个字符则获取到的username满足要求
        		String usernameString=null;
        		int start=matcher.start();
                if(start>0) {
                	Character preChar = substring.charAt(start-1);
                	if(preChar!='-'&& preChar!='_' && !Character.isLetter(preChar) && !Character.isDigit(preChar)) {
                		usernameString = matcher.group(1);
                	}
                }else {	//start==0 匹配username-mention在开头
                	usernameString = matcher.group(1);
                }
                if(usernameString!=null)
                	ansSet.add(usernameString); 
             }
    	}
    	return ansSet;
//        throw new RuntimeException("not implemented");
    }
    
    /**
                *   自实现 获得一条tweet中所有的hash tag
                *   默认hash tag与username的编码规则相同 且同样不区分大小写
     * @return hashtag 的集合
     */
    public static Map<String,Set<String>> getMetionedHashTags(List<Tweet> tweets) {
    	Pattern pattern = Pattern.compile("#([\\w,-]+)");
    	Map<String, Set<String>> ansMap=new HashMap<String, Set<String>>();
    	for(Tweet tweet:tweets) {
    		String authorString=tweet.getAuthor();
	    	String substring=tweet.getText();
	    	if(!ansMap.containsKey(authorString)) {
	    		ansMap.put(authorString, new HashSet<String>());
	    	}
	    	Set<String> ansSet=new HashSet<String>();
	    	Matcher matcher = pattern.matcher(substring.toLowerCase());
	    	while(matcher.find()){
	    		Set<String> hashtags=ansMap.get(authorString);
	    		hashtags.add(matcher.group(1));
	    		ansMap.put(authorString, hashtags);
	         }
    	}
    	return ansMap;
    }

}
