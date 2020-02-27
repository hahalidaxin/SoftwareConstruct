package apis;

import apis.gui.LauncherFrame;
import applications.atomstructure.AtomStructure;
import applications.socialnetworkcircle.SocialNetworkCircle;
import applications.trackgame.TrackGame;
import javax.swing.JFrame;
import otherdirectory.exception.CheckedException;

public class OrbitLauncher {
  public static final int TRACKGAME = 0;
  public static final int ATOMSTRUCTURE = 1;
  public static final int SOCIALNETWORK = 2;
  static final int WINDOWWIDTH = 1500;
  static final int WINDOWHEIGHT = 950;

  /**
   * .
   * main函数
   *
   * @param args 程序启动参数
   */
  public static void main(String[] args) {
    OrbitLauncher launcer = new OrbitLauncher();
    LauncherFrame frame = new LauncherFrame(launcer);
    frame.setVisible(true);
  }

  /**
   * .
   * 根据轨道系统类型以及轨道系统文件启动对应轨道系统
   *
   * @param orbitType 轨道系统类型
   * @param filePath  轨道系统配置文件
   * @throws CheckedException Checked 异常
   */
  public void launchOrbit(int orbitType, String filePath) throws CheckedException {
    JFrame orbitFrame = new JFrame();
    orbitFrame.setSize(WINDOWWIDTH, WINDOWHEIGHT);
    switch (orbitType) {
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
}
