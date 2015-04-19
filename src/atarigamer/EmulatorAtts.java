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
import java.awt.event.*;

public class EmulatorAtts extends JPanel{
  protected GrDesktop grd;
  JTextField para = new JTextField();
  JLabel paraL = new JLabel("Parameters");
  JTextField file = new JTextField();
  JLabel fileL = new JLabel("File");
  JTextField app = new JTextField();
  JLabel appL = new JLabel("Emulator");
  JButton fileB = new JButton("Browse");
  JButton appB = new JButton("Browse");

  public EmulatorAtts() {
    setUp();
  }

  protected void showMe(){
    JOptionPane.showMessageDialog(grd,this,"Emulator Preferences",JOptionPane.OK_OPTION);
    writeData();
  }
  protected void setGRD(GrDesktop grd){
    this.grd = grd;
  }
  private void setUp(){
    GridLayout gl = new GridLayout(6,2,20,2);
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
    this.add(appL);
    this.add(Box.createHorizontalStrut(30));
    this.add(app);
    this.add(appB);
    fileB.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        try{
          File tFile = grd.devProject.fileChoose("Select Rom File", "Select",
                                                 "Select BIN",
                                                 JFileChooser.FILES_ONLY, null,
                                                 grd.devProject.binFilterArray);
          file.setText(tFile.getAbsolutePath());
        }catch(NullPointerException npe){

        }
      }
    });
    appB.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        try{
          File tFile = grd.devProject.fileChoose("Select Emulator", "Select",
                                                 "Select Executable",
                                                 JFileChooser.FILES_ONLY, null,
                                                 grd.devProject.exeFilterArray);
          app.setText(tFile.getAbsolutePath());
        }catch(NullPointerException npe){

        }
      }
    });

    readData();
  }
  private void readData(){
    File systemFile = new File("emulator.dat");
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
          this.app.setText(br.readLine());
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
    File systemFile = new File("emulator.dat");
    try{
      if (!systemFile.exists()) systemFile.createNewFile();
      String wString = para.getText()+"\n"+file.getText()+"\n"+app.getText();
      FileOutputStream fos = new FileOutputStream(systemFile);
      PrintStream ps = new PrintStream(fos);
      ps.print(wString);
      ps.close();
    }catch(FileNotFoundException e){
    }catch(IOException e2){}
  }
}