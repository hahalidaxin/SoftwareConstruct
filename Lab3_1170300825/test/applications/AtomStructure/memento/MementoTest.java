package applications.AtomStructure.memento;

import applications.AtomStructure.Electron;
import org.junit.jupiter.api.Test;
import track.Track;

public class MementoTest {
    @Test
    void testReback() {
        ElectronTransitCareTaker careTaker = new ElectronTransitCareTaker();
        Track track1 = Track.getInstance(1);
        Track track2 = Track.getInstance(2);
        Track track3 = Track.getInstance(3);

        careTaker.addMemento(Electron.getInstance(),track1,track2);
        careTaker.addMemento(Electron.getInstance(),track2,track3);
        careTaker.addMemento(Electron.getInstance(),track3,track1);
        careTaker.addMemento(Electron.getInstance(),track3,track2);

        careTaker.rebackMemento(1).stream()
        .forEach(x->System.out.println(x.getElectron().getObName()+" "+x.getFromTrack().getRadius()+" "+x.getToTrack().getRadius()));
    }
}
