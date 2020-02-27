package physicalobject;

import applications.SocialNetworkCircle.Friend;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CommonObjectTest {
    @Test
    void commonObjHashTest() {
        Friend friend = Friend.getInstance("lidaxin");
        Friend friend2 = Friend.getInstance("DavidChen");
        Map<Friend,Integer> map = new HashMap<>();
        map.put(friend,1);
        map.put(friend2,2);
        for(Friend fr:map.keySet()) {
            System.out.println(map.get(fr));
        }
    }
}
