package apis;

import org.junit.jupiter.api.Test;
import otherdirectory.exception.CheckedException;

public class OrbitLauncherTest {
    @Test
    void test() {
        OrbitLauncher launcher = new OrbitLauncher();
        try {
            launcher.launchOrbit(0, "src/configfile/trackgame.txt");
        } catch(CheckedException e) {
            System.out.println(e);
        }
        String[] args = new String[0];
        OrbitLauncher.main(args);
    }
}
