package circularorbit;

import applications.atomstructure.AtomCircularOrbit;
import applications.atomstructure.AtomCore;
import applications.atomstructure.Electron;
import centralobject.CentralObject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import otherdirectory.exception.UncheckedException;
import physicalobject.PhysicalObject;
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


public class ConcreteCircularOrbitTest {

    /**
     * 轨道操作
     */
    @Test
    public void testAddTrack() {
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        Track track1 = TrackFactory.getTrackInstance(1.0);
        concreteCircularOrbit.addTrack(track1);
        System.out.println(concreteCircularOrbit.addTrack(track1));
    }

    @Test
    public void testRemoveTrack() {
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        Track track1 = TrackFactory.getTrackInstance(1.0);
        Track track2 = TrackFactory.getTrackInstance(2.0);
        concreteCircularOrbit.addTrack(track1);
        try {
            System.out.println(concreteCircularOrbit.removeTrack(track2));
        } catch(UncheckedException e) {
            System.out.println(e);
        }
    }

    /**
     *  添加物体到轨道
     */

//    添加物体到不存在的轨道
    @Test
    public void testAddPhysicalObj2Track_SPNST() {
        try {
            ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
            Track track1 = TrackFactory.getTrackInstance(1.0);
            Track track2 = TrackFactory.getTrackInstance(2.0);
            PhysicalObject po1 = PhysicalObject.getInstance("po1");
            PhysicalObject po2 = PhysicalObject.getInstance("po2");
            concreteCircularOrbit.addTrack(track1);
            System.out.println(concreteCircularOrbit.addPhysicalObj2Track(po1, track1));
            System.out.println(concreteCircularOrbit.addPhysicalObj2Track(po1, track2));
        } catch(UncheckedException e) {
            System.out.println(e.toString());
        }
    }

    //    相同物体先后添加到不同的轨道
    @Test
    public void testAddPhysicalObj2Track_SPNST2() {
        try {
            ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
            Track track1 = TrackFactory.getTrackInstance(1.0);
            Track track2 = TrackFactory.getTrackInstance(2.0);
            PhysicalObject po1 = PhysicalObject.getInstance("po1");
            PhysicalObject po2 = PhysicalObject.getInstance("po2");
            concreteCircularOrbit.addTrack(track1);
            concreteCircularOrbit.addTrack(track2);
            System.out.println(concreteCircularOrbit.addPhysicalObj2Track(po1, track1));
            System.out.println(concreteCircularOrbit.addPhysicalObj2Track(po1, track2));
        } catch(UncheckedException e) {
            System.out.println(e.toString());
        }
    }

    //    相同物体先后添加到相同轨道
    @Test
    public void testAddPhysicalObj2Track_SPNST3() {
        try {
            ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
            Track track1 = TrackFactory.getTrackInstance(1.0);
            Track track2 = TrackFactory.getTrackInstance(2.0);
            PhysicalObject po1 = PhysicalObject.getInstance("po1");
            PhysicalObject po2 = PhysicalObject.getInstance("po2");
            concreteCircularOrbit.addTrack(track1);
            concreteCircularOrbit.addTrack(track2);
            System.out.println(concreteCircularOrbit.addPhysicalObj2Track(po1, track1));
            System.out.println(concreteCircularOrbit.addPhysicalObj2Track(po1, track1));
        } catch(UncheckedException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * 测试transit
     */

    @Test
    public void testTransit() {
        try {
            ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
            Track track1 = TrackFactory.getTrackInstance(1.0);
            Track track2 = TrackFactory.getTrackInstance(2.0);
            PhysicalObject po1 = PhysicalObject.getInstance("po1");
            PhysicalObject po2 = PhysicalObject.getInstance("po2");
            concreteCircularOrbit.addTrack(track1);
            concreteCircularOrbit.addTrack(track2);
            PhysicalObject pot = new PhysicalObject(po1.getObName());
            System.out.println(concreteCircularOrbit.addPhysicalObj2Track(po1, track1));
            //concreteCircularOrbit.transit(po1,pot,track2);
            UncheckedException.assertTrue(track2==concreteCircularOrbit.getTrackForPO(pot),"track2!=track for pot");
        } catch(UncheckedException e) {
            System.out.println(e.toString());
        }
    }

//    对不存在与系统的轨道物体进行transit
    @Test
    public void testTransit_noExistOldObj() {
        try {
            ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
            Track track1 = TrackFactory.getTrackInstance(1.0);
            Track track2 = TrackFactory.getTrackInstance(2.0);
            PhysicalObject po1 = PhysicalObject.getInstance("po1");
            PhysicalObject po2 = PhysicalObject.getInstance("po2");
            concreteCircularOrbit.addTrack(track1);
            concreteCircularOrbit.addTrack(track2);
            System.out.println(concreteCircularOrbit.addPhysicalObj2Track(po1, track1));
            //concreteCircularOrbit.transit(po2,new PhysicalObject(po1.getObName()),track2);
        } catch(UncheckedException e) {
            System.out.println(e.toString());
        }
    }

    //    transit到不存在的轨道
    @Test
    public void testTransit_noExistTrack() {
        try {
            ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
            Track track1 = TrackFactory.getTrackInstance(1.0);
            Track track2 = TrackFactory.getTrackInstance(2.0);
            PhysicalObject po1 = PhysicalObject.getInstance("po1");
            PhysicalObject po2 = PhysicalObject.getInstance("po2");
            concreteCircularOrbit.addTrack(track1);
    //        concreteCircularOrbit.addTrack(track2);
            System.out.println(concreteCircularOrbit.addPhysicalObj2Track(po1, track1));
            PhysicalObject pot = new PhysicalObject(po1.getObName());
            //concreteCircularOrbit.transit(po1,pot,track2);

        } catch(UncheckedException e) {
            System.out.println(e.toString());
        }
    }

//    测试move
//    @Test
//    public void testMove() {
//        try {
//            ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
//            Track track1 = TrackFactory.getTrackInstance(1.0);
//            Track track2 = TrackFactory.getTrackInstance(2.0);
//            PhysicalObject po1 = PhysicalObject.getInstance("po1");
//            PhysicalObject po2 = PhysicalObject.getInstance("po2");
//            concreteCircularOrbit.addTrack(track1);
//            //        concreteCircularOrbit.addTrack(track2);
//            System.out.println(concreteCircularOrbit.addPhysicalObj2Track(po1, track1));
//            PhysicalObject pot = new PhysicalObject(po1.getObName());
//            concreteCircularOrbit.move(po1,pot);
//            UncheckedException.assertTrue(track1==concreteCircularOrbit.getTrackForPO(pot),"移动失败");
//        } catch(UncheckedException e) {
//            System.out.println(e.toString());
//        }
//    }

    /**
     * 测试轨道系统熵值计算
     */

    @Test
    public void testGetObjectDistributionEntropy0() {
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        Track track1 = TrackFactory.getTrackInstance(1.0);
        Track track2 = TrackFactory.getTrackInstance(2.0);
        Track track3 = TrackFactory.getTrackInstance(3.0);
        PhysicalObject po1 = PhysicalObject.getInstance("po1");
        PhysicalObject po2 = PhysicalObject.getInstance("po2");
        PhysicalObject po3 = PhysicalObject.getInstance("po3");
        PhysicalObject po4 = PhysicalObject.getInstance("po4");
        PhysicalObject po5 = PhysicalObject.getInstance("po5");
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track2);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObj2Track(po1,track1);
        concreteCircularOrbit.addPhysicalObj2Track(po2,track1);
        concreteCircularOrbit.addPhysicalObj2Track(po3,track1);
        System.out.println(concreteCircularOrbit.getObjectDistributionEntropy());
    }

    @Test
    public void testGetObjectDistributionEntropyA() {
        // TODO: 2019/4/11 好像跟我自己计算的有点差别？？？？
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        Track track1 = TrackFactory.getTrackInstance(1.0);
        Track track2 = TrackFactory.getTrackInstance(2.0);
        Track track3 = TrackFactory.getTrackInstance(3.0);
        PhysicalObject po1 = PhysicalObject.getInstance("po1");
        PhysicalObject po2 = PhysicalObject.getInstance("po2");
        PhysicalObject po3 = PhysicalObject.getInstance("po3");
        PhysicalObject po4 = PhysicalObject.getInstance("po4");
        PhysicalObject po5 = PhysicalObject.getInstance("po5");
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track2);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObj2Track(po1,track1);
        concreteCircularOrbit.addPhysicalObj2Track(po2,track2);
        concreteCircularOrbit.addPhysicalObj2Track(po3,track3);
        System.out.println(concreteCircularOrbit.getObjectDistributionEntropy());
    }

    /**
     * 测试LogicalDistance
     */

//    po1--po2--po4--po5
//      |__po3 __|
    @Test
    public void testGetLogicalDistance() {
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        Track track1 = TrackFactory.getTrackInstance(1.0);
        Track track2 = TrackFactory.getTrackInstance(2.0);
        Track track3 = TrackFactory.getTrackInstance(3.0);
        PhysicalObject po1 = PhysicalObject.getInstance("po1");
        PhysicalObject po2 = PhysicalObject.getInstance("po2");
        PhysicalObject po3 = PhysicalObject.getInstance("po3");
        PhysicalObject po4 = PhysicalObject.getInstance("po4");
        PhysicalObject po5 = PhysicalObject.getInstance("po5");
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track2);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObj2Track(po1,track1);
        concreteCircularOrbit.addPhysicalObj2Track(po2,track2);
        concreteCircularOrbit.addPhysicalObj2Track(po3,track3);
        concreteCircularOrbit.addPhysicalObj2Track(po4,track1);
        concreteCircularOrbit.addPhysicalObj2Track(po5,track2);

        concreteCircularOrbit.addRelationOf2TrackObs(po1,po2,1);
        concreteCircularOrbit.addRelationOf2TrackObs(po1,po3,1);
        concreteCircularOrbit.addRelationOf2TrackObs(po4,po2,1);
        concreteCircularOrbit.addRelationOf2TrackObs(po3,po4,1);
        concreteCircularOrbit.addRelationOf2TrackObs(po4,po5,1);
        try {
            UncheckedException.assertTrue(3 == concreteCircularOrbit.getLogicalDistance(po1, po5), "距离求错");
            UncheckedException.assertTrue(1 == concreteCircularOrbit.getLogicalDistance(po1, po3), "距离求错");
            UncheckedException.assertTrue(2 == concreteCircularOrbit.getLogicalDistance(po3, po5), "距离求错");
        } catch(UncheckedException e) {
            System.out.println(e);
        }
    }

    /**
     * 测试计算物理距离
     */
    @Test
    public void testgetPhysicalDistance() {
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        Track track1 = TrackFactory.getTrackInstance(1.0);
        Track track2 = TrackFactory.getTrackInstance(2.0);
        Track track3 = TrackFactory.getTrackInstance(3.0);
        PhysicalObject po1 = PhysicalObject.getInstance("po1");
        PhysicalObject po2 = PhysicalObject.getInstance("po2");
        PhysicalObject po3 = PhysicalObject.getInstance("po3");
        PhysicalObject po4 = PhysicalObject.getInstance("po4");
        PhysicalObject po5 = PhysicalObject.getInstance("po5");
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track2);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObj2Track(po1,track1);
        concreteCircularOrbit.addPhysicalObj2Track(po2,track2);
        concreteCircularOrbit.addPhysicalObj2Track(po3,track3);
        concreteCircularOrbit.addPhysicalObj2Track(po4,track1);
        concreteCircularOrbit.addPhysicalObj2Track(po5,track2);
//        UncheckedException.assertTrue(3.0==concreteCircularOrbit.getPhysicalDistance(po1,po2),"物理距离求错");
//        UncheckedException.assertTrue(5.0==concreteCircularOrbit.getPhysicalDistance(po1,po3),"物理距离求错");
    }

    /**
     * 测试Iterator
     */
    @Test
    public void testIterator() {
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        Track track1 = TrackFactory.getTrackInstance(1.0);
        Track track2 = TrackFactory.getTrackInstance(2.0);
        Track track3 = TrackFactory.getTrackInstance(3.0);
        PhysicalObject po1 = PhysicalObject.getInstance("po2");
        PhysicalObject po2 = PhysicalObject.getInstance("po1");
        PhysicalObject po3 = PhysicalObject.getInstance("po5");
        PhysicalObject po4 = PhysicalObject.getInstance("po4");
        PhysicalObject po5 = PhysicalObject.getInstance("po3");
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track2);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObj2Track(po1,track1);
        concreteCircularOrbit.addPhysicalObj2Track(po2,track1);
        concreteCircularOrbit.addPhysicalObj2Track(po3,track1);
        concreteCircularOrbit.addPhysicalObj2Track(po4,track2);
        concreteCircularOrbit.addPhysicalObj2Track(po5,track3);
        Iterator<PhysicalObject> iterator = concreteCircularOrbit.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next().getObName());
        }
    }

    /**
     * 测试Difference
     */
    @Test
    void testDifference() {
//        1:ABC 2:D 3:E
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        Track track1 = TrackFactory.getTrackInstance(1.0);
        Track track2 = TrackFactory.getTrackInstance(2.0);
        Track track3 = TrackFactory.getTrackInstance(3.0);
        PhysicalObject po1 = PhysicalObject.getInstance("A");
        PhysicalObject po2 = PhysicalObject.getInstance("B");
        PhysicalObject po3 = PhysicalObject.getInstance("C");
        PhysicalObject po4 = PhysicalObject.getInstance("D");
        PhysicalObject po5 = PhysicalObject.getInstance("E");
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track2);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObj2Track(po1,track1);
        concreteCircularOrbit.addPhysicalObj2Track(po2,track1);
        concreteCircularOrbit.addPhysicalObj2Track(po3,track1);
        concreteCircularOrbit.addPhysicalObj2Track(po4,track2);
        concreteCircularOrbit.addPhysicalObj2Track(po5,track3);

//        1:FG 2:HI 3:E
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit2 = new ConcreteCircularOrbit<>();
        Track track12 = TrackFactory.getTrackInstance(1.0);
        Track track22 = TrackFactory.getTrackInstance(2.0);
        Track track32 = TrackFactory.getTrackInstance(3.0);
        Track track42 = TrackFactory.getTrackInstance(4.0);
        PhysicalObject po12 = PhysicalObject.getInstance("F");
        PhysicalObject po22 = PhysicalObject.getInstance("G");
        PhysicalObject po32 = PhysicalObject.getInstance("H");
        PhysicalObject po42 = PhysicalObject.getInstance("I");
        PhysicalObject po52 = PhysicalObject.getInstance("E");
        concreteCircularOrbit2.addTrack(track12);
        concreteCircularOrbit2.addTrack(track22);
        concreteCircularOrbit2.addTrack(track32);
        concreteCircularOrbit2.addTrack(track42);
        concreteCircularOrbit2.addPhysicalObj2Track(po12,track12);
        concreteCircularOrbit2.addPhysicalObj2Track(po22,track12);
        concreteCircularOrbit2.addPhysicalObj2Track(po32,track22);
        concreteCircularOrbit2.addPhysicalObj2Track(po42,track22);
        concreteCircularOrbit2.addPhysicalObj2Track(po52,track32);

        System.out.println(concreteCircularOrbit.getDifference(concreteCircularOrbit2));
    }

    /**
     * 测试Difference
     */
    @Test
    void testDifference2() {
//        1:ABC 2:D 3:E
//        1:FG 2:HI 3:E
        Electron po1 = Electron.getInstance();
        Electron po2 = Electron.getInstance();
        Electron po3 = Electron.getInstance();
        Electron po4 = Electron.getInstance();
        Electron po5 = Electron.getInstance();
        Track track1 = TrackFactory.getTrackInstance(1.0);
        Track track2 = TrackFactory.getTrackInstance(2.0);
        Track track3 = TrackFactory.getTrackInstance(3.0);

        AtomCore atomcore = AtomCore.getInstance();
        Map<Track, List<Electron>> pm = new HashMap<>();
        pm.put(track1, Arrays.asList(po1,po2,po3));
        pm.put(track2, Arrays.asList(po4));
        pm.put(track3, Arrays.asList(po5));
        AtomCircularOrbit concreteCircularOrbit1 = new AtomCircularOrbit(atomcore,pm,null,null,"");

        Track track12 = TrackFactory.getTrackInstance(1.0);
        Track track22 = TrackFactory.getTrackInstance(2.0);
        Track track32 = TrackFactory.getTrackInstance(3.0);
        Track track42 = TrackFactory.getTrackInstance(4.0);
        Electron po12 = Electron.getInstance();
        Electron po22 = Electron.getInstance();
        Electron po32 = Electron.getInstance();
        Electron po42 = Electron.getInstance();
        Electron po52 = Electron.getInstance();
        Map<Track,List<Electron>> pm2 = new HashMap<>();
        pm2.put(track12,Arrays.asList(po12,po22));
        pm2.put(track22,Arrays.asList(po32,po42));
        pm2.put(track32,Arrays.asList(po32,po52));
        AtomCircularOrbit concreteCircularOrbit2 = new AtomCircularOrbit(atomcore,pm2,null ,null,"");

        System.out.println(concreteCircularOrbit1.getDifference(concreteCircularOrbit2));
    }

    @Test
    void testVisualizePanel() {
        try {
            ConcreteCircularOrbit<CentralObject, PhysicalObject> orbit = new ConcreteCircularOrbit<CentralObject, PhysicalObject>();
            orbit.visualize(null);
        } catch(UncheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void testVisualizePanel2() {
        try {
            ConcreteCircularOrbit<CentralObject, PhysicalObject> orbit = new ConcreteCircularOrbit<CentralObject, PhysicalObject>();
            orbit.visualizeContentPanel();
        } catch(UncheckedException e) {
            System.out.println(e);
        }
    }

}
