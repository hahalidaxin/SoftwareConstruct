package APIs;

import APIs.gui.LauncherFrame;
import applications.AtomStructure.AtomStructure;
import applications.SocialNetworkCircle.SocialNetworkCircle;
import applications.TrackGame.TrackGame;
import otherDirectory.exception.CheckedException;

import javax.swing.*;

public class OrbitLauncher {
    public static final int TRACKGAME = 0;
    public static final int ATOMSTRUCTURE = 1;
    public static final int SOCIALNETWORK = 2;
    static final int WINDOWWIDTH = 1500;
    static final int WINDOWHEIGHT = 950;

    public void launchOrbit(int orbitType,String filePath) throws CheckedException {
        JFrame orbitFrame = new JFrame();
        orbitFrame.setSize(WINDOWWIDTH, WINDOWHEIGHT);
        switch(orbitType) {
            case TRACKGAME:
                TrackGame trackgame = new TrackGame(filePath);
                trackgame.initialize();
                trackgame.draw(orbitFrame);
                break;
            case ATOMSTRUCTURE:
                AtomStructure atomgame = new AtomStructure(filePath);
                atomgame.initialize();
                atomgame.draw(orbitFrame);
                break;
            case SOCIALNETWORK:
                SocialNetworkCircle game = new SocialNetworkCircle(filePath);
                game.initialize();
                game.draw(orbitFrame);
                break;
            default:
                break;
        }
        orbitFrame.setVisible(true);
    }

    public static void main(String[] args) {
        OrbitLauncher launcer = new OrbitLauncher();
        LauncherFrame frame = new LauncherFrame(launcer);
        frame.setVisible(true);
    }
}
