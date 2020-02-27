package applications.atomstructure;

import physicalobject.PhysicalObject;


public class Electron extends PhysicalObject {

  public Electron(String obName) {
    super(obName);
  }

  public Electron() {
    super("e");
  }

  public static Electron getInstance() {
    return new Electron();
  }

  public static Electron getInstance(String obName) {
    return new Electron(obName);
  }

  @Override
  public boolean equalsObject(Object obj) {
    return super.equalsObject(obj);
  }
}
