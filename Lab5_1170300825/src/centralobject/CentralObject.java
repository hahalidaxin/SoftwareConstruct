package centralobject;

public class CentralObject extends CommonObject {

  public CentralObject(String obName) {
    super(obName);

  }

  /**
   * .
   * 静态工厂方法
   *
   * @param obName 物体名称
   * @return 中心点物体
   */
  public static CentralObject getInstance(String obName) {
    return new CentralObject(obName);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj instanceof CentralObject) {
      CentralObject that = (CentralObject) obj;
      return that.getObName().equals(this.getObName())
          && that.getPos().equals(this.getPos());
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return obName.hashCode() * 31;
  }
}
