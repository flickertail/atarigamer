package atarigamer;

/**
 * <p>Title: Atari Gamer</p>
 * <p>Description: Atari Video Game Console Development Environment</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Atari Aaron</p>
 * @author Aaron.Bergstrom@ndsu.nodak.edu
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;

import java.util.*;

public class PrtScnLine extends JPanel{

public Color pfColor = Color.black;
public String pfColorHex = "FF";
private EmptyBorder eBorder = new EmptyBorder(1,1,1,1);
public JCheckBox[] pf0_box = new JCheckBox[4];
public JCheckBox[] pf1_box = new JCheckBox[8];
public JCheckBox[] pf2_box = new JCheckBox[8];
private JPanel space = new JPanel(new BorderLayout());
public JPanel pf0 = new JPanel(new GridLayout(1,4));
public JPanel pf1 = new JPanel(new GridLayout(1,8));
public JPanel pf2 = new JPanel(new GridLayout(1,8));
public JButton pfColorBut = new JButton();
private DevPF dpf;
protected int startLine;
protected int lineCount;
public int group;
protected boolean defPFTrue = true;

  public PrtScnLine(Color pfColor, String pfColorHex, DevPF dpf, int group) {
    this.group = group;
    if(group < 10){
      pfColorBut.setText("  "+group+": Color ");
    }else{
      pfColorBut.setText(""+group+": Color ");
    }
    this.dpf = dpf;
    if(pfColor != null) this.pfColor = pfColor;
    this.setLayout(new GridLayout(1,3));
    setPFColorBut();
    space.add(pf0);
    this.add(pf1);
    this.add(pf2);
    setPf0();
    setPf1();
    setPf2();
  }
  
  public void setButtonColor(String value)
  {
	  this.pfColorBut.setBackground(dpf.dp.cp.selectColorWithText(value));
  }

  public void setPf0(){
    pf0.setBorder(new EtchedBorder());
    for(int i=0; i<4;i++){
      pf0_box[i] = new JCheckBox();
      pf0_box[i].setBackground(pfColor);
      pf0_box[i].setBorder(eBorder);
      pf0.add(pf0_box[i]);
    }
  }
  public void setPf1(){
    pf1.setBorder(new EtchedBorder());
    for(int i=0; i<8;i++){
      pf1_box[i] = new JCheckBox();
      pf1_box[i].setBackground(pfColor);
      pf1_box[i].setBorder(eBorder);
      pf1.add(pf1_box[i]);
    }
  }
  public void setPf2(){
    pf2.setBorder(new EtchedBorder());
    for(int i=0; i<8;i++){
      pf2_box[i] = new JCheckBox();
      pf2_box[i].setBackground(pfColor);
      pf2_box[i].setBorder(eBorder);
      pf2.add(pf2_box[i]);
    }
  }
  protected void changeColor(){
    pfColor = dpf.acColor;
    pfColorHex = dpf.acHexColor;
    colorRun();
  }
  public void setPFColorBut(){
    pfColorBut.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(dpf,dpf.acLabel,"Select Scan Line Color", JOptionPane.OK_OPTION);
        changeColor();
        defPFTrue = false;
      }
    });
    pfColorBut.setBorder(new EtchedBorder());
    this.setFont(new Font("New Times Roman",Font.PLAIN,10));
    space.add("West",pfColorBut);
    this.add(space);
    this.setBackground(pfColor);
  }
  public boolean isDefColor(){
    return defPFTrue;
  }
  private void colorRun(){
    for(int i=0; i<4;i++){
      pf0_box[i].setBackground(pfColor);
    }
    for(int i=0; i<8;i++){
      pf1_box[i].setBackground(pfColor);
    }
    for(int i=0; i<8;i++){
      pf2_box[i].setBackground(pfColor);
    }
  }
  public void setToDefColor(String pfColorHex){
    this.pfColorHex = pfColorHex;
    pfColor = dpf.dp.cp.selectColorWithText(pfColorHex);//(Color)dpf.dp.colorHash.get(pfColorHex);
    colorRun();
    defPFTrue = true;
  }
  protected void setStartLine(int startLine){
    this.startLine = startLine;
  }
  protected void setLineCount(int lineCount){
    this.lineCount = lineCount;
  }
  protected void checkBoxesByGraphics(boolean[] boxes){
    int countNum = 0;
    int bl = boxes.length;
    while(countNum < 4){
      if(boxes[countNum])pf0_box[countNum].setSelected(true);
      countNum = countNum+1;
    }
    while(countNum > 3 & countNum < 12){
      int i = countNum - 4;
      if(boxes[countNum])pf1_box[i].setSelected(true);
      countNum = countNum+1;
    }
    while(countNum > 11 & countNum < 20){
      int i = countNum - 12;
      if(boxes[countNum])pf2_box[i].setSelected(true);
      countNum = countNum+1;
    }
  }
  protected void unCheckBoxesByGraphics(boolean[] boxes){
    int countNum = 0;
    int bl = boxes.length;
    while(countNum < 4){
      if(boxes[countNum])pf0_box[countNum].setSelected(false);
      countNum = countNum+1;
    }
    while(countNum > 3 & countNum < 12){
      int i = countNum - 4;
      if(boxes[countNum])pf1_box[i].setSelected(false);
      countNum = countNum+1;
    }
    while(countNum > 11 & countNum < 20){
      int i = countNum - 12;
      if(boxes[countNum])pf2_box[i].setSelected(false);
      countNum = countNum+1;
    }
  }
}