/*
 * Created by JFormDesigner on Sun Apr 21 16:35:47 CST 2019
 */

package applications.TrackGame.gui;

import applications.TrackGame.Runner;
import applications.TrackGame.TrackGame;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;
import otherDirectory.exception.UncheckedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brainrain
 */
public class RunnerInfoFrame extends JFrame {
    private static Logger logger = Logger.getLogger(Runner.class.getName());
    TrackGame trackGame;
    public RunnerInfoFrame(TrackGame trackGame, List<String> trackList) {
        this.trackGame = trackGame;
        initComponents();
        this.comboBoxTrackList.removeAllItems();
        for(String str:trackList) {
            this.comboBoxTrackList.addItem(str);
        }
    }

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
    private void buttonSummitMousePressed(MouseEvent e) {
        try {
            if(!testMatch(textFieldName.getText(),"([a-zA-Z]+)")) {
                UncheckedException.assertTrue(false,"运动员名称不符合要求");
            }
            String obName = this.textFieldName.getText();

            if(!testMatch(textFieldID.getText(),"(\\d+)")) {
                UncheckedException.assertTrue(false,"运动员ID不符合要求");
            }
            int id = Integer.parseInt(this.textFieldID.getText());

            if(!testMatch(textFieldNation.getText(),"([A-Z]{3})")) {
                UncheckedException.assertTrue(false,"运动员国家不符合要求");
            }
            String nation = this.textFieldNation.getText();

            if(!testMatch(textFieldAge.getText(),"(\\d+)")) {
                UncheckedException.assertTrue(false,"运动员年龄不符合要求");
            }
            int age = Integer.parseInt(this.textFieldAge.getText());

            if(!testMatch(textField5BestScore.getText(),"(\\d{1,2}\\.\\d{2})")) {
                UncheckedException.assertTrue(false,"运动员最佳成绩不符合要求");
            }
            double bestScore = Double.parseDouble(this.textField5BestScore.getText());

            Runner runner = Runner.getInstance(obName, id, age, bestScore, nation);
            double trackRadius = Double.parseDouble((String) this.comboBoxTrackList.getSelectedItem());
            trackGame.addPhysicalObject(runner, trackRadius);
            this.setVisible(false);
        } catch(UncheckedException except) {
            logger.error(except);
            JOptionPane.showMessageDialog(this,except);
            trackGame.reLoadAll();
//            UncheckedException.assertTrue(false,"运动员信息输入不符合规范");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        separator1 = new JSeparator();
        infopanel = new JPanel();
        label2 = new JLabel();
        textFieldName = new JTextField();
        label4 = new JLabel();
        textFieldID = new JTextField();
        label5 = new JLabel();
        textFieldNation = new JTextField();
        label6 = new JLabel();
        textFieldAge = new JTextField();
        label7 = new JLabel();
        textField5BestScore = new JTextField();
        label8 = new JLabel();
        comboBoxTrackList = new JComboBox<>();
        buttonSummit = new JButton();

        //======== this ========
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
            "[]"));

        //---- label1 ----
        label1.setText("\u8be6\u7ec6\u4fe1\u606f");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 12f));
        contentPane.add(label1, "cell 4 0");
        contentPane.add(separator1, "cell 5 1");

        //======== infopanel ========
        {
            infopanel.setPreferredSize(new Dimension(520, 401));
            infopanel.setBackground(Color.white);
            infopanel.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
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
                "[]" +
                "[]" +
                "[]"));

            //---- label2 ----
            label2.setText("\u540d\u79f0\uff1a");
            label2.setForeground(Color.black);
            label2.setFont(label2.getFont().deriveFont(label2.getFont().getSize() + 6f));
            infopanel.add(label2, "cell 1 1");

            //---- textFieldName ----
            textFieldName.setBackground(Color.white);
            textFieldName.setMinimumSize(new Dimension(100, 27));
            infopanel.add(textFieldName, "cell 8 1");

            //---- label4 ----
            label4.setText("ID\uff1a");
            label4.setForeground(Color.black);
            label4.setFont(label4.getFont().deriveFont(label4.getFont().getSize() + 6f));
            infopanel.add(label4, "cell 1 2");
            infopanel.add(textFieldID, "cell 8 2");

            //---- label5 ----
            label5.setText("\u56fd\u5bb6\uff1a");
            label5.setForeground(Color.black);
            label5.setFont(label5.getFont().deriveFont(label5.getFont().getSize() + 6f));
            infopanel.add(label5, "cell 1 3");
            infopanel.add(textFieldNation, "cell 8 3");

            //---- label6 ----
            label6.setText("\u5e74\u9f84\uff1a");
            label6.setForeground(Color.black);
            label6.setFont(label6.getFont().deriveFont(label6.getFont().getSize() + 6f));
            infopanel.add(label6, "cell 1 4");
            infopanel.add(textFieldAge, "cell 8 4");

            //---- label7 ----
            label7.setText("\u6700\u597d\u6210\u7ee9\uff1a");
            label7.setForeground(Color.black);
            label7.setFont(label7.getFont().deriveFont(label7.getFont().getSize() + 6f));
            infopanel.add(label7, "cell 1 5");
            infopanel.add(textField5BestScore, "cell 8 5");
        }
        contentPane.add(infopanel, "cell 4 2");

        //---- label8 ----
        label8.setText("\u6dfb\u52a0\u5230\uff1a");
        label8.setForeground(Color.black);
        label8.setFont(label8.getFont().deriveFont(label8.getFont().getSize() + 6f));
        contentPane.add(label8, "cell 4 3");
        contentPane.add(comboBoxTrackList, "cell 4 3");

        //---- buttonSummit ----
        buttonSummit.setText("\u63d0\u4ea4");
        buttonSummit.setPreferredSize(new Dimension(150, 50));
        buttonSummit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonSummitMousePressed(e);
            }
        });
        contentPane.add(buttonSummit, "cell 2 6 5 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JSeparator separator1;
    private JPanel infopanel;
    private JLabel label2;
    private JTextField textFieldName;
    private JLabel label4;
    private JTextField textFieldID;
    private JLabel label5;
    private JTextField textFieldNation;
    private JLabel label6;
    private JTextField textFieldAge;
    private JLabel label7;
    private JTextField textField5BestScore;
    private JLabel label8;
    private JComboBox<String> comboBoxTrackList;
    private JButton buttonSummit;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
