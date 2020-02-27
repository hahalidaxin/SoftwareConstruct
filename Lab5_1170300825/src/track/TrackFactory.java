package track;

import java.util.HashMap;
import java.util.Map;

/**
 * .
 *
 * @author hahalidaxin
 */
public class TrackFactory {
  private static Map<Double, Track> trackMap = new HashMap<>();

  /**
   * .
   * FlyWeight Track 静态获得方法
   * @param trackRadius 轨道半径
   * @return 一定半径的轨道
   */
  public static Track getTrackInstance(Double trackRadius) {
    Track track = trackMap.get(trackRadius);
    if (track == null) {
      track = Track.getInstance(trackRadius);
      trackMap.put(trackRadius, track);
    }
    return track;
  }
}
