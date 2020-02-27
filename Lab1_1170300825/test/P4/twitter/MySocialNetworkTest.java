package P4.twitter;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class MySocialNetworkTest {

	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

	private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?#hyper", d1);
	private static final Tweet tweet2 = new Tweet(2, "lidaxin", "rivest talk in 30 minutes #hype", d2);
	private static final Tweet tweet3 = new Tweet(2, "bbitdiddle", "@Xin.  bitdiddle@mit.edu -@alyssa #MIT @lidaxin _@lidaxin.rivest talk in 30 minutes #MITComputing",d2);
	@Test
	void testGuessFollowsGraph() {
		List<Tweet> tweets=new ArrayList<Tweet>(); 
		tweets.add(tweet1);
		tweets.add(tweet2);
		tweets.add(tweet3);
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
	}

}
