package adts;

import java.util.ArrayList;
import java.util.List;

/**
 * .
 *
 * @author hahalidaxin
 */
public class Ladder {

  /**
   * AF: 编号为LadderIndex，从左到右梯子为rungsL2R的梯子。
   * RI: true
   * Rep Explosure: 调用的时候需要保证传入之后的rungs不能再次改变，get操作返回的Rung是不可变类型
   * Thread Safety：不可变类型，线程安全
   */

  private int ladderIndex;
  private List<Rung> rungsL2R = new ArrayList<>();

  public Ladder(int ladderIndex,List<Rung> rungs) {
    this.ladderIndex = ladderIndex;
    rungsL2R.addAll(rungs);
  }

  public Rung getRungL2R(int rungIndex) {
    return rungsL2R.get(rungIndex-1);
  }

  public Rung getRungR2L(int rungIndex) {
    return rungsL2R.get(rungsL2R.size()-rungIndex);
  }

  public int getRungLength() {
    return rungsL2R.size();
  }

  public int getLadderIndex() {
    return ladderIndex;
  }
}
