package physicalobject;

import centralobject.CommonObject;

public class PhysicalObject extends CommonObject implements Comparable<PhysicalObject> {

  public PhysicalObject(String obName) {
    super(obName);
  }

  /**
   * .
   * 静态工厂方法
   *
   * @param obName 物体名称
   * @return 轨道物体
   */
  public static PhysicalObject getInstance(String obName) {
    return new PhysicalObject(obName);
  }

  //在电子系统中存在多个电子 对象相同 但是需要通过引用来判别是否相同
  //所以该equals需要在子类中进行覆盖而不在PhysicalObj中进行覆盖

  public boolean equalsObject(Object obj) {
    PhysicalObject that = (PhysicalObject) obj;
    return that.getObName().equals(this.getObName());
  }

  @Override
  public int compareTo(PhysicalObject that) {
    if (this.equals(that)) {
      return 0;
    }
    return this.getObName().compareTo(that.getObName());
  }
}
