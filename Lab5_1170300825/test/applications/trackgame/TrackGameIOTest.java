package applications.trackgame;

import applications.trackgame.iostrategy.TrackGameScannerIoStrategy;
import org.junit.jupiter.api.Test;
import otherdirectory.exception.CheckedException;

/**
 * .
 *
 * @author hahalidaxin
 */
public class TrackGameIOTest {

  @Test
  void testOutput() {
    try {
      TrackGame game = new TrackGame("src/configfile/trackgame.txt");
      game.initialize();
      game.outputOrbitInfo("out/output/trackgame.txt",new TrackGameScannerIoStrategy());
    } catch (CheckedException e) {
      e.printStackTrace();
    }
  }

}
