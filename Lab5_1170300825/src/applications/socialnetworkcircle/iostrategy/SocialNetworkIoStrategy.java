package applications.socialnetworkcircle.iostrategy;

import applications.socialnetworkcircle.SocialNetworkCircle;
import otherdirectory.exception.CheckedException;

/**
 * .
 *
 * @author hahalidaxin
 */
public interface SocialNetworkIoStrategy {
  void read(SocialNetworkCircle snc, String filepath) throws CheckedException;

  void write(SocialNetworkCircle snc, String filepath);

  Long getOutputTime();

  Long getInputTime();
}
