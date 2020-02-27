/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class ExtractTest {

	/*
	 * TODO: your testing strategies for these methods should go here. See the
	 * ic03-testing exercise for examples of what a testing strategy comment looks
	 * like. Make sure you have partitions.
	 */

	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

	private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
	private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
	private static final Tweet tweet3 = new Tweet(2, "bbitdiddle", "@Xin.  bitdiddle@mit.edu -@lidaxin @LIDAXIN _@lidaxin.rivest talk in 30 minutes #hype", d2);

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testGetTimespanTwoTweets() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));

		assertEquals("expected start", d1, timespan.getStart());
		assertEquals("expected end", d2, timespan.getEnd());
	}

	@Test
    public void testGetMentionedUsersNoMention() {
		List<Tweet> tweets=new ArrayList<Tweet>(); 
		tweets.add(tweet1);
		tweets.add(tweet3);
        Set<String> mentionedUsers = Extract.getMentionedUsers(tweets);
        for(String username:mentionedUsers) {
        	System.out.println(username);
        }
//        assertTrue("expected empty set", mentionedUsers.isEmpty());
    	
    }
	
	@Test
	public void regexMatchTest() {
//    	\W 匹配非字母、数字、下划线。等价于 '[^A-Za-z0-9_]'
//    	\w 匹配字母、数字、下划线。等价于'[A-Za-z0-9_]'
    	Pattern pattern = Pattern.compile("@([\\w,-]+)");
    	String substring=" @u9ser:     @lidaxin kfjdalj _2@daxin";
    	Matcher matcher = pattern.matcher(substring.toLowerCase());
    	while(matcher.find()){
    		System.out.println(matcher.start());
    		String usernameString=null;
    		int start=matcher.start();
            if(start>0) {
            	Character preChar = substring.charAt(start-1);
            	if(preChar!='-'&& preChar!='_' && !Character.isLetter(preChar) && !Character.isDigit(preChar)) {
            		System.out.println(matcher.group(1));
            		usernameString = matcher.group(1);
            	}
            }else {
            	System.out.println(matcher.group(1));
            	usernameString = matcher.group(1);
            }
//            mentionedusersLowerCase.add(matcher.group(1)); 
         }
	}
 
	/*
	 * Warning: all the tests you write here must be runnable against any Extract
	 * class that follows the spec. It will be run against several staff
	 * implementations of Extract, which will be done by overwriting (temporarily)
	 * your version of Extract with the staff's version. DO NOT strengthen the spec
	 * of Extract or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own that
	 * you have put in Extract, because that means you're testing a stronger spec
	 * than Extract says. If you need such helper methods, define them in a
	 * different class. If you only need them in this test class, then keep them in
	 * this test class.
	 */

}
