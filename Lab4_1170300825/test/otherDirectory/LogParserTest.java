package otherDirectory;

import org.junit.jupiter.api.Test;
import otherDirectory.logparser.LogParser;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  直接调用构造函数与getFilterLogs测试
 */

public class LogParserTest {
    @Test
    void testLogInit() {
        LogParser logParser = new LogParser();
        System.out.println(logParser.getFilterLogs(it->it.getLogType().equals("ERROR")));
    }

}
