package apis;

import java.awt.Font;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * .
 *
 * @author hahalidaxin
 */
public class jFreeChartTest {


  /**
   * @param args
   */
  public static void main(String[] args) {

    JFreeChart chart = ChartFactory.createBarChart("统计", "水果", "水果种类",
        getDataSet(), PlotOrientation.VERTICAL, true, true, true);

    // 从这里开始
    CategoryPlot plot = chart.getCategoryPlot();// 获取图表区域对象
    CategoryAxis domainAxis = plot.getDomainAxis(); // 水平底部列表
    domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 14)); // 水平底部标题
    domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12)); // 垂直标题
    ValueAxis rangeAxis = plot.getRangeAxis();// 获取柱状
    rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
    chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
    chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));// 设置标题字体

    ChartPanel chartPanel = new ChartPanel(chart, true); // 这里也可以用chartFrame,可以直接生成一个独立的Frame
    JFrame frame = new JFrame("Java数据统计图");
    frame.add(chartPanel);           //添加柱形图
    frame.setBounds(50, 50, 900, 600);
    frame.setVisible(true);
  }

  private static CategoryDataset getDataSet() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    dataset.addValue(100, "北京", "苹果");
    dataset.addValue(100, "上海", "苹果");
    dataset.addValue(100, "广州", "苹果");
    dataset.addValue(200, "北京", "梨子");
    dataset.addValue(200, "上海", "梨子");
    dataset.addValue(200, "广州", "梨子");
    dataset.addValue(300, "北京", "葡萄");
    dataset.addValue(300, "上海", "葡萄");
    dataset.addValue(300, "广州", "葡萄");
    dataset.addValue(400, "北京", "香蕉");
    dataset.addValue(400, "上海", "香蕉");
    dataset.addValue(400, "广州", "香蕉");
    dataset.addValue(500, "北京", "荔枝");
    dataset.addValue(500, "上海", "荔枝");
    dataset.addValue(500, "广州", "荔枝");
    return dataset;
  }

}
