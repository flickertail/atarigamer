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
import javax.swing.border.*;
//import javax.swing.ImageIcon;
import javax.swing.event.*;
import java.net.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import java.beans.*;

//public class WebBrowser extends JInternalFrame implements HyperlinkListener{
public class WebBrowser extends JFrame implements HyperlinkListener{
  public DevProject dp;
  public JDesktopPane jdp;
  protected WebBrowser wb;
  JEditorPane jep = new JEditorPane();
  JPanel cp = new JPanel(new CardLayout(0,0));
  JScrollPane jsp = new JScrollPane(jep,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  Container container;
  JMenuBar mb = new JMenuBar();
  JMenu jm = new JMenu("Files");
  JMenu et = new JMenu("Utilities");
  JMenuItem copy = new JMenuItem("Copy");
  JMenu home = new JMenu("Set Home");
  JMenuItem thome = new JMenuItem("Set Current Page As Homepage");
  JMenuItem rhome = new JMenuItem("Set Software Page As Home Homepage");
  JMenuItem op = new JMenuItem("Open");
  JMenuItem tt = new JMenuItem("Tutorials");
  JMenuItem cl = new JMenuItem("Close");
  JMenuItem pt = new JMenuItem("Print");
  JLabel status = new JLabel("Status");
  JPanel icon = new JPanel(new BorderLayout(2,2));
  JPanel tooltop = new JPanel(new BorderLayout(0,0));
  JPanel textbot = new JPanel(new BorderLayout(2,1));
  JLabel workLab = new JLabel();
  JSplitPane southPan = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
  JPanel barPan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
  ProgressPanel pp;
  JButton toolBut1 = new JButton("Back");
  JButton toolBut2 = new JButton("Forward");
  JButton toolBut3 = new JButton("Stop");
  JButton toolBut4 = new JButton("Reload");
  JButton toolBut5 = new JButton("Home");
  JButton toolBut6 = new JButton("Print");
  JLabel urlLab = new JLabel(" Address:  ");
  JTextField urlText = new JTextField();
  JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
  JButton goBut = new JButton();
  ProgressMonitor pm;
  URL loadURL;
  Stack backStack = new Stack();
  Stack nextStack = new Stack();
  URL homeURL;
  String homeString = "";
  boolean isReloading = false;
  protected InputStream is;
  protected BufferedReader bfr;
  protected ProgressMonitorInputStream pmi;
  boolean isLoadingPage = false;
  int fileL = 0;
  boolean isLoaded = false;
  int lc = -1;
  URL nurl;
  protected JButton obutton = new JButton();

  public WebBrowser(DevProject dp) {
    this.dp = dp;
    this.jdp = this.dp.jdp;
    setUp();
    windowEventSetup();
    jepSetup();
    containerSetup();
    this.addMouseMotionListener(new MouseMotionListener(){
      public void mouseMoved(MouseEvent e){

      }
      public void mouseDragged(MouseEvent e){
        southPan.setDividerLocation(wb.getSize().width-110);
      }
    });
    this.repaint();
  }
  public void jepSetup(){
    jep.setEditable(false);
    jep.addHyperlinkListener(this);
    cp.add(jsp,"htmlpage");
  }
  public void containerSetup(){
    System.out.print("Hi There Aaron!\n");
    icon.add("East", workLab);
    icon.add("Center", tooltop);
    tooltop.add("North", textbot);
    tooltop.add("South", toolbar);
    textbot.add("West", urlLab);
    textbot.add("Center", urlText);
    urlText.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        processURLTextInput();
      }
    });
    textbot.add("East",goBut);
    goBut.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        processURLTextInput();
      }
    });
    textbot.add("South",Box.createVerticalStrut(2));
    toolbar.setFloatable(false);
//    ImageIcon undoIconB = new ImageIcon(WebBrowser.class.getResource("asm_undo.gif"));
//    ImageIcon redoIconB = new ImageIcon(WebBrowser.class.getResource("asm_redo.gif"));
//    ImageIcon homeIconB = new ImageIcon(WebBrowser.class.getResource("home.gif"));
//    ImageIcon stopIconB = new ImageIcon(WebBrowser.class.getResource("stop.gif"));
//    ImageIcon reloadIconB = new ImageIcon(WebBrowser.class.getResource("reload.gif"));
//    ImageIcon printIconB = new ImageIcon(WebBrowser.class.getResource("asm_print.gif"));

    toolbar.add(toolBut1);
//    toolBut1.setIcon(undoIconB);
    toolbar.add(toolBut2);
//    toolBut2.setIcon(redoIconB);
    toolbar.add(toolBut3);
//    toolBut3.setIcon(stopIconB);
    toolbar.add(toolBut4);
//    toolBut4.setIcon(reloadIconB);
    toolbar.add(toolBut5);
//    toolBut5.setIcon(homeIconB);
    toolbar.add(toolBut6);
//    toolBut6.setIcon(printIconB);

    toolbar.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    workLab.setBorder(new EmptyBorder(1,1,1,1));
    workLab.setIcon(new ImageIcon(WebBrowser.class.getResource("pong.jpg")));
    toolBut1.setMargin(new Insets(0,0,0,0));
    toolBut1.setBorderPainted(false);
    toolBut1.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        loadBackStack();
      }
    });
    toolBut2.setMargin(new Insets(0,0,0,0));
    toolBut2.setBorderPainted(false);
    toolBut2.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        loadNextStack();
      }
    });
    toolBut3.setMargin(new Insets(0,0,0,0));
    toolBut3.setBorderPainted(false);
    toolBut3.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        stopLoad();
      }
    });
    toolBut4.setMargin(new Insets(0,0,0,0));
    toolBut4.setBorderPainted(false);
    toolBut4.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        isReloading = true;
        processURLTextInput();
        isReloading = false;
      }
    });
    toolBut5.setMargin(new Insets(0,0,0,0));
    toolBut5.setBorderPainted(false);
    toolBut5.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        loadHome();
      }
    });

    toolBut6.setMargin(new Insets(0,0,0,0));
    toolBut6.setBorderPainted(false);
    goBut.setBorder(new EmptyBorder(1,1,1,1));
//    goBut.setIcon(new ImageIcon(WebBrowser.class.getResource("gobut.gif")));
    status.setForeground(Color.BLACK);
    mb.add(jm);
    jm.add(op);
    jm.add(tt);
    jm.addSeparator();
    jm.add(pt);
    jm.addSeparator();
    jm.addSeparator();
    jm.add(cl);
    cl.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        hideSelf();
      }
    });
    mb.add(et);
    et.add(copy);
    et.addSeparator();
    et.add(home);
    home.add(thome);
    home.add(rhome);
    thome.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        setCH();
      }
    });
    rhome.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        resetHome();
      }
    });
    copy.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        jep.copy();
      }
    });
    this.setJMenuBar(mb);
    container = this.getContentPane();
    container.setLayout(new BorderLayout());
    container.add("North", icon);
    container.add("Center",cp);
    southPan.setBorder(new EmptyBorder(0,0,0,0));
    southPan.setDividerSize(0);
    southPan.add(status);
    pp  = new ProgressPanel();
    southPan.add(pp);

    container.add("South",southPan);
    this.jep.setContentType("text/html");
    this.homeURL = loadHomePref();
    this.setURL(null);
  }

  protected void setURL(String surl){
//    nurl = loadURL;
    if(surl==null){
      this.loadURL = homeURL;
      lc = 0;
      loadPage();
    }else{
      try{
        if(!isReloading) backStack.push(loadURL);
        this.loadURL = new URL(urlText.getText());
        lc = 1;
        loadPage();
      }catch(MalformedURLException me){
        JOptionPane.showMessageDialog(this,"Page Not Found","Page Not Found Error",JOptionPane.OK_OPTION);
      }
    }
  }

  protected void loadPage(){
	try{
	    isLoaded = false;
	    fileL = setUpBar();
	    isLoadingPage = true;
	    DThread dthread = new DThread(this);
	    dthread.start();
	    status.setText("Status");
	    //pp.setAmount(0);
	}
	catch(NullPointerException npe)
	{
		System.out.println(npe.toString());
	}
  }
  public int setUpBar(){
    int fileLength = 0;
    try{
      fileLength = loadURL.openConnection().getContentLength();
    }catch(IOException e){}
    return fileLength;
  }

  public void hyperlinkUpdate(HyperlinkEvent event) {
    if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
      int choice = loadChoice(event.getURL().toExternalForm());
      switch(choice){
        case 0:
          download(event.getURL());
          break;

        case 1:
          loadPDF(event.getURL());
          break;

        default:
          if(loadURL!=null)backStack.push(loadURL);
          loadURL = event.getURL();
          status.setText("Status  Opening page - "+event.getURL().toExternalForm());
          lc = 2;
          loadPage();
          break;
      }
    }
    if(event.getEventType() == HyperlinkEvent.EventType.ENTERED){
      status.setText("Status  "+event.getURL().toExternalForm());
    }
    if(event.getEventType() == HyperlinkEvent.EventType.EXITED){
      status.setText("Status");
    }

  }


  public void setUp(){
    this.wb = this;
//    jdp.add(this);
    this.setSize(640,480);
//    this.setIconifiable(true);
 //   this.setMaximizable(true);
    this.setResizable(true);
 //   this.setClosable(true);
 //   this.setFrameIcon(new ImageIcon(WebBrowser.class.getResource("html_fi.gif")));
//    obutton.setIcon(this.getFrameIcon());
    obutton.setMargin(new Insets(0,0,0,0));
    obutton.setBorderPainted(false);
    obutton.setForeground(Color.BLACK);
//    obutton.addActionListener(new ActionListener(){
//      public void actionPerformed(ActionEvent e){
//        try{
//          wb.setIcon(false);
 //       }catch(PropertyVetoException pve){};
//        wb.setVisible(true);
//      }
//    });
    wb.setVisible(true);
  }

  public void windowEventSetup(){
    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
/*
    this.addInternalFrameListener(new InternalFrameListener(){
      public void internalFrameDeactivated(InternalFrameEvent e){
      }
      public void internalFrameActivated(InternalFrameEvent e){
        southPan.setDividerLocation(wb.getSize().width-110);
      }
      public void internalFrameDeiconified(InternalFrameEvent e){
        southPan.setDividerLocation(wb.getSize().width-110);
        dp.grd.icontool.remove(obutton);
      }
      public void internalFrameIconified(InternalFrameEvent e){
        obutton.setToolTipText(wb.getTitle());
        wb.setVisible(false);
        dp.grd.icontool.add(obutton,2);
      }
      public void internalFrameClosed(InternalFrameEvent e){
      }
      public void internalFrameClosing(InternalFrameEvent e){
        hideSelf();
      }
      public void internalFrameOpened(InternalFrameEvent e){
        southPan.setDividerLocation(wb.getSize().width-110);
      }
    });
    */
  }

  public void loadNextStack(){
    if(!nextStack.empty()){
      nurl = loadURL;
      loadURL = (URL) nextStack.pop();
      status.setText("Status Opening page - "+loadURL.toExternalForm());
      lc = 3;
      loadPage();
    }else{
      JOptionPane.showMessageDialog(this,"No forward page history available.","Cannot Find Page",JOptionPane.OK_OPTION);
    }
  }
  public void loadBackStack(){
    if (!backStack.empty()) {
      nurl = loadURL;
      loadURL = (URL) backStack.pop();
      status.setText("Status Opening page - " + loadURL.toExternalForm());
      lc = 4;
      loadPage();
    }else{
      JOptionPane.showMessageDialog(this,"No page history available.","Cannot Find Page",JOptionPane.OK_OPTION);
    }
  }
  public void showMe(){
    this.setVisible(true);
  }
  public void hideSelf(){
    this.setVisible(false);
  }
  public void processURLTextInput(){
    String tString = urlText.getText();
    boolean begins = tString.startsWith("http:");
    boolean begins2 = tString.startsWith("file:");
    if(begins|begins2){

    }else{
      urlText.setText("http://" + tString);
    }
    int choice = loadChoice(urlText.getText());
    switch(choice){
      case 0:
        try{
          URL durl = new URL(urlText.getText());
          download(durl);
        }catch(MalformedURLException mue){

        }
        break;
      case 1:
        loadPDF(urlText.getText());
        break;
      default:
        wb.setURL(urlText.getText());
        break;
    }
  }
  public void loadPDF(String str){
    JOptionPane.showMessageDialog(this,"PDF viewer not yet implemented, sorry.","PDF Files",JOptionPane.OK_OPTION);
    urlText.setText(loadURL.toExternalForm());
  }
  public void loadPDF(URL pdfURL){
    JOptionPane.showMessageDialog(this,"PDF viewer not yet implemented, sorry.","PDF Files",JOptionPane.OK_OPTION);
    urlText.setText(loadURL.toExternalForm());
  }
  public void download(URL url){
    JOptionPane.showMessageDialog(this,"Download tools not yet implemented, sorry.","Download Files",JOptionPane.OK_OPTION);
    urlText.setText(loadURL.toExternalForm());
  }
  public void loadHome(){
      nurl = loadURL;
      loadURL = homeURL;
      status.setText("Status Opening page - " + loadURL.toExternalForm());
      lc = 5;
      loadPage();
  }
  public int loadChoice(String lc){
    String tString = lc.substring(lc.lastIndexOf(".")+1);
    tString.toLowerCase();
    int choice = -1;
    if(tString.equals(new String("exe"))|tString.equals(new String("zip"))) choice = 0;
    if(tString.equals(new String("pdf"))) choice = 1;
    return choice;
  }
  public void setCH(){
    homeURL = loadURL;
    writeHome();
  }
  public void resetHome(){
    homeURL = WebBrowser.class.getResource("html/index.html");
    writeHome();
  }
  public void writeHome(){
    try{
      FileWriter fw = new FileWriter(new File(homeString),false);
      BufferedWriter bw = new BufferedWriter(fw);
      String tString = homeURL.toExternalForm();
      tString = tString.replaceAll("%20"," ");
      bw.write(tString);
      bw.close();
    }catch(FileNotFoundException e){
      System.out.println(""+e.toString()+"\n"+homeString);
    }catch(IOException ioe){
      System.out.println(""+ioe.toString()+"\n"+homeString);
    }

  }

  public void stopLoad(){
    isLoadingPage = false;
    //pp.setAmount(0);
    repaint();
  }

  public URL loadHomePref(){
    URL retURL = WebBrowser.class.getResource("html/index.html");
    String tString=" ";
    try{
      URL datURL = WebBrowser.class.getResource("html/home.txt");
      homeString = datURL.getFile();
      homeString = homeString.replaceAll("%20"," ");
      File homeFile = new File(homeString);
      FileReader fr = new FileReader(homeFile);
      BufferedReader br = new BufferedReader(fr);
      tString = br.readLine();
      System.out.println(tString);
      br.close();
    }catch(FileNotFoundException e){
    }catch(IOException ioe){
    }catch(NullPointerException npe)
    {
    	System.out.println(npe.toString());
    }
    try{
      if(tString != "html/index.html"){
        if (tString != " "){
          retURL = new URL(tString);
        }
      }
    }catch(MalformedURLException mue){}
    return retURL;
  }
/*  
   public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new WebBrowser();
  }
  */
}