package applications.atomstructure;

import org.junit.jupiter.api.Test;
import otherdirectory.exception.CheckedException;

/**
 * Testing Strategy
 *  对于函数中的spec，设计测试用例考虑覆盖pre-condition与正常情况。
 *  对于含有分支的函数，根据分支划分等价类，对于每一个等价类设计一个测试用例
 *
 *  普通操作：只要一个分支，只需要设计一个等价类带包测试用例
 *  对于构造类，负责GUI之间的桥接工作，所以对于一些gui操作，没有办法覆盖到。
 *  loadConfig：需要测试抛出异常，根据读入错误的种类划分等价类，对于每一种异常，设计一组测试用例文件，
 *  文件位于src/configFile下
 */

public class AtomStructureTest {
    @Test
    void test() {
        AtomStructure structure = new AtomStructure("src/configfile/AtomicStructure.txt");
        try {
            structure.loadConfig();
            structure.initCircularOrbit();
        } catch(CheckedException e) {
            System.out.println(e);
        }
    }

//    测试输入格式不正确
    @Test
    void testLoadFaultFile() {
        try {
            AtomStructure structure = new AtomStructure("src/configfile/AtomicStructure_F0.txt");
            structure.loadConfig();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
//        structure.initCircularOrbit();
    }

//    测试声明轨道数目和真实轨道数目不相同的情况
    @Test
    void testDiffTrackNum() {
        try {
            AtomStructure structure = new AtomStructure("src/configfile/AtomicStructure_F1.txt");
            structure.loadConfig();
        } catch(CheckedException e) {
            System.out.println(e.toString());
        }
    }


//    测试TrackOperations
    @Test
    void testTrackOp() {
        AtomStructure structure = new AtomStructure("src/configfile/AtomicStructure.txt");
        try {
            structure.loadConfig();
            structure.initCircularOrbit();
            structure.addTrack(22);
            structure.removeTrack(22);
        } catch(CheckedException e) {
            System.out.println(e);
        }
    }

//    测试physicalObjsOperations
    @Test
    void testPhysicalObjOperations() {
        AtomStructure structure = new AtomStructure("src/configfile/AtomicStructure.txt");
        try {
//            structure.loadConfig();
//            structure.initCircularOrbit();
            structure.initialize();
//            structure.addTrack(22);
            structure.addPhysicalObject(Electron.getInstance(), 1);
            structure.addPhysicalObject(Electron.getInstance(), 1);
            structure.removePhysicalObject(1);
//            structure.removeTrack(22);
        } catch(CheckedException e) {
            System.out.println(e);
        }
    }

//      测试ElectronTransit
    @Test
    void testTransit() {
        AtomStructure structure = new AtomStructure("src/configfile/AtomicStructure.txt");
        try {
            structure.loadConfig();
            structure.initCircularOrbit();
            structure.electronTransit(1,2);
            structure.electronTransit(2,3);
            structure.rebackHistory(0);
        } catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void test1() {
        AtomStructure structure = new AtomStructure("src/configfile/AtomicStructure_E_ElementName.txt");
        try {
            structure.initialize();
        } catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void test2() {
        AtomStructure structure = new AtomStructure("src/configfile/AtomicStructure_E_Label.txt");
        try {
            structure.initialize();
        } catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void test3() {
        AtomStructure structure = new AtomStructure("src/configfile/AtomicStructure_E_NumE.txt");
        try {
            structure.initialize();
        } catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void test4() {
        AtomStructure structure = new AtomStructure("src/configfile/AtomicStructure_E_NumE2.txt");
        try {
            structure.initialize();
        } catch(CheckedException e) {
            System.out.println(e);
        }
    }

    @Test
    void test5() {
        AtomStructure structure = new AtomStructure("src/configfile/AtomicStructure_E_TrackNum.txt");
        try {
            structure.initialize();
        } catch(CheckedException e) {
            System.out.println(e);
        }
    }

}
