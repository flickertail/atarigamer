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
import java.awt.geom.*;

import javax.swing.event.*;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;

import java.util.*;
import java.io.*;

public class DevPF extends JInternalFrame{

  protected DevProject dp;
  private JMenuBar jMenuBar = new JMenuBar();
  private JMenu win = new JMenu("Files");
  private JMenu options = new JMenu("Options");
  private JMenuItem savePF = new JMenuItem("Save");
  private JMenuItem saveAsPF = new JMenuItem("Save As: *.pfd");
  private JMenuItem exitPF = new JMenuItem("Exit");
  private JMenuItem atts = new JMenuItem("Set Playfield Attributes");
  private JMenuItem bsl = new JMenuItem("Build Scan Line Groups");
  private JMenu qExport = new JMenu("Export");
  private JMenuItem rawDump = new JMenuItem("Raw PF Data --> Output Window");
  private JMenuItem asmExport = new JMenuItem("PF Data --> ASM Editor");
  private JMenuItem chDump = new JMenuItem("Processed PF Data --> Output Window");
  private JMenuItem pfSetup = new JMenuItem("Reverse Organized PF Data --> Output Window");
  private JMenuItem pfDefSelect = new JMenuItem("Set Default Playfield Color");
  private JMenuItem bgSelect = new JMenuItem("Set Background Color");
  private JMenuItem pfCBs = new JMenuItem("Checkbox Interface");
  private ImageIcon ii;
  private DevPF insidePF;
  private int kernalCount1 = 0;
  public int drawType = 0;
  public JCheckBox reflected = new JCheckBox("Reflected",false);
  public JCheckBox asymmetrical = new JCheckBox("Asymmetrical",false);
  public JPanel bgPanel = new JPanel(new BorderLayout(0,0));
  public JLabel bgLabel = new JLabel();
  public JLabel pfLabel = new JLabel();
  public JLabel acLabel = new JLabel();
  public Color bgColor = Color.black;
  public Color pfDefColor = Color.white;
  public Color acColor = Color.white;
  public String genPFHexColor = "0E";
  public String bgHexColor = "00";
  public String defPFHexColor = "0E";
  public String acHexColor = "0E";
  public JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
  public JSplitPane leftPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
  public JSplitPane rightPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
  private JTextField slRepeat = new JTextField("4");
  private JTextField slLines = new JTextField("192");
  private JLabel slOutput = new JLabel("");
  public JPanel cbMenu = new JPanel(new GridLayout(1,4));
  public JButton clearAll = new JButton("Delete Complete History");
  public JButton updatePF = new JButton("Update Playfield");
  public JPanel cbPanel = new JPanel(new BorderLayout());
  public JSplitPane cbSP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
  public GridBagLayout lcbl = new GridBagLayout();
  public GridBagLayout rcbl = new GridBagLayout();
  public GridBagConstraints lcbc = new GridBagConstraints();
  public GridBagConstraints rcbc = new GridBagConstraints();
  public JPanel cbLPan = new JPanel(lcbl);
  public JPanel cbRPan = new JPanel(rcbl);
  public JScrollPane cbLeft = new JScrollPane();
  public JScrollPane cbRight = new JScrollPane();
  protected int remainder = 0;
  protected int lines_ints = 0;
  protected int group_int = 0;
  protected int r_ints = 0;
  public  int allGroups = 0;
  public Point startPoint = new Point();
  public Point currentPoint = new Point();
  private int count = 0;
  protected String directory = null;

  public DefaultMutableTreeNode numNode = new DefaultMutableTreeNode("Group");
  public DefaultTreeModel numMod = new DefaultTreeModel(numNode);
  public JTree numTree = new JTree(numMod);
  public JLayeredPane twoPane = new JLayeredPane();
  public JScrollPane numPane = new JScrollPane(numTree);

  public DefaultMutableTreeNode shapeNode = new DefaultMutableTreeNode("History");
  public DefaultTreeModel shapeMod = new DefaultTreeModel(shapeNode);
  public JTree shapeTree = new JTree(shapeMod);
  public JScrollPane shapePane = new JScrollPane(shapeTree);

  public PaintField leftPaint = new PaintField(this);
  public ToolPaintPane toolPaint = new ToolPaintPane(this);
//  public JPanel rightPaint = new JPanel();
  public JPanel sizePanel = new JPanel(new BorderLayout(0,0));
  public JPanel attsPan = new JPanel();
  public JPanel slgSettings = new JPanel();
  public Hashtable leftScnLine = new Hashtable();
  public Hashtable rightScnLine = new Hashtable();
  public Hashtable shapeTable = new Hashtable();
  public Hashtable shapeTypeTable = new Hashtable();
  public Hashtable shapeTableNames = new Hashtable();
  public Vector stNames = new Vector(1);
  protected int boxNumb = 0;
  protected int eraseBoxNumb = 0;
  protected Vector leftPFData = new Vector(1);
  protected Vector rightPFData = new Vector(1);
  protected DevPF tDevpf;
  protected String shapeSelection;
  protected JPopupMenu shapePopup;
  protected String[] shapeSelections;
  protected int nameSize = 0;
  protected Vector tempVector;
  protected int erasePixelNumb = 0;
  protected int drawPixelNumb = 0;

  public DevPF(DevProject dp, String title) {
    this.dp = dp;
    tDevpf = this;
    this.setTitle(title);
    drawType = dp.drawType;
    setup();//Setting up Playfield Window layout
    setSGL();
    setCBPanel();
    setShapeTreeListeners();
  }

  public DevPF(DevProject dp) {
    this.dp = dp;
    tDevpf = this;
//    this.setTitle("Playfield: "+title);
//    this.setTitle("unknown.pfd");
    drawType = dp.drawType;
    setup();//Setting up Playfield Window layout
    setSGL();
    setCBPanel();
  }
  private void setup(){
    insidePF = this;
    this.getContentPane().setLayout(new BorderLayout(0,0));
    setSplitPane();
    setBGLabel();
    setDefPFLabel();
    setAcLabel();
    this.getContentPane().add(sizePanel);
    Dimension dpd = new Dimension(526,240);//406(412,225);
    this.setSize(dpd);
    dp.jdp.add(this);
    this.setClosable(true);
    this.setIconifiable(true);
    this.setMaximizable(false);
    this.setResizable(false);
//    this.setBackground(bgColor);
//    Desktop dt = dp.getDT();
    ii = new ImageIcon(dp.grd.image1.getImage());
    this.setFrameIcon(ii);
    jMenuBar.add(win);
    jMenuBar.add(options);
    options.add(atts);
    atts.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        setPFatts();
      }
    });
    options.add(bsl);
    bsl.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        buildSLGSet();
      }
    });
    win.add(savePF);
    savePF.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        dp.savePFFile(tDevpf);
      }
    });
    win.add(saveAsPF);
    saveAsPF.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        File checkFile = dp.fileChoose("Save Playfield As..","Save","Save Playfield",JFileChooser.FILES_ONLY,tDevpf.directory,dp.pfFilterArray);//pfdFilter);
        if(checkFile != null){
          tDevpf.setTitle(checkFile.getName());
          tDevpf.directory = checkFile.getParent();
          try{
            if(!checkFile.exists()) checkFile.createNewFile();
            dp.savePFFile(tDevpf);
          }catch(IOException ioe){
            JOptionPane.showMessageDialog(dp.pTools,"Input/Output Error - Unable to complete operation.", "I/O Error",JOptionPane.OK_OPTION);
          }
        }
      }
    });
    win.addSeparator();
    win.add(qExport);
    win.addSeparator();
    win.addSeparator();
    win.add(exitPF);
    exitPF.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
//        extractSelf();
        killwindow();
      }
    });
    qExport.add(rawDump);
    qExport.add(asmExport);
    qExport.add(chDump);
    qExport.add(pfSetup);
    pfSetup.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        pfSet();
      }
    });
    chDump.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        changeDump();
      }
    });
    asmExport.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        doASMExport();
      }
    });
    rawDump.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        doRawDump();
      }
    });
    options.addSeparator();
    options.add(pfDefSelect);
    pfDefSelect.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        choosePFDefColor();
      }
    });
    options.add(bgSelect);
    bgSelect.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        chooseBGColor();
      }
    });
    options.addSeparator();
    options.add(pfCBs);
    pfCBs.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        showPFCBs();
      }
    });
    this.setJMenuBar(jMenuBar);

    this.setVisible(true);
    setAttsPan();
  }
  public void choosePFDefColor(){
    JOptionPane.showMessageDialog(this,pfLabel,"Select Default Playfield Color", JOptionPane.OK_OPTION);
    this.acHexColor = this.defPFHexColor;
    this.acColor = this.pfDefColor;
    if(this.leftScnLine.size()!=0) changeDefColors(this.leftScnLine);
    if(this.rightScnLine.size()!=0) changeDefColors(this.rightScnLine);
    leftPaint.repaint();
  }
  public void changeDefColors(Hashtable hash){
    Object cdc[] = hash.values().toArray();
    int cdcLength = cdc.length;
    for(int i=0;i<cdcLength;i++){
      PrtScnLine tempPSL = (PrtScnLine)cdc[i];
      if(tempPSL.isDefColor()) tempPSL.changeColor();
    }
  }
  public void chooseBGColor(){
    JOptionPane.showMessageDialog(this,bgLabel,"Select Background Color",JOptionPane.OK_OPTION);
  }

  //Setting the Default Playfield Color
  public void setDefPFLabel(){
    pfLabel.setIcon(dp.colpal);
    pfLabel.addMouseListener(new MouseListener(){
      public void mouseExited(MouseEvent e){
        pfLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
      public void mouseEntered(MouseEvent e){
        pfLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
      }
      public void mouseReleased(MouseEvent e){}
      public void mousePressed(MouseEvent e){}
      public void mouseClicked(MouseEvent e){
//        defPFHexColor = dp.getAtariHexColor(e.getPoint());
//        pfDefColor = (Color)dp.colorHash.get(defPFHexColor);
        
        defPFHexColor = (String) dp.cp.hColorVector.get(dp.cp.hColorVector.size());
           pfDefColor = dp.cp.selectColorWithText(defPFHexColor);

        dp.opTA.append("The Default Playfield Color has been\nset to HEX Value: "+defPFHexColor+"\n\n");
      }
    });

  }
  public void setAcLabel(){
    acLabel.setIcon(dp.colpal);
    acLabel.addMouseListener(new MouseListener(){
      public void mouseExited(MouseEvent e){
        acLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
      public void mouseEntered(MouseEvent e){
        acLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
      }
      public void mouseReleased(MouseEvent e){}
      public void mousePressed(MouseEvent e){}
      public void mouseClicked(MouseEvent e){
//        acHexColor = dp.getAtariHexColor(e.getPoint());
//        acColor = (Color)dp.colorHash.get(acHexColor);

        acHexColor = (String) dp.cp.hColorVector.get(dp.cp.hColorVector.size());
           acColor = dp.cp.selectColorWithText(acHexColor);

        //        dp.opTA.append("The Default Playfield Color has been\nset to HEX Value: "+defPFHexColor+"\n\n");
      }
    });

  }
  public void setBGLabel(){
    bgLabel.setIcon(dp.colpal);
    bgLabel.addMouseListener(new MouseListener(){
      public void mouseExited(MouseEvent e){
        bgLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
      public void mouseEntered(MouseEvent e){
        bgLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
      }
      public void mouseReleased(MouseEvent e){}
      public void mousePressed(MouseEvent e){}
      public void mouseClicked(MouseEvent e){
        bgHexColor = (String) dp.cp.hColorVector.get(dp.cp.hColorVector.size());
           bgColor = dp.cp.selectColorWithText(bgHexColor);
//        bgColor = (Color)dp.colorHash.get(bgHexColor);
        drawPlayfield(leftPaint);
//        leftPaint.setBackground(bgColor);
//        rightPaint.setBackground(bgColor);
        dp.opTA.append("The Background Color has been\nset to HEX Value: "+bgHexColor+"\n\n");
      }
    });
  }
  public void setAttsPan(){
    attsPan.setLayout(new GridLayout(2,1));
    asymmetrical.setForeground(Color.black);
    reflected.setForeground(Color.black);
    asymmetrical.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e){
        if(asymmetrical.isSelected()){
          reflected.setSelected(false);
          reflected.setEnabled(false);
          cbSP.setDividerLocation(245);
          cbSP.setDividerSize(10);
        }else{
          reflected.setEnabled(true);
          cbSP.setDividerLocation(500);
          cbSP.setDividerSize(0);
        }
        drawPlayfield(leftPaint);
      }
    });
    reflected.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e){
        if(reflected.isSelected()){
          asymmetrical.setSelected(false);
          asymmetrical.setEnabled(false);
        }else{
          asymmetrical.setEnabled(true);
        }
        drawPlayfield(leftPaint);
      }
    });
    attsPan.add(asymmetrical);
    attsPan.add(reflected);
  }
  public void setPFatts(){
    JOptionPane.showMessageDialog(this,attsPan,"Playfield Attributes",JOptionPane.OK_OPTION);
  }
  public void buildSLGSet(){
    JOptionPane.showMessageDialog(this,slgSettings,"Set Graphic Group Variables",JOptionPane.OK_OPTION);
  }

  private void setSGL(){
    JButton slrBut = new JButton("Build");
    JLabel slrLabel = new JLabel("Repeat Every: ");
    JLabel slr2Label = new JLabel("Scan Line #:");
    slrLabel.setForeground(Color.black);
    slOutput.setForeground(Color.black);
    slrBut.setForeground(Color.black);
    slgSettings.setLayout(new GridLayout(3,2,1,1));
    slgSettings.add(slrLabel);
    slgSettings.add(slRepeat);
    slgSettings.add(slr2Label);
    slgSettings.add(slLines);
    slgSettings.add(new JLabel(" "));
    slgSettings.add(slrBut);
//    slgSettings.add(slOutput);
    slrBut.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        buildSLGroups(slRepeat.getText(),slLines.getText());
        drawPlayfield(leftPaint);
      }
    });
  }

  public void buildSLGroups(String repeats, String lines){
    try{
      lines_ints = Integer.parseInt(lines);
      if(0 < lines_ints){// && lines_ints < 305){
        Dimension tDim = this.getSize();
        if(lines_ints > 191){//addition
          tDim.height = 48+lines_ints;//33+lines_ints;
          this.setSize(tDim);
          leftPaint.setBounds(0,0,320,lines_ints);
          toolPaint.setBounds(0,0,320,lines_ints);
        }
        r_ints = Integer.parseInt(repeats);
        if(0 < r_ints & r_ints <= lines_ints){
          dp.opTA.append("Creating groups.\n\n");
          double group_d = lines_ints/(double)r_ints;
          group_int = (int)Math.floor(group_d);
          remainder = group_int * r_ints;
          remainder = lines_ints - remainder;
          constructGroupTree(r_ints, group_int, lines_ints);
          constructGroupHash();
        }else{
          dp.opTA.append("Error: "+r_ints+" is not a proper value. Enter an integer from 1 to 192! You have 192 scan lines with which to work. PAL/SECAM not currently supported.\n\n");
        }
      }else{
        JOptionPane.showMessageDialog(this,"Error: No Scan Line Settings less than 1 or more than 305","Build Error Message",JOptionPane.OK_OPTION);
      }
    }catch(Exception ex){
      dp.opTA.append("Error: enter an integer!\n\n");
    }
  }
  public void constructGroupTree(int loop, int groups, int lineNumb){
    if(remainder != 0){
      allGroups = groups + 1;
      dp.opTA.append("Constructing "+allGroups+" graphic line groups.\n");
      dp.opTA.append(groups+" groups contain "+loop+" scan lines each, and \n");
      dp.opTA.append("the last group with "+remainder+" scan lines.\n");
      dp.opTA.append("All "+lineNumb+" scan lines have been used.\n\n");
    }else{
      allGroups = groups;
      dp.opTA.append("Constructing "+allGroups+" graphic line groups.\n");
      dp.opTA.append(groups+" groups contain "+loop+" scan lines each.\n");
      dp.opTA.append("All "+lineNumb+" scan lines have been used.\n\n");
    }
    checkRemoveTree();
//    if(remainder != 0){
      for(int i = 0; i<allGroups; i++){
        int name = i;
        DefaultMutableTreeNode newTNode = new DefaultMutableTreeNode(""+name);
        numNode.insert(newTNode,i);
      }
//    }
    numTree.expandRow(0);
    numTree.updateUI();
  }
  public void checkRemoveTree(){
    try{
      numNode.removeAllChildren();
      numTree.updateUI();
    }catch(Exception ex){
      dp.opTA.append("No children in tree.\n\n");
    }
  }
  public void constructGroupHash(){
    addScnLines(leftScnLine,cbLPan,lcbc);
    addScnLines(rightScnLine,cbRPan,rcbc);
    if (!asymmetrical.isSelected()){
      cbSP.setDividerLocation(500);
      cbSP.setDividerSize(0);
    }else{
      cbSP.setDividerLocation(245);
      cbSP.setDividerSize(10);
    }
  }
  public void clearGroupHash(Hashtable ht, JPanel sp){
    sp.removeAll();
    ht.clear();
  }
  public void addScnLines(Hashtable ht, JPanel sp, GridBagConstraints gbc){
    sp.removeAll();
    ht.clear();
    for(int i=0; i < allGroups;i++){
      int name = i;
      this.dp.grd.statusBar.setText(""+name+", "+allGroups);
      PrtScnLine prt = new PrtScnLine(pfDefColor,defPFHexColor,this,name);
      if(remainder!=0){
        prt.setLineCount(remainder);
      }else{
        prt.setLineCount(r_ints);
      }
      int theSL = i*r_ints;
      prt.setStartLine(theSL);
      try{
        ht.put(""+name, prt);
        sp.add((PrtScnLine)ht.get(""+name),gbc);
      }catch(Exception hte){
//        this.dp.getDT().statusBar.setText(hte.toString());
      }
    }
    cbPanel.updateUI();
  }
  public void setGroupTree(){
    numTree.expandRow(0);
  }
  public void updatePaintAtts(int drawType){
    this.drawType = drawType;
    toolPaint.setHotCursor(drawType);
//    leftPaint.setHotCursor(drawType);
  }
  public void setCBPanel(){
    cbMenu.add(clearAll);
    cbMenu.add(updatePF);
    updatePF.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        drawPlayfield(leftPaint);
      }
    });
    clearAll.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        deleteAllHistory();
      }
    });
    cbPanel.add("North",cbMenu);
    cbPanel.add("Center",cbSP);
    cbSP.setDividerLocation(245);
    cbSP.setOneTouchExpandable(false);
    lcbc.anchor = lcbc.NORTHWEST;
    lcbc.gridx = 0;
    lcbc.gridy = lcbc.RELATIVE;
    rcbc.anchor = rcbc.NORTHWEST;
    rcbc.gridx = 0;
    rcbc.gridy = rcbc.RELATIVE;
    cbLeft.setViewportView(cbLPan);
    cbRight.setViewportView(cbRPan);
    cbPanel.setPreferredSize(new Dimension(500,200));
    cbSP.add(cbLeft);
    cbSP.add(cbRight);
  }
  public void showPFCBs(){
    if(leftScnLine.size()!=0) JOptionPane.showMessageDialog(this,cbPanel,title+" Checkbox Interface",JOptionPane.OK_OPTION);
  }
  public void setSplitPane(){
    numPane.setVerticalScrollBarPolicy(numPane.VERTICAL_SCROLLBAR_ALWAYS);
    numPane.setHorizontalScrollBarPolicy(numPane.HORIZONTAL_SCROLLBAR_NEVER);
    shapePane.setVerticalScrollBarPolicy(shapePane.VERTICAL_SCROLLBAR_ALWAYS);
    shapePane.setHorizontalScrollBarPolicy(shapePane.HORIZONTAL_SCROLLBAR_NEVER);
//    sizePanel.add(BorderLayout.CENTER,splitPane);
    sizePanel.add(BorderLayout.CENTER,rightPane);
//    sizePanel.add(BorderLayout.CENTER,leftPane);
    leftPane.setDividerLocation(80);
    leftPane.setDividerSize(0);
    leftPane.setOneTouchExpandable(false);
//    leftPaint.setBackground(Color.black);
    leftPane.add(numPane);
    //set JTree expanded
    setGroupTree();
    leftPaint.setOpaque(true);
    leftPaint.setBounds(0,0,320,192);
    toolPaint.setBounds(0,0,320,192);
    twoPane.setBorder(new EmptyBorder(0,0,0,0));
//    twoPane.setSize(new Dimension(320,192));
//    twoPane.setPreferredSize(new Dimension(320,192));
    twoPane.add(leftPaint, new Integer(0));
    toolPaint.setOpaque(false);
    twoPane.add(toolPaint, new Integer(1));
//    leftPane.add(leftPaint);
    leftPane.add(twoPane);

    rightPane.setDividerLocation(401);
    rightPane.setDividerSize(0);
    rightPane.setBorder(new EmptyBorder(0,0,0,0));
    rightPane.add(leftPane);
    rightPane.add(shapePane);
//    rightPane.add(new JPanel());
//    rightPaint.setBackground(Color.black);
//    rightPane.add(rightPaint);
//    splitPane.setDividerLocation(240);
//    splitPane.setDividerSize(0);
//    splitPane.add(leftPane);
//    splitPane.setBorder(new EmptyBorder(0,0,0,0));
    leftPane.setBorder(new EmptyBorder(0,0,0,0));
//    rightPane.setBorder(new EmptyBorder(0,0,0,0));
    numPane.setBorder(new EmptyBorder(0,0,0,0));
//    splitPane.add(rightPane);
    setPainters();
  }
  public void setPainters(){
    toolPaint.addMouseMotionListener(new MouseMotionListener(){
      public void mouseMoved(MouseEvent e){
      }
      public void mouseDragged(MouseEvent e){
        currentPoint = e.getPoint();
        if(e.isAltDown()){
          toolPaint.isAlt = true;
        }else{
          toolPaint.isAlt = false;
        }
        drawToolBounds();
      }
    });
    toolPaint.addMouseListener(new MouseListener(){
      public void mouseExited(MouseEvent e){
        toolPaint.setCursor(toolPaint.defCursor);
      }
      public void mouseEntered(MouseEvent e){
        toolPaint.setCursor(toolPaint.hotCursor);
      }
      public void mouseReleased(MouseEvent e){
        currentPoint = e.getPoint();
        if(e.isAltDown()){
          toolPaint.isAlt = true;
        }else{
          toolPaint.isAlt = false;
        }
        createShape();
//        calculateCBSelection();
        drawPlayfield(leftPaint);
        clearToolPane(toolPaint);
      }
      public void mousePressed(MouseEvent e){
        startPoint = e.getPoint();
        if(e.isAltDown()){
          toolPaint.isAlt = true;
        }else{
          toolPaint.isAlt = false;
        }
        if(drawType == 2) {
          tempVector = new Vector(1);
          addRecToTempVec(startPoint);
        }
      }
      public void mouseClicked(MouseEvent e){
      }
    });
  }
  protected void addRecToTempVec(Point sa){
    Point sp = new Point();
    sp.x = sa.x + 3;
    sp.y = sa.y + this.r_ints;
    Point locs[] = this.hitShuffle(sa,sp);
    tempVector.add(makeRec(locs[0],locs[1]));
  }
  public Rectangle2D.Float makeRec(Point sa, Point sp){
    Rectangle2D.Float newRec = new Rectangle2D.Float();
    if(sa.x < sp.x & sa.y < sp.y ){
      newRec.setRect(sa.x,sa.y,sp.x-sa.x,sp.y-sa.y);
    }
    if(sa.x > sp.x & sa.y > sp.y ){
      newRec.setRect(sp.x,sp.y,sa.x-sp.x,sa.y-sp.y);
    }
    if(sa.x < sp.x & sa.y > sp.y ){
      newRec.setRect(sa.x,sp.y,sp.x-sa.x,sa.y-sp.y);
    }
    if(sa.x > sp.x & sa.y < sp.y ){
      newRec.setRect(sp.x,sa.y,sa.x-sp.x,sp.y-sa.y);
    }
    return newRec;
  }

  private void setShapeTreeListeners(){
    shapePopup = new JPopupMenu();
    JMenuItem cancelSP = new JMenuItem("Cancel");
    JMenuItem selSPShowAtt = new JMenuItem("Selection Attributes");
    JMenuItem selSPDel = new JMenuItem("Remove Selected Shapes");
    shapePopup.add(cancelSP);
    shapePopup.addSeparator();
    shapePopup.add(selSPShowAtt);
    shapePopup.add(selSPDel);
    selSPShowAtt.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        boolean go = true;
        if(shapeSelection.equalsIgnoreCase("history")) go = false;
        if(shapeSelection.equals(null)) go = false;
        if(go) editShapeAttributes(shapeSelection);
      }
    });
    selSPDel.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        int go = 0;
        if(shapeSelection.equalsIgnoreCase("history")) go = 1;
        if(shapeSelection.equals(null)) go = 2;
        if(go == 1){
          try{
            boolean bCheck = shapeSelections[1].equals(null);
            if(!bCheck) go = 2;
          }catch(ArrayIndexOutOfBoundsException aioobe){
//            go = 2;
          }
        }
        dp.grd.statusBar.setText(""+go);
        switch(go){
          case 0:
            int check = JOptionPane.showConfirmDialog(dp.pTools,"This operation cannot be undone. Do you wish to continue?","Delete Selected Shapes",JOptionPane.YES_NO_OPTION);
            if(check == JOptionPane.YES_OPTION) delShape(shapeSelections);
            break;
          case 1:
            int check2 = JOptionPane.showConfirmDialog(dp.pTools,"This operation will completely delete the History and cannot be undone. Do you wish to continue?","Delete Selected Shapes",JOptionPane.YES_NO_OPTION);
            if(check2 == JOptionPane.YES_OPTION) deleteAllHistory();// delShape(shapeSelections);
            break;
          default:
            break;
        }
      }
    });
    shapeTree.addTreeSelectionListener(new TreeSelectionListener(){
      public void valueChanged(TreeSelectionEvent e){
        try{
          TreePath[] tpst = shapeTree.getSelectionPaths();
          shapeSelection = tpst[0].getLastPathComponent().toString();
          int tpstInt = tpst.length;
          shapeSelections = new String[tpstInt];
          for(int i=0;i<tpstInt;i++){
            shapeSelections[i] = tpst[i].getLastPathComponent().toString();
          }
        }catch(NullPointerException npe){

        }
      }
    });

    shapeTree.addMouseListener(new MouseListener(){
      public void mouseExited(MouseEvent e){
      }
      public void mouseEntered(MouseEvent e){
      }
      public void mouseReleased(MouseEvent e){
        int modifier = e.getModifiers();
        Point butPoint = e.getPoint();
        if((modifier & e.BUTTON3_MASK) !=0){
          dp.grd.statusBar.setText("Blah");
          shapePopup.show((Component)e.getSource(),butPoint.x,butPoint.y);
        }
      }
      public void mousePressed(MouseEvent e){
      }
      public void mouseClicked(MouseEvent e){
      }
    });
  }
  protected void delShape(String[] shapeNames){
    int snInt = shapeNames.length;
    TreePath[] tPaths = shapeTree.getSelectionPaths();
    for(int i=0;i<snInt;i++){
      stNames.remove(shapeNames[i]);
      shapeTable.remove(shapeNames[i]);
      shapeTypeTable.remove(shapeNames[i]);
    }
    int stNInt = stNames.size();
    shapeNode.removeAllChildren();
    shapeMod.reload();
    for(int i=0;i<stNInt;i++){
      this.addShapeTree((String)stNames.get(i));
    }
    leftPaint.repaint();
  }
  protected void editShapeAttributes(String shapeName){
    Integer typeInt = (Integer)shapeTypeTable.get(shapeName);
    int tInt = typeInt.intValue();
    Object shapeObject = shapeTable.get(shapeName);
    switch(tInt){
      case 0:
        attPane((Rectangle2D.Float)shapeObject, shapeName);
        break;
      case 5:
        attPane((Rectangle2D.Float)shapeObject, shapeName);
        break;
      default:
        break;
    }
    leftPaint.repaint();
  }

  protected void attPane(Rectangle2D.Float box,String shapeName){
    JPanel bPan = new JPanel(new GridLayout(3,6));
    JLabel pan1 = new JLabel("Location");
    JLabel pan2 = new JLabel("Height");
    JLabel pan3 = new JLabel("Width");
    JLabel empty1 = new JLabel("");
    JLabel empty2 = new JLabel("");
    JTextField pan4 = new JTextField(""+(int)box.x);
    JTextField pan5 = new JTextField(""+(int)box.y);
    JTextField pan6 = new JTextField(""+(int)box.height);
    JTextField pan7 = new JTextField(""+(int)box.width);
    bPan.add(pan1);
    bPan.add(pan4);
    bPan.add(pan5);
    bPan.add(pan2);
    bPan.add(pan6);
    bPan.add(empty1);
    bPan.add(pan3);
    bPan.add(pan7);
    bPan.add(empty2);
    JOptionPane.showMessageDialog(dp.pTools,bPan,shapeName,JOptionPane.OK_OPTION);
    try{
      box.x = Float.parseFloat(pan4.getText());
      box.y = Float.parseFloat(pan5.getText());
      box.height = Float.parseFloat(pan6.getText());
      box.width = Float.parseFloat(pan7.getText());
    }catch(Exception anyE){
      this.dp.grd.statusBar.setText("Invalid Value");
    }
    bPan = null;
  }

  public void addShapeTree(String shapeName){
    DefaultMutableTreeNode newSTreeNode = new DefaultMutableTreeNode(shapeName);
    int cc = shapeNode.getChildCount();
    this.shapeMod.insertNodeInto(newSTreeNode,shapeNode,cc);
//    shapeNode.insert(newSTreeNode,cc);
    this.shapeTree.expandRow(0);
    this.shapePane.updateUI();
  }
  public Point[] hitShuffle(Point sa, Point sp){
    if(!this.asymmetrical.isSelected()){
      if(this.reflected.isSelected()){
        if(sa.x >159 && sp.x > 159){
          sa.x = sa.x - ((sa.x - 160)*2);
          sp.x = sp.x - ((sp.x - 160)*2);
        }
        if(sa.x <= 159 && sp.x > 159){
          sp.x = 159;
        }
        if(sa.x > 159 && sp.x <= 159){
          sa.x = sa.x - ((sa.x - 160)*2);
          sp.x = 159;
        }
      }else{
        if(sa.x >159 && sp.x > 159){
          sa.x = sa.x - 160;
          sp.x = sp.x - 160;
        }
        if(sa.x <= 159 && sp.x > 159){
          sp.x = 160;
        }
        if(sa.x > 159 && sp.x <= 159){
          sa.x = 160;
        }
      }
    }
    Point[] newPArray = new Point[2];
    newPArray[0] = sa;
    newPArray[1] = sp;
    return newPArray;
  }
  protected void createPointGroups(){

  }
  public void createShape(){
    Point sa = this.startPoint;
    Point sp = this.currentPoint;
    Point[] locs = new Point[2];
    String shapeName = "";
    if(this.leftScnLine.size()!=0){
      switch(drawType){
        case 0://dp.pTools.BOX:
          if(toolPaint.isAlt){
            locs = hitShuffle(sa, sp);
            Rectangle2D.Float newErRec = makeRec(locs[0], locs[1]);
            shapeName = "Erase("+eraseBoxNumb+")";
            shapeTable.put(shapeName,newErRec);
            shapeTypeTable.put(shapeName,new Integer(5));
            stNames.add(shapeName);
            eraseBoxNumb = eraseBoxNumb+1;
            addShapeTree(shapeName);
          }else{
            locs = hitShuffle(sa, sp);
            Rectangle2D.Float newRec = makeRec(locs[0], locs[1]);
            shapeName = "Box("+boxNumb+")";
            shapeTable.put(shapeName,newRec);
            shapeTypeTable.put(shapeName,new Integer(0));
            stNames.add(shapeName);
            boxNumb = boxNumb+1;
            addShapeTree(shapeName);
          }
          break;
        case 1://dp.pTools.LINE:
          break;
        case 2://dp.pTools.PIXEL:
          if(toolPaint.isAlt){
            shapeName = "EPixel("+erasePixelNumb+")";
            shapeTable.put(shapeName,tempVector);
            shapeTypeTable.put(shapeName,new Integer(7));
            stNames.add(shapeName);
            erasePixelNumb = erasePixelNumb+1;
            addShapeTree(shapeName);
          }else{
            shapeName = "DPixel("+drawPixelNumb+")";
            shapeTable.put(shapeName,tempVector);
            shapeTypeTable.put(shapeName,new Integer(2));
            stNames.add(shapeName);
            drawPixelNumb = drawPixelNumb+1;
            addShapeTree(shapeName);
          }
          break;
        case 3://dp.pTools.ELLIPSE:
          break;
        case 4://dp.pTools.TEXT:
          break;
//        case 5://dp.pTools.ERASE:
//          break;
        case 6://dp.pTools.MOVE;
          break;
//        case 7://... ok... erasepixel
//          break;
//        case 9:
//          break;
        default:
          break;
      }
    }
  }
  public int[] getGroups(int y1, int y2){
    int combined = y1+y2;
    Object[] groupArray = this.leftScnLine.values().toArray();
    int pl = groupArray.length;
    int checkValue = y1;
    int vecSize = 0;
    Vector vec = new Vector(1);
    while(checkValue <= combined){
      for(int i=0;i<pl;i++){
        PrtScnLine tPrt = (PrtScnLine)groupArray[i];
        if(checkValue == tPrt.startLine){
          vec.add(vecSize,tPrt);
          vecSize = vecSize + 1;
        }
      }
      checkValue = checkValue+1;
    }
    int[] groups = new int[vecSize];
    for(int i=0;i<vecSize;i++){
      PrtScnLine tPrt = (PrtScnLine)vec.get(i);
      groups[i] = tPrt.group;
    }
    return groups;
  }
  public int getCheckLeftNumb(int x, boolean left){
    int checked = 0;
    if(left){
      checked = 0;
      int test = 0;
      for(int i=0;i<20;i++){
        if(x>=test & x < 160) checked = checked+1;
        test = test +8;
      }
    }else{
      checked = 0;
      int test = 160;
      for(int i=0;i<20;i++){
        if(x>=test) checked = checked+1;
        test = test +8;
      }
    }
//    test = 0;

    return checked-1;
  }
  public boolean[][] getAssBoxes(int x, int width){
    int checkNumb = x;
    boolean[][] boxes = new boolean[2][20];
    for(int i=0;i<20;i++){
      boxes[0][i] = false;
      boxes[1][i] = false;
    }
    while(checkNumb<=x+width){
      int holdChecker = getCheckLeftNumb(checkNumb,true);
      if(holdChecker>=0) boxes[0][holdChecker] = true;
      holdChecker = getCheckLeftNumb(checkNumb,false);
      if(holdChecker>=0) boxes[1][holdChecker] = true;
      checkNumb = checkNumb+1;
    }
    return boxes;
  }
  public boolean[] getLeftBoxes(int x, int width){
    int checkNumb = x;
    boolean[] boxes = new boolean[20];
    for(int i=0;i<20;i++){
      boxes[i] = false;
    }
    while(checkNumb<=x+width){
      int holdChecker = getCheckLeftNumb(checkNumb,true);
      if(holdChecker>=0) boxes[holdChecker] = true;
      holdChecker = getCheckLeftNumb(checkNumb,false);
      if(holdChecker>=0) boxes[holdChecker] = true;
      checkNumb = checkNumb+1;
    }
    return boxes;
  }
  public void removeCBForErase(Rectangle2D.Float rec){
    int gtn[] = getGroups((int)rec.y,(int)rec.height);
    boolean boxesAss[][];
    boolean boxes[];
    if(asymmetrical.isSelected()){
      boxesAss = getAssBoxes((int)rec.x, (int)rec.width);
      int gtnl = gtn.length;
      for(int i=0;i<gtnl;i++){
        PrtScnLine lPrt = (PrtScnLine)leftScnLine.get(""+gtn[i]);
        PrtScnLine rPrt = (PrtScnLine)rightScnLine.get(""+gtn[i]);
        lPrt.unCheckBoxesByGraphics(boxesAss[0]);
        rPrt.unCheckBoxesByGraphics(boxesAss[1]);
      }
    }else{
      boxes = getLeftBoxes((int)rec.x, (int)rec.width);
      int gtnl = gtn.length;
      for(int i=0;i<gtnl;i++){
        PrtScnLine tPrt = (PrtScnLine)leftScnLine.get(""+gtn[i]);
        tPrt.unCheckBoxesByGraphics(boxes);
      }
    }
  }
  public void removeCBForErase(Point point){

  }
  public void setCBForBoxes(Rectangle2D.Float rec){
    int gtn[] = getGroups((int)rec.y,(int)rec.height);
    boolean boxesAss[][];
    boolean boxes[];
    if(asymmetrical.isSelected()){
      boxesAss = getAssBoxes((int)rec.x, (int)rec.width);
      int gtnl = gtn.length;
      for(int i=0;i<gtnl;i++){
        PrtScnLine lPrt = (PrtScnLine)leftScnLine.get(""+gtn[i]);
        PrtScnLine rPrt = (PrtScnLine)rightScnLine.get(""+gtn[i]);
        lPrt.checkBoxesByGraphics(boxesAss[0]);
        rPrt.checkBoxesByGraphics(boxesAss[1]);
      }
    }else{
      boxes = getLeftBoxes((int)rec.x, (int)rec.width);
      int gtnl = gtn.length;
      for(int i=0;i<gtnl;i++){
        PrtScnLine tPrt = (PrtScnLine)leftScnLine.get(""+gtn[i]);
        tPrt.checkBoxesByGraphics(boxes);
      }
    }
  }
  public void calculateCBSelection(){
    int drawType = 0;
    int stSize = stNames.size();
    for(int i=0;i<stSize;i++){
//      String shapeName = (String)shapeTableNames.get(new Integer(i));
      String shapeName = (String)stNames.get(i);
      Integer holdInt = (Integer)shapeTypeTable.get(shapeName);
      drawType = holdInt.intValue();
      Object holdShape = shapeTable.get(shapeName);
      switch(drawType){
        case 0:
          setCBForBoxes((Rectangle2D.Float)holdShape);
          break;
        case 5:
          if(toolPaint.isAlt){
            removeCBForErase((Rectangle2D.Float)holdShape);
          }
          break;
        default:
          break;
      }
    }
  }
  public void clearToolPane(ToolPaintPane toolPanel){
    toolPanel.tool = false;
    toolPanel.repaint();
  }
  public void drawPlayfield(PaintField drawPanel){
    drawPanel.back = true;
    drawPanel.pf = true;
//    drawPanel.tool = false;
    drawPanel.repaint();//paint(drawPanel.getGraphics());
  }
  public void drawToolBounds(){//ToolPaintPane toolPanel){
    boolean isDragTool = false;
    if(drawType == dp.pTools.PIXEL)isDragTool=true;
//    if(drawType == dp.pTools.ERASE & toolPaint.isAlt == false)isDragTool=true;
    if(drawType == dp.pTools.MOVE)isDragTool=true;
    if(isDragTool)setJCheckBoxes(isDragTool);
    if(this.lines_ints > 0){
      toolPaint.tool = true;
      toolPaint.repaint();
//      drawPanel.repaint();//paint(drawPanel.getGraphics());
    }
  }

  protected void updateShapeLocs(int x, int y){
    startPoint.x = startPoint.x + x;
    startPoint.y = startPoint.y + y;
    boolean isASelection = true;
    try{
      int ssInt = shapeSelections.length;
      if(shapeSelections[0].equalsIgnoreCase("")) isASelection = false;
      if(shapeSelections[0].equalsIgnoreCase("history")) isASelection = false;
      if(isASelection){
        for(int i=0;i<ssInt;i++){
          Integer ssType = (Integer)shapeTypeTable.get(shapeSelections[i]);
          int ssTypeInt = ssType.intValue();
          switch(ssTypeInt){
            case 0:
              Rectangle2D.Float box = (Rectangle2D.Float)shapeTable.get(shapeSelections[i]);
              box.x = box.x + x;
              box.y = box.y + y;
              break;
            case 2:
              Vector getTVec1 = (Vector)shapeTable.get(shapeSelections[i]);
              int gtv1 = getTVec1.size();
              for(int j=0;j<gtv1;j++){
                Rectangle2D.Float pbox = (Rectangle2D.Float)getTVec1.get(j);
                pbox.x = pbox.x + x;
                pbox.y = pbox.y + y;
              }
              break;
            case 5:
              Rectangle2D.Float eraseBox = (Rectangle2D.Float)shapeTable.get(shapeSelections[i]);
              eraseBox.x = eraseBox.x + x;
              eraseBox.y = eraseBox.y + y;
              break;
            case 7:
              Vector getTVec2 = (Vector)shapeTable.get(shapeSelections[i]);
              int gtv2 = getTVec2.size();
              for(int j=0;j<gtv2;j++){
                Rectangle2D.Float pebox = (Rectangle2D.Float)getTVec2.get(j);
                pebox.x = pebox.x + x;
                pebox.y = pebox.y + y;
              }
              break;
            default:
              break;
          }
        }
      }
    }catch(Exception exc){
      dp.grd.statusBar.setText(exc.toString());
    }
  }
  public void setJCheckBoxes(boolean isDragTool){
    if(isDragTool){
      switch(drawType){
        case 2:
          addRecToTempVec(currentPoint);
          break;
        case 6:
          int x = this.currentPoint.x - this.startPoint.x;
          int y = this.currentPoint.y - this.startPoint.y;
          updateShapeLocs(x,y);
          break;
        default:
          break;
      }
    }else{

    }
  }
  public Point getXValues(int register, int pos, boolean left, boolean ref){
    Point retPoint = dp.getPFPoint(register,pos,left,ref);
    return retPoint;
  }
  public void clearAllCheckBoxes(){
    int lsl = leftScnLine.size();
    int rsl = rightScnLine.size();
    if(lsl !=0){
      for(int i=0;i<lsl;i++){
        String istr = ""+i;
        PrtScnLine lprt = (PrtScnLine)leftScnLine.get(istr);
        PrtScnLine rprt = (PrtScnLine)rightScnLine.get(istr);
        int countNum = 0;
        while(countNum < 4){
          lprt.pf0_box[countNum].setSelected(false);
          rprt.pf0_box[countNum].setSelected(false);
          countNum = countNum+1;
        }
        while(countNum > 3 & countNum < 12){
          int j = countNum - 4;
          lprt.pf1_box[j].setSelected(false);
          rprt.pf1_box[j].setSelected(false);
          countNum = countNum+1;
        }
        while(countNum > 11 & countNum < 20){
          int j = countNum - 12;
          lprt.pf2_box[j].setSelected(false);
          rprt.pf2_box[j].setSelected(false);
          countNum = countNum+1;
        }
      }
    }
  }
  protected void deleteAllHistory(){
    this.shapeNode.removeAllChildren();
    this.shapeMod.reload(shapeNode);
    this.shapeSelection = null;
    this.shapeSelections = null;
    this.shapeTable.clear();
    this.stNames.clear();
    this.shapeTypeTable.clear();
//    this.tempVector.clear();
    this.drawPixelNumb = 0;
    this.erasePixelNumb = 0;
    this.eraseBoxNumb = 0;
    this.boxNumb = 0;
    this.leftPaint.repaint();
/*
    Rectangle getrec = this.leftPaint.getBounds();
    this.startPoint = getrec.getLocation();
    this.currentPoint = new Point(getrec.getLocation().x + getrec.width, getrec.getLocation().y + getrec.height);
    this.drawType = dp.pTools.ERASE;
    this.toolPaint.isAlt = true;
    createShape();
    this.toolPaint.isAlt = false;
    this.leftPaint.repaint();
    this.drawType = dp.drawType;
    */
  }
  public void doASMExport(){
//    Integer editN = new Integer(dp.editors);
//    ASMEditor edit = new ASMEditor(dp);
//    writeToEditor(edit);
//    dp.editHash.put(editN,edit);
  }
//FindIt
  public void pfSet(){
    generatePFData();
    Vector grab0V = (Vector)leftPFData.get(0);
    Vector grab1V = (Vector)leftPFData.get(1);
    Vector grab2V = (Vector)leftPFData.get(2);

    Vector tb0 = new Vector(1);
    Vector tb1 = new Vector(1);
    Vector tb2 = new Vector(1);

    tb0.clear();
    tb1.clear();
    tb2.clear();

    int pf0 = grab0V.size();
    int pf1 = grab1V.size();
    int pf2 = grab2V.size();

    tb0.add("PF0Stor\n");
    tb1.add("PF1Stor\n");
    tb2.add("PF2Stor\n");
    for(int j=pf0; j>0; j--){
      Vector tempVec01 = (Vector)grab0V.get(j-1);
      Vector tempVec02 = (Vector)grab1V.get(j-1);
      Vector tempVec03 = (Vector)grab2V.get(j-1);
      int tvInt = tempVec01.size();
      for(int i=tvInt; i>0; i--){
        tb0.add("     .byte $"+(String)tempVec01.get(i-1)+"\n");
        tb1.add("     .byte $"+(String)tempVec02.get(i-1)+"\n");
        tb2.add("     .byte $"+(String)tempVec03.get(i-1)+"\n");
      }
    }
    int tb0Int = tb0.size();
    for(int i=0;i<tb0Int;i++){
      this.dp.opTA.append((String)tb0.get(i));
    }
    int tb1Int = tb0.size();
    for(int i=0;i<tb1Int;i++){
      this.dp.opTA.append((String)tb1.get(i));
    }
    int tb2Int = tb2.size();
    for(int i=0;i<tb2Int;i++){
      this.dp.opTA.append((String)tb2.get(i));
    }
  }
  public void changeDump(){
    generatePFData();
    Vector grab0V = (Vector)leftPFData.get(0);
    Vector grab1V = (Vector)leftPFData.get(1);
    Vector grab2V = (Vector)leftPFData.get(2);
//    Vector t0Vec = (Vector)grab0V.get(0);
//    Vector t1Vec = (Vector)grab1V.get(0);
//    Vector t2Vec = (Vector)grab2V.get(0);
    Vector tb0 = new Vector(1);
    Vector tb1 = new Vector(1);
    Vector tb2 = new Vector(1);
    Vector tb3 = new Vector(1);
    tb0.clear();
    tb1.clear();
    tb2.clear();
    int pf0 = grab0V.size();
    int pf1 = grab1V.size();
    int pf2 = grab2V.size();
//    String testSt1 = "";
//    String testSt2 = "";
    tb0.add("left_pf0\n");
    tb1.add("left_pf1\n");
    tb2.add("left_pf2\n");
    tb3.add("count\n");
    String pastCheck = "";
    for(int i=0;i<pf0;i++){
      pastCheck = seperatePFData(tb0,tb1,tb2,tb3,(Vector)grab0V.get(i),(Vector)grab1V.get(i),(Vector)grab2V.get(i), pastCheck);
    }
    tb3.add("  .byte $"+decimalToHex(kernalCount1)+"\n");
    int wtb0 = tb0.size();
    int wtb1 = tb1.size();
    int wtb2 = tb2.size();
    int wtb3 = tb3.size();

    for(int i=0;i<wtb0;i++){
      this.dp.opTA.append((String)tb0.get(i));
    }
    for(int i=0;i<wtb1;i++){
      this.dp.opTA.append((String)tb1.get(i));
    }
    for(int i=0;i<wtb2;i++){
      this.dp.opTA.append((String)tb2.get(i));
    }
    for(int i=0;i<wtb3;i++){
      this.dp.opTA.append((String)tb3.get(i));
    }
  }
  public String lessThanSixteen(int lts){
    String hexOut = "";
    if(lts <10){
      hexOut = ""+lts;
    }else{
      if(lts < 16){
        switch(lts){
          case 10:
            hexOut = "A";
            break;
          case 11:
            hexOut = "B";
            break;
          case 12:
            hexOut = "C";
            break;
          case 13:
            hexOut = "D";
            break;
          case 14:
            hexOut = "E";
            break;
          case 15:
            hexOut = "F";
            break;
          default:
            break;
        }
      }
    }
    return hexOut;
  }
  public String decimalToHex(int deci){
    String hexOut = "";
    if((deci > -1) && (deci<256)){
      double deciD = (double)deci;
      deciD = deciD/16;
      if(deciD < 1.0d){
        hexOut = "0"+lessThanSixteen(deci)+"; "+deci;
      }else{
        double deciD1 = Math.floor(deciD);
        int deci2 = (int)deciD1;
        hexOut = lessThanSixteen(deci2);
        deci2 = deci - (deci2 * 16);
        hexOut = hexOut + lessThanSixteen(deci2)+"; "+deci;
      }
    }else{
      hexOut = "ERROR - more than 255 lines between playfield changes";
    }
    return hexOut;
  }
  public String seperatePFData(Vector taker1, Vector taker2, Vector taker3, Vector taker4, Vector giver1, Vector giver2, Vector giver3, String pc){
    int gNumb = giver1.size();
    String testSt1 = "";
    String testSt2 = "";
    if(pc.equals("")){
      taker1.add("  .byte $"+(String)giver1.get(0)+";taker 1\n");
      taker2.add("  .byte $"+(String)giver2.get(0)+";taker 2\n");
      taker3.add("  .byte $"+(String)giver3.get(0)+";taker 3\n");
      kernalCount1 = r_ints;
    }else{
      String pc2 = ""+""+(String)giver1.get(0)+(String)giver2.get(0)+(String)giver3.get(0);
      if(!pc.equals(pc2)){
        taker1.add("  .byte $"+(String)giver1.get(0)+";taker 1\n");//problem
        taker2.add("  .byte $"+(String)giver2.get(0)+";taker 2\n");//problem
        taker3.add("  .byte $"+(String)giver3.get(0)+";taker 3\n");//problem
        taker4.add("  .byte $"+decimalToHex(kernalCount1)+";taker 4\n");
        kernalCount1 = r_ints;
      }else{
        kernalCount1 = kernalCount1 + r_ints;
      }
    }
    for(int i=1;i<gNumb;i++){
      testSt1 = ""+(String)giver1.get(i-1)+(String)giver2.get(i-1)+(String)giver3.get(i-1);
      testSt2 = ""+(String)giver1.get(i)+(String)giver2.get(i)+(String)giver3.get(i);
      if(!testSt1.equals(testSt2)){
        taker1.add("  .byte $"+(String)giver1.get(i)+"; "+i+"\n");
        taker2.add("  .byte $"+(String)giver2.get(i)+"; "+i+"\n");
        taker3.add("  .byte $"+(String)giver3.get(i)+"; "+i+"\n");
        taker4.add("  .byte $"+decimalToHex(kernalCount1)+";taker 4\n");
        kernalCount1 = r_ints;
      }else{
        kernalCount1 = kernalCount1 + r_ints;
      }
    }
    return testSt2;
  }
  public void generatePFData(){
    int groupsNumb = this.leftScnLine.size();
    double dataGps = (double)groupsNumb/8;
    double dataGroups = Math.floor(dataGps);
    double testDataGps = dataGps - dataGroups;
    if(testDataGps != 0) dataGroups = dataGroups + 1;
//    if((testDataGps != 0) && (lines_ints < 193)) dataGroups = dataGroups + 1;
    int dsc = 0;
    int dataGCount = 0;
    int dataG = (int)dataGroups;
//    if(testDataGps != 0) dataG = dataG+1;
    int errorC=0;
    //pf0 data groups
    leftPFData.clear();
    rightPFData.clear();
    Vector pf0_l = new Vector(1);
    Vector pf0_r = new Vector(1);
    Vector pf1_l = new Vector(1);
    Vector pf1_r = new Vector(1);
    Vector pf2_l = new Vector(1);
    Vector pf2_r = new Vector(1);
    for(int i=0;i<dataG;i++){
      String pfhold = "";
      String pfrhold = "";
      Vector pf0_lefttemp = new Vector(1);
      Vector pf0_righttemp = new Vector(1);
      Vector pf1_lefttemp = new Vector(1);
      Vector pf1_righttemp = new Vector(1);
      Vector pf2_lefttemp = new Vector(1);
      Vector pf2_righttemp = new Vector(1);
      while(dataGCount < 8){
        try{
        int dscAdd = dsc + dataGCount;
        PrtScnLine lprt = (PrtScnLine)leftScnLine.get(""+dscAdd);
        PrtScnLine rprt = (PrtScnLine)rightScnLine.get(""+dscAdd);
        for(int j=0;j<4;j++){
          errorC = errorC+1;
//          this.dp.getDT().statusBar.setText(""+errorC);
          if(lprt.pf0_box[j].isSelected()){
            pfhold = "1"+pfhold;
          }else{
            pfhold =  "0"+pfhold;
          }
          if(rprt.pf0_box[j].isSelected()){
            pfrhold = "1"+pfrhold;
          }else{
            pfrhold = "0"+pfrhold;
          }
        }
        String tempPF0l = dp.calHexValue(pfhold)+"0";
        String tempPF0r = dp.calHexValue(pfrhold)+"0";
        pf0_lefttemp.add(tempPF0l);
        pf0_righttemp.add(tempPF0r);

        pfhold = "";
        pfrhold = "";

        for(int j=0;j<4;j++){
          if(lprt.pf1_box[j].isSelected()){
            pfhold = pfhold + "1";
          }else{
            pfhold = pfhold + "0";
          }
          if(rprt.pf1_box[j].isSelected()){
            pfrhold = pfrhold+"1";
          }else{
            pfrhold = pfrhold+"0";
          }
        }
        String tempPF1la = dp.calHexValue(pfhold);
        String tempPF1ra = dp.calHexValue(pfrhold);

        pfhold = "";
        pfrhold = "";

        for(int j=4;j<8;j++){
          if(lprt.pf1_box[j].isSelected()){
            pfhold = pfhold+"1";
          }else{
            pfhold =  pfhold+"0";
          }
          if(rprt.pf1_box[j].isSelected()){
            pfrhold = pfrhold+"1";
          }else{
            pfrhold = pfrhold+"0";
          }
        }
        String tempPF1lb = dp.calHexValue(pfhold);
        String tempPF1rb = dp.calHexValue(pfrhold);

        String tempPF1l = tempPF1la + tempPF1lb;
        String tempPF1r = tempPF1ra + tempPF1rb;
        pf1_lefttemp.add(tempPF1l);
        pf1_righttemp.add(tempPF1r);

        pfhold = "";
        pfrhold = "";

        for(int j=0;j<4;j++){
          if(lprt.pf2_box[j].isSelected()){
            pfhold = "1"+pfhold;
          }else{
            pfhold = "0"+pfhold;
          }
          if(rprt.pf2_box[j].isSelected()){
            pfrhold = "1"+pfrhold;
          }else{
            pfrhold = "0"+pfrhold;
          }
        }
        String tempPF2la = dp.calHexValue(pfhold);
        String tempPF2ra = dp.calHexValue(pfrhold);

        pfhold = "";
        pfrhold = "";

        for(int j=4;j<8;j++){
          if(lprt.pf2_box[j].isSelected()){
            pfhold = "1"+pfhold;
          }else{
            pfhold =  "0"+pfhold;
          }
          if(rprt.pf2_box[j].isSelected()){
            pfrhold = "1"+pfrhold;
          }else{
            pfrhold = "0"+pfrhold;
          }
        }
        String tempPF2lb = dp.calHexValue(pfhold);
        String tempPF2rb = dp.calHexValue(pfrhold);

        String tempPF2l = tempPF2lb + tempPF2la;
        String tempPF2r = tempPF2rb + tempPF2ra;
        pf2_lefttemp.add(tempPF2l);
        pf2_righttemp.add(tempPF2r);

        pfhold = "";
        pfrhold = "";

        }catch(Exception ething){
        }
        dataGCount = dataGCount + 1;
      }
      pf0_l.add(pf0_lefttemp);
      pf0_r.add(pf0_righttemp);
      pf1_l.add(pf1_lefttemp);
      pf1_r.add(pf1_righttemp);
      pf2_l.add(pf2_lefttemp);
      pf2_r.add(pf2_righttemp);
      dsc = dsc + dataGCount;
      dataGCount = 0;
    }
    leftPFData.add(pf0_l);
    rightPFData.add(pf0_r);
    leftPFData.add(pf1_l);
    rightPFData.add(pf1_r);
    leftPFData.add(pf2_l);
    rightPFData.add(pf2_r);
  }
  public void writeToEditor(ASMEditor edit){
    String tempString = "";
    generatePFData();
    //Set up processor, includes, and ram/rom
    tempString = "    processor 6502\n"+
    "    include vcs.h\n"+
    "    org $F000\n" +
    "PFX = $80\nPFY = $81\nPFZ = $82\n";
    edit.editArea.append(tempString);

    //Set up stack and memory clear
    tempString = "Start\n"+
"        SEI\n"+
"        CLD\n"+
"        LDX  #$FF\n"+
"        TXS\n"+
"        LDA  #0\n"+
"ClearMem\n"+
"        STA 0,X\n"+
"        DEY\n"+
"        BNE ClearMem\n";
    edit.editArea.append(tempString);
    tempString = "        LDA #$" + this.bgHexColor + "\n" +
"        STA COLUBK\n"+
"        LDA #$" + this.defPFHexColor + "\n" +
"        STA COLUPF\n\n";
    edit.editArea.append(tempString);

    //Start Kernel and burn scanlines
    tempString = "MainLoop\n"+
"VSyncSetup\n"+
"        LDA #2\n"+
"        STA VSYNC\n"+
"        STA WSYNC\n"+
"        STA WSYNC\n"+
"        STA WSYNC\n"+
"        LDA #43\n"+
"        STA TIM64T\n"+
"        LDA #0\n"+
"        STA VSYNC\n\n";
    edit.editArea.append(tempString);

    //Zero Playfield - Wait for Vertical blank to finish

    tempString = "        LDA #$00 ;ZERO out playfield\n"+
"        STA PF0\n"+
"        STA PF1\n"+
"        STA PF2\n\n"+

"VBlankWait\n"+
"        LDA #0\n        TAX\n"+
//"        LDA pf0DataL_0\n"+
//"        STA PFX\n"+
//"        LDA pf1DataL_0\n"+
//"        STA PFY\n"+
//"        LDA pf2DataL_0\n"+
//"        STA PFZ\n"+
"        LDA INTIM\n"+
"        BNE VBlankWait\n";
    edit.editArea.append(tempString);

    if(reflected.isSelected()){
      tempString = "        LDA #1\n";
      edit.editArea.append(tempString);
    }else{
      tempString = "        LDA #0\n";
      edit.editArea.append(tempString);
    }
    int yValue = 0;
    if(this.asymmetrical.isSelected()){
      yValue = r_ints;
    }else{
      yValue = r_ints-1;
    }
    tempString = "        STA CTRLPF\n"+
"        LDY #8\n"+
"        STA WSYNC\n"+
"        STA VBLANK\n"+
//"        STA WSYNC\n"+
"        LDY #"+ yValue +"\n"+
"        STA WSYNC\n";
    edit.editArea.append(tempString);

//Drawing
//Group variables Setup
    int groupsNumb = this.leftScnLine.size();
    double dataGps = (double)groupsNumb/8;
    double dataGroups = Math.floor(dataGps);
    double testDataGps = dataGps - dataGroups;
//    if((testDataGps != 0) && (lines_ints < 193)) dataGroups = dataGroups + 1;
    if(testDataGps != 0) dataGroups = dataGroups + 1;
    int dataG = (int)dataGroups;
    Vector tempVecL0 = (Vector)leftPFData.get(0);
    Vector tempVecR0 = (Vector)rightPFData.get(0);
    Vector tempVecL1 = (Vector)leftPFData.get(1);
    Vector tempVecR1 = (Vector)rightPFData.get(1);
    Vector tempVecL2 = (Vector)leftPFData.get(2);
    Vector tempVecR2 = (Vector)rightPFData.get(2);
//
    tempString = "Draw\n";
    edit.editArea.append(tempString);
    int countUp = 0;
    int cCount = 0;
    int jumpNumb = 0;
    for(int i=0;i<groupsNumb;i++){
      if(this.asymmetrical.isSelected()){
        tempString = "Line"+i+"\n"+
        "        STA WSYNC\n"+
        "        LDA pf0DataL_"+countUp+",X\n"+
        "        STA PF0\n"+
        "        LDA pf1DataL_"+countUp+",X\n"+
        "        STA PF1\n"+
        "        LDA pf2DataL_"+countUp+",X\n"+
        "        STA PF2\n";
        if(cCount==0) edit.editArea.append(tempString);

        tempString = "" +
        "        LDA pf0DataR_"+countUp+",X\n"+
        "        STA PF0\n"+
        "        NOP\n        NOP\n" +
        "        LDA pf1DataR_"+countUp+",X\n"+
        "        STA PF1\n"+
        "        LDA pf2DataR_"+countUp+",X\n"+
        "        STA PF2\n";
        if(cCount==0) edit.editArea.append(tempString);
        tempString = "        DEY\n" +
        "        BNE Line"+i+"\n" +
        "        LDY #"+yValue+"\n" +
        "        INX\n" +
        "        CPX #8\n" +
        "        BNE Line"+i+"\n" +
        "        LDX #0\n";
        if(cCount==0) edit.editArea.append(tempString);
        cCount = cCount +1;
        if(cCount==8){
          cCount = 0;
          countUp = countUp +1;
        }
      }else{
        tempString = "Line"+i+"\n"+
        "        STA WSYNC\n"+
        "        LDA pf0DataL_"+countUp+",X\n"+
        "        STA PF0\n"+
        "        LDA pf1DataL_"+countUp+",X\n"+
        "        STA PF1\n"+
        "        LDA pf2DataL_"+countUp+",X\n"+
        "        STA PF2\n";
        if(cCount==0) edit.editArea.append(tempString);
        tempString = "Line"+i+"Rep\n"+
        "        STA WSYNC\n"+
        "        DEY\n"+
        "        BNE Line"+i+"Rep\n"+
        "        LDY #"+yValue+"\n"+
        "        INX\n"+
        "        CPX #8\n"+
        "        BNE Line"+i+"\n"+
        "        LDX #0\n";
        if(cCount==0) edit.editArea.append(tempString);
        cCount = cCount +1;
        if(cCount==8){
          cCount = 0;
          countUp = countUp +1;
        }
      }
    }

//End and Overscan
    tempString = "End\n"+
"        LDA #2\n"+
"        STA WSYNC\n"+
"        STA VBLANK\n"+
"        LDY #30\n"+
"OverScan\n"+
"        STA WSYNC\n"+
"        DEY\n"+
"        BNE OverScan\n"+
//    LDA #$B6
//    STA COLUPF
"        JMP MainLoop\n";
    edit.editArea.append(tempString);


//closing up shop
//    tempString = "    org $FF00\n\n";
//    edit.editArea.append(tempString);

    int topVec = 3;
//    if((testDataGps != 0) && (lines_ints > 192)) dataG = dataG + 1;
      for(int i=0;i<dataG;i++){
        Vector pf0LTVec = (Vector)tempVecL0.get(i);
        int pf0LInt = pf0LTVec.size();
        tempString = "pf0DataL_"+i+"\n";
        edit.editArea.append(tempString);
        tempString = "        .byte $"+ (String)pf0LTVec.get(0);
        for(int j=1;j<pf0LInt;j++){
          tempString = tempString + ",$"+(String)pf0LTVec.get(j);
        }
        tempString = tempString + "\n";
        edit.editArea.append(tempString);
      }
      tempString = ";-----pf0 Left End\n\n\n";
      edit.editArea.append(tempString);
//insert pf0 right side here
      if(this.asymmetrical.isSelected()){
        for(int i=0;i<dataG;i++){
          Vector pf0RTVec = (Vector)tempVecR0.get(i);
          int pf0RInt = pf0RTVec.size();
          tempString = "pf0DataR_"+i+"\n";
          edit.editArea.append(tempString);
          tempString = "        .byte $"+ (String)pf0RTVec.get(0);
          for(int j=1;j<pf0RInt;j++){
            tempString = tempString + ",$"+(String)pf0RTVec.get(j);
          }
          tempString = tempString + "\n";
          edit.editArea.append(tempString);
        }
        tempString = ";-----pf0 Right End\n\n\n";
        edit.editArea.append(tempString);
      }
//pf1 begin
      for(int i=0;i<dataG;i++){
        Vector pf1LTVec = (Vector)tempVecL1.get(i);
        int pf1LInt = pf1LTVec.size();
        tempString = "pf1DataL_"+i+"\n";
        edit.editArea.append(tempString);
        tempString = "        .byte $"+ (String)pf1LTVec.get(0);
        for(int j=1;j<pf1LInt;j++){
          tempString = tempString + ",$"+(String)pf1LTVec.get(j);
        }
        tempString = tempString + "\n";
        edit.editArea.append(tempString);
      }
      tempString = ";-----pf1 Left End\n\n\n";
      edit.editArea.append(tempString);
//insert pf1 right side here
      if(this.asymmetrical.isSelected()){
        for(int i=0;i<dataG;i++){
          Vector pf1RTVec = (Vector)tempVecR1.get(i);
          int pf1RInt = pf1RTVec.size();
          tempString = "pf1DataR_"+i+"\n";
          edit.editArea.append(tempString);
          tempString = "        .byte $"+ (String)pf1RTVec.get(0);
          for(int j=1;j<pf1RInt;j++){
            tempString = tempString + ",$"+(String)pf1RTVec.get(j);
          }
          tempString = tempString + "\n";
          edit.editArea.append(tempString);
        }
        tempString = ";-----pf1 Right End\n\n\n";
        edit.editArea.append(tempString);
      }
//pf2 begin
      for(int i=0;i<dataG;i++){
        Vector pf2LTVec = (Vector)tempVecL2.get(i);
        int pf2LInt = pf2LTVec.size();
        tempString = "pf2DataL_"+i+"\n";
        edit.editArea.append(tempString);
        tempString = "        .byte $"+ (String)pf2LTVec.get(0);
        for(int j=1;j<pf2LInt;j++){
          tempString = tempString + ",$"+(String)pf2LTVec.get(j);
        }
        tempString = tempString + "\n";
        edit.editArea.append(tempString);
      }
      tempString = ";-----pf2 Left End\n\n\n";
      edit.editArea.append(tempString);

//insert pf2 right side here
      if(this.asymmetrical.isSelected()){
        for(int i=0;i<dataG;i++){
          Vector pf2RTVec = (Vector)tempVecR2.get(i);
          int pf2RInt = pf2RTVec.size();
          tempString = "pf2DataR_"+i+"\n";
          edit.editArea.append(tempString);
          tempString = "        .byte $"+ (String)pf2RTVec.get(0);
          for(int j=1;j<pf2RInt;j++){
            tempString = tempString + ",$"+(String)pf2RTVec.get(j);
          }
          tempString = tempString + "\n";
          edit.editArea.append(tempString);
        }
        tempString = ";-----pf2 Right End\n\n\n";
        edit.editArea.append(tempString);
      }

    tempString = "    org $FFFC\n"+
"    .word Start\n"+
"    .word Start";
    edit.editArea.append(tempString);
//\n"+
//end controlled dump
  }

  public void doRawDump(){
//    dp.opTA
    int exportNum = leftScnLine.size();
    String leftStr = "left_pf";
    String rightStr = "right_pf";
    String n = "\n";
    String b = "  .byte ";
    String lTString = "";
    String rTString = "";
    if(exportNum > 0){
//Left Pf0 Dump
      dp.opTA.append(leftStr+"0"+n);
      for(int i=0;i<exportNum;i++){
        PrtScnLine lPrt = (PrtScnLine)leftScnLine.get(""+i);
        lTString = lTString + b;
        String pf0ST = "";
        for(int j=0;j < 4;j++){
          if(lPrt.pf0_box[j].isSelected()){
            pf0ST = pf0ST + "1";
          }else{
            pf0ST = pf0ST + "0";
          }
        }
        lTString = lTString + pf0ST + "0000" + ";" + n;
        dp.opTA.append(lTString);
        lTString = "";
      }
//Right PF0 Dump
      if(asymmetrical.isSelected()){
        dp.opTA.append(rightStr+"0"+n);
        for(int i=0;i<exportNum;i++){
          PrtScnLine rPrt = (PrtScnLine)rightScnLine.get(""+i);
          rTString = rTString + b;
          String pf0ST = "";
          for(int j=0;j < 4;j++){
            if(rPrt.pf0_box[j].isSelected()){
              pf0ST = pf0ST + "1";
            }else{
              pf0ST = pf0ST + "0";
            }
          }
          rTString = rTString + "0000" + pf0ST + ";" + n;
          dp.opTA.append(rTString);
          rTString = "";
        }

      }
      //end pf0

//Left Pf1 Dump
      dp.opTA.append(leftStr+"1"+n);
      for(int i=0;i<exportNum;i++){
        PrtScnLine lPrt = (PrtScnLine)leftScnLine.get(""+i);
        lTString = lTString + b;
        String pf1ST = "";
        for(int j=0;j < 8;j++){
          if(lPrt.pf1_box[j].isSelected()){
            pf1ST = "1" + pf1ST;
          }else{
            pf1ST = "0" + pf1ST;
          }
        }
        lTString = lTString + pf1ST + ";" + n;
        dp.opTA.append(lTString);
        lTString = "";
      }
//Right PF1 Dump
      if(asymmetrical.isSelected()){
        dp.opTA.append(rightStr+"1"+n);
        for(int i=0;i<exportNum;i++){
          PrtScnLine rPrt = (PrtScnLine)rightScnLine.get(""+i);
          rTString = rTString + b;
          String pf1ST = "";
          for(int j=0;j < 8;j++){
            if(rPrt.pf1_box[j].isSelected()){
              pf1ST = "1" + pf1ST;
            }else{
              pf1ST = "0" + pf1ST;
            }
          }
          rTString = rTString + pf1ST + ";" + n;
          dp.opTA.append(rTString);
          rTString = "";
        }

      }
      //end pf1

//Left Pf2 Dump
      dp.opTA.append(leftStr+"2"+n);
      for(int i=0;i<exportNum;i++){
        PrtScnLine lPrt = (PrtScnLine)leftScnLine.get(""+i);
        lTString = lTString + b;
        String pf2ST = "";
        for(int j=0;j < 8;j++){
          if(lPrt.pf2_box[j].isSelected()){
            pf2ST = pf2ST + "1";
          }else{
            pf2ST = pf2ST + "0";
          }
        }
        lTString = lTString + pf2ST + ";" + n;
        dp.opTA.append(lTString);
        lTString = "";
      }
//Right PF2 Dump
      if(asymmetrical.isSelected()){
        dp.opTA.append(rightStr+"2"+n);
        for(int i=0;i<exportNum;i++){
          PrtScnLine rPrt = (PrtScnLine)rightScnLine.get(""+i);
          rTString = rTString + b;
          String pf2ST = "";
          for(int j=0;j < 8;j++){
            if(rPrt.pf2_box[j].isSelected()){
              pf2ST = pf2ST + "1";
            }else{
              pf2ST = pf2ST + "0";
            }
          }
          rTString = rTString + pf2ST + ";" + n;
          dp.opTA.append(rTString);
          rTString = "";
        }

      }
      //end pf2

    }
  }
//  private void extractSelf(){
//    dp.pfHash.remove(this.getTitle());
//  }
  private void killwindow(){
    this.setVisible(false);
    this.dispose();
  }
//  protected void processWindowEvent(WindowEvent e) {
//    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
//      extractSelf();
//    }
//  }
}