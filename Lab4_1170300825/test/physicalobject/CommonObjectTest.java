package physicalobject;

import applications.SocialNetworkCircle.Friend;
import org.junit.jupiter.api.Test;
import otherDirectory.GraphicsPainter;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  简单测试
 */

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
    @Test
    void drawTest() {
        Friend friend = Friend.getInstance("ll0","F",11);
        JFrame frame = new JFrame();
        frame.setSize(800,800);
        frame.getContentPane().add(new JPanel(){
            @Override
            public void paint(Graphics g) {
                GraphicsPainter ctpainter = new GraphicsPainter();
                ctpainter.setX(400); ctpainter.setY(400);
                ctpainter.setRadius(20);
                ctpainter.setOvalColor(Color.red);
//                centralObject.drawGraphics(graphics,ctpainter);
                friend.drawGraphics(g,ctpainter);
            }
        });
        frame.setVisible(true);
    }
}
