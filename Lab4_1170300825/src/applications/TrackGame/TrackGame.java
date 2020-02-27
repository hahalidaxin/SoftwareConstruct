package applications.TrackGame;

import APIs.CircularOrbitAPIs;
import applications.TrackGame.gui.TrackGamePanel;
import applications.TrackGame.strategy.AssignmentStrategy;
import applications.TrackGame.strategy.RandomStrategy;
import applications.TrackGame.strategy.SimpleStrategy;
import applications.TrackGame.strategy.SortedScoreStrategy;
import centralObject.CentralObject;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import circularOrbit.Drawable;
import org.apache.log4j.Logger;
import otherDirectory.exception.CheckedException;
import otherDirectory.exception.GrammarException;
import otherDirectory.exception.SameLabelException;
import otherDirectory.exception.UncheckedException;
import track.Track;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 题目理解：
 * 在TrackGame中忽略绝对位置
 */
public class TrackGame implements Drawable {
    private static Logger logger = Logger.getLogger(TrackGame.class.getName());

    private static final double[] TRACKRADIUS = {1,2,3,4,5,6,7,8};

    public List<AssignmentStrategy> strategies = new ArrayList<>();
    public List<Runner> runnerList = new ArrayList<>();
    public List<Integer> gameTypes = new ArrayList<>();
    private List<TrackCircularOrbit> trackCircularOrbitList = new ArrayList<>();
    public  int numofTracks;
    private String filename;

    private TrackGamePanel panel;
    private int nowDisplayIndex = 1;

    public TrackGame(String filename) {
        this.filename = filename;
    }

    /**
     * 测试是否完全匹配
     * @param line 匹配行
     * @param regex 正则表达式
     * @return  是否 完全 匹配
     */
    private boolean testMatch(String line,String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(line);
        if(matcher.find()) {
            if(matcher.group(1).equals(line)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 获得读入错误的具体失败原因
     * @param line 读入的行
     * @return 错误原因 如果没有出现代码中考虑的错误则直接返回错误信息“匹配失败：未知错误类型”
     */
    private String getReadFaultReason(String line) {
        String[] splitStrs = line.split("::=");
        String label = splitStrs[0].trim();
        String content = splitStrs[1].trim();
//        String athletePattern = "Athlete\\s*::=\\s*<([a-zA-Z]+),(\\d+),([A-Z]{3}),(\\d+),(\\d{1,2}\\.\\d{2})>";
//        String gamePattern = "Game\\s*::=\\s*(100|200|400)";
//        String tracksPattern = "NumOfTracks\\s*::=\\s*([4-9]|10)";

        if(label.equals("Athlete")) {
            splitStrs = content.split(",");
            if(splitStrs.length!=5) {
                return String.format("匹配失败：只提供了 %d 个运动分量",splitStrs.length);
            }
            if(!testMatch(splitStrs[0],"(<[a-zA-Z]+)")) {
                return "匹配失败：非法运动员名称";
            }
            if(!testMatch(splitStrs[1],"(\\d+)")) {
                return "匹配失败：非法运动员ID";
            }
            if(!testMatch(splitStrs[2],"([A-Z]{3})")) {
                return "匹配失败：非法运动员国家";
            }
            if(!testMatch(splitStrs[3],"(\\d+)")) {
                return "匹配失败：非法运动员年龄";
            }
            if(!testMatch(splitStrs[4],"(\\d{1,2}\\.\\d{2}>)")) {
                return "匹配失败：非法运动员最好成绩";
            }
        } else if(label.equals("Game")) {
            try {
                int length = Integer.parseInt(content);
                if(length!=100 && length!=200 && length!=800) {
                    return "匹配失败：没有对应的场地长度";
                }
            } catch (NumberFormatException e) {
                return "匹配失败：非法输入格式";
            }
        } else if(label.equals("NumOfTracks")) {
            try {
                int num = Integer.parseInt(content);
                if(!(num>=4 && num<=9)) {
                    return "匹配失败：非法比赛轨道数目";
                }
            } catch (NumberFormatException e) {
                return "匹配失败：非法输入格式";
            }
        } else {
            return "匹配失败：标签错误";
        }
        return "匹配失败：未知类型错误";
    }

    /**
     * 读入文件配置
     * 利用正则表达式进行解析
     * 初始化所有的运动员 、 比赛类型 、 轨道数目
     */
    public void loadConfig() throws CheckedException {
        BufferedReader reader = null;
        String faultReason;
        Set<String> runnerNamePool = new HashSet<>();

        try {
//            reader = new BufferedReader(new FileReader( new File(filename|)));
            InputStreamReader isr = new InputStreamReader(new FileInputStream(filename), "UTF-8");
            reader = new BufferedReader(isr);
            String line = reader.readLine();
            String[] splitStrs;
            while(line!=null) {
                if(line.length()==0) {
                    line = reader.readLine();
                    continue;
                }
                line = line.trim();
                String athletePattern = "^Athlete\\s*::=\\s*<([a-zA-Z]+),(\\d+),([A-Z]{3}),(\\d+),(\\d{1,2}\\.\\d{2})>\\s*$";
                String gamePattern = "^Game\\s*::=\\s*(100|200|400)\\s*$";
                String tracksPattern = "^NumOfTracks\\s*::=\\s*([4-9]|10)\\s*$";
                // 创建 Pattern 对象
                Matcher athleteMatcher = Pattern.compile(athletePattern).matcher(line);
                Matcher gameMatcher = Pattern.compile(gamePattern).matcher(line);
                Matcher tracksMatcher = Pattern.compile(tracksPattern).matcher(line);

                if(athleteMatcher.find()) {
                    String obName = athleteMatcher.group(1);
                    int id = Integer.parseInt(athleteMatcher.group(2));
                    String country = athleteMatcher.group(3);
                    int age = Integer.parseInt(athleteMatcher.group(4));
                    double bestScore = Double.parseDouble(athleteMatcher.group(5));

                    SameLabelException.assertTrue(!runnerNamePool.contains(obName),"出现两个相同名称的运动员");

                    runnerNamePool.add(obName);

                    Runner runner = Runner.getInstance(obName,id,age,bestScore,country);
                    runnerList.add(runner);

                } else if(gameMatcher.find()) {
                    splitStrs = gameMatcher.group(1).split("\\|");
                    for(int i=0;i<splitStrs.length;i++) {
                        gameTypes.add(Integer.parseInt(splitStrs[i]));
                    }
                } else if(tracksMatcher.find()) {
                    numofTracks = Integer.parseInt(tracksMatcher.group(1));
                } else {
//                    定位匹配失败原因
                    GrammarException.assertTrue(false,getReadFaultReason(line));
                }

                line = reader.readLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
        } catch(CheckedException e) {
            logger.error(e);
            throw e;
        }
        finally {
            try {
                if(reader!=null)
                    reader.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将所有的runner根据传入的编排策略进行划分
     * @param assignmentStrategy 比赛编排策略
     */
    public void divideIntoGroups(AssignmentStrategy assignmentStrategy) {
        UncheckedException.assertTrue(!runnerList.isEmpty(),"需要先进行loadConfig");

        List<Track> trackList = new ArrayList<>();
        for(int i=0;i<numofTracks;i++) {
            trackList.add(new Track(TRACKRADIUS[i]));
        }

        List<Map<Track,Runner>> runnerGroups = assignmentStrategy.assign(trackList,runnerList);
        List<TrackCircularOrbit> trackCircularOrbits = new ArrayList<>();

        for(Map<Track,Runner> group:runnerGroups) {
            TrackCircularOrbitBuilder trackBuilder = new TrackCircularOrbitBuilder();
            trackBuilder.createConcreteCircularOrbit(null);
            trackBuilder.buildTracks(trackList);
            Map<Track,List<Runner>> newGroup = group.keySet().stream()
                    .collect(Collectors.toMap(x->x,x->
                        Arrays.asList(group.get(x))));
            for (int i = group.size(); i < numofTracks; i++) {
                newGroup.put(Track.getInstance(TRACKRADIUS[i]),new ArrayList<>());
            }
            trackBuilder.buildObjects(null,newGroup);
            trackBuilder.buildRelation(new ArrayList<>(),new HashMap<>());
            ConcreteCircularOrbit<CentralObject, Runner> tmp = trackBuilder.getConcreteCircularOrbit();
            if(tmp instanceof TrackCircularOrbit) {
                trackCircularOrbits.add((TrackCircularOrbit) tmp);
            }
        }
        this.trackCircularOrbitList = trackCircularOrbits;
    }

    public List<Runner> getRunnerList() {
        return runnerList;
    }

    ///////////////////////////////////////////////////////////////////

    private Runner getPhysicalObjByObName(String obname, ConcreteCircularOrbit circularOrbit) {
        Iterator<Runner> ite = circularOrbit.iterator();
        List<Runner>  physicalObjs = new ArrayList<>();
        while(ite.hasNext()) {
            Runner tmp = ite.next();
            if(tmp.getObName().equals(obname)) {
                physicalObjs.add(tmp);
                break;
            }
        }
        Collections.shuffle(physicalObjs);
        return physicalObjs.get(0);
    }
    /**
     * 向轨道系统co中添加轨道tk
     */
    public void addTrack(double addRadius) {
        trackCircularOrbitList.get(nowDisplayIndex-1).addTrack(Track.getInstance(addRadius));
        reLoadAll();
    }
    /**
     * 在轨道系统co中移除轨道tk
     */
    public void removeTrack(double rmRadius) {
        trackCircularOrbitList.get(nowDisplayIndex-1).removeTrack(Track.getInstance(rmRadius));
        reLoadAll();
    }
    /**
     * 向co的tk轨道添加运动员runner
     * @param runner
     */
    public void addPhysicalObject(Runner runner,double tkRadius) {
        trackCircularOrbitList.get(nowDisplayIndex - 1).addPhysicalObj2Track(runner, Track.getInstance(tkRadius));
        reLoadAll();
    }
    /**
     * 在轨道系统co中移除运动员runner
     */
    public void removePhysicalObject(String rmObName) {
        TrackCircularOrbit circularOrbit = trackCircularOrbitList.get(nowDisplayIndex-1);
        Runner runner = getPhysicalObjByObName(rmObName,circularOrbit);
        circularOrbit.removePhysicalObject(runner);
        reLoadAll();
    }

    public List<TrackCircularOrbit> getTrackCircularOrbitList() {
        return trackCircularOrbitList;
    }

    @Override
    public void initialize() throws CheckedException{
        strategies.add(new SimpleStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new SortedScoreStrategy());

        loadConfig();
    }

    @Override
    public void draw(JFrame frame) {
        panel = new TrackGamePanel(this);

        panel.initComboBoxStrategyItems(strategies.stream()
                .map(AssignmentStrategy::getStrategyName).collect(Collectors.toList()));

        frame.getContentPane().add(panel);
    }
    public void reLoadAll() {
        if(panel==null) return;
        CircularOrbitAPIs<CentralObject,Runner> apis = new CircularOrbitAPIs<>();
        CircularOrbit<CentralObject,Runner> nowCircularOrbit = trackCircularOrbitList.get(nowDisplayIndex-1);
        boolean state = apis.checkOrbitAvailable(nowCircularOrbit);
        double entropy = apis.getObjectDistributionEntropy(nowCircularOrbit);
        List<String> trackList = nowCircularOrbit.getTrackRadiusList();
        Iterator<Runner> ite = ((TrackCircularOrbit) nowCircularOrbit).iterator();
        List<String> physicalObjs = new ArrayList<>();
        while(ite.hasNext()) {
            physicalObjs.add(ite.next().getObName());
        }
        List<String> otherGroupObjs = new ArrayList<>();
        for(int i=1;i<=trackCircularOrbitList.size();i++) if(i!=nowDisplayIndex){
            Iterator<Runner> ite2 = trackCircularOrbitList.get(i-1).iterator();
            while(ite2.hasNext()) {
                Runner r = ite2.next();
                otherGroupObjs.add(String.format("%d::%s",i,r.getObName()));
            }
        }

        panel.reloadGameInfo(state,entropy,trackList,physicalObjs,otherGroupObjs);
        nowCircularOrbit.visualize(panel.getDrawPanel());
    }

//////////////////////////////////////////////////////////////////////////////////////////////

    public void visualizeOrbit(Integer index) {
        nowDisplayIndex = index;
        reLoadAll();
    }
    public void selecttedGameStrategy(String strategyName) {
        for(AssignmentStrategy strategy:strategies)
            if(strategy.getStrategyName().equals(strategyName)) {
                divideIntoGroups(strategy);
            }
        List<String> groups = new ArrayList<>();
        for(int i=1;i<=trackCircularOrbitList.size();i++) {
            groups.add(Integer.toString(i));
        }
        this.panel.initComboBoxGroupSelectItems(groups);
    }
    /**
     * 交换选手ra和rb的比赛组
     */
    public void exchangeGroup(String raName,String rbName) {
        Runner ra = getPhysicalObjByObName(raName,trackCircularOrbitList.get(nowDisplayIndex-1));
        Runner rb = getPhysicalObjByObName(rbName.split("::")[1],
                trackCircularOrbitList.get(Integer.parseInt(rbName.split("::")[0])-1));
        TrackCircularOrbit tcoa = null,tcob = null;
        Track tka = null,tkb = null;
        for(TrackCircularOrbit tco:trackCircularOrbitList) {
            Track tmp = tco.getTrackForPO(ra);
            if(tmp!=null) {
                tcoa = tco;
                tka = tmp;
            }
            tmp = tco.getTrackForPO(rb);
            if(tmp!=null) {
                tcob = tco;
                tkb = tmp;
            }
        }
        UncheckedException.assertTrue(tcoa!=null && tcob!=null,"有runner不存在所有game中");

        tcoa.removePhysicalObject(ra);
        tcob.removePhysicalObject(rb);
        tcoa.addPhysicalObj2Track(rb,tka);
        tcob.addPhysicalObj2Track(ra,tkb);

        reLoadAll();
    }

    /**
     * 交换选手ra和rb的赛道
     * 前提：ra与rb必须要在同一组中
     */
    public void exchangeTrack(String raName,String rbName) {
        TrackCircularOrbit circularOrbit  = trackCircularOrbitList.get(nowDisplayIndex-1);
        Runner ra = getPhysicalObjByObName(raName,circularOrbit);
        Runner rb = getPhysicalObjByObName(rbName,circularOrbit);
        TrackCircularOrbit tcoE = null;
        Track tka = null,tkb = null;
        boolean probFlag = false;
        for(TrackCircularOrbit tco:trackCircularOrbitList) {
            if(tco.getTrackForPO(rb)!=null && tco.getTrackForPO(ra)!=null) {
                tka = tco.getTrackForPO(ra);
                tkb = tco.getTrackForPO(rb);
                tcoE = tco;
                probFlag = true;
                break;
            }
        }
        UncheckedException.assertTrue(probFlag,"无法将ra与rb确定为同一轨道的两个物体");

        tcoE.removePhysicalObject(ra);
        tcoE.removePhysicalObject(rb);
        tcoE.addPhysicalObj2Track(ra,tkb);
        tcoE.addPhysicalObj2Track(rb,tka);

        if(panel==null) return ;
        reLoadAll();
    }


}
