package atarigamer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;

/**
 * <p>Title: Atari Gamer</p>
 * <p>Description: Atari Video Game Console Development Environment</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Atari Aaron</p>
 * @author Aaron.Bergstrom@ndsu.nodak.edu
 * @version 1.0
 */

public class GrDesktop extends JFrame{
  JPanel contentPane;
  DevProject devProject;
  PrintStream outside = System.out;
  PrintStream inside;
  FileOutputStream outstream;
  Dimension pToolsLoc = new Dimension();
  Dimension outPLoc = new Dimension();
  Dimension outPSize = new Dimension();
  JLabel statusBar = new JLabel("Status Bar");
  JMenuBar mBar = new JMenuBar();
  JMenu files = new JMenu("Files");
  JMenu help = new JMenu("Help");
  JMenuItem tutor = new JMenuItem("Tutorials");
  JMenuItem about = new JMenuItem("About CGDK");
  JMenuItem newPF = new JMenuItem("New Playfield");
  JMenuItem openPF = new JMenuItem("Open Playfield");
  JMenuItem newSprite = new JMenuItem("New Sprite");
  JMenuItem openSprite = new JMenuItem("Open Sprite");
  JMenuItem newAud = new JMenuItem("New Audio Track");
  JMenuItem openAud = new JMenuItem("Open Audio Track");
  JMenuItem newMissile = new JMenuItem("New Missle");
  JMenuItem openMissile = new JMenuItem("Open Missle");
  JMenuItem newAsm = new JMenuItem("New ASM");
  JMenuItem openAsm = new JMenuItem("Open ASM");
  JMenuItem newPFBall = new JMenuItem("New Playfield Ball");
  JMenuItem openPFBall = new JMenuItem("Open Playfield Ball");
  JMenuItem setAssem = new JMenuItem("Assembler Preferences");
  JMenuItem runAssem = new JMenuItem("Run Assembler");
  JMenuItem setEmul = new JMenuItem("Emulator Preferences");
  JMenuItem runEmul = new JMenuItem("Run Emulator");
  JMenuItem exit = new JMenuItem("Exit");
  JMenu toolbars = new JMenu("Tools");
  JMenu colorChooser = new JMenu("Color Chooser");
  JMenuItem showPT = new JMenuItem("Show Paint Tools");
  JMenuItem showCC = new JMenuItem("Show Color Chooser");
  JMenu setCType = new JMenu("Select Color Format");
  JMenuItem ntsc = new JMenuItem("NTSC");
  JMenuItem pal = new JMenuItem("PAL");
  JMenuItem secam = new JMenuItem("SECAM");
  JMenuItem showHTML = new JMenuItem("Show Web Browser");
  JToolBar jToolBar = new JToolBar();
  ImageIcon image1;
  ImageIcon image2;
  ImageIcon image3;
  ImageIcon image4;
  ImageIcon image5;
  ImageIcon image6;
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  JButton jButton4 = new JButton();
  JButton jButton5 = new JButton();
  JButton jButton6 = new JButton();
  GrDesktop grd;
  protected AssemblerAtts aa = new AssemblerAtts();
  protected EmulatorAtts ea = new EmulatorAtts();
  GraphicsEnvironment ge;
  GraphicsDevice[] gds;
  GraphicsDevice gd;
  boolean software;
  protected JToolBar icontool = new JToolBar();
  protected JButton exitButton = new JButton(" EXIT ");


  public GrDesktop() {
    this.grd = this;
    settingsRead();
    wSetup();
  }
  private void wSetup(){
    Toolkit tk = Toolkit.getDefaultToolkit();
    exitButton.setIcon(new ImageIcon(tk.createImage(GrDesktop.class.getResource("ag_fi.gif"))));
    this.setIconImage(tk.createImage(GrDesktop.class.getResource("ag.gif")));
    setTitle(" Classic Game Development Kit");
    exitButton.setMargin(new Insets(1,1,1,1));
    ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    gds = ge.getScreenDevices();
    gd = gds[0];
    grd.setResizable(false);
    grd.setUndecorated(true);
    Dimension screensize = tk.getScreenSize();
    grd.setSize(screensize);
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(new BorderLayout());
    devProject = new DevProject(this);
    setUPTB();
    menuSetup();
    menuActions();
    contentPane.add("North",jToolBar);
    this.setJMenuBar(mBar);
    aa.setGRD(this);
    ea.setGRD(this);
    Thread timeThread = new Thread(){
      public void run(){
        JPanel tp = new JPanel(new BorderLayout(0,0));
        grd.contentPane.add("South",tp);
        JLabel time = new JLabel();
        icontool.setFloatable(false);
        icontool.add(exitButton);
        exitButton.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
            System.exit(0);
          }
        });
        icontool.add(new JLabel("               "));
        icontool.add(Box.createVerticalStrut(25));
        tp.add("Center",icontool);
        tp.add("East",time);
        software = true;
        int num = 0;
        while(software){
          try{
            Date date = new Date();
            String sdate = date.toString();
            int dint = sdate.indexOf(":");
            String hour = sdate.substring(dint - 2, dint);
            String minute = sdate.substring(dint, dint + 3);
            String am_pm = "";
            int hvalue = Integer.parseInt(hour);
            if (hvalue > 11) {
              am_pm = "PM";
              if (hvalue > 12) {
                hvalue = hvalue - 12;
              }
            }
            else {
              if(hvalue==0) hvalue = 12;
              am_pm = "AM";
            }
            sdate = "" + hvalue + minute + " " + am_pm + "  ";
            time.setText(sdate);
          }catch(ArrayIndexOutOfBoundsException aeo){}
          try{
            Thread.sleep(1000);
          }catch(InterruptedException ie){}
        }
      }
    };
    timeThread.start();
    grd.setVisible(true);
  }
  private void menuSetup(){
    mBar.add(files);
    mBar.add(toolbars);
    mBar.add(help);
    help.add(tutor);
    help.addSeparator();
    help.add(about);
    colorChooser.add(showCC);
    colorChooser.add(setCType);
    setCType.add(ntsc);
    setCType.add(pal);
    setCType.add(secam);
    toolbars.add(showPT);
    toolbars.add(colorChooser);
    toolbars.add(showHTML);
//    showCC.addActionListener(new ActionListener()  {
//      public void actionPerformed(ActionEvent e) {
//        devProject.cc.setVisible(true);
//      }
//    });

    ntsc.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        devProject.cPalette = 0;
      }
    });

    pal.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
          devProject.cPalette = 1;
      }
    });

    secam.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
          devProject.cPalette = 2;
      }
    });

    showHTML.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        devProject.wb.showMe();
      }
    });

    files.add(newPF);
    files.add(openPF);
    files.addSeparator();
    files.add(newSprite);
    files.add(openSprite);
    files.addSeparator();
    files.add(newAud);
    files.add(openAud);
    files.addSeparator();
    files.add(newAsm);
    files.add(openAsm);
    files.addSeparator();
    files.addSeparator();
    files.add(setAssem);
    files.add(runAssem);
    files.addSeparator();
    files.add(setEmul);
    files.add(runEmul);
    files.addSeparator();
    files.addSeparator();
    files.addSeparator();
    files.add(exit);
  }
  protected void menuActions(){
    runAssem.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        runAssembler();
      }
    });

    runEmul.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        runEmulator();
      }
    });

    newPF.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        devProject.addNewPlayfield();
      }
    });

    about.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        Desktop_AboutBox dlg = new Desktop_AboutBox(grd);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.pack();
        dlg.show();
      }
    });
    setAssem.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        aa.showMe();
      }
    });

    setEmul.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        ea.showMe();
      }
    });

    openSprite.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        devProject.loadSprite();
      }
    });
    newSprite.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        devProject.newSprite();
      }
    });
    openPF.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        devProject.loadPlayfield();
      }
    });
    newAsm.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        devProject.addNewASMEdit(null);
      }
    });
    openAsm.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        devProject.loadASMFile();
      }
    });

    exit.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        exit_actionPerformed(null);
      }
    });
    showPT.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        devProject.pTools.setVisible(true);
      }
    });
  }
  private void setUPTB(){
    image1 = new ImageIcon(atarigamer.GrDesktop.class.getResource("npf.gif"));
    image2 = new ImageIcon(atarigamer.GrDesktop.class.getResource("sprite.gif"));
    image3 = new ImageIcon(atarigamer.GrDesktop.class.getResource("audio.gif"));
    image4 = new ImageIcon(atarigamer.GrDesktop.class.getResource("asmedit.gif"));
    image5 = new ImageIcon(atarigamer.GrDesktop.class.getResource("color_but.gif"));
    image6 = new ImageIcon(atarigamer.GrDesktop.class.getResource("html.gif"));

    jButton1.setIcon(image1);
    jButton2.setIcon(image2);
    jButton3.setIcon(image3);
    jButton4.setIcon(image4);
    jButton5.setIcon(image5);
    jButton6.setIcon(image6);
    EtchedBorder newBord = new EtchedBorder(EtchedBorder.LOWERED);
    newBord.getBorderInsets(jButton1,new Insets(0,0,0,0));
    jButton1.setMargin(new Insets(0,0,0,0));
    jButton2.setMargin(new Insets(0,0,0,0));
    jButton3.setMargin(new Insets(0,0,0,0));
    jButton4.setMargin(new Insets(0,0,0,0));
    jButton5.setMargin(new Insets(0,0,0,0));
    jButton6.setMargin(new Insets(0,0,0,0));


    jButton1.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        devProject.addNewPlayfield();
      }
    });
    jButton2.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        devProject.newSprite();
      }
    });
    jButton3.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {

      }
    });
    jButton4.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        devProject.addNewASMEdit(null);
      }
    });

    jButton5.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        devProject.cc.setVisible(true);
      }
    });

    jButton6.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        devProject.wb.showMe();
      }
    });

    jButton1.setToolTipText("New Playfield");
    jButton2.setToolTipText("New Sprite");
    jButton3.setToolTipText("New Audio Track");
    jButton4.setToolTipText("New ASM Editor");
    jButton5.setToolTipText("Show Color Chooser");
    jButton6.setToolTipText("Show Web Browser");
    jToolBar.add(jButton1);
    jToolBar.add(jButton2);
    jToolBar.add(jButton3);
    jToolBar.add(jButton4);
    jToolBar.add(jButton5);
    jToolBar.add(jButton6);
    jToolBar.setFloatable(false);
  }
  protected void settingsRead(){
    String settingName = "settings.txt";
    File settings = new File(settingName);
    if(!settings.exists()){
      URL defset = GrDesktop.class.getResource("def_settings.txt");
      settings = new File(defset.getFile());
    }
    try{
      FileReader in = new FileReader(settings);
      BufferedReader br = new BufferedReader(in);
      setADim(br.readLine(),outPLoc);
      setADim(br.readLine(),outPSize);
      setADim(br.readLine(),pToolsLoc);
      br.close();
    }
    catch(FileNotFoundException ex){
      System.out.println(ex.toString());
    }
    catch (IOException ioex){
      System.out.println(ioex.toString());
    }
  }

  protected void systemRead(){
    File systemFile = new File("system.txt");
    if(systemFile.exists()){
      try {
        FileReader in = new FileReader(systemFile);
        BufferedReader br = new BufferedReader(in);
        boolean isReading  = true;
        while(isReading){
          try{
            String tString = br.readLine();
            if(tString==null){
              br.close();
              this.devProject.opTA.append("\n");
              isReading = false;
            }else{
              tString = tString +"\n";
              this.devProject.opTA.append(tString);
            }
          }catch (IOException e) {
            System.out.println(e.toString());
            isReading = false;
          }
        }
      }catch (FileNotFoundException ex) {
        System.out.println(ex.toString());
      }
    }
  }


  protected void setABool(String temp, boolean tBool){
    int tsize = temp.length();
    String t1 = "";
    String space = " ";
    for(int i=0;i<tsize;i++){
      if(space.equals(new String("")+temp.charAt(i))){
        i = tsize;
        if(t1.equals(new String("false"))){
          tBool = false;
          System.out.println(t1);
        }else{
          tBool = true;
          System.out.println(t1);
        }
      }else{
        t1 = t1+temp.charAt(i);
      }
    }
  }
  protected void setADim(String temp, Dimension tDim){
    int tsize = temp.length();
    boolean first = true;
    String t1 = "";
    String t2 = "";
    String comma = ",";
    String space = " ";
    for(int i=0;i<tsize;i++){
      if(first){
        if(comma.equals(new String("")+temp.charAt(i))){
          first = false;
        }else{
          t1 = t1+temp.charAt(i);
        }
      }else{
        if(space.equals(new String("")+temp.charAt(i))){
          i = tsize;
          int d1 = Integer.parseInt(t1);
          int d2 = Integer.parseInt(t2);
          tDim.width = d1;
          tDim.height = d2;
        }else{
          t2 = t2+temp.charAt(i);
        }
      }
    }
  }

  protected void settingsWrite(){
    String settingName = "settings.txt";
    File settings = new File(settingName);
    try{
      if(!settings.exists()) settings.createNewFile();
      FileWriter setWrite = new FileWriter(settings.getPath(),false);
      BufferedWriter bw = new BufferedWriter(setWrite);
      bw.write(""+outPLoc.width+","+outPLoc.height+" ;Output Window Location\n");
      bw.write(""+outPSize.width+","+outPSize.height+" ;Output Window Size\n");
      bw.write(""+pToolsLoc.width+","+pToolsLoc.height+" ;Paint Tools Location");
      bw.close();
    }catch(IOException ioex){
    }
  }
  protected void exit_actionPerformed(ActionEvent e) {
    processClose();
    settingsWrite();
    System.exit(0);
  }
  protected void processClose(){
    Point tempPt = devProject.dpOutput.getLocation();
    this.outPLoc.width = tempPt.x;
    this.outPLoc.height = tempPt.y;
    this.outPSize = devProject.dpOutput.getSize();
    tempPt = devProject.pTools.getLocation();
    this.pToolsLoc.width = tempPt.x;
    this.pToolsLoc.height = tempPt.y;
  }

  protected void writeTextAreaFile(JTextArea jta,String filename){
    File newDir = new File(filename);
    try{
      File newPF = new File(filename);
      try{
        if(!newPF.exists()){
          newPF.createNewFile();
        }
        FileWriter test = new FileWriter(newPF.getPath(),false);
        BufferedWriter bw = new BufferedWriter(test);
        bw.write(jta.getText());
        bw.close();
      }catch(Exception e){
        System.out.println(e.toString());
      }
    }catch(Exception e2){
      System.out.println(e2.toString());
    }
  }

  protected void setUpPrintStream(){
    try{
      File sysFile = new File("system.txt");
      if(!sysFile.exists()) sysFile.createNewFile();
      outstream = new FileOutputStream(new File("system.txt"));
      inside = new PrintStream(outstream);
    }catch(FileNotFoundException fnfe){

    }catch(IOException ioe){

    }
  }

  protected void startRedirect(){
    setUpPrintStream();
    System.setOut(inside);
  }

  protected void endRedirect(){
    try{
      outstream.close();
      System.setOut(outside);
      systemRead();
    }catch(IOException ioe){

    }
  }

  protected void runAssembler(){
    String romLoc = ea.file.getText();
    String tString = aa.app.getText()+" "+aa.file.getText()+" "+aa.para.getText()+romLoc.substring(0,romLoc.lastIndexOf("\\")+1)+aa.out.getText()+" "+aa.inc.getText()+" "+aa.ver.getText();
    this.devProject.opTA.append(tString+"\n");
    this.devProject.exCommandCode(tString);
  }

  protected void runEmulator(){
    String tString = ea.app.getText()+" "+ea.para.getText()+" "+ea.file.getText();
    this.devProject.opTA.append(tString+"\n");
    this.devProject.exCommandCode(tString);
  }
}