package applications.TrackGame;

import applications.TrackGame.strategy.SimpleStrategy;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

public class TrackGameTest {
    @Test
    void testLoadFile() {
        TrackGame game = new TrackGame("src/configFile/TrackGame_Larger.txt");
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
    }

    @Test
    public void testDivideIntoGroups() {
//        List<Runner> runners = new ArrayList<>();
//        runners.add(Runner.getInstance("A",1,12,98.0,"CN"));
//        runners.add(Runner.getInstance("B",2,12,98.0,"UK"));
//        runners.add(Runner.getInstance("C",3,12,98.0,"UN"));
//        runners.add(Runner.getInstance("D",4,12,98.0,"US"));
//        runners.add(Runner.getInstance("E",5,12,98.0,"GE"));
//        runners.add(Runner.getInstance("F",6,12,98.0,"JP"));
//        runners.add(Runner.getInstance("G",6,12,98.0,"JP"));
        TrackGame game = new TrackGame("src/configFile/TrackGame.txt");
        game.loadConfig();
        game.divideIntoGroups(new SimpleStrategy());
        List<TrackCircularOrbit> trackGamesCircular = game.getTrackCircularOrbitList();
        for(TrackCircularOrbit circularOrbit:trackGamesCircular) {
            Iterator<Runner> iterator = circularOrbit.iterator();
            while(iterator.hasNext()) {
                Runner runner = iterator.next();
                System.out.println(runner.getObName()+" :: "+runner.getBestScore());
            }
            System.out.println();
        }
    }

    /**
     * 测试exchangeGroup
     */
    @Test
    void testExchangeGroup() {
        TrackGame game = new TrackGame("src/configFile/TrackGame.txt");
        game.loadConfig();
        game.divideIntoGroups(new SimpleStrategy());
        List<TrackCircularOrbit> trackGamesCircular = game.getTrackCircularOrbitList();
        List<Runner> runnerList = game.getRunnerList();
        Runner ra = runnerList.get(0)
                ,rb = runnerList.get(5);
        System.out.println(ra.getObName()+"<->"+rb.getObName()+"\n");
        game.exchangeGroup(ra.getObName(),rb.getObName());

        for(TrackCircularOrbit circularOrbit:trackGamesCircular) {
            Iterator<Runner> iterator = circularOrbit.iterator();
            while(iterator.hasNext()) {
                Runner runner = iterator.next();
                System.out.println(runner.getObName()+" :: "+runner.getBestScore());
            }
            System.out.println();
        }
    }

    /**
     * 测试exchangeTrack
     */
    @Test
    void testExchangeTrack() {
        TrackGame game = new TrackGame("src/configFile/TrackGame.txt");
        game.loadConfig();
        game.divideIntoGroups(new SimpleStrategy());
        List<TrackCircularOrbit> trackGamesCircular = game.getTrackCircularOrbitList();
        List<Runner> runnerList = game.getRunnerList();
        Runner ra = runnerList.get(0)
                ,rb = runnerList.get(1);
        System.out.println(ra.getObName()+"<->"+rb.getObName()+"\n");
        game.exchangeTrack(ra.getObName(),rb.getObName());

        for(TrackCircularOrbit circularOrbit:trackGamesCircular) {
            Iterator<Runner> iterator = circularOrbit.iterator();
            while(iterator.hasNext()) {
                Runner runner = iterator.next();
                System.out.println(runner.getObName()+" :: "+runner.getBestScore());
            }
            System.out.println();
        }
    }


}
