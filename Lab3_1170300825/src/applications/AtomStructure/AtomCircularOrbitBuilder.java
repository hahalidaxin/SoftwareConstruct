package applications.AtomStructure;

import circularOrbit.ConcreteCircularOrbit;
import circularOrbit.ConcreteCircularOrbitBuilder;

public class AtomCircularOrbitBuilder extends ConcreteCircularOrbitBuilder<AtomCore,Electron> {

    @Override
    public ConcreteCircularOrbit<AtomCore, Electron> getConcreteCircularOrbit() {
        return super.getConcreteCircularOrbit();
    }

    @Override
    public void createConcreteCircularOrbit(String sysName) {
        this.concreteCircularOrbit = new AtomCircularOrbit(sysName);
    }

}
