package atarigamer;

import javax.swing.*;
import java.awt.*;
/**
 * <p>Title: Atari Gamer</p>
 * <p>Description: Atari Video Game Console Development Environment</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Atari Aaron</p>
 * @author Aaron.Bergstrom@ndsu.nodak.edu
 * @version 1.0
 */

public class PosPanel extends JPanel{
  public PrintEditArea pea;
  public JScrollPane jsp;
  public PosPanel(PrintEditArea pea) {
    this.pea = pea;
    setUp();
  }

  protected void setUp(){
    this.setLayout(new BorderLayout());
    jsp = new JScrollPane(pea);
    jsp.setSize(133,100);
    this.setSize(133,100);
    this.add(BorderLayout.CENTER, jsp);
    this.add(BorderLayout.WEST, Box.createVerticalStrut(100));
    this.add(BorderLayout.SOUTH, Box.createHorizontalStrut(133));
    PrintChecker pc = new PrintChecker(this);
    pc.start();
  }

}