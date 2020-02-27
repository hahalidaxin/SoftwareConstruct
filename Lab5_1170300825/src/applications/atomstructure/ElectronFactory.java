package applications.atomstructure;

import java.util.HashMap;
import java.util.Map;
import track.Track;

/**
 * .
 *
 * @author hahalidaxin
 */
public class ElectronFactory {
  static Map<Track, Electron> electrons = new HashMap<>();

  /**
   * .
   * 获取处于track轨道上的电子的实例
   * @param track
   * @return
   */
  public static Electron getInstance(Track track) {
    Electron electron = electrons.get(track);
    if (electron == null) {
      electron = Electron.getInstance();
      electrons.put(track, electron);
    }
    return electron;
  }

}
