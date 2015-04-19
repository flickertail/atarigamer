package atarigamer;

/**
 * <p>Title: Atari Gamer</p>
 * <p>Description: Atari Video Game Console Development Environment</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Atari Aaron</p>
 * @author Aaron.Bergstrom@ndsu.nodak.edu
 * @version 1.0
 */

import javax.swing.*;
import java.lang.*;
import java.awt.geom.*;
import java.awt.*;
import java.io.*;


public class ProgressPanel extends JPanel{
  protected int amount = 0;
//  protected WebBrowser wb;

  public ProgressPanel() {
//    this.wb = wb;
    this.setSize(104,16);
  }
  public void setAmount(int num){
    amount = num;
    if(amount > 100) amount = 100;
    if(amount < 0) amount = 0;
  }
  public void paint(Graphics g){
    Graphics2D g2d = (Graphics2D)g;
    g2d.setColor(new Color(175,175,175));
    Rectangle2D.Float bk = new Rectangle2D.Float(0,3,100,10);
    g2d.fill(bk);
    g2d.draw(bk);
    g2d.setColor(new Color(0,153,255));
    Rectangle2D.Float dlr = new Rectangle2D.Float(0,3,amount,10);
    g2d.fill(dlr);
    g2d.draw(dlr);
    g2d.setColor(Color.DARK_GRAY);
    g2d.drawRect(0,3,100,10);
  }


}