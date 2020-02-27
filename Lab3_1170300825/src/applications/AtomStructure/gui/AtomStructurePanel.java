/*
 * Created by JFormDesigner on Sat Apr 20 15:40:57 CST 2019
 */

package applications.AtomStructure.gui;

import applications.AtomStructure.AtomStructure;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * @author Brainrain
 */
public class AtomStructurePanel extends JPanel {
    AtomStructure atomStructure;
    List<String> trackList;

    public AtomStructurePanel(AtomStructure atomStructure) {
        this.atomStructure = atomStructure;
        initComponents();
    }

    public void reloadGameInfo(boolean state, double entropy, java.util.List<String> trackList, java.util.List<String> poList,List<String> history) {
        this.trackList = trackList;
        String sysState = state?"合法":"不合法";
        this.labelState.setText(sysState);
        this.labelEntropy.setText(Double.toString(entropy));
        this.comboBoxRemovePO.removeAllItems();
        this.comboBoxRemoveTrack.removeAllItems();
        this.comboBoxSource.removeAllItems();
        this.comboBoxTarget.removeAllItems();
        for(String str:trackList) {
            this.comboBoxRemoveTrack.addItem(str);
            this.comboBoxRemovePO.addItem(str);
            this.comboBoxSource.addItem(str);
            this.comboBoxTarget.addItem(str);
        }
        this.comboBoxHistory.removeAllItems();
        for(String str:history) {
            this.comboBoxHistory.addItem(str);
        }
    }


    public JPanel getDrawPanel() {
        return drawPanel;
    }


    private void buttonRemoveTrackMousePressed(MouseEvent e) {
        double rmRadius = Double.valueOf((String) this.comboBoxRemoveTrack.getSelectedItem());
        atomStructure.removeTrack(rmRadius);
    }

    private void buttonAddTrackMousePressed(MouseEvent e) {
        double addRadius = Double.valueOf(this.textFieldAddTrack.getText());
        atomStructure.addTrack(addRadius);
    }

    private void buttonAddPOMousePressed(MouseEvent e) {
        JFrame inputframe = new ElectronInfoFrame(this.atomStructure,this.trackList);
        inputframe.setVisible(true);
        inputframe = null;
    }

    private void buttonRemovePOMousePressed(MouseEvent e) {
        double rmRadius = Double.valueOf((String) this.comboBoxRemovePO.getSelectedItem());
        atomStructure.removePhysicalObject(rmRadius);
    }

    private void buttonTransitMousePressed(MouseEvent e) {
        System.out.println(this.comboBoxSource.getSelectedItem());
        double source = Double.valueOf((String) this.comboBoxSource.getSelectedItem());
        double target = Double.valueOf((String) this.comboBoxTarget.getSelectedItem());
        atomStructure.electronTransit(source,target);
    }

    private void buttonRebackMousePressed(MouseEvent e) {
        int index = this.comboBoxHistory.getSelectedIndex();
        this.atomStructure.rebackHistory(index);
    }




    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        labelTitle = new JLabel();
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
        label3 = new JLabel();
        comboBoxSource = new JComboBox();
        label9 = new JLabel();
        comboBoxTarget = new JComboBox();
        buttonTransit = new JButton();
        label12 = new JLabel();
        comboBoxHistory = new JComboBox();
        buttonReback = new JButton();

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
        labelTitle.setText("AtomStructure-\u8f68\u9053\u7cfb\u7edf");
        labelTitle.setFont(labelTitle.getFont().deriveFont(labelTitle.getFont().getStyle() | Font.BOLD, labelTitle.getFont().getSize() + 9f));
        add(labelTitle, "cell 7 5 3 4");

        //======== drawPanel ========
        {
            drawPanel.setPreferredSize(new Dimension(800, 800));
            drawPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        }
        add(drawPanel, "cell 0 15 30 28");

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
                    "[]"));

                //---- label11 ----
                label11.setText("\u6539\u53d8\u7cfb\u7edf");
                label11.setFont(label11.getFont().deriveFont(label11.getFont().getSize() + 12f));
                opratepanel.add(label11, "cell 1 1");
                opratepanel.add(separator2, "cell 1 2 17 1");

                //---- label4 ----
                label4.setText("\u5220\u9664\u8f68\u9053");
                opratepanel.add(label4, "cell 1 3");
                opratepanel.add(comboBoxRemoveTrack, "cell 8 3");

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
                opratepanel.add(buttonRemoveTrack, "cell 17 3");

                //---- label5 ----
                label5.setText("\u6dfb\u52a0\u8f68\u9053");
                opratepanel.add(label5, "cell 1 4");

                //---- textFieldAddTrack ----
                textFieldAddTrack.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                textFieldAddTrack.setToolTipText("\u8f68\u9053\u534a\u5f84");
                opratepanel.add(textFieldAddTrack, "cell 8 4");

                //---- buttonAddTrack ----
                buttonAddTrack.setText("\u786e\u8ba4");
                buttonAddTrack.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonAddTrackMousePressed(e);
                    }
                });
                opratepanel.add(buttonAddTrack, "cell 17 4");

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
                opratepanel.add(buttonAddPO, "cell 17 6");

                //---- label7 ----
                label7.setText("\u5220\u9664\u7269\u4f53\uff08\u8f68\u9053\u4e0a\u4efb\u4e00\u4e2a\uff09");
                opratepanel.add(label7, "cell 1 8");
                opratepanel.add(comboBoxRemovePO, "cell 8 8");

                //---- buttonRemovePO ----
                buttonRemovePO.setText("\u786e\u8ba4");
                buttonRemovePO.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonRemovePOMousePressed(e);
                    }
                });
                opratepanel.add(buttonRemovePO, "cell 17 8");

                //---- label10 ----
                label10.setText("\u7279\u6b8a\u529f\u80fd");
                label10.setFont(label10.getFont().deriveFont(label10.getFont().getSize() + 11f));
                opratepanel.add(label10, "cell 1 14");
                opratepanel.add(separator1, "cell 1 15 17 2");

                //---- label8 ----
                label8.setText("\u7535\u5b50\u8dc3\u8fc1\uff08\u8f68\u9053\u4e0a\u4efb\u4e00\u4e2a\uff09");
                opratepanel.add(label8, "cell 1 17");

                //---- label3 ----
                label3.setText("\u6e90\u8f68\u9053");
                opratepanel.add(label3, "cell 2 17");
                opratepanel.add(comboBoxSource, "cell 4 17");

                //---- label9 ----
                label9.setText("\u76ee\u6807\u8f68\u9053");
                opratepanel.add(label9, "cell 6 17");
                opratepanel.add(comboBoxTarget, "cell 8 17");

                //---- buttonTransit ----
                buttonTransit.setText("\u786e\u8ba4");
                buttonTransit.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonTransitMousePressed(e);
                    }
                });
                opratepanel.add(buttonTransit, "cell 17 17");

                //---- label12 ----
                label12.setText("\u5386\u53f2\u56de\u6eda");
                opratepanel.add(label12, "cell 1 19");
                opratepanel.add(comboBoxHistory, "cell 4 19");

                //---- buttonReback ----
                buttonReback.setText("\u786e\u8ba4");
                buttonReback.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonRebackMousePressed(e);
                    }
                });
                opratepanel.add(buttonReback, "cell 17 19");
            }
            rightPanel.add(opratepanel, "cell 1 2 1 3");
        }
        add(rightPanel, "cell 30 15 1 28");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel labelTitle;
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
    private JLabel label3;
    private JComboBox comboBoxSource;
    private JLabel label9;
    private JComboBox comboBoxTarget;
    private JButton buttonTransit;
    private JLabel label12;
    private JComboBox comboBoxHistory;
    private JButton buttonReback;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
