package otherDirectory;

import org.junit.jupiter.api.Test;
import physicalObject.PhysicalObject;

import java.util.ArrayList;
import java.util.List;

public class DifferenceTest {

//    集合差异
    @Test
    void testToStringSet() {
        List<PhysicalObject> orbits1Objs = new ArrayList<>();
        orbits1Objs.add(new PhysicalObject("A"));
        orbits1Objs.add(new PhysicalObject("B"));
        orbits1Objs.add(new PhysicalObject("C"));
        orbits1Objs.add(new PhysicalObject("A"));
        orbits1Objs.add(new PhysicalObject("A"));
//        System.out.println(orbits1Objs.size());

        List<PhysicalObject> orbits2Objs = new ArrayList<>();
        orbits2Objs.add(new PhysicalObject("D"));
        orbits2Objs.add(new PhysicalObject("F"));
        orbits2Objs.add(new PhysicalObject("A"));
        orbits2Objs.add(new PhysicalObject("A"));
//        System.out.println(orbits2Objs.size());

        Difference<PhysicalObject> Diff = new Difference<>(0);
        Diff.addTrackSet(orbits1Objs,orbits2Objs);
        Diff.addTrackSet(orbits1Objs,new ArrayList<>());
        System.out.println(Diff.toString());
    }

//    单个元素差异
    @Test
    void testToStringSingle() {
        List<PhysicalObject> orbits1Objs = new ArrayList<>();
        orbits1Objs.add(new PhysicalObject("A"));
//        System.out.println(orbits1Objs.size());

        List<PhysicalObject> orbits2Objs = new ArrayList<>();
        orbits2Objs.add(new PhysicalObject("D"));
        orbits2Objs.add(new PhysicalObject("A"));
        orbits2Objs.add(new PhysicalObject("C"));
//        System.out.println(orbits2Objs.size());

        Difference<PhysicalObject> Diff = new Difference<>(0);
        Diff.addTrackSet(orbits1Objs,orbits2Objs);
        System.out.println(Diff.toString());
    }
}
