package track;


import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  主要针对hash equals进行测试
 *  使用Map测试hash结果，看Map是否能够正常使用
 *  划分等价类：同一个对象，不同对象且值不同，不同对象但是值相同
 */

public class TrackTest {
    /**
     * 测试track的 Hash 性质
     */
    @Test
    public void testTrackHash() {
        HashMap<Track,Integer> testMap = new HashMap<>();
        Track track = TrackFactory.getTrackInstance(1.0);
        Track track2 = TrackFactory.getTrackInstance(1.0);
        testMap.put(track,1);
        testMap.put(track2,2);
        System.out.println(testMap.get(track));
        System.out.println(testMap.get(track2));
//        UncheckedException.assertTrue(testMap.containsKey(track),"TrackHash失败");
    }
}
