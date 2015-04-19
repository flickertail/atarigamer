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
import java.awt.*;
import javax.swing.border.*;

public class SpriteDisplay extends JComponent{
  public SpriteDisplay() {
    this.setName("Sprite Display");
    this.setBorder(new EmptyBorder(5,5,5,5));
  }

}