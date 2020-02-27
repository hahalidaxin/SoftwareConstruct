package applications.socialnetworkcircle;

import applications.socialnetworkcircle.iostrategy.SocialNetworkScannerStrategy;
import org.junit.jupiter.api.Test;
import otherdirectory.exception.CheckedException;

/**
 * .
 *
 * @author hahalidaxin
 */
public class SocialNetworkTest {
  @Test
  void testIO() {
    try {
      SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle.txt");
      socialNetworkCircle.initialize();
      socialNetworkCircle.outputOrbitInfo("out/output/socialnetwork.txt",new SocialNetworkScannerStrategy());
    } catch(CheckedException e) {
      System.out.println(e.toString());
    }
  }
}
