package applications.TrackGame;

import centralObject.CentralObject;
import circularOrbit.ConcreteCircularOrbit;
import circularOrbit.ConcreteCircularOrbitBuilder;

public class TrackCircularOrbitBuilder extends ConcreteCircularOrbitBuilder<CentralObject,Runner> {

    @Override
    public ConcreteCircularOrbit<CentralObject, Runner> getConcreteCircularOrbit() {
        this.concreteCircularOrbit = new TrackCircularOrbit(centralUser,physicalObjMap,relOfCentralObjs,relfOf2TraObjs);
        return this.concreteCircularOrbit;
    }

}
