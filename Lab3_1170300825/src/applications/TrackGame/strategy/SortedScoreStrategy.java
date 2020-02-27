package applications.TrackGame.strategy;

import applications.TrackGame.Runner;
import track.Track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortedScoreStrategy extends AssignmentStrategy{

    public SortedScoreStrategy() {
        this.strategyName = "SortedScoreStrategy";
    }

    /**
     *  根据运动员的 本年度最好成绩从高到低排序，
     *  排名越靠前则出场越晚且更占据中央的赛道
     *
     * @param tracks
     * @param runnerList 所有比赛运动员 对于最后一组不全的情况，将至分配在最靠近中心的几条赛道上
     * @return
     */
    @Override
    public List<Map<Track, Runner>> assign(List<Track> tracks, List<Runner> runnerList) {
//        按照BestScore从小到大排序
        runnerList.sort(Runner::compareToWithScoreACS);
        List<Map<Track,Runner>> assignment = new ArrayList<>();
        int numOfTracks = tracks.size();
        int groupsCount = -1;

        int restLimit=0;
        if(runnerList.size()%numOfTracks!=0) {
            restLimit = runnerList.size()%numOfTracks;
        }

        for(int i=0;i<runnerList.size();i++) {
            Runner runner = runnerList.get(i);
            if(i%numOfTracks==0) {
                groupsCount++;
                assignment.add(new HashMap<>());
            }

            int uplimit=numOfTracks;
            if(restLimit!=0 && i/numOfTracks == runnerList.size()/numOfTracks) {
                uplimit = restLimit;
            }
            Track track = tracks.get(uplimit-1-(i%numOfTracks));

//            if(((i%numOfTracks)&1)==0) {
//                track = tracks.get(i%numOfTracks/2);
//            } else {
//                if(restLimit!=0 && i/numOfTracks == runnerList.size()/numOfTracks)
//                    uplimit = restLimit;
//                track = tracks.get(uplimit-1-(i%numOfTracks)/2);
//            }
            assignment.get(groupsCount).put(track,runner);
        }

        return assignment;
    }
}
