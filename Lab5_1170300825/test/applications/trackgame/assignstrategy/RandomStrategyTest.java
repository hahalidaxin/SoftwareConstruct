package applications.trackgame.assignstrategy;

import applications.trackgame.Runner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import track.Track;
import track.TrackFactory;

public class RandomStrategyTest {

    @Test
    public void testSimpleStrategy() {
        List<Track> tracks = new ArrayList<>();
        tracks.add(TrackFactory.getTrackInstance(1.0));
        tracks.add(TrackFactory.getTrackInstance(2.0));
        tracks.add(TrackFactory.getTrackInstance(3.0));
        List<Runner> runners = new ArrayList<>();
        runners.add(Runner.getInstance("A",1,12,98.0,"CN"));
        runners.add(Runner.getInstance("B",2,12,98.0,"UK"));
        runners.add(Runner.getInstance("C",3,12,98.0,"UN"));
        runners.add(Runner.getInstance("D",4,12,98.0,"US"));
        runners.add(Runner.getInstance("E",5,12,98.0,"GE"));
        runners.add(Runner.getInstance("F",6,12,98.0,"JP"));
        runners.add(Runner.getInstance("G",6,12,98.0,"JP"));
        List<Map<Track,Runner>> groups = new RandomStrategy().assign(tracks,runners);
        for(Map<Track,Runner> group:groups) {
            for(Map.Entry<Track,Runner> entry:group.entrySet()) {
                System.out.println(entry.getKey().getRadius()+" :: "+entry.getValue().getObName());
            }
            System.out.println();
        }
    }

}
