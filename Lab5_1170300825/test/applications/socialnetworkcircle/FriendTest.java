package applications.socialnetworkcircle;

import org.junit.jupiter.api.Test;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  简单测试
 */

public class FriendTest {
//    测试值相等
    @Test
    void testValueEqual() {
        Friend friend = Friend.getInstance("1","M",12);
        Friend friend3 = Friend.getInstance("1","M",12);
        Friend friend1 = Friend.getInstance("2","M",12);
        assert !friend.equalsObject(friend1);
        assert friend3.equalsObject(friend);
    }
}
