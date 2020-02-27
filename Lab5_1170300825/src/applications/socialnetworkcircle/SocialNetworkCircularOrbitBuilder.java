package applications.socialnetworkcircle;

import circularorbit.ConcreteCircularOrbit;
import circularorbit.ConcreteCircularOrbitBuilder;
import java.util.List;
import java.util.Map;
import otherdirectory.Relation;
import track.Track;

public class SocialNetworkCircularOrbitBuilder
    extends ConcreteCircularOrbitBuilder<CentralUser, Friend> {
  @Override
  public ConcreteCircularOrbit<CentralUser, Friend> getConcreteCircularOrbit() {
    this.concreteCircularOrbit =
        new SocialNetworkCircularOrbit(
            centralUser,
            physicalObjMap,
            relOfCentralObjs, relfOf2TraObjs);
    return this.concreteCircularOrbit;
  }

  @Override
  public void buildObjects(CentralUser centralObjects,
                           Map<Track, List<Friend>> physicalObjMap) {
    super.buildObjects(centralObjects, physicalObjMap);
  }

  @Override
  public void buildRelation(
      List<Relation<CentralUser, Friend>> relOfCentralObjs,
      Map<Friend, List<Relation<Friend, Friend>>> friendMap) {
    super.buildRelation(relOfCentralObjs, friendMap);
  }
}
