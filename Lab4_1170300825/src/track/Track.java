package track;


import otherDirectory.exception.UncheckedException;

/**
 * Immutable Object
 */
public class Track implements Comparable<Track>{
//    AF(radius) = 一条以radius为半径的圆形轨道
//    RI：radius>=0
//    Rep Explosure : 没有暴露对象的方法


    double radius;

    public static Track getInstance(double radius) {
        return new Track(radius);
    }
    public Track(double radius) {
        this.radius = radius;
        checkRep();
    }
    private void checkRep() {
        UncheckedException.assertTrue(radius>=0,"Track Radiu 不满足>=0");
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public int compareTo(Track that) {
        return Double.compare(this.radius,that.radius);
    }

    @Override
    public int hashCode() {
        return (int)this.radius;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(obj instanceof Track) {
            Track that = (Track) obj;
            return that.radius == this.radius;
        } else {
            return false;
        }
    }
}
