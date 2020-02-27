package otherdirectory;

import applications.socialnetworkcircle.CentralUser;
import applications.socialnetworkcircle.Friend;
import otherdirectory.exception.UncheckedException;

/**
 * .
 * Immutable Object
 * 两个物体之间的关系
 * 单向边，但是有节点顺序
 * 相等判断使用值相等
 */
public class Relation<L, E> {
  //AF(*) = 一条以obju开始到objv结束权值为weight的有向边
  //RI ： obju!=null && objv!=null
  //Rep Explosure : 在getter中暴露了obju和objv但是LE类型都是Immutable的类型 所以安全

  L obju;
  E objv;
  double weight;

  /**
   * .
   * 构造方法
   * @param obju 边的起点
   * @param objv 边的终点
   * @param weight 边的权重
   */
  public Relation(L obju, E objv, double weight) {
    this.obju = obju;
    this.objv = objv;
    this.weight = weight;
    checkRep();
  }

  public static Relation<Friend, Friend> getFriendsTie(Friend obju, Friend objv, double weight) {
    return new Relation<>(obju, objv, weight);
  }

  public static Relation<CentralUser, Friend> getCentralUserTie(
      CentralUser obju,
      Friend objv,
      double weight) {
    return new Relation<>(obju, objv, weight);
  }

  private void checkRep() {
    UncheckedException.assertTrue(obju != null && objv != null,
        "不满足uv不空的条件");
  }

  public L getObju() {
    return obju;
  }

  public E getObjv() {
    return objv;
  }

  public double getWeight() {
    return weight;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj instanceof Relation) {
      Relation<L, E> that = (Relation<L, E>) obj;
      return this.obju.equals(that.obju)
          && this.objv.equals(that.objv)
          && this.weight == that.weight;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return this.obju.hashCode() * 31 * 31 + this.objv.hashCode() * 31 + (int) this.weight;
  }

}
