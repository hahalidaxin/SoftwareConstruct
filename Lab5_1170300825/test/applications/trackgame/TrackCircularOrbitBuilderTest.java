package applications.trackgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import track.Track;
import track.TrackFactory;

public class TrackCircularOrbitBuilderTest {
    @Test
    public void testTrackGameBuilder() {
        TrackCircularOrbitBuilder builder = new TrackCircularOrbitBuilder();
        builder.createConcreteCircularOrbit(null);
        List<Track> tracks = new ArrayList<>();
        Track track1 = TrackFactory.getTrackInstance(1.0);
        Track track2 = TrackFactory.getTrackInstance(2.0);
        Track track3 = TrackFactory.getTrackInstance(3.0);
        Track track4 = TrackFactory.getTrackInstance(4.0);
        tracks.add(track1);
        tracks.add(track2);
        tracks.add(track3);
        tracks.add(track4);
        //builder.buildTracks(tracks);
        List<Runner> runners = new ArrayList<>();
        runners.add(Runner.getInstance("A",1,12,98.0,"CN"));

        runners.add(Runner.getInstance("B",2,12,98.0,"UK"));
        runners.add(Runner.getInstance("C",3,12,98.0,"UN"));
        runners.add(Runner.getInstance("D",4,12,98.0,"US"));

        runners.add(Runner.getInstance("E",5,12,98.0,"GE"));
        runners.add(Runner.getInstance("F",6,12,98.0,"JP"));
        runners.add(Runner.getInstance("G",6,12,98.0,"JP"));
        Map<Track,Runner> runnerMap = new HashMap<>();
        runnerMap.put(track3,runners.get(0));
        runnerMap.put(track2,runners.get(1));
        runnerMap.put(track1,runners.get(2));
        runnerMap.put(track4,runners.get(4));
        builder.buildObjects(null,
                runnerMap.keySet().stream()
                                .collect(Collectors.toMap(x->x,x->Arrays.asList(runnerMap.get(x)))));
        TrackCircularOrbit circularOrbit = (TrackCircularOrbit) builder.getConcreteCircularOrbit();
        Iterator<Runner> iterator = circularOrbit.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next().getObName());
        }
    }
}
