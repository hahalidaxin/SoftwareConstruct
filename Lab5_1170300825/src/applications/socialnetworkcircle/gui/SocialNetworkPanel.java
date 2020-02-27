/*
 * Created by JFormDesigner on Sat Apr 20 16:30:04 CST 2019
 */

package applications.socialnetworkcircle.gui;

import apis.gui.LoggerFrame;
import applications.socialnetworkcircle.SocialNetworkCircle;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;
import otherdirectory.exception.UncheckedException;

/**
 * @author Brainrain
 */
public class SocialNetworkPanel extends JPanel {
  private static Logger logger = Logger.getLogger(SocialNetworkPanel.class.getName());

  SocialNetworkCircle socialNetworkCircle;
  List<String> trackList;
  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  private JLabel labelTitle;
  private JButton buttonLog;
  private JPanel drawPanel;
  private JPanel rightPanel;
  private JPanel infoPanel;
  private JLabel label2;
  private JLabel labelState;
  private JLabel label1;
  private JLabel labelEntropy;
  private JPanel opratepanel;
  private JLabel label11;
  private JSeparator separator2;
  private JLabel label4;
  private JComboBox<String> comboBoxRemoveTrack;
  private JButton buttonRemoveTrack;
  private JLabel label5;
  private JTextField textFieldAddTrack;
  private JButton buttonAddTrack;
  private JLabel label6;
  private JButton buttonAddPO;
  private JLabel label7;
  private JComboBox<String> comboBoxRemovePO;
  private JButton buttonRemovePO;
  private JLabel label10;
  private JSeparator separator1;
  private JLabel label3;
  private JComboBox comboBoxInfoDiff;
  private JLabel labelInfoDiff;
  private JLabel label8;
  private JComboBox<String> comboBoxRelationU;
  private JComboBox<String> comboBoxRelationV;
  private JTextField textFieldDensity;
  private JButton buttonAddRelation;
  private JLabel label12;
  private JComboBox comboBoxRmRelationU;
  private JComboBox comboBoxRmRelationV;
  private JButton buttonRmRelation;
  private JLabel label9;
  private JComboBox<String> comboBoxDisU;
  private JComboBox<String> comboBoxDisV;
  private JLabel labelDistant;
  public SocialNetworkPanel(SocialNetworkCircle socialNetworkCircle) {
    this.socialNetworkCircle = socialNetworkCircle;
    initComponents();
  }

  public void reloadGameInfo(boolean state, double entropy, List<String> trackList, List<String> poList, List<String> relObjects, List<String> firstTrackPOs) {
    this.trackList = trackList;
    String sysState = state ? "合法" : "不合法";
    this.labelState.setText(sysState);
    this.labelEntropy.setText(Double.toString(entropy));
    this.comboBoxRemoveTrack.removeAllItems();
    for (String str : trackList) {
      this.comboBoxRemoveTrack.addItem(str);
    }

    this.comboBoxRemovePO.removeAllItems();
    this.comboBoxDisV.removeAllItems();
    this.comboBoxDisU.removeAllItems();
    this.comboBoxRmRelationU.removeAllItems();
    for (String str : poList) {
      this.comboBoxRemovePO.addItem(str);
      this.comboBoxDisV.addItem(str);
      this.comboBoxDisU.addItem(str);
    }

    this.comboBoxRelationU.removeAllItems();
    this.comboBoxRelationV.removeAllItems();
    for (String str : relObjects) {
      this.comboBoxRelationU.addItem(str);
      this.comboBoxRelationV.addItem(str);
      this.comboBoxRmRelationU.addItem(str);
    }

    this.comboBoxInfoDiff.removeAllItems();
    for (String str : firstTrackPOs) {
      this.comboBoxInfoDiff.addItem(str);
    }


  }

  private void processException(UncheckedException exept) {
    logger.error(exept);
    JOptionPane.showMessageDialog(this, exept);
    socialNetworkCircle.reLoadAll();
  }

  private void buttonRemoveTrackMousePressed(MouseEvent e) {
    try {
      double rmRadius = Double.parseDouble((String) this.comboBoxRemoveTrack.getSelectedItem());
      socialNetworkCircle.removeTrack(rmRadius);
    } catch (UncheckedException exept) {
      processException(exept);
    }
  }

  private void buttonAddTrackMousePressed(MouseEvent e) {
    try {
      double addRadius = Double.parseDouble(this.textFieldAddTrack.getText());
      socialNetworkCircle.addTrack(addRadius);
    } catch (UncheckedException exept) {
      processException(exept);
    }
  }

  private void buttonAddPOMousePressed(MouseEvent e) {
    try {
      JFrame inputframe = new FriendInfoFrame(this.socialNetworkCircle, this.trackList);
      inputframe.setVisible(true);
    } catch (UncheckedException exept) {
      processException(exept);
    }
//        inputframe = null;
  }

  private void buttonRemovePOMousePressed(MouseEvent e) {
    try {
      String rmPO = (String) this.comboBoxRemovePO.getSelectedItem();
      socialNetworkCircle.removePhysicalObject(rmPO);
    } catch (UncheckedException exept) {
      processException(exept);
    }
  }

  private void comboBoxInfoDiffItemStateChanged(ItemEvent e) {
    try {
      switch (e.getStateChange()) {
        case ItemEvent.SELECTED:
          this.labelInfoDiff.setText(String.format("%.2f",
              socialNetworkCircle.getInfoDiffusion((String) e.getItem())));
          break;
        default:
          break;
      }
    } catch (UncheckedException exept) {
      processException(exept);
    }
  }

  private void buttonAddRelationMousePressed(MouseEvent e) {
    try {
      String obNameU = (String) this.comboBoxRelationU.getSelectedItem();
      String obNameV = (String) this.comboBoxRelationV.getSelectedItem();
      if(obNameU.equals(obNameV)) {
        JOptionPane.showMessageDialog(this,"两人不能相同");
        logger.error("两人不能相同");
      } else {
        double density = Double.parseDouble(this.textFieldDensity.getText());
        socialNetworkCircle.addRelation(obNameU, obNameV, density);
      }
    } catch (UncheckedException exept) {
      processException(exept);
    }
  }

  private void refreshDistant() {
    String obNameU = (String) this.comboBoxDisU.getSelectedItem();
    String obNameV = (String) this.comboBoxDisV.getSelectedItem();
    if (obNameU == null || obNameV == null) return;
    int dis = this.socialNetworkCircle.getLogicalDistance(obNameU, obNameV);
    String displayText = dis == Integer.MAX_VALUE ? "INF" : Integer.toString(dis - 1);
    this.labelDistant.setText(displayText);
  }

  private void comboBoxDisUItemStateChanged(ItemEvent e) {
    try {
      refreshDistant();
    } catch (UncheckedException exept) {
      processException(exept);
    }
  }

  private void comboBoxDisVItemStateChanged(ItemEvent e) {
    try {
      refreshDistant();
    } catch (UncheckedException exept) {
      processException(exept);
    }
  }

  private void buttonRmRelationMousePressed(MouseEvent e) {
    try {
      String obNameU = (String) this.comboBoxRmRelationU.getSelectedItem();
      String obNameV = (String) this.comboBoxRmRelationV.getSelectedItem();
      this.socialNetworkCircle.removeRelation(obNameU, obNameV);
    } catch (UncheckedException exept) {
      processException(exept);
    }
  }

  private void comboBoxRmRelationUItemStateChanged(ItemEvent e) {
    try {
      // 修改RelationV
      String obNameU = (String) e.getItem();
      if (obNameU == null) return;
      List<String> surroudings = socialNetworkCircle.getSurroudings(obNameU);
      this.comboBoxRmRelationV.removeAllItems();
      for (String str : surroudings) {
        this.comboBoxRmRelationV.addItem(str);
      }
    } catch (UncheckedException exept) {
      processException(exept);
    }
  }

  private void buttonLogMousePressed(MouseEvent e) {
    LoggerFrame frame = new LoggerFrame();
    frame.setSize(750, 800);
    frame.setVisible(true);
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    labelTitle = new JLabel();
    buttonLog = new JButton();
    drawPanel = new JPanel();
    rightPanel = new JPanel();
    infoPanel = new JPanel();
    label2 = new JLabel();
    labelState = new JLabel();
    label1 = new JLabel();
    labelEntropy = new JLabel();
    opratepanel = new JPanel();
    label11 = new JLabel();
    separator2 = new JSeparator();
    label4 = new JLabel();
    comboBoxRemoveTrack = new JComboBox<>();
    buttonRemoveTrack = new JButton();
    label5 = new JLabel();
    textFieldAddTrack = new JTextField();
    buttonAddTrack = new JButton();
    label6 = new JLabel();
    buttonAddPO = new JButton();
    label7 = new JLabel();
    comboBoxRemovePO = new JComboBox<>();
    buttonRemovePO = new JButton();
    label10 = new JLabel();
    separator1 = new JSeparator();
    label3 = new JLabel();
    comboBoxInfoDiff = new JComboBox();
    labelInfoDiff = new JLabel();
    label8 = new JLabel();
    comboBoxRelationU = new JComboBox<>();
    comboBoxRelationV = new JComboBox<>();
    textFieldDensity = new JTextField();
    buttonAddRelation = new JButton();
    label12 = new JLabel();
    comboBoxRmRelationU = new JComboBox();
    comboBoxRmRelationV = new JComboBox();
    buttonRmRelation = new JButton();
    label9 = new JLabel();
    comboBoxDisU = new JComboBox<>();
    comboBoxDisV = new JComboBox<>();
    labelDistant = new JLabel();

    //======== this ========
    setLayout(new MigLayout(
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
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
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
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

    //---- labelTitle ----
    labelTitle.setText("SocialNetwork-\u8f68\u9053\u7cfb\u7edf");
    labelTitle.setFont(labelTitle.getFont().deriveFont(labelTitle.getFont().getStyle() | Font.BOLD, labelTitle.getFont().getSize() + 9f));
    add(labelTitle, "cell 7 5 3 5");

    //---- buttonLog ----
    buttonLog.setText("\u67e5\u770b\u65e5\u5fd7");
    buttonLog.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        buttonLogMousePressed(e);
      }
    });
    add(buttonLog, "cell 11 7");

    //======== drawPanel ========
    {
      drawPanel.setPreferredSize(new Dimension(800, 800));
      drawPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    }
    add(drawPanel, "cell 0 16 31 28");

    //======== rightPanel ========
    {
      rightPanel.setPreferredSize(new Dimension(400, 800));
      rightPanel.setBackground(new Color(204, 204, 204));
      rightPanel.setLayout(new MigLayout(
          "hidemode 3",
          // columns
          "[fill]" +
              "[fill]",
          // rows
          "[]" +
              "[]" +
              "[]" +
              "[]" +
              "[]"));

      //======== infoPanel ========
      {
        infoPanel.setPreferredSize(new Dimension(400, 200));
        infoPanel.setBackground(new Color(153, 153, 153));
        infoPanel.setLayout(new MigLayout(
            "hidemode 3",
            // columns
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
                "[]"));

        //---- label2 ----
        label2.setText("\u72b6\u6001\uff1a");
        label2.setFont(label2.getFont().deriveFont(label2.getFont().getStyle() | Font.BOLD, label2.getFont().getSize() + 8f));
        label2.setForeground(Color.black);
        infoPanel.add(label2, "cell 1 1");

        //---- labelState ----
        labelState.setFont(labelState.getFont().deriveFont(labelState.getFont().getSize() + 6f));
        infoPanel.add(labelState, "cell 4 1");

        //---- label1 ----
        label1.setText("\u71b5\u503c\uff1a");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 8f));
        label1.setForeground(Color.black);
        infoPanel.add(label1, "cell 1 5");

        //---- labelEntropy ----
        labelEntropy.setFont(labelEntropy.getFont().deriveFont(labelEntropy.getFont().getSize() + 6f));
        infoPanel.add(labelEntropy, "cell 4 5");
      }
      rightPanel.add(infoPanel, "cell 0 0 2 2");

      //======== opratepanel ========
      {
        opratepanel.setPreferredSize(new Dimension(400, 600));
        opratepanel.setFont(opratepanel.getFont().deriveFont(opratepanel.getFont().getSize() + 8f));
        opratepanel.setLayout(new MigLayout(
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
                "[]" +
                "[]" +
                "[]" +
                "[]"));

        //---- label11 ----
        label11.setText("\u6539\u53d8\u7cfb\u7edf");
        label11.setFont(label11.getFont().deriveFont(label11.getFont().getSize() + 12f));
        opratepanel.add(label11, "cell 1 1");
        opratepanel.add(separator2, "cell 1 2 11 1");

        //---- label4 ----
        label4.setText("\u5220\u9664\u8f68\u9053");
        opratepanel.add(label4, "cell 1 3");
        opratepanel.add(comboBoxRemoveTrack, "cell 6 3");

        //---- buttonRemoveTrack ----
        buttonRemoveTrack.setText("\u786e\u8ba4");
        buttonRemoveTrack.setMinimumSize(new Dimension(50, 30));
        buttonRemoveTrack.setPreferredSize(new Dimension(50, 30));
        buttonRemoveTrack.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            buttonRemoveTrackMousePressed(e);
          }
        });
        opratepanel.add(buttonRemoveTrack, "cell 10 3");

        //---- label5 ----
        label5.setText("\u6dfb\u52a0\u8f68\u9053");
        opratepanel.add(label5, "cell 1 4");

        //---- textFieldAddTrack ----
        textFieldAddTrack.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        textFieldAddTrack.setToolTipText("\u8f68\u9053\u534a\u5f84");
        opratepanel.add(textFieldAddTrack, "cell 6 4");

        //---- buttonAddTrack ----
        buttonAddTrack.setText("\u786e\u8ba4");
        buttonAddTrack.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            buttonAddTrackMousePressed(e);
          }
        });
        opratepanel.add(buttonAddTrack, "cell 10 4");

        //---- label6 ----
        label6.setText("\u6dfb\u52a0\u7269\u4f53");
        opratepanel.add(label6, "cell 1 6");

        //---- buttonAddPO ----
        buttonAddPO.setText("\u8f93\u5165");
        buttonAddPO.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            buttonAddPOMousePressed(e);
          }
        });
        opratepanel.add(buttonAddPO, "cell 10 6");

        //---- label7 ----
        label7.setText("\u5220\u9664\u7269\u4f53");
        opratepanel.add(label7, "cell 1 8");
        opratepanel.add(comboBoxRemovePO, "cell 6 8");

        //---- buttonRemovePO ----
        buttonRemovePO.setText("\u786e\u8ba4");
        buttonRemovePO.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            buttonRemovePOMousePressed(e);
          }
        });
        opratepanel.add(buttonRemovePO, "cell 10 8");

        //---- label10 ----
        label10.setText("\u7279\u6b8a\u529f\u80fd");
        label10.setFont(label10.getFont().deriveFont(label10.getFont().getSize() + 11f));
        opratepanel.add(label10, "cell 1 14");
        opratepanel.add(separator1, "cell 1 16 15 1");

        //---- label3 ----
        label3.setText("\u4fe1\u606f\u6269\u6563\u5ea6");
        opratepanel.add(label3, "cell 1 17");

        //---- comboBoxInfoDiff ----
        comboBoxInfoDiff.addItemListener(e -> comboBoxInfoDiffItemStateChanged(e));
        opratepanel.add(comboBoxInfoDiff, "cell 6 17");
        opratepanel.add(labelInfoDiff, "cell 10 17");

        //---- label8 ----
        label8.setText("\u589e\u52a0\u5173\u7cfb");
        opratepanel.add(label8, "cell 1 18");
        opratepanel.add(comboBoxRelationU, "cell 6 18");
        opratepanel.add(comboBoxRelationV, "cell 7 18");

        //---- textFieldDensity ----
        textFieldDensity.setToolTipText("\u7d27\u5bc6\u5ea6");
        textFieldDensity.setMinimumSize(new Dimension(100, 27));
        opratepanel.add(textFieldDensity, "cell 8 18");

        //---- buttonAddRelation ----
        buttonAddRelation.setText("\u786e\u8ba4");
        buttonAddRelation.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            buttonAddRelationMousePressed(e);
          }
        });
        opratepanel.add(buttonAddRelation, "cell 10 18");

        //---- label12 ----
        label12.setText("\u5220\u9664\u5173\u7cfb");
        opratepanel.add(label12, "cell 1 20");

        //---- comboBoxRmRelationU ----
        comboBoxRmRelationU.addItemListener(e -> comboBoxRmRelationUItemStateChanged(e));
        opratepanel.add(comboBoxRmRelationU, "cell 6 20");
        opratepanel.add(comboBoxRmRelationV, "cell 7 20");

        //---- buttonRmRelation ----
        buttonRmRelation.setText("\u786e\u8ba4");
        buttonRmRelation.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            buttonRmRelationMousePressed(e);
          }
        });
        opratepanel.add(buttonRmRelation, "cell 10 20");

        //---- label9 ----
        label9.setText("\u903b\u8f91\u8ddd\u79bb");
        opratepanel.add(label9, "cell 1 21");

        //---- comboBoxDisU ----
        comboBoxDisU.addItemListener(e -> comboBoxDisUItemStateChanged(e));
        opratepanel.add(comboBoxDisU, "cell 6 21");

        //---- comboBoxDisV ----
        comboBoxDisV.setDoubleBuffered(true);
        comboBoxDisV.addItemListener(e -> comboBoxDisVItemStateChanged(e));
        opratepanel.add(comboBoxDisV, "cell 7 21");

        //---- labelDistant ----
        labelDistant.setText("\u65e0");
        labelDistant.setHorizontalAlignment(SwingConstants.CENTER);
        opratepanel.add(labelDistant, "cell 10 21");
      }
      rightPanel.add(opratepanel, "cell 0 2 2 3");
    }
    add(rightPanel, "cell 31 16 1 28");
    // JFormDesigner - End of component initialization  //GEN-END:initComponents
  }
  // JFormDesigner - End of variables declaration  //GEN-END:variables

  public JPanel getDrawPanel() {
    return drawPanel;
  }
}
