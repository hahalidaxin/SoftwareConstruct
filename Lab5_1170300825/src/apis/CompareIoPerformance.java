package apis;

import applications.socialnetworkcircle.SocialNetworkCircle;
import applications.socialnetworkcircle.iostrategy.SocialNetworkBufferStrategy;
import applications.socialnetworkcircle.iostrategy.SocialNetworkIoStrategy;
import applications.socialnetworkcircle.iostrategy.SocialNetworkNioStrategy;
import applications.socialnetworkcircle.iostrategy.SocialNetworkScannerStrategy;
import applications.trackgame.TrackGame;
import applications.trackgame.assignstrategy.RandomStrategy;
import applications.trackgame.iostrategy.TrackGameBufferIoStrategy;
import applications.trackgame.iostrategy.TrackGameIoStrategy;
import applications.trackgame.iostrategy.TrackGameNioStrategy;
import applications.trackgame.iostrategy.TrackGameScannerIoStrategy;
import java.awt.Font;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import otherdirectory.exception.CheckedException;

/**
 * .
 * 比较不同IO的性能
 * @author hahalidaxin
 */
public class CompareIoPerformance {

  public static void compareTrackGameIo() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    String inputfilename = "src/configfile/TrackGame_Largest.txt";

    //TrackGameBufferIoStrategy
    try {
      TrackGame game = new TrackGame(inputfilename);
      TrackGameIoStrategy strategy = new TrackGameBufferIoStrategy();
      game.inputOrbitInfo(inputfilename, strategy);
      game.divideIntoGroups(new RandomStrategy());
      game.outputOrbitInfo("out/output/trackgame_buffer.txt", strategy);
      dataset.addValue(strategy.getInputTime(),"InputTime","Buffer");
      dataset.addValue(strategy.getOutputTime(),"OutputTime","Buffer");
    } catch (CheckedException e) {
      e.printStackTrace();
    }

    //TrackGameScannerIoStrategy
    try {
      TrackGame game = new TrackGame(inputfilename);
      TrackGameIoStrategy strategy = new TrackGameScannerIoStrategy();
      game.inputOrbitInfo(inputfilename, strategy);
      game.divideIntoGroups(new RandomStrategy());
      game.outputOrbitInfo("out/output/trackgame_scannner.txt", strategy);
      dataset.addValue(strategy.getInputTime(),"InputTime","Scannner");
      dataset.addValue(strategy.getOutputTime(),"OutputTime","Scannner");
    } catch (CheckedException e) {
      e.printStackTrace();
    }

    //TrackGameNioStrategy
    try {
      TrackGame game = new TrackGame(inputfilename);
      TrackGameIoStrategy strategy = new TrackGameNioStrategy();
      game.inputOrbitInfo(inputfilename, strategy);
      game.divideIntoGroups(new RandomStrategy());
      game.outputOrbitInfo("out/output/trackgame_nio.txt", strategy);
      dataset.addValue(strategy.getInputTime(),"InputTime","Nio");
      dataset.addValue(strategy.getOutputTime(),"OutputTime","Nio");
    } catch (CheckedException e) {
      e.printStackTrace();
    }

    //创建主题样式
    StandardChartTheme mChartTheme = new StandardChartTheme("CN");
    //设置标题字体
    mChartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 20));
    //设置轴向字体
    mChartTheme.setLargeFont(new Font("宋体", Font.CENTER_BASELINE, 15));
    //设置图例字体
    mChartTheme.setRegularFont(new Font("宋体", Font.CENTER_BASELINE, 15));
    //应用主题样式
    ChartFactory.setChartTheme(mChartTheme);

    JFreeChart chart = ChartFactory.createBarChart("TrackGame - IO性能比较", "IO类型", "执行时间",
        dataset, PlotOrientation.VERTICAL, true, true, true);
    ChartPanel chartPanel = new ChartPanel(chart, true); // 这里也可以用chartFrame,可以直接生成一个独立的Frame
    JFrame frame = new JFrame("IO时间比较图");
    frame.add(chartPanel);           //添加柱形图
    frame.setBounds(50, 50, 900, 600);
    frame.setVisible(true);
  }

  public static void compareSocialNetworkIo() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    String inputfilename = "src/configfile/SocialNetworkCircle_Largest.txt";

    SocialNetworkCircle.INPUTCHECKON = false;
    //TrackGameBufferIoStrategy
    try {
      SocialNetworkCircle game = new SocialNetworkCircle(inputfilename);
      SocialNetworkIoStrategy strategy = new SocialNetworkBufferStrategy();
      game.inputOrbitInfo(inputfilename, strategy);
      game.initCircularOrbit();
      game.outputOrbitInfo("out/output/socialnetwork_buffer.txt", strategy);
      dataset.addValue(strategy.getInputTime(),"InputTime","Buffer");
      dataset.addValue(strategy.getOutputTime(),"OutputTime","Buffer");
    } catch (CheckedException e) {
      e.printStackTrace();
    }

    //TrackGameScannerIoStrategy
    try {
      SocialNetworkCircle game = new SocialNetworkCircle(inputfilename);
      SocialNetworkIoStrategy strategy = new SocialNetworkScannerStrategy();
      game.inputOrbitInfo(inputfilename, strategy);
      game.initCircularOrbit();
      game.outputOrbitInfo("out/output/socialnetwork_scannner.txt", strategy);
      dataset.addValue(strategy.getInputTime(),"InputTime","Scannner");
      dataset.addValue(strategy.getOutputTime(),"OutputTime","Scannner");
    } catch (CheckedException e) {
      e.printStackTrace();
    }

    //TrackGameNioStrategy
    try {
      SocialNetworkCircle game = new SocialNetworkCircle(inputfilename);
      SocialNetworkIoStrategy strategy = new SocialNetworkNioStrategy();
      game.inputOrbitInfo(inputfilename, strategy);
      game.initCircularOrbit();
      game.outputOrbitInfo("out/output/socialnetwork_nio.txt", strategy);
      dataset.addValue(strategy.getInputTime(),"InputTime","Nio");
      dataset.addValue(strategy.getOutputTime(),"OutputTime","Nio");
    } catch (CheckedException e) {
      e.printStackTrace();
    }

    //创建主题样式
    StandardChartTheme mChartTheme = new StandardChartTheme("CN");
    //设置标题字体
    mChartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 20));
    //设置轴向字体
    mChartTheme.setLargeFont(new Font("宋体", Font.CENTER_BASELINE, 15));
    //设置图例字体
    mChartTheme.setRegularFont(new Font("宋体", Font.CENTER_BASELINE, 15));
    //应用主题样式
    ChartFactory.setChartTheme(mChartTheme);

    JFreeChart chart = ChartFactory.createBarChart("SocialNetwork - IO性能比较", "IO类型", "执行时间",
        dataset, PlotOrientation.VERTICAL, true, true, true);
    ChartPanel chartPanel = new ChartPanel(chart, true); // 这里也可以用chartFrame,可以直接生成一个独立的Frame
    JFrame frame = new JFrame("IO时间比较图");
    frame.add(chartPanel);           //添加柱形图
    frame.setBounds(50, 50, 900, 600);
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    compareSocialNetworkIo();
    //compareTrackGameIo();
  }

}
