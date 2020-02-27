package applications.AtomStructure;

import centralObject.CentralObject;

public class AtomCore extends CentralObject {

    public static AtomCore getInstance() {
        return new AtomCore();
    }

    public AtomCore() {
        super("AtomCore");
    }
}
