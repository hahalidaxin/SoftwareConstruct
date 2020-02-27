/*
 * Created by JFormDesigner on Thu May 16 21:56:55 CST 2019
 */

package APIs.gui;

import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;
import otherDirectory.logparser.LogParser;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;

/**
 * @author Brainrain
 */
public class LoggerFrame extends JFrame {
    private static Logger logger = Logger.getLogger(LoggerFrame.class.getName());
    static final String ANY = "任意";
    LogParser parser = new LogParser();

    public LoggerFrame() {
        initComponents();
        initComboBoxContent();
    }

    private void initComboBoxContent() {
        this.comboBoxEType.addItem(ANY);
        parser.getAllETypes().forEach(et->this.comboBoxEType.addItem(et));
        this.comboBoxClass.addItem(ANY);
        parser.getAllClassNames().forEach(cn->this.comboBoxClass.addItem(cn));
        this.comboBoxMethod.addItem(ANY);
        parser.getAllMethodNames().forEach(mn->this.comboBoxMethod.addItem(mn));
    }

    private void buttonSelectMousePressed(MouseEvent e) {
        String etype = (String)this.comboBoxEType.getSelectedItem();
        String classname = (String) this.comboBoxClass.getSelectedItem();
        String methodname = (String) this.comboBoxMethod.getSelectedItem();
        String dateduration = (String) this.textAreaDate.getText();
        try {
            String content = parser.getFilterLogs(it -> {
                boolean ans = true;
                if (!etype.equals(ANY)) {
                    ans = ans && it.getLogType().equals(etype);
                }
                if (!classname.equals(ANY)) {
                    ans = ans && it.getClassName().equals(classname);
                }
                if (!methodname.equals(ANY)) {
                    ans = ans && it.getMethodName().equals(methodname);
                }
                if (dateduration != null && dateduration.length() != 0) {
                    Instant timestart = Instant.parse(dateduration.split(",")[0].trim());
                    Instant timeend = Instant.parse(dateduration.split(",")[1].trim());
                    Instant itTime = it.getTime();
                    ans = ans && itTime.compareTo(timestart) >= 0 && itTime.compareTo(timeend) <= 0;
                }
                return ans;
            });
            this.textAreaLogContent.setRows(parser.getLogCount());
            this.textAreaLogContent.setText(content);
        } catch(RuntimeException exept) {
            logger.error("date输入格式错误");
            JOptionPane.showMessageDialog(this,"date输入格式错误");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        buttonSelect = new JButton();
        separator1 = new JSeparator();
        label2 = new JLabel();
        comboBoxEType = new JComboBox();
        label3 = new JLabel();
        comboBoxClass = new JComboBox();
        label4 = new JLabel();
        comboBoxMethod = new JComboBox();
        label5 = new JLabel();
        textAreaDate = new JTextArea();
        label6 = new JLabel();
        scrollPane1 = new JScrollPane();
        textAreaLogContent = new JTextArea();

        //======== this ========
        setMinimumSize(new Dimension(600, 800));
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("\u7a0b\u5e8f\u8fd0\u884c\u65e5\u5fd7");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 10f));
        contentPane.add(label1, "cell 2 0 3 1");

        //---- buttonSelect ----
        buttonSelect.setText("\u7b5b\u9009");
        buttonSelect.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonSelectMousePressed(e);
            }
        });
        contentPane.add(buttonSelect, "cell 6 0");
        contentPane.add(separator1, "cell 2 1");

        //---- label2 ----
        label2.setText("\u7ea7\u522b");
        contentPane.add(label2, "cell 3 2");
        contentPane.add(comboBoxEType, "cell 4 2");

        //---- label3 ----
        label3.setText("\u7c7b\u540d");
        contentPane.add(label3, "cell 3 3");
        contentPane.add(comboBoxClass, "cell 4 3");

        //---- label4 ----
        label4.setText("\u65b9\u6cd5\u540d");
        contentPane.add(label4, "cell 5 3");
        contentPane.add(comboBoxMethod, "cell 6 3");

        //---- label5 ----
        label5.setText("\u65e5\u671f\u533a\u95f4");
        contentPane.add(label5, "cell 3 5");

        //---- textAreaDate ----
        textAreaDate.setPreferredSize(new Dimension(20, 25));
        contentPane.add(textAreaDate, "cell 4 5");

        //---- label6 ----
        label6.setText("(Instant\u5b57\u4e32\u683c\u5f0f)");
        contentPane.add(label6, "cell 5 5");

        //======== scrollPane1 ========
        {
            scrollPane1.setBorder(new LineBorder(Color.lightGray, 2));

            //---- textAreaLogContent ----
            textAreaLogContent.setPreferredSize(new Dimension(560, 600));
            textAreaLogContent.setEditable(false);
            textAreaLogContent.setRows(258);
            scrollPane1.setViewportView(textAreaLogContent);
        }
        contentPane.add(scrollPane1, "cell 2 8 6 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JButton buttonSelect;
    private JSeparator separator1;
    private JLabel label2;
    private JComboBox comboBoxEType;
    private JLabel label3;
    private JComboBox comboBoxClass;
    private JLabel label4;
    private JComboBox comboBoxMethod;
    private JLabel label5;
    private JTextArea textAreaDate;
    private JLabel label6;
    private JScrollPane scrollPane1;
    private JTextArea textAreaLogContent;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
