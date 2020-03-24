package util;

import java.awt.Font;
import javax.swing.JFrame;

import dao.impl.OtherDaoImpl;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChartDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        setChart("成绩直方图", "成绩分布", "人数");
    }

    private static void setChart(String title, String xName, String yName){
        JFreeChart chart = ChartFactory.createBarChart(title, xName, yName, getChartDataSet());

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
        JFrame frame=new JFrame("成绩分布图");
        frame.add(chartPanel);           //添加柱形图
        frame.setBounds(50, 50, 900, 600);
        frame.setVisible(true);
    }
    private static CategoryDataset getChartDataSet() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int score_0_60 = new OtherDaoImpl().analysis(172102, 00003, 0, 60);
        int score_60_75 = new OtherDaoImpl().analysis(172102, 00003, 60, 75);
        int score_75_90 = new OtherDaoImpl().analysis(172102, 00003, 75, 90);
        int score_90_100 = new OtherDaoImpl().analysis(172102, 00003, 90, 100);
        int score_100 = new OtherDaoImpl().analysis(172102, 00003, 100, 101);

        dataset.addValue(score_75_90, "aaaa","数据结构");

        /*dataset.addValue(10, "北京", "苹果");*/
        return dataset;
    }

}