package applications.socialnetworkcircle;

import apis.CircularOrbitApis;
import applications.socialnetworkcircle.gui.SocialNetworkPanel;
import applications.socialnetworkcircle.iostrategy.SocialNetworkBufferStrategy;
import applications.socialnetworkcircle.iostrategy.SocialNetworkIoStrategy;
import centralobject.CommonObject;
import circularorbit.CircularOrbit;
import circularorbit.ConcreteCircularOrbit;
import circularorbit.Drawable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import otherdirectory.Relation;
import otherdirectory.exception.CheckedException;
import otherdirectory.exception.DependencyException;
import otherdirectory.exception.GrammarException;
import otherdirectory.exception.SameLabelException;
import otherdirectory.exception.UncheckedException;
import track.Track;
import track.TrackFactory;

public class SocialNetworkCircle implements Drawable {

  private static Logger logger = Logger.getLogger(SocialNetworkCircle.class.getName());
  //是否进行额外的输入合法性检查
  public static boolean INPUTCHECKON = true;

  SocialNetworkPanel panel;
  private String filename;
  private CentralUser centralUser;
  private List<SocialTieEntity> socialTieEntityList = new ArrayList<>();
  //private List<Friend> friendList = new ArrayList<>();
  private Map<String, Friend> friendName2Ref = new HashMap<>();
  private List<Track> trackList = new ArrayList<>();
  private SocialNetworkCircularOrbit circularOrbit;
  private List<Relation<CentralUser, Friend>> relOfCentralObjs = new ArrayList<>();
  private Map<Friend, List<Relation<Friend, Friend>>> relOf2FriendObjs = new HashMap<>();
  private Map<Track, List<Friend>> physicalObjMap = new HashMap<>();
  private int maxTrackRadius = 0;

  public SocialNetworkCircle(String filename) {
    this.filename = filename;
  }

  private <E extends CommonObject> int getNameIndex(List<E> objs, String obName) {
    for (int i = 0; i < objs.size(); i++) {
      if (objs.get(i).getObName().equals(obName)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * .
   * 测试是否完全匹配
   *
   * @param line  匹配行
   * @param regex 正则表达式
   * @return 是否 完全 匹配
   */
  private boolean testMatch(String line, String regex) {
    Matcher matcher = Pattern.compile(regex).matcher(line);
    if (matcher.find()) {
      if (matcher.group(1).equals(line)) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * .
   * 获得读入错误的具体失败原因
   *
   * @param line 读入的行
   * @return 错误原因 如果没有出现代码中考虑的错误则直接返回错误信息“匹配失败：未知错误类型”
   */
  private String getReadFaultReason(String line) {
    String[] splitStrs = line.split("::=");
    String label = splitStrs[0].trim();
    String content = splitStrs[1].trim();

    switch (label) {
      case "CentralUser":
        splitStrs = content.split(",");
        if (splitStrs.length != 3) {
          return "匹配错误：非法CentralUser分量数目";
        }
        if (!testMatch(splitStrs[0].trim(), "(<[A-Za-z0-9]+)")) {
          return "匹配错误：非法CentralUser用户名";
        }
        if (!testMatch(splitStrs[1].trim(), "(\\\\d+)")) {
          return "匹配错误：非法CentralUser年龄";
        }
        if (!testMatch(splitStrs[2].trim(), "([MF])")) {
          return "匹配错误：非法CentralUser性别";
        }
        break;
      case "Friend":
        splitStrs = content.split(",");
        if (splitStrs.length != 3) {
          return "匹配错误：非法Friend分量数目";
        }
        if (!testMatch(splitStrs[0].trim(), "(<[A-Za-z0-9]+)")) {
          return "匹配错误：非法Friend名称";
        }
        if (!testMatch(splitStrs[1].trim(), "(\\d+)")) {
          return "匹配错误：非法Friend年龄";
        }
        if (!testMatch(splitStrs[2].trim(), "([MF]>)")) {
          return "匹配错误：非法Friend性别";
        }
        break;
      case "SocialTie":
        splitStrs = content.split(",");
        if (splitStrs.length != 3) {
          return "匹配错误：非法SocialTie分量数目";
        }
        if (!testMatch(splitStrs[0].trim(), "(<[A-Za-z0-9]+)")) {
          return "匹配错误：非法SociaTie用户名A";
        }
        if (!testMatch(splitStrs[1].trim(), "([A-Za-z0-9]+)")) {
          return "匹配错误：非法SocialTie用户名B";
        }
        if (!testMatch(splitStrs[2].trim(), "(0(?:\\.\\d{1,3})?|1(?:\\.0{1,3})?>)")) {
          return "匹配错误：非法SociaTie用户亲密度";
        }
        if (Double.parseDouble(splitStrs[2].trim()) == 0) {
          return "匹配错误：非法SocialTie用户亲密度";
        }
        break;
      default:
        return "匹配失败：标签错误";
    }
    return "匹配失败：未知类型错误";
  }


  /**
   * .
   * 提供接口
   * 处理一行输入 并构造轨道信息
   *
   * @param line 输入行
   * @throws CheckedException 处理错误抛出异常
   */
  public void matchInputLine(String line) throws CheckedException {

    String centralUserPattern =
        "^CentralUser\\s*::=\\s*<([A-Za-z0-9]+),\\s*(\\d+),\\s*([MF])>$";
    String friendPattern = "^Friend\\s*::=\\s*<([A-Za-z0-9]+),\\s*(\\d+),\\s*([MF])>$";
    //使用?:表示不捕获该括号内的元素（非捕获组），如果出现嵌套的括号，匹配规则是由左向右，由外向内
    String socialTiePattern = "^SocialTie\\s*::=\\s*<([A-Za-z0-9]+),"
        + "\\s*([A-Za-z0-9]+),"
        + "\\s*(0(?:\\.\\d{1,3})?|1(?:\\.0{1,3})?)>$";

    // 创建 Pattern 对象
    Matcher centralUserMatcher = Pattern.compile(centralUserPattern).matcher(line);
    Matcher friendMatcher = Pattern.compile(friendPattern).matcher(line);
    Matcher socialTieMatcher = Pattern.compile(socialTiePattern).matcher(line);

    if (centralUserMatcher.find()) {
      String userName = centralUserMatcher.group(1);
      int age = Integer.parseInt(centralUserMatcher.group(2));
      String gender = centralUserMatcher.group(3);
      centralUser = CentralUser.getInstance(userName, gender, age);
    } else if (friendMatcher.find()) {
      String userName = friendMatcher.group(1);
      int age = Integer.parseInt(friendMatcher.group(2));
      String gender = friendMatcher.group(3);

      if (INPUTCHECKON) {
        UncheckedException.assertTrue(!friendName2Ref.containsKey(userName), "存在同名的Friend");
      }

      Friend fr = Friend.getInstance(userName, gender, age);
      //friendList.add(fr);
      friendName2Ref.put(userName, fr);
    } else if (socialTieMatcher.find()) {
      if (INPUTCHECKON) {
        UncheckedException.assertTrue(centralUser != null, "输入数据顺序错误，centralUser为Null");
      }

      socialTieEntityList.add(new SocialTieEntity(
          socialTieMatcher.group(1),
          socialTieMatcher.group(2),
          Double.parseDouble(socialTieMatcher.group(3))));
    } else {
      GrammarException.assertTrue(false, getReadFaultReason(line));
    }
  }

  public void processInputData() throws CheckedException {
    Map<String, Set<String>> friendEdgePool = new HashMap<>();

    for (SocialTieEntity socialTie : socialTieEntityList) {
      String userNameA = socialTie.getUserNameA();
      String userNameB = socialTie.getUserNameB();

      if (INPUTCHECKON) {
        if (!friendEdgePool.containsKey(userNameA)) {
          friendEdgePool.put(userNameA, new HashSet<>());
        }
        if (!friendEdgePool.containsKey(userNameB)) {
          friendEdgePool.put(userNameB, new HashSet<>());
        }
        SameLabelException.assertTrue(
            !friendEdgePool.get(userNameA).contains(userNameB),
            "两个用户之间不能存在两条边");
        friendEdgePool.get(userNameA).add(userNameB);
        friendEdgePool.get(userNameB).add(userNameA);

        SameLabelException.assertTrue(!userNameA.equals(userNameB), "出现一条自己连向自己的边");
      }

      double socialDensity = socialTie.getSocianDensity();

      if (INPUTCHECKON) {
        DependencyException.assertTrue(centralUser.getObName().equals(userNameA)
            || friendName2Ref.containsKey(userNameA), "没有声明" + userNameA);
        DependencyException.assertTrue(centralUser.getObName().equals(userNameB)
            || friendName2Ref.containsKey(userNameB), "没有声明" + userNameB);
      }

      if (centralUser.getObName().equals(userNameA)) {
        Friend friend = friendName2Ref.get(userNameB);
        relOfCentralObjs.add(Relation.getCentralUserTie(centralUser, friend, socialDensity));
        continue;
      }

      if (centralUser.getObName().equals(userNameB)) {
        Friend friend = friendName2Ref.get(userNameA);
        relOfCentralObjs.add(Relation.getCentralUserTie(centralUser, friend, socialDensity));
        continue;
      }

      Friend friendA = friendName2Ref.get(userNameA);
      Friend friendB = friendName2Ref.get(userNameB);
      if (!relOf2FriendObjs.containsKey(friendA)) {
        relOf2FriendObjs.put(friendA, new ArrayList<>());
      }
      if (!relOf2FriendObjs.containsKey(friendB)) {
        relOf2FriendObjs.put(friendB, new ArrayList<>());
      }
      relOf2FriendObjs.get(friendA).add(Relation.getFriendsTie(friendA, friendB, socialDensity));
      relOf2FriendObjs.get(friendB).add(Relation.getFriendsTie(friendB, friendA, socialDensity));
    }

  }

  /**
   * .
   * 读入文件配置
   * 利用正则表达式进行解析
   */
  public void loadConfig() throws CheckedException {
    new SocialNetworkBufferStrategy().read(this, filename);
  }

  /**
   * .
   * IO
   * 获得中心用户信息
   * @return 中心用户信息
   */
  public String getCentralUserInfo() {
    return "CentralUser ::= " + circularOrbit.getCentralObjectInfo();
  }

  /**
   * .
   * IO
   * 获得所有社交关系信息
   * @return
   */
  public List<String> getSocialTieInfo() {
    return circularOrbit.getRelationsInfo()
        .stream()
        .map(s ->
            "SocialTie ::= " + s)
        .collect(Collectors.toList());
  }

  /**
   * .
   * IO
   * 获得所有轨道用户信息
   * @return 所有轨道用户信息
   */
  public List<String> getFriendInfo() {
    return circularOrbit.getPhysicalObjectsInfo()
        .stream()
        .map(s ->
            "Friend ::= " + s)
        .collect(Collectors.toList());
  }

  /**
   * .
   * 输出轨道系统信息到新文件
   * @param filepath 文件地址
   * @param strategy 输出策略
   */
  public void outputOrbitInfo(String filepath, SocialNetworkIoStrategy strategy) {
    strategy.write(this,filepath);
  }

  /**
   * .
   * 对外接口
   * 根据策略读入轨道系统信息到构造类 并建立轨道系统
   */
  public void inputOrbitInfo(String filepath, SocialNetworkIoStrategy strategy)
      throws CheckedException {
    strategy.read(this, filepath);
    initCircularOrbit();
  }


  private Map<Friend, Integer> bfs2GetPhysicalMap(List<Friend> sources) {
    Queue<Friend> queue = new LinkedBlockingQueue<>();

    Map<Friend, Integer> distantMap = new HashMap<>();
    for (Friend st : sources) {
      if (!queue.offer(st)) {
        UncheckedException.assertTrue(false, "queue.offer失败");
      }
      distantMap.put(st, 1);
    }

    while (!queue.isEmpty()) {
      Friend topPerson = queue.poll();
      int nowDis = distantMap.get(topPerson);
      List<Friend> neighborList = relOf2FriendObjs.getOrDefault(topPerson, new ArrayList<>())
          .stream()
          .map(Relation::getObjv).collect(Collectors.toList());
      for (Friend ps : neighborList) {
        if (!distantMap.containsKey(ps)) {
          distantMap.put(ps, nowDis + 1);
          if (nowDis == maxTrackRadius) {
            trackList.add(TrackFactory.getTrackInstance((double)++maxTrackRadius));
            physicalObjMap.put(trackList.get(maxTrackRadius - 1), new ArrayList<>());
          }
          physicalObjMap.get(trackList.get(nowDis)).add(ps);
          if (!queue.offer(ps)) {
            UncheckedException.assertTrue(false, "queue.offer失败");
          }
        }
      }
    }
    return distantMap;
  }

  //void bfs2GetFlag(Friend source, Map<Friend, Integer> flag, int flagIndex) {
  //  Queue<Friend> queue = new LinkedBlockingQueue<>();
  //
  //  if (!queue.offer(source)) {
  //    UncheckedException.assertTrue(false, "queue.offer失败");
  //  }
  //  flag.put(source, flagIndex);
  //
  //  while (!queue.isEmpty()) {
  //    Friend topPerson = queue.poll();
  //    List<Friend> neighborList = relOf2FriendObjs.getOrDefault(topPerson, new ArrayList<>())
  //        .stream()
  //        .map(Relation::getObjv).collect(Collectors.toList());
  //    for (Friend ps : neighborList) {
  //      if (!flag.containsKey(ps)) {
  //        flag.put(ps, flagIndex);
  //        if (!queue.offer(ps)) {
  //          UncheckedException.assertTrue(false, "queue.offer失败");
  //        }
  //      }
  //    }
  //  }
  //}

  private void getPhysicalMap() {
    //        将所有其他不与centralUser联通的节点子联通图取代表元与中心点连接，紧密度为0
    //Map<Friend, Integer> flag = new HashMap<>();
    //int flagIndex = 0;
    //
    //for (Relation<CentralUser, Friend> rel : relOfCentralObjs) {
    //  Friend st = rel.getObjv();
    //  if (!relOf2FriendObjs.containsKey(st)) {
    //    relOf2FriendObjs.put(st, new ArrayList<>());
    //  }
    //  bfs2GetFlag(st, flag, flagIndex);
    //}
    //friendList.removeIf(x -> !flag.containsKey(x));

    trackList.add(TrackFactory.getTrackInstance(1.0));
    ++maxTrackRadius;

    List<Friend> firstTrackFriends = new ArrayList<>();
    for (Relation<CentralUser, Friend> relOfCentralObj : relOfCentralObjs) {
      Friend pv = relOfCentralObj.getObjv();
      if (!relOf2FriendObjs.containsKey(pv)) {
        relOf2FriendObjs.put(pv, new ArrayList<>());
      }
      firstTrackFriends.add(pv);
    }
    physicalObjMap.put(trackList.get(0), firstTrackFriends);
    Map<Friend, Integer> distantMap = bfs2GetPhysicalMap(firstTrackFriends);

    relOf2FriendObjs.keySet().stream()
        .filter(x -> !distantMap.containsKey(x))
        .collect(Collectors.toList())
        .forEach(relOf2FriendObjs::remove);
  }

  /**
   * .
   * 根据文件中的配置
   * 初始化轨道系统
   */
  public void initCircularOrbit() {
    SocialNetworkCircularOrbitBuilder builder = new SocialNetworkCircularOrbitBuilder();
    builder.createConcreteCircularOrbit(null);

    getPhysicalMap();

    //builder.buildTracks(trackList);
    builder.buildObjects(centralUser, physicalObjMap);
    builder.buildRelation(relOfCentralObjs, relOf2FriendObjs);

    ConcreteCircularOrbit<CentralUser, Friend> tmp = builder.getConcreteCircularOrbit();
    if (tmp instanceof SocialNetworkCircularOrbit) {
      circularOrbit = (SocialNetworkCircularOrbit) tmp;
    }
  }

  public SocialNetworkCircularOrbit getCircularOrbit() {
    return circularOrbit;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////
  private Friend getPhysicalObjByObName(Predicate<Friend> predicate,
                                        ConcreteCircularOrbit circularOrbit) {
    Iterator<Friend> ite = circularOrbit.iterator();
    List<Friend> physicalObjs = new ArrayList<>();
    while (ite.hasNext()) {
      Friend tmp = ite.next();
      physicalObjs.add(tmp);
    }
    Collections.shuffle(physicalObjs);
    try {
      return physicalObjs.stream().filter(predicate)
          .collect(Collectors.toList()).get(0);
    } catch (ArrayIndexOutOfBoundsException e) {
      UncheckedException.assertTrue(false, "没有该Friend");
    }
    return null;
  }

  /**
   * .
   * 向轨道系统co中添加轨道tk
   */
  public void addTrack(double addRadius) {
    circularOrbit.addTrack(TrackFactory.getTrackInstance(addRadius));
    reLoadAll();
  }

  /**
   * .
   * 在轨道系统co中移除轨道tk
   */
  public void removeTrack(double rmRadius) {
    circularOrbit.removeTrack(TrackFactory.getTrackInstance(rmRadius));
    reLoadAll();
  }

  /**
   * .
   * 向co的tk轨道添加运动员Friend
   */
  public void addPhysicalObject(Friend friend, double tkRadius) {
    circularOrbit.addPhysicalObj2Track(friend, TrackFactory.getTrackInstance(tkRadius));
    reLoadAll();
  }

  /**
   * .
   * 在轨道系统co中移除运动员Friend
   */
  public void removePhysicalObject(String rmObName) {
    Friend friend = getPhysicalObjByObName(x -> x.getObName().equals(rmObName),
        circularOrbit);
    circularOrbit.removePhysicalObject(friend);
    reLoadAll();
  }

  @Override
  public void initialize() throws CheckedException {
    loadConfig();
    initCircularOrbit();
  }

  @Override
  public void draw(JFrame frame) {
    panel = new SocialNetworkPanel(this);
    frame.getContentPane().add(panel);
    circularOrbit.visualize(panel.getDrawPanel());
    reLoadAll();
  }

  /**
   * .
   * 重新计算GUI面板上的轨道系统信息
   */
  public void reLoadAll() {
    if (panel == null) {
      return;
    }
    CircularOrbitApis<CentralUser, Friend> apis = new CircularOrbitApis<>();
    CircularOrbit<CentralUser, Friend> nowCircularOrbit = circularOrbit;
    boolean state = apis.checkOrbitAvailable(nowCircularOrbit);
    double entropy = apis.getObjectDistributionEntropy(nowCircularOrbit);
    List<String> trackList = nowCircularOrbit.getTrackRadiusList();
    Iterator<Friend> ite = ((SocialNetworkCircularOrbit) nowCircularOrbit).iterator();
    List<String> physicalObjs = new ArrayList<>();
    List<String> relObjects = new ArrayList<>();
    while (ite.hasNext()) {
      String obName = ite.next().getObName();
      physicalObjs.add(obName);
      relObjects.add(obName);
    }
    relObjects.add(circularOrbit.getCentralUser().getObName());

    panel.reloadGameInfo(state, entropy, trackList,
        physicalObjs, relObjects,
        circularOrbit.getFirstTrackFriends());
    nowCircularOrbit.visualize(panel.getDrawPanel());
  }

  public double getInfoDiffusion(String obName) {
    Friend friend = getPhysicalObjByObName(x -> x.getObName().equals(obName), circularOrbit);
    return circularOrbit.getInfoDiffusion().get(friend);
  }
  ////////////////////////////////////////////////////////////////////////////////////////

  /**
   * .
   * 添加关系
   *
   * @param obNameU 边的起始点
   * @param obNameV 边的终点
   * @param density 边的权重
   */
  public void addRelation(String obNameU, String obNameV, double density) {
    CentralUser centralUser;
    Friend friendU;
    if (circularOrbit.isCenterUser(obNameU)) {
      centralUser = circularOrbit.getCentralUser();
      friendU = getPhysicalObjByObName(x -> x.getObName().equals(obNameV), circularOrbit);
      circularOrbit.extendRelationOfCentralObj2TrackObj(centralUser, friendU, density);
      reLoadAll();
      return;
    }

    if (circularOrbit.isCenterUser(obNameV)) {
      centralUser = circularOrbit.getCentralUser();
      friendU = getPhysicalObjByObName(x -> x.getObName().equals(obNameU), circularOrbit);
      circularOrbit.extendRelationOfCentralObj2TrackObj(centralUser, friendU, density);
      reLoadAll();
      return;
    }

    Friend friendV;
    friendU = getPhysicalObjByObName(x -> x.getObName().equals(obNameU), circularOrbit);
    friendV = getPhysicalObjByObName(x -> x.getObName().equals(obNameV), circularOrbit);
    circularOrbit.extendRelationOf2TrackObs(friendU, friendV, density);
    circularOrbit.extendRelationOf2TrackObs(friendV, friendU, density);

    reLoadAll();
  }

  /**
   * .
   * 移除关系
   *
   * @param obNameU 边的起点
   * @param obNameV 边的终点
   */
  public void removeRelation(String obNameU, String obNameV) {
    CentralUser centralUser;
    Friend friendU;
    if (circularOrbit.isCenterUser(obNameU)) {
      centralUser = circularOrbit.getCentralUser();
      friendU = getPhysicalObjByObName(x -> x.getObName().equals(obNameV), circularOrbit);
      circularOrbit.curtailRelationOfCentralObjs2TraObj(centralUser, friendU);
      reLoadAll();
      return;
    }

    if (circularOrbit.isCenterUser(obNameV)) {
      centralUser = circularOrbit.getCentralUser();
      friendU = getPhysicalObjByObName(x -> x.getObName().equals(obNameU), circularOrbit);
      circularOrbit.curtailRelationOfCentralObjs2TraObj(centralUser, friendU);
      reLoadAll();
      return;
    }

    Friend friendV;
    friendU = getPhysicalObjByObName(x -> x.getObName().equals(obNameU), circularOrbit);
    friendV = getPhysicalObjByObName(x -> x.getObName().equals(obNameV), circularOrbit);
    circularOrbit.curtailRelationOf2TrackObs(friendU, friendV);
    circularOrbit.curtailRelationOf2TrackObs(friendV, friendU);

    reLoadAll();
  }

  /**
   * .
   * 获得逻辑距离
   *
   * @param obNameU 起点
   * @param obNameV 终点
   * @return 逻辑距离
   */
  public int getLogicalDistance(String obNameU, String obNameV) {
    Friend fr1 = getPhysicalObjByObName(x -> x.getObName().equals(obNameU), circularOrbit);
    Friend fr2 = getPhysicalObjByObName(x -> x.getObName().equals(obNameV), circularOrbit);
    return circularOrbit.getLogicalDistance(fr1, fr2);
  }

  /**
   * .
   * 获得用户周围所有的Friend
   *
   * @param obName 用户名称
   * @return 所有相邻的用户名称
   */
  public List<String> getSurroudings(String obName) {
    //对于Item事件监听会导致一个Race问题，此时可能申请已经不存在的物体 所以需要捕获Exception 处理
    try {
      if (circularOrbit.isCenterUser(obName)) {
        return circularOrbit.getFirstTrackFriends();
      }
      Friend fr = getPhysicalObjByObName(x -> x.getObName().equals(obName), circularOrbit);
      return circularOrbit.getSurroundings(fr);
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  private static class SocialTieEntity {
    String userNameA;
    String userNameB;
    double socianDensity;

    public SocialTieEntity(String userNameA, String userNameB, double socianDensity) {
      this.userNameA = userNameA;
      this.userNameB = userNameB;
      this.socianDensity = socianDensity;
    }

    public String getUserNameA() {
      return userNameA;
    }

    public String getUserNameB() {
      return userNameB;
    }

    public double getSocianDensity() {
      return socianDensity;
    }
  }
}
