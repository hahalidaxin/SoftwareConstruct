package strategy;

import adts.Ladder;
import adts.Monkey;
import bootstrap.MonkeyGenerator;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.List;
import java.util.Map;

/**
 * .
 * 优先选择没有猴子的梯子，若所有梯子上都有猴子，则优先选择没有与我对向而行的猴子的梯子；
 * 若满足该条件的梯子有很多，则随机选择；
 *
 * @author hahalidaxin
 */
public class SimpleStrategy implements CrossriverStrategy {
  MonkeyGenerator generator;
  public SimpleStrategy(MonkeyGenerator generator) {
    this.generator = generator;
  }
  @Override
  public Ladder assign(Monkey monkey) {
    synchronized (generator.ladder2Monkey) {
      Map<Ladder, List<Monkey>> ladder2Monkey = generator.ladder2Monkey;
      Ladder ladderNoDirConflict = null;
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
                ladderNoDirConflict = ladder;
              }
              if (monkey.getDirection().equals("R->L") && lastMonkey.getRungIndex() < ladder.getRungLength() - 1) {
                ladderNoDirConflict = ladder;
              }
            }
          }
        }
      }
      if (ladderNoDirConflict != null) {
        monkey.jump2Ladder(ladderNoDirConflict);
        return ladderNoDirConflict;
      }
      return null;
    }
  }

  @Override
  public String toString() {
    return "SimpleStrategy";
  }
}
