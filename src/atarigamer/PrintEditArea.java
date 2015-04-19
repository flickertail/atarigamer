package atarigamer;

/**
 * <p>Title: Atari Gamer</p>
 * <p>Description: Atari Video Game Console Development Environment</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Atari Aaron</p>
 * @author Aaron.Bergstrom@ndsu.nodak.edu
 * @version 1.0
 */

import java.awt.print.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.print.*;
import java.util.*;
import javax.print.*;
import javax.print.attribute.standard.*;
import javax.print.attribute.*;
import javax.print.event.*;
import java.io.*;


public class PrintEditArea extends JTextArea implements Printable, Pageable{
  DevProject dp;
  String filename = "tPrint.txt";
  File printfile = new File(filename);
  FileReader fr;
  PrinterJob printJob;
  PrintService printer;
  PageFormat pageformat;
  double pWidth;
  double pHeight;
  int piWidth;
  int piHeight;
  ASMEditor asm;
  int textBottom;
  double pagetotal;
  int ptotal;
  Point pt;
  int countOff = 0;
  String readOff = "";
  String stringTemp = "";
  double ySpacing = 10;
  double yPos = 0;
  double xPos = 0;
  Paper paper;
  int permission = PAGE_EXISTS;
  int pageCount = 1;
  double pages;
  int pCountLength;
  Vector pageList = new Vector(1);
  Vector paintVec;

  public PrintEditArea() {
    this.setLineWrap(true);
  }

  public PrintEditArea(DevProject dp, ASMEditor asm){
    this.asm = asm;
    this.dp = dp;
    this.setLineWrap(true);
  }

  public void setDP(ASMEditor asm){
    this.asm = asm;
    this.dp = asm.dp;
  }

  protected void setPrintObjects(){
    printJob = PrinterJob.getPrinterJob();
    printer = printJob.getPrintService();
    pageformat = new PageFormat();
  }

  protected void printSetUp(){
    pWidth = pageformat.getImageableWidth();
    pHeight = pageformat.getImageableHeight();
    pt = new Point(0,0);
    pWidth = Math.floor(pWidth);
    pHeight = Math.floor(pHeight);
    piWidth = (int)pWidth;
    piHeight = (int)pHeight-16;
    this.xPos = pageformat.getImageableX();
    this.yPos = pageformat.getImageableY()+8;
    readOff = this.getText();
    pages = (double)piHeight/10;
    pages = Math.floor(pages);
    pCountLength = (int)pages;
  }
  protected void printIt(){
    printJob.setPageable(this);
    if(printJob.printDialog()){
      try{
        printJob.print();
      }catch(PrinterException pe){
        JOptionPane.showMessageDialog(dp.grd,"The Program has thrown a printer error.","Printer Error",JOptionPane.OK_OPTION);
      }
    }
  }

  protected void startPrintProcess(){
    setPrintObjects();
    setThisText();
    printSetUp();
    printIt();
    pageCountReset();
  }

  protected void pageCountReset(){
    this.pageCount = 1;
  }

  protected void setThisText(){
    String startString = asm.editArea.getText();
    String procString = "1    ";
    boolean processing = true;
    int countUp = 2;
    while(processing){
      try{
        int retIndex = startString.indexOf("\n");
        String string1 = startString.substring(0,retIndex+1);
        procString = procString+string1+countUp+"    ";
        if(retIndex == startString.length()-1) processing = false;
        startString = startString.substring(retIndex+1);
      }catch(NullPointerException npe){
        procString = procString+startString;
        processing = true;
      }
      countUp = countUp+1;
    }
    startString = procString;
    processing = true;
    procString = "";
    while(processing){
      try{
        int tabIndex = startString.indexOf("\t");
        String string1 = startString.substring(0,tabIndex);
        procString = procString + string1 + "     ";
        if(tabIndex == startString.length()-1) processing = false;
        startString = startString.substring(tabIndex+1);
      }catch(NullPointerException npe){
        procString = procString+startString;
        processing = false;
      }catch(StringIndexOutOfBoundsException sioobe){
        procString = procString+startString;
        processing = false;
      }
    }
    this.setText(procString);
  }

  public int print(Graphics g,PageFormat pf,int pageIndex) throws PrinterException {
    yPos = pageformat.getImageableY()+8;
    Graphics2D g2d = (Graphics2D) g;
    g2d.setFont(new Font("Times New Roman", Font.PLAIN, 10));
    int psl = paintVec.size();
    for (int i = 0; i < psl; i++) {
      g2d.drawString( (String) paintVec.get(i), (float) xPos, (float) yPos);
      yPos = yPos + ySpacing;
    }
    return PAGE_EXISTS;
  }

  protected Vector getPageStrings(){
    Vector pagestrings = new Vector(1);
    String editString = "";
    int pCountUp = 0;
    pagestrings.add(pCountUp,asm.getTitle());
    pCountUp = 1;
    String blank = "     ";
    pagestrings.add(pCountUp,blank);
    pCountUp = 2;
    boolean testEnd = false;
    boolean processing = true;
    boolean checkEnd = false;

    while(processing){
      if (pCountUp >= pCountLength-3) {
        pagestrings.add(pCountUp, blank);
        processing = false;
      }else{
        try {
          int retIndex = readOff.indexOf("\n");
          String string1 = readOff.substring(0, retIndex);
          if (retIndex == readOff.length() - 2) {
            testEnd = true;
            pagestrings.add(pCountUp, string1);
            readOff = " ";
          }else{
            readOff = readOff.substring(retIndex + 1);
            pagestrings.add(pCountUp, string1);
          }
        }catch (NullPointerException npe) {
          if(checkEnd){
            pagestrings.add(pCountUp, blank);
          }else{
            pagestrings.add(pCountUp, readOff);
            checkEnd = true;
          }
          testEnd = true;
        }catch (StringIndexOutOfBoundsException sioobe){
          if(checkEnd){
            pagestrings.add(pCountUp, blank);
          }else{
            pagestrings.add(pCountUp, readOff);
            checkEnd = true;
          }
          testEnd = true;
        }
      }
      pCountUp = pCountUp+1;
    }

    if(testEnd){
      readOff = " ";
      pagestrings.add(pagestrings.size(),"Page: "+pageCount);
    }else{
      pagestrings.add(pagestrings.size(),"Page: "+pageCount);
      pageCount = pageCount+1;
    }
    return pagestrings;
  }

  public int getNumberOfPages(){
    int vec_c = 0;
    while(readOff.length()>0){
      pageList.add(vec_c,getPageStrings());
      if(readOff.equals(new String(" "))) readOff = "";
      vec_c = vec_c+1;
    }
    ptotal = pageList.size();
    return ptotal;
  }
  public Printable getPrintable(int pageIndex){
    paintVec = (Vector)pageList.get(pageIndex);
    return this;
  }
  public PageFormat getPageFormat(int pageIndex){
    return pageformat;
  }
}