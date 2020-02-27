package applications.trackgame.assignstrategy;

import applications.trackgame.Runner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import track.Track;
import track.TrackFactory;

public class SortedScoreStrategyTest {
    @Test
    void testAssign() {
        List<Track> tracks = new ArrayList<>();
        tracks.add(TrackFactory.getTrackInstance(1.0));
        tracks.add(TrackFactory.getTrackInstance(2.0));
        tracks.add(TrackFactory.getTrackInstance(3.0));
        tracks.add(TrackFactory.getTrackInstance(4.0));
        tracks.add(TrackFactory.getTrackInstance(5.0));
        List<Runner> runners = new ArrayList<>();
        runners.add(Runner.getInstance("A",1,12,5,"CN"));
        runners.add(Runner.getInstance("B",2,12,7,"UK"));
        runners.add(Runner.getInstance("C",3,12,3,"UN"));
        runners.add(Runner.getInstance("D",4,12,6,"US"));
        runners.add(Runner.getInstance("E",5,12,1,"GE"));

        runners.add(Runner.getInstance("A1",11,12,15,"CN"));
        runners.add(Runner.getInstance("B1",12,12,17,"UK"));
        runners.add(Runner.getInstance("C1",13,12,13,"UN"));
//        runners.add(Runner.getInstance("D1",14,12,16,"US"));
//        runners.add(Runner.getInstance("E1",15,12,11,"GE"));
//        runners.add(Runner.getInstance("F",6,12,4,"JP"));
//        runners.add(Runner.getInstance("G",6,12,2,"JP"));
        List<Map<Track,Runner>> groups = new SortedScoreStrategy().assign(tracks,runners);
        for(Map<Track,Runner> group:groups) {
            group.keySet().stream()
                    .sorted()
                    .forEach((x)->{
                        System.out.println(x.getRadius()+" :: "+group.get(x).getObName());
                    });
            System.out.println();
        }
    }
}
