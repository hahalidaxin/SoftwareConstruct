package applications.AtomStructure;

import org.junit.jupiter.api.Test;

public class AtomStructureTest {
    @Test
    void test() {
        AtomStructure structure = new AtomStructure("src/configFile/AtomicStructure_Medium.txt");
        structure.loadConfig();
        structure.initCircularOrbit();
    }
}
