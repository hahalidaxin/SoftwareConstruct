package APIs;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LauncherFrameTest {
    @Test
    void testListFiles() {
        File file = new File("src/configFile");
        File[] array = file.listFiles();
        List<String> filenames = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if(array[i].isFile()) {
                filenames.add(array[i].getName());
            }
        }
        filenames.forEach(System.out::println);
    }
}
