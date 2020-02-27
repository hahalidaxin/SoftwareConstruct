package apis;

import apis.gui.LoggerFrame;
import circularorbit.CircularOrbit;
import javax.swing.JFrame;

public class CircularObjectHelper {

  /**
   * .
   * 可视化函数
   *
   * @param c 进行可视化的轨道系统
   */
  public static void visualize(CircularOrbit c) {
    JFrame frame = new JFrame();
    frame.setSize(800, 800);
    frame.getContentPane().add(c.visualizeContentPanel());
    frame.setVisible(true);
  }

  /**
   * .
   * 主函数
   *
   * @param args 程序运行参数
   */
  public static void main(String[] args) {
    LoggerFrame frame = new LoggerFrame();
    frame.setSize(750, 800);
    frame.setVisible(true);
  }
}
