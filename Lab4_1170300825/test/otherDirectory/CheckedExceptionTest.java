package otherDirectory;

import org.junit.jupiter.api.Test;
import otherDirectory.exception.CheckedException;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  测试asserTrue函数
 */

public class CheckedExceptionTest {
    @Test
    void testCheck() {
        try {
            CheckedException.assertTrue(false, "1233");
        } catch(CheckedException e) {
            System.out.println(e);
        }
    }
}
