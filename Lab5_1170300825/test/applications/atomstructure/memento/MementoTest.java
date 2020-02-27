package applications.atomstructure.memento;

import applications.atomstructure.Electron;
import org.junit.jupiter.api.Test;
import track.Track;
import track.TrackFactory;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  简单测试
 */

public class MementoTest {
    @Test
    void testReback() {
        ElectronTransitCareTaker careTaker = new ElectronTransitCareTaker();
        Track track1 = TrackFactory.getTrackInstance(1.0);
        Track track2 = TrackFactory.getTrackInstance(2.0);
        Track track3 = TrackFactory.getTrackInstance(3.0);

        careTaker.addMemento(Electron.getInstance(),track1,track2);
        careTaker.addMemento(Electron.getInstance(),track2,track3);
        careTaker.addMemento(Electron.getInstance(),track3,track1);
        careTaker.addMemento(Electron.getInstance(),track3,track2);
        careTaker.getAllHistory().forEach(System.out::println);

        careTaker.rebackMemento(1).stream()
        .forEach(x->System.out.println(x.getElectron().getObName()+" "+x.getFromTrack().getRadius()+" "+x.getToTrack().getRadius()));
    }
}
