/*
 * Created by JFormDesigner on Thu May 16 20:38:45 CST 2019
 */

package APIs.gui;

import APIs.OrbitLauncher;
import net.miginfocom.swing.MigLayout;
import otherDirectory.exception.CheckedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Brainrain
 */
public class LauncherFrame extends JFrame {
    String filepath = "src/configFile/";
    OrbitLauncher orbitLauncher;
    public LauncherFrame(OrbitLauncher launcher) {
        initComponents();
        orbitLauncher = launcher;
        this.comboBoxSystemSelect.addItem("TrackGame");
        this.comboBoxSystemSelect.addItem("AtomicStructure");
        this.comboBoxSystemSelect.addItem("SocialNetworkCircle");
        this.comboBoxSystemSelect.setSelectedIndex(0);
        refreshAll();
    }
    private void refreshAll() {
        this.comboBoxFileSelect.removeAllItems();
        getOrbitFilenames((String)this.comboBoxSystemSelect.getSelectedItem())
                .forEach(this.comboBoxFileSelect::addItem);
    }

    private List<String> getOrbitFilenames(String orbitName) {
        File file = new File(filepath);
        File[] array = file.listFiles();
        if(array==null) return new ArrayList<>();
        List<String> filenames = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if(array[i].isFile() && array[i].getName().startsWith(orbitName)) {
                filenames.add(array[i].getName());
            }
        }
        return filenames;
    }

    private void comboBoxSystemSelectItemStateChanged(ItemEvent e) {
//        选择了系统 需要更新需要显示的文件
        switch(e.getStateChange()) {
            case ItemEvent.SELECTED:
                refreshAll();
                break;
            default:
                break;
        }
    }

    private void buttonStartMousePressed(MouseEvent e) {
        boolean visiblebool = false;
        try {
            String orbitFilePath = filepath + (String) this.comboBoxFileSelect.getSelectedItem();
            int orbitType = this.comboBoxSystemSelect.getSelectedIndex();
            orbitLauncher.launchOrbit(orbitType,orbitFilePath);
        } catch(CheckedException exept) {
//            此时读入文件发生错误，需要提示用户重新选择输入文件
            visiblebool = true;
            JOptionPane.showMessageDialog(this,
                    String.format("输入文件发生错误%n错误信息:\t%s%n请重新选择输入文件",exept));
        }
//        此时创建窗口成功，需要关闭当前窗口
        this.setVisible(visiblebool);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        separator1 = new JSeparator();
        label2 = new JLabel();
        comboBoxSystemSelect = new JComboBox();
        label3 = new JLabel();
        comboBoxFileSelect = new JComboBox();
        buttonStart = new JButton();

        //======== this ========
        setResizable(false);
        setMinimumSize(new Dimension(440, 320));
        setTitle("SystemLauncher");
        setFont(new Font("Dialog", Font.BOLD, 12));
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
            "[]"));
        contentPane.add(separator1, "cell 1 3");

        //---- label2 ----
        label2.setText("\u8f68\u9053\u7cfb\u7edf\u9009\u62e9");
        label2.setFont(label2.getFont().deriveFont(label2.getFont().getSize() + 10f));
        contentPane.add(label2, "cell 2 9");

        //---- comboBoxSystemSelect ----
        comboBoxSystemSelect.addItemListener(e -> comboBoxSystemSelectItemStateChanged(e));
        contentPane.add(comboBoxSystemSelect, "cell 7 9");

        //---- label3 ----
        label3.setText("\u8f93\u5165\u6587\u4ef6\u9009\u62e9");
        label3.setFont(label3.getFont().deriveFont(label3.getFont().getSize() + 10f));
        contentPane.add(label3, "cell 2 14");
        contentPane.add(comboBoxFileSelect, "cell 7 14");

        //---- buttonStart ----
        buttonStart.setText("\u542f\u52a8\u7cfb\u7edf");
        buttonStart.setFont(buttonStart.getFont().deriveFont(buttonStart.getFont().getSize() + 8f));
        buttonStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonStartMousePressed(e);
            }
        });
        contentPane.add(buttonStart, "cell 2 18 2 2");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JSeparator separator1;
    private JLabel label2;
    private JComboBox comboBoxSystemSelect;
    private JLabel label3;
    private JComboBox comboBoxFileSelect;
    private JButton buttonStart;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
