package applications.socialnetworkcircle;

import physicalobject.PhysicalObject;

public class Friend extends PhysicalObject {

  private String gender;
  private int age;

  public Friend(String obName) {
    super(obName);
  }

  /**
   * .
   * 构造方法
   *
   * @param obName 名称
   * @param gender 性别
   * @param age    年龄
   */
  public Friend(String obName, String gender, int age) {
    super(obName);
    this.gender = gender;
    this.age = age;
  }

  public static Friend getInstance(String obName, String gender, int age) {
    return new Friend(obName, gender, age);
  }

  public static Friend getInstance(String obName) {
    return new Friend(obName);
  }

  /**
   * .
   * 判断两个Friend是否值相等
   *
   * @return 两者是否值相等
   */
  @Override
  public boolean equalsObject(Object obj) {
    Friend that = (Friend) obj;
    return this.getObName().equals(that.getObName())
        && this.getAge() == that.getAge()
        && this.getGender().equals(that.getGender());
  }

  @Override
  public String toString() {
    return String.format("<%s, %d,%s>", obName, age, gender);
  }

  public String getGender() {
    return gender;
  }

  public int getAge() {
    return age;
  }
}
