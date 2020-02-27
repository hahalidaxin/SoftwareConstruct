package adts;

import bootstrap.MonkeyGenerator;
import org.apache.log4j.Logger;
import strategy.CrossriverStrategy;
import strategy.PushspeedStrategy;
import strategy.SimpleStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * .
 *
 * @author hahalidaxin
 */
public class Monkey {

  /**
   * AF: 一个编号为id，爬梯子的速度为vel，当前状态为state的猴子。
   * RI: true
   * Rep Explosure: 泄露，保留着外部传入的MonkeyGenerator，MonkeyGenerator中保存着多个线程共享的信息
   * Thread Safety: 没有对于当前ADT线程不安全的操作。
   * 但是jump2Ladder是一个线程不安全的操作，这一步与选择梯子应该作为一个原子操作使用。
   */

  public Logger logger = Logger.getLogger(Monkey.class.getName());
  public List<CrossriverStrategy> crossriverStrategies ;

  private int id;
  private int vel;
  private MonkeyState state;

  private MonkeyGenerator generator;

  public Monkey(MonkeyGenerator generator, List<CrossriverStrategy> strategies, int id, int vel, String direction, int borntime) {
    this.generator = generator;
    this.crossriverStrategies = strategies;
    if(crossriverStrategies==null) {
      crossriverStrategies =
          new ArrayList<>(Arrays.asList(
              new PushspeedStrategy(generator),
              new SimpleStrategy(generator)
          ));
    }

    this.id = id;
    this.vel = vel;
    this.state = new MonkeyState(id, direction, borntime);
  }

  public void startCrossRiverThread() {
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    Random random = new Random();
    CrossriverStrategy strategy =
        crossriverStrategies.get(random.nextInt(crossriverStrategies.size()));

    final String jobId = "startCrossRiverThread";
    final Map<String, Future> futureMap = new HashMap<>();
    Future future = service.scheduleAtFixedRate(() -> {

      //  过河线程
      if (state.state == 1) {
        Ladder ladder = strategy.assign(this);
        if (ladder != null) {
          nextStep();
        }
        state.increaseLifeTime(1);
        logger.info(state.toString());
      } else if (state.state == 2) {
        nextStep();
        state.increaseLifeTime(1);
        logger.info(state.toString());
      } else if (state.state == 3) {
        //结束过河线程
        if(generator.crossedCount == generator.N) {
          generator.getInfoOfGame();
        }
        futureMap.get(jobId).cancel(true);
        service.shutdown();
      }


    }, 0, MonkeyGenerator.STEPTIME, TimeUnit.MILLISECONDS);
    futureMap.put(jobId, future);
  }

  public void jump2Ladder(Ladder ladder) {
    generator.ladder2Monkey.get(ladder).add(this);
    if (state.direction.equals("L->R")) {
      state.setLocation(ladder, ladder.getRungL2R(1));
    } else {
      state.setLocation(ladder, ladder.getRungR2L(1));
    }
    logger.info(String.format("猴子%d跳到%d个梯子上 ", id, ladder.getLadderIndex()));
    state.nextState();
  }

  public void nextStep() {
    Ladder ladder = getLadder();
    synchronized (ladder) {
      Map<Ladder, List<Monkey>> ladder2Monkey = generator.ladder2Monkey;
      Ladder curLadder = getLadder();
      List<Monkey> monkeyList = ladder2Monkey.get(curLadder);
      int size = monkeyList.size();
      int index = -1;
      for (int i = 0; i < size; i++) {
        if (monkeyList.get(i) == this) {
          index = i;
          break;
        }
      }
      assert index != -1 : "没有找到指定的猴子";
      Monkey frontMonkey = null;
      if (index != 0) {
        frontMonkey = monkeyList.get(index - 1);
        assert frontMonkey.state.state != 3 : "已经达到状态3的猴子没有被及时删除";
      }

      int nextRungIndex;
      if (getDirection().equals("L->R")) {
        nextRungIndex = curLadder.getRungLength() + 1;
        if (frontMonkey != null) {
          nextRungIndex = Math.min(nextRungIndex, frontMonkey.getRungIndex() - 1);
        }
        nextRungIndex = Math.min(nextRungIndex, getRungIndex() + vel);
        if (nextRungIndex > curLadder.getRungLength()) {
          //  这只猴子已经达到状态3
          state.nextState();
          //需要在Map中将该猴子删除
          monkeyList.remove(index);
        } else {
          state.setLocation(curLadder, curLadder.getRungL2R(nextRungIndex));
        }
      } else {
        nextRungIndex = 0;
        if (frontMonkey != null) {
          nextRungIndex = Math.max(nextRungIndex, frontMonkey.getRungIndex() + 1);
        }
        nextRungIndex = Math.max(nextRungIndex, getRungIndex() - vel);
        if (nextRungIndex <= 0) {
          state.nextState();
          monkeyList.remove(index);
        } else {
          state.setLocation(curLadder, curLadder.getRungL2R(nextRungIndex));
        }
      }
    }
  }

  public String getDirection() {
    return this.state.direction;
  }

  public int getDeadTime() {
    return state.lifetime + state.borntime;
  }

  public int getState() {
    return state.state;
  }

  public int getRungIndex() {
    return this.state.rung.getRungIndex();
  }

  public Ladder getLadder() {
    return this.state.ladder;
  }

  public int getId() {
    return id;
  }

  public int getVel() {
    return vel;
  }

  @Override
  public String toString() {
    return String.format("%nID: %d%nvel: %d%ndir: %s%n", id, vel, getDirection());
  }

  private class MonkeyState {

    /**
     * AF: 一个编号为id，前进方向为direction，状态阶段为state（ladder，rung），
     * 出生时间为borntime，存活时间为liftime的猴子
     * RI:如果当前猴子为state2，要求ladder和rung非空
     * Rep Explosure: 不会泄露
     * Thread Safety：包括修改操作，使用的时候需要注意线程安全
     */

    //猴子id
    int id;
    //猴子方向
    String direction;
    //猴子状态阶段
    int state;
    //猴子所处的梯子 阶段2
    Ladder ladder;
    //猴子所处的踏板 阶段2
    Rung rung;
    //猴子生存的时长
    int lifetime;
    //猴子被创建的时间
    int borntime;

    public MonkeyState(int id, String direction,int borntime) {
      this.id = id;
      this.direction = direction;
      this.state = 1;
      this.borntime = borntime;
      this.lifetime = 0;
    }

    private void checkRep() {
      if (state == 2) {
        assert ladder != null && rung != null : "错误的state2状态";
      }
    }

    public void nextState() {
      this.state += 1;
      if (this.state == 3) {
        synchronized (generator.crossedCountLock) {
          generator.crossedCount += 1;
        }
      }
      checkRep();
    }

    public void setLocation(Ladder ladder, Rung rung) {
      this.ladder = ladder;
      this.rung = rung;
    }

    public void increaseLifeTime(int inctime) {
      if (state == 3) return;
      this.lifetime += inctime;
    }

    @Override
    public String toString() {
      String loc;
      String aim;
      String dir;
      if (direction.equals("L->R")) {
        loc = "左";
        aim = "右";
        dir = "自左向右";
      } else {
        loc = "右";
        aim = "左";
        dir = "自右向左";
      }

      switch (state) {
        case 1:
          return String.format("猴子%d正在%s岸等待，离出生已经%d秒", id, loc, lifetime);
        case 2:
          return String.format("猴子%d正在第%d架梯子的第%d个踏板上，%s行进，离出生已经%d秒"
              , id, ladder.getLadderIndex(), getRungIndex(), dir, lifetime);
        case 3:
          return String.format("猴子%d已从%s岸抵达%s岸，共耗时%d秒", id, loc, aim, lifetime);
        default:
          return "";
      }
    }
  }
}
