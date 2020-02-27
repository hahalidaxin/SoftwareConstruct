package adts;

/**
 * .
 *
 * @author hahalidaxin
 */
public class Rung {

  /**
   * AF: 顺序（从L->R）为rungIndex的踏板
   * RI: true
   * Rep Explosure : 没有泄露的操作
   * Thead Safety：Immutable类型对象，线程安全
   */

  private int rungIndex;

  public Rung(int rungIndex) {
    this.rungIndex = rungIndex;
  }

  public int getRungIndex() {
    return rungIndex;
  }
}
