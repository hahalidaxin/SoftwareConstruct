package applications.socialnetworkcircle.iostrategy;

import applications.socialnetworkcircle.SocialNetworkCircle;
import org.junit.jupiter.api.Test;
import otherdirectory.exception.CheckedException;

/**
 * .
 *
 * @author hahalidaxin
 */
public class SocialNetworkScannerStrategyTest {
  @Test
  void testIo() {
    try {
      SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configFile/SocialNetworkCircle.txt");
      socialNetworkCircle.inputOrbitInfo("src/configFile/SocialNetworkCircle.txt",new SocialNetworkScannerStrategy());
      socialNetworkCircle.outputOrbitInfo("out/output/socialnetwork.txt",new SocialNetworkScannerStrategy());
    } catch(CheckedException e) {
      System.out.println(e.toString());
    }
  }
}
