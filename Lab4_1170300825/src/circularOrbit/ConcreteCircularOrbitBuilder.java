package circularOrbit;

import otherDirectory.Relation;
import track.Track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ConcreteCircularOrbitBuilder<L,E> {
    protected ConcreteCircularOrbit<L,E> concreteCircularOrbit;
    protected String sysName;
    protected L centralUser;
    protected List<Track> tracks = new ArrayList<>();
    protected Map<Track,List<E>> physicalObjMap = new HashMap<>();
    protected List<Relation<L, E>> relOfCentralObjs = new ArrayList<>();
    protected Map<E,List<Relation<E,E>>> relfOf2TraObjs = new HashMap<>();

    public abstract ConcreteCircularOrbit<L,E> getConcreteCircularOrbit();

    /**
     * 创建对象
     */
    public void createConcreteCircularOrbit(String sysName) {
        this.sysName = sysName;
    };


    /**
     * 构造步骤：设置轨道系统中的轨道
     * @param trackList 需要添加的所有轨道
     */
    public void buildTracks(List<Track> trackList) {
//        for(Track track:tracks){
//            concreteCircularOrbit.addTrack(track);
//        }
        this.tracks = new ArrayList<>();
        trackList.forEach(x->this.tracks.add(x));
    }

    /**
     * 构造步骤：向轨道系统中添加中心物体和轨道物体
     */
    public void buildObjects(L centralObjects, Map<Track,List<E>> physicalObjMap) {
//        concreteCircularOrbit.addCentralObject(centralObjects);
//        for(Map.Entry<Track, List<E>> entry:physicalObjMap.entrySet()) {
//            for(E po:entry.getValue()) {
//                concreteCircularOrbit.addPhysicalObj2Track(po, entry.getKey());
//            }
//        }
        this.centralUser = centralObjects;
        this.physicalObjMap = new HashMap<>();
        for(Map.Entry<Track,List<E>> entry:physicalObjMap.entrySet()) {
            Track tk = entry.getKey();
            this.physicalObjMap.put(tk,new ArrayList<>());
            entry.getValue().forEach(x->this.physicalObjMap.get(tk).add(x));
        }
    }

    public void buildRelation(List<Relation<L, E>> relOfCentralObjs, Map<E,List<Relation<E,E>>> friendMap){
//        空实现
        this.relOfCentralObjs = new ArrayList<>();
        relOfCentralObjs.forEach(x->this.relOfCentralObjs.add(x));
        this.relfOf2TraObjs = new HashMap<>();
        friendMap.forEach((x,y)->{
            this.relfOf2TraObjs.put(x,new ArrayList<>());
            y.forEach(rel->this.relfOf2TraObjs.get(x).add(rel));
        });
    }
}