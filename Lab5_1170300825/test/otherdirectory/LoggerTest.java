package otherdirectory;

import apis.gui.LoggerFrame;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  一个随便测试logger的类
 */

public class LoggerTest {
    private static Logger logger = Logger.getLogger(LoggerTest.class.getName());
    @Test
    void testLog() {
        logger.info("log4j print");
    }

    @Test
    void testLogger() {
        LoggerFrame frame = new LoggerFrame();
        frame.setVisible(true);
    }
 }
