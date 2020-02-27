package applications.TrackGame;

import applications.TrackGame.strategy.SimpleStrategy;
import org.junit.jupiter.api.Test;
import otherDirectory.exception.CheckedException;
import otherDirectory.exception.UncheckedException;

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

public class TrackGameTest {
    @Test
    void testLoadFile() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame.txt");
            game.loadConfig();
//        System.out.println("numofTracks: "+game.numofTracks);
//        System.out.println("gameTypes:");
//        for(Integer type:game.gameTypes) {
//            System.out.println(type);
//        }
//        System.out.println("runnerList:");
//        for(Runner runner:game.runnerList) {
//            System.out.println(runner.getObName()+" "+runner.getBestScore());
//        }
        } catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    public void testDivideIntoGroups() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame.txt");
            game.loadConfig();
            game.divideIntoGroups(new SimpleStrategy());
            List<TrackCircularOrbit> trackGamesCircular = game.getTrackCircularOrbitList();
            for (TrackCircularOrbit circularOrbit : trackGamesCircular) {
                Iterator<Runner> iterator = circularOrbit.iterator();
                while (iterator.hasNext()) {
                    Runner runner = iterator.next();
                    System.out.println(runner.getObName() + " :: " + runner.getBestScore());
                }
                System.out.println();
            }
        } catch(CheckedException e) {
            System.out.println(e);
        }
    }

    /**
     * 测试exchangeGroup
     */
    @Test
    void testExchangeGroup() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame.txt");
            game.loadConfig();
            game.divideIntoGroups(new SimpleStrategy());
            List<TrackCircularOrbit> trackGamesCircular = game.getTrackCircularOrbitList();
            List<Runner> runnerList = game.getRunnerList();
            Runner ra = runnerList.get(0), rb = runnerList.get(5);
//            System.out.println( "<->" + rb.getObName() + "\n");
            game.exchangeGroup("Bolt","2::Park");

            for (TrackCircularOrbit circularOrbit : trackGamesCircular) {
                Iterator<Runner> iterator = circularOrbit.iterator();
                while (iterator.hasNext()) {
                    Runner runner = iterator.next();
                    System.out.println(runner.getObName() + " :: " + runner.getBestScore());
                }
                System.out.println();
            }
        } catch(CheckedException e) {
            System.out.println(e);
        } catch(UncheckedException e) {
            System.out.println(e);
        }
    }

    /**
     * 测试exchangeTrack
     */
    @Test
    void testExchangeTrack() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame.txt");
            game.loadConfig();
            game.divideIntoGroups(new SimpleStrategy());
            List<TrackCircularOrbit> trackGamesCircular = game.getTrackCircularOrbitList();
            List<Runner> runnerList = game.getRunnerList();
            Runner ra = runnerList.get(0), rb = runnerList.get(1);
            System.out.println(ra.getObName() + "<->" + rb.getObName() + "\n");
            game.exchangeTrack(ra.getObName(), rb.getObName());

            for (TrackCircularOrbit circularOrbit : trackGamesCircular) {
                Iterator<Runner> iterator = circularOrbit.iterator();
                while (iterator.hasNext()) {
                    Runner runner = iterator.next();
                    System.out.println(runner.getObName() + " :: " + runner.getBestScore());
                }
                System.out.println();
            }
        } catch(CheckedException e) {
            System.out.println(e);
        } catch(UncheckedException e) {
            System.out.println(e);
        }
    }

//    测试输入格式错误
    @Test
    void testLoadFautlFile() {
//        测试方法：修改对应文件 然后运行test进行测试
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame_F0.txt");
            game.loadConfig();
        } catch (CheckedException e) {
            System.out.println(e.toString());
        }
    }


//    测试有两个相同名称的runner
    @Test
    void testSameName() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame_F1.txt");
            game.loadConfig();
        } catch (CheckedException e) {
            System.out.println(e.toString());
        }
    }


    @Test
    void testTrackOpration() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame.txt");
            game.initialize();
            game.addTrack(10);
            game.removeTrack(10);
        } catch (CheckedException e) {
            System.out.println(e.toString());
        } catch(IndexOutOfBoundsException e) {
//            System.out.println(e);
        }
    }

    @Test
    void testPOOpration() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame.txt");
            game.initialize();
            game.addTrack(10);
            game.addPhysicalObject(Runner.getInstance("ffasdfas",12,12,12,"ABC"),10);
            game.removePhysicalObject("123");
        } catch (CheckedException e) {
            System.out.println(e.toString());
        } catch(IndexOutOfBoundsException e) {
//            System.out.println(e);
        }
    }

    @Test
    void test1() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame_E_AtheleteAge.txt");
            game.initialize();
        }catch(CheckedException e) {
            System.out.println(e);
        }
    }
    @Test
    void test2() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame_E_AtheleteBestScore.txt");
            game.initialize();
        }catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void test3() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame_E_AtheleteCountry.txt");
            game.initialize();
        }catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void test4() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame_E_AtheleteID.txt");
            game.initialize();
        }catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void test5() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame_E_AtheleteName.txt");
            game.initialize();
        }catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void test6() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame_E_Label.txt");
            game.initialize();
        }catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void test7() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame_E_Length.txt");
            game.initialize();
        }catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void test8() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame_E_TrackFormat.txt");
            game.initialize();
        }catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void test9() {
        try {
            TrackGame game = new TrackGame("src/configFile/TrackGame_E_TrackNum.txt");
            game.initialize();
        }catch(CheckedException e) {
            System.out.println(e);
        }
    }
}
