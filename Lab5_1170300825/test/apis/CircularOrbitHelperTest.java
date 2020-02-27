package apis;

import applications.socialnetworkcircle.CentralUser;
import applications.socialnetworkcircle.Friend;
import applications.socialnetworkcircle.SocialNetworkCircle;
import applications.socialnetworkcircle.SocialNetworkCircularOrbit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import otherdirectory.Relation;
import track.Track;
import track.TrackFactory;

public class CircularOrbitHelperTest {
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
    @Test
    void testGui() {
        String[] args = new String[0];
        CircularObjectHelper.main(args);

        SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configfile/socialnetworkcircle.txt");
        CentralUser centralUser = CentralUser.getInstance("central");
        Friend frA = Friend.getInstance("A","M",12);
        Friend frB = Friend.getInstance("B","M",12);
        Friend frC = Friend.getInstance("C","M",12);
        Friend frD = Friend.getInstance("D","M",12);
        Friend frE = Friend.getInstance("E","M",12);
        Friend frF = Friend.getInstance("F","M",12);
        Friend frG = Friend.getInstance("G","M",12);
        Track tk1 = TrackFactory.getTrackInstance(1.0);
        Track tk2 = TrackFactory.getTrackInstance(2.0);
        Track tk3 = TrackFactory.getTrackInstance(3.0);
        Track tk4 = TrackFactory.getTrackInstance(4.0);

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

        CircularObjectHelper.visualize(orbit);

//        try {
//            JFrame trackGameFrame = new JFrame();
//            trackGameFrame.setTitle("trackgame");
//            trackGameFrame.setSize(WINDOWWIDTH, WINDOWHEIGHT);
//            trackgame trackgame = new trackgame(trackGame[2]);
//            trackgame.initialize();
//            trackgame.draw(trackGameFrame);
//            trackGameFrame.setVisible(true);
//        } catch(CheckedException e) {
//
//        }
//
//        try {
//            JFrame atomStructureFrame = new JFrame();
//            atomStructureFrame.setSize(WINDOWWIDTH, WINDOWHEIGHT);
//            atomStructureFrame.setTitle("atomstructure");
//            atomstructure atomgame = new atomstructure(atomStructure[1]);
//            atomgame.initialize();
//            atomgame.draw(atomStructureFrame);
//            atomStructureFrame.setVisible(true);
////////
//        } catch(CheckedException e) {
//
//        }
//
//        try {
//            JFrame socialNetworkFrame = new JFrame();
//            socialNetworkFrame.setSize(WINDOWWIDTH, WINDOWHEIGHT);
//            socialNetworkFrame.setTitle("SocialNetwork");
//            socialnetworkcircle game = new socialnetworkcircle(socialNetwork[0]);
//            game.initialize();
//            game.draw(socialNetworkFrame);
//            socialNetworkFrame.setVisible(true);
//        }catch(CheckedException e) {
//
//        }
    }
}
