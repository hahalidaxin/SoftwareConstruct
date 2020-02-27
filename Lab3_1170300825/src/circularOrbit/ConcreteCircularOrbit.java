package circularOrbit;

import otherDirectory.Difference;
import otherDirectory.MyExp;
import otherDirectory.Relation;
import physicalObject.PhysicalObject;
import track.Track;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class  ConcreteCircularOrbit<L,E> implements  CircularOrbit<L,E> ,Iterable<E> {
    protected L centralObject;
    protected Map<Track,List<E>> physicalObjectMap = new HashMap<>();
    protected List<Relation<L,E>> relOfCobj2TraObj = new ArrayList<>();
    protected Map<E,List<Relation<E,E>>> relOf2TraObjs = new HashMap<>();

    @Override
    public boolean addTrack(Track newTrack) {
        if(physicalObjectMap.containsKey(newTrack)) {
            return false;
        }
        physicalObjectMap.put(newTrack,new ArrayList<>());
        return true;
    }

    @Override
    public boolean removeTrack(Track  rmTrack) {

//        删除轨道上的所有物体
        List<E> poList = new ArrayList<>();
        for(E po:physicalObjectMap.get(rmTrack)) {
            poList.add(po);
        }
        for(E po:poList) {
            removePhysicalObject(po);
        }

        if(!physicalObjectMap.containsKey(rmTrack)) {
            return false;
        }
        physicalObjectMap.remove(rmTrack);
        return true;
    }

    @Override
    public void addCentralObject(L co) {
        this.centralObject = co;
    }

    @Override
    public boolean addPhysicalObj2Track(E po, Track tk) {
        MyExp.assertTrue(physicalObjectMap.containsKey(tk),"轨道系统中不包含目标轨道");

        physicalObjectMap.keySet().stream()
                .filter((x)->physicalObjectMap.get(x).contains(po))
                .forEach((x)->{
                    MyExp.assertTrue(x==tk,"其他轨道已经包含该物体");
                });
        if(physicalObjectMap.get(tk).contains(po)) return false;
        physicalObjectMap.get(tk).add(po);

        if(!relOf2TraObjs.containsKey(po)) {
            relOf2TraObjs.put(po,new ArrayList<>());
        }
        return true;
    }

    @Override
    public boolean addRelationOfCentralObj2TrackObj(L co, E po,double weight) {
        return relOfCobj2TraObj.add(new Relation<>(co,po,weight));
    }

    public boolean removeRelationOfCentralObjs2TraObj(L co, E po) {
        return relOfCobj2TraObj.removeIf(rel->rel.getObjv().equals(po));
    }

    /**
     * 添加一条有向边
     * @param po1
     * @param po2
     * @param weight
     * @return
     */
    @Override
    public boolean addRelationOf2TrackObs(E po1, E po2,double weight) {
        if(!relOf2TraObjs.containsKey(po1)) {
            relOf2TraObjs.put(po1,new ArrayList<>());
        }

        if(relOf2TraObjs.get(po1).contains(new Relation<>(po1,po2,weight))) {
            return false;
        }

        relOf2TraObjs.get(po1).add(new Relation<>(po1,po2,weight));
        return true;
    }

    /**
     * 删除一条有向边
     * @param po1
     * @param po2
     * @return
     */
    public boolean removeRelationOf2TrackObs(E po1,E po2) {
        MyExp.assertTrue(relOf2TraObjs.get(po1).stream()
                .map(x->x.getObjv()).collect(Collectors.toList())
                .contains(po2),"两者之间不存在关系");

        return relOf2TraObjs.get(po1).removeIf(rel->rel.getObjv().equals(po2));
    }


    /**
     *  删除轨道系统中存在的轨道物体 oldObj
     * @param oldObj 需要删除的轨道物体
     */
    @Override
    public void removePhysicalObject(E oldObj) {
        boolean rmFlag = false;
        for(Map.Entry<Track,List<E>> entry:physicalObjectMap.entrySet()) {
            if(entry.getValue().contains(oldObj)) {
                entry.getValue().remove(oldObj);
                rmFlag = true;
                break;
            }
        }

        MyExp.assertTrue(rmFlag,"轨道系统不包含进行 remove 的对象oldObject");

//        在关系表中删除该节点
        relOfCobj2TraObj.removeIf((x)->(x.getObju()==oldObj||x.getObjv()==oldObj));


        if(relOf2TraObjs.containsKey(oldObj)) {
            relOf2TraObjs.remove(oldObj);
        }
        for(E u:relOf2TraObjs.keySet()) {
            relOf2TraObjs.get(u).removeIf((x)->(x.getObjv()==oldObj||x.getObju()==oldObj));
        }
    }

    /**
     * 将 oldObj 从当前所在轨道 迁移到 t
     * 因为PhysicalObject是Immutable的，所以需要新建对象（更改pos）然后放入新的轨道t
     * @param newObj 新对象
     * @param oldObj 旧对象
     * @param t 轨道
     */
    @Override
    public void transit(E oldObj,E newObj,Track t) {
        removePhysicalObject(oldObj);
        addPhysicalObj2Track(newObj,t);
    }

    /**
     * 将 object 从 当前 位置 移动 到新的 sitha 角度 所对应的位置 ：
     * @param oldObject 旧对象
     * @param newObject 新对象
     */
    @Override
    public void move(E oldObject, E newObject) {
        boolean rmFlag = false;
        for(Map.Entry<Track,List<E>> entry:physicalObjectMap.entrySet()) {
            if(entry.getValue().contains(oldObject)) {
                entry.getValue().remove(oldObject);
                entry.getValue().add(newObject);
                rmFlag = true;
                break;
            }
        }
        MyExp.assertTrue(rmFlag,"轨道系统不包含进行 move 的对象oldObject");
    }

    /**
     * 轨道-物体 获得轨道系统的熵值
     * @return 轨道系统的熵值
     */
    @Override
    public double getObjectDistributionEntropy() {
        double totalObjs = physicalObjectMap.values().stream()
                .mapToDouble(List::size).sum();

        double ans = physicalObjectMap.values().stream()
                .mapToDouble(List::size)
                .reduce(0.0,(acc,item)->{
                    double p = item/totalObjs;
                    if(item>0) acc-=p * Math.log(p)/Math.log(2.0);
                    return acc;
                });
        return ans;
    }

    protected Map<E,Integer> getSingleSourceDistances(List<E> sources) {
        sources.stream().forEach(x->MyExp.assertTrue(relOf2TraObjs.containsKey(x),"source"+x.toString()+"不存在"));

        Queue<E> queue = new LinkedBlockingQueue<>();
        Map<E, Integer> distantMap = new HashMap<>();

        for(E source:sources) {
            queue.offer(source);
            distantMap.put(source, 1);
        }
        while(!queue.isEmpty()) {
            E topPerson = queue.poll();
            int nowDis=distantMap.get(topPerson);
            List<E> neighborList = relOf2TraObjs.getOrDefault(topPerson,new ArrayList<>())
                    .stream()
                    .map(Relation::getObjv).collect(Collectors.toList());
            for(E ps:neighborList) if(!distantMap.containsKey(ps)){
                distantMap.put(ps,nowDis+1);
                queue.offer(ps);
            }
        }
        return distantMap;
    }

    /**
     * 获得轨道物体 e1和 e2的 逻辑距离
     * @param e1 轨道物体1
     * @param e2 轨道物体2
     * @return  两者的逻辑距离 如果两者之间无法到达 则返回Integer.MAX_VALUE
     */
    @Override
    public int getLogicalDistance(E e1, E e2) {
        Map<E,Integer> distanceMap = getSingleSourceDistances(Arrays.asList(e1));
        return distanceMap.getOrDefault(e2,Integer.MAX_VALUE);
    }

//    @Override
//    public <E extends PhysicalObject> double getPhysicalDistance(E e1, E e2) {
//        Position pos1 = e1.getPos(), pos2 = e2.getPos();
//        double x1 = pos1.getRho()*Math.cos(pos1.getTheta()),
//                y1 = pos1.getRho()*Math.sin(pos1.getTheta());
//        double x2 = pos2.getRho()*Math.cos(pos2.getTheta()),
//                y2 = pos2.getRho()*Math.sin(pos2.getTheta());
//        return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
//    }

    @Override
    public List<Track> getSortedTrack() {
        return physicalObjectMap.keySet().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public List<E> getPhysicalObjectsOnTrack(Track tk) {
        if(!physicalObjectMap.containsKey(tk)) {
            return new ArrayList<>();
        }
        return Collections.unmodifiableList(physicalObjectMap.get(tk));
    }

    @Override
    public Difference getDifference(CircularOrbit<L, E> that) {
        List<Track> thisSortedTracks = this.getSortedTrack();
        List<Track> thatSortedTracks = that.getSortedTrack();
        Difference diff = new Difference(thisSortedTracks.size() - thatSortedTracks.size());

        if(thisSortedTracks.size()<thatSortedTracks.size()) {
            for(int i=0;i<thatSortedTracks.size()-thisSortedTracks.size();i++) {
                thisSortedTracks.add(null);
            }
        }

        if(thatSortedTracks.size()<thisSortedTracks.size()) {
            for(int i=0;i<thatSortedTracks.size()-thisSortedTracks.size();i++) {
                thatSortedTracks.add(null);
            }
        }

        for(int i=0;i<thisSortedTracks.size();i++) {
            Track thisTrack = thisSortedTracks.get(i);
            Track thatTrack = thatSortedTracks.get(i);
            diff.addTrackSet(this.getPhysicalObjectsOnTrack(thisTrack),
                    that.getPhysicalObjectsOnTrack(thatTrack));
        }

        return diff;
    }

    /**
     * 返回当前轨道系统中po所在的轨道Track
     * @param po 查询轨道物体
     * @return po所在的轨道，如果不在该轨道系统中则返回null
     */
    public Track getTrackForPO(PhysicalObject po) {
        boolean ans = false;
        for(Track track:physicalObjectMap.keySet()) {
            if(physicalObjectMap.get(track).contains(po)) {
                return track;
            }
        }
        return null;
    }


    @Override
    public boolean checkOribitAvailable() {

        return true;
    }

    @Override
    public List<String> getTrackRadiusList() {
        return physicalObjectMap.keySet().stream()
                .map(x->Double.toString(x.getRadius()))
                .sorted().collect(Collectors.toList());
    }

    /**
     * 迭代器实现
     */

    @Override
    public  Iterator<E> iterator() {
        return new MyIterator(physicalObjectMap);
    }

    private class MyIterator<E extends Comparable> implements Iterator<E> {
        private List<E> physicalList;
        private int ite;
        private int size;

        public MyIterator(Map<Track,List<E>> physicalMap) {
            ite = 0;
            size = physicalMap.values().stream()
                    .mapToInt(List::size).sum();
            physicalList = physicalMap.keySet().stream()
                    .sorted()
                    .map(physicalMap::get)
                    .reduce(new ArrayList<>(),(acc,item)->{
                        Collections.sort(item);
                        acc.addAll(item);
                        return acc;
                    });
        }

        @Override
        public boolean hasNext() {
            return ite<size;
        }

        @Override
        public E next() {
            return physicalList.get(ite++);
        }
    }

    @Override
    public void visualize(JPanel panel) {
        MyExp.assertTrue(false,"调用错误，由子应用系统具体实现");
    }

    @Override
    public JPanel visualizeContentPanel() {
        MyExp.assertTrue(false,"调用错误，由子应用系统具体实现");
        return null;
    }
}
