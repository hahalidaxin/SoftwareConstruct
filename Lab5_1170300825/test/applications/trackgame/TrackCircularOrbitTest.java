package applications.trackgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.junit.jupiter.api.Test;
import track.Track;
import track.TrackFactory;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，划分等价类，对于每一个等价类设计一个测试用例
 *
 *  普通函数：只有一个操作分支 设计一个等价类
 *  getPhysicalObjectsOnTrack: 不包含tk，包含tk
 */

public class TrackCircularOrbitTest {
    @Test
    void testVisualizePanel() {
        Map<Track, List<Runner>> pm = new HashMap<>();
        Runner r1 = Runner.getInstance("123",1,12,33.0,"CN");
        Runner r2 = Runner.getInstance("123",1,12,33.0,"CN");
        Runner r3 = Runner.getInstance("123",1,12,33.0,"CN");
        Runner r4 = Runner.getInstance("123",1,12,33.0,"CN");
        pm.put(TrackFactory.getTrackInstance(1.0),new ArrayList<>(Arrays.asList(r1)));
        pm.put(TrackFactory.getTrackInstance(2.0),new ArrayList<>(Arrays.asList(r2)));
        pm.put(TrackFactory.getTrackInstance(3.0),new ArrayList<>(Arrays.asList(r3)));
        pm.put(TrackFactory.getTrackInstance(4.0),new ArrayList<>(Arrays.asList(r4)));
        TrackCircularOrbit orbit = new TrackCircularOrbit(null,pm,new ArrayList<>(),new HashMap<>());

        JFrame jframe = new JFrame();
        jframe.setSize(800,800);
        jframe.getContentPane().add(orbit.visualizeContentPanel());
        jframe.setVisible(true);
    }
    @Test
    void testVisualize() {
        Map<Track, List<Runner>> pm = new HashMap<>();
        Runner r1 = Runner.getInstance("123",1,12,33.0,"CN");
        Runner r2 = Runner.getInstance("123",1,12,33.0,"CN");
        Runner r3 = Runner.getInstance("123",1,12,33.0,"CN");
        Runner r4 = Runner.getInstance("123",1,12,33.0,"CN");
        pm.put(TrackFactory.getTrackInstance(1.0),new ArrayList<>(Arrays.asList(r1)));
        pm.put(TrackFactory.getTrackInstance(2.0),new ArrayList<>(Arrays.asList(r2)));
        pm.put(TrackFactory.getTrackInstance(3.0),new ArrayList<>(Arrays.asList(r3)));
        pm.put(TrackFactory.getTrackInstance(4.0),new ArrayList<>(Arrays.asList(r4)));
        TrackCircularOrbit orbit = new TrackCircularOrbit(null,pm,new ArrayList<>(),new HashMap<>());

        JFrame jframe = new JFrame();
        jframe.setSize(800,800);
        JPanel jpanel = new JPanel();
        orbit.visualize(jpanel);
        jframe.getContentPane().add(jpanel);
        jframe.setVisible(true);
    }
}
