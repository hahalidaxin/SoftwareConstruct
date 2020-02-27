package applications.trackgame.assignstrategy;

import applications.trackgame.Runner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import track.Track;

public class SortedScoreStrategy extends AssignmentStrategy {

  public SortedScoreStrategy() {
    this.strategyName = "SortedScoreStrategy";
  }

  /**
   * .
   * 根据运动员的 本年度最好成绩从高到低排序，
   * 排名越靠前则出场越晚且更占据中央的赛道
   *
   * @param tracks     轨道系统中的所有轨道
   * @param runnerList 所有比赛运动员 对于最后一组不全的情况，将至分配在最靠近中心的几条赛道上
   * @return 经过编排之后的轨道到轨道上所有物体的映射
   */
  @Override
  public List<Map<Track, Runner>> assign(List<Track> tracks, List<Runner> runnerList) {
    //按照BestScore从小到大排序
    runnerList.sort(Runner::compareToWithScoreAcs);
    List<Map<Track, Runner>> assignment = new ArrayList<>();
    int numOfTracks = tracks.size();
    int groupsCount = -1;

    int restLimit = 0;
    if (runnerList.size() % numOfTracks != 0) {
      restLimit = runnerList.size() % numOfTracks;
    }

    for (int i = 0; i < runnerList.size(); i++) {
      Runner runner = runnerList.get(i);
      if (i % numOfTracks == 0) {
        groupsCount++;
        assignment.add(new HashMap<>());
      }

      int uplimit = numOfTracks;
      if (restLimit != 0 && i / numOfTracks == runnerList.size() / numOfTracks) {
        uplimit = restLimit;
      }
      Track track = tracks.get(uplimit - 1 - (i % numOfTracks));

      assignment.get(groupsCount).put(track, runner);
    }

    return assignment;
  }
}
