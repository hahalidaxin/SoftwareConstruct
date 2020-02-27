package P4.twitter;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ListModel;

import org.junit.jupiter.api.Test;

class MyExtractTest {

	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

	private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
	private static final Tweet tweet2 = new Tweet(2, "lidaxin", "rivest talk in 30 minutes #hype", d2);
	private static final Tweet tweet3 = new Tweet(2, "bbitdiddle", "@Xin.  bitdiddle@mit.edu -@lidaxin #MIT @LIDAXIN _@lidaxin.rivest talk in 30 minutes #MITComputing", d2);
	
	@Test
	void testGetMetionedHashTags() {
		List<Tweet> tweets=new ArrayList<Tweet>(); 
		tweets.add(tweet1);
		tweets.add(tweet2);
		tweets.add(tweet3);
		Map<String, Set<String>> haSet=Extract.getMetionedHashTags(tweets);
		for(String username:haSet.keySet()) {
			StringBuilder sbBuilder=new StringBuilder();
			sbBuilder.append(username+": ");
			for(String tag:haSet.get(username)) {
				sbBuilder.append(tag+",");
			}
			System.out.println(sbBuilder.toString());
		}
	}

}
