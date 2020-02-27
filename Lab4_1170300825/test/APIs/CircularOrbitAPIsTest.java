package APIs;

import applications.SocialNetworkCircle.CentralUser;
import applications.SocialNetworkCircle.Friend;
import applications.SocialNetworkCircle.SocialNetworkCircle;
import applications.SocialNetworkCircle.SocialNetworkCircularOrbit;
import org.junit.jupiter.api.Test;
import otherDirectory.Relation;
import track.Track;

import java.util.*;

public class CircularOrbitAPIsTest {


    void addEdge(Map<Friend,List<Relation<Friend,Friend>>> pm,Friend A,Friend B,double w) {
        if(!pm.containsKey(A)) {
            pm.put(A,new ArrayList<>());
        }
        if(!pm.containsKey(B)) {
            pm.put(B,new ArrayList<>());
        }
        pm.get(A).add(new Relation<>(A,B,w));
        pm.get(B).add(new Relation<>(B,A,w));
    }

    /**
     * 该API类实际在ConcreteCircularOrbit中实现，
     * 因此将Test放到ConcreteCircularOrbitTest中
     */

    @Test
    void testAPI() {
        SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configFile/SocialNetworkCircle.txt");
        CentralUser centralUser = CentralUser.getInstance("central");
        Friend frA = Friend.getInstance("A","M",12);
        Friend frB = Friend.getInstance("B","M",12);
        Friend frC = Friend.getInstance("C","M",12);
        Friend frD = Friend.getInstance("D","M",12);
        Friend frE = Friend.getInstance("E","M",12);
        Friend frF = Friend.getInstance("F","M",12);
        Friend frG = Friend.getInstance("G","M",12);
        Track tk1 = Track.getInstance(1);
        Track tk2 = Track.getInstance(2);
        Track tk3 = Track.getInstance(3);
        Track tk4 = Track.getInstance(4);

        Map<Track, List<Friend>> pm = new HashMap<>();
        pm.put(tk1, Arrays.asList(frA,frB));
        pm.put(tk2, Arrays.asList(frC));
        pm.put(tk3, Arrays.asList(frD,frE,frF));
        pm.put(tk4, Arrays.asList(frG));
        List<Relation<CentralUser,Friend>> rel1 = new ArrayList<>();
        rel1.add(new Relation<>(centralUser,frA,1));
        rel1.add(new Relation<>(centralUser,frB,1));

        Map<Friend,List<Relation<Friend,Friend>>> rel2 = new HashMap<>();

        addEdge(rel2,frA,frC,1);
        addEdge(rel2,frB,frC,1);
        addEdge(rel2,frC,frD,1);
        addEdge(rel2,frC,frE,1);
        addEdge(rel2,frC,frF,1);
        addEdge(rel2,frD,frG,1);

        SocialNetworkCircularOrbit orbit = new SocialNetworkCircularOrbit(centralUser,pm,rel1,rel2);

        new CircularOrbitAPIs<CentralUser,Friend>().checkOrbitAvailable(orbit);
        System.out.println(new CircularOrbitAPIs<CentralUser,Friend>().getObjectDistributionEntropy(orbit));
        System.out.println(new CircularOrbitAPIs<CentralUser,Friend>().getLogicalDistance(orbit,frA,frF));
        System.out.println(new CircularOrbitAPIs<CentralUser,Friend>().getObjectDistributionEntropy(orbit));

    }

    @Test
    void testDiff() {
        SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configFile/SocialNetworkCircle.txt");
        CentralUser centralUser = CentralUser.getInstance("central");
        Friend frA = Friend.getInstance("A","M",12);
        Friend frB = Friend.getInstance("B","M",12);
        Friend frC = Friend.getInstance("C","M",12);
        Friend frD = Friend.getInstance("D","M",12);
        Friend frE = Friend.getInstance("E","M",12);
        Friend frF = Friend.getInstance("F","M",12);
        Friend frG = Friend.getInstance("G","M",12);
        Track tk1 = Track.getInstance(1);
        Track tk2 = Track.getInstance(2);
        Track tk3 = Track.getInstance(3);
        Track tk4 = Track.getInstance(4);

        Map<Track, List<Friend>> pm = new HashMap<>();
        pm.put(tk1, Arrays.asList(frA,frB));
        pm.put(tk2, Arrays.asList(frC));
        pm.put(tk3, Arrays.asList(frD,frE,frF));
        pm.put(tk4, Arrays.asList(frG));
        List<Relation<CentralUser,Friend>> rel1 = new ArrayList<>();
        rel1.add(new Relation<>(centralUser,frA,1));
        rel1.add(new Relation<>(centralUser,frB,1));

        Map<Friend,List<Relation<Friend,Friend>>> rel2 = new HashMap<>();

        addEdge(rel2,frA,frC,1);
        addEdge(rel2,frB,frC,1);
        addEdge(rel2,frC,frD,1);
        addEdge(rel2,frC,frE,1);
        addEdge(rel2,frC,frF,1);
        addEdge(rel2,frD,frG,1);

        SocialNetworkCircularOrbit orbit = new SocialNetworkCircularOrbit(centralUser,pm,rel1,rel2);
        SocialNetworkCircularOrbit orbit1 = new SocialNetworkCircularOrbit(centralUser,pm,rel1,rel2);

        System.out.println(new CircularOrbitAPIs<CentralUser,Friend>().getDifference(orbit,orbit1));
    }

}
