package applications.socialnetworkcircle.iostrategy;

import applications.socialnetworkcircle.SocialNetworkCircle;
import org.junit.jupiter.api.Test;
import otherdirectory.exception.CheckedException;

/**
 * .
 *
 * @author hahalidaxin
 */
public class SocialNetworkBufferStrategyTest {
  @Test
  void testIo() {
    try {
      SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configFile/SocialNetworkCircle.txt");
      socialNetworkCircle.inputOrbitInfo("src/configFile/SocialNetworkCircle.txt",new SocialNetworkBufferStrategy());
      socialNetworkCircle.outputOrbitInfo("out/output/socialnetwork.txt",new SocialNetworkBufferStrategy());
    } catch(CheckedException e) {
      System.out.println(e.toString());
    }
  }
}
