package applications.AtomStructure;

import applications.AtomStructure.memento.ElectronTransitMemento;
import circularOrbit.ConcreteCircularOrbit;
import otherDirectory.GraphicsPainter;
import otherDirectory.MyExp;
import track.Track;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AtomCircularOrbit extends ConcreteCircularOrbit<AtomCore,Electron> {
    private String elementName;

    public AtomCircularOrbit(String elementName) {
        this.elementName = elementName;
    }

    /**
     * 根据备忘录进行历史记录的回退
     * @param mementoList 所有操作历史
     */
    public void reback(List<ElectronTransitMemento> mementoList) {
//        首先对原来的关系进行复制 以便恢复
        Map<Track,List<Electron>> copyElectronMap = new HashMap<>();
        for(Track track:this.physicalObjectMap.keySet()) {
            copyElectronMap.put(track,new ArrayList<>());
            for(Electron electron:physicalObjectMap.get(track)) {
                copyElectronMap.get(track).add(electron);
            }
        }
        int len = mementoList.size();
        for(int i=len-1;i>=0;i--) {
            ElectronTransitMemento memento = mementoList.get(i);
            MyExp.assertTrue(getTrackForPO(memento.getElectron()).equals(memento.getToTrack()),
                    "操作历史与实际情况不符");
            transit(memento.getElectron(),memento.getElectron(),memento.getFromTrack());
        }
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
                final int padding = 80;
                int trasz  = physicalObjectMap.size();
                int[] trackRadius = new int[trasz];
                for(int i=0;i<trasz;i++) {
                    trackRadius[i] = (pnLength-padding)/(2*trasz)*(i+1);
                }

                // 必须先调用父类的paint方法
                super.paint(graphics);
                // 用画笔Graphics，在画板JPanel上画一个小人
                int centerX = pnLength/2,centerY = pnLength/2;
                int centerObjRadius = 20;

                graphics.setColor(Color.red);

                GraphicsPainter ctpainter = new GraphicsPainter();
                ctpainter.setX(centerX); ctpainter.setY(centerY);
                ctpainter.setRadius(30);
                ctpainter.setOvalColor(Color.red);
                centralObject.drawGraphics(graphics,ctpainter);

                List<Track> trackList = physicalObjectMap.keySet().stream()
                        .sorted().collect(Collectors.toList());
                for(int idx = 1;idx<=trasz;idx++) {
                    Track track = trackList.get(idx-1);
                    int tr = trackRadius[idx-1];
                    int width = 2*tr,height = 2*tr;
                    graphics.drawOval(centerX-tr,centerY-tr,
                            width,height);

                    List<Electron> electrons = physicalObjectMap.get(track);
                    int sz = physicalObjectMap.get(track).size();
                    for(int i=0;i<sz;i++) {
                        GraphicsPainter painter = new GraphicsPainter();
                        double theta = i*(2*Math.PI/(sz));
                        int cx = (int)(tr*Math.cos(theta))+centerX,cy = (int)(tr*Math.sin(theta))+centerY;
                        painter.setX(cx); painter.setY(cy);
                        painter.setRadius(2);
                        electrons.get(i).drawGraphics(graphics,painter);
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
