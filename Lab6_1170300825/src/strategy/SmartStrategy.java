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
public class SmartStrategy implements CrossriverStrategy {

  public static int MIDSPEED = 4;
  public static int LOWSPEED = 2;

  private MonkeyGenerator generator;

  public SmartStrategy(MonkeyGenerator generator) {
    this.generator = generator;
  }

  @Override
  public Ladder assign(Monkey monkey) {
    synchronized (generator.ladder2Monkey) {
      Map<Ladder, List<Monkey>> ladder2Monkey = generator.ladder2Monkey;
      List<Ladder> laddersNoConflict = new ArrayList<>();
      int minLastMonkeyRungIndex = Integer.MAX_VALUE;
      Ladder minLastLadder = null;
      int maxLastMonkeyRungIndex = Integer.MIN_VALUE;
      Ladder maxLastLadder = null;
      for (Map.Entry<Ladder, List<Monkey>> entry : ladder2Monkey.entrySet()) {
        Ladder ladder = entry.getKey();
        synchronized (ladder) {
          List<Monkey> monkeyList = entry.getValue();
          if (monkeyList.isEmpty()) {
            if(monkey.getVel()>=LOWSPEED) {
              monkey.jump2Ladder(ladder);
              return ladder;
            }
          } else {
            Monkey lastMonkey = monkeyList.get(monkeyList.size() - 1);
            if (lastMonkey.getDirection().equals(monkey.getDirection())) {
              if (monkey.getDirection().equals("L->R") && lastMonkey.getRungIndex() > 1) {
                laddersNoConflict.add(ladder);
                if (minLastMonkeyRungIndex > lastMonkey.getRungIndex()) {
                  minLastMonkeyRungIndex = lastMonkey.getRungIndex();
                  minLastLadder = ladder;
                }
                if (maxLastMonkeyRungIndex < lastMonkey.getRungIndex()) {
                  maxLastMonkeyRungIndex = lastMonkey.getRungIndex();
                  maxLastLadder = ladder;
                }
              }
              if (monkey.getDirection().equals("R->L") && lastMonkey.getRungIndex() < ladder.getRungLength() - 1) {
                laddersNoConflict.add(ladder);
                if (minLastMonkeyRungIndex > lastMonkey.getRungIndex()) {
                  minLastMonkeyRungIndex = lastMonkey.getRungIndex();
                  minLastLadder = ladder;
                }
                if (maxLastMonkeyRungIndex < lastMonkey.getRungIndex()) {
                  maxLastMonkeyRungIndex = lastMonkey.getRungIndex();
                  maxLastLadder = ladder;
                }
              }
            }
          }
        }
      }
      if (laddersNoConflict.isEmpty()) {
        return null;
      } else {
        if (monkey.getVel() >= MIDSPEED) {
          //  认为速度比较快
          if(monkey.getDirection().equals("L->R")) {
            monkey.jump2Ladder(maxLastLadder);
            return maxLastLadder;
          } else {
            monkey.jump2Ladder(minLastLadder);
            return minLastLadder;
          }
        } else {
          //  认为速度比较慢
          if(monkey.getDirection().equals("L->R")) {
            monkey.jump2Ladder(minLastLadder);
            return minLastLadder;
          } else {
            monkey.jump2Ladder(maxLastLadder);
            return maxLastLadder;
          }
        }
      }
    }
  }

  @Override
  public String toString() {
    return "SmartStrategy";
  }
}
