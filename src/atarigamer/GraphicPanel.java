package atarigamer;

import javax.swing.*;
import javax.swing.border.*;
import java.lang.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.Cursor;
import java.awt.geom.*;
import java.util.*;

/**
 * <p>Title: Atari Gamer</p>
 * <p>Description: Atari Video Game Console Development Environment</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Atari Aaron</p>
 * @author Aaron.Bergstrom@ndsu.nodak.edu
 * @version 1.0
 */

public class GraphicPanel extends JPanel{
  protected ImageIcon dImage = null;
  protected Vector byteVector = new Vector(1);
  private Color bColor = Color.WHITE;
  private String sName = " ";
  protected int[] pixels;
  protected Image sImage;
  private Component vBox;
  private Component hBox;
  private Image mi;
  private Hashtable editors;

  public GraphicPanel() {
    this.setLayout(new BorderLayout(0,0));
    vBox = Box.createVerticalStrut(0);
    hBox = Box.createHorizontalStrut(0);
    this.add("South",hBox);
    this.add("East",vBox);
    this.setName("Editable Canvas");
    this.setBorder(new EmptyBorder(0,0,0,0));
  }
  public void setVBox(int y){
    this.remove(vBox);
    vBox = Box.createVerticalStrut(y);
    this.add("East",vBox);
  }
  public void setHBox(int x){
    this.remove(hBox);
    hBox = Box.createHorizontalStrut(x);
    this.add("South",hBox);
  }
  public void paint(Graphics g){
    if(sImage != null) {
      Graphics2D g2d = (Graphics2D)g;
      g2d.drawImage(sImage,0,0,Color.WHITE,this);
    }
  }
  public void storePixels(){
    PixelGrabber pg;
    if(dImage != null){

      byteVector.clear();
      int iw = mi.getWidth(this);
      int ih = mi.getHeight(this);
      Image anImage = sImage.getScaledInstance(iw,ih,Image.SCALE_REPLICATE);
      pixels = new int[iw * ih];
      pg = new PixelGrabber(anImage,0,0,iw,ih,pixels,0,iw);
      try{
        pg.grabPixels();
      }catch(InterruptedException ie){}
      int bwInt = getByteWidth((float)iw);
      for(int i=0;i<bwInt;i++){
        Vector vec = new Vector(1);
        byteVector.add(i,vec);
      }
      for(int i=0;i<ih;i++){
        readImageLines(i,bwInt);
      }
    }
  }

  public void setBlankColor(Color color){
    bColor = color;
  }

  public void readImageLines(int row, int vecs){
    int pst = 0;
    int psp = 8;
    for(int i=0;i<vecs;i++){
      Vector tVec = (Vector)byteVector.get(i);
      String vString = "";
      for(int j = pst;j<psp;j++){
        Color c = getPixelColor(row, j);
        if(c.equals(Color.WHITE)){
          vString = vString+"0";
        }else{
          vString = vString+"1";
        }
      }
      tVec.add(vString);
      pst = pst + 8;
      psp = psp + 8;
    }
  }

  public Color getPixelColor(int row, int column){
    int pixel = pixels[row * sImage.getWidth(this) + column];
    int alpha = (pixel >> 24) & 0xff;
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = pixel & 0xff;
    Color c = new Color(red, green, blue, alpha);
    return c;
  }
  public int getLineHeight(float lif){
    return (int)lif;
  }

  public int getByteWidth(float bif){
    bif = bif/8;
    return (int)bif;
  }

  public void registerASMEditors(Hashtable editors){
    this.editors = editors;
    Collection col = this.editors.values();
    Object[] obj = col.toArray();
    int colint = obj.length;
    for(int i=0;i<colint;i++){
      ASMEditor tedit = (ASMEditor)obj[i];
      tedit.addGraphicPanel(this);
    }
  }

  public void setGraphicName(String name){
    sName = name;
  }

  public String getGraphicName(){
    return sName;
  }

  public void setMyImage(ImageIcon icon){
    dImage = icon;
    setVBox(dImage.getIconHeight());
    setHBox(dImage.getIconWidth());
    sImage = dImage.getImage();
  }

  public void setMasterImage(Image mi){
    this.mi = mi;
  }
}