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
import java.lang.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.beans.*;
import java.awt.*;
import java.awt.image.*;

public class CC2600 extends JInternalFrame{
  private int notifier = -1;
  protected Color[][] ntsc = new Color[16][8];
  protected Color[][] pal = new Color[16][8];
  protected Color[][] secam = new Color[16][8];
  protected int col = 0;
  protected int lum = 0;
  protected ImageIcon nii;
  protected ImageIcon pii;
  protected ImageIcon sii;
  protected Image ni;
  protected Image pi;
  protected Image si;
  protected int pixels[];
  protected JButton obutton = new JButton();
  protected int cf = 0;
  protected CC2600 cc;
  protected DevProject dp;
  protected JDesktopPane jdp;
  protected JPanel card;
  protected CJPanels[] cjp = new CJPanels[3];
  protected String[] tvType = {"NTSC","PAL","SECAM"};
  protected CardLayout cl = new CardLayout(0,0);
  protected PixelGrabber[] pgs = new PixelGrabber[3];
  protected JButton[] tvChoice = new JButton[3];
  protected JMenu options = new JMenu("Options");
  protected JMenuBar mbar = new JMenuBar();
  private ColorPalettes cp;
  
  public CC2600(DevProject dp) {
    this.cc = this;
    this.dp = dp;
    this.cp = dp.cp;
    this.jdp = this.dp.jdp;
//    createNTSCColors();
//    createPALColors();
//    createSECAMColors();
    setupFrame();
    loadPalettes();
  }

  protected void setupFrame(){
//    mbar.add(options);
//    this.setJMenuBar(mbar);
    this.setFrameIcon(new ImageIcon(ASMEditor.class.getResource("color_fi.gif")));
    this.setSize(276,194);
    this.setResizable(false);
    this.setIconifiable(true);
    this.setClosable(true);
    this.getContentPane().setLayout(new BorderLayout(1,1));
    JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));

    for(int i=0;i<3;i++){
      tvChoice[i] = new JButton();
      tvChoice[i].setBackground(new Color(204,153,153));
      tvChoice[i].setMargin(new Insets(0,0,0,0));
      tvChoice[i].setBorder(new javax.swing.border.LineBorder(Color.BLACK,1,true));
      menu.add(tvChoice[i]);
      menu.add(new JLabel("  "));
    }
    
    tvChoice[0].setText("   NTSC  ");
    tvChoice[1].setText("    PAL   ");
    tvChoice[2].setText("  SECAM  ");
    tvChoice[0].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        setColorType(0);
      }
    });

    tvChoice[1].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        setColorType(1);
      }
    });

    tvChoice[2].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        setColorType(2);
      }
    });

    this.getContentPane().add("North", menu);
    JLabel gs = new JLabel("Based on color charts compiled by Glenn Saunders");
    gs.setForeground(Color.BLACK);
    this.getContentPane().add("South",gs);
    setNewTitle();
    obutton.setIcon(this.getFrameIcon());
    obutton.setToolTipText(cc.getTitle());
    obutton.setMargin(new Insets(0,0,0,0));
    obutton.setBorderPainted(false);
    obutton.setForeground(Color.BLACK);
    obutton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        try{
          cc.setIcon(false);
        }catch(PropertyVetoException pve){};
        cc.setVisible(true);
        cc.revalidate();
      }
    });
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
        obutton.setToolTipText(cc.getTitle());
        cc.setVisible(false);
        dp.grd.icontool.add(obutton,2);
      }
      public void internalFrameClosed(InternalFrameEvent e){
      }
      public void internalFrameClosing(InternalFrameEvent e){
        cc.setVisible(false);
      }
      public void internalFrameOpened(InternalFrameEvent e){

      }
    });
/*
    jdp.add(this);

    JPanel info = new JPanel(new GridLayout(2,1,0,0));
    card = new JPanel(cl);
    cjp[0] = new CJPanels(ntsc,tvType[0],this);
    cjp[1] = new CJPanels(pal,tvType[1],this);
    cjp[2] = new CJPanels(secam,tvType[2],this);
    for(int i=0;i<3;i++){
      card.add(cjp[i].tp,tvType[i]);
    }
    info.add(card);
    this.getContentPane().add("Center",info);
*/
  }

  protected void setColorType(int ct){
//    this.cf = ct;
	dp.cPalette = ct;
	cp.setDrawImage(dp.cPalette);
    setNewTitle();
//    this.obutton.setToolTipText(this.getTitle());
//    cl.show(card,tvType[cf]);
  }

  protected void setNewTitle(){
    switch(dp.cPalette){
      case 1:
        this.setTitle("2600 Color Chooser - PAL");
        break;
      case 2:
        this.setTitle("2600 Color Chooser - SECAM");
        break;
      default:
        this.setTitle("2600 Color Chooser - NTSC");
        break;
    }
    //cp.repaint();
    revalidate();
  }

  protected void loadPalettes(){
    this.getContentPane().add("Center", cp);
    overRideClose();
    jdp.add(this);
    this.setVisible(false);
/*    nii = new ImageIcon(CC2600.class.getResource("ntsc.jpg"));
    pii = new ImageIcon(CC2600.class.getResource("pal.jpg"));
    sii = new ImageIcon(CC2600.class.getResource("secam.jpg"));
    ni = nii.getImage();
    pi = pii.getImage();
    si = sii.getImage();
*/
  }

private void overRideClose(){
	this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	this.addInternalFrameListener(new InternalFrameAdapter(){
		public void internalFrameClosing(InternalFrameEvent e){
			setVisible(false);
		}
	});
}

  protected void createNTSCColors(){
    pixels = new int[nii.getIconWidth() * nii.getIconHeight()];
    pgs[0] = new PixelGrabber(ni,0,0,nii.getIconWidth(),nii.getIconHeight(),pixels,0,nii.getIconWidth());
    try{
      pgs[0].grabPixels();
    }catch(InterruptedException ie){}

    col = 12;
    lum = 11;
    for(int i=0;i<8;i++){
      setColorLine(i,ntsc,ni);
    }
    lum = 11;
  }

  protected void createPALColors(){
    pixels = new int[pii.getIconWidth() * pii.getIconHeight()];
    pgs[1] = new PixelGrabber(pi,0,0,pii.getIconWidth(),pii.getIconHeight(),pixels,0,pii.getIconWidth());
    try{
      pgs[1].grabPixels();
    }catch(InterruptedException ie){}

    col = 12;
    lum = 11;
    for(int i=0;i<8;i++){
      setColorLine(i,pal,pi);
    }
    lum = 11;
  }

  protected void createSECAMColors(){
    pixels = new int[sii.getIconWidth() * sii.getIconHeight()];
    pgs[2] = new PixelGrabber(si,0,0,sii.getIconWidth(),sii.getIconHeight(),pixels,0,sii.getIconWidth());
    try{
      pgs[2].grabPixels();
    }catch(InterruptedException ie){}

    col = 12;
    lum = 11;
    for(int i=0;i<8;i++){
      setColorLine(i,secam,si);
    }
    lum = 11;
  }

  protected void setColorLine(int row, Color[][] palette, Image sImage){
    for(int i=0;i<16;i++){
      palette[i][row] = getPixelColor(lum,col,sImage);
      col = col+24;
    }
    col = 12;
    lum = lum+12;
  }
  protected Color getVCSColor(String value){
    String lumString = value.substring(0,1);
    String colString = value.substring(1);
    lumString = (String)cjp[cf].y.get(lumString);
    colString = (String)cjp[cf].x.get(colString);
    int lumInt = Integer.parseInt(lumString);
    int colInt = Integer.parseInt(colString);
    Color tColor = cjp[cf].getPixelColor(lumInt,colInt,cjp[cf].bi);
    return tColor;
  }

  public Color getPixelColor(int row, int column, Image sImage){
    int pixel = pixels[row * sImage.getWidth(this) + column];
    int alpha = (pixel >> 24) & 0xff;
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = pixel & 0xff;
    Color c = new Color(red, green, blue, alpha);
    return c;
  }

  public void setNotifier(int notifier){
    this.notifier = notifier;
  }

  public int getNotifier(){
    return notifier;
  }

}