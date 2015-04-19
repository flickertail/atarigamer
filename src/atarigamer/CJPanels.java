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
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

public class CJPanels extends JPanel implements ImageObserver{
  protected Color[][] ca;
  protected CJPanels cj;
  protected JPanel tp = new JPanel(new BorderLayout(0,0));
  protected BufferedImage bi;
  protected PixelGrabber pg;
  protected Hashtable x = new Hashtable();
  protected Hashtable xout = new Hashtable();
  protected Hashtable y = new Hashtable();
  protected Hashtable yout = new Hashtable();
  private CC2600 frame;
  protected int[] pixels;
  private Cursor dropperCur;
  private Cursor defaultCur;

  public CJPanels(){

  }

  public CJPanels(Color[][] ca, String name, CC2600 frame) {
    this.cj = this;
    this.ca = ca;
    this.pg = pg;
    this.frame = frame;
    tp.setBorder(new TitledBorder(name));
    tp.add("Center",this);
    setHashtables();
    generateImage();
    generateCursors();
    setListener();
  }

  protected void generateCursors(){
    defaultCur = new Cursor(Cursor.DEFAULT_CURSOR);
    dropperCur = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().createImage(CJPanels.class.getResource("eyedrop.gif")),new Point(17,17),"eyedrop");
  }

  protected void setHashtables(){
    x.put("0","0");
    x.put("1","15");
    x.put("2","30");
    x.put("3","45");
    x.put("4","60");
    x.put("5","75");
    x.put("6","90");
    x.put("7","105");
    x.put("8","120");
    x.put("9","135");
    x.put("A","150");
    x.put("B","165");
    x.put("C","180");
    x.put("D","195");
    x.put("E","210");
    x.put("F","225");

    xout.put("0","0");
    xout.put("15","1");
    xout.put("30","2");
    xout.put("45","3");
    xout.put("60","4");
    xout.put("75","5");
    xout.put("90","6");
    xout.put("105","7");
    xout.put("120","8");
    xout.put("135","9");
    xout.put("150","A");
    xout.put("165","B");
    xout.put("180","C");
    xout.put("195","D");
    xout.put("210","E");
    xout.put("225","F");

    y.put("0","0");
    y.put("1","0");
    y.put("2","15");
    y.put("3","15");
    y.put("4","30");
    y.put("5","30");
    y.put("6","45");
    y.put("7","45");
    y.put("8","60");
    y.put("9","60");
    y.put("A","75");
    y.put("B","75");
    y.put("C","90");
    y.put("D","90");
    y.put("E","105");
    y.put("F","105");

    yout.put("0","0");
    yout.put("15","2");
    yout.put("30","4");
    yout.put("45","6");
    yout.put("60","8");
    yout.put("75","A");
    yout.put("90","C");
    yout.put("105","E");
  }

  protected void generateImage(){
    Graphics2D g2d = null;
    int width = 16 * 15;
    int height = 8 * 15;
    JPanel dpanel = new JPanel();
    bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    g2d = bi.createGraphics();
    drawGraphic(g2d);
    pixels = new int[width * height];
    pg = new PixelGrabber(bi,0,0,width,height,pixels,0,width);
    try{
      pg.grabPixels();
    }catch(InterruptedException ie){}
  }

  public void drawGraphic(Graphics2D g2d){
    int lum = 0;
    int col = 0;
    for(int i=0;i<8;i++){
      for(int j=0;j<16;j++){
        g2d.setColor(ca[j][i]);
        Rectangle2D.Float tr = new Rectangle2D.Float(col,lum,15,15);
        g2d.fill(tr);
        g2d.draw(tr);
        col = col+15;
      }
      col = 0;
      lum = lum+15;
    }
  }

  public Color getPixelColor(int row, int column, Image sImage){
    int pixel = pixels[row * sImage.getWidth(this) + column];
    int alpha = (pixel >> 24) & 0xff;
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = pixel & 0xff;
    Color c = new Color(red, green, blue, alpha);
    return c;
  }

  public void paint(Graphics g){
    Graphics2D g2d = (Graphics2D)g;
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("Veranda",Font.PLAIN,12));
    g2d.drawString("0",30,10);
    g2d.drawString("1",45,10);
    g2d.drawString("2",60,10);
    g2d.drawString("3",75,10);
    g2d.drawString("4",90,10);
    g2d.drawString("5",105,10);
    g2d.drawString("6",120,10);
    g2d.drawString("7",135,10);
    g2d.drawString("8",150,10);
    g2d.drawString("9",165,10);
    g2d.drawString("A",180,10);
    g2d.drawString("B",195,10);
    g2d.drawString("C",210,10);
    g2d.drawString("D",225,10);
    g2d.drawString("E",240,10);
    g2d.drawString("F",255,10);
    g2d.drawString("0",15,25);
    g2d.drawString("2",15,40);
    g2d.drawString("4",15,55);
    g2d.drawString("6",15,70);
    g2d.drawString("8",15,85);
    g2d.drawString("A",15,100);
    g2d.drawString("C",15,115);
    g2d.drawString("E",15,130);
    g2d.drawImage(bi,27,13,Color.BLACK,this);
//    g2d.drawImage(gImage,0,0,Color.BLACK,this);
  }

  public void setListener(){
    tp.addMouseListener(new MouseListener(){
      public void mouseClicked(MouseEvent evt) {

      }

      public void mousePressed(MouseEvent evt) {

      }

      public void mouseEntered(MouseEvent evt) {
        frame.setCursor(dropperCur);
      }

      public void mouseExited(MouseEvent evt) {
        frame.setCursor(defaultCur);
      }

      public void mouseReleased(MouseEvent evt) {

      }
    });
    tp.addMouseMotionListener(new MouseMotionListener(){
      public void mouseMoved(MouseEvent evt){

      }
      public void mouseDragged(MouseEvent evt){

      }
    });
  }
  private void chooseColor(){

  }
}