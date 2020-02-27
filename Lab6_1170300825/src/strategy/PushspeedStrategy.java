package strategy;

import adts.Ladder;
import adts.Monkey;
import bootstrap.MonkeyGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author hahalidaxin
 */
public class PushspeedStrategy implements CrossriverStrategy {
  MonkeyGenerator generator;
  public PushspeedStrategy(MonkeyGenerator generator) {
    this.generator = generator;
  }

  @Override
  public Ladder assign(Monkey monkey) {
    synchronized (generator.ladder2Monkey) {
      Map<Ladder, List<Monkey>> ladder2Monkey = generator.ladder2Monkey;
      List<Ladder> laddersNoConflict = new ArrayList<>();
      int minMonkeyCount = Integer.MAX_VALUE;
      for (Map.Entry<Ladder, List<Monkey>> entry : ladder2Monkey.entrySet()) {
        Ladder ladder = entry.getKey();
        synchronized (ladder) {
          List<Monkey> monkeyList = entry.getValue();
          if (monkeyList.isEmpty()) {
            monkey.jump2Ladder(ladder);
            return ladder;
          } else {
            Monkey lastMonkey = monkeyList.get(monkeyList.size() - 1);
            if (lastMonkey.getDirection().equals(monkey.getDirection())) {
              if (monkey.getDirection().equals("L->R") && lastMonkey.getRungIndex() > 1) {
                laddersNoConflict.add(ladder);
                minMonkeyCount = Math.min(minMonkeyCount, monkeyList.size());
              }
              if (monkey.getDirection().equals("R->L") && lastMonkey.getRungIndex() < ladder.getRungLength() - 1) {
                laddersNoConflict.add(ladder);
                minMonkeyCount = Math.min(minMonkeyCount, monkeyList.size());
              }
            }
          }
        }
      }
      if (laddersNoConflict.isEmpty()) {
        return null;
      } else {
        List<Ladder> ladderMinCount = new ArrayList<>();
        for (Ladder ladder : laddersNoConflict) {
          if (ladder2Monkey.get(ladder).size() == minMonkeyCount) {
            ladderMinCount.add(ladder);
          }
        }
        int maxSpeed = Integer.MIN_VALUE;
        int maxSpeedIndex = 0;
        for(int i=0;i<ladderMinCount.size();i++) {
          Ladder ladder = ladderMinCount.get(i);
          List<Monkey> monkeyList = ladder2Monkey.get(ladder);
          Monkey lastMonkey = monkeyList.get(monkeyList.size()-1);
          maxSpeed = Math.max(maxSpeed,lastMonkey.getVel());
          maxSpeedIndex = i;
        }
        Ladder finalLadder = ladderMinCount.get(maxSpeedIndex);
        monkey.jump2Ladder(finalLadder);
        return finalLadder;
      }
    }
  }

  @Override
  public String toString() {
    return "PushspeedStrategy";
  }
}
