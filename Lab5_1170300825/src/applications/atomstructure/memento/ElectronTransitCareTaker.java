package applications.atomstructure.memento;

import applications.atomstructure.Electron;
import java.util.ArrayList;
import java.util.List;
import otherdirectory.exception.UncheckedException;
import track.Track;

/**
 * .
 * CareTaker - 负责人
 */
public class ElectronTransitCareTaker {

  List<ElectronTransitMemento> transitHistorys = new ArrayList<>();

  public void addMemento(Electron electron, Track fromTrack, Track toTrack) {
    transitHistorys.add(new ElectronTransitMemento(electron, fromTrack, toTrack));
  }

  /**
   * .
   * 进行历史记录的回退，同时返回区间内部的操作历史
   *
   * @param step 将step之后的所有操作都回退
   * @return 回退点之后的所有操作历史
   */
  public List<ElectronTransitMemento> rebackMemento(int step) {
    UncheckedException.assertTrue(step < transitHistorys.size() && step >= 0, "回退位置错误");

    if (transitHistorys.size() == 0) {
      return null;
    } else {
      List<ElectronTransitMemento> ans = transitHistorys.subList(step, transitHistorys.size());
      transitHistorys = transitHistorys.subList(0, step);
      return ans;
    }
  }

  /**
   * .
   * 获得所有的操作历史
   *
   * @return String 类型 操作历史
   */
  public List<String> getAllHistory() {
    List<String> history = new ArrayList<>();
    int cnt = 0;
    for (ElectronTransitMemento memento : transitHistorys) {
      history.add(String.format("[%d] %s", cnt++, memento.toString()));
    }
    return history;
  }

}
