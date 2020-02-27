package applications.AtomStructure.memento;

import applications.AtomStructure.Electron;
import track.Track;

/**
 * Memento - 备忘录
 */
public class ElectronTransitMemento {

    Electron electron;
    Track fromTrack,toTrack;

    public ElectronTransitMemento(Electron electron,Track fromTrack,Track toTrack) {
        this.electron = electron;
        this.fromTrack = fromTrack;
        this.toTrack = toTrack;
    }

    public Electron getElectron() {
        return electron;
    }

    public Track getFromTrack() {
        return fromTrack;
    }

    public Track getToTrack() {
        return toTrack;
    }

    @Override
    public String toString() {
        return String.format("Transit e from %.2f to %.2f",fromTrack.getRadius(),toTrack.getRadius());
    }
}
