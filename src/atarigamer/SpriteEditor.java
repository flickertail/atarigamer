package atarigamer;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.beans.*;
import java.awt.image.*;
import java.util.*;
//import org.j3d.util.ImageUtils;

/**
 * <p>Title: Atari Gamer</p>
 * <p>Description: Atari Video Game Console Development Environment</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Atari Aaron</p>
 * @author Aaron.Bergstrom@ndsu.nodak.edu
 * @version 1.0
 */

public class SpriteEditor extends JInternalFrame{
  protected ImageIcon dImage;
  protected DevProject dp;
  protected SpriteEditor se;
  protected File file;
  protected String byteLabel = "Sprite";
  protected boolean isHex = false;
  private JMenuBar mb = new JMenuBar();
  private JMenu files = new JMenu("Files");
  private JMenu edit = new JMenu("Edit");
  private JMenu display = new JMenu("Display");
  private JMenu export = new JMenu("Export");
  private JMenuItem bytesOut = new JMenuItem("Export Bytes - Top to Bottom");
  private JMenuItem bytesOutR = new JMenuItem("Export Bytes - Bottom to Top");
  private JMenuItem register = new JMenuItem("Register");
  private JMenuItem saveas = new JMenuItem("Save as...");
  private JMenuItem close = new JMenuItem("Close");
  private JMenu rbgSep = new JMenu("Seperate");
  private JMenuItem redSep = new JMenuItem("Red");
  private JMenuItem blueSep = new JMenuItem("Blue");
  private JMenuItem greenSep = new JMenuItem("Green");
  private JMenuItem prefs = new JMenuItem("Preferences");
  protected JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
  protected JScrollPane scp;
  protected JScrollPane hscp;
  protected JScrollPane escp;
  protected JScrollPane dscp;
  protected JTabbedPane tabbedPane = new JTabbedPane();
  protected JTabbedPane disPane = new JTabbedPane();
  private GraphicPanel panel;
  private SpriteDisplay sPanel;
  private JLabel info = new JLabel(" ");
  private HistoryPanel hp;
  private ExtrasPanel ep;
  protected JButton obutton = new JButton();
  private int zoom = 100;
  private Toolkit tk = Toolkit.getDefaultToolkit();
  private Image bi;
  private int iw;
  private int ih;
  private int drawType;
  private boolean isAlt = false;
  private MouseListener ml;
  private MouseMotionListener mml;
  private KeyListener kl;
  private JPanel regSplit;
  private JList available;
  private JList selected;
  private JScrollPane spLeft;
  private JScrollPane spRight;
  private Hashtable regSelected = new Hashtable();
  private Vector selVec = new Vector();
  private Hashtable regAvailable = new Hashtable();
  private Vector avaVec = new Vector();
  private JDialog regDialog;

  public SpriteEditor(DevProject dp, File file) {
    this.se = this;
    this.dp = dp;
    this.file = file;
    setRegisterPanel();
    setMenu();
    setGraphic();
    setFrameBehavior();
  }

  public SpriteEditor(DevProject dp){
    this.se = this;
    this.dp = dp;
    setRegisterPanel();
    setMenu();
    setEditorStructure();
    setFrameBehavior();
  }

  protected void setMenu(){
    this.setJMenuBar(mb);
    mb.add(files);
    files.add(saveas);
//    grabP.addActionListener(new ActionListener(){
//      public void actionPerformed(ActionEvent e){
//        panel.storePixels();
//      }
//    });
    files.addSeparator();
    files.add(bytesOut);
    files.add(bytesOutR);
    files.addSeparator();
    files.add(register);
    register.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        getEditors();
      }
    });
    files.addSeparator();
//    files.add(rbgSep);
//    files.addSeparator();
    files.add(prefs);
    files.addSeparator();
    files.addSeparator();
    files.add(close);
    close.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        setVisible(false);
        dp.jdp.remove(se);
        dispose();
      }
    });
    mb.add(edit);
    mb.add(display);
//    rbgSep.add(redSep);
//    rbgSep.add(blueSep);
//    rbgSep.add(greenSep);
  }

  protected void setEditorStructure(){
    JTextField wfield = new JTextField("8");
    JTextField hfield = new JTextField("8");
    JTextField zfield = new JTextField(""+zoom);
    JLabel wtext = new JLabel(" Width (Pixels): ");
    JLabel htext = new JLabel(" Height (Pixels): ");
    JLabel ztext = new JLabel(" Zoom (%):");
    wtext.setForeground(Color.BLACK);
    htext.setForeground(Color.BLACK);
    JPanel outer = new JPanel(new BorderLayout(0,0));
    JPanel inner = new JPanel(new GridLayout(3,2,0,0));
    outer.add("West", Box.createVerticalStrut(30));
    outer.add("North", Box.createHorizontalStrut(30));
    outer.add("Center", inner);
    inner.add(wtext);
    inner.add(wfield);
    inner.add(htext);
    inner.add(hfield);
    inner.add(ztext);
    inner.add(zfield);
    JOptionPane.showMessageDialog(dp.grd,outer,"Set Image Size",JOptionPane.OK_OPTION);
    zoom = Integer.parseInt(zfield.getText());
    try{
      iw = Integer.parseInt(wfield.getText());
      ih = Integer.parseInt(hfield.getText());
      BufferedImage buff = new BufferedImage(iw,ih,BufferedImage.TYPE_INT_RGB);
      bi = buff.getScaledInstance(iw,ih,Image.SCALE_DEFAULT);
      double dw = (double)iw;
      double dh = (double)ih;
      double ds = (double)zoom;
      dw = dw*ds/100d;
      dh = dh*ds/100d;
      Image anImage = bi.getScaledInstance((int)Math.round(dw),(int)Math.round(dh),Image.SCALE_DEFAULT);
      dImage = new ImageIcon(anImage);
      setup();
    }catch(Exception e){
      JOptionPane.showMessageDialog(dp.grd,"Error, couldn't process input"+e.toString(),"Operation Cancelled",JOptionPane.OK_OPTION);
    }
  }

  protected void setGraphic(){
    if(file != null){

      JTextField zfield = new JTextField(""+zoom);
      JLabel ztext = new JLabel(" Zoom (%):");
      ztext.setForeground(Color.BLACK);
      JPanel outer = new JPanel(new BorderLayout(0,0));
      JPanel inner = new JPanel(new GridLayout(1,2,0,0));
      outer.add("West", Box.createVerticalStrut(30));
      outer.add("North", Box.createHorizontalStrut(30));
      outer.add("Center", inner);
      inner.add(ztext);
      inner.add(zfield);
      JOptionPane.showMessageDialog(dp.grd,outer,"Set Image Size",JOptionPane.OK_OPTION);
      zoom = Integer.parseInt(zfield.getText());
      bi = tk.createImage(file.getAbsolutePath());
      dImage = new ImageIcon(bi);
      iw = dImage.getIconWidth();
      ih = dImage.getIconHeight();
      double dw = (double)iw;
      double dh = (double)ih;
      double ds = (double)zoom;
      dw = dw*ds/100d;
      dh = dh*ds/100d;
      Image anImage = bi.getScaledInstance((int)Math.round(dw),(int)Math.round(dh),Image.SCALE_DEFAULT);
      dImage = new ImageIcon(anImage);
      setup();
    }else{
      JOptionPane.showMessageDialog(dp.grd,"Error!", "Operation Cancelled", JOptionPane.OK_OPTION);
    }
  }

  protected void setup(){
      panel = new GraphicPanel();
      sPanel = new SpriteDisplay();
      hp = new HistoryPanel();
      ep = new ExtrasPanel();
      int height = dImage.getIconHeight();
      int width = dImage.getIconWidth();
      panel.setHBox(width);
      panel.setVBox(height);
      width = 610;
      height = height+100;
      Dimension dimension = new Dimension(width,height);
      this.setSize(dimension);
      this.setLocation(100,100);
      this.setBackground(Color.lightGray);

      scp = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      scp.setBorder(new EmptyBorder(0,0,0,0));
      scp.setName("Editable Canvas");

      hscp = new JScrollPane(hp,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      hscp.setBorder(new EmptyBorder(0,0,0,0));
      hscp.setName("History");

      escp = new JScrollPane(ep,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      escp.setBorder(new EmptyBorder(0,0,0,0));
      escp.setName("Extras");

      dscp = new JScrollPane(sPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      dscp.setBorder(new EmptyBorder(0,0,0,0));
      dscp.setName("Sprite Display");

      info.setForeground(Color.BLACK);

      disPane.add(scp);
      disPane.add(escp);
      disPane.setBorder(new EmptyBorder(0,0,0,0));

      tabbedPane.add(sPanel);
      tabbedPane.add(hscp);
      tabbedPane.setBorder(new EmptyBorder(0,0,0,0));

      splitPane.setOneTouchExpandable(true);
      splitPane.add(JSplitPane.LEFT,disPane);
      splitPane.add(JSplitPane.RIGHT,tabbedPane);
      splitPane.setDividerLocation(this.getSize().width/2);
      splitPane.setDividerSize(6);
      this.getContentPane().setLayout(new BorderLayout());
      this.getContentPane().add(splitPane,BorderLayout.CENTER);
      this.getContentPane().add("South",info);
      this.setIconifiable(true);
      this.setClosable(true);
      this.setMaximizable(true);
      this.setResizable(true);
      this.setFrameIcon(new ImageIcon(SpriteEditor.class.getResource("sprite_fi.gif")));
      obutton.setIcon(this.getFrameIcon());
//      obutton.setToolTipText("Srpite Editor/Converter");
      obutton.setMargin(new Insets(0,0,0,0));
      obutton.setBorderPainted(false);
      obutton.setForeground(Color.BLACK);
      obutton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          try{
            se.setIcon(false);
          }catch(PropertyVetoException pve){};
          se.setVisible(true);
        }
      });
      createListeners();
      panel.setMyImage(dImage);
//      panel.dImage = dImage;
      panel.setMasterImage(bi);
      this.setVisible(true);
  }
  protected void setFrameBehavior(){
 /*   this.addMouseMotionListener(new MouseMotionListener(){
      public void mouseMoved(MouseEvent e){

      }
      public void mouseDragged(MouseEvent e){
          se.splitPane.setDividerLocation(se.getSize().width/2);
      }
    });*/

    this.addInternalFrameListener(new InternalFrameListener(){
      public void internalFrameDeactivated(InternalFrameEvent e){

      }
      public void internalFrameActivated(InternalFrameEvent e){

      }
      public void internalFrameDeiconified(InternalFrameEvent e){
        dp.grd.icontool.remove(obutton);

      }
      public void internalFrameIconified(InternalFrameEvent e){
        obutton.setToolTipText(se.getTitle());
        se.setVisible(false);
        dp.grd.icontool.add(obutton,2);
      }
      public void internalFrameClosed(InternalFrameEvent e){

      }
      public void internalFrameClosing(InternalFrameEvent e){

      }
      public void internalFrameOpened(InternalFrameEvent e){

      }
    });
  }

  public int getImageZoom(){
    return zoom;
  }

  public void setDrawType(int drawType){
    this.drawType = drawType;
  }

  public void changeCursor(MouseEvent evt){
    switch(drawType){
      case 2:
        if(evt.isShiftDown()){
          this.setCursor(dp.getCursor(2));
        }else{
          this.setCursor(dp.getCursor(1));
        }
        break;
      default:
        break;
    }
  }

  public void changeDefCursor(){
    this.setCursor(dp.getCursor(0));
  }

  public void activateAction(MouseEvent evt){
    switch(this.drawType){
      case 2:
        double tempZoom = (double)zoom;
        if(evt.isShiftDown()){
          tempZoom = tempZoom/2;
          tempZoom = tempZoom/100;
          if(tempZoom<1){
            tempZoom = 1.0;
          }else{
            tempZoom = Math.round(tempZoom);
          }
          tempZoom = tempZoom*100;
        }else{
          tempZoom = tempZoom *2;
          tempZoom = tempZoom/100;
          if(tempZoom > 32){
            tempZoom = 32.0;
          }else{
            tempZoom = Math.round(tempZoom);
          }
          tempZoom = tempZoom * 100;
        }
        zoom = (int)tempZoom;
        double dw = (double)iw;
        double dh = (double)ih;
        dw = dw*tempZoom/100d;
        dh = dh*tempZoom/100d;
        Image anImage = bi.getScaledInstance((int)Math.round(dw),(int)Math.round(dh),Image.SCALE_DEFAULT);
        dImage = new ImageIcon(anImage);
        panel.setMyImage(dImage);
        this.repaint();
        String tString = this.getTitle();
        tString = tString.substring(0,tString.lastIndexOf("@")+1);
        tString = tString +" "+zoom+"%"+" Width: "+iw+" Height: "+ih;
        this.setTitle(tString);
        break;
      default:
        break;
    }
  }

  public int getSpriteWidth(){
    return iw;
  }

  public int getSpriteHeight(){
    return ih;
  }

  public void createListeners(){
    ml = new MouseListener(){
      public void mouseEntered(MouseEvent evt){
        changeCursor(evt);
      }
      public void mouseExited(MouseEvent evt){
        changeDefCursor();
      }
      public void mousePressed(MouseEvent evt){

      }
      public void mouseReleased(MouseEvent evt){
        activateAction(evt);
      }

      public void mouseClicked(MouseEvent evt){
      }
    };

    mml = new MouseMotionListener(){
      public void mouseDragged(MouseEvent evt){

      }
      public void mouseMoved(MouseEvent evt){
        changeCursor(evt);
      }
    };

    kl = new KeyListener(){
      public void keyTyped(KeyEvent evt){
      }
      public void keyPressed(KeyEvent evt){
      }
      public void keyReleased(KeyEvent evt){
      }
    };

    panel.addMouseListener(ml);
    panel.addMouseMotionListener(mml);
    panel.addKeyListener(kl);
  }

  public void getEditors(){
    Component[] co = dp.jdp.getComponents();
    int coLength = co.length;
    regAvailable.clear();
    for(int i=0;i<coLength;i++){
      try{
        ASMEditor tedit = (ASMEditor) co[i];
        ASMEditor selEdit = (ASMEditor)regSelected.get(tedit.getTitle());
        if(selEdit==null) regAvailable.put(tedit.getTitle(),tedit);
      }catch(ClassCastException cce){}
    }
    int avaInt = regAvailable.size();
    Collection col = regAvailable.values();
    Object[] anArray = col.toArray();
    avaVec.clear();
    for(int i=0;i<avaInt;i++){
      ASMEditor tedit = (ASMEditor)anArray[i];
      avaVec.add(tedit.getTitle());
    }
    int selInt = regSelected.size();
    col = regSelected.values();
    anArray = col.toArray();
    selVec.clear();
    for(int i=0;i<selInt;i++){
      ASMEditor tedit = (ASMEditor)anArray[i];
      selVec.add(tedit.getTitle());
    }
    String tString = this.getTitle();
    tString = tString.substring(0,tString.indexOf(" @"));
    tString = "Register source files with sprite:  "+tString;
    regDialog.setTitle(tString);
    regDialog.setVisible(true);
  }

  public void moveToSelected(){
//    available = new JList(avaVec);
    Object[] objects = available.getSelectedValues();
    int vInts = objects.length;
    for(int i=0;i<vInts;i++){
      ASMEditor tedit = (ASMEditor)regAvailable.get((String)objects[i]);
      avaVec.remove((String)objects[i]);
      regAvailable.remove((String)objects[i]);
      regSelected.put(tedit.getTitle(),tedit);
      selVec.add(tedit.getTitle());
    }
    available.clearSelection();
    panel.registerASMEditors(regSelected);
    regDialog.repaint();
  }

  public void moveToAvailable(){
//    selected = new JList(selVec);
    Object[] objects = selected.getSelectedValues();
    int vInts = objects.length;
    for(int i=0;i<vInts;i++){
      ASMEditor tedit = (ASMEditor)regSelected.get((String)objects[i]);
      selVec.remove((String)objects[i]);
      regSelected.remove((String)objects[i]);
      regAvailable.put(tedit.getTitle(),tedit);
      avaVec.add(tedit.getTitle());
    }
    selected.clearSelection();
    regDialog.repaint();
  }

  public void setRegisterPanel(){
    JButton toAva = new JButton("    <    ");
    JButton toSel = new JButton("    >    ");
    toAva.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        moveToAvailable();
      }
    });
    toSel.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        moveToSelected();
      }
    });
    toAva.setForeground(Color.BLACK);
    toSel.setForeground(Color.BLACK);
    toAva.setMargin(new Insets(0,0,0,0));
    toSel.setMargin(new Insets(0,0,0,0));
    JPanel bottom = new JPanel();
    bottom.add(toAva);
    bottom.add(toSel);
    Point rdpt = new Point((int)this.dp.grd.getSize().getWidth(),(int)this.dp.grd.getSize().getHeight());
    rdpt.x = rdpt.x/2;
    rdpt.y = rdpt.y/2;
    rdpt.x = rdpt.x-260;
    rdpt.y = rdpt.y-145;
    regDialog = new JDialog(this.dp.grd);
    regDialog.getContentPane().setLayout(new BorderLayout(0,0));
    regDialog.setSize(520,290);
    regDialog.setResizable(false);
    regDialog.setLocation(rdpt);
    JPanel topPanel = new JPanel(new GridLayout(1,2,0,0));
    JLabel lab01 = new JLabel("Available");
    lab01.setForeground(Color.BLACK);
    JLabel lab02 = new JLabel("Selected");
    lab02.setForeground(Color.BLACK);
    topPanel.add(lab01);
    topPanel.add(lab02);
    regSplit = new JPanel(new GridLayout(1,2,2,0));
    regDialog.getContentPane().add("North",topPanel);
    regDialog.getContentPane().add("Center",regSplit);
    regDialog.getContentPane().add("South",bottom);
    available = new JList(avaVec);
    selected = new JList(selVec);
    available.setFixedCellHeight(20);
    available.setFixedCellWidth(246);
    selected.setFixedCellHeight(20);
    selected.setFixedCellWidth(246);
    spLeft = new JScrollPane(available,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    spRight = new JScrollPane(selected,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    regSplit.add(spLeft);
    regSplit.add(spRight);
  }
  public void setGraphicName(String gname){
    panel.setGraphicName(gname);
  }
}