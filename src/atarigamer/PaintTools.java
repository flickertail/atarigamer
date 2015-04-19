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
import javax.swing.tree.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class PaintTools extends JInternalFrame{

  private DevProject dp;
  private Toolkit tk = Toolkit.getDefaultToolkit();

  /*Buttons that select the proper paint tool
   *Move/Pixel
   *Zoom/Line
   *Fill/Rectangle
   *Text/Rounded Rectangle
   *Color/Ellipse
  */
  private JButton[][] ptb = new JButton[5][2];
  private Color cColor = Color.BLACK;

  protected final int MOVE = 0;
  protected final int PIXEL = 1;
  protected final int ZOOM = 2;
  protected final int LINE = 3;
  protected final int FILL = 4;
  protected final int RECTANGLE = 5;
  protected final int TEXT = 6;
  protected final int ROUNDED = 7;
  protected final int ELLIPSE = 8;
  protected final int ERASE = 9;

  private GridBagConstraints gbc = new GridBagConstraints();
  private GridBagLayout gl = new GridBagLayout();
  private int drawType = MOVE;

  public PaintTools(DevProject dp) {
    this.getContentPane().setLayout(gl);
    this.setBackground(Color.lightGray);
    this.setForeground(Color.darkGray);
    this.dp = dp;
    this.setSize(80,200);
    this.setClosable(true);
    this.setTitle(" Tools");
    Dimension nd = dp.grd.pToolsLoc;
    this.setFrameIcon(new ImageIcon(PaintTools.class.getResource("ag_fi.gif")));
    this.setLocation(nd.width, nd.height);
    setButtons();
    overRideClose();
    this.dp.jdp.add(this);
    this.setVisible(true);
  }

  private void overRideClose(){
    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    this.addInternalFrameListener(new InternalFrameAdapter(){
      public void internalFrameClosing(InternalFrameEvent e){
        setVisible(false);
      }
    });
  }

  private void setButtons(){
    //Move Paint Tool Button
    ptb[0][0] = new JButton(new ImageIcon(tk.createImage(PaintTools.class.getResource("hand.gif"))));
    ptb[0][0].setBorder(new EmptyBorder(0,0,0,0));

    //Pixel Paint Tool Button
    ptb[0][1] = new JButton(new ImageIcon(tk.createImage(PaintTools.class.getResource("pixel.gif"))));
    ptb[0][1].setBorder(new EmptyBorder(0,0,0,0));

    //Zoom Paint Tool Button
    ptb[1][0] = new JButton(new ImageIcon(tk.createImage(PaintTools.class.getResource("zoombut.gif"))));
    ptb[1][0].setBorder(new EmptyBorder(0,0,0,0));

    //Line Paint Tool Button
    ptb[1][1] = new JButton(new ImageIcon(tk.createImage(PaintTools.class.getResource("line.gif"))));
    ptb[1][1].setBorder(new EmptyBorder(0,0,0,0));

    //Fill Paint Tool Button
    ptb[2][0] = new JButton(new ImageIcon(tk.createImage(PaintTools.class.getResource("fill.gif"))));
    ptb[2][0].setBorder(new EmptyBorder(0,0,0,0));

    //Rectangle Paint Tool Button
    ptb[2][1] = new JButton(new ImageIcon(tk.createImage(PaintTools.class.getResource("box.gif"))));
    ptb[2][1].setBorder(new EmptyBorder(0,0,0,0));

    //Text Paint Tool Button
    ptb[3][0] = new JButton(new ImageIcon(tk.createImage(PaintTools.class.getResource("textbut.gif"))));
    ptb[3][0].setBorder(new EmptyBorder(0,0,0,0));

    //Rounded Rectangle Button
    ptb[3][1] = new JButton(new ImageIcon(tk.createImage(PaintTools.class.getResource("round.gif"))));
    ptb[3][1].setBorder(new EmptyBorder(0,0,0,0));

    //Current Draw Color Button
    ptb[4][0] = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().createImage(PaintTools.class.getResource("colorblank.png"))));
    ptb[4][0].setBorder(new EmptyBorder(0,0,0,0));
    ptb[4][0].setContentAreaFilled(false);
    ptb[4][0].setOpaque(true);
    ptb[4][0].setBackground(cColor);

    //Ellipse Paint Tool Button
    ptb[4][1] = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().createImage(PaintTools.class.getResource("ellipsebut.gif"))));
    ptb[4][1].setBorder(new EmptyBorder(0,0,0,0));


    //-----------------------
    //New Area
    //Setting Up GridBagLayout
    gbc.anchor = gbc.NORTHWEST;

    //One/Left
    gbc.gridy=0;
    gbc.gridx=0;
    gl.setConstraints(ptb[0][0],gbc);
    this.getContentPane().add(ptb[0][0]);

    //One/Right
    gbc.gridx=1;
    gl.setConstraints(ptb[0][1],gbc);
    this.getContentPane().add(ptb[0][1]);

    //Two/Left
    gbc.gridy=1;
    gbc.gridx=0;
    gl.setConstraints(ptb[1][0],gbc);
    this.getContentPane().add(ptb[1][0]);

    //Two/Right
    gbc.gridx=1;
    gl.setConstraints(ptb[1][1],gbc);
    this.getContentPane().add(ptb[1][1]);

    //Three/Left
    gbc.gridy=2;
    gbc.gridx=0;
    gl.setConstraints(ptb[2][0],gbc);
    this.getContentPane().add(ptb[2][0]);

    //Three/Right
    gbc.gridx=1;
    gl.setConstraints(ptb[2][1],gbc);
    this.getContentPane().add(ptb[2][1]);

    //Four/Left
    gbc.gridy=3;
    gbc.gridx=0;
    gl.setConstraints(ptb[3][0],gbc);
    this.getContentPane().add(ptb[3][0]);

    //Four/Right
    gbc.gridx=1;
    gl.setConstraints(ptb[3][1],gbc);
    this.getContentPane().add(ptb[3][1]);

    //Five/Left
    gbc.gridy=4;
    gbc.gridx=0;
    gl.setConstraints(ptb[4][0],gbc);
    this.getContentPane().add(ptb[4][0]);

    //Five/Right
    gbc.gridx=1;
    gl.setConstraints(ptb[4][1],gbc);
    this.getContentPane().add(ptb[4][1]);


    //-----------------------
    //New Area
    //Setting Up Button ActionListeners

    //Sets The Draw Utilities to Move Mode
    ptb[0][0].addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        setDrawUtil(MOVE);
      }
    });

    //Sets The Draw Utilities to Pixel Mode
    ptb[0][1].addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        setDrawUtil(PIXEL);
      }
    });

    //Sets The Draw Utilities to Zoom Mode
    ptb[1][0].addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        setDrawUtil(ZOOM);
      }
    });

    //Sets The Draw Utilities to Line Mode
    ptb[1][1].addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        setDrawUtil(LINE);
      }
    });

    //Sets The Draw Utilities to Fill Mode
    ptb[2][0].addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        setDrawUtil(FILL);
      }
    });

    //Sets The Draw Utilities to Rectangle Mode
    ptb[2][1].addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        setDrawUtil(RECTANGLE);
      }
    });

    //Sets The Draw Utilities to Text Mode
    ptb[3][0].addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        setDrawUtil(TEXT);
      }
    });

    //Sets The Draw Utilities to Rounded Rectangle Mode
    ptb[3][1].addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        setDrawUtil(ROUNDED);
      }
    });

    //Request that an color update be conducted
    ptb[4][0].addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        requestColorNotification();
      }
    });

    //Sets The Draw Utilities to Ellipse Mode
    ptb[4][1].addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        setDrawUtil(ELLIPSE);
      }
    });
  }

  //Requests that the CC2600 Color Chooser Notify Paint Tools
  //The next time a color has been chosen
  public void requestColorNotification(){
	  dp.cc.setVisible(true);
	  dp.cc.setNewTitle();
	  dp.cc.revalidate();
  }

  public void setCurrentColor(Color c){
    cColor = c;
    System.out.println(c.toString());
    ptb[4][0].setBackground(cColor);
    ptb[4][0].repaint();
  }

  public void setDrawUtil(int drawType){
    updateEditors(drawType);
  }
  public void updateEditors(int drawType){
    String typeTemp = "";
    switch(drawType){
      case MOVE:
        typeTemp = "Move - Deactivated";
        break;
      case PIXEL:
        typeTemp = "Pixel - Deactivated";
        break;
      case ZOOM:
        typeTemp = "Zoom";
        break;
      case 3:
        typeTemp = "Line - Deactivated";
        break;
      case 4:
        typeTemp = "Fill - Deactivated";
        break;
      case 5:
        typeTemp = "Rectangle - Deactivated";
        break;
      case 6:
        typeTemp = "Text - Deactivated";
        break;
      case 7:
        typeTemp = "Rounded Rectangle - Deactivated";
        break;
      case 8:
        typeTemp = "Ellipse - Deactivated";
        break;
      default:
        typeTemp = "Error - no tool selected: "+ drawType;
        break;
    }
    this.drawType = drawType;
    dp.opTA.append("Paint Tool: "+typeTemp+"\n");
    frameUpdates();
  }
  public void frameUpdates(){
    Component[] co = dp.jdp.getComponents();
    int coLength = co.length;
    for(int i=0;i<coLength;i++){
      try{
        SpriteEditor se = (SpriteEditor)co[i];
        se.setDrawType(drawType);
      }catch(ClassCastException cce1){}
    }
  }

}