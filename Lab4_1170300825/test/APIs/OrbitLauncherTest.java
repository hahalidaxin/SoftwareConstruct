package APIs;

import org.junit.jupiter.api.Test;
import otherDirectory.exception.CheckedException;

public class OrbitLauncherTest {
    @Test
    void test() {
        OrbitLauncher launcher = new OrbitLauncher();
        try {
            launcher.launchOrbit(0, "src/configFile/TrackGame.txt");
        } catch(CheckedException e) {
            System.out.println(e);
        }
        String[] args = new String[0];
        OrbitLauncher.main(args);
    }
}
