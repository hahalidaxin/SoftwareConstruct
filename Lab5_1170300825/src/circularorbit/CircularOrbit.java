package circularorbit;

import centralobject.CentralObject;
import java.util.List;
import javax.swing.JPanel;
import otherdirectory.Difference;
import physicalobject.PhysicalObject;
import track.Track;

/**
 * .
 *
 * @param <L> 中心点物体类型
 * @param <E> 轨道物体类型
 */
public interface CircularOrbit<L, E> {
  /**
   * .
   * 静态工厂类，返回一个空的ConcreteCircularOrbit具体实现类
   *
   * @return 空实例
   */
  static CircularOrbit<CentralObject, PhysicalObject> empty() {
    return new ConcreteCircularOrbit<CentralObject, PhysicalObject>();
  }

  /**
   * * .
   * 增加一条轨道
   * @param newTrack 需要添加的轨道 不为null
   * @return 是否添加成功 如果已经包含该newTrack或是添加失败则返回false 否则返回true
   */
  boolean addTrack(Track newTrack);

  /**
   * .
   * 删除一条轨道
   *
   * @param rmTrack 需要进行删除的轨道 不为null
   * @return 删除是否成功 如果不包含该rmTrack或者删除失败则返回false 否则返回true
   */
  boolean removeTrack(Track rmTrack);

  /**
   * .
   * 添加一个中心点物体（不考虑物理位置）
   *
   * @param co 需要添加的中心点物体
   */
  void addCentralObject(L co);

  /**
   * .
   * 向特定轨道上添加一个物体（不考虑物理位置）
   *
   * @param po 需要添加的物体po，po不为null，而且其他轨道不能已经包含该po
   * @param tk 添加到tk贵大so上，该轨道必须已经在轨道系统中
   * @return 是否添加成功 如果添加成功则返回true 如果添加失败则返回false
   */
  boolean addPhysicalObj2Track(E po, Track tk);

  /**
   * .
   * 删除一个轨道物体
   *
   * @param oldObj 需要在轨道系统中进行删除的轨道物体，轨道系统中必须已经包含该轨道物体
   */
  void removePhysicalObject(E oldObj);

  /**
   * .
   * 添加中心点物体和轨道物体之间的单向关系
   *
   * @param centralObject  中心物体，不为null且与轨道系统的中心物体相同
   * @param physicalObject 轨道物体，buweinull且需要存在于轨道系统中
   * @param weight         关系的权重
   * @return 添加结果
   */
  boolean addRelationOfCentralObj2TrackObj(L centralObject, E physicalObject, double weight);

  /**
   * .
   * 添加两个轨道物体之间的关系
   *
   * @param physicalObj1 轨道物体1，要求不为空而且存在于轨道系统中
   * @param physicalObj2 轨道物体2，要求不为空而且存在于轨道系统中
   * @param weight       关系的权重
   * @return 添加结果
   */
  boolean addRelationOf2TrackObs(E physicalObj1, E physicalObj2, double weight);


  //void transit(E oldObj, E newObj, Track t);

  /**
   * .
   * 获得该系统的熵值
   *
   * @return 轨道系统的熵值，如果一条轨道上轨道物体的数目为0则忽略该轨道
   */
  double getObjectDistributionEntropy();

  /**
   * .
   * 获得e1和e2之间的逻辑距离
   *
   * @param e1 起始点，要求e1处于轨道系统中
   * @param e2 结束点，要求e2处于轨道系统中
   * @return e1和e2之间的逻辑距离 如果e1和e2不连通则返回Integer.MAXVALUE，要求距离>=0
   */
  int getLogicalDistance(E e1, E e2);

  /**
   * .
   * 获得当前轨道系统与另一个轨道系统之间的轨道差异
   *
   * @param that 另一个进行比较的轨道系统 ，要求that!=null
   * @return 比价之后产生的轨道系统差异对象
   */
  Difference getDifference(CircularOrbit<L, E> that);

  /**
   * .
   * 获得排序之后的轨道列表
   *
   * @return 已经排好序的轨道列表
   */
  List<Track> getSortedTrack();

  /**
   * .
   * 获得一条轨道上的所有轨道物体
   *
   * @param tk 目标轨道
   * @return tk轨道上所有的轨道物体, 如果该轨道不存在该轨道系统中，则返回空数组
   */
  List<E> getPhysicalObjectsOnTrack(Track tk);

  /**
   * .
   * 按照题目要求检查轨道系统是否合法
   *
   * @return 是否合法
   */
  boolean checkOribitAvailable();

  /**
   * .
   * 将轨道系统可视化为一个JPanel
   * 特殊方法 如果ConcreteCircularOrbit类调用则报错，必须由子类实现并调用
   */
  JPanel visualizeContentPanel();

  /**
   * .
   * 将轨道物体可视化之后的JPanel添加到外部容器panel中
   * 特殊方法 如果ConcreteCircularOrbit类调用则报错，必须由子类实现并调用
   */
  void visualize(JPanel panel);

  /**
   * .
   * 获得轨道系统中所有轨道半径
   *
   * @return 返回一个列表，列表中包含所有轨道系统的String类型的半径内
   */
  List<String> getTrackRadiusList();

}
