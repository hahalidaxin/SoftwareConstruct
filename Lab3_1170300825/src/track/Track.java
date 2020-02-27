package track;


/**
 * 相等 -> 对象相等
 */
public class Track implements Comparable<Track>{
    double radius;
//    Set<E> physicalObjectSet = new HashSet<>();

    public static Track getInstance(double radius) {
        return new Track(radius);
    }

    public Track(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public int compareTo(Track that) {
        if(this.radius<that.radius)
            return -1;
        else if(this.radius==that.radius)
            return 0;
        else return 1;
    }

    @Override
    public int hashCode() {
        return (int)this.radius;
    }

    @Override
    public boolean equals(Object obj) {
        Track that = (Track) obj;
        return that.radius == this.radius;
    }
}
