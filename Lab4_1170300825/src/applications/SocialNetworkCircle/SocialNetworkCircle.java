package applications.SocialNetworkCircle;

import APIs.CircularOrbitAPIs;
import applications.SocialNetworkCircle.gui.SocialNetworkPanel;
import centralObject.CommonObject;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import circularOrbit.Drawable;
import org.apache.log4j.Logger;
import otherDirectory.Relation;
import otherDirectory.exception.*;
import track.Track;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SocialNetworkCircle implements Drawable {

    private static Logger logger = Logger.getLogger(SocialNetworkCircle.class.getName());

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

//        String centralUserPattern = "CentralUser\\s*::=\\s*<([A-Za-z0-9]+),\\s*(\\d+),\\s*([MF])>";
//        String friendPattern = "Friend\\s*::=\\s*<([A-Za-z0-9]+),\\s*(\\d+),\\s*([MF])>";
//                使用?:表示不捕获该括号内的元素（非捕获组），如果出现嵌套的括号，匹配规则是由左向右，由外向内
//        String socialTiePattern = "SocialTie\\s*::=\\s*<([A-Za-z0-9]+),\\s*([A-Za-z0-9]+),\\s*(0(?:\\.\\d{1,3})?|1(?:\\.0{1,3})?)>";

        if(label.equals("CentralUser")) {
            splitStrs = content.split(",");
            if(splitStrs.length!=3) {
                return "匹配错误：非法CentralUser分量数目";
            }
            if(!testMatch(splitStrs[0].trim(),"(<[A-Za-z0-9]+)")) {
                return "匹配错误：非法CentralUser用户名";
            }
            if(!testMatch(splitStrs[1].trim(),"(\\\\d+)")) {
                return "匹配错误：非法CentralUser年龄";
            }
            if(!testMatch(splitStrs[2].trim(),"([MF])")) {
                return "匹配错误：非法CentralUser性别";
            }
        } else if(label.equals("Friend")) {
            splitStrs = content.split(",");
            if(splitStrs.length!=3) {
                return "匹配错误：非法Friend分量数目";
            }
            if(!testMatch(splitStrs[0].trim(),"(<[A-Za-z0-9]+)")) {
                return "匹配错误：非法Friend名称";
            }
            if(!testMatch(splitStrs[1].trim(),"(\\d+)")) {
                return "匹配错误：非法Friend年龄";
            }
            if(!testMatch(splitStrs[2].trim(),"([MF]>)")) {
                return "匹配错误：非法Friend性别";
            }
        } else if(label.equals("SocialTie")) {
            splitStrs =  content.split(",");
            if(splitStrs.length!=3) {
                return "匹配错误：非法SocialTie分量数目";
            }
            if(!testMatch(splitStrs[0].trim(),"(<[A-Za-z0-9]+)")) {
                return "匹配错误：非法SociaTie用户名A";
            }
            if(!testMatch(splitStrs[1].trim(),"([A-Za-z0-9]+)")) {
                return "匹配错误：非法SocialTie用户名B";
            }
            if(!testMatch(splitStrs[2].trim(),"(0(?:\\.\\d{1,3})?|1(?:\\.0{1,3})?>)")) {
                return "匹配错误：非法SociaTie用户亲密度";
            }
//            需要特殊判断0的情况
            if(Double.parseDouble(splitStrs[2].trim())==0) {
                return "匹配错误：非法SocialTie用户亲密度";
            }
        } else {
            return "匹配失败：标签错误";
        }
        return "匹配失败：未知类型错误";
    }


    /**
     * 读入文件配置
     * 利用正则表达式进行解析
     * */
    public void loadConfig() throws CheckedException {
        BufferedReader reader = null;
        try {
//            reader = new BufferedReader(new FileReader( new File(filename)));|
            InputStreamReader isr = new InputStreamReader(new FileInputStream(filename), "UTF-8");
            reader = new BufferedReader(isr);
            String line = reader.readLine();
//            String[] splitStrs;
            while(line!=null) {
                if(line.length()==0) {
                    line = reader.readLine();
                    continue;
                }
                line = line.trim();

//                System.out.println(line);
                String centralUserPattern = "^CentralUser\\s*::=\\s*<([A-Za-z0-9]+),\\s*(\\d+),\\s*([MF])>$";
                String friendPattern = "^Friend\\s*::=\\s*<([A-Za-z0-9]+),\\s*(\\d+),\\s*([MF])>$";
//                使用?:表示不捕获该括号内的元素（非捕获组），如果出现嵌套的括号，匹配规则是由左向右，由外向内
                String socialTiePattern = "^SocialTie\\s*::=\\s*<([A-Za-z0-9]+),\\s*([A-Za-z0-9]+),\\s*(0(?:\\.\\d{1,3})?|1(?:\\.0{1,3})?)>$";
                // 创建 Pattern 对象
                Matcher centralUserMatcher = Pattern.compile(centralUserPattern).matcher(line);
                Matcher friendMatcher = Pattern.compile(friendPattern).matcher(line);
                Matcher socialTieMatcher = Pattern.compile(socialTiePattern).matcher(line);

                if(centralUserMatcher.find()) {
                    String userName = centralUserMatcher.group(1);
                    int age = Integer.parseInt(centralUserMatcher.group(2));
                    String gender = centralUserMatcher.group(3);
                    centralUser = CentralUser.getInstance(userName,gender,age);
                } else if(friendMatcher.find()) {
                    String userName = friendMatcher.group(1);
                    int age = Integer.parseInt(friendMatcher.group(2));
                    String gender = friendMatcher.group(3);
                    UncheckedException.assertTrue(friendList.stream()
                            .filter(x->x.getObName().equals(userName))
                            .count()==0,"存在同名的Friend");
                    friendList.add(Friend.getInstance(userName,gender,age));
                } else if(socialTieMatcher.find()) {
                    UncheckedException.assertTrue(centralUser!=null,"输入数据顺序错误，centralUser为Null");

                    socialTieEntityList.add(new SocialTieEntity(socialTieMatcher.group(1),socialTieMatcher.group(2),
                            Double.parseDouble(socialTieMatcher.group(3))));
                } else {
                    GrammarException.assertTrue(false,getReadFaultReason(line));
                }

                line = reader.readLine();
            }

            Map<String,Set<String>> friendEdgePool = new HashMap<>();

            for(SocialTieEntity socialTie:socialTieEntityList) {
                String userNameA = socialTie.getUserNameA();
                String userNameB = socialTie.getUserNameB();

                if(!friendEdgePool.containsKey(userNameA)) {
                    friendEdgePool.put(userNameA,new HashSet<>());
                }
                if(!friendEdgePool.containsKey(userNameB)) {
                    friendEdgePool.put(userNameB,new HashSet<>());
                }
                SameLabelException.assertTrue(!friendEdgePool.get(userNameA).contains(userNameB),"两个用户之间不能存在两条边");

                friendEdgePool.get(userNameA).add(userNameB);
                friendEdgePool.get(userNameB).add(userNameA);

                SameLabelException.assertTrue(!userNameA.equals(userNameB),"出现一条自己连向自己的边");

                double socialDensity = socialTie.getSocianDensity();

                DependencyException.assertTrue(getNameIndex(Arrays.asList(centralUser),userNameA)!=-1
                        || getNameIndex(friendList,userNameA)!=-1,"没有声明"+userNameA) ;
                DependencyException.assertTrue(getNameIndex(Arrays.asList(centralUser),userNameB)!=-1
                        || getNameIndex(friendList,userNameB)!=-1,"没有声明"+userNameB);
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
        } catch(CheckedException e) {
            logger.error(e);
            throw e;
        }finally {
            try {
                if(reader!=null)
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
            if(!queue.offer(st)) {
                UncheckedException.assertTrue(false,"queue.offer失败");
            }
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
                if(!queue.offer(ps)) {
                    UncheckedException.assertTrue(false,"queue.offer失败");
                }
            }
        }
    }

    void bfs2GetFlag(Friend source , Map<Friend,Integer> flag, int flagIndex) {
        Queue<Friend> queue = new LinkedBlockingQueue<>();

        if(!queue.offer(source)) {
            UncheckedException.assertTrue(false,"queue.offer失败");
        }
        flag.put(source,flagIndex);

        while(!queue.isEmpty()) {
            Friend topPerson = queue.poll();
            List<Friend> neighborList = relOf2FriendObjs.getOrDefault(topPerson,new ArrayList<>())
                    .stream()
                    .map(Relation::getObjv).collect(Collectors.toList());
            for(Friend ps:neighborList) if(!flag.containsKey(ps)) {
                flag.put(ps,flagIndex);
                if(!queue.offer(ps)) {
                    UncheckedException.assertTrue(false,"queue.offer失败");
                }
            }
        }
    }

    private void getPhysicalMap() {
        //        将所有其他不与centralUser联通的节点子联通图取代表元与中心点连接，紧密度为0
        Map<Friend,Integer> flag = new HashMap<>();
        int flagIndex = 0;

        for(Relation<CentralUser,Friend> rel:relOfCentralObjs) {
            Friend st = rel.getObjv();
            if(!relOf2FriendObjs.containsKey(st)) {
                relOf2FriendObjs.put(st,new ArrayList<>());
            }
            bfs2GetFlag(st,flag,flagIndex);
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

        ConcreteCircularOrbit<CentralUser, Friend> tmp = builder.getConcreteCircularOrbit();
        if(tmp instanceof SocialNetworkCircularOrbit) {
            circularOrbit = (SocialNetworkCircularOrbit) tmp;
        }
    }

    public SocialNetworkCircularOrbit getCircularOrbit() {
        return circularOrbit;
    }



    private static class SocialTieEntity {
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
        try {
            return physicalObjs.stream().filter(predicate)
                    .collect(Collectors.toList()).get(0);
        } catch(ArrayIndexOutOfBoundsException e) {
            UncheckedException.assertTrue(false,"没有该Friend");
        }
        return null;
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
    public void initialize() throws CheckedException {
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
        if(panel==null) return ;
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
        circularOrbit.curtailRelationOf2TrackObs(friendV,friendU) ;

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
