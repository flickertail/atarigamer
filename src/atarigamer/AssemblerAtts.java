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
import java.io.*;

public class AssemblerAtts extends JPanel{
  protected GrDesktop grd;
  JTextField para = new JTextField();
  JLabel paraL = new JLabel("General Parameters");
  JTextField inc = new JTextField();
  JLabel incL = new JLabel("\"Includes\" Directory");
  JTextField file = new JTextField();
  JLabel fileL = new JLabel("File");
  JTextField out = new JTextField();
  JLabel outL = new JLabel("Output File Name");
  JTextField app = new JTextField();
  JLabel appL = new JLabel("Assembler Application");
  JButton fileB = new JButton("Browse");
  JButton appB = new JButton("Browse");
  JButton incB = new JButton("Browse");
  JButton outB =  new JButton("Browse");
  JTextField ver = new JTextField();
  JLabel verL = new JLabel("Verbrose Parameters");

  public AssemblerAtts() {
    setUp();
  }
  protected void showMe(){
    JOptionPane.showMessageDialog(grd,this,"Assembler Preferences",JOptionPane.OK_OPTION);
    writeData();
  }
  protected void setGRD(GrDesktop grd){
    this.grd = grd;
  }
  private void setUp(){
    GridLayout gl = new GridLayout(13,2,20,2);
    this.setLayout(gl);
    paraL.setForeground(Color.BLACK);
    this.add(paraL);
    this.add(Box.createHorizontalStrut(30));
    this.add(para);
    this.add(Box.createHorizontalStrut(30));
    this.add(fileL);
    this.add(Box.createHorizontalStrut(30));
    this.add(file);
    this.add(fileB);
    this.add(incL);
    this.add(Box.createHorizontalStrut(30));
    this.add(inc);
    this.add(incB);
    this.add(outL);
    this.add(Box.createHorizontalStrut(30));
    this.add(out);
    this.add(Box.createHorizontalStrut(30));
    this.add(appL);
    this.add(Box.createHorizontalStrut(30));
    this.add(app);
    this.add(appB);
    this.add(verL);
    this.add(Box.createHorizontalStrut(30));
    this.add(ver);
    this.add(Box.createHorizontalStrut(30));
    readData();
  }
  private void readData(){
    File systemFile = new File("assembler.dat");
    if(systemFile.exists()){
      try {
        FileReader in = new FileReader(systemFile);
        BufferedReader br = new BufferedReader(in);
        boolean isReading  = true;
        try{
//          String tString = "";
//          String tString2 = "";
          this.para.setText(br.readLine());
          this.file.setText(br.readLine());
          this.inc.setText(br.readLine());
          this.out.setText(br.readLine());
          this.app.setText(br.readLine());
          this.ver.setText(br.readLine());
          br.close();
        }catch (IOException e) {
          System.out.println(e.toString());
        }
      }catch (FileNotFoundException ex) {
        System.out.println(ex.toString());
      }
    }
  }

  private void writeData(){
    File systemFile = new File("assembler.dat");
    try{
      if (!systemFile.exists()) systemFile.createNewFile();
      String wString = para.getText()+"\n"+file.getText()+"\n"+inc.getText()+"\n"+out.getText()+"\n"+app.getText()+"\n"+ver.getText();
      FileOutputStream fos = new FileOutputStream(systemFile);
      PrintStream ps = new PrintStream(fos);
      ps.print(wString);
      ps.close();
    }catch(FileNotFoundException e){
    }catch(IOException e2){}
  }
}