package otherdirectory;

import org.junit.jupiter.api.Test;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  简单测试
 */

public class PositionTest {
    @Test
    void test1() {
        Position pos1 = new Position(0.0,0.0);
        Position pos2 = new Position(0.0,0.0);
        pos1.setPx(1.0);
        pos1.setPy(2.0);
        double x = pos2.getPx();
        double y = pos2.getPy();
        assert !pos1.equals(pos2):"错误";
    }

    @Test
    void testHash() {
        Position pos1 = new Position(0,0);
        Position pos2 = new Position(0,0);
        Position pos3 = new Position(12,0);
        assert pos1.hashCode()==pos2.hashCode();
        assert pos1.hashCode()!=pos3.hashCode();
    }
}
