package applications.socialnetworkcircle;

import circularorbit.ConcreteCircularOrbit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import otherdirectory.GraphicsPainter;
import otherdirectory.Position;
import otherdirectory.Relation;
import otherdirectory.exception.UncheckedException;
import track.Track;
import track.TrackFactory;

public class SocialNetworkCircularOrbit extends ConcreteCircularOrbit<CentralUser, Friend> {
  //AF（*） = SocialNetwork轨道系统
  //
  //RI: 如果某个人出现在第 n条轨道上，那么他和中心点的人之间最短路径是 n。
  //例如下图 a用户，从中心点到它的最短 路径是通过 b（而非 c-d-e-a），那么 a的位置应该是轨道 2（d所在的 轨道）。
  //如果某个人与中心点用户不连通，则不应出现在系统系统中。
  //
  //Rep Explosure：没有暴露方法

  private static Logger logger = Logger.getLogger(SocialNetworkCircularOrbit.class.getName());

  public SocialNetworkCircularOrbit(
      CentralUser co,
      Map<Track, List<Friend>> pm,
      List<Relation<CentralUser, Friend>> rel1,
      Map<Friend, List<Relation<Friend, Friend>>> rel2) {
    super(co, pm, rel1, rel2);
  }

  @Override
  protected void checkRep() {
    super.checkRep();
  }

  @Override
  public boolean checkOribitAvailable() {
    List<Friend> firstTrackFriends = relOfCobj2TraObj.stream()
        .map(x -> x.getObjv()).collect(Collectors.toList());
    Map<Friend, Integer> distantMap = getSingleSourceDistances(firstTrackFriends);
    for (List<Friend> friendList : physicalObjectMap.values()) {
      for (Friend friend : friendList) {
        if (!distantMap.containsKey(friend)) {
          return false;
        }
      }
    }
    return true;
  }

  private Map<Friend, Double> bfs2SingleSourceDiffustion(Friend source, double stDensity) {

    Queue<Friend> queue = new LinkedBlockingQueue<>();
    Map<Friend, Double> diffusionMap = new HashMap<>();
    if (!queue.offer(source)) {
      UncheckedException.assertTrue(false, "queue.offer失败");
    }
    diffusionMap.put(source, stDensity);

    while (!queue.isEmpty()) {
      Friend topPerson = queue.poll();
      double nowDiffusiton = diffusionMap.get(topPerson);
      List<Relation<Friend, Friend>> neighborList =
          relOf2TraObjs.getOrDefault(topPerson, new ArrayList<>());
      for (Relation<Friend, Friend> rel : neighborList) {
        if (!diffusionMap.containsKey(rel.getObjv())) {
          diffusionMap.put(rel.getObjv(), nowDiffusiton * rel.getWeight());
          if (!queue.offer(rel.getObjv())) {
            UncheckedException.assertTrue(false, "queue.offer失败");
          }
        }
      }
    }
    return diffusionMap;
  }

  /**
   * .
   * 获得轨道系统中第一层轨道物体的扩散度
   * 定义扩散度：这里我们定义第一层轨道上的物体为V，其他物体为U，记v->u路径上紧密度的乘积为val(v,u)，则v的扩散度为其所有能到达的物体u’的val(v,u’)之和
   *
   * @return 第一层轨道物体到对应扩散度的映射
   */
  public Map<Friend, Double> getInfoDiffusion() {
    Map<Friend, Double> diffusion = new HashMap<>();

    for (Relation<CentralUser, Friend> rel : relOfCobj2TraObj) {
      Friend st = rel.getObjv();
      double stDensity = rel.getWeight();
      diffusion.put(st, bfs2SingleSourceDiffustion(st, stDensity).values().stream()
          .reduce((acc, item) -> {
            acc += item;
            return acc;
          }).get());
    }
    return diffusion;
  }

  @Override
  public int getLogicalDistance(Friend e1, Friend e2) {
    if (e1 == e2) {
      return 1;
    }
    return super.getLogicalDistance(e1, e2);
  }

  private void bfs2GetPhysicalMap(List<Friend> sources) {
    physicalObjectMap = new HashMap<>();
    Queue<Friend> queue = new LinkedBlockingQueue<>();
    List<Track> trackList = new ArrayList<>();
    trackList.add(TrackFactory.getTrackInstance(1.0));
    Map<Friend, Integer> distantMap = new HashMap<>();
    physicalObjectMap.put(trackList.get(0), new ArrayList<>());
    for (Friend st : sources) {
      if (!queue.offer(st)) {
        UncheckedException.assertTrue(false, "queue.offer失败");
      }
      distantMap.put(st, 1);
      physicalObjectMap.get(trackList.get(0)).add(st);
    }
    int maxTrackRadius = 1;

    while (!queue.isEmpty()) {
      Friend topPerson = queue.poll();
      int nowDis = distantMap.get(topPerson);
      List<Relation<Friend,Friend>> neighborList =
          relOf2TraObjs.getOrDefault(topPerson, new ArrayList<>());
      for (Relation<Friend,Friend> rel : neighborList) {
        Friend ps = rel.getObjv();
        if (!distantMap.containsKey(ps)) {
          distantMap.put(ps, nowDis + 1);
          if (nowDis == maxTrackRadius) {
            trackList.add(TrackFactory.getTrackInstance((double)++maxTrackRadius));
            physicalObjectMap.put(trackList.get(maxTrackRadius - 1), new ArrayList<>());
          }
          physicalObjectMap.get(trackList.get(nowDis)).add(ps);
          if (!queue.offer(ps)) {
            UncheckedException.assertTrue(false, "queue.offer失败");
          }
        }
      }
    }
  }

  /**
   * .
   * 重新调整图结构
   * 包括 删除不连通的边，调整轨道物体所处的轨道
   */
  private void adjustFriendLocation() {
    List<Friend> firstTrackFriends = relOfCobj2TraObj.stream()
        .map(x -> x.getObjv()).collect(Collectors.toList());
    bfs2GetPhysicalMap(firstTrackFriends);
    //List<Friend> rmFriendList = new ArrayList<>();
    //for (Friend friend : rmFriendList) {
    //  relOf2TraObjs.remove(friend);
    //}

    checkRep();
    UncheckedException.assertTrue(checkOribitAvailable(), "不满足轨道系统合法的循环不变性");
  }

  /**
   * .
   * 删除一个轨道物体
   * 在该轨道系统中，删除物体之后需要重新调整整个图结构
   *
   * @param oldObj 需要在轨道系统中进行删除的轨道物体，轨道系统中必须已经包含该轨道物体
   */
  @Override
  public void removePhysicalObject(Friend oldObj) {
    super.removePhysicalObject(oldObj);
    adjustFriendLocation();
  }

  /**
   * .
   * 扩展一条从中心点到轨道物体之间的关系
   * （post-pre-condition与ConcreteCircularOrbit相同）
   *
   * @param co     中心物体
   * @param po     轨道物体
   * @param weight 关系权重
   * @return 是否扩展成功
   */
  public boolean extendRelationOfCentralObj2TrackObj(CentralUser co, Friend po, double weight) {
    boolean ans = super.addRelationOfCentralObj2TrackObj(co, po, weight);
    adjustFriendLocation();
    return ans;
  }

  /**
   * .
   * 减少一条由中心物体到轨道物体之间的关系
   * （post-pre-condition与ConcreteCircularOrbit相同）
   *
   * @param co 中心物体
   * @param po 轨道物体
   * @return 是否减少成功
   */
  public boolean curtailRelationOfCentralObjs2TraObj(CentralUser co, Friend po) {
    boolean ans = super.removeRelationOfCentralObjs2TraObj(co, po);
    adjustFriendLocation();
    return ans;
  }

  /**
   * .
   * 添加一条轨道物体之间的关系
   * （post-pre-condition与ConcreteCircularOrbit相同）
   *
   * @param po1    轨道物体1
   * @param po2    轨道物体2
   * @param weight 关系权重
   * @return 是否添加成功
   */
  public boolean extendRelationOf2TrackObs(Friend po1, Friend po2, double weight) {
    boolean ans = super.addRelationOf2TrackObs(po1, po2, weight);
    adjustFriendLocation();
    return ans;
  }

  /**
   * .
   * 减少一条轨道物体之间的关系
   * （post-pre-condition与ConcreteCircularOrbit相同）
   *
   * @param po1 轨道物体1
   * @param po2 轨道物体2
   * @return 是否减少成功
   */
  public boolean curtailRelationOf2TrackObs(Friend po1, Friend po2) {
    boolean ans = super.removeRelationOf2TrackObs(po1, po2);
    adjustFriendLocation();
    return ans;
  }

  @Override
  public JPanel visualizeContentPanel() {
    logger.info("将SocialNetworkOrbit轨道系统可视化");
    return new JPanel() {
      //序列号（可省略）
      private static final long serialVersionUID = 1L;

      // 重写paint方法
      @Override
      public void paint(Graphics graphics) {
        final int pnLength = 800;
        final int padding = 80;
        int trasz = physicalObjectMap.size();
        int[] trackRadius = new int[trasz];
        for (int i = 0; i < trasz; i++) {
          trackRadius[i] = (pnLength - padding) / (2 * trasz) * (i + 1);
        }

        // 必须先调用父类的paint方法
        super.paint(graphics);
        // 用画笔Graphics，在画板JPanel上画一个小人
        int centerX = pnLength / 2;
        int centerY = pnLength / 2;
        int centerObjRadius = 20;

        GraphicsPainter ctpainter = new GraphicsPainter();
        ctpainter.setPx(centerX);
        ctpainter.setPy(centerY);
        ctpainter.setRadius(centerObjRadius);
        ctpainter.setOvalColor(Color.red);
        centralObject.drawGraphics(graphics, ctpainter);

        List<Track> trackList = physicalObjectMap.keySet().stream()
            .sorted().collect(Collectors.toList());
        for (int idx = 1; idx <= trasz; idx++) {
          Track track = trackList.get(idx - 1);
          int tr = trackRadius[idx - 1];
          int width = 2 * tr;
          int height = 2 * tr;
          graphics.drawOval(centerX - tr, centerY - tr,
              width, height);

          List<Friend> friends = physicalObjectMap.get(track);
          int sz = physicalObjectMap.get(track).size();
          for (int i = 0; i < sz; i++) {
            GraphicsPainter painter = new GraphicsPainter();
            double theta = i * (2 * Math.PI / (sz));
            int cx = (int) (tr * Math.cos(theta)) + centerX;
            int cy = (int) (tr * Math.sin(theta)) + centerY;
            painter.setPx(cx);
            painter.setPy(cy);
            painter.setRadius(5);
            painter.setPtFont(new Font("TimesRoman", Font.PLAIN, 10));
            friends.get(i).drawGraphics(graphics, painter);
          }
        }
        for (Relation<CentralUser, Friend> rel : relOfCobj2TraObj) {
          Position pos1 = rel.getObju().getPos();
          Position pos2 = rel.getObjv().getPos();
          int x1 = (int) pos1.getPx();
          int x2 = (int) pos2.getPx();
          int y1 = (int) pos1.getPy();
          int y2 = (int) pos2.getPy();
          graphics.setColor(Color.red);
          graphics.drawLine(x1, y1, x2, y2);
          graphics.drawString(Double.toString(rel.getWeight()), (x1 + x2) / 2, (y1 + y2) / 2);
          graphics.setColor(Color.black);
        }
        for (Map.Entry<Friend, List<Relation<Friend, Friend>>> entry : relOf2TraObjs.entrySet()) {
          for (Relation<Friend, Friend> rel : entry.getValue()) {
            Position pos1 = rel.getObju().getPos();
            Position pos2 = rel.getObjv().getPos();
            int x1 = (int) pos1.getPx();
            int x2 = (int) pos2.getPx();
            int y1 = (int) pos1.getPy();
            int y2 = (int) pos2.getPy();
            graphics.drawLine(x1, y1, x2, y2);
            graphics.drawString(Double.toString(rel.getWeight()), (x1 + x2) / 2, (y1 + y2) / 2);
          }
        }
      }
    };
  }

  @Override
  public void visualize(JPanel panel) {
    JPanel contentPanel = visualizeContentPanel();
    panel.removeAll();
    contentPanel.setPreferredSize(new Dimension(800, 800));
    panel.add(contentPanel);
    panel.validate();
    panel.repaint();
  }

  public List<String> getFirstTrackFriends() {
    return relOfCobj2TraObj.stream()
        .map(rel -> rel.getObjv().getObName()).collect(Collectors.toList());
  }

  public boolean isCenterUser(String obName) {
    return this.centralObject.getObName().equals(obName);
  }

  public CentralUser getCentralUser() {
    return this.centralObject;
  }

  public List<String> getSurroundings(Friend friend) {
    return relOf2TraObjs.get(friend).stream()
        .map(rel -> rel.getObjv().getObName()).collect(Collectors.toList());
  }

  @Override
  public List<String> getRelationsInfo() {
    List<String> relationInfo = new ArrayList<>();

    for (Relation<CentralUser, Friend> rel : relOfCobj2TraObj) {
      relationInfo.add(String.format("<%s, %s, %.3f>",
          rel.getObju().getObName(), rel.getObjv().getObName(), rel.getWeight()));
    }
    Map<Friend, Set<Friend>> flagMap = new HashMap<>();
    for (Map.Entry<Friend, List<Relation<Friend, Friend>>> entry : relOf2TraObjs.entrySet()) {
      Friend pu = entry.getKey();
      for (Relation<Friend, Friend> rel : entry.getValue()) {
        Friend pv = rel.getObjv();
        if (!flagMap.containsKey(pu)) {
          flagMap.put(pu, new HashSet<>());
        }
        if (!flagMap.containsKey(pv)) {
          flagMap.put(pv, new HashSet<>());
        }
        if (!flagMap.get(pu).contains(pv) && !flagMap.get(pv).contains(pu)) {
          relationInfo.add(String.format("<%s, %s, %.3f>",
              pu.getObName(), pv.getObName(), rel.getWeight()));
          flagMap.get(pu).add(pv);
        }
      }
    }
    return relationInfo;
  }
}
