package otherdirectory;

import org.junit.jupiter.api.Test;
import physicalobject.PhysicalObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  划分等价类：轨道没有物体差异，轨道差异只有一个，轨道差异不止一个
 *  测试结果？ 打印Difference.toString
 *
 */

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
