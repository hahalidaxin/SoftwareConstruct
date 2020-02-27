package applications.AtomStructure;

import circularOrbit.ConcreteCircularOrbit;
import circularOrbit.ConcreteCircularOrbitBuilder;

public class AtomCircularOrbitBuilder extends ConcreteCircularOrbitBuilder<AtomCore,Electron> {

    @Override
    public ConcreteCircularOrbit<AtomCore, Electron> getConcreteCircularOrbit() {
        this.concreteCircularOrbit = new AtomCircularOrbit(centralUser,physicalObjMap,relOfCentralObjs,relfOf2TraObjs,
                this.sysName);
        return  this.concreteCircularOrbit;
    }

}
