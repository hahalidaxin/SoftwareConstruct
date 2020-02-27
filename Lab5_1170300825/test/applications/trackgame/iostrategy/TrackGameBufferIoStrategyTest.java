package applications.trackgame.iostrategy;

import applications.trackgame.TrackGame;
import applications.trackgame.assignstrategy.RandomStrategy;
import org.junit.jupiter.api.Test;
import otherdirectory.exception.CheckedException;

/**
 * .
 *
 * @author hahalidaxin
 */
public class TrackGameBufferIoStrategyTest {
  @Test
  void testIO() {
    try {
      TrackGame game = new TrackGame("src/configfile/trackgame.txt");
      game.inputOrbitInfo("src/configfile/trackgame.txt", new TrackGameBufferIoStrategy());
      game.divideIntoGroups(new RandomStrategy());
      game.outputOrbitInfo("out/output/trackgame.txt", new TrackGameScannerIoStrategy());
    } catch (CheckedException e) {
      e.printStackTrace();
    }
  }
}
