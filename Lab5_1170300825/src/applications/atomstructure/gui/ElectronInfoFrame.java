/*
 * Created by JFormDesigner on Sun Apr 21 20:07:30 CST 2019
 */

package applications.atomstructure.gui;

import applications.atomstructure.AtomStructure;
import applications.atomstructure.ElectronFactory;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import net.miginfocom.swing.MigLayout;
import track.Track;
import track.TrackFactory;

/**
 * @author Brainrain
 */
public class ElectronInfoFrame extends JFrame {
  AtomStructure atomStructure;
  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  private JLabel label1;
  private JSeparator separator1;
  private JPanel infopanel;
  private JLabel label2;
  private JLabel label3;
  private JLabel label8;
  private JComboBox<String> comboBoxTrackList;
  private JButton buttonSummit;
  public ElectronInfoFrame(AtomStructure atomStructure, List<String> trackList) {
    this.atomStructure = atomStructure;
    initComponents();
    this.comboBoxTrackList.removeAllItems();
    for (String str : trackList) {
      this.comboBoxTrackList.addItem(str);
    }
  }

  private void buttonSummitMousePressed(MouseEvent e) {
    double trackRadius = Double.parseDouble((String) this.comboBoxTrackList.getSelectedItem());
    Track track = TrackFactory.getTrackInstance(trackRadius);
    atomStructure.addPhysicalObject(ElectronFactory.getInstance(track), trackRadius);
    this.setVisible(false);
  }

  private void buttonSummitMouseClicked(MouseEvent e) {
    // TODO add your code here
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    label1 = new JLabel();
    separator1 = new JSeparator();
    infopanel = new JPanel();
    label2 = new JLabel();
    label3 = new JLabel();
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

      //---- label3 ----
      label3.setText("e");
      label3.setForeground(Color.black);
      label3.setFont(label3.getFont().deriveFont(label3.getFont().getSize() + 12f));
      infopanel.add(label3, "cell 9 1");
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
      public void mouseClicked(MouseEvent e) {
        buttonSummitMouseClicked(e);
      }

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
