package applications.trackgame;

import org.junit.jupiter.api.Test;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  简单测试
 */

public class RunnerTest {
    @Test
    void test1() {
        Runner runner1 = Runner.getInstance("23",12,12,33.4,"AFK");
        Runner runner2 = Runner.getInstance("23",12,12,33.4,"AFK");
        runner1.getBestScore();
        runner1.getAge();
        runner1.getId();
        runner1.getCountry();
        if(runner1.equalsObject(runner2)) {
            System.out.println("yes");
        }
    }
}
