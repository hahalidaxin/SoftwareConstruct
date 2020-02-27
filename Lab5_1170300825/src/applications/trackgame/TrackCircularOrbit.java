package applications.trackgame;

import centralobject.CentralObject;
import circularorbit.ConcreteCircularOrbit;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import otherdirectory.GraphicsPainter;
import otherdirectory.Relation;
import otherdirectory.exception.UncheckedException;
import track.Track;

public class TrackCircularOrbit extends ConcreteCircularOrbit<CentralObject, Runner> {
  //AF(*) = TrackGame轨道系统
  //
  //RI : 无中心点物体；跑道数为 无中心点物体；
  //跑道数为 4-10 之间；每一组的人数不能超 过跑道数、每一组的条里最多 1位运动员（但可以没有）；
  //如果第 n组的人数少于跑道数，则第 0到第 n-1各组的人数必须等于跑 道数。
  //
  //Rep Explosure ：与ConcreteCircularOrbit相同

  private static Logger logger = Logger.getLogger(TrackCircularOrbit.class.getName());

  public TrackCircularOrbit(
      CentralObject co,
      Map<Track, List<Runner>> pm,
      List<Relation<CentralObject, Runner>> rel1,
      Map<Runner, List<Relation<Runner, Runner>>> rel2) {
    super(co, pm, rel1, rel2);
  }

  @Override
  protected void checkRep() {
    super.checkRep();
    try {
      UncheckedException.assertTrue(checkOribitAvailable(), "不满足轨道系统合法的循环不变性");
    } catch (UncheckedException e) {
      logger.error(e);
      throw e;
    }
  }

  @Override
  public boolean checkOribitAvailable() {
    boolean ans = true;
    //无中心点物体
    ans = ans && centralObject == null;
    //跑道数在4-10之间
    int numOfTrack = physicalObjectMap.keySet().size();
    ans = ans && numOfTrack >= 4 && numOfTrack <= 10;
    //每组人数不超过跑道数
    int numOfRunner = physicalObjectMap.values().stream()
        .mapToInt(List::size).sum();
    ans = ans && numOfRunner <= numOfTrack;
    for (Map.Entry<Track, List<Runner>> entry : physicalObjectMap.entrySet()) {
      ans = ans && physicalObjectMap.get(entry.getKey()).size() <= 1;
    }
    return ans;
  }

  @Override
  public JPanel visualizeContentPanel() {
    logger.info("将TrackGame轨道系统可视化");
    return new JPanel() {
      //序列号（可省略）
      private static final long serialVersionUID = 1L;

      // 重写paint方法
      @Override
      public void paint(Graphics graphics) {
        final int pnLength = 800;
        final int padding = 100;
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

        List<Track> trackList = physicalObjectMap.keySet().stream()
            .sorted().collect(Collectors.toList());
        for (int idx = 1; idx <= trasz; idx++) {
          Track track = trackList.get(idx - 1);
          int tr = trackRadius[idx - 1];
          int width = 2 * tr;
          int height = 2 * tr;
          graphics.drawOval(centerX - tr, centerY - tr,
              width, height);

          List<Runner> poList = physicalObjectMap.get(track);
          int sz = poList.size();
          for (int i = 0; i < sz; i++) {
            GraphicsPainter painter = new GraphicsPainter();
            double theta = i * (2 * Math.PI / (sz));
            int cx = (int) (tr * Math.cos(theta)) + centerX;
            int cy = (int) (tr * Math.sin(theta)) + centerY;
            painter.setPx(cx);
            painter.setPy(cy);
            painter.setRadius(10);
            Runner runner = poList.get(i);
            runner.drawGraphics(graphics, painter);
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
