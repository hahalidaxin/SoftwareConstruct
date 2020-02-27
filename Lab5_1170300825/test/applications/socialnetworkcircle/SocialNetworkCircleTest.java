package applications.socialnetworkcircle;

import org.junit.jupiter.api.Test;
import otherdirectory.exception.CheckedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  普通操作：只要一个分支，只需要设计一个等价类带包测试用例
 *  对于构造类，负责GUI之间的桥接工作，所以对于一些gui操作，没有办法覆盖到。
 *  loadConfig：需要测试抛出异常，根据读入错误的种类划分等价类，对于每一种异常，设计一组测试用例文件，
 *  文件位于src/configFile下
 */

public class SocialNetworkCircleTest {

    static String[] socialNetwork = {"src/configfile/socialnetworkcircle.txt",
            "src/configfile/SocialNetworkCircle_Medium.txt",
            "src/configfile/SocialNetworkCircle_Larger.txt"
    };

    @Test
    void testLoadConfig() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/socialnetworkcircle.txt");
//            socialNetworkCircle.loadConfig();
            socialNetworkCircle.initialize();
            System.out.println("...");
        } catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void testInitGame() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle(socialNetwork[0]);
            socialNetworkCircle.loadConfig();
            socialNetworkCircle.initCircularOrbit();
        }catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void testIterator() {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        Iterator<Integer> iterator = integers.iterator();
        while(iterator.hasNext()) {
            int now = iterator.next();
            System.out.println(now);
            if(now==3) iterator.remove();
        }
        System.out.println();
        integers.stream().forEach(System.out::println);
    }

     @Test
     void testLoadFaultFile() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_F0.txt");
            socialNetworkCircle.loadConfig();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
     }

//     测试两个用户之间存在两条边的情况
     @Test
    void testSameEdge() {
         try {
             SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_F1.txt");
             socialNetworkCircle.loadConfig();
         } catch(CheckedException e) {
             System.out.println(e.toString());
         }
    }


//    测试没有声明Friend就开始在SocialTie中使用的情况
    @Test
    void testSocialTieWithoutUserName() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_F2.txt");
            socialNetworkCircle.loadConfig();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

//    测试Track操作
    @Test
    void testTrackOperation() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/socialnetworkcircle.txt");
            socialNetworkCircle.loadConfig();
            socialNetworkCircle.initCircularOrbit();
            socialNetworkCircle.addTrack(10);
            socialNetworkCircle.removeTrack(1);
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

//    测试Relation操作
    @Test
    void testRelationOperation() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/socialnetworkcircle.txt");
            socialNetworkCircle.loadConfig();
            socialNetworkCircle.initCircularOrbit();
            socialNetworkCircle.addRelation("TomWong","FrankLee",2);
            socialNetworkCircle.addRelation("TommyWong","FrankLee",2);
            socialNetworkCircle.removeRelation("TommyWong","LisaWong");
            socialNetworkCircle.removeRelation("TomWong","FrankLee");
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

    //    测试Obj操作
    @Test
    void testPOOperation() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/socialnetworkcircle.txt");
            socialNetworkCircle.loadConfig();
            socialNetworkCircle.initCircularOrbit();
            socialNetworkCircle.addPhysicalObject(Friend.getInstance("123","M",12), 1);
            socialNetworkCircle.removePhysicalObject("123");
//            socialNetworkCircle.removeRelation("TommyWong","LisaWong");
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void testLogicalDistance() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/socialnetworkcircle.txt");
            socialNetworkCircle.loadConfig();
            socialNetworkCircle.initCircularOrbit();
            System.out.println(socialNetworkCircle.getLogicalDistance("TomWong","FrankLee"));
            socialNetworkCircle.getSurroudings("FrankLee");
//            socialNetworkCircle.removeRelation("TommyWong","LisaWong");
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void test1() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_E_CentralUserAge.txt");
            socialNetworkCircle.initialize();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void test2() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_E_CentralUserGender.txt");
            socialNetworkCircle.initialize();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void test3() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_E_CentralUserLen.txt");
            socialNetworkCircle.initialize();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void test4() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_E_CentralUserName.txt");
            socialNetworkCircle.initialize();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void test5() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_E_FriendAge.txt");
            socialNetworkCircle.initialize();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void test6() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_E_FriendGender.txt");
            socialNetworkCircle.initialize();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void test7() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_E_FriendLen.txt");
            socialNetworkCircle.initialize();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void test8() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_E_FriendName.txt");
            socialNetworkCircle.initialize();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void test9() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_E_Label.txt");
            socialNetworkCircle.initialize();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void test10() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_E_SocialTieDensity.txt");
            socialNetworkCircle.initialize();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void test11() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_E_SocialTieUA.txt");
            socialNetworkCircle.initialize();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void test12() {
        try {
            SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/SocialNetworkCircle_E_SocialTieUB.txt");
            socialNetworkCircle.initialize();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }

}
