package otherdirectory;


/**
 * .
 * Mutable Object
 */
public class Position {
  //AF(px.,py) = 用xy为坐标信息的位置
  //Rep Explosure : 不包含暴露操作

  private double px;
  private double py;

  private static Position originPos = new Position(0, 0);

  public static Position getOriginInstance() {
    return originPos;
  }

  public Position(double px, double py) {
    this.px = px;
    this.py = py;
  }

  public double getPx() {
    return px;
  }

  public void setPx(double px) {
    this.px = px;
  }

  public double getPy() {
    return py;
  }

  public void setPy(double py) {
    this.py = py;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj instanceof Position) {
      Position that = (Position) obj;
      return that.px == this.px && that.py == this.py;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return (int) (px * 31 + py) % 107;
  }
}
