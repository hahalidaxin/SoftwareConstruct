package otherDirectory;


/**
 * Mutable Object
 */
public class Position {
//    AF(x.,y) = 用xy为坐标信息的位置
//    Rep Explosure : 不包含暴露操作

    private double x,y;

    public Position(double x, double y) {
        this.x = x;
        this.y= y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(obj instanceof  Position) {
            Position that = (Position) obj;
            return that.x == this.x && that.y == this.y;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (int)(x*31+y)%107;
    }
}
