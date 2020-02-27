package otherDirectory;

import applications.SocialNetworkCircle.CentralUser;
import applications.SocialNetworkCircle.Friend;
import centralObject.CentralObject;
import org.junit.jupiter.api.Test;

public class RelationTest {
    @Test
    void testHash() {
        CentralUser centralUser = CentralUser.getInstance("Name");
        Friend friend = Friend.getInstance("friend");
        Relation<CentralObject, Friend> relation1 = new Relation<>(centralUser,friend,1);
        Relation<CentralObject, Friend> relation2 = new Relation<>(centralUser,friend,1);
        System.out.println(relation1.hashCode()==relation2.hashCode());
    }
    @Test
    void testHash2() {
        CentralUser centralUser = CentralUser.getInstance("Name");
        Friend friend = Friend.getInstance("friend");
        Relation<CentralObject, Friend> relation1 = new Relation<>(centralUser,friend,1);
        Relation<CentralObject, Friend> relation2 = new Relation<>(centralUser,friend,2);
        System.out.println(relation1.hashCode()==relation2.hashCode());
    }

    @Test
    void testHash3() {
        CentralUser centralUser = CentralUser.getInstance("Name");
        Friend friend = Friend.getInstance("friend");
        Friend friend2 = Friend.getInstance("friend");
        Relation<CentralObject, Friend> relation1 = new Relation<>(centralUser,friend,1);
        Relation<CentralObject, Friend> relation2 = new Relation<>(centralUser,friend2,1);
        System.out.println(relation1.equals(relation2));
    }
}
