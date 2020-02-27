package APIs;

import circularOrbit.CircularOrbit;
import otherDirectory.Difference;

public class CircularOrbitAPIs<L,E> {
    public double getObjectDistributionEntropy(CircularOrbit<L,E> c) {
        return c.getObjectDistributionEntropy();
    }
    public int getLogicalDistance(CircularOrbit<L,E> c,E e1,E e2) {
        return c.getLogicalDistance(e1,e2);
    }
//    public <E extends PhysicalObject> double getPhysicalDistance(CircularOrbit<L,E> c, E e1, E e2) {
//        return c.getPhysicalDistance(e1,e2);
//    }
    public Difference getDifference(CircularOrbit<L,E> c1, CircularOrbit<L,E> c2) {
        return c1.getDifference(c2);
    }
    public boolean checkOrbitAvailable(CircularOrbit<L,E> c) {
        return c.checkOribitAvailable();
    }
}
