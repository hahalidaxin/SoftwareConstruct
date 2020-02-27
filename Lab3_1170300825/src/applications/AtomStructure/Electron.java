package applications.AtomStructure;

import physicalObject.PhysicalObject;

public class Electron extends PhysicalObject {

    public static Electron getInstance() {
        return new Electron();
    }
    public static Electron getInstance(String obName) {
        return new Electron(obName);
    }

    public Electron(String obName) {
        super(obName);
    }

    public Electron() {
        super("e");
    }

    @Override
    public boolean equalsObject(Object obj) {
        return super.equalsObject(obj);
    }
}
