package applications.SocialNetworkCircle;

import org.junit.jupiter.api.Test;
import otherDirectory.Relation;
import otherDirectory.exception.CheckedException;
import track.Track;

import javax.swing.*;
import java.util.*;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  普通函数：只有一个操作分支 设计一个等价类
 *  如何验证Orbit操作是否成功？
 *  因为没有提供接口负责获得域，所以对于一些复杂的需要检查的test，这里使用添加Debug断点的方法
 *  人工查看在函数执行之后的系统状态
 */

public class SocialNetworkCircularOrbitTest {

    void addEdge(Map<Friend,List<Relation<Friend,Friend>>> pm,Friend A,Friend B,double w) {
        if(!pm.containsKey(A)) {
            pm.put(A,new ArrayList<>());
        }
        if(!pm.containsKey(B)) {
            pm.put(B,new ArrayList<>());
        }
        pm.get(A).add(new Relation<>(A,B,w));
        pm.get(B).add(new Relation<>(B,A,w));
    }

    @Test
    void testGetInfoDiffusion() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configFile/SocialNetworkCircle.txt");
            socialNetworkCircle.loadConfig();
            socialNetworkCircle.initCircularOrbit();
            socialNetworkCircle.getCircularOrbit().getInfoDiffusion().entrySet().stream()
                    .forEach(entry -> System.out.println(entry.getKey().getObName() + " :: " + entry.getValue()));
        } catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void testRemoveRelation2TrackObjs() {
        CentralUser centralUser = CentralUser.getInstance("central");
        Friend frA = Friend.getInstance("A");
        Friend frB = Friend.getInstance("B");
        Friend frC = Friend.getInstance("C");
        Friend frD = Friend.getInstance("D");
        Friend frE = Friend.getInstance("E");
        Friend frF = Friend.getInstance("F");
        Track tk1 = Track.getInstance(1);
        Track tk2 = Track.getInstance(2);
        Track tk3 = Track.getInstance(3);
        Track tk4 = Track.getInstance(4);

        Map<Track, List<Friend>> pm = new HashMap<>();
        pm.put(tk1, Arrays.asList(frA,frB));
        pm.put(tk2, Arrays.asList(frC));
        pm.put(tk3, Arrays.asList(frD,frE,frF));
        List<Relation<CentralUser,Friend>> rel1 = new ArrayList<>();
        rel1.add(new Relation<>(centralUser,frA,1));
        rel1.add(new Relation<>(centralUser,frB,1));

        Map<Friend,List<Relation<Friend,Friend>>> rel2 = new HashMap<>();

        addEdge(rel2,frA,frC,1);
        addEdge(rel2,frB,frC,1);
        addEdge(rel2,frC,frD,1);
        addEdge(rel2,frC,frE,1);
        addEdge(rel2,frC,frF,1);

        SocialNetworkCircularOrbit circularOrbit = new SocialNetworkCircularOrbit(centralUser,pm,rel1,rel2);

        circularOrbit.getInfoDiffusion().entrySet().stream()
                .forEach(entry-> System.out.println(entry.getKey().getObName()+" :: "+entry.getValue()));

        System.out.println(circularOrbit.removeRelationOf2TrackObs(frA,frC));

        System.out.println();
        circularOrbit.getInfoDiffusion().entrySet().stream()
                .forEach(entry-> System.out.println(entry.getKey().getObName()+" :: "+entry.getValue()));

    }

    @Test
    void testRemoveRelation2TrackObjs2() {
        SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configFile/SocialNetworkCircle.txt");
        CentralUser centralUser = CentralUser.getInstance("central");
        Friend frA = Friend.getInstance("A");
        Friend frB = Friend.getInstance("B");
        Friend frC = Friend.getInstance("C");
        Friend frD = Friend.getInstance("D");
        Friend frE = Friend.getInstance("E");
        Friend frF = Friend.getInstance("F");
        Friend frG = Friend.getInstance("G");
        Track tk1 = Track.getInstance(1);
        Track tk2 = Track.getInstance(2);
        Track tk3 = Track.getInstance(3);
        Track tk4 = Track.getInstance(4);

        Map<Track, List<Friend>> pm = new HashMap<>();
        pm.put(tk1, Arrays.asList(frA,frB));
        pm.put(tk2, Arrays.asList(frC));
        pm.put(tk3, Arrays.asList(frD,frE,frF));
        pm.put(tk4, Arrays.asList(frG));
        List<Relation<CentralUser,Friend>> rel1 = new ArrayList<>();
        rel1.add(new Relation<>(centralUser,frA,1));
        rel1.add(new Relation<>(centralUser,frB,1));

        Map<Friend,List<Relation<Friend,Friend>>> rel2 = new HashMap<>();

        addEdge(rel2,frA,frC,1);
        addEdge(rel2,frB,frC,1);
        addEdge(rel2,frC,frD,1);
        addEdge(rel2,frC,frE,1);
        addEdge(rel2,frC,frF,1);
        addEdge(rel2,frD,frG,1);

        SocialNetworkCircularOrbit circularOrbit = new SocialNetworkCircularOrbit(centralUser,pm,rel1,rel2);

        circularOrbit.getInfoDiffusion().entrySet().stream()
                .forEach(entry-> System.out.println(entry.getKey().getObName()+" :: "+entry.getValue()));

        System.out.println(circularOrbit.extendRelationOfCentralObj2TrackObj(centralUser,frF,1));
        System.out.println(circularOrbit.extendRelationOf2TrackObs(frD,frF,1));
        System.out.println(circularOrbit.curtailRelationOf2TrackObs(frD,frF));
        System.out.println(circularOrbit.curtailRelationOfCentralObjs2TraObj(centralUser,frF));

        System.out.println();
        circularOrbit.getInfoDiffusion().entrySet().stream()
                .forEach(entry-> System.out.println(entry.getKey().getObName()+" :: "+entry.getValue()));
        System.out.println("frF :: "+circularOrbit.getTrackForPO(frF).getRadius());
    }

    @Test
    void testVisualizePanle() {
        SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configFile/SocialNetworkCircle.txt");
        CentralUser centralUser = CentralUser.getInstance("central");
        Friend frA = Friend.getInstance("A");
        Friend frB = Friend.getInstance("B");
        Friend frC = Friend.getInstance("C");
        Friend frD = Friend.getInstance("D");
        Friend frE = Friend.getInstance("E");
        Friend frF = Friend.getInstance("F");
        Friend frG = Friend.getInstance("G");
        Track tk1 = Track.getInstance(1);
        Track tk2 = Track.getInstance(2);
        Track tk3 = Track.getInstance(3);
        Track tk4 = Track.getInstance(4);

        Map<Track, List<Friend>> pm = new HashMap<>();
        pm.put(tk1, Arrays.asList(frA,frB));
        pm.put(tk2, Arrays.asList(frC));
        pm.put(tk3, Arrays.asList(frD,frE,frF));
        pm.put(tk4, Arrays.asList(frG));
        List<Relation<CentralUser,Friend>> rel1 = new ArrayList<>();
        rel1.add(new Relation<>(centralUser,frA,1));
        rel1.add(new Relation<>(centralUser,frB,1));

        Map<Friend,List<Relation<Friend,Friend>>> rel2 = new HashMap<>();

        addEdge(rel2,frA,frC,1);
        addEdge(rel2,frB,frC,1);
        addEdge(rel2,frC,frD,1);
        addEdge(rel2,frC,frE,1);
        addEdge(rel2,frC,frF,1);
        addEdge(rel2,frD,frG,1);

        SocialNetworkCircularOrbit orbit = new SocialNetworkCircularOrbit(centralUser,pm,rel1,rel2);

        JFrame jframe = new JFrame();
        jframe.setSize(800,800);
        JPanel jpanel = new JPanel();
        orbit.visualize(jpanel);
        jframe.getContentPane().add(jpanel);
        jframe.setVisible(true);

        jframe.getContentPane().removeAll();
        jframe.getContentPane().add(orbit.visualizeContentPanel());
        jframe.setVisible(true);
    }


}
