package circularorbit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import otherdirectory.Relation;
import track.Track;

public abstract class ConcreteCircularOrbitBuilder<L, E> {
  protected ConcreteCircularOrbit<L, E> concreteCircularOrbit;
  protected String sysName;
  protected L centralUser;
  protected Map<Track, List<E>> physicalObjMap = new HashMap<>();
  protected List<Relation<L, E>> relOfCentralObjs = new ArrayList<>();
  protected Map<E, List<Relation<E, E>>> relfOf2TraObjs = new HashMap<>();

  public abstract ConcreteCircularOrbit<L, E> getConcreteCircularOrbit();

  /**
   * .
   * 创建对象
   */
  public void createConcreteCircularOrbit(String sysName) {
    this.sysName = sysName;
  }

  /**
   * .
   * 构造步骤：向轨道系统中添加中心物体和轨道物体
   */
  public void buildObjects(L centralObjects, Map<Track, List<E>> physicalObjMap) {
    this.centralUser = centralObjects;
    this.physicalObjMap = new HashMap<>();
    for (Map.Entry<Track, List<E>> entry : physicalObjMap.entrySet()) {
      Track tk = entry.getKey();
      this.physicalObjMap.put(tk, new ArrayList<>());
      entry.getValue().forEach(x -> this.physicalObjMap.get(tk).add(x));
    }
  }

  /**
   * .
   * 添加轨道关系
   * @param relOfCentralObjs 中心物体与第一层轨道物体的关系
   * @param friendMap 轨道物体之间的关系
   */
  public void buildRelation(
      List<Relation<L, E>> relOfCentralObjs,
      Map<E, List<Relation<E, E>>> friendMap) {
    this.relOfCentralObjs = new ArrayList<>();
    relOfCentralObjs.forEach(x -> this.relOfCentralObjs.add(x));
    this.relfOf2TraObjs = new HashMap<>();
    friendMap.forEach((x, y) -> {
      this.relfOf2TraObjs.put(x, new ArrayList<>());
      y.forEach(rel -> this.relfOf2TraObjs.get(x).add(rel));
    });
  }
}