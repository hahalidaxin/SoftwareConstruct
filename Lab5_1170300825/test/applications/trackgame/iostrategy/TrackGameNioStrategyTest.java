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
public class TrackGameNioStrategyTest {
  @Test
  void testIO() {
    try {
      TrackGame game = new TrackGame("src/configfile/TrackGame_Largest.txt");
      game.inputOrbitInfo("src/configfile/TrackGame_Largest.txt", new TrackGameNioStrategy());
      game.divideIntoGroups(new RandomStrategy());
      game.outputOrbitInfo("out/output/trackgame.txt", new TrackGameNioStrategy());
    } catch (CheckedException e) {
      e.printStackTrace();
    }
  }
}
