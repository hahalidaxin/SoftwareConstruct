package applications.SocialNetworkCircle;

import circularOrbit.ConcreteCircularOrbit;
import circularOrbit.ConcreteCircularOrbitBuilder;
import otherDirectory.Relation;
import track.Track;

import java.util.List;
import java.util.Map;

public class SocialNetworkCircularOrbitBuilder extends ConcreteCircularOrbitBuilder<CentralUser,Friend> {
    @Override
    public ConcreteCircularOrbit<CentralUser, Friend> getConcreteCircularOrbit() {
        return super.getConcreteCircularOrbit();
    }

    @Override
    public void createConcreteCircularOrbit(String sysName) {
        this.concreteCircularOrbit = new SocialNetworkCircularOrbit();
    }

    @Override
    public void buildTracks(List<Track> tracks) {
        super.buildTracks(tracks);
    }

    @Override
    public void buildObjects(CentralUser centralObjects, Map<Track, List<Friend>> physicalObjMap) {
        super.buildObjects(centralObjects, physicalObjMap);
    }

    @Override
    public void buildRelation(List<Relation<CentralUser,Friend>> relOfCentralObjs, Map<Friend,List<Relation<Friend,Friend>>> friendMap) {
        relOfCentralObjs.stream().forEach((rel)->{
            concreteCircularOrbit.addRelationOfCentralObj2TrackObj(rel.getObju(),rel.getObjv(),rel.getWeight());
        });
        for(Friend friend:friendMap.keySet()) {
            for(Relation<Friend,Friend> rel:friendMap.get(friend)) {
                concreteCircularOrbit.addRelationOf2TrackObs(friend,rel.getObjv(),rel.getWeight());
            }
        }
    }
}
