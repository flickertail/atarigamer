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
import java.awt.geom.*;
import java.beans.*;
import java.io.*;
import java.lang.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

public class DevProject{
  public final int DEFAULT = 0;
  public final int ZOOMPLUS = 1;
  public final int ZOOMMINUS = 2;
  protected String title;
  public JDesktopPane jdp;
  public PaintTools pTools;
  public JInternalFrame dpOutput = new JInternalFrame();
//  public JPanel cPanel = new JPanel(new BorderLayout(2,0));
  public JTextField command = new JTextField();
  public JTextArea opTA = new JTextArea();
  public JScrollPane outputScroll = new JScrollPane(opTA);
  public ImageIcon colpal = new ImageIcon(DevProject.class.getResource("colors.jpg"));
  public DefaultTreeModel pfModel;
  public DefaultMutableTreeNode pfNode;
  public int pfInsert = 0;
  public int drawType = 0;
  protected int editors = 0;
  protected GrDesktop grd;
  ExtensionFilter pfFilterArray[] = new ExtensionFilter[1];
  ExtensionFilter spriteFilterArray[] = new ExtensionFilter[4];
  ExtensionFilter asmFilterArray[] = new ExtensionFilter[2];
  ExtensionFilter binFilterArray[] = new ExtensionFilter[1];
  ExtensionFilter exeFilterArray[] = new ExtensionFilter[1];
  protected Dimension asmSize = null;
  protected Point asmPoint = null;
  public JMenuBar comMenu = new JMenuBar();
  public JMenu comWin = new JMenu("Window");
  public JMenuItem comClear = new JMenuItem("Clear");
  public String [] comStrings = {"","","","","","","","","",""};
  int comInt = 0;
  int comPlace = 0;

  public JFileChooser files = new JFileChooser();
  public WebBrowser wb;
  protected JButton obutton = new JButton();
  protected ColorPalettes cp;
  protected CC2600 cc;
  private Toolkit tk = Toolkit.getDefaultToolkit();
  private Cursor[] cursors = new Cursor[3];
  protected String slHold = "";//necessary for loading in playfields

  public int cPalette = 0;

  public DevProject(){
    setFileFilters();
    setUpCursors();
  }
  public DevProject(GrDesktop grd){
    setFileFilters();
    setUpCursors();
    this.grd = grd;
    createDP();
    this.grd.contentPane.add(jdp);
    outputFrameSetup(0);
    wb = new WebBrowser(this);
    pTools = new PaintTools(this);
    cp = new ColorPalettes(this);
    cc = new CC2600(this);
  }

  public void setProjectTitle(String title){
    this.title = title;
  }
  public void setFileFilters(){
    pfFilterArray[0] = new ExtensionFilter(".pfd", "Playfield files (*.pfd)");
    spriteFilterArray[0] = new ExtensionFilter(".jpg", "JPEG files (*.jpg)");
    spriteFilterArray[1] = new ExtensionFilter(".gif", "GIF files (*.gif)");
    spriteFilterArray[2] = new ExtensionFilter(".png", "PNG files (*.png)");
    spriteFilterArray[3] = new ExtensionFilter(".cgd", "CGD files (*.cgd)");
    asmFilterArray[1] = new ExtensionFilter(".txt", "Text files (*.txt)");
    asmFilterArray[0] = new ExtensionFilter(".asm", "ASM files (*.asm)");
    binFilterArray[0] = new ExtensionFilter(".bin", "ROM files (*.bin)");
    exeFilterArray[0] = new ExtensionFilter(".exe", "Executable files (*.exe)");
  }

  protected void loadASMFile(){
    try{
      File asmFile = fileChoose("Open ASM/Text As..", "Open", "Open ASM/Text", JFileChooser.FILES_ONLY, null, asmFilterArray);
      addNewASMEdit(asmFile);
    }catch(Exception e){
      System.out.println(e.toString());
    }
  }
  protected void loadASMFile(ASMEditor tEdit, String title){
    asmSize = tEdit.getSize();
    asmPoint = tEdit.getLocation();
    boolean newASM = false;
    try{
      File asmFile = new File(title);
      if(asmFile!=null){
//        newASM = addNewASMEdit(asmFile);
      }
      if(newASM){
        tEdit.setVisible(false);
        tEdit.dispose();
      }
    }catch(Exception e){
      System.out.println(e.toString());
    }
  }
  protected void loadASMFile(ASMEditor tEdit){
    boolean newASM = false;
    asmSize = tEdit.getSize();
    asmPoint = tEdit.getLocation();
    try{
      File asmFile = fileChoose("Open ASM/Text As..", "Open", "Open ASM/Text", JFileChooser.FILES_ONLY, null, asmFilterArray);
      if(asmFile!=null){
        newASM = addNewASMEdit(asmFile);
      }
      if(newASM){
//        System.out.println("newASM is true");
        tEdit.setVisible(false);
        tEdit.dispose();
      }
    }catch(Exception e){
      System.out.println(e.toString());
    }

  }
  protected boolean addNewASMEdit(File asmFile){
    boolean isLoading = true;
    if(asmFile == null) isLoading = false;
    String tString = " ";
    if(asmFile==null){
    }else{
      tString = asmFile.getAbsolutePath();
    }
    Component[] co = jdp.getComponents();
    int coi = co.length;
    Vector coivec = new Vector(1);
    for(int i=0;i<coi;i++){
      try{
        ASMEditor tae = (ASMEditor)co[i];
        coivec.add(tae);
      }catch(ClassCastException cee){

      }
    }
    int size = coivec.size();
    int scount = 0;
    int correct = 0;
    boolean found = true;
    for(int j=0;j<size;j++){
      if(found){
        if(!isLoading) tString = "untitled_" + j + ".asm";
        int ifound = 0;
        for (int i = 0; i < size; i++) {
          ASMEditor asmFrame = (ASMEditor) coivec.get(i);
          String tit = asmFrame.getTitle();
          if (tit.equals(tString)) ifound = ifound + 1;
           else correct = j;
        }
        if(ifound>0){
          found = true;
        }else{
          found = false;
        }
      }
    }
    if(size==0){
      tString = "untitled_0.asm";
    }else{
      if (found) {
        correct = size;
      }
      tString = "untitled_" + correct + ".asm";
    }
    boolean newASM = false;
    ASMEditor tEdit = new ASMEditor(this,tString);
    if(asmFile == null) {
      tEdit.addSelf();
      if(asmSize != null){
        tEdit.setSize(asmSize);
        tEdit.setLocation(asmPoint);
      }
      newASM = true;
    }else{
      tString = asmFile.getAbsolutePath();
      tEdit = new ASMEditor(this,tString);
      if(asmSize != null){
        tEdit.setSize(asmSize);
        tEdit.setLocation(asmPoint);
      }
      if(asmFile != null){
        try{
          String readString = "";
          FileReader in = new FileReader(asmFile);
          BufferedReader br = new BufferedReader(in);
          boolean reading = true;
          while(reading){
            try{
              String tempString = br.readLine();
              if(tempString != null){
                tEdit.editArea.append(tempString+"\n");
              }else{
                br.close();
                reading = false;
              }
            }catch(Exception eReading){
              System.out.println(eReading.toString());
            }
          }
        }catch(Exception e){
          System.out.println(e.toString());
        }
      }
      tEdit.addSelf();
      newASM = true;
    }
    asmSize = null;
    asmPoint = null;
    return newASM;
  }

  protected void addNewPlayfield(){
	  DevPF dvp = new DevPF(this, "Temp Name");
  }

  private void createDP(){
    jdp = new JDesktopPane();
  }

  public String getAtariHexColor(Point point){//, String tempHex){
    Color tempCol = new Color(0,0,0);
    String tempX = "F";
    String tempY = "E";
    String tempHex = "";
    if(point.x < 234){
      tempX = "E";
      if(point.x < 218){
        tempX = "D";
        if(point.x < 203){
          tempX = "C";
          if(point.x < 187){
            tempX = "B";
            if(point.x < 171){
              tempX = "A";
              if(point.x < 155){
                tempX = "9";
                if(point.x < 140){
                  tempX = "8";
                  if(point.x < 125){
                    tempX = "7";
                    if(point.x < 109){
                      tempX = "6";
                      if(point.x < 93){
                        tempX = "5";
                        if(point.x < 78){
                          tempX = "4";
                          if(point.x < 62){
                            tempX = "3";
                            if(point.x < 47){
                              tempX = "2";
                              if(point.x < 31){
                                tempX = "1";
                                if(point.x < 15){
                                  tempX = "0";
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    if(point.y < 109){
      tempY = "C";
      if(point.y < 94){
        tempY = "A";
        if(point.y < 79){
          tempY = "8";
          if(point.y < 63){
            tempY = "6";
            if(point.y < 47){
              tempY = "4";
              if(point.y < 32){
                tempY = "2";
                if(point.y < 16){
                  tempY = "0";
                }
              }
            }
          }
        }
      }
    }
    tempHex = tempX + tempY;
//    tempCol = (Color)colorHash.get(tempHex);
//    return tempCol;
    return tempHex;
  }
  protected void loadSprite(){
    try{
      File imageFile = fileChoose("Open Sprite As..", "Open", "Open Sprite", JFileChooser.FILES_ONLY, null, spriteFilterArray);
      SpriteEditor se = new SpriteEditor(this, imageFile);
      se.setTitle(imageFile.getAbsolutePath()+" @ "+se.getImageZoom()+"%"+" Width: "+se.getSpriteWidth()+" Height: "+se.getSpriteHeight());
      String atString = imageFile.getName();
      atString = atString.substring(0,atString.lastIndexOf("."));
      se.setGraphicName(atString);
      jdp.add(se);
    }catch(Exception e){
      System.out.println(e.toString());
    }
  }

  protected void newSprite(){
    Component[] co = jdp.getComponents();
    int coi = co.length;
    Vector coivec = new Vector(1);
    for(int i=0;i<coi;i++){
      try{
        SpriteEditor tpf = (SpriteEditor)co[i];
        coivec.add(tpf);
      }catch(ClassCastException cee){

      }
    }
    int size = coivec.size();
    int scount = 0;
    int correct = 0;
    boolean found = true;
    for(int j=0;j<size;j++){
      if(found){
        String tString = "untitled_" + j + ".spt";
        int ifound = 0;
        for (int i = 0; i < size; i++) {
          SpriteEditor pfFrame = (SpriteEditor) coivec.get(i);
          String tit = pfFrame.getTitle();
          if (tit.equals(tString)) ifound = ifound+1;
            else correct = j;
        }
        if(ifound>0){
          found = true;
        }else{
          found = false;
        }
      }
    }
    String tString = "";
    if(size==0){
      tString = "untitled_0.spt";
    }else{
      if (found) {
        correct = size;
      }
      tString = "untitled_" + correct + ".spt";
    }
    SpriteEditor se = new SpriteEditor(this);
    se.setTitle(tString+" @ "+se.getImageZoom()+"%"+" Width: "+se.getSpriteWidth()+" Height: "+se.getSpriteHeight());
    String atString = tString;
    atString = atString.substring(0,atString.lastIndexOf("."));
    se.setGraphicName(atString);
    jdp.add(se);
  }

  protected void loadPlayfield(){
    int fileLength = 0;
    try{
      File holdFile = fileChoose("Import Playfield File", "Select", "Select Playfield", JFileChooser.FILES_ONLY, null, pfFilterArray);
      String tString = holdFile.getName();
      String readString = "";
      FileReader in = new FileReader(holdFile);
      BufferedReader br = new BufferedReader(in);
      boolean reading = true;
      DevPF devpf = new DevPF(this,tString);
        while(reading){
          try{
            String tempString = br.readLine();
            if(tempString != null){
              processPFFileLine(tempString,devpf);
            }else{
              br.close();
              reading = false;
            }
          }catch(Exception e){
//            reading = false;
          }
        }
      devpf.directory = holdFile.getParent();
//      pfHash.put(devpf.getTitle(),devpf);
      }catch(Exception e){
    }
  }


  protected void processPFFileLine(String text, DevPF devpf){
    String process = "";
    String value = "";
    int testNumb = -1;
    boolean testIt = true;
    int testInt = 0;
    while(testIt){
      if(new String(""+text.charAt(testInt)).equals(":")){
        testInt = testInt + 1;
        testIt = false;
      }else{
        process = process + text.charAt(testInt);
        testInt = testInt + 1;
      }
    }
    testIt = true;
    while(testIt){
      try{
        value = value + text.charAt(testInt);
        testInt = testInt + 1;
      }catch(Exception e){
        testIt = false;
      }
    }
    if(process.equals("Title")) testNumb = 0;
    if(process.equals("Type")) testNumb = 1;
    if(process.equals("Scanlines")) testNumb = 2;
    if(process.equals("Repeats")) testNumb = 3;
    if(process.equals("Box")) testNumb = 4;
    if(process.equals("Erase")) testNumb = 5;
    if(process.equals("BKCOLU")) testNumb = 6;
    if(process.equals("PFCOLU")) testNumb = 7;
//    if(process.equals("Title")) testNumb = 8;
    float[] fValues = new float[4];
    String[] fString = new String[4];
    int k = 0;
    switch(testNumb){
      case 0:
//        tempTitle = value;
//        devpf.setTitle("Playfield: "+value);
        break;
      case 1:
        if(value.equals("asy")){
          devpf.reflected.setSelected(false);
          devpf.asymmetrical.setSelected(true);
        }
        if(value.equals("ref")){
          devpf.asymmetrical.setSelected(false);
          devpf.reflected.setSelected(true);
        }
        break;
      case 2:
        slHold = value;
        break;
      case 3:
        devpf.buildSLGroups(value,slHold);
        break;
      case 4:
        value=value+"\n";
        for(int i=0;i<4;i++){
          fString[i] = new String("");
//          fValues[i] = 0;
        }
        k = 0;
        for(int i=0;i<4;i++){
          boolean j = true;
          while(j){
              if(new String(""+value.charAt(k)).equals(",")){
                k = k+1;
                j = false;
              }else{
                if(new String(""+value.charAt(k)).equals("\n")){
                  j = false;
                }else{
                  fString[i] = fString[i] + value.charAt(k);
                  k = k+1;
                }
            }
          }
          fValues[i] = Float.parseFloat(fString[i]);
        }
        devpf.startPoint = new Point((int)fValues[0],(int)fValues[1]);
        devpf.currentPoint = new Point((int)fValues[0]+(int)fValues[2],(int)fValues[1]+(int)fValues[3]);
        devpf.drawType = 0;
        devpf.createShape();
        break;
      case 5:
        for(int i=0;i<4;i++){
          fString[i] = new String("");
        }
        k = 0;
        boolean l = true;
        for(int i=0;i<4;i++){
          boolean j = true;
          while(j){
            try{
              if(new String(""+value.charAt(k)).equals(",")){
                k = k+1;
                j = false;
              }else{
                fString[i] = fString[i] + value.charAt(k);
                k = k+1;
              }
            }catch(Exception e2){
              j = false;
            }
          }
          fValues[i] = Float.parseFloat(fString[i]);
        }
        devpf.startPoint = new Point((int)fValues[0],(int)fValues[1]);
        devpf.currentPoint = new Point((int)fValues[0]+(int)fValues[2],(int)fValues[1]+(int)fValues[3]);
        devpf.drawType = 0;
        devpf.toolPaint.isAlt = true;
        devpf.createShape();
        devpf.toolPaint.isAlt = false;
        break;
      case 6:
        devpf.bgHexColor = value;
        devpf.bgColor = this.cc.getVCSColor(value);
        break;
      case 7:
        devpf.defPFHexColor = value;
        devpf.pfDefColor = this.cc.getVCSColor(value);
        break;
      default:
        boolean sep = true;
        boolean left = true;
        int loc = 0;
        String sclnNumb = "";
        while(sep){
          if(new String(""+process.charAt(loc)).equals("_")){
            loc = loc + 1;
            if(new String(""+process.charAt(loc)).equals("r"))left=false;
            sep = false;
          }else{
            sclnNumb = sclnNumb + process.charAt(loc);
            loc = loc + 1;
          }
        }
        if(left){
          PrtScnLine tsl = (PrtScnLine)devpf.leftScnLine.get(sclnNumb);
          tsl.setButtonColor(value);
        }else{
          PrtScnLine tsl = (PrtScnLine)devpf.rightScnLine.get(sclnNumb);
          tsl.setButtonColor(value);
        }
        break;
    }
    devpf.clearToolPane(devpf.toolPaint);
  }

  public void outputFrameSetup(int userInt){
    opTA.setLineWrap(true);
    command.addKeyListener(new KeyListener(){
      public void keyReleased(KeyEvent e){
        int whichKey = e.getKeyCode();
        switch(whichKey){
          case 38://Up Arrow
            break;
          case 40://Down Arrow
            break;
          case 10://Enter/Return
            comStrings[comPlace] = command.getText();
            if(comPlace>=9){
              comPlace = 0;
            }else{
              comPlace = comPlace+1;
            }
            if(command.getText()!=null){
              opTA.append("\n"+command.getText());
              exCommandCode(command.getText());
//            command.setBackground(Color.WHITE);
//            command.setText("");
            }
            break;
          default:
            break;
        }
      }
      public void keyPressed(KeyEvent e){

      }
      public void keyTyped(KeyEvent e){

      }
    });
    outputScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    outputScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    dpOutput.getContentPane().setLayout(new BorderLayout());
    this.opTA.setBackground(Color.BLACK);
    this.opTA.setForeground(Color.GREEN);
    dpOutput.setJMenuBar(comMenu);
    comMenu.add(comWin);
    comWin.add(comClear);
    opTA.setEditable(false);
    comClear.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        opTA.setText("");
      }
    });
    JButton aaBut = new JButton("Assembler Prefs");
    JButton raBut = new JButton("Run Assembler");
    JButton eaBut = new JButton("Emulator Prefs");
    JButton reBut = new JButton("Run Emulator");
    JToolBar exToolbar = new JToolBar(JToolBar.HORIZONTAL);
    exToolbar.add(aaBut);
    aaBut.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        grd.aa.showMe();
      }
    });

    exToolbar.add(raBut);
    exToolbar.add(eaBut);
    eaBut.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        grd.ea.showMe();
      }
    });
    raBut.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        grd.runAssembler();
      }
    });

    reBut.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        grd.runEmulator();
      }
    });


    exToolbar.add(reBut);
    exToolbar.setFloatable(false);
    dpOutput.getContentPane().add("North",exToolbar);
    dpOutput.getContentPane().add("Center",outputScroll);
    dpOutput.getContentPane().add("South",command);
    dpOutput.setMaximizable(true);
    dpOutput.setClosable(false);
    dpOutput.setIconifiable(true);
    dpOutput.setResizable(true);
    dpOutput.setSize(grd.outPSize);
//    dpOutput.setSize(350,200);
    dpOutput.setFrameIcon(new ImageIcon(DevProject.class.getResource("ag_fi.gif")));
    obutton.setIcon(dpOutput.getFrameIcon());
//    obutton.setToolTipText("Command Window");
    obutton.setMargin(new Insets(0,0,0,0));
    obutton.setBorderPainted(false);
    obutton.setForeground(Color.BLACK);
    obutton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        try{
          dpOutput.setIcon(false);
        }catch(PropertyVetoException pve){};
        dpOutput.setVisible(true);
      }
    });
//    dpOutput.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
    dpOutput.addInternalFrameListener(new InternalFrameAdapter(){
      public void internalFrameIconified(InternalFrameEvent e){
        dpOutput.setVisible(false);
        obutton.setToolTipText(dpOutput.getTitle());
        grd.icontool.add(obutton,2);
      }
      public void internalFrameDeiconified(InternalFrameEvent e){
        grd.icontool.remove(obutton);
      }
    });
//    Dimension holdDem = jdp.getSize();
    dpOutput.setLocation(grd.outPLoc.width,grd.outPLoc.height);
    dpOutput.setTitle("Command Window");
    jdp.add(dpOutput);
    dpOutput.setVisible(true);
  }

  public Point getLeftP0(int pos){
    Point xPoint = new Point();
    switch(pos){
      case 1:
        xPoint.x = 8;
        xPoint.y = 15;
        break;
      case 2:
        xPoint.x = 16;
        xPoint.y = 23;
        break;
      case 3:
        xPoint.x = 24;
        xPoint.y = 31;
        break;
      default:
        xPoint.x = 0;
        xPoint.y = 7;
        break;
    }
    return xPoint;
  }
  public Point getLeftP1(int pos){
    Point xPoint = new Point();
    switch(pos){
      case 1:
        xPoint.x = 40;
        xPoint.y = 47;
        break;
      case 2:
        xPoint.x = 48;
        xPoint.y = 55;
        break;
      case 3:
        xPoint.x = 56;
        xPoint.y = 63;
        break;
      case 4:
        xPoint.x = 64;
        xPoint.y = 71;
        break;
      case 5:
        xPoint.x = 72;
        xPoint.y = 79;
        break;
      case 6:
        xPoint.x = 80;
        xPoint.y = 87;
        break;
      case 7:
        xPoint.x = 88;
        xPoint.y = 95;
        break;
      default:
        xPoint.x = 32;
        xPoint.y = 39;
        break;
    }
    return xPoint;
  }
  public Point getLeftP2(int pos){
    Point xPoint = new Point();
    switch(pos){
      case 1:
        xPoint.x = 104;
        xPoint.y = 111;
        break;
      case 2:
        xPoint.x = 112;
        xPoint.y = 119;
        break;
      case 3:
        xPoint.x = 120;
        xPoint.y = 127;
        break;
      case 4:
        xPoint.x = 128;
        xPoint.y = 135;
        break;
      case 5:
        xPoint.x = 136;
        xPoint.y = 143;
        break;
      case 6:
        xPoint.x = 144;
        xPoint.y = 151;
        break;
      case 7:
        xPoint.x = 152;
        xPoint.y = 159;
        break;
      default:
        xPoint.x = 96;
        xPoint.y = 103;
        break;
    }
    return xPoint;
  }

  public Point getRightP0(int pos){
    Point xPoint = new Point();
    switch(pos){
      case 1:
        xPoint.x = 168;
        xPoint.y = 175;
        break;
      case 2:
        xPoint.x = 176;
        xPoint.y = 183;
        break;
      case 3:
        xPoint.x = 184;
        xPoint.y = 191;
        break;
      default:
        xPoint.x = 160;
        xPoint.y = 167;
        break;
    }
    return xPoint;
  }
  public Point getRightP1(int pos){
    Point xPoint = new Point();
    switch(pos){
      case 1:
        xPoint.x = 200;
        xPoint.y = 207;
        break;
      case 2:
        xPoint.x = 208;
        xPoint.y = 215;
        break;
      case 3:
        xPoint.x = 216;
        xPoint.y = 223;
        break;
      case 4:
        xPoint.x = 224;
        xPoint.y = 231;
        break;
      case 5:
        xPoint.x = 232;
        xPoint.y = 239;
        break;
      case 6:
        xPoint.x = 240;
        xPoint.y = 247;
        break;
      case 7:
        xPoint.x = 248;
        xPoint.y = 255;
        break;
      default:
        xPoint.x = 192;
        xPoint.y = 199;
        break;
    }
    return xPoint;
  }
  public Point getRightP2(int pos){
    Point xPoint = new Point();
    switch(pos){
      case 1:
        xPoint.x = 264;
        xPoint.y = 271;
        break;
      case 2:
        xPoint.x = 272;
        xPoint.y = 279;
        break;
      case 3:
        xPoint.x = 280;
        xPoint.y = 287;
        break;
      case 4:
        xPoint.x = 288;
        xPoint.y = 295;
        break;
      case 5:
        xPoint.x = 296;
        xPoint.y = 303;
        break;
      case 6:
        xPoint.x = 304;
        xPoint.y = 311;
        break;
      case 7:
        xPoint.x = 312;
        xPoint.y = 319;
        break;
      default:
        xPoint.x = 256;
        xPoint.y = 263;
        break;
    }
    return xPoint;
  }

  public Point getRightRP0(int pos){
    Point xPoint = new Point();
    switch(pos){
      case 1:
        xPoint.x = 304;
        xPoint.y = 311;
        break;
      case 2:
        xPoint.x = 296;
        xPoint.y = 303;
        break;
      case 3:
        xPoint.x = 288;
        xPoint.y = 295;
        break;
      default:
        xPoint.x = 312;
        xPoint.y = 319;
        break;
    }
    return xPoint;
  }
  public Point getRightRP1(int pos){
    Point xPoint = new Point();
    switch(pos){
      case 1:
        xPoint.x = 272;
        xPoint.y = 279;
        break;
      case 2:
        xPoint.x = 264;
        xPoint.y = 271;
        break;
      case 3:
        xPoint.x = 256;
        xPoint.y = 263;
        break;
      case 4:
        xPoint.x = 248;
        xPoint.y = 255;
        break;
      case 5:
        xPoint.x = 240;
        xPoint.y = 247;
        break;
      case 6:
        xPoint.x = 232;
        xPoint.y = 239;
        break;
      case 7:
        xPoint.x = 224;
        xPoint.y = 231;
        break;
      default:
        xPoint.x = 280;
        xPoint.y = 287;
        break;
    }
    return xPoint;
  }
  public Point getRightRP2(int pos){
    Point xPoint = new Point();
    switch(pos){
      case 1:
        xPoint.x = 208;
        xPoint.y = 215;
        break;
      case 2:
        xPoint.x = 200;
        xPoint.y = 207;
        break;
      case 3:
        xPoint.x = 192;
        xPoint.y = 199;
        break;
      case 4:
        xPoint.x = 184;
        xPoint.y = 191;
        break;
      case 5:
        xPoint.x = 176;
        xPoint.y = 183;
        break;
      case 6:
        xPoint.x = 168;
        xPoint.y = 175;
        break;
      case 7:
        xPoint.x = 160;
        xPoint.y = 167;
        break;
      default:
        xPoint.x = 216;
        xPoint.y = 223;
        break;
    }
    return xPoint;
  }

  public Point getPFPoint(int register, int pos, boolean left, boolean rev){
    Point xPoint = new Point();
    if(left){
      switch(register){
        case 1:
          xPoint = this.getLeftP1(pos);
          break;
        case 2:
          xPoint = this.getLeftP2(pos);
          break;
        default:
          xPoint = this.getLeftP0(pos);
          break;
      }
    }else{
      if(rev){//left false --> means right side of screen
        switch(register){
          case 1:
            xPoint = this.getRightRP1(pos);
            break;
          case 2:
            xPoint = this.getRightRP2(pos);
            break;
          default:
            xPoint = this.getRightRP0(pos);
            break;
        }
      }else{
        //left false, rev false --> means right side reflected
        switch(register){
          case 1:
            xPoint = this.getRightP1(pos);
            break;
          case 2:
            xPoint = this.getRightP2(pos);
            break;
          default:
            xPoint = this.getRightP0(pos);
            break;
        }
      }
    }
    return xPoint;
  }
  public String calHexValue(String bitValue){
    int intValue = 0;
    String retValue = "";
    if(new String(""+bitValue.charAt(3)).equals("1")){
      intValue = intValue + 1;
    }
    if(new String(""+bitValue.charAt(2)).equals("1")){
      intValue = intValue + 2;
    }
    if(new String(""+bitValue.charAt(1)).equals("1")){
      intValue = intValue + 4;
    }
    if(new String(""+bitValue.charAt(0)).equals("1")){
      intValue = intValue + 8;
    }
    switch(intValue){
      case 0:
        retValue = "0";
        break;
      case 1:
        retValue = "1";
        break;
      case 2:
        retValue = "2";
        break;
      case 3:
        retValue = "3";
        break;
      case 4:
        retValue = "4";
        break;
      case 5:
        retValue = "5";
        break;
      case 6:
        retValue = "6";
        break;
      case 7:
        retValue = "7";
        break;
      case 8:
        retValue = "8";
        break;
      case 9:
        retValue = "9";
        break;
      case 10:
        retValue = "A";
        break;
      case 11:
        retValue = "B";
        break;
      case 12:
        retValue = "C";
        break;
      case 13:
        retValue = "D";
        break;
      case 14:
        retValue = "E";
        break;
      case 15:
        retValue = "F";
        break;
      default:
        retValue = "blah";
        break;
    }
    return retValue;
  }
  public String getHexValue(String bitValue){
    String test1 = "";
    String test2 = "";
    String hex1 = "";
    String hex2 = "";
    String hexValue = "";
    for(int i=0; i<4;i++){
      test1 = test1 + bitValue.charAt(i);
    }
    for(int i=4; i<8;i++){
      test1 = test1 + bitValue.charAt(i);
    }
    hex1 = calHexValue(test1);
    hex2 = calHexValue(test2);
    hexValue = hex1 + hex2;
    return hexValue;
  }

  public File fileChoose(String dialogTitle, String approveButtonText, String approveButtonTooltip, int type, String location, ExtensionFilter[] ef){
    files.setDialogTitle(dialogTitle);
    files.setApproveButtonText(approveButtonText);
    files.setApproveButtonToolTipText(approveButtonTooltip);
    files.setFileSelectionMode(type);
    files.rescanCurrentDirectory();
    if(location != null) files.setSelectedFile(new File(location));
    int efLength = ef.length;
    for(int i=0;i<2;i++){
      files.removeChoosableFileFilter(asmFilterArray[i]);
    }
    for(int i=0;i<4;i++){
      files.removeChoosableFileFilter(spriteFilterArray[i]);
    }
    files.removeChoosableFileFilter(pfFilterArray[0]);
    files.removeChoosableFileFilter(binFilterArray[0]);
    files.removeChoosableFileFilter(exeFilterArray[0]);
    for(int i=0;i<efLength;i++){
      files.addChoosableFileFilter(ef[i]);
    }
    files.setFileFilter(ef[0]);
    int result = files.showDialog(grd, null);
    return (result == files.APPROVE_OPTION) ? files.getSelectedFile() : null;
  }

  protected void savePFFile(DevPF devpf){
    File checkFile = new File("");
    if(devpf.directory == null){
      checkFile = this.fileChoose("Save Playfield As..","Save As","Save Playfield",JFileChooser.FILES_ONLY,null,pfFilterArray);
      if(checkFile != null){
        devpf.setTitle(checkFile.getName());
        devpf.directory = checkFile.getPath();
      }
    }
    JTextArea jta = new JTextArea();
    jta.append("Title:"+devpf.getTitle()+"\n");
    String tempType = "normal";
//    DevProject devPrj = (DevProject)this.prjHash.get(devprj);
//    DevPF devpf = (DevPF)devPrj.pfHash.get(fileName);
    if(devpf.reflected.isSelected())tempType = "ref";
    if(devpf.asymmetrical.isSelected())tempType = "asy";
    jta.append("Type:"+tempType+"\n");
    jta.append("BKCOLU:"+devpf.bgHexColor+"\n");
    jta.append("PFCOLU:"+devpf.defPFHexColor+"\n");
    jta.append("Scanlines:"+devpf.lines_ints+"\n");
    jta.append("Repeats:"+devpf.r_ints+"\n");
    jta.append("History:\n");
    int his = devpf.stNames.size();
    if(his >0){
      for(int i=0;i<his;i++){
        String tString = (String)devpf.stNames.get(i);
        Integer tempInt = (Integer)devpf.shapeTypeTable.get(tString);
        int typeInt = tempInt.intValue();
        switch(typeInt){
          case 0:
            Rectangle2D.Float dRec = (Rectangle2D.Float)devpf.shapeTable.get(tString);
            jta.append("Box:"+dRec.x+","+dRec.y+","+dRec.width+","+dRec.height+"\n");
            break;
          case 5:
            Rectangle2D.Float eRec = (Rectangle2D.Float)devpf.shapeTable.get(tString);
            jta.append("Erase:"+eRec.x+","+eRec.y+","+eRec.width+","+eRec.height+"\n");
            break;
          default:
            break;
        }
      }
      jta.append("Colors:\n");
      int scnln = devpf.leftScnLine.size();
      for(int i=0;i<scnln;i++){
        String tColor1 = "";
        String tColor2 = "";
        PrtScnLine tScnL = (PrtScnLine)devpf.leftScnLine.get(""+i);
        PrtScnLine tScnR = (PrtScnLine)devpf.rightScnLine.get(""+i);
        if(!tScnL.isDefColor()){
          tColor1 = "" + i + "_l:" + tScnL.pfColorHex + "\n";
          jta.append(tColor1);
        }
        if(!tScnR.isDefColor()){
          tColor2 = "" + i + "_r:"+tScnR.pfColorHex+"\n";
          jta.append(tColor2);
        }
      }
      System.out.println("Write out made it this far\n\n");
      writeTextAreaFile(jta,devpf.directory,devpf.getTitle());
    }else{
      this.grd.statusBar.setText("There is no work to save.");
    }
  }
  
  protected void writeTextAreaFile(JTextArea jta, String directory, String fileName){
    File newPF = new File("");
//      File prDrChk = new File(directory);
//      if(!prDrChk.exists()) projSelect();
    File newDir = new File(directory);
    System.out.println(directory);
    try{
      if(!newDir.exists()) newDir.mkdir();
    }catch(Exception edir1){
      System.out.println("Directory is null");
    }
    try{
      newPF = new File(newDir,fileName);
      if(!newPF.exists()){
        newPF.createNewFile();
      }
      FileWriter test = new FileWriter(newPF.getPath(),false);
      BufferedWriter bw = new BufferedWriter(test);
      bw.write(jta.getText());
      bw.close();
    }catch(Exception blah){
      System.out.println("hello");
    }
  }
  public void exCommandCode(String commands){
//    try{
    try{
      Runtime rt = Runtime.getRuntime();
      Process prc = rt.exec(commands);
      ProcesCollector pc = new ProcesCollector(prc,grd);
      pc.start();
    }catch(IOException ie){

    }catch(IndexOutOfBoundsException ioobe){

    }
//    }catch(IOException e){}
  }

  public void setUpCursors(){
    Point pt = new Point(17,17);
    cursors[0] = Cursor.getDefaultCursor();
    cursors[1] = tk.createCustomCursor(tk.createImage(DevProject.class.getResource("zoomcur.gif")),pt,"zoomplus");
    cursors[2] = tk.createCustomCursor(tk.createImage(DevProject.class.getResource("zoommin.gif")),pt,"zoomminus");
  }
  public Cursor getCursor(int choice){
    return cursors[choice];
  }
}
