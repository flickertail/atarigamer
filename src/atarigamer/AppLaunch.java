package atarigamer;

import javax.swing.UIManager;
import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: Atari Gamer</p>
 * <p>Description: Atari Video Game Console Development Environment</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Atari Aaron</p>
 * @author Aaron.Bergstrom@ndsu.nodak.edu
 * @version 1.0
 */

public class AppLaunch {
  private boolean packFrame = false;

  //Construct the application
  public AppLaunch() {
//    SplashOpen();
    SplashOpen blah = new SplashOpen();
    blah.openGraphic(Toolkit.getDefaultToolkit().createImage(AppLaunch.class.getResource("splash.jpg")),300,200);
//    try{
//      Thread.sleep(5000);
//    }catch(Exception blahc){

//    }
    GrDesktop frame = new GrDesktop();

//    Desktop frame = new Desktop();
    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //Center the window
/***** Changed for fullscreen mode
//    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


//    int x = (int)screenSize.getHeight();
    x = x-30;
    screenSize.height = x;
    frame.setSize(screenSize);
*/
//    frame.setVisible(true);
    blah.closeGraphic();
    blah = null;
  }

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new AppLaunch();
  }
}