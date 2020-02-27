package otherDirectory;

import org.junit.jupiter.api.Test;

import java.awt.*;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  简单测试
 */

public class GraphicPainterTest {
    @Test
    void test1() {
        GraphicsPainter painter = new GraphicsPainter();
        painter.setX(0);
        painter.setY(0);
        painter.setPtFont(new Font("Times New Roman",Font.ITALIC,18));
        painter.setRadius(1);
        painter.setOvalColor(Color.BLACK);
        painter.setTextBackColor(Color.white);
    }
}
