/*
 * Created by JFormDesigner on Sat Apr 20 08:53:36 CST 2019
 */

package applications.TrackGame.gui;

import APIs.gui.LoggerFrame;
import applications.TrackGame.TrackGame;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;
import otherDirectory.exception.UncheckedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * @author Brainrain
 */
public class TrackGamePanel extends JPanel {
    private static Logger logger = Logger.getLogger(TrackGamePanel.class.getName());

    TrackGame trackGame;
    List<String> trackList;
    public TrackGamePanel(TrackGame trackGame) {
        this.trackGame = trackGame;
        initComponents();
    }

    public void reloadGameInfo(boolean state, double entropy, List<String> trackList, List<String> poList,List<String> otherPoList) {
        this.trackList = trackList;
        String sysState = state?"合法":"不合法";
        this.labelState.setText(sysState);
        this.labelEntropy.setText(Double.toString(entropy));
        this.comboBoxRemoveTrack.removeAllItems();
        for(String str:trackList) {
            this.comboBoxRemoveTrack.addItem(str);
        }

        this.comboBoxRemovePO.removeAllItems();
        this.comboBoxPOTrack1.removeAllItems();
        this.comboBoxPOTrack2.removeAllItems();
        this.comboBoxPOGroup1.removeAllItems();
        this.comboBoxPOGroup2.removeAllItems();
        for(String str:poList) {
            this.comboBoxRemovePO.addItem(str);
            this.comboBoxPOTrack1.addItem(str);
            this.comboBoxPOTrack2.addItem(str);
            this.comboBoxPOGroup1.addItem(str);
        }
        for(String str:otherPoList) {
            this.comboBoxPOGroup2.addItem(str);
        }
    }

    private void processException(UncheckedException exept) {
        logger.error(exept);
        JOptionPane.showMessageDialog(this,exept);
        trackGame.reLoadAll();
    }

    private void comboBoxStrategyItemStateChanged(ItemEvent e) {
        try {
            switch (e.getStateChange()) {
                case ItemEvent.SELECTED:
                    trackGame.selecttedGameStrategy((String) e.getItem());
                    break;
                default:
                    break;
            }
        } catch(UncheckedException exept) {
            processException(exept);
        }
    }

    private void comboBoxGroupSelectItemStateChanged(ItemEvent e) {
        try {
            switch (e.getStateChange()) {
                case ItemEvent.SELECTED:
                    trackGame.visualizeOrbit(Integer.parseInt((String) e.getItem()));
                    break;
                default:
                    break;
            }
        }catch(UncheckedException exept) {
            processException(exept);
        }
    }

    public void initComboBoxGroupSelectItems(List<String> items) {
        comboBoxGroupSelect.removeAllItems();
        for(String item:items) {
            comboBoxGroupSelect.addItem(item);
        }
    }
    public void initComboBoxStrategyItems(List<String> items) {
        comboBoxStrategy.removeAllItems();
        for(String item:items) {
            comboBoxStrategy.addItem(item);
        }
    }

    public JPanel getDrawPanel() {
        return drawPanel;
    }

    private void buttonRemoveTrackMousePressed(MouseEvent e) {
        try {
            double rmRadius = Double.parseDouble((String) this.comboBoxRemoveTrack.getSelectedItem());
            trackGame.removeTrack(rmRadius);
        } catch(UncheckedException exept) {
            processException(exept);
        }
    }

    private void buttonAddTrackMousePressed(MouseEvent e) {
        try {
            double addRadius = Double.parseDouble(this.textFieldAddTrack.getText());
            trackGame.addTrack(addRadius);
        } catch(UncheckedException exept) {
            processException(exept);
        }
    }

    private void buttonAddPOMousePressed(MouseEvent e) {
        JFrame inputframe = new RunnerInfoFrame(this.trackGame,this.trackList);
        inputframe.setVisible(true);
//        inputframe = null;
    }

    private void buttonRemovePOMousePressed(MouseEvent e) {
        try {
            String rmPO = (String) this.comboBoxRemovePO.getSelectedItem();
            trackGame.removePhysicalObject(rmPO);
        } catch(UncheckedException exept) {
            processException(exept);
        }
    }

    private void buttonChangePOTrackMousePressed(MouseEvent e) {
        try {
            String raName = (String) this.comboBoxPOTrack1.getSelectedItem();
            String rbName = (String) this.comboBoxPOTrack2.getSelectedItem();
            trackGame.exchangeTrack(raName, rbName);
        } catch(UncheckedException exept) {
            processException(exept);
        }
    }

    private void buttonChangePOGroupMousePressed(MouseEvent e) {
        try {
            String raName = (String) this.comboBoxPOGroup1.getSelectedItem();
            String rbName = (String) this.comboBoxPOGroup2.getSelectedItem();
            trackGame.exchangeGroup(raName, rbName);
        } catch(UncheckedException exept) {
            processException(exept);
        }
    }

    private void buttonLogMousePressed(MouseEvent e) {
        LoggerFrame frame = new LoggerFrame();
        frame.setSize(750,800);
        frame.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        labelTitle = new JLabel();
        buttonLog = new JButton();
        labelStrategy = new JLabel();
        comboBoxStrategy = new JComboBox<>();
        labelChangeGroup = new JLabel();
        comboBoxGroupSelect = new JComboBox();
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
        label8 = new JLabel();
        comboBoxPOTrack1 = new JComboBox<>();
        comboBoxPOTrack2 = new JComboBox<>();
        buttonChangePOTrack = new JButton();
        label9 = new JLabel();
        comboBoxPOGroup1 = new JComboBox<>();
        comboBoxPOGroup2 = new JComboBox<>();
        buttonChangePOGroup = new JButton();

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
            "[]"));

        //---- labelTitle ----
        labelTitle.setText("TrackGame-\u8f68\u9053\u7cfb\u7edf");
        labelTitle.setFont(labelTitle.getFont().deriveFont(labelTitle.getFont().getStyle() | Font.BOLD, labelTitle.getFont().getSize() + 9f));
        add(labelTitle, "cell 7 5 3 4");

        //---- buttonLog ----
        buttonLog.setText("\u67e5\u770b\u65e5\u5fd7");
        buttonLog.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonLogMousePressed(e);
            }
        });
        add(buttonLog, "cell 11 7");

        //---- labelStrategy ----
        labelStrategy.setText("\u7f16\u6392\u7b56\u7565\uff08\u91cd\u7f16\uff09\uff1a");
        add(labelStrategy, "cell 19 7");

        //---- comboBoxStrategy ----
        comboBoxStrategy.addItemListener(e -> comboBoxStrategyItemStateChanged(e));
        add(comboBoxStrategy, "cell 21 7");

        //---- labelChangeGroup ----
        labelChangeGroup.setText("\u9009\u62e9\u5c0f\u7ec4\uff1a");
        add(labelChangeGroup, "cell 24 7");

        //---- comboBoxGroupSelect ----
        comboBoxGroupSelect.addItemListener(e -> comboBoxGroupSelectItemStateChanged(e));
        add(comboBoxGroupSelect, "cell 26 7");

        //======== drawPanel ========
        {
            drawPanel.setPreferredSize(new Dimension(800, 800));
            drawPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        }
        add(drawPanel, "cell 0 15 32 28");

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
                    "[]"));

                //---- label11 ----
                label11.setText("\u6539\u53d8\u7cfb\u7edf");
                label11.setFont(label11.getFont().deriveFont(label11.getFont().getSize() + 12f));
                opratepanel.add(label11, "cell 1 1");
                opratepanel.add(separator2, "cell 1 2 10 1");

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
                opratepanel.add(buttonRemoveTrack, "cell 9 3");

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
                opratepanel.add(buttonAddTrack, "cell 9 4");

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
                opratepanel.add(buttonAddPO, "cell 9 6");

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
                opratepanel.add(buttonRemovePO, "cell 9 8");

                //---- label10 ----
                label10.setText("\u7279\u6b8a\u529f\u80fd");
                label10.setFont(label10.getFont().deriveFont(label10.getFont().getSize() + 11f));
                opratepanel.add(label10, "cell 1 14");
                opratepanel.add(separator1, "cell 1 16 14 1");

                //---- label8 ----
                label8.setText("\u4ea4\u6362\u8f68\u9053");
                opratepanel.add(label8, "cell 1 18");
                opratepanel.add(comboBoxPOTrack1, "cell 4 18");
                opratepanel.add(comboBoxPOTrack2, "cell 6 18");

                //---- buttonChangePOTrack ----
                buttonChangePOTrack.setText("\u786e\u8ba4");
                buttonChangePOTrack.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonChangePOTrackMousePressed(e);
                    }
                });
                opratepanel.add(buttonChangePOTrack, "cell 9 18");

                //---- label9 ----
                label9.setText("\u4ea4\u6362\u7ec4");
                opratepanel.add(label9, "cell 1 21");
                opratepanel.add(comboBoxPOGroup1, "cell 4 21");
                opratepanel.add(comboBoxPOGroup2, "cell 6 21");

                //---- buttonChangePOGroup ----
                buttonChangePOGroup.setText("\u786e\u8ba4");
                buttonChangePOGroup.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonChangePOGroupMousePressed(e);
                    }
                });
                opratepanel.add(buttonChangePOGroup, "cell 9 21");
            }
            rightPanel.add(opratepanel, "cell 0 2 2 3");
        }
        add(rightPanel, "cell 32 15 1 28");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel labelTitle;
    private JButton buttonLog;
    private JLabel labelStrategy;
    private JComboBox<String> comboBoxStrategy;
    private JLabel labelChangeGroup;
    private JComboBox comboBoxGroupSelect;
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
    private JLabel label8;
    private JComboBox<String> comboBoxPOTrack1;
    private JComboBox<String> comboBoxPOTrack2;
    private JButton buttonChangePOTrack;
    private JLabel label9;
    private JComboBox<String> comboBoxPOGroup1;
    private JComboBox<String> comboBoxPOGroup2;
    private JButton buttonChangePOGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
