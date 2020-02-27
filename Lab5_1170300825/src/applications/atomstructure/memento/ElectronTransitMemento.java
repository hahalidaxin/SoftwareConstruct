package applications.atomstructure.memento;

import applications.atomstructure.Electron;
import track.Track;

/**
 * .
 * Memento - 备忘录
 */
public class ElectronTransitMemento {

  Electron electron;
  Track fromTrack;
  Track toTrack;

  /**
   * .
   * 构造方法
   *
   * @param electron  电子
   * @param fromTrack 源轨道
   * @param toTrack   目标轨道
   */
  public ElectronTransitMemento(Electron electron, Track fromTrack, Track toTrack) {
    this.electron = electron;
    this.fromTrack = fromTrack;
    this.toTrack = toTrack;
  }

  public Electron getElectron() {
    return electron;
  }

  public Track getFromTrack() {
    return fromTrack;
  }

  public Track getToTrack() {
    return toTrack;
  }

  @Override
  public String toString() {
    return String.format("Transit e from %.2f to %.2f", fromTrack.getRadius(), toTrack.getRadius());
  }
}
