package applications.TrackGame.strategy;

import applications.TrackGame.Runner;
import track.Track;

import java.util.*;

public class SimpleStrategy extends AssignmentStrategy {

    public SimpleStrategy() {
        this.strategyName = "SimpleStrategy";
    }

    /**
     * 原来顺序
     * @param runnerList 所有比赛运动员
     * @return
     */
    @Override
    public List<Map<Track, Runner>> assign(List<Track> tracks, List<Runner> runnerList) {
//        随机化
        int numOfTracks = tracks.size();
        List<Map<Track,Runner>> assignment = new ArrayList<>();
        int groupsCount = -1;
        for(int i=0;i<runnerList.size();i++) {
            Runner runner = runnerList.get(i);
            Track track = tracks.get(i%numOfTracks);
            if(i%numOfTracks==0) {
                groupsCount++;
                assignment.add(new HashMap<>());
            }
            assignment.get(groupsCount).put(track,runner);
        }
        return assignment;
    }
}
