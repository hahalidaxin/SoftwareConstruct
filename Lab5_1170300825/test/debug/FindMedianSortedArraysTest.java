package debug;

import org.junit.jupiter.api.Test;

/**
 * Testing Strategy
 * 划分等价类：A.len>B.len, A.len==B.len, A.len<B.len
 * 对于以上等价类继续划分等价类：A全部元素小于B，A全部元素大于B，AB中元素大小交叉
 */

public class FindMedianSortedArraysTest {
    @Test
    void test1() {
        int[] A = {1,1,1};
        int[] B = {5,6,7};
        assert(new FindMedianSortedArrays().findMedianSortedArrays(A,B))==3;
    }
    @Test
    void test2() {
        int[] A = {1,3};
        int[] B = {2};
        assert(new FindMedianSortedArrays().findMedianSortedArrays(A,B))==2;
    }
    @Test
    void test3() {
        int[] A = {1,3};
        int[] B = {2,4,5};
        assert(new FindMedianSortedArrays().findMedianSortedArrays(A,B))==3;
    }

    @Test
    void test4() {
        int[] B = {1,1,1};
        int[] A = {5,6,7};
        assert(new FindMedianSortedArrays().findMedianSortedArrays(A,B))==3;
    }

    @Test
    void test5() {
        int[] A = {1,3,5};
        int[] B = {2,4,6};
        assert(new FindMedianSortedArrays().findMedianSortedArrays(A,B))==3.5;
    }
}
