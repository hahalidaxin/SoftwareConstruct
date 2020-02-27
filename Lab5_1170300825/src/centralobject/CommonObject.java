package centralobject;

import java.awt.Graphics;
import otherdirectory.GraphicsPainter;
import otherdirectory.Position;
import otherdirectory.exception.UncheckedException;

public abstract class CommonObject {
  //AF(obName,pos)=名称为obName 处于pos位置的物体
  //RI：obName!=null && obName.size>0

  protected String obName;
  protected Position pos;

  /**
   * .
   * 构造方法
   * @param obName 物体名称
   */
  public CommonObject(String obName) {
    this.obName = obName;
    this.pos = Position.getOriginInstance();
    checkRep();
  }

  private void checkRep() {
    UncheckedException.assertTrue(obName != null && obName.length() > 0, "不满足obName要求");
  }


  public String getObName() {
    return obName;
  }

  public Position getPos() {
    return pos;
  }

  /**
   * .
   * 将物体绘制在轨道上
   * @param g 重写paint传入的Graphics参数
   * @param painter 绘制信息
   */
  public void drawGraphics(Graphics g, GraphicsPainter painter) {
    int x = painter.getPx();
    int y = painter.getPy();
    int radius = painter.getRadius();
    //g.drawOval(x-radius,y-radius,2*radius,2*radius);
    g.setColor(painter.getOvalColor());
    g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);//画圆块
    g.setColor(painter.getTextBackColor());
    if (painter.getPtFont() == null) {
      g.setFont(null);
      g.drawString(obName, x - radius - 5, y - radius - 5);
    } else {
      g.setFont(painter.getPtFont());
      g.drawString(obName, x - radius - 5, y - radius - 5);
    }
    this.pos = new Position(x, y);
  }

  @Override
  public boolean equals(Object obj) {
    return this == obj;
  }

  @Override
  public int hashCode() {
    return obName.hashCode() * 31;
  }

  @Override
  public String toString() {
    return obName;
  }

}
