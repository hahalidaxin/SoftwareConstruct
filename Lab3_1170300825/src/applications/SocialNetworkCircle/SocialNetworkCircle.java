package applications.SocialNetworkCircle;

import APIs.CircularOrbitAPIs;
import applications.SocialNetworkCircle.gui.SocialNetworkPanel;
import centralObject.CommonObject;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import circularOrbit.Drawable;
import otherDirectory.MyExp;
import otherDirectory.Relation;
import track.Track;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SocialNetworkCircle implements Drawable {
    private String filename;
    private CentralUser centralUser;
    private List<SocialTieEntity> socialTieEntityList = new ArrayList<>();
    private List<Friend> friendList = new ArrayList<>();
    private List<Track> trackList = new ArrayList<>();
    private SocialNetworkCircularOrbit circularOrbit ;
    private List<Relation<CentralUser,Friend>> relOfCentralObjs = new ArrayList<>();
    private Map<Friend,List<Relation<Friend,Friend>>> relOf2FriendObjs = new HashMap<>();
    private Map<Track, List<Friend>> physicalObjMap = new HashMap<>();
    private int maxTrackRadius = 0;

    SocialNetworkPanel panel;

    public SocialNetworkCircle(String filename) {
        this.filename = filename;
    }

    private <E extends CommonObject>  int getNameIndex(List<E> objs,String obName) {
        for(int i=0;i<objs.size();i++) {
            if(objs.get(i).getObName().equals(obName)) {
                return i;
            }
        }
        return -1;
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

//                System.out.println(line);
                String centralUserPattern = "CentralUser\\s*::=\\s*<([A-Za-z0-9]+),\\s*(\\d+),\\s*([MF])>";
                String friendPattern = "Friend\\s*::=\\s*<([A-Za-z0-9]+),\\s*(\\d+),\\s*([MF])>";
//                使用?:表示不捕获该括号内的元素（非捕获组），如果出现嵌套的括号，匹配规则是由左向右，由外向内
                String socialTiePattern = "SocialTie\\s*::=\\s*<([A-Za-z0-9]+),\\s*([A-Za-z0-9]+),\\s*(0(?:\\.\\d{1,3})?|1(?:\\.0{1,3})?)>";
                // 创建 Pattern 对象
                Matcher centralUserMatcher = Pattern.compile(centralUserPattern).matcher(line);
                Matcher friendMatcher = Pattern.compile(friendPattern).matcher(line);
                Matcher socialTieMatcher = Pattern.compile(socialTiePattern).matcher(line);

                if(centralUserMatcher.find()) {
                    String userName = centralUserMatcher.group(1);
                    int age = Integer.valueOf(centralUserMatcher.group(2));
                    String gender = centralUserMatcher.group(3);
                    centralUser = CentralUser.getInstance(userName,gender,age);
                } else if(friendMatcher.find()) {
                    String userName = friendMatcher.group(1);
                    int age = Integer.valueOf(friendMatcher.group(2));
                    String gender = friendMatcher.group(3);
                    MyExp.assertTrue(friendList.stream()
                            .filter(x->x.getObName().equals(userName))
                            .count()==0,"存在同名的Friend");
                    friendList.add(Friend.getInstance(userName,gender,age));
                } else if(socialTieMatcher.find()) {
                    MyExp.assertTrue(centralUser!=null,"输入数据顺序错误，centralUser为Null");

                    socialTieEntityList.add(new SocialTieEntity(socialTieMatcher.group(1),socialTieMatcher.group(2),
                            Double.valueOf(socialTieMatcher.group(3))));
                } else {
                    MyExp.assertTrue(false,"匹配失败");
                }

                line = reader.readLine();
            }

            for(SocialTieEntity socialTie:socialTieEntityList) {
                String userNameA = socialTie.getUserNameA();
                String userNameB = socialTie.getUserNameB();
                double socialDensity = Double.valueOf(socialTie.getSocianDensity());
                MyExp.assertTrue(getNameIndex(Arrays.asList(centralUser),userNameA)!=-1
                        || getNameIndex(friendList,userNameA)!=-1,"不包含"+userNameA);
                MyExp.assertTrue(getNameIndex(Arrays.asList(centralUser),userNameB)!=-1
                        || getNameIndex(friendList,userNameB)!=-1,"不包含"+userNameB);
                if(getNameIndex(Arrays.asList(centralUser),userNameA)!=-1) {
                    Friend friend = friendList.get(getNameIndex(friendList,userNameB));
                    relOfCentralObjs.add(Relation.getCentralUserTie(centralUser,friend,socialDensity));
                    continue;
                }

                if(getNameIndex(Arrays.asList(centralUser),userNameB)!=-1) {
                    Friend friend = friendList.get(getNameIndex(friendList,userNameA));
                    relOfCentralObjs.add(Relation.getCentralUserTie(centralUser,friend,socialDensity));
                    continue;
                }

                Friend friendA = friendList.get(getNameIndex(friendList,userNameA));
                Friend friendB = friendList.get(getNameIndex(friendList,userNameB));
                if(!relOf2FriendObjs.containsKey(friendA)) {
                    relOf2FriendObjs.put(friendA,new ArrayList<>());
                }
                if(!relOf2FriendObjs.containsKey(friendB)) {
                    relOf2FriendObjs.put(friendB,new ArrayList<>());
                }
                relOf2FriendObjs.get(friendA).add(Relation.getFriendsTie(friendA,friendB,socialDensity));
                relOf2FriendObjs.get(friendB).add(Relation.getFriendsTie(friendB,friendA,socialDensity));
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

    private void bfs2GetPhysicalMap(List<Friend> sources) {
        Queue<Friend> queue = new LinkedBlockingQueue<>();

        Map<Friend,Integer> distantMap = new HashMap<>();
        for(Friend st:sources) {
            queue.offer(st);
            distantMap.put(st, 1);
        }

        while(!queue.isEmpty()) {
            Friend topPerson = queue.poll();
            int nowDis=distantMap.get(topPerson);
            List<Friend> neighborList = relOf2FriendObjs.getOrDefault(topPerson,new ArrayList<>())
                    .stream()
                    .map(Relation::getObjv).collect(Collectors.toList());
            for(Friend ps:neighborList) if(!distantMap.containsKey(ps)) {
                distantMap.put(ps,nowDis+1);
                if(nowDis==maxTrackRadius) {
                    trackList.add(Track.getInstance(++maxTrackRadius));
                    physicalObjMap.put(trackList.get(maxTrackRadius-1),new ArrayList<>());
                }
                physicalObjMap.get(trackList.get(nowDis)).add(ps);
                queue.offer(ps);
            }
        }
    }

    void bfs2GetFlag(Friend source , Map<Friend,Integer> flag, int flagIndex) {
        Queue<Friend> queue = new LinkedBlockingQueue<>();

        queue.offer(source);
        flag.put(source,flagIndex);

        while(!queue.isEmpty()) {
            Friend topPerson = queue.poll();
            List<Friend> neighborList = relOf2FriendObjs.getOrDefault(topPerson,new ArrayList<>())
                    .stream()
                    .map(Relation::getObjv).collect(Collectors.toList());
            for(Friend ps:neighborList) if(!flag.containsKey(ps)) {
                flag.put(ps,flagIndex);
                queue.offer(ps);
            }
        }
    }

    private void getPhysicalMap() {
        //        将所有其他不与centralUser联通的节点子联通图取代表元与中心点连接，紧密度为0
        Map<Friend,Integer> flag = new HashMap<>();
        int flagIndex = 0;
        Set<Integer> doneFlags = new HashSet<>();

        for(Relation<CentralUser,Friend> st:relOfCentralObjs) {
            bfs2GetFlag(st.getObjv(),flag,flagIndex);
        }

        friendList.removeIf(x->!flag.containsKey(x));
        relOf2FriendObjs.keySet().stream()
                .filter(x->!friendList.contains(x))
                .collect(Collectors.toList())
                .forEach(relOf2FriendObjs::remove);

        trackList.add(Track.getInstance(1));
        ++maxTrackRadius;
        physicalObjMap.put(trackList.get(0),new ArrayList<>());
        List<Friend> firstTrackFriends = relOfCentralObjs.stream().map(x->x.getObjv()).collect(Collectors.toList());
        physicalObjMap.get(trackList.get(0))
                .addAll(firstTrackFriends);
        bfs2GetPhysicalMap(firstTrackFriends);

    }

    /**
     * 根据文件中的配置
     * 初始化轨道系统
     */
    public void initCircularOrbit() {
        SocialNetworkCircularOrbitBuilder builder = new SocialNetworkCircularOrbitBuilder();
        builder.createConcreteCircularOrbit(null);

        getPhysicalMap();

//        List<Track> sortedTracks = physicalObjMap.keySet().stream()
//                .sorted().collect(Collectors.toList());
//        for(Track track:sortedTracks) {
//            System.out.println("Track "+physicalObjMap.get(track).size()+" ");
////            for(Friend friend:physicalObjMap.get(track)) {
////                System.out.println(friend.getObName());
////            }
//        }

        builder.buildTracks(trackList);
        builder.buildObjects(centralUser,physicalObjMap);
        builder.buildRelation(relOfCentralObjs,relOf2FriendObjs);

        circularOrbit = (SocialNetworkCircularOrbit) builder.getConcreteCircularOrbit();
    }

    public SocialNetworkCircularOrbit getCircularOrbit() {
        return circularOrbit;
    }



    private class SocialTieEntity {
        String userNameA,userNameB;
        double socianDensity;

        public SocialTieEntity(String userNameA,String userNameB,double socianDensity) {
            this.userNameA = userNameA;
            this.userNameB = userNameB;
            this.socianDensity = socianDensity;
        }

        public String getUserNameA() {
            return userNameA;
        }

        public String getUserNameB() {
            return userNameB;
        }

        public double getSocianDensity() {
            return socianDensity;
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Friend getPhysicalObjByObName(Predicate<Friend> predicate, ConcreteCircularOrbit circularOrbit) {
        Iterator<Friend> ite = circularOrbit.iterator();
        List<Friend>  physicalObjs = new ArrayList<>();
        while(ite.hasNext()) {
            Friend tmp = ite.next();
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
        circularOrbit.addTrack(Track.getInstance(addRadius));
        reLoadAll();
    }
    /**
     * 在轨道系统co中移除轨道tk
     */
    public void removeTrack(double rmRadius) {
        circularOrbit.removeTrack(Track.getInstance(rmRadius));
        reLoadAll();
    }
    /**
     * 向co的tk轨道添加运动员Friend
     */
    public void addPhysicalObject(Friend friend,double tkRadius) {
        circularOrbit.addPhysicalObj2Track(friend, Track.getInstance(tkRadius));
        reLoadAll();
    }
    /**
     * 在轨道系统co中移除运动员Friend
     */
    public void removePhysicalObject(String rmObName) {
        Friend friend = getPhysicalObjByObName(x->x.getObName().equals(rmObName)
                ,circularOrbit);
        circularOrbit.removePhysicalObject(friend);
        reLoadAll();
    }

    @Override
    public void initialize() {
        loadConfig();
        initCircularOrbit();
    }

    @Override
    public void draw(JFrame frame) {
        panel = new SocialNetworkPanel(this);
        frame.getContentPane().add(panel);
        circularOrbit.visualize(panel.getDrawPanel());
        reLoadAll();
    }

    public void reLoadAll() {
        CircularOrbitAPIs<CentralUser,Friend> apis = new CircularOrbitAPIs<>();
        CircularOrbit<CentralUser,Friend> nowCircularOrbit = circularOrbit;
        boolean state = apis.checkOrbitAvailable(nowCircularOrbit);
        double entropy = apis.getObjectDistributionEntropy(nowCircularOrbit);
        List<String> trackList = nowCircularOrbit.getTrackRadiusList();
        Iterator<Friend> ite = ((SocialNetworkCircularOrbit) nowCircularOrbit).iterator();
        List<String> physicalObjs = new ArrayList<>();
        List<String> relObjects = new ArrayList<>();
        while(ite.hasNext()) {
            String obName = ite.next().getObName();
            physicalObjs.add(obName);
            relObjects.add(obName);
        }
        relObjects.add(circularOrbit.getCentralUser().getObName());

        panel.reloadGameInfo(state,entropy,trackList,physicalObjs,relObjects,circularOrbit.getFirstTrackFriends());
        nowCircularOrbit.visualize(panel.getDrawPanel());
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    public double getInfoDiffusion(String obName) {
        Friend friend = getPhysicalObjByObName(x->x.getObName().equals(obName),circularOrbit);
        return circularOrbit.getInfoDiffusion().get(friend);
    }

    public void addRelation(String obNameU,String obNameV,double density) {
        CentralUser centralUser;
        Friend friendU,friendV;
        if(circularOrbit.isCenterUser(obNameU)) {
            centralUser = circularOrbit.getCentralUser();
            friendU = getPhysicalObjByObName(x->x.getObName().equals(obNameV),circularOrbit);
            circularOrbit.extendRelationOfCentralObj2TrackObj(centralUser,friendU,density);
            reLoadAll();
            return ;
        }

        if(circularOrbit.isCenterUser(obNameV)) {
            centralUser = circularOrbit.getCentralUser();
            friendU = getPhysicalObjByObName(x->x.getObName().equals(obNameU),circularOrbit);
            circularOrbit.extendRelationOfCentralObj2TrackObj(centralUser,friendU,density);
            reLoadAll();
            return ;
        }

        friendU = getPhysicalObjByObName(x->x.getObName().equals(obNameU),circularOrbit);
        friendV = getPhysicalObjByObName(x->x.getObName().equals(obNameV),circularOrbit);
        circularOrbit.extendRelationOf2TrackObs(friendU,friendV,density);
        circularOrbit.extendRelationOf2TrackObs(friendV,friendU,density);

        reLoadAll();
    }

    public void removeRelation(String obNameU,String obNameV) {
        CentralUser centralUser;
        Friend friendU,friendV;
        if(circularOrbit.isCenterUser(obNameU)) {
            centralUser = circularOrbit.getCentralUser();
            friendU = getPhysicalObjByObName(x->x.getObName().equals(obNameV),circularOrbit);
            circularOrbit.curtailRelationOfCentralObjs2TraObj(centralUser,friendU);
            reLoadAll();
            return ;
        }

        if(circularOrbit.isCenterUser(obNameV)) {
            centralUser = circularOrbit.getCentralUser();
            friendU = getPhysicalObjByObName(x->x.getObName().equals(obNameU),circularOrbit);
            circularOrbit.curtailRelationOfCentralObjs2TraObj(centralUser,friendU);
            reLoadAll();
            return ;
        }

        friendU = getPhysicalObjByObName(x->x.getObName().equals(obNameU),circularOrbit);
        friendV = getPhysicalObjByObName(x->x.getObName().equals(obNameV),circularOrbit);
        circularOrbit.curtailRelationOf2TrackObs(friendU,friendV);
        circularOrbit.curtailRelationOf2TrackObs(friendV,friendU);

        reLoadAll();
    }
    public int getLogicalDistance(String obNameU,String obNameV) {
        Friend fr1 = getPhysicalObjByObName(x->x.getObName().equals(obNameU),circularOrbit);
        Friend fr2 = getPhysicalObjByObName(x->x.getObName().equals(obNameV),circularOrbit);
        return circularOrbit.getLogicalDistance(fr1,fr2);
    }
    public List<String> getSurroudings(String obName) {
//        对于Item事件监听会导致一个Race问题，此时可能申请已经不存在的物体 所以需要捕获Exception 处理
        try {
            if (circularOrbit.isCenterUser(obName))
                return circularOrbit.getFirstTrackFriends();

            Friend fr = getPhysicalObjByObName(x -> x.getObName().equals(obName), circularOrbit);
            return circularOrbit.getSurroundings(fr);
        }catch(Exception e) {
            return new ArrayList<>();
        }
    }
}
