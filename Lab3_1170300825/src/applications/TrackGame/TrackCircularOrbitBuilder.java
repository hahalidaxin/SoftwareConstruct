package applications.TrackGame;

import centralObject.CentralObject;
import circularOrbit.ConcreteCircularOrbit;
import circularOrbit.ConcreteCircularOrbitBuilder;

public class TrackCircularOrbitBuilder extends ConcreteCircularOrbitBuilder<CentralObject,Runner> {

    @Override
    public ConcreteCircularOrbit<CentralObject, Runner> getConcreteCircularOrbit() {
        return this.concreteCircularOrbit;
    }

    @Override
    public void createConcreteCircularOrbit(String sysName) {
        this.concreteCircularOrbit = new TrackCircularOrbit();
    }

}
