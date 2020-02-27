package applications.atomstructure;

import apis.CircularOrbitApis;
import applications.atomstructure.gui.AtomStructurePanel;
import applications.atomstructure.memento.ElectronTransitCareTaker;
import circularorbit.CircularOrbit;
import circularorbit.ConcreteCircularOrbit;
import circularorbit.Drawable;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import otherdirectory.exception.CheckedException;
import otherdirectory.exception.DependencyException;
import otherdirectory.exception.GrammarException;
import track.Track;
import track.TrackFactory;

public class AtomStructure implements Drawable {

  private static Logger logger = Logger.getLogger(AtomStructure.class.getName());
  //    读入的文件配置
  String elementName;
  int numofTracks;
  List<Integer> trackIdList = new ArrayList<>();
  List<Integer> electronNumList = new ArrayList<>();
  Map<Track, List<Electron>> physicalObjMap;
  //    生成的构造配置
  List<Track> trackList = new ArrayList<>();
  AtomCircularOrbit atomCircularOrbit;
  AtomStructurePanel panel;
  ElectronTransitCareTaker careTaker = new ElectronTransitCareTaker();
  private String filename;


  public AtomStructure(String filename) {
    this.filename = filename;
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
    String label;
    String content;
    try {
      label = splitStrs[0].trim();
      content = splitStrs[1].trim();
    } catch (IndexOutOfBoundsException e) {
      return "匹配错误：基本格式 *::=* 错误";
    }

    switch (label) {
      case "ElementName":
        if (!testMatch(content, "([A-Z]{1}[a-z]{0,1})")) {
          return "匹配错误：原子名称错误";
        }
        break;
      case "NumberOfElectron":
        if (!testMatch(content, "((?:\\d+\\/\\d+;)*(?:\\d+\\/\\d+))")) {
          return "匹配错误：非法轨道电子数目形式";
        }
        break;
      case "NumberOfTracks":
        if (!testMatch(content, "(\\\\d+)")) {
          return "匹配错误：非法轨道数目";
        }
        break;
      default:
        return "匹配失败：标签错误";
    }
    return "匹配失败：未知类型错误";
  }

  /**
   * .
   * 读入文件配置
   * 利用正则表达式进行解析
   */
  public void loadConfig() throws CheckedException {
    BufferedReader reader = null;
    try {
      //reader = new BufferedReader(new FileReader( new File(filename)));
      InputStreamReader isr = new InputStreamReader(new FileInputStream(filename), "UTF-8");
      reader = new BufferedReader(isr);
      String line = reader.readLine();
      String[] splitStrs;
      int realNumOfTracks = 0;

      while (line != null) {
        if (line.length() == 0) {
          line = reader.readLine();
          continue;
        }
        line = line.trim();
        String elementNamePattern = "^ElementName\\s*::=\\s*([A-Z]{1}[a-z]{0,1})$";
        String tracksPattern = "^NumberOfTracks\\s*::=\\s*(\\d+)$";
        String electronPattern = "^NumberOfElectron\\s*::=\\s*((?:\\d+\\/\\d+;)*(?:\\d+\\/\\d+))$";

        // 创建 Pattern 对象
        Matcher electronMatcher = Pattern.compile(electronPattern).matcher(line);
        Matcher elementNameMatcher = Pattern.compile(elementNamePattern).matcher(line);
        Matcher tracksMatcher = Pattern.compile(tracksPattern).matcher(line);

        if (electronMatcher.find()) {
          splitStrs = electronMatcher.group(1).trim().split(";");
          realNumOfTracks = splitStrs.length;
          for (int i = 0; i < splitStrs.length; i++) {
            int trackId = Integer.parseInt(splitStrs[i].split("/")[0]);
            int electronNum = Integer.parseInt(splitStrs[i].split("/")[1]);
            trackIdList.add(trackId);
            electronNumList.add(electronNum);
          }
        } else if (elementNameMatcher.find()) {
          elementName = elementNameMatcher.group(1).trim();
        } else if (tracksMatcher.find()) {
          numofTracks = Integer.parseInt(tracksMatcher.group(1).trim());
        } else {
          GrammarException.assertTrue(false, getReadFaultReason(line));
        }

        line = reader.readLine();
      }
      DependencyException.assertTrue(realNumOfTracks == numofTracks, "声明轨道数目和真实轨道数目不符");

    } catch (IOException e) {
      e.printStackTrace();
    } catch (CheckedException e) {
      logger.error(e);
      throw e;
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * .
   * 根据文件中的配置
   * 初始化轨道系统
   */
  public void initCircularOrbit() {
    AtomCircularOrbitBuilder builder = new AtomCircularOrbitBuilder();
    builder.createConcreteCircularOrbit(elementName);

    for (int i = 0; i < numofTracks; i++) {
      trackList.add(TrackFactory.getTrackInstance((double) (i + 1)));
    }
    //builder.buildTracks(trackList);
    physicalObjMap = new HashMap<>();
    for (int i = 0; i < trackIdList.size(); i++) {
      Track track = trackList.get(trackIdList.get(i) - 1);
      int eleNum = electronNumList.get(i);
      if (!physicalObjMap.containsKey(track)) {
        physicalObjMap.put(track, new ArrayList<>());
      }
      for (int j = 0; j < eleNum; j++) {
        physicalObjMap.get(track).add(ElectronFactory.getInstance(track));
      }
    }

    builder.buildObjects(AtomCore.getInstance(), physicalObjMap);
    builder.buildRelation(new ArrayList<>(), new HashMap<>());
    ConcreteCircularOrbit<AtomCore, Electron> tmp = builder.getConcreteCircularOrbit();
    if (tmp instanceof AtomCircularOrbit) {
      atomCircularOrbit = (AtomCircularOrbit) tmp;
    }
  }


  //////////////////////////////////////////////////////////////////////////////////////////////////
  private Electron getPhysicalObjByObName(
      Predicate<Electron> predicate, ConcreteCircularOrbit circularOrbit) {
    Iterator<Electron> ite = circularOrbit.iterator();
    List<Electron> physicalObjs = new ArrayList<>();
    while (ite.hasNext()) {
      Electron tmp = ite.next();
      physicalObjs.add(tmp);
    }
    Collections.shuffle(physicalObjs);
    return physicalObjs.stream().filter(predicate)
        .collect(Collectors.toList()).get(0);
  }

  /**
   * .
   * 向轨道系统co中添加轨道tk
   */
  public void addTrack(double addRadius) {
    atomCircularOrbit.addTrack(TrackFactory.getTrackInstance(addRadius));
    reLoadAll();
  }

  /**
   * .
   * 在轨道系统co中移除轨道tk
   */
  public void removeTrack(double rmRadius) {
    atomCircularOrbit.removeTrack(TrackFactory.getTrackInstance(rmRadius));
    reLoadAll();
  }

  /**
   * .
   * 向co的tk轨道添加运动员Electron
   */
  public void addPhysicalObject(Electron electron, double tkRadius) {
    atomCircularOrbit.addPhysicalObj2Track(electron, TrackFactory.getTrackInstance(tkRadius));
    reLoadAll();
  }

  /**
   * .
   * 在轨道系统co中移除运动员Electron
   */
  public void removePhysicalObject(double rmRadius) {
    Track tk = TrackFactory.getTrackInstance(rmRadius);
    Electron electron = getPhysicalObjByObName(
        x -> atomCircularOrbit.getTrackForPO(x).equals(tk),
        atomCircularOrbit);
    atomCircularOrbit.removePhysicalObject(electron);
    reLoadAll();
  }

  @Override
  public void initialize() throws CheckedException {
    loadConfig();
    initCircularOrbit();
  }

  @Override
  public void draw(JFrame frame) {
    panel = new AtomStructurePanel(this);
    frame.getContentPane().add(panel);
    atomCircularOrbit.visualize(panel.getDrawPanel());
    reLoadAll();
  }

  /**
   * .
   * 重新计算轨道系统GUI中需要到的轨道系统信息
   */
  public void reLoadAll() {
    if (panel == null) {
      return;
    }
    CircularOrbitApis<AtomCore, Electron> apis = new CircularOrbitApis<>();
    CircularOrbit<AtomCore, Electron> nowCircularOrbit = atomCircularOrbit;
    boolean state = apis.checkOrbitAvailable(nowCircularOrbit);
    double entropy = apis.getObjectDistributionEntropy(nowCircularOrbit);
    List<String> trackList = nowCircularOrbit.getTrackRadiusList();
    Iterator<Electron> ite = ((AtomCircularOrbit) nowCircularOrbit).iterator();
    List<String> physicalObjs = new ArrayList<>();
    while (ite.hasNext()) {
      physicalObjs.add(ite.next().getObName());
    }
    panel.reloadGameInfo(state, entropy, trackList, physicalObjs, careTaker.getAllHistory());
    nowCircularOrbit.visualize(panel.getDrawPanel());
  }
  //////////////////////////////////////////////////////////////////////////////////////////

  /**
   * .
   * 电子迁移
   *
   * @param source 电子迁移的源轨道半径
   * @param target 电子迁移的目标轨道半径
   */
  public void electronTransit(double source, double target) {
    Track sourceTrack = TrackFactory.getTrackInstance(source);
    Track targetTrack = TrackFactory.getTrackInstance(target);

    Electron electron = getPhysicalObjByObName(
        x -> atomCircularOrbit.getTrackForPO(x).equals(sourceTrack), atomCircularOrbit);
    atomCircularOrbit.removePhysicalObject(electron);
    atomCircularOrbit.addPhysicalObj2Track(electron, targetTrack);
    careTaker.addMemento(electron, sourceTrack, targetTrack);
    reLoadAll();
  }

  public void rebackHistory(int index) {
    atomCircularOrbit.reback(careTaker.rebackMemento(index));
    reLoadAll();
  }
}
