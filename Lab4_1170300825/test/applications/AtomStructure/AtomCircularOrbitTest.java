package applications.AtomStructure;

import applications.AtomStructure.memento.ElectronTransitCareTaker;
import applications.AtomStructure.memento.ElectronTransitMemento;
import org.junit.jupiter.api.Test;
import track.Track;

import javax.swing.*;
import java.util.*;

/**
 * Testing Strategy
 *
 */

public class AtomCircularOrbitTest {

    /**
     * Testing Strategy
     *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
     *  对于含有分支的函数，划分等价类，对于每一个等价类设计一个测试用例
     *
     *  普通函数：只有一个操作分支 设计一个等价类
     *  visualize方法：外部使用JFrame或者JPanel接受返回值，并显示，
     *  因为在test中GUI不能够正常显示（一闪而过），所以只起到覆盖的作用。
     */

    @Test
    public void tetsOrbitReback() {
        ElectronTransitCareTaker careTaker = new ElectronTransitCareTaker();
        AtomCore atomCore = AtomCore.getInstance();
        Track track1 = Track.getInstance(1);
        Track track2 = Track.getInstance(2);
        Track track3 = Track.getInstance(3);
        Track track4 = Track.getInstance(4);
        Electron electron1 = Electron.getInstance("e1");
        Electron electron2 = Electron.getInstance("e2");
        Electron electron3 = Electron.getInstance("e3");

        Map<Track,List<Electron>> pm = new HashMap<>();
        pm.put(track1, new ArrayList<>(Arrays.asList(electron1)));
        pm.put(track2, new ArrayList<>(Arrays.asList(electron2)));
        pm.put(track3, new ArrayList<>(Arrays.asList(electron3)));
        pm.put(track4, new ArrayList<>());

        AtomCircularOrbit circularOrbit = new AtomCircularOrbit(atomCore,pm,new ArrayList<>(),new HashMap<>(),"Na");
//        0
        circularOrbit.transit(electron1,electron1,track4);
        careTaker.addMemento(electron1,track1,track4);
//       1
        circularOrbit.transit(electron2,electron2,track3);
        careTaker.addMemento(electron2,track2,track3);
//       2
        circularOrbit.transit(electron3,electron3,track1);
        careTaker.addMemento(electron3,track3,track1);
//       3
        circularOrbit.transit(electron2,electron2,track1);
        careTaker.addMemento(electron2,track3,track1);

        List<ElectronTransitMemento> mementoList = careTaker.rebackMemento(3);
//        mementoList.stream()
//                .forEach(x-> System.out.println(x.getElectron().getObName()+" "+x.getFromTrack().getRadius()+" "+x.getToTrack().getRadius()));
//
        circularOrbit.reback(mementoList);

        System.out.println("e1 :: "+circularOrbit.getTrackForPO(electron1).getRadius());
        System.out.println("e2 :: "+circularOrbit.getTrackForPO(electron2).getRadius());
        System.out.println("e3 :: "+circularOrbit.getTrackForPO(electron3).getRadius());
    }

    @Test
    void testVisualizePanel() {
        ElectronTransitCareTaker careTaker = new ElectronTransitCareTaker();
        AtomCore atomCore = AtomCore.getInstance();
        Track track1 = Track.getInstance(1);
        Track track2 = Track.getInstance(2);
        Track track3 = Track.getInstance(3);
        Track track4 = Track.getInstance(4);
        Electron electron1 = Electron.getInstance("e1");
        Electron electron2 = Electron.getInstance("e2");
        Electron electron3 = Electron.getInstance("e3");

        Map<Track,List<Electron>> pm = new HashMap<>();
        pm.put(track1, new ArrayList<>(Arrays.asList(electron1)));
        pm.put(track2, new ArrayList<>(Arrays.asList(electron2)));
        pm.put(track3, new ArrayList<>(Arrays.asList(electron3)));
        pm.put(track4, new ArrayList<>());

        AtomCircularOrbit circularOrbit = new AtomCircularOrbit(atomCore,pm,new ArrayList<>(),new HashMap<>(),"Na");

        JFrame jframe = new JFrame();
        jframe.setSize(800,800);
        jframe.getContentPane().add(circularOrbit.visualizeContentPanel());
        jframe.setVisible(true);
    }

    @Test
    void testVisualize() {
        ElectronTransitCareTaker careTaker = new ElectronTransitCareTaker();
        AtomCore atomCore = AtomCore.getInstance();
        Track track1 = Track.getInstance(1);
        Track track2 = Track.getInstance(2);
        Track track3 = Track.getInstance(3);
        Track track4 = Track.getInstance(4);
        Electron electron1 = Electron.getInstance("e1");
        Electron electron2 = Electron.getInstance("e2");
        Electron electron3 = Electron.getInstance("e3");

        Map<Track,List<Electron>> pm = new HashMap<>();
        pm.put(track1, new ArrayList<>(Arrays.asList(electron1)));
        pm.put(track2, new ArrayList<>(Arrays.asList(electron2)));
        pm.put(track3, new ArrayList<>(Arrays.asList(electron3)));
        pm.put(track4, new ArrayList<>());

        AtomCircularOrbit circularOrbit = new AtomCircularOrbit(atomCore,pm,new ArrayList<>(),new HashMap<>(),"Na");

        JFrame jframe = new JFrame();
        jframe.setSize(800,800);
        JPanel jpanel = new JPanel();
        circularOrbit.visualize(jpanel);
        jframe.getContentPane().add(jpanel);
        jframe.setVisible(true);
    }
}
