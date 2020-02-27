package applications.SocialNetworkCircle;

import circularOrbit.ConcreteCircularOrbit;
import otherDirectory.GraphicsPainter;
import otherDirectory.Position;
import otherDirectory.Relation;
import track.Track;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class SocialNetworkCircularOrbit extends ConcreteCircularOrbit<CentralUser, Friend> {

    private Map<Friend,Double> bfs2SingleSourceDiffustion(Friend source,double stDensity) {

        Queue<Friend> queue = new LinkedBlockingQueue<>();
        Map<Friend, Double> diffusionMap = new HashMap<>();

        queue.offer(source);
        diffusionMap.put(source, stDensity);

        while(!queue.isEmpty()) {
            Friend topPerson = queue.poll();
            double nowDiffusiton = diffusionMap.get(topPerson);
            List<Relation<Friend,Friend>> neighborList = relOf2TraObjs.getOrDefault(topPerson,new ArrayList<>())
                    .stream()
                    .collect(Collectors.toList());
            for(Relation<Friend,Friend> rel:neighborList) if(!diffusionMap.containsKey(rel.getObjv())){
                diffusionMap.put(rel.getObjv(),nowDiffusiton*rel.getWeight());
                queue.offer(rel.getObjv());
            }
        }
        return diffusionMap;
    }

    public Map<Friend,Double> getInfoDiffusion() {
        Map<Friend,Double> diffusion = new HashMap<>();

        for(Relation<CentralUser,Friend> rel:relOfCobj2TraObj) {
            Friend st = rel.getObjv();
            double stDensity = rel.getWeight();
            diffusion.put(st,bfs2SingleSourceDiffustion(st,stDensity).values().stream()
                .reduce((acc,item)-> {  acc+=item; return acc; }).get());
        }
        return diffusion;
    }

    @Override
    public int getLogicalDistance(Friend e1, Friend e2) {
        if(e1==e2) return 1;
        return super.getLogicalDistance(e1, e2);
    }


    private void bfs2GetPhysicalMap(List<Friend> sources) {
        physicalObjectMap = new HashMap<>();
        Queue<Friend> queue = new LinkedBlockingQueue<>();
        List<Track> trackList = new ArrayList<>();
        trackList.add(Track.getInstance(1));
        Map<Friend,Integer> distantMap = new HashMap<>();
        physicalObjectMap.put(trackList.get(0),new ArrayList<>());
        for(Friend st:sources) {
            queue.offer(st);
            distantMap.put(st, 1);
            physicalObjectMap.get(trackList.get(0)).add(st);
        }
        int maxTrackRadius = 1;

        while(!queue.isEmpty()) {
            Friend topPerson = queue.poll();
            int nowDis=distantMap.get(topPerson);
            List<Friend> neighborList = relOf2TraObjs.getOrDefault(topPerson,new ArrayList<>())
                    .stream()
                    .map(Relation::getObjv).collect(Collectors.toList());
            for(Friend ps:neighborList) if(!distantMap.containsKey(ps)) {
                distantMap.put(ps,nowDis+1);
                if(nowDis==maxTrackRadius) {
                    trackList.add(Track.getInstance(++maxTrackRadius));
                    physicalObjectMap.put(trackList.get(maxTrackRadius-1),new ArrayList<>());
                }
                physicalObjectMap.get(trackList.get(nowDis)).add(ps);
                queue.offer(ps);
            }
        }
    }
    private void adjustFriendLocation() {
        List<Friend> firstTrackFriends = relOfCobj2TraObj.stream()
                .map(x->x.getObjv()).collect(Collectors.toList());
        bfs2GetPhysicalMap(firstTrackFriends);
        List<Friend> rmFriendList = new ArrayList<>();
        Set<Friend> existFriendSet = relOf2TraObjs.values().stream()
                .map(x->new HashSet(x))
                .reduce(new HashSet<Friend>(),(acc,item)->{
                    acc.addAll(item);
                    return acc;
                });
        for(Friend friend:rmFriendList) {
            relOf2TraObjs.remove(friend);
        }
//        firstTrackFriends.forEach(x-> System.out.println(x.getObName()));
//        Map<Friend,Integer> distantMap = getSingleSourceDistances(firstTrackFriends);
////        List<Track> trackList = physicalObjectMap.keySet().stream().sorted().collect(Collectors.toList());
//
//        Map<Track,List<Friend>> copyPysicalObjectMap = new HashMap<>();
//        for(Track track:physicalObjectMap.keySet()) {
//            copyPysicalObjectMap.put(track,new ArrayList<>());
//            for(Friend friend:physicalObjectMap.get(track)) {
//                copyPysicalObjectMap.get(track).add(friend);
//            }
//        }
//
//        for(Track track:copyPysicalObjectMap.keySet()) {
//            Iterator<Friend> iterator = copyPysicalObjectMap.get(track).iterator();
//            while(iterator.hasNext()) {
//                Friend friend = iterator.next();
//                if(!distantMap.containsKey(friend)) {
//                    physicalObjectMap.get(track).remove(friend);
//                } else {
//                    int distant = distantMap.get(friend);
//                    int i = physicalObjectMap.size()+1;
//                    while(i<distant) {
//                        Track newTrack = Track.getInstance(i++);
//                        physicalObjectMap.put(newTrack,new ArrayList<>());
//                        addTrack(newTrack);
//                    }
//                    if(track.getRadius()!=(double)distant) {
//                        physicalObjectMap.get(track).remove(friend);
//                        physicalObjectMap.get(Track.getInstance(distant)).add(friend);
//                    }
//                }
//            }
//        }
    }

    @Override
    public void removePhysicalObject(Friend oldObj) {
        super.removePhysicalObject(oldObj);
        adjustFriendLocation();
    }

    public boolean extendRelationOfCentralObj2TrackObj(CentralUser co, Friend po, double weight) {
        boolean ans = super.addRelationOfCentralObj2TrackObj(co, po, weight);
        adjustFriendLocation();
        return ans;
    }

    public boolean curtailRelationOfCentralObjs2TraObj(CentralUser co, Friend po) {
        boolean ans = super.removeRelationOfCentralObjs2TraObj(co, po);
        adjustFriendLocation();
        return ans;
    }

    public boolean extendRelationOf2TrackObs(Friend po1, Friend po2, double weight) {
        boolean ans = super.addRelationOf2TrackObs(po1, po2, weight);
        adjustFriendLocation();
        return ans;
    }

    public boolean curtailRelationOf2TrackObs(Friend po1, Friend po2) {
        boolean ans = super.removeRelationOf2TrackObs(po1, po2);
        adjustFriendLocation();
        return ans;
    }

    @Override
    public boolean checkOribitAvailable() {
        List<Friend> firstTrackFriends = relOfCobj2TraObj.stream()
                .map(x->x.getObjv()).collect(Collectors.toList());
        Map<Friend,Integer> distantMap = getSingleSourceDistances(firstTrackFriends);
        for(List<Friend> friendList:physicalObjectMap.values()) {
            for(Friend friend:friendList)
                if(!distantMap.containsKey(friend)) {
                    return false;
                }
        }
        return true;
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

                GraphicsPainter ctpainter = new GraphicsPainter();
                ctpainter.setX(centerX); ctpainter.setY(centerY);
                ctpainter.setRadius(centerObjRadius);
                ctpainter.setOvalColor(Color.red);
                centralObject.drawGraphics(graphics,ctpainter);

                List<Track> trackList = physicalObjectMap.keySet().stream()
                        .sorted().collect(Collectors.toList());
                for(int idx=1;idx<=trasz;idx++) {
                    Track track = trackList.get(idx-1);
                    int tr = trackRadius[idx-1];
                    int width = 2*tr,height = 2*tr;
                    graphics.drawOval(centerX-tr,centerY-tr,
                            width,height);

                    List<Friend> friends= physicalObjectMap.get(track);
                    int sz = physicalObjectMap.get(track).size();
                    for(int i=0;i<sz;i++) {
                        GraphicsPainter painter = new GraphicsPainter();
                        double theta = i*(2*Math.PI/(sz));
                        int cx = (int)(tr*Math.cos(theta))+centerX,cy = (int)(tr*Math.sin(theta))+centerY;
                        painter.setX(cx); painter.setY(cy);
                        painter.setRadius(5);
                        painter.setPtFont(new Font("TimesRoman",Font.PLAIN,10));
                        friends.get(i).drawGraphics(graphics,painter);
                    }
                }
                for(Relation<CentralUser,Friend> rel:relOfCobj2TraObj) {
                    Position pos1 = rel.getObju().getPos();
                    Position pos2 = rel.getObjv().getPos();
                    int x1 = (int)pos1.getX();
                    int x2 = (int)pos2.getX();
                    int y1 = (int) pos1.getY();
                    int y2 = (int) pos2.getY();
                    graphics.setColor(Color.red);
                    graphics.drawLine(x1,y1,x2,y2);
                    graphics.drawString(Double.toString(rel.getWeight()),(x1+x2)/2,(y1+y2)/2);
                    graphics.setColor(Color.black);
                }
                for(Map.Entry<Friend,List<Relation<Friend,Friend>>> entry:relOf2TraObjs.entrySet()) {
                    for(Relation<Friend,Friend> rel:entry.getValue()) {
                        Position pos1 = rel.getObju().getPos();
                        Position pos2 = rel.getObjv().getPos();
                        int x1 = (int)pos1.getX();
                        int x2 = (int)pos2.getX();
                        int y1 = (int) pos1.getY();
                        int y2 = (int) pos2.getY();
                        graphics.drawLine(x1,y1,x2,y2);
                        graphics.drawString(Double.toString(rel.getWeight()),(x1+x2)/2,(y1+y2)/2);
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

    public List<String> getFirstTrackFriends() {
        return relOfCobj2TraObj.stream()
                .map(rel->rel.getObjv().getObName()).collect(Collectors.toList());
    }

    public boolean isCenterUser(String obName) {
        return this.centralObject.getObName().equals(obName);
    }
    public boolean isFriend(String obName) {
        Iterator<Friend> ite = this.iterator();
        while(ite.hasNext()) {
            if(ite.next().getObName().equals(obName)) {
                return true;
            }
        }
        return false;
    }
    public CentralUser getCentralUser() {
        return this.centralObject;
    }
    public List<String> getSurroundings(Friend friend) {
        return relOf2TraObjs.get(friend).stream()
                .map(rel->rel.getObjv().getObName()).collect(Collectors.toList());
    }
}
