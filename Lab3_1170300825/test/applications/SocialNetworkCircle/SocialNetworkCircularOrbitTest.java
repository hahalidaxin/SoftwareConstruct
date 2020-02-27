package applications.SocialNetworkCircle;

import org.junit.jupiter.api.Test;
import track.Track;

public class SocialNetworkCircularOrbitTest {
    @Test
    void testGetInfoDiffusion() {
        SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configFile/SocialNetworkCircle.txt");
        socialNetworkCircle.loadConfig();
        socialNetworkCircle.initCircularOrbit();
        socialNetworkCircle.getCircularOrbit().getInfoDiffusion().entrySet().stream()
        .forEach(entry-> System.out.println(entry.getKey().getObName()+" :: "+entry.getValue()));
    }

    @Test
    void testRemoveRelation2TrackObjs() {
        SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configFile/SocialNetworkCircle.txt");
        CentralUser centralUser = CentralUser.getInstance("central");
        Friend frA = Friend.getInstance("A");
        Friend frB = Friend.getInstance("B");
        Friend frC = Friend.getInstance("C");
        Friend frD = Friend.getInstance("D");
        Friend frE = Friend.getInstance("E");
        Friend frF = Friend.getInstance("F");
        Track tk1 = Track.getInstance(1);
        Track tk2 = Track.getInstance(2);
        Track tk3 = Track.getInstance(3);
        Track tk4 = Track.getInstance(4);
        SocialNetworkCircularOrbit circularOrbit = new SocialNetworkCircularOrbit();
        circularOrbit.addCentralObject(centralUser);
        circularOrbit.addTrack(tk1); circularOrbit.addTrack(tk2);
        circularOrbit.addTrack(tk3); circularOrbit.addTrack(tk4);
        circularOrbit.addPhysicalObj2Track(frA,tk1);
        circularOrbit.addPhysicalObj2Track(frB,tk1);
        circularOrbit.addPhysicalObj2Track(frC,tk2);
        circularOrbit.addPhysicalObj2Track(frD,tk3);
        circularOrbit.addPhysicalObj2Track(frE,tk3);
        circularOrbit.addPhysicalObj2Track(frF,tk3);
        circularOrbit.addRelationOfCentralObj2TrackObj(centralUser,frA,1);
        circularOrbit.addRelationOfCentralObj2TrackObj(centralUser,frB,1);
        circularOrbit.addRelationOf2TrackObs(frA,frC,1);
        circularOrbit.addRelationOf2TrackObs(frB,frC,1);
        circularOrbit.addRelationOf2TrackObs(frC,frD,1);
        circularOrbit.addRelationOf2TrackObs(frC,frE,1);
        circularOrbit.addRelationOf2TrackObs(frC,frF,1);

        circularOrbit.getInfoDiffusion().entrySet().stream()
                .forEach(entry-> System.out.println(entry.getKey().getObName()+" :: "+entry.getValue()));

        System.out.println(circularOrbit.removeRelationOf2TrackObs(frA,frC));

        System.out.println();
        circularOrbit.getInfoDiffusion().entrySet().stream()
                .forEach(entry-> System.out.println(entry.getKey().getObName()+" :: "+entry.getValue()));

    }

    @Test
    void testRemoveRelation2TrackObjs2() {
        SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configFile/SocialNetworkCircle.txt");
        CentralUser centralUser = CentralUser.getInstance("central");
        Friend frA = Friend.getInstance("A");
        Friend frB = Friend.getInstance("B");
        Friend frC = Friend.getInstance("C");
        Friend frD = Friend.getInstance("D");
        Friend frE = Friend.getInstance("E");
        Friend frF = Friend.getInstance("F");
        Friend frG = Friend.getInstance("G");
        Track tk1 = Track.getInstance(1);
        Track tk2 = Track.getInstance(2);
        Track tk3 = Track.getInstance(3);
        Track tk4 = Track.getInstance(4);
        SocialNetworkCircularOrbit circularOrbit = new SocialNetworkCircularOrbit();
        circularOrbit.addCentralObject(centralUser);
        circularOrbit.addTrack(tk1); circularOrbit.addTrack(tk2);
        circularOrbit.addTrack(tk3); circularOrbit.addTrack(tk4);
        circularOrbit.addPhysicalObj2Track(frA,tk1);
        circularOrbit.addPhysicalObj2Track(frB,tk1);
        circularOrbit.addPhysicalObj2Track(frC,tk2);
        circularOrbit.addPhysicalObj2Track(frD,tk3);
        circularOrbit.addPhysicalObj2Track(frE,tk3);
        circularOrbit.addPhysicalObj2Track(frF,tk3);
        circularOrbit.addPhysicalObj2Track(frG,tk4);
        circularOrbit.addRelationOfCentralObj2TrackObj(centralUser,frA,1);
        circularOrbit.addRelationOfCentralObj2TrackObj(centralUser,frB,1);
        circularOrbit.addRelationOf2TrackObs(frA,frC,1);
        circularOrbit.addRelationOf2TrackObs(frB,frC,1);
        circularOrbit.addRelationOf2TrackObs(frC,frD,1);
        circularOrbit.addRelationOf2TrackObs(frC,frE,1);
        circularOrbit.addRelationOf2TrackObs(frC,frF,1);
        circularOrbit.addRelationOf2TrackObs(frD,frG,1);

        circularOrbit.getInfoDiffusion().entrySet().stream()
                .forEach(entry-> System.out.println(entry.getKey().getObName()+" :: "+entry.getValue()));

        System.out.println(circularOrbit.extendRelationOfCentralObj2TrackObj(centralUser,frF,1));

        System.out.println();
        circularOrbit.getInfoDiffusion().entrySet().stream()
                .forEach(entry-> System.out.println(entry.getKey().getObName()+" :: "+entry.getValue()));
        System.out.println("frF :: "+circularOrbit.getTrackForPO(frF).getRadius());
    }
}
