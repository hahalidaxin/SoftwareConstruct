package applications.atomstructure;

import circularorbit.ConcreteCircularOrbit;
import circularorbit.ConcreteCircularOrbitBuilder;

public class AtomCircularOrbitBuilder extends ConcreteCircularOrbitBuilder<AtomCore, Electron> {

  @Override
  public ConcreteCircularOrbit<AtomCore, Electron> getConcreteCircularOrbit() {
    this.concreteCircularOrbit = new AtomCircularOrbit(centralUser, physicalObjMap,
        relOfCentralObjs, relfOf2TraObjs, this.sysName);
    return this.concreteCircularOrbit;
  }

}
