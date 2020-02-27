package applications.AtomStructure.memento;

import applications.AtomStructure.Electron;
import org.junit.jupiter.api.Test;
import track.Track;

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
        Track track1 = Track.getInstance(1);
        Track track2 = Track.getInstance(2);
        Track track3 = Track.getInstance(3);

        careTaker.addMemento(Electron.getInstance(),track1,track2);
        careTaker.addMemento(Electron.getInstance(),track2,track3);
        careTaker.addMemento(Electron.getInstance(),track3,track1);
        careTaker.addMemento(Electron.getInstance(),track3,track2);
        careTaker.getAllHistory().forEach(System.out::println);

        careTaker.rebackMemento(1).stream()
        .forEach(x->System.out.println(x.getElectron().getObName()+" "+x.getFromTrack().getRadius()+" "+x.getToTrack().getRadius()));
    }
}
