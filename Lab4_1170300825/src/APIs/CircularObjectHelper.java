package APIs;

import APIs.gui.LoggerFrame;
import circularOrbit.CircularOrbit;

import javax.swing.*;

public class CircularObjectHelper {

    public static void visualize(CircularOrbit c) {
        JFrame frame = new JFrame();
        frame.setSize(800,800);
        frame.getContentPane().add(c.visualizeContentPanel());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        LoggerFrame frame = new LoggerFrame();
        frame.setSize(750,800);
        frame.setVisible(true);

//        JFrame jf = new JFrame("测试窗口");
//        jf.setSize(250, 250);
//        jf.setLocationRelativeTo(null);
//        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

//        // 创建文本区域组件
//        JTextArea textArea = new JTextArea();
//        textArea.setLineWrap(true);                         // 自动换行
//        textArea.setFont(new Font(null, Font.PLAIN, 18));   // 设置字体
//
//        // 创建滚动面板, 指定滚动显示的视图组件(textArea), 垂直滚动条一直显示, 水平滚动条从不显示
//        JScrollPane scrollPane = new JScrollPane(
//                textArea,
//                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
//                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
//        );
//
//        jf.setContentPane(scrollPane);
//        jf.setVisible(true);
    }
}
