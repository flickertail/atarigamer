package atarigamer;

import javax.swing.*;
import java.awt.print.*;
import javax.swing.undo.*;
import javax.swing.tree.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.*;
import java.beans.*;
import java.util.*;
/**
 * <p>Title: Atari Gamer</p>
 * <p>Description: Atari Video Game Console Development Environment</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Atari Aaron</p>
 * @author Aaron.Bergstrom@ndsu.nodak.edu
 * @version 1.0
 */

public class ASMEditor extends JInternalFrame{
  protected File thisFile;
  protected UndoManager um = new UndoManager();
  private JMenuBar mb = new JMenuBar();
  protected ASMEditor asmThis;
  private JMenu file = new JMenu("File");
  private JMenuItem print =  new JMenuItem("Print");
//  protected JMenuItem printSet = new JMenuItem("Print Setup");
  private JMenuItem save = new JMenuItem("Save");
  private JMenuItem open = new JMenuItem("Open");
  protected JMenu edit = new JMenu("Edit");
  protected JMenuItem exit = new JMenuItem("Close");
  //protected UndoAction undo = new UndoAction();
  //protected RedoAction redo = new RedoAction();
//  protected PrintEditArea pea = new PrintEditArea();
  protected JMenuItem undo = new JMenuItem("Undo");
  protected JMenuItem redo = new JMenuItem("Redo");
  protected JMenuItem cut = new JMenuItem("Cut");
  protected JMenuItem copy = new JMenuItem("Copy");
  protected JMenuItem paste = new JMenuItem("Paste");
  protected JMenuItem prefs = new JMenuItem("Set Undo/Redo #");
  protected JButton openB = new JButton();
  protected JButton saveB = new JButton();
  protected JButton cutB = new JButton();
  protected JButton copyB = new JButton();
  protected JButton pasteB = new JButton();
  protected JButton undoB = new JButton();
  protected JButton redoB = new JButton();
  protected JButton printB = new JButton();
  protected JTextArea editArea = new JTextArea();
  protected PrintEditArea printArea = new PrintEditArea();
  protected JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
  protected JScrollPane jscp = new JScrollPane(editArea);
  protected DevProject dp;
  protected JToolBar texttool = new JToolBar(JToolBar.HORIZONTAL);
  private JLabel status = new JLabel(" ");
  protected JButton obutton = new JButton();
  private Hashtable graphicsPanels = new Hashtable();
  private JPopupMenu rmenu = new JPopupMenu("Right Menu");
  private JMenuItem  cancel = new JMenuItem("Cancel");
  private JMenu graphics = new JMenu("Graphics");
  private JMenuItem insert = new JMenuItem("Insert Graphic Header");
  private JMenuItem remGraphic = new JMenuItem("Remove Graphic");
  private JMenuItem iDown = new JMenuItem("Update Graphic Data T/B");
  private JMenuItem iUp = new JMenuItem("Update Graphic Data B/T");
  private JDialog gWindow;
  private Toolkit tk = Toolkit.getDefaultToolkit();
  private Vector gVec = new Vector();
  private JList gList = new JList(gVec);
  private static int INSERTNAME = 0;
  private int cga = -1;

  public ASMEditor(DevProject dp, String title) {
    this.dp = dp;
    this.asmThis = this;
    this.setTitle(title);
    setUp();
    setUndoRedo();
    showEditor();
    printArea.setDP(this);
    setGraphicsWindow();
  }

  public void setUndoRedo(){
    um.setLimit(30);
    editArea.getDocument().addUndoableEditListener(new UndoableEditListener(){
      public void undoableEditHappened(UndoableEditEvent e){
        um.addEdit(e.getEdit());
      }
    });

    redo.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        try {
          um.redo();
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(asmThis, "No more Redo's Possible", "Redo Alert", JOptionPane.OK_OPTION);
        }
      }
    });

    undo.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        try {
          um.undo();
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(asmThis, "No more Undo's Possible", "Undo Alert", JOptionPane.OK_OPTION);
        }
      }
    });


  }

  public void setUpButtons(){

    ImageIcon openIconB = new ImageIcon(atarigamer.ASMEditor.class.getResource("asm_open.gif"));
    ImageIcon saveIconB = new ImageIcon(atarigamer.ASMEditor.class.getResource("asm_save.gif"));
    ImageIcon cutIconB = new ImageIcon(atarigamer.ASMEditor.class.getResource("asm_cut.gif"));
    ImageIcon copyIconB = new ImageIcon(atarigamer.ASMEditor.class.getResource("asm_copy.gif"));
    ImageIcon pasteIconB = new ImageIcon(atarigamer.ASMEditor.class.getResource("asm_paste.gif"));
    ImageIcon undoIconB = new ImageIcon(atarigamer.ASMEditor.class.getResource("asm_undo.gif"));
    ImageIcon redoIconB = new ImageIcon(atarigamer.ASMEditor.class.getResource("asm_redo.gif"));
    ImageIcon printIconB = new ImageIcon(atarigamer.ASMEditor.class.getResource("asm_print.gif"));
    openB.setSize(30,30);
    saveB.setSize(30,30);
    cutB.setSize(30,30);
    copyB.setSize(30,30);
    pasteB.setSize(30,30);
    undoB.setSize(30,30);
    redoB.setSize(30,30);
    printB.setSize(30,30);
    openB.setToolTipText("Open File");
    saveB.setToolTipText("Save File");
    cutB.setToolTipText("Cut Text");
    copyB.setToolTipText("Copy Text");
    pasteB.setToolTipText("Paste Text");
    undoB.setToolTipText("Undo");
    redoB.setToolTipText("Redo");
    printB.setToolTipText("Print");
    openB.setMargin(new Insets(0,0,0,0));
    saveB.setMargin(new Insets(0,0,0,0));
    cutB.setMargin(new Insets(0,0,0,0));
    copyB.setMargin(new Insets(0,0,0,0));
    pasteB.setMargin(new Insets(0,0,0,0));
    undoB.setMargin(new Insets(0,0,0,0));
    redoB.setMargin(new Insets(0,0,0,0));
    printB.setMargin(new Insets(0,0,0,0));
    openB.setIcon(openIconB);
    saveB.setIcon(saveIconB);
    cutB.setIcon(cutIconB);
    copyB.setIcon(copyIconB);
    pasteB.setIcon(pasteIconB);
    undoB.setIcon(undoIconB);
    redoB.setIcon(redoIconB);
    printB.setIcon(printIconB);
    texttool.setFloatable(false);
    texttool.add(openB);
    texttool.add(saveB);
    texttool.add(cutB);
    texttool.add(copyB);
    texttool.add(pasteB);
    texttool.add(undoB);
    texttool.add(redoB);
    texttool.add(printB);
    openB.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        dp.loadASMFile(asmThis);
      }
    });

    saveB.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        saveFile(false, asmThis.editArea);
      }
    });
    cutB.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        editArea.cut();
      }
    });
    copyB.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        editArea.copy();
      }
    });
    pasteB.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        editArea.paste();
      }
    });
    undoB.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        try{
          um.undo();
        }catch(Exception ue){
          JOptionPane.showMessageDialog(asmThis, "No more Undo's Possible", "Undo Alert", JOptionPane.OK_OPTION);
        }
      }
    });
    redoB.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        try{
          um.redo();
        }catch(Exception ue){
          JOptionPane.showMessageDialog(asmThis, "No more Redo's Possible", "Redo Alert", JOptionPane.OK_OPTION);
        }
      }
    });
    printB.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        printArea.startPrintProcess();
        //pea.startPrintProcess(editArea.getText());
      }
    });
  }


  public void setUp(){
    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    this.addInternalFrameListener(new InternalFrameListener(){
      public void internalFrameDeactivated(InternalFrameEvent e){
      }
      public void internalFrameActivated(InternalFrameEvent e){

      }
      public void internalFrameDeiconified(InternalFrameEvent e){
        dp.grd.icontool.remove(obutton);
      }
      public void internalFrameIconified(InternalFrameEvent e){
        obutton.setToolTipText(asmThis.getTitle());
        asmThis.setVisible(false);
        dp.grd.icontool.add(obutton,2);
      }
      public void internalFrameClosed(InternalFrameEvent e){
      }
      public void internalFrameClosing(InternalFrameEvent e){
        boolean shouldClose = saveCheck();
        extractSelf(shouldClose);
      }
      public void internalFrameOpened(InternalFrameEvent e){

      }
    });

    setUpButtons();
    jsp.add(jscp,1);
    jsp.setDividerLocation(0);
    jsp.setDividerSize(0);
    jsp.setBorder(new EmptyBorder(0,0,0,0));
    jscp.setHorizontalScrollBarPolicy(jscp.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jscp.setVerticalScrollBarPolicy(jscp.VERTICAL_SCROLLBAR_AS_NEEDED);
    this.getContentPane().setLayout(new BorderLayout());
    this.setJMenuBar(mb);
    mb.add(file);
    mb.add(edit);
    edit.add(undo);
    edit.add(redo);
    edit.addSeparator();
    edit.add(cut);
    edit.add(copy);
    edit.add(paste);
    edit.addSeparator();
    edit.add(prefs);
    file.add(open);
    file.add(save);
    file.addSeparator();
    file.add(print);
    file.addSeparator();
    file.addSeparator();
    file.add(exit);
    save.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        saveFile(false, asmThis.editArea);
      }
    });
    exit.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        asmThis.doDefaultCloseAction();
      }
    });
    prefs.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        String undoNum = JOptionPane.showInputDialog(dp.grd,"Set the number of possible Undo/Redo actions:",""+um.getLimit());
        um.setLimit(Integer.parseInt(undoNum));
      }
    });
    open.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        dp.loadASMFile(asmThis);
      }
    });

    cut.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        editArea.cut();
      }
    });

    copy.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        editArea.copy();
      }
    });

    paste.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        editArea.paste();
      }
    });

    print.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
 //       pea.startPrintProcess(editArea.getText());
//        try{
//          editArea.printJob.getPrinterJob().printDialog();
//        }catch(Exception ep){
//          System.out.println(ep.toString());
//        }
        printArea.startPrintProcess();
//        System.out.println(editArea.getDocument().toString());
//        editArea.print(editArea.getGraphics());
      }
    });

    rmenu.setInvoker(this);
    rmenu.addSeparator();
    rmenu.addSeparator();
    rmenu.add(cancel);
    rmenu.addSeparator();
    rmenu.addSeparator();
//    rmenu.add(graphics);
    rmenu.add(insert);
    insert.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        cga = INSERTNAME;
        setGraphicVector();
        gWindow.setVisible(true);
      }
    });
    rmenu.addSeparator();
    rmenu.add(remGraphic);
    rmenu.addSeparator();
    rmenu.add(iDown);
    rmenu.add(iUp);

    editArea.addMouseListener(new MouseListener(){
      public void mouseReleased(MouseEvent evt){
        if(evt.getButton() != MouseEvent.BUTTON1){
          rmenu.show(asmThis, evt.getX(),evt.getY()+70);
        }
      }
      public void mousePressed(MouseEvent evt){

      }
      public void mouseClicked(MouseEvent evt){

      }

      public void mouseExited(MouseEvent evt){

      }
      public void mouseEntered(MouseEvent evt){

      }
    });

    editArea.getCaret().addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e){
        try{
          Point cursorLoc = editArea.modelToView(editArea.getSelectionStart()).getLocation();
          cursorLoc.x = (cursorLoc.x+7)/7;
          cursorLoc.y = (cursorLoc.y+17)/17;
//          status.setText(""+editArea.modelToView(editArea.getSelectionStart()));
          status.setText("Line: "+cursorLoc.y+" Column: "+cursorLoc.x);
        }catch(Exception bde){
        }
      }
    });

    this.getContentPane().add(jsp,BorderLayout.CENTER);
    this.getContentPane().add(status,BorderLayout.SOUTH);
    this.getContentPane().add(texttool,BorderLayout.NORTH);
    this.setBackground(Color.lightGray);
//    this.status.setText(""+this.getDefaultCloseOperation());
  }
  public void showEditor(){
    Dimension tdim = new Dimension(400,300);
    this.setSize(tdim);
    this.setVisible(true);
    this.setResizable(true);
    this.setIconifiable(true);
    this.setClosable(true);
    this.setFrameIcon(new ImageIcon(ASMEditor.class.getResource("asmedit_fi.gif")));
    obutton.setIcon(this.getFrameIcon());
//    obutton.setToolTipText("ASM Editor");
    obutton.setMargin(new Insets(0,0,0,0));
    obutton.setBorderPainted(false);
    obutton.setForeground(Color.BLACK);
    obutton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        try{
          asmThis.setIcon(false);
        }catch(PropertyVetoException pve){};
        asmThis.setVisible(true);
      }
    });
//    this.setIcon(true);
    this.setMaximizable(true);
  }
  protected void extractSelf(boolean shouldClose){
//    System.out.println("Extract is called");
    if(shouldClose){
      this.setVisible(false);
      dp.jdp.remove(this);
//      this.dispose();
    }
  }
  protected void addSelf(){
    dp.jdp.add(this,0);
  }

  protected void saveFile(boolean isClosing, JTextArea saveArea){
    thisFile = dp.fileChoose("Save ASM/Text As..", "Save", "Save ASM/Text", JFileChooser.FILES_ONLY, null, dp.asmFilterArray);
    if(thisFile!=null){
      String getname = thisFile.getAbsolutePath();
      this.setTitle(getname);
      um.discardAllEdits();
      dp.grd.writeTextAreaFile(saveArea,getname);
      if(!isClosing) dp.loadASMFile(asmThis, getname);
    }else{
      JOptionPane.showMessageDialog(dp.grd, "Your save was cancelled", "Save Cancelled", JOptionPane.OK_OPTION);
    }
  }

  protected boolean saveCheck(){
    boolean sc = um.canUndoOrRedo();
    boolean shouldClose = false;
    if(sc){
     int askOption = JOptionPane.showConfirmDialog(dp.grd,"This file has been changed. Would you\nlike to save before closing?", "Save File?",JOptionPane.YES_NO_CANCEL_OPTION);
     switch(askOption){
       case JOptionPane.YES_OPTION:
         saveFile(true,asmThis.editArea);
         shouldClose = true;
         break;
       case JOptionPane.NO_OPTION:
         shouldClose = true;
         break;
       default:
         break;
     }
    }else{
      shouldClose = true;
    }
    return shouldClose;
  }

  public void addGraphicPanel(GraphicPanel gp){
    graphicsPanels.put(gp.getGraphicName(),gp);
  }

  public void removeGraphicPanel(GraphicPanel gp){
    graphicsPanels.remove(gp.getGraphicName());
  }

  public void setGraphicsWindow(){
    gWindow = new JDialog(this.dp.grd, "Graphics Panel");
    gWindow.setSize(250,300);
    Point pt = new Point((int)tk.getScreenSize().getWidth(),(int)tk.getScreenSize().getHeight());
    pt.x = pt.x/2-125;
    pt.y = pt.y/2-150;
    gWindow.setLocation(pt);
    gWindow.getContentPane().setLayout(new BorderLayout(0,0));
    gWindow.getContentPane().add("Center",gList);
//    gList.addListSelectionListener(new ListSelectionListener(){
//      public void valueChanged(ListSelectionEvent evt){

//      }
//    });
    gList.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent evt){
        System.out.println("mouseclick cool");
        if(evt.getClickCount()>1) conductGraphicAction();
      }
    });
    setGraphicVector();
  }

  public void setGraphicVector(){
    Collection col = graphicsPanels.values();
    Object[] obj = col.toArray();
    int gLength = obj.length;
    gVec.clear();
    for(int i=0;i<gLength;i++){
      GraphicPanel gp = (GraphicPanel)obj[i];
      gVec.add(gp.getGraphicName());
    }
  }

  public void conductGraphicAction(){
    switch(cga){
      case 0:
        System.out.println("activated");
        Object[] object = gList.getSelectedValues();
        String tString = (String)object[0];
        int caretLoc = editArea.getCaretPosition();
        String tString1 = editArea.getText();
        String tString2 = tString1.substring(0,caretLoc);
        String tString3 = tString1.substring(caretLoc,tString1.length());
        String tString5 = tString2 + ";--- " + tString + " --- start ---;\n";
        String tString6 = ";--- " + tString + " --- end ---;\n" + tString3;
        tString1 = tString5 + tString6;
        editArea.setText(tString1);
        editArea.setCaretPosition(caretLoc);
        gWindow.setVisible(false);
        cga = -1;
        break;
      default:
        break;
    }
  }
}