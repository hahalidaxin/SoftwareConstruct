package applications.socialnetworkcircle.iostrategy;

import applications.socialnetworkcircle.SocialNetworkCircle;
import org.junit.jupiter.api.Test;
import otherdirectory.exception.CheckedException;

/**
 * .
 *
 * @author hahalidaxin
 */
public class SocialNetworkNioStrategyTest {
  @Test
  void testIo() {
    try {
      SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configFile/SocialNetworkCircle.txt");
      socialNetworkCircle.inputOrbitInfo("src/configFile/SocialNetworkCircle.txt",new SocialNetworkNioStrategy());
      socialNetworkCircle.outputOrbitInfo("out/output/socialnetwork.txt",new SocialNetworkNioStrategy());
    } catch(CheckedException e) {
      System.out.println(e.toString());
    }
  }
}
