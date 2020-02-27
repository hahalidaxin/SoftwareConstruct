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
import otherDirectory.MyExp;
import track.Track;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 题目理解：
 * 在TrackGame中忽略绝对位置
 */
public class TrackGame implements Drawable {
    public static final double[] TRACKRADIUS = {1,2,3,4,5,6,7,8};

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
     * 读入文件配置
     * 利用正则表达式进行解析
     * 初始化所有的运动员 、 比赛类型 、 轨道数目
     */
    public void loadConfig() {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader( new File(filename)));
            String line = reader.readLine().trim();
            String[] splitStrs;
            while(line!=null) {
                if(line.length()==0) {
                    line = reader.readLine();
                    continue;
                }
                String athletePattern = "Athlete\\s*::=\\s*<([a-zA-Z]+),(\\d+),([A-Z]{3}),(\\d+),(\\d{1,2}\\.\\d{2})>";
                String gamePattern = "Game\\s*::=\\s*(100|200|400)";
                String tracksPattern = "NumOfTracks\\s*::=\\s*([4-9]|10)";
                // 创建 Pattern 对象
                Matcher athleteMatcher = Pattern.compile(athletePattern).matcher(line);
                Matcher gameMatcher = Pattern.compile(gamePattern).matcher(line);
                Matcher tracksMatcher = Pattern.compile(tracksPattern).matcher(line);

                if(athleteMatcher.find()) {
                    String obName = athleteMatcher.group(1);
                    int id = Integer.valueOf(athleteMatcher.group(2));
                    String country = athleteMatcher.group(3);
                    int age = Integer.valueOf(athleteMatcher.group(4));
                    double bestScore = Double.valueOf(athleteMatcher.group(5));

                    Runner runner = Runner.getInstance(obName,id,age,bestScore,country);
                    runnerList.add(runner);

                } else if(gameMatcher.find()) {
                    splitStrs = gameMatcher.group(1).split("\\|");
                    for(int i=0;i<splitStrs.length;i++) {
                        gameTypes.add(Integer.valueOf(splitStrs[i]));
                    }
                } else if(tracksMatcher.find()) {
                    numofTracks = Integer.valueOf(tracksMatcher.group(1));
                } else {
                    MyExp.assertTrue(false,"匹配失败");
                }

                line = reader.readLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
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
        MyExp.assertTrue(!runnerList.isEmpty(),"需要先进行loadConfig");

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

            Map<Track,List<Runner>> physicalObjsMap = new HashMap<>();
            int uplimit= Math.min(numofTracks,group.size());
            Map<Track,List<Runner>> newGroup = group.keySet().stream()
                    .collect(Collectors.toMap(x->x,x->
                        Arrays.asList(group.get(x))));
            trackBuilder.buildObjects(null,newGroup);
            trackCircularOrbits.add((TrackCircularOrbit) trackBuilder.getConcreteCircularOrbit());
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
    public void initialize() {
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
                trackCircularOrbitList.get(Integer.valueOf(rbName.split("::")[0])-1));
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
        MyExp.assertTrue(tcoa!=null && tcob!=null,"有runner不存在所有game中");

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
        MyExp.assertTrue(probFlag,"无法将ra与rb确定为同一轨道的两个物体");

        tcoE.removePhysicalObject(ra);
        tcoE.removePhysicalObject(rb);
        tcoE.addPhysicalObj2Track(ra,tkb);
        tcoE.addPhysicalObj2Track(rb,tka);

        reLoadAll();
    }


}
