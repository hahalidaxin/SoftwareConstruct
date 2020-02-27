package applications.TrackGame;

import centralObject.CentralObject;
import circularOrbit.ConcreteCircularOrbit;
import otherDirectory.GraphicsPainter;
import track.Track;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class TrackCircularOrbit extends ConcreteCircularOrbit<CentralObject,Runner> {


    public TrackCircularOrbit() {
    }

    @Override
    public boolean checkOribitAvailable() {
        boolean ans = true;
//        无中心点物体
        ans = ans && centralObject==null;
//        跑道数在4-10之间
        int numOfTrack = physicalObjectMap.keySet().size();
        ans = ans && numOfTrack>=4 && numOfTrack<=10;
//        每组人数不超过跑道数
        int numOfRunner = physicalObjectMap.values().stream()
                .mapToInt(List::size).sum();
        ans = ans && numOfRunner<=numOfTrack;
        for(Track tk:physicalObjectMap.keySet()) {
            ans = ans && physicalObjectMap.get(tk).size()<=1;
        }
        return ans;
    }

    @Override
    public JPanel visualizeContentPanel() {
        return new JPanel() {
            //序列号（可省略）
            private static final long serialVersionUID = 1L;

            // 重写paint方法
            @Override
            public void paint(Graphics graphics) {
                final int pnLength = 800;
                final int padding = 100;
                int trasz  = physicalObjectMap.size();
                int[] trackRadius = new int[trasz];
                for(int i=0;i<trasz;i++) {
                    trackRadius[i] = (pnLength-padding)/(2*trasz)*(i+1);
                }
                // 必须先调用父类的paint方法
                super.paint(graphics);
                // 用画笔Graphics，在画板JPanel上画一个小人
                int centerX = pnLength/2,centerY = pnLength/2;

                List<Track> trackList = physicalObjectMap.keySet().stream()
                        .sorted().collect(Collectors.toList());
                for(int idx=1;idx<=trasz;idx++) {
                    Track track = trackList.get(idx-1);
                    int tr = trackRadius[idx-1];
                    int width = 2*tr,height = 2*tr;
                    graphics.drawOval(centerX-tr,centerY-tr,
                            width,height);

                    List<Runner> poList = physicalObjectMap.get(track);
                    int sz = poList.size();
                    for(int i=0;i<sz;i++) {
                        Runner runner = poList.get(i);
                        GraphicsPainter painter = new GraphicsPainter();
                        double theta = i*(2*Math.PI/(sz));
                        int cx = (int)(tr*Math.cos(theta))+centerX,cy = (int)(tr*Math.sin(theta))+centerY;
                        painter.setX(cx); painter.setY(cy);
                        painter.setRadius(10);
                        runner.drawGraphics(graphics,painter);
                    }
                }
            }
        };
    }

    @Override
    public void visualize(JPanel panel) {
        JPanel contentPanel = visualizeContentPanel();
        panel.removeAll();
        contentPanel.setPreferredSize(new Dimension(800,800));
        panel.add(contentPanel);
        panel.validate();
        panel.repaint();
    }
}
