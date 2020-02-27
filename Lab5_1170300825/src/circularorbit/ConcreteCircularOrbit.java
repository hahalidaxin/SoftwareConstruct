package circularorbit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import otherdirectory.Difference;
import otherdirectory.Relation;
import otherdirectory.exception.UncheckedException;
import track.Track;

public class ConcreteCircularOrbit<L, E>
    implements CircularOrbit<L, E>, Iterable<E>,
    CircularOrbitInfo {
  //AF(*) = 以centralObject为中心点物体的轨道系统，
  //轨道系统中所有轨道为physicalObjectMap.keySet()，轨道上物体映射为physiclObjectMap，
  //中心物体与第一层轨道物体的关系为relOfCobj2TraObj，轨道物体之间的关系为relOf2TraObjs。
  //
  //RI：relOfxxx中的轨道物体必须已经在physicalObjectMap中出现
  //  ，relOfCobj2TracObj中的所有物体都必须处于第一层轨道上
  //
  //Rep Explosure： getTrackPo 返回Track 是Immutable类型
  //                getPhsycialObjList 使用Collections.unmodifiableList进行防御式保护
  //                 对于域修改轨道系统有关系的函数，其输入参数都为Immutable的类型


  private static Logger logger = Logger.getLogger(ConcreteCircularOrbit.class.getName());
  protected L centralObject;
  protected Map<Track, List<E>> physicalObjectMap = new HashMap<>();
  protected List<Relation<L, E>> relOfCobj2TraObj = new ArrayList<>();
  protected Map<E, List<Relation<E, E>>> relOf2TraObjs = new HashMap<>();


  //    提供构造方法
  //空的构造方法
  public ConcreteCircularOrbit() {

  }

  //    不能通过修改操作来构造系统 否则无法正常进行checkRep检查不变性

  /**
   * .
   * 构造方法
   *
   * @param co   中心物体
   * @param pm   轨道-轨道物体的映射
   * @param rel1 中心物体与第一层轨道物体关系
   * @param rel2 轨道物体之间的关系
   */
  public ConcreteCircularOrbit(L co, Map<Track, List<E>> pm,
                               List<Relation<L, E>> rel1, Map<E, List<Relation<E, E>>> rel2) {
    this.centralObject = co;
    this.physicalObjectMap = pm;
    this.relOfCobj2TraObj = rel1;
    this.relOf2TraObjs = rel2;
    checkRep();
  }

  //    维护循环不变性
  protected void checkRep() {
    if (relOf2TraObjs == null) {
      return;
    }

    try {
      Set<E> poSet = new HashSet<>();
      Iterator<E> ite = this.iterator();
      while (ite.hasNext()) {
        poSet.add(ite.next());
      }

      for (Map.Entry<E, List<Relation<E, E>>> entry : relOf2TraObjs.entrySet()) {
        for (Relation<E, E> rel : entry.getValue()) {
          UncheckedException.assertTrue(
              poSet.contains(rel.getObjv()) && poSet.contains(rel.getObju()),
              "relOf2TraObjs不满足循环不变性");
        }
      }
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
  }

  @Override
  public boolean addTrack(Track newTrack) {
    try {
      UncheckedException.assertTrue(newTrack != null, "不满足newTrack不为空的条件");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }

    logger.info(String.format("添加一条半径为%f的圆形轨道", newTrack.getRadius()));
    if (physicalObjectMap.containsKey(newTrack)) {
      return false;
    }
    physicalObjectMap.put(newTrack, new ArrayList<>());
    checkRep();
    return true;
  }

  @Override
  public boolean removeTrack(Track rmTrack) {
    try {
      UncheckedException.assertTrue(physicalObjectMap.containsKey(rmTrack), "不满足rmTrack存在轨道系统的要求");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
    logger.info(String.format("删除一条半径为%f的圆形轨道", rmTrack.getRadius()));
    //删除轨道上的所有物体
    List<E> poList = new ArrayList<>();
    for (E po : physicalObjectMap.get(rmTrack)) {
      poList.add(po);
    }
    for (E po : poList) {
      removePhysicalObject(po);
    }

    if (!physicalObjectMap.containsKey(rmTrack)) {
      return false;
    }
    physicalObjectMap.remove(rmTrack);
    checkRep();
    return true;
  }

  @Override
  public void addCentralObject(L co) {
    this.centralObject = co;
    logger.info("添加中心物体");
  }

  @Override
  public boolean addPhysicalObj2Track(E po, Track tk) {
    try {
      UncheckedException.assertTrue(physicalObjectMap.containsKey(tk), "轨道系统中不包含目标轨道");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
    logger.info(String.format("将物体%s添加到%f轨道", po, tk.getRadius()));

    physicalObjectMap.keySet().stream()
        .filter((x) -> physicalObjectMap.get(x).contains(po))
        .forEach((x) -> {
          UncheckedException.assertTrue(x == tk, "其他轨道已经包含该物体");
        });
    if (physicalObjectMap.get(tk).contains(po)) {
      return false;
    }
    physicalObjectMap.get(tk).add(po);

    if (!relOf2TraObjs.containsKey(po)) {
      relOf2TraObjs.put(po, new ArrayList<>());
    }
    checkRep();
    return true;
  }

  @Override
  public void removePhysicalObject(E oldObj) {
    boolean rmFlag = false;
    for (Map.Entry<Track, List<E>> entry : physicalObjectMap.entrySet()) {
      if (entry.getValue().contains(oldObj)) {
        entry.getValue().remove(oldObj);
        rmFlag = true;
        break;
      }
    }
    try {
      UncheckedException.assertTrue(rmFlag, "轨道系统不包含进行 remove 的对象oldObject");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
    logger.info(String.format("删除轨道物体%s", oldObj));

    //在关系表中删除该节点
    relOfCobj2TraObj.removeIf((x) -> (x.getObju() == oldObj || x.getObjv() == oldObj));


    if (relOf2TraObjs.containsKey(oldObj)) {
      relOf2TraObjs.remove(oldObj);
    }
    for (Map.Entry<E, List<Relation<E, E>>> entry : relOf2TraObjs.entrySet()) {
      entry.getValue().removeIf((x) -> (x.getObjv() == oldObj || x.getObju() == oldObj));
    }
    checkRep();
  }

  @Override
  public boolean addRelationOfCentralObj2TrackObj(L co, E po, double weight) {
    try {
      UncheckedException.assertTrue(co == centralObject, "不满足co限制");
      UncheckedException.assertTrue(getTrackForPO(po) != null && po != null,
          "不满足po限制");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
    logger.info(String.format("添加一条中心物体到轨道物体%s的权重为%f的关系", po, weight));
    boolean ans = relOfCobj2TraObj.add(new Relation<>(co, po, weight));
    checkRep();
    return ans;
  }

  /**
   * .
   * 删除中心物体到轨道物体之间的关系，具体限制与添加spec相同
   *
   * @param co 轨道物体
   * @param po 中心物体
   * @return 删除结果 如果不包含这条关系则返回false 成功删除关系则返回true
   */
  public boolean removeRelationOfCentralObjs2TraObj(L co, E po) {
    try {
      UncheckedException.assertTrue(co == centralObject, "不满足co限制");
      UncheckedException.assertTrue(getTrackForPO(po) != null && po != null,
          "不满足po限制");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
    logger.info(String.format("删除一条中心物体到轨道物体%s的关系", po));
    boolean ans = relOfCobj2TraObj.removeIf(rel -> rel.getObjv().equals(po));
    checkRep();
    return ans;
  }

  @Override
  public boolean addRelationOf2TrackObs(E po1, E po2, double weight) {
    try {
      UncheckedException.assertTrue(getTrackForPO(po1) != null, "po1不存在轨道系统");
      UncheckedException.assertTrue(getTrackForPO(po2) != null, "po2不存在轨道系统");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }

    logger.info(String.format("添加一条轨道物体%s到%s的关系", po1, po2));
    if (!relOf2TraObjs.containsKey(po1)) {
      relOf2TraObjs.put(po1, new ArrayList<>());
    }

    if (relOf2TraObjs.get(po1).contains(new Relation<>(po1, po2, weight))) {
      return false;
    }

    relOf2TraObjs.get(po1).add(new Relation<>(po1, po2, weight));
    checkRep();
    return true;
  }

  /**
   * .
   * 删除一条有向边
   *
   * @param po1 物体1 ，要求必须存在于轨道系统中
   * @param po2 物体2 ，要求必须存在于轨道系统中
   *            ，po1->po2这条边的关系必须存在于轨道系统中
   * @return 删除结果
   */
  public boolean removeRelationOf2TrackObs(E po1, E po2) {
    try {
      UncheckedException.assertTrue(getTrackForPO(po1) != null, "po1不存在轨道系统");
      UncheckedException.assertTrue(getTrackForPO(po2) != null, "po2不存在轨道系统");
      UncheckedException.assertTrue(relOf2TraObjs.get(po1).stream()
          .map(x -> x.getObjv()).collect(Collectors.toList())
          .contains(po2), "两者之间不存在关系");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
    logger.info(String.format("删除一条轨道物体%s到%s的关系", po1, po2));
    boolean ans = relOf2TraObjs.get(po1).removeIf(rel -> rel.getObjv().equals(po2));
    checkRep();
    return ans;
  }

  @Override
  public double getObjectDistributionEntropy() {
    double totalObjs = physicalObjectMap.values().stream()
        .mapToDouble(List::size).sum();

    double ans = physicalObjectMap.values().stream()
        .mapToDouble(List::size)
        .reduce(0.0, (acc, item) -> {
          double p = item / totalObjs;
          if (item > 0) {
            acc -= p * Math.log(p) / Math.log(2.0);
          }
          return acc;
        });
    logger.info(String.format("获取轨道系统的熵值为%f", ans));
    return ans;
  }

  protected Map<E, Integer> getSingleSourceDistances(List<E> sources) {
    try {
      sources.forEach(
          x ->
              UncheckedException.assertTrue(relOf2TraObjs.containsKey(x),
                  "source" + x.toString() + "不存在"));
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }

    Queue<E> queue = new LinkedBlockingQueue<>();
    Map<E, Integer> distantMap = new HashMap<>();

    for (E source : sources) {
      if (!queue.offer(source)) {
        UncheckedException.assertTrue(false, "queue.offer失败");
      }
      distantMap.put(source, 1);
    }
    while (!queue.isEmpty()) {
      E topPerson = queue.poll();
      int nowDis = distantMap.get(topPerson);
      List<E> neighborList = relOf2TraObjs.getOrDefault(topPerson, new ArrayList<>())
          .stream()
          .map(Relation::getObjv).collect(Collectors.toList());
      for (E ps : neighborList) {
        if (!distantMap.containsKey(ps)) {
          distantMap.put(ps, nowDis + 1);
          if (!queue.offer(ps)) {
            UncheckedException.assertTrue(false, "queue.offer失败");
          }
        }
      }
    }
    return distantMap;
  }

  @Override
  public int getLogicalDistance(E e1, E e2) {
    try {
      UncheckedException.assertTrue(e1 != null && e2 != null, "不满足e1e2不为空的条件");

      Map<E, Integer> distanceMap = getSingleSourceDistances(Arrays.asList(e1));
      int ans = distanceMap.getOrDefault(e2, Integer.MAX_VALUE);
      UncheckedException.assertTrue(ans >= 0, "不满足LogicalDistance>=0的后置条件");
      logger.info(String.format("获取从轨道物体%s到%s的逻辑距离为%d", e1, e2, ans));
      return ans;
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
  }

  @Override
  public List<Track> getSortedTrack() {
    return physicalObjectMap.keySet().stream()
        .sorted()
        .collect(Collectors.toList());
  }

  @Override
  public List<E> getPhysicalObjectsOnTrack(Track tk) {
    if (!physicalObjectMap.containsKey(tk)) {
      return new ArrayList<>();
    }
    return Collections.unmodifiableList(physicalObjectMap.get(tk));
  }

  @Override
  public Difference getDifference(CircularOrbit<L, E> that) {
    try {
      UncheckedException.assertTrue(that != null, "不满足that不为空的条件");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
    logger.info("获取两个轨道系统的差异");
    List<Track> thisSortedTracks = this.getSortedTrack();
    List<Track> thatSortedTracks = that.getSortedTrack();
    Difference diff = new Difference(thisSortedTracks.size() - thatSortedTracks.size());

    if (thisSortedTracks.size() < thatSortedTracks.size()) {
      for (int i = 0; i < thatSortedTracks.size() - thisSortedTracks.size(); i++) {
        thisSortedTracks.add(null);
      }
    }

    if (thatSortedTracks.size() < thisSortedTracks.size()) {
      for (int i = 0; i < thatSortedTracks.size() - thisSortedTracks.size(); i++) {
        thatSortedTracks.add(null);
      }
    }

    for (int i = 0; i < thisSortedTracks.size(); i++) {
      Track thisTrack = thisSortedTracks.get(i);
      Track thatTrack = thatSortedTracks.get(i);
      diff.addTrackSet(this.getPhysicalObjectsOnTrack(thisTrack),
          that.getPhysicalObjectsOnTrack(thatTrack));
    }

    return diff;
  }

  /**
   * .
   * 返回当前轨道系统中po所在的轨道Track
   *
   * @param po 查询轨道物体，要求不为空
   * @return po所在的轨道，如果不在该轨道系统中则返回null
   */
  public Track getTrackForPO(E po) {
    try {
      UncheckedException.assertTrue(po != null, "不满足po不为空的要求");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
    boolean ans = false;
    for (Map.Entry<Track, List<E>> entry : physicalObjectMap.entrySet()) {
      if (entry.getValue().contains(po)) {
        return entry.getKey();
      }
    }
    return null;
  }


  @Override
  public boolean checkOribitAvailable() {

    return true;
  }

  @Override
  public List<String> getTrackRadiusList() {
    return physicalObjectMap.keySet().stream()
        .map(x -> Double.toString(x.getRadius()))
        .sorted().collect(Collectors.toList());
  }

  /**
   * .
   * 迭代器实现
   */

  @Override
  public Iterator<E> iterator() {
    return new MyIterator(physicalObjectMap);
  }

  @Override
  public void visualize(JPanel panel) {
    try {
      UncheckedException.assertTrue(false, "调用错误，由子应用系统具体实现");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
  }

  @Override
  public JPanel visualizeContentPanel() {
    try {
      UncheckedException.assertTrue(false, "调用错误，由子应用系统具体实现");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
    return null;
  }

  private class MyIterator<E extends Comparable> implements Iterator<E> {
    private List<E> physicalList;
    private int ite;
    private int size;

    public MyIterator(Map<Track, List<E>> physicalMap) {
      ite = 0;
      size = physicalMap.values().stream()
          .mapToInt(List::size).sum();
      physicalList = physicalMap.keySet().stream()
          .sorted()
          .map(physicalMap::get)
          .reduce(new ArrayList<>(), (acc, item) -> {
            Collections.sort(item);
            acc.addAll(item);
            return acc;
          });
    }

    @Override
    public boolean hasNext() {
      return ite < size;
    }

    @Override
    public E next() {
      if (ite >= size) {
        throw new NoSuchElementException();
      }
      return physicalList.get(ite++);
    }
  }

  @Override
  public List<String> getPhysicalObjectsInfo() {
    List<String> infoList = new ArrayList<>();
    for (Map.Entry<Track, List<E>> entry : physicalObjectMap.entrySet()) {
      for (E po : entry.getValue()) {
        infoList.add(po.toString());
      }
    }
    return infoList;
  }

  @Override
  public String getCentralObjectInfo() {
    return centralObject.toString();
  }

  /**
   * .
   * 由SocialNetwork实现
   * @return
   */
  @Override
  public List<String> getRelationsInfo() {
    return null;
  }
}
