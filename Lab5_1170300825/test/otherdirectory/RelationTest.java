package otherdirectory;

import applications.socialnetworkcircle.CentralUser;
import applications.socialnetworkcircle.Friend;
import centralobject.CentralObject;
import org.junit.jupiter.api.Test;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  主要针对hash进行测试
 *  划分等价类：同一个对象，不同对象且值不同，不同对象但是值相同
 */

public class RelationTest {
    @Test
    void testHash() {
        CentralUser centralUser = CentralUser.getInstance("Name","M",1);
        Friend friend = Friend.getInstance("friend","M",13);
        Relation<CentralObject, Friend> relation1 = new Relation<>(centralUser,friend,1);
        Relation<CentralObject, Friend> relation2 = new Relation<>(centralUser,friend,1);
        System.out.println(relation1.hashCode()==relation2.hashCode());
    }
    @Test
    void testHash2() {
        CentralUser centralUser =  CentralUser.getInstance("Name","M",1);
        Friend friend = Friend.getInstance("friend","M",13);
        Relation<CentralObject, Friend> relation1 = new Relation<>(centralUser,friend,1);
        Relation<CentralObject, Friend> relation2 = new Relation<>(centralUser,friend,2);
        System.out.println(relation1.hashCode()==relation2.hashCode());
    }

    @Test
    void testHash3() {
        CentralUser centralUser =  CentralUser.getInstance("Name","M",1);
        Friend friend = Friend.getInstance("friend","M",13);
        Friend friend2 = Friend.getInstance("friend","M",13);
        Relation<CentralObject, Friend> relation1 = new Relation<>(centralUser,friend,1);
        Relation<CentralObject, Friend> relation2 = new Relation<>(centralUser,friend2,1);
        System.out.println(relation1.equals(relation2));
    }
}
