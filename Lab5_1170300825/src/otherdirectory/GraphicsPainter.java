package otherdirectory;

import java.awt.Color;
import java.awt.Font;

/**
 * .
 * Mutable Object
 */
public class GraphicsPainter {
  int px;
  int py;
  int radius;
  Color ovalColor;
  Color textBackColor;
  Font ptFont;

  /**
   * .
   * 构造方法
   */
  public GraphicsPainter() {
    px = 0;
    py = 0;
    radius = 0;
    ovalColor = Color.black;
    textBackColor = Color.black;
    ptFont = null;
  }

  public int getPx() {
    return px;
  }

  public void setPx(int px) {
    this.px = px;
  }

  public int getPy() {
    return py;
  }

  public void setPy(int py) {
    this.py = py;
  }

  public int getRadius() {
    return radius;
  }

  public void setRadius(int radius) {
    this.radius = radius;
  }

  public Color getOvalColor() {
    return ovalColor;
  }

  public void setOvalColor(Color ovalColor) {
    this.ovalColor = ovalColor;
  }

  public Color getTextBackColor() {
    return textBackColor;
  }

  public void setTextBackColor(Color textBackColor) {
    this.textBackColor = textBackColor;
  }

  public Font getPtFont() {
    return ptFont;
  }

  public void setPtFont(Font ptFont) {
    this.ptFont = ptFont;
  }
}
