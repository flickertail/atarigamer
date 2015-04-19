package atarigamer;

//Here is an implementation using swing 1.1 and JLabel
/************************************************************/
// Author: Manoj Agarwala manoj@ysoftware.com
/************************************************************/

import java.awt.*;
import java.applet.*;
import java.awt.Image;
import javax.swing.*;

public class SplashOpen{
  JLabel spLabel;
  JWindow spWindow;
  JFrame spFrame;

  public SplashOpen(){

  }
  public void openGraphic(Image image, int width, int height){
    spFrame = new JFrame();
    spFrame.addNotify();
    spWindow = new JWindow(spFrame);
    spWindow.addNotify();
    spLabel = new JLabel(new ImageIcon(image));
    spWindow.getContentPane().add(spLabel);
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    int x = ( dim.width - width )/2;
    int y = ( dim.height - height )/2;
    spWindow.setLocation(x,y);
    spWindow.setSize(width,height);
    spWindow.setVisible(true);
  }
  public void closeGraphic(){
    spWindow.setVisible(false);
    spLabel = null;
    spFrame = null;
    spWindow = null;
  }
}
