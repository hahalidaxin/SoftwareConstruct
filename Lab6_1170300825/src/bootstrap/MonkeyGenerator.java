package bootstrap;

import adts.Ladder;
import adts.Monkey;
import adts.Rung;
import gui.ContentPanel;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import strategy.CrossriverStrategy;
import strategy.PushspeedStrategy;
import strategy.SimpleStrategy;
import strategy.SmartStrategy;
import sun.java2d.pipe.SpanShapeRenderer;
import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * .
 *
 * @author hahalidaxin
 */
public class MonkeyGenerator {
  public static CountDownLatch threadlatch;
  public static DefaultCategoryDataset throughRateDataSet = new DefaultCategoryDataset();
  public static DefaultCategoryDataset fairRateDataSet = new DefaultCategoryDataset();
  public Logger logger = Logger.getLogger(MonkeyGenerator.class.getName());
  public static int STEPTIME = 50;
  public static String CONFIGPATH = "src/resources/configfiles/config0.txt";
  public  List<Monkey> monkeys = new ArrayList<>();
  public List<Ladder> ladders = new ArrayList<>();
  public Map<Ladder, List<Monkey>> ladder2Monkey = new HashMap<>();

  //Ladder的数目
  public int n;
  //每个Ladder有多少个Rung
  public int h;
  //每隔t秒钟生成猴子
  private int t;
  //一共需要生成N个猴子
  public int N;
  public int total;
  //每隔t秒钟生成k个猴子
  private int k;
  //猴子的最大可能速度
  private int MV;

  private int idCount = 0;

  public  int crossedCount = 0;
  public Object crossedCountLock = new Object();
  public double throughputRate;
  public double fairRate;

  private String filepath;
  private String dataName;

  private List<CrossriverStrategy> strategies;

  public MonkeyGenerator(String dataName,String filepath) {
    this.filepath = filepath;
    this.dataName = dataName;
  }

  public void setStrategies(List<CrossriverStrategy> strategies) {
    this.strategies = strategies;
  }


  public void loadConfig() {
    InputStreamReader stream = null;
    BufferedReader reader = null;

    try {
      stream = new InputStreamReader(new FileInputStream(filepath));
      reader = new BufferedReader(stream);
      n = Integer.parseInt(reader.readLine().trim());
      h = Integer.parseInt(reader.readLine().trim());
      t = Integer.parseInt(reader.readLine().trim());
      N = Integer.parseInt(reader.readLine().trim());
      k = Integer.parseInt(reader.readLine().trim());
      MV = Integer.parseInt(reader.readLine().trim());

      total = N;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (stream != null) {
          stream.close();
        }
        if (reader != null) {
          reader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    //构造ladders
    for (int i = 1; i <= n; i++) {
      List<Rung> rungs = new ArrayList<>();
      for (int j = 1; j <= h; j++) {
        rungs.add(new Rung(j));
      }
      Ladder ladder = new Ladder(i, rungs);
      ladder2Monkey.put(ladder, new ArrayList<>());
      ladders.add(ladder);
    }
  }


  private Map<Integer, List<Monkey>> monkeyMap = new HashMap<>();
  private Set<Integer> timeSet = new HashSet<>();
  private int maxTime = 0;

  public void loadConfigV3() {
    InputStreamReader stream = null;
    BufferedReader reader = null;

    try {
      stream = new InputStreamReader(new FileInputStream(filepath));
      reader = new BufferedReader(stream);

      String regex;
      Matcher matcher;

      String line = reader.readLine();
      regex = "n=(\\d+)";
      matcher = Pattern.compile(regex).matcher(line);
      if (matcher.find()) {
        n = Integer.parseInt(matcher.group(1));
      } else {
        assert false : "未能匹配";
      }

      line = reader.readLine();
      regex = "h=(\\d+)";
      matcher = Pattern.compile(regex).matcher(line);
      if (matcher.find()) {
        h = Integer.parseInt(matcher.group(1));
      } else {
        assert false : "未能匹配";
      }

      N = 0;
      line = reader.readLine();
      while (line != null) {
        regex = "monkey=<(\\d+),(\\d+),((?:L->R)|(?:R->L)),(\\d+)>";
        matcher = Pattern.compile(regex).matcher(line.trim());
        if (matcher.find()) {
          N += 1;
          int time = Integer.parseInt(matcher.group(1));
          maxTime = Math.max(time, maxTime);
          if (!timeSet.contains(time)) {
            timeSet.add(time);
          }
          int id = Integer.parseInt(matcher.group(2));
          String dir = matcher.group(3);
          int vel = Integer.parseInt(matcher.group(4));
          Monkey monkey = new Monkey(this, strategies, id, vel, dir, time);
          if (!monkeyMap.containsKey(time)) {
            monkeyMap.put(time, new ArrayList<>());
          }
          monkeyMap.get(time).add(monkey);
        } else {
          assert false : "未能正常匹配";
        }
        line = reader.readLine();
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (stream != null) {
          stream.close();
        }
        if (reader != null) {
          reader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    //构造ladders
    for (int i = 1; i <= n ; i++) {
      List<Rung> rungs = new ArrayList<>();
      for (int j = 1; j <= h ; j++) {
        rungs.add(new Rung(j));
      }
      Ladder ladder = new Ladder(i,rungs);
      ladder2Monkey.put(ladder,new ArrayList<>());
      ladders.add(ladder);
    }
  }

  public int timeCount = 0;
  public void genMonkeys() {
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    final String jobId = "genMonkey";
    final Map<String, Future> futureMap = new HashMap<>();
    Random random = new Random();

    Future future = service.scheduleAtFixedRate(() -> {
      //如果已经生完 则停止生猴子线程
      if (total <= 0) {
        Future thisFuture = futureMap.get(jobId);

        if(thisFuture!=null) {
          thisFuture.cancel(true);
          service.shutdown();
        }
      }

      int genlimit = Math.min(total, k);
      for (int i = 0; i < genlimit; i++) {
        int vel = random.nextInt(MV) + 1;
        String direction = "L->R";
        if (random.nextBoolean()) {
          direction = "R->L";
        }
        Monkey monkey = new Monkey(this, strategies, ++idCount, vel, direction, timeCount);
        logger.info(monkey);
        monkeys.add(monkey);
        monkey.startCrossRiverThread();
      }
      total -= k;
      timeCount += t;
    }, 0, t*STEPTIME, TimeUnit.MILLISECONDS);
    futureMap.put(jobId, future);

  }

  public void genMonkeysV3() {
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    final String jobId = "genMonkey";
    final Map<String, Future> futureMap = new HashMap<>();

    Future future = service.scheduleAtFixedRate(() -> {
      if (timeSet.contains(timeCount)) {
        for (Monkey monkey : monkeyMap.get(timeCount)) {
          monkeys.add(monkey);
          monkey.startCrossRiverThread();
        }
      }
      if (timeCount > maxTime) {
        futureMap.get(jobId).cancel(true);
        service.shutdown();
      }
      timeCount += 1;

    }, 0, STEPTIME, TimeUnit.MILLISECONDS);
    futureMap.put(jobId, future);
  }

  private boolean displayFlag = false;
  private StringBuilder infosb;
  public String getInfoOfGame() {
    if(displayFlag) {
      return infosb.toString();
    }
    StringBuilder sb = new StringBuilder();
    int T = 0;
    int msize = monkeys.size();
    int sum = 0;
    for (int i=0;i<msize;i++) {
      Monkey monkeyU = monkeys.get(i);
      for(int j=i+1;j<msize;j++) {
        Monkey monkeyV = monkeys.get(j);
        if ((monkeyU.getId() - monkeyV.getId()) * (monkeyU.getDeadTime() - monkeyV.getDeadTime()) > 0) {
          sum += 1;
        } else {
          sum -= 1;
        }
      }
      T = Math.max(T, monkeyU.getDeadTime());
    }
    throughputRate =(double)N/(double)T ;
    fairRate = (double)sum/(double)(N*(N-1)/2);
    throughRateDataSet.addValue(throughputRate,strategies.get(0).toString(),dataName);
    fairRateDataSet.addValue(fairRate,strategies.get(0).toString(),dataName);

    sb.append(String.format("吞吐率为：%.6f%n", throughputRate));
    sb.append(String.format("公平性为：%.6f%n", fairRate));
    infosb = sb;
    logger.error(filepath+"\n"+strategies.get(0).toString()+"\n"+sb.toString());
    displayFlag = true;
    if (threadlatch != null) {
      threadlatch.countDown();
    }
    return sb.toString();
  }


  public void showGui() {
    JFrame jframe = new JFrame("猴子过河");
    jframe.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        //如果点击GUI面板的叉号则代表停止当前运行的程序
        System.exit(0);
      }
    });
    jframe.setSize(new Dimension(1400, 850));
    ContentPanel jpanel = new ContentPanel(this);
    jpanel.setPreferredSize(new Dimension(1400, 850));
    jframe.getContentPane().add(jpanel);
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    service.scheduleAtFixedRate(()->{
      jpanel.refresh();
    },0,STEPTIME/2,TimeUnit.MILLISECONDS);
    jframe.setVisible(true);
  }

  public static void showCompareChart() {

    DefaultCategoryDataset throughData = new DefaultCategoryDataset();
    DefaultCategoryDataset fairData = new DefaultCategoryDataset();

    for (int i = 1; i <= 1; i++) {
      String filename = "f" + i;
      String algrithmname ="";
      for (int j = 0; j <= 2; j++) {
        switch(j) {
          case 0:
            algrithmname = "SimpleStrategy";
            break;
          case 1:
            algrithmname = "PushspeedStrategy";
            break;
          case 2:
            algrithmname = "SmartStrategy";
            break;
        }
        int row1 = throughRateDataSet.getRowIndex(algrithmname);
        int col1 = throughRateDataSet.getColumnIndex(filename);
        throughData.addValue(throughRateDataSet.getValue(row1,col1),algrithmname,filename);

        row1 = fairRateDataSet.getRowIndex(algrithmname);
        col1 = fairRateDataSet.getColumnIndex(filename);
        fairData.addValue(fairRateDataSet.getValue(row1,col1),algrithmname,filename);
      }
    }

    //创建主题样式
    StandardChartTheme mchartTheme = new StandardChartTheme("CN");
    //设置标题字体
    mchartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 20));
    //设置轴向字体
    mchartTheme.setLargeFont(new Font("宋体", Font.CENTER_BASELINE, 15));
    //设置图例字体
    mchartTheme.setRegularFont(new Font("宋体", Font.CENTER_BASELINE, 15));
    //应用主题样式
    ChartFactory.setChartTheme(mchartTheme);

    JFreeChart throughRateChart = ChartFactory.createBarChart("吞吐率比较", "文件名称", "执行时间",
        throughData, PlotOrientation.VERTICAL, true, true, true);
    JFreeChart fairRateChart = ChartFactory.createBarChart("公平性比较", "文件名称", "执行时间",
        fairData, PlotOrientation.VERTICAL, true, true, true);
    ChartPanel throughChartPanel = new ChartPanel(throughRateChart, true); // 这里也可以用chartFrame,可以直接生成一个独立的Frame
    ChartPanel fairChartPanel = new ChartPanel(fairRateChart, true); // 这里也可以用chartFrame,可以直接生成一个独立的Frame
    JFrame frame1 = new JFrame("吞吐率比较图");
    frame1.add(throughChartPanel);           //添加柱形图
    frame1.setBounds(50, 50, 900, 600);
    frame1.setVisible(true);
    JFrame frame2 = new JFrame("公平性比较图");
    frame2.add(fairChartPanel);           //添加柱形图
    frame2.setBounds(50, 50, 900, 600);
    frame2.setVisible(true);

    //JFreeChart throughRateChart2 = ChartFactory.createBarChart("吞吐率比较-real", "文件名称", "执行时间",
    //    throughRateDataSet, PlotOrientation.VERTICAL, true, true, true);
    //ChartPanel throughChartPanel2 = new ChartPanel(throughRateChart2, true); // 这里也可以用chartFrame,可以直接生成一个独立的Frame
    //JFrame frame12 = new JFrame("吞吐率比较图");
    //frame12.add(throughChartPanel2);           //添加柱形图
    //frame12.setBounds(50, 50, 900, 600);
    //frame12.setVisible(true);
  }

  public static void compare() {
    STEPTIME = 50;
    MonkeyGenerator.threadlatch = new CountDownLatch(3);
    final int exp = 2;
    for (int i = 1; i <= 1; i++) {
      for (int j = 0; j <= 2; j++) {
        final int fi = i;
        final int fj = j;
        //new Thread(()->{
        //String filepath = String.format("src/resources/configfiles/configExp%d_%d.txt",exp, fi);
        String filepath = String.format("src/resources/configfiles/configPressure%d_%d.txt",exp, fi);
        //String filepath = "src/resources/configfiles/configPressure1_1.txt";
        //System.out.println("出现"+filepath);
        List<CrossriverStrategy> strategies;
        MonkeyGenerator generator = new MonkeyGenerator("f"+i,filepath);
        if (fj == 0) {
          strategies = new ArrayList<>(Arrays.asList(new SimpleStrategy(generator)));
        } else if (fj == 1) {
          strategies = new ArrayList<>(Arrays.asList(new PushspeedStrategy(generator)));
        } else {
          strategies = new ArrayList<>(Arrays.asList(new SmartStrategy(generator)));
        }
        generator.setStrategies(strategies);
        generator.loadConfig();
        generator.genMonkeys();
        //}).start();
      }
    }
    try {
      threadlatch.await();
      Thread.sleep(1000);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
  //  freechart显示比较结果
    showCompareChart();
  }

  public static void GeneratorMain() {
    STEPTIME = 50;
    MonkeyGenerator generator = new MonkeyGenerator("generatorMain","src/resources/configfiles/configExp1_1.txt");
    List<CrossriverStrategy> strategies = new ArrayList<>();
    strategies.add(new PushspeedStrategy(generator));
    generator.setStrategies(strategies);
    generator.loadConfig();
    generator.genMonkeys();
    generator.showGui();
  }

  public static void GeneratorMainV3() {
    STEPTIME = 1000;
    //for(int i=0;i<10;i++) {
      MonkeyGenerator generator = new MonkeyGenerator("generatorMainV3", "src/resources/competitionfiles/Competition_3.txt");
      SmartStrategy.LOWSPEED = 1;
      SmartStrategy.MIDSPEED = 3;
      List<CrossriverStrategy> strategies = new ArrayList<>();
      strategies.add(new SmartStrategy(generator));
      generator.setStrategies(strategies);
      generator.loadConfigV3();
      generator.genMonkeysV3();
      generator.showGui();
    //}
  }


  public static void main(String[] args) {
    //GeneratorMain();
    GeneratorMainV3();
    //compare();
  }

}
