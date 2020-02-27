package applications.AtomStructure;

import APIs.CircularOrbitAPIs;
import applications.AtomStructure.gui.AtomStructurePanel;
import applications.AtomStructure.memento.ElectronTransitCareTaker;
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
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AtomStructure implements Drawable {
    private String filename;

//    读入的文件配置
    String elementName;
    int numofTracks;
    List<Integer> trackIdList = new ArrayList<>();
    List<Integer> electronNumList = new ArrayList<>();
    Map<Track,List<Electron>> physicalObjMap;

    //    生成的构造配置
    List<Track> trackList = new ArrayList<>();
    AtomCircularOrbit atomCircularOrbit;

    AtomStructurePanel panel;
    ElectronTransitCareTaker careTaker = new ElectronTransitCareTaker();


    public AtomStructure(String filename) {
        this.filename = filename;
    }

    /**
     * 读入文件配置
     * 利用正则表达式进行解析
     * */
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

                String elementNamePattern = "ElementName\\s*::=\\s*([A-Z]{1}[a-z]{0,1})";
                String tracksPattern = "NumberOfTracks\\s*::=\\s*(\\d+)";
                String electronPattern = "NumberOfElectron\\s*::=\\s*((?:(?:\\d+\\/\\d+)|;)+)";

                // 创建 Pattern 对象
                Matcher electronMatcher = Pattern.compile(electronPattern).matcher(line);
                Matcher elementNameMatcher = Pattern.compile(elementNamePattern).matcher(line);
                Matcher tracksMatcher = Pattern.compile(tracksPattern).matcher(line);

                if(electronMatcher.find()) {
                    splitStrs = electronMatcher.group(1).trim().split(";");
                    for(int i=0;i<splitStrs.length;i++) {
                        int trackId = Integer.valueOf(splitStrs[i].split("/")[0]);
                        int electronNum = Integer.valueOf(splitStrs[i].split("/")[1]);
                        trackIdList.add(trackId);
                        electronNumList.add(electronNum);
                    }
                } else if(elementNameMatcher.find()) {
                    elementName = elementNameMatcher.group(1).trim();
                } else if(tracksMatcher.find()) {
                    numofTracks = Integer.valueOf(tracksMatcher.group(1).trim());
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
     * 根据文件中的配置
     * 初始化轨道系统
     */
    public void initCircularOrbit() {
        AtomCircularOrbitBuilder builder = new AtomCircularOrbitBuilder();
        builder.createConcreteCircularOrbit(elementName);
//        这里把轨道号直接当做其半径

        for(int i=0;i<numofTracks;i++) {
            trackList.add(Track.getInstance(i+1));
        }
        builder.buildTracks(trackList);
        physicalObjMap = new HashMap<>();
        int cnt=0;
        for(int i=0;i<trackIdList.size();i++) {
            Track track = trackList.get(trackIdList.get(i)-1);
            int eleNum = electronNumList.get(i);
            if(!physicalObjMap.containsKey(track)) {
                physicalObjMap.put(track,new ArrayList<>());
            }
            for(int j=0;j<eleNum;j++) {
                physicalObjMap.get(track).add(Electron.getInstance());
            }
        }

        builder.buildObjects(AtomCore.getInstance(),physicalObjMap);
        atomCircularOrbit = (AtomCircularOrbit) builder.getConcreteCircularOrbit();
    }

//    @Override
//    public void initialize() {
//        loadConfig();
//        initCircularOrbit();
//    }
//
//    @Override
//    public void draw(JFrame frame) {
//        panel = new AtomStructurePanel(this);
//        frame.getContentPane().add(panel);
//        atomCircularOrbit.visualize(panel.getDrawPanel());
//    }


    //////////////////////////////////////////////////////////////////////////////////////////////////
    private Electron getPhysicalObjByObName(Predicate<Electron> predicate, ConcreteCircularOrbit circularOrbit) {
        Iterator<Electron> ite = circularOrbit.iterator();
        List<Electron>  physicalObjs = new ArrayList<>();
        while(ite.hasNext()) {
            Electron tmp = ite.next();
            physicalObjs.add(tmp);
        }
        Collections.shuffle(physicalObjs);
        return physicalObjs.stream().filter(predicate)
                .collect(Collectors.toList()).get(0);
    }

    /**
     * 向轨道系统co中添加轨道tk
     */
    public void addTrack(double addRadius) {
        atomCircularOrbit.addTrack(Track.getInstance(addRadius));
        reLoadAll();
    }
    /**
     * 在轨道系统co中移除轨道tk
     */
    public void removeTrack(double rmRadius) {
        atomCircularOrbit.removeTrack(Track.getInstance(rmRadius));
        reLoadAll();
    }
    /**
     * 向co的tk轨道添加运动员Electron
     */
    public void addPhysicalObject(Electron electron,double tkRadius) {
        atomCircularOrbit.addPhysicalObj2Track(electron, Track.getInstance(tkRadius));
        reLoadAll();
    }
    /**
     * 在轨道系统co中移除运动员Electron
     */
    public void removePhysicalObject(double rmRadius) {
        Track tk = Track.getInstance(rmRadius);
        Electron electron = getPhysicalObjByObName(x->atomCircularOrbit.getTrackForPO(x).equals(tk)
                ,atomCircularOrbit);
        atomCircularOrbit.removePhysicalObject(electron);
        reLoadAll();
    }

    @Override
    public void initialize() {
        loadConfig();
        initCircularOrbit();
    }

    @Override
    public void draw(JFrame frame) {
        panel = new AtomStructurePanel(this);
        frame.getContentPane().add(panel);
        atomCircularOrbit.visualize(panel.getDrawPanel());
        reLoadAll();
    }

    public void reLoadAll() {
        CircularOrbitAPIs<AtomCore,Electron> apis = new CircularOrbitAPIs<>();
        CircularOrbit<AtomCore, Electron> nowCircularOrbit = atomCircularOrbit;
        boolean state = apis.checkOrbitAvailable(nowCircularOrbit);
        double entropy = apis.getObjectDistributionEntropy(nowCircularOrbit);
        List<String> trackList = nowCircularOrbit.getTrackRadiusList();
        Iterator<Electron> ite = ((AtomCircularOrbit) nowCircularOrbit).iterator();
        List<String> physicalObjs = new ArrayList<>();
        while(ite.hasNext()) {
            physicalObjs.add(ite.next().getObName());
        }
        panel.reloadGameInfo(state,entropy,trackList,physicalObjs,careTaker.getAllHistory());
        nowCircularOrbit.visualize(panel.getDrawPanel());
    }
    //////////////////////////////////////////////////////////////////////////////////////////

    public void electronTransit(double source,double target) {
        Track sourceTrack = Track.getInstance(source);
        Track targetTrack = Track.getInstance(target);

        Electron electron = getPhysicalObjByObName(x->atomCircularOrbit.getTrackForPO(x).equals(sourceTrack)
                ,atomCircularOrbit);
        atomCircularOrbit.removePhysicalObject(electron);
        atomCircularOrbit.addPhysicalObj2Track(electron,targetTrack);
        careTaker.addMemento(electron,sourceTrack,targetTrack);
        reLoadAll();
    }
    public void rebackHistory(int index) {
        atomCircularOrbit.reback(careTaker.rebackMemento(index));
        reLoadAll();
    }
}
