package APIs;

import applications.AtomStructure.AtomStructure;
import applications.SocialNetworkCircle.SocialNetworkCircle;
import applications.TrackGame.TrackGame;
import circularOrbit.CircularOrbit;

import javax.swing.*;

public class CircularObjectHelper {
    static final int WINDOWWIDTH = 1500;
    static final int WINDOWHEIGHT = 950;

    static String[] trackGame = {"src/configFile/TrackGame.txt",
            "src/configFile/TrackGame_Medium.txt",
            "src/configFile/TrackGame_Larger.txt"};
    static String[] atomStructure = {"src/configFile/AtomicStructure.txt",
            "src/configFile/AtomicStructure_Medium.txt"
    };
    static String[] socialNetwork = {"src/configFile/SocialNetworkCircle.txt",
            "src/configFile/SocialNetworkCircle_Medium.txt",
            "src/configFile/SocialNetworkCircle_Larger.txt",
            "src/configFile/SocialNetworkCircle_Normal.txt",
    };


    public static void visualize(CircularOrbit c) {
        JFrame frame = new JFrame();
        frame.setSize(800,800);
        frame.getContentPane().add(c.visualizeContentPanel());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame trackGameFrame = new JFrame();
        trackGameFrame.setTitle("TrackGame");
        trackGameFrame.setSize(WINDOWWIDTH,WINDOWHEIGHT);
        TrackGame trackgame = new TrackGame(trackGame[2]);
        trackgame.initialize();
        trackgame.draw(trackGameFrame);
        trackGameFrame.setVisible(true);

        JFrame atomStructureFrame = new JFrame();
        atomStructureFrame.setSize(WINDOWWIDTH,WINDOWHEIGHT);
        atomStructureFrame.setTitle("AtomStructure");
        AtomStructure atomgame = new AtomStructure(atomStructure[1]);
        atomgame.initialize();
        atomgame.draw(atomStructureFrame);
        atomStructureFrame.setVisible(true);
//////
        JFrame socialNetworkFrame = new JFrame();
        socialNetworkFrame.setSize(WINDOWWIDTH,WINDOWHEIGHT);
        socialNetworkFrame.setTitle("SocialNetwork");
        SocialNetworkCircle game = new SocialNetworkCircle(socialNetwork[0]);
        game.initialize();
        game.draw(socialNetworkFrame);
        socialNetworkFrame.setVisible(true);
    }
}
