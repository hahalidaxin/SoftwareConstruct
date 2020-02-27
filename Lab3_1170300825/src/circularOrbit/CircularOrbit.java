package circularOrbit;

import centralObject.CentralObject;
import otherDirectory.Difference;
import physicalObject.PhysicalObject;
import track.Track;

import javax.swing.*;
import java.util.List;

/**
 *
 * @param <L> 中心点物体类型
 * @param <E>  轨道物体类型
 */
public interface CircularOrbit<L,E> {
    public static CircularOrbit<CentralObject,PhysicalObject> empty() {
        return new ConcreteCircularOrbit<CentralObject,PhysicalObject>();
    }
    public boolean addTrack(Track newTrack);
    public boolean removeTrack(Track rmTrack);
    public void addCentralObject(L co);
    public boolean addPhysicalObj2Track(E po, Track tk);
    public void removePhysicalObject(E oldObj) ;

    public boolean addRelationOfCentralObj2TrackObj(L centralObject,E physicalObject,double weight);
    public boolean addRelationOf2TrackObs(E physicalObj1,E physicalObj2,double weight);
//    public static void loadConfig(File file);
    public void transit(E oldObj,E newObj,Track t);
    public void move(E oldObject, E newObject);
//    public <E extends PhysicalObject> double getPhysicalDistance(E e1,E e2);

//    APIs
    public double getObjectDistributionEntropy() ;
    public int getLogicalDistance(E e1,E e2);
    public Difference getDifference(CircularOrbit<L,E> that) ;


    public List<Track> getSortedTrack();
    public List<E> getPhysicalObjectsOnTrack(Track tk);

    public boolean checkOribitAvailable();

    public JPanel visualizeContentPanel();
    public void visualize(JPanel panel) ;

    public List<String> getTrackRadiusList() ;
}
