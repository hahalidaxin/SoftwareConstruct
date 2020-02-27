package circularOrbit;

import otherDirectory.exception.CheckedException;

import javax.swing.*;

public interface Drawable {
    void initialize() throws CheckedException;
    void draw(JFrame frame);
}
