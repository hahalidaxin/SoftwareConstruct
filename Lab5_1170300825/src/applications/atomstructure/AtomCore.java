package applications.atomstructure;

import centralobject.CentralObject;

public class AtomCore extends CentralObject {

  public AtomCore() {
    super("AtomCore");
  }

  public static AtomCore getInstance() {
    return new AtomCore();
  }
}
