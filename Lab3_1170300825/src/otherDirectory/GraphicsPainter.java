package otherDirectory;

import java.awt.*;

public class GraphicsPainter {
    int x,y,radius;
    Color ovalColor;
    Color textBackColor;
    Font ptFont;

    public GraphicsPainter() {
        x = 0; y = 0; radius = 0;
        ovalColor = Color.black;
        textBackColor = Color.black;
        ptFont = null;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
