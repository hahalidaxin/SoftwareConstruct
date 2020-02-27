package track;


import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class TrackTest {
    /**
     * 测试track的 Hash 性质
     */
    @Test
    public void testTrackHash() {
        HashMap<Track,Integer> testMap = new HashMap<>();
        Track track = Track.getInstance(1.0);
        Track track2 = Track.getInstance(1.0);
        testMap.put(track,1);
        testMap.put(track2,2);
        System.out.println(testMap.get(track));
        System.out.println(testMap.get(track2));
//        MyExp.assertTrue(testMap.containsKey(track),"TrackHash失败");
    }
}
