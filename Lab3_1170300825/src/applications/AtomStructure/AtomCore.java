package applications.AtomStructure;

import centralObject.CentralObject;
import otherDirectory.Position;

public class AtomCore extends CentralObject {

    public static AtomCore getInstance() {
        return new AtomCore();
    }

    public AtomCore(String obName, Position pos) {
        super(obName);
    }

    public AtomCore() {
        super("AtomCore");
    }
}
