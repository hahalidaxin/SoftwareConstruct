package centralObject;

import otherDirectory.GraphicsPainter;
import otherDirectory.Position;
import otherDirectory.exception.UncheckedException;

import java.awt.*;

public abstract class CommonObject {
//    AF(obName,pos)=名称为obName 处于pos位置的物体
//    RI：obName!=null && obName.size>0

    protected String obName;
    protected Position pos;

    public CommonObject(String obName) {
        this.obName = obName;
        this.pos = new Position(0,0);
        checkRep();
    }
    private void checkRep() {
        UncheckedException.assertTrue(obName!=null && obName.length()>0,"不满足obName要求");
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
    public boolean equals(Object obj) {
        return this==obj;
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
