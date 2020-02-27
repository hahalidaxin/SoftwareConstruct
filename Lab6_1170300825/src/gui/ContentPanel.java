package gui;

import adts.Ladder;
import adts.Monkey;
import bootstrap.MonkeyGenerator;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * .
 *
 * @author hahalidaxin
 */
public class ContentPanel extends JPanel {
  //布局设置
  public static final int MARGIN = 50;
  public static final int WAITINGAREAWIDTH = 50;
  public static final int WAITINGAREAHEIGHT = 800;
  public static final int CROSSINGAREAWIDTH = 800;
  public static final int CROSSINGAREAHEIGHT = 800;

  MonkeyGenerator generator;
  public ContentPanel(MonkeyGenerator generator) {
    this.generator = generator;
  }

  public void refresh() {
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawContentPanel(g);
    drawInfoPanel(g);
  }

  private void drawContentPanel(Graphics g) {
    List<Monkey> leftMonkeys = generator.monkeys.stream()
        .filter(x -> x.getState() == 1 && x.getDirection().equals("L->R"))
        .collect(Collectors.toList());
    List<Monkey> rightMonkeys = generator.monkeys.stream()
        .filter(x -> x.getState() == 1 && x.getDirection().equals("R->L"))
        .collect(Collectors.toList());
    List<Monkey> crossingMonkeys = generator.monkeys.stream()
        .filter(x -> x.getState() == 2)
        .collect(Collectors.toList());


    //  绘制桥

    int stx = WAITINGAREAWIDTH + MARGIN;
    int sty = MARGIN;
    int width = (CROSSINGAREAWIDTH - 2 * MARGIN) / generator.h;
    int height = width;
    int ystrep = (CROSSINGAREAHEIGHT - 2 * MARGIN) / generator.n;

    int x = stx;
    int y = sty;
    List<Ladder> ladders = generator.ladders;
    for (int i = 0; i < ladders.size(); i++) {
      Ladder ladder = ladders.get(i);
      for (int j = 0; j < ladder.getRungLength(); j++) {
        g.drawRect(x, y, width, height);
        x += width;
      }
      x = stx;
      y += ystrep;
    }
    //  绘制过桥猴子
    for (Monkey monkey : crossingMonkeys) {
      int li = monkey.getLadder().getLadderIndex();
      int ri = monkey.getRungIndex();
      x = (ri - 1) * width + stx;
      y = (li - 1) * ystrep + sty;
      g.drawString(String.format("[%d,%d]", monkey.getId(), monkey.getVel()), x, y + width / 2);
      //g.fillOval(x, y, width, height);
    }
    //  绘制等待方格和猴子
    int leftsize = leftMonkeys.size();
    if (leftsize > 0) {
      height = WAITINGAREAHEIGHT / leftsize;
      width = WAITINGAREAWIDTH;
      x = width / 8;
      y = 0;
      for (int i = 0; i < leftsize; i++) {
        Monkey monkey = leftMonkeys.get(i);
        g.drawString(String.format("[%d,%d]", monkey.getId(), monkey.getVel()), x, y + height / 2);
        g.drawRect(x, y, width, height);
        y += height;
      }
    }

    int rightsize = rightMonkeys.size();
    if (rightsize > 0) {
      height = WAITINGAREAHEIGHT / rightsize;
      width = WAITINGAREAWIDTH;
      x = WAITINGAREAWIDTH + CROSSINGAREAWIDTH + width / 8;
      y = 0;
      for (int i = 0; i < rightsize; i++) {
        Monkey monkey = rightMonkeys.get(i);
        g.drawString(String.format("[%d,%d]", monkey.getId(), monkey.getVel()), x, y + height / 2);
        g.drawRect(x, y, width, height);
        y += height;
      }
    }
  }

  private void drawInfoPanel(Graphics g) {
    int stX = MARGIN * 3 + WAITINGAREAWIDTH + CROSSINGAREAWIDTH;
    int stY = MARGIN;
    int x = stX;
    int y = stY;
    long count = generator.monkeys.stream()
        .filter(it -> it.getState() == 3).count();
    g.drawString(String.format("已经达到对岸的猴子数目：%d",
        count), x, y);
    if (count == generator.N) {
      g.drawString(generator.getInfoOfGame(),x,y+30);
    }
  }
}
