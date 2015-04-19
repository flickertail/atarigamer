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
import javax.swing.border.*;
import java.awt.*;

public class ExtrasPanel extends JPanel{
  public ExtrasPanel() {
    this.setName("Extras");
    this.setBorder(new EmptyBorder(0,0,0,0));
    this.setLayout(new BorderLayout(0,0));
    this.add("West",Box.createHorizontalStrut(15));
    this.add("East",Box.createHorizontalStrut(15));
  }

}