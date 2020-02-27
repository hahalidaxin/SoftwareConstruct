/*
 * Created by JFormDesigner on Sun Apr 21 21:28:26 CST 2019
 */

package applications.socialnetworkcircle.gui;

import applications.socialnetworkcircle.Friend;
import applications.socialnetworkcircle.SocialNetworkCircle;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;
import otherdirectory.exception.UncheckedException;

/**
 * @author Brainrain
 */
public class FriendInfoFrame extends JFrame {
  private static Logger logger = Logger.getLogger(FriendInfoFrame.class.getName());
  SocialNetworkCircle socialNetworkCircle;
  List<String> trackList;
  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  private JLabel label1;
  private JSeparator separator1;
  private JPanel infopanel;
  private JLabel label2;
  private JTextField textFieldName;
  private JLabel label6;
  private JTextField textFieldAge;
  private JLabel label3;
  private JComboBox<String> comboBoxGender;
  private JLabel label8;
  private JComboBox<String> comboBoxTrackList;
  private JButton buttonSummit;
  public FriendInfoFrame(SocialNetworkCircle socialNetworkCircle, List<String> trackList) {
    this.socialNetworkCircle = socialNetworkCircle;
    this.trackList = trackList;
    initComponents();
    comboBoxGender.addItem("M");
    comboBoxGender.addItem("F");
    this.comboBoxTrackList.removeAllItems();
    for (String str : trackList) {
      this.comboBoxTrackList.addItem(str);
    }
  }

  private boolean testMatch(String line, String regex) {
    Matcher matcher = Pattern.compile(regex).matcher(line);
    if (matcher.find()) {
      if (matcher.group(1).equals(line)) {
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
      if (!testMatch(this.textFieldName.getText(), "([A-Za-z0-9]+)")) {
        UncheckedException.assertTrue(false, "Friend名称不合法");
      }
      String obName = this.textFieldName.getText();
      if (!testMatch(this.textFieldAge.getText(), "(\\d+)")) {
        UncheckedException.assertTrue(false, "Friend年龄不合法");
      }
      int age = Integer.parseInt(this.textFieldAge.getText());

      if (!testMatch((String) this.comboBoxGender.getSelectedItem(), "([MF])")) {
        UncheckedException.assertTrue(false, "Friend性别不合法");
      }
      String gender = (String) this.comboBoxGender.getSelectedItem();

      double trackRadius = Double.parseDouble((String) this.comboBoxTrackList.getSelectedItem());
      socialNetworkCircle.addPhysicalObject(Friend.getInstance(obName, gender, age), trackRadius);
      this.setVisible(false);
    } catch (UncheckedException exept) {
      logger.error(exept);
      JOptionPane.showMessageDialog(this, exept);
      socialNetworkCircle.reLoadAll();
    }
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    label1 = new JLabel();
    separator1 = new JSeparator();
    infopanel = new JPanel();
    label2 = new JLabel();
    textFieldName = new JTextField();
    label6 = new JLabel();
    textFieldAge = new JTextField();
    label3 = new JLabel();
    comboBoxGender = new JComboBox<>();
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
              "[]" +
              "[]"));

      //---- label2 ----
      label2.setText("\u540d\u79f0\uff1a");
      label2.setForeground(Color.black);
      label2.setFont(label2.getFont().deriveFont(label2.getFont().getSize() + 6f));
      infopanel.add(label2, "cell 1 1");

      //---- textFieldName ----
      textFieldName.setBackground(Color.white);
      textFieldName.setPreferredSize(new Dimension(100, 27));
      infopanel.add(textFieldName, "cell 8 1");

      //---- label6 ----
      label6.setText("\u5e74\u9f84\uff1a");
      label6.setForeground(Color.black);
      label6.setFont(label6.getFont().deriveFont(label6.getFont().getSize() + 6f));
      infopanel.add(label6, "cell 1 3");

      //---- textFieldAge ----
      textFieldAge.setPreferredSize(new Dimension(100, 27));
      infopanel.add(textFieldAge, "cell 8 3");

      //---- label3 ----
      label3.setText("\u6027\u522b\uff1a");
      label3.setForeground(Color.black);
      label3.setFont(label3.getFont().deriveFont(label3.getFont().getStyle() & ~Font.BOLD, label3.getFont().getSize() + 6f));
      infopanel.add(label3, "cell 1 5");

      //---- comboBoxGender ----
      comboBoxGender.setOpaque(false);
      comboBoxGender.setPreferredSize(new Dimension(120, 38));
      infopanel.add(comboBoxGender, "cell 8 5");
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
  // JFormDesigner - End of variables declaration  //GEN-END:variables
}
