package centralObject;

import otherDirectory.GraphicsPainter;
import otherDirectory.Position;

import java.awt.*;

public abstract class CommonObject {
    protected String obName;
    protected Position pos;

    public CommonObject(String obName) {
        this.obName = obName;
    }

    public String getObName() {
        return obName;
    }

    public Position getPos() {
        return pos;
    }

    public void drawGraphics(Graphics g, GraphicsPainter painter) {
        int x = painter.getX(),y = painter.getY();
        int radius = painter.getRadius();
//        g.drawOval(x-radius,y-radius,2*radius,2*radius);
        g.setColor(painter.getOvalColor());
        g.fillOval(x-radius,y-radius,2*radius,2*radius);//画圆块
        g.setColor(painter.getTextBackColor());
        if(painter.getPtFont()==null) {
            g.setFont(null);
            g.drawString(obName, x - radius - 5, y - radius - 5);
        }
        else {
            g.setFont(painter.getPtFont());
            g.drawString(obName, x - radius - 5, y - radius - 5);
        }
        this.pos = new Position(x,y);
    }

    @Override
    public int hashCode() {
        return obName.hashCode()*31;
    }

    @Override
    public String toString() {
        return obName;
    }

}
