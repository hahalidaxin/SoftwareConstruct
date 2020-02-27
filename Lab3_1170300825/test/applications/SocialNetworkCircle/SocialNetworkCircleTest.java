package applications.SocialNetworkCircle;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocialNetworkCircleTest {

    static String[] socialNetwork = {"src/configFile/SocialNetworkCircle.txt",
            "src/configFile/SocialNetworkCircle_Medium.txt",
            "src/configFile/SocialNetworkCircle_Larger.txt"
    };

    @Test
    void testLoadConfig() {
        SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configFile/SocialNetworkCircle.txt");
        socialNetworkCircle.loadConfig();
    }

    @Test
    void testInitGame() {
        SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle(socialNetwork[0]);
        socialNetworkCircle.loadConfig();
        socialNetworkCircle.initCircularOrbit();
    }

    @Test
    void testIterator() {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        Iterator<Integer> iterator = integers.iterator();
        while(iterator.hasNext()) {
            int now = iterator.next();
            System.out.println(now);
            if(now==3) iterator.remove();
        }
        System.out.println();
        integers.stream().forEach(System.out::println);
    }


    @Test
    void testRegex() {
        String str="5";
        String pattern = "[4-9|10]";
        Matcher matcher= Pattern.compile(pattern).matcher(str);
        if(matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            System.out.println(matcher.group(4));
        }
     }
}
