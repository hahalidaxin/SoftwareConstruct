package otherDirectory;

public class Position {
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
        Position that = (Position) obj;
        return that.x==this.x && that.y==this.y;
    }

    @Override
    public int hashCode() {
        return (int)(x*y)%107;
    }
}
