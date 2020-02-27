package debug;

import org.junit.jupiter.api.Test;

/**
 * Testing Strategy
 * 划分等价类：
 * 查询时间：恰好时间，中间时间，两人票数相同的时间（选择最近投票）
 */

/*
 * Example 1:
 *
 * Input: ["TopVotedCandidate","q","q","q","q","q","q"],
 * [[[0,1,1,0,0,1,0],[0,5,10,15,20,25,30]],[3],[12],[25],[15],[24],[8]]
 *  Output:
 * [null,0,1,1,0,0,1]
*/

public class TopVotedCandidateTest {
    @Test
    void test1() {
        int[] persons = {0,1,1,0,0,1,0};
        int[] time = {0,5,10,15,20,25,30};
        TopVotedCandidate candidate = new TopVotedCandidate(persons,time);
        assert (candidate.q(3)==0);
        assert (candidate.q(12)==1);
        assert (candidate.q(25)==1);
        assert (candidate.q(16)==0);
        assert (candidate.q(15)==0);
        assert (candidate.q(24)==0);
        assert (candidate.q(8)==1);
    }
}
