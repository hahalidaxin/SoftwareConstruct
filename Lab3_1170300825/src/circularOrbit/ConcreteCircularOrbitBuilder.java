package circularOrbit;

import applications.SocialNetworkCircle.CentralUser;
import applications.SocialNetworkCircle.Friend;
import otherDirectory.Relation;
import track.Track;

import java.util.List;
import java.util.Map;

public abstract class ConcreteCircularOrbitBuilder<L,E> {
    protected ConcreteCircularOrbit<L,E> concreteCircularOrbit;

    public ConcreteCircularOrbit<L,E> getConcreteCircularOrbit(){
        return concreteCircularOrbit;
    }

    /**
     * 创建对象
     */
    public abstract void createConcreteCircularOrbit(String sysName) ;


    /**
     * 构造步骤：设置轨道系统中的轨道
     * @param tracks 需要添加的所有轨道
     */
    public void buildTracks(List<Track> tracks) {
        for(Track track:tracks){
            concreteCircularOrbit.addTrack(track);
        }
    }

    /**
     * 构造步骤：向轨道系统中添加中心物体和轨道物体
     */
    public void buildObjects(L centralObjects, Map<Track,List<E>> physicalObjMap) {
        concreteCircularOrbit.addCentralObject(centralObjects);
        for(Map.Entry<Track, List<E>> entry:physicalObjMap.entrySet()) {
            for(E po:entry.getValue()) {
                concreteCircularOrbit.addPhysicalObj2Track(po, entry.getKey());
            }
        }
    }

    public void buildRelation(List<Relation<CentralUser, Friend>> relOfCentralObjs, Map<Friend,List<Relation<Friend,Friend>>> friendMap){
//        空实现
    }
}