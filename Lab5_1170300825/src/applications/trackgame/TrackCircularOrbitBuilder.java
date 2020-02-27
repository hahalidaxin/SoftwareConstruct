package applications.trackgame;

import centralobject.CentralObject;
import circularorbit.ConcreteCircularOrbit;
import circularorbit.ConcreteCircularOrbitBuilder;

public class TrackCircularOrbitBuilder extends ConcreteCircularOrbitBuilder<CentralObject, Runner> {

  @Override
  public ConcreteCircularOrbit<CentralObject, Runner> getConcreteCircularOrbit() {
    this.concreteCircularOrbit = new TrackCircularOrbit(
        centralUser,
        physicalObjMap,
        relOfCentralObjs,
        relfOf2TraObjs);
    return this.concreteCircularOrbit;
  }

}
