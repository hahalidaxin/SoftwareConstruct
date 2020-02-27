package applications.atomstructure;

import applications.atomstructure.memento.ElectronTransitMemento;
import circularorbit.ConcreteCircularOrbit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import otherdirectory.GraphicsPainter;
import otherdirectory.Relation;
import otherdirectory.exception.UncheckedException;
import track.Track;

public class AtomCircularOrbit extends ConcreteCircularOrbit<AtomCore, Electron> {
  //AF（）= 原子名称为elementName的轨道系统
  //
  //RI：   与ConcreteCircularOrbit的RI相同
  //
  //Rep Explosure： 与ConcreteCircularOrbit相同


  private static Logger logger = Logger.getLogger(AtomCircularOrbit.class.getName());
  private String elementName;

  public AtomCircularOrbit(AtomCore co, Map<Track, List<Electron>> pm,
                           List<Relation<AtomCore, Electron>> rel1,
                           Map<Electron, List<Relation<Electron, Electron>>> rel2, String en) {
    super(co, pm, rel1, rel2);
    this.elementName = en;
  }

  @Override
  public boolean addPhysicalObj2Track(Electron po, Track tk) {
    try {
      UncheckedException.assertTrue(physicalObjectMap.containsKey(tk), "轨道系统中不包含目标轨道");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
    logger.info(String.format("将物体%s添加到%f轨道", po, tk.getRadius()));

    //不进行重复物体检查
    //physicalObjectMap.keySet().stream()
    //    .filter((x) -> physicalObjectMap.get(x).contains(po))
    //    .forEach((x) -> {
    //      UncheckedException.assertTrue(x == tk, "其他轨道已经包含该物体");
    //    });
    //if (physicalObjectMap.get(tk).contains(po)) {
    //  return false;
    //}
    physicalObjectMap.get(tk).add(po);

    if (!relOf2TraObjs.containsKey(po)) {
      relOf2TraObjs.put(po, new ArrayList<>());
    }
    checkRep();
    return true;
  }

  /**
   * .
   * 将物体进行轨道迁移
   *
   * @param oldObj 原来的轨道物体，要求该物体需要存在于轨道系统中
   * @param newObj 迁移更改轨道物体信息之后的轨道物体，要求不为null
   * @param t      迁移到的目标轨道，要求不为null
   */
  public void transit(Electron oldObj, Electron newObj, Track t) {
    try {
      UncheckedException.assertTrue(getTrackForPO(oldObj) != null, "oldObj不存在");
      UncheckedException.assertTrue(getTrackForPO(newObj) != null, "oldObj不存在");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
    logger.info(String.format("将%s迁移到新的轨道%f", oldObj, t.getRadius()));
    removePhysicalObject(oldObj);
    addPhysicalObj2Track(newObj, t);
    checkRep();
  }

  /**
   * .
   * 根据备忘录进行历史记录的回退
   *
   * @param mementoList 所有操作历史，操作历史是按照操作的顺序进行排序的
   *                    如果操作过程中遇到操作历史与轨道系统实际情况不符的情况则报错
   */
  public void reback(List<ElectronTransitMemento> mementoList) {
    //首先对原来的关系进行复制 以便恢复
    Map<Track, List<Electron>> copyElectronMap = new HashMap<>();
    for (Map.Entry<Track, List<Electron>> entry : physicalObjectMap.entrySet()) {
      Track track = entry.getKey();
      copyElectronMap.put(track, new ArrayList<>());
      for (Electron electron : entry.getValue()) {
        copyElectronMap.get(track).add(electron);
      }
    }
    int len = mementoList.size();
    try {
      for (int i = len - 1; i >= 0; i--) {
        ElectronTransitMemento memento = mementoList.get(i);
        UncheckedException.assertTrue(
            getTrackForPO(memento.getElectron()).equals(memento.getToTrack()),
            "操作历史与实际情况不符");
        transit(memento.getElectron(), memento.getElectron(), memento.getFromTrack());
      }
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
    logger.info(String.format("回退历史%d步", mementoList.size()));
  }

  @Override
  public JPanel visualizeContentPanel() {
    logger.info("将AtomCircularOrbit轨道系统可视化");
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

        graphics.setColor(Color.red);

        GraphicsPainter ctpainter = new GraphicsPainter();
        ctpainter.setPx(centerX);
        ctpainter.setPy(centerY);
        ctpainter.setRadius(30);
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

          List<Electron> electrons = physicalObjectMap.get(track);
          int sz = physicalObjectMap.get(track).size();
          for (int i = 0; i < sz; i++) {
            GraphicsPainter painter = new GraphicsPainter();
            double theta = i * (2 * Math.PI / (sz));
            int cx = (int) (tr * Math.cos(theta)) + centerX;
            int cy = (int) (tr * Math.sin(theta)) + centerY;
            painter.setPx(cx);
            painter.setPy(cy);
            painter.setRadius(2);
            electrons.get(i).drawGraphics(graphics, painter);
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
}
