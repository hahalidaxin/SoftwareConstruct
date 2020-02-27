package debug;

import org.junit.jupiter.api.Test;

/**
 * Testing Strategy
 *  划分等价类：单行注释// ，多行注释/*，两者均有
 */

public class RemoveCommentsTest {
    @Test
    void test1() {
        String[] source = {"/*Test program */", "int main()", "{ ", "  // variable declaration ", "int a, b, c;", "/* This is a test", "   multiline  ", "   comment for ", "   testing */", "a = b + c;", "}"};
        new Solution().removeComments(source).forEach(System.out::println);
    }

    @Test
    void test2() {
        String[] source = {"a/*comment", "line", "more_comment*/b"};
        new Solution().removeComments(source).forEach(System.out::println);
    }

    @Test
    void test3() {
        String[] source = { "int main()", "{ ", "  // variable declaration ", "int a, b, c;", "a = b + c;", "}"};
        new Solution().removeComments(source).forEach(System.out::println);
    }

}