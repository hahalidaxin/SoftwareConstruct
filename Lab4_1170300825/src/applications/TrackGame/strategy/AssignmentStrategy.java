package applications.TrackGame.strategy;

import applications.TrackGame.Runner;
import track.Track;

import java.util.List;
import java.util.Map;

public abstract class AssignmentStrategy {
    protected String strategyName;
    /**
     * 比赛编排方案
     * @param tracks 所有的赛道
     * @param runnerList 所有比赛运动员
     * @return 编排组，每一个List代表一组运动员
     */
    abstract public List<Map<Track,Runner>> assign(List<Track> tracks, List<Runner> runnerList) ;

    public String getStrategyName() {
        return strategyName;
    }
}
