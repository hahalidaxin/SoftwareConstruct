package applications.AtomStructure;

import applications.AtomStructure.memento.ElectronTransitCareTaker;
import applications.AtomStructure.memento.ElectronTransitMemento;
import org.junit.jupiter.api.Test;
import track.Track;

import java.util.List;

public class AtomCircularOrbitTest {
    @Test
    public void tetsOrbitReback() {
        AtomCircularOrbit circularOrbit = new AtomCircularOrbit("Na");
        ElectronTransitCareTaker careTaker = new ElectronTransitCareTaker();

        Track track1 = Track.getInstance(1);
        Track track2 = Track.getInstance(2);
        Track track3 = Track.getInstance(3);
        Track track4 = Track.getInstance(4);
        circularOrbit.addTrack(track1);
        circularOrbit.addTrack(track2);
        circularOrbit.addTrack(track3);
        circularOrbit.addTrack(track4);
        Electron electron1 = Electron.getInstance("e1");
        Electron electron2 = Electron.getInstance("e2");
        Electron electron3 = Electron.getInstance("e3");

        circularOrbit.addPhysicalObj2Track(electron1,track1);
        circularOrbit.addPhysicalObj2Track(electron2,track2);
        circularOrbit.addPhysicalObj2Track(electron3,track3);

//        0
        circularOrbit.transit(electron1,electron1,track4);
        careTaker.addMemento(electron1,track1,track4);
//       1
        circularOrbit.transit(electron2,electron2,track3);
        careTaker.addMemento(electron2,track2,track3);
//       2
        circularOrbit.transit(electron3,electron3,track1);
        careTaker.addMemento(electron3,track3,track1);
//       3
        circularOrbit.transit(electron2,electron2,track1);
        careTaker.addMemento(electron2,track3,track1);

        List<ElectronTransitMemento> mementoList = careTaker.rebackMemento(3);
//        mementoList.stream()
//                .forEach(x-> System.out.println(x.getElectron().getObName()+" "+x.getFromTrack().getRadius()+" "+x.getToTrack().getRadius()));
//
        circularOrbit.reback(mementoList);

        System.out.println("e1 :: "+circularOrbit.getTrackForPO(electron1).getRadius());
        System.out.println("e2 :: "+circularOrbit.getTrackForPO(electron2).getRadius());
        System.out.println("e3 :: "+circularOrbit.getTrackForPO(electron3).getRadius());
    }
}
