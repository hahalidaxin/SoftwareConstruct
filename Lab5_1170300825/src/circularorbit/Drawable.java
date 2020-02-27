package circularorbit;

import javax.swing.JFrame;
import otherdirectory.exception.CheckedException;

public interface Drawable {
  void initialize() throws CheckedException;

  void draw(JFrame frame);
}
