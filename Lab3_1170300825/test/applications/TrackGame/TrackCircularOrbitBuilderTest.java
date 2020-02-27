package applications.TrackGame;

import org.junit.jupiter.api.Test;
import track.Track;

import java.util.*;
import java.util.stream.Collectors;

public class TrackCircularOrbitBuilderTest {
    @Test
    public void testTrackGameBuilder() {
        TrackCircularOrbitBuilder builder = new TrackCircularOrbitBuilder();
        builder.createConcreteCircularOrbit(null);
        List<Track> tracks = new ArrayList<>();
        Track track1 = Track.getInstance(1.0);
        Track track2 = Track.getInstance(2.0);
        Track track3 = Track.getInstance(3.0);
        tracks.add(track1);
        tracks.add(track2);
        tracks.add(track3);
        builder.buildTracks(tracks);
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
