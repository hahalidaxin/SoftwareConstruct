package applications.trackgame.iostrategy;

import applications.trackgame.TrackGame;
import otherdirectory.exception.CheckedException;

/**
 * @author hahalidaxin
 * @date 2019/5/21
 */
public interface TrackGameIoStrategy {
  void read(TrackGame tg, String filepath) throws CheckedException;

  void write(TrackGame tg, String filepath);

  Long getOutputTime();

  Long getInputTime();
}
