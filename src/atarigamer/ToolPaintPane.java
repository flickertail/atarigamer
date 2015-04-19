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
import java.awt.Cursor;
import java.awt.geom.*;
import java.util.*;

public class ToolPaintPane extends JComponent{
  protected DevPF dpf;
  protected boolean tool = true;
  protected int drawType = 0;
  protected Cursor defCursor = new Cursor(Cursor.DEFAULT_CURSOR);
  protected Cursor rectangleCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
  protected Cursor hotCursor = rectangleCursor;
  protected Cursor roundedCursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().createImage(PaintField.class.getResource("roundcur.gif")),new Point(17,17),"Line");
  protected Cursor lineCursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().createImage(PaintField.class.getResource("linecur.gif")),new Point(17,17),"Line");
  protected Cursor pixelCursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().createImage(PaintField.class.getResource("pixelcur.gif")),new Point(17,17),"Pixel");
  protected Cursor ellipseCursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().createImage(PaintField.class.getResource("ellipsecur.gif")),new Point(17,17),"Ellipse");
  protected Cursor eraseCursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().createImage(PaintField.class.getResource("erasecur.gif")),new Point(17,17),"Erase");
  protected Cursor zoomCursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().createImage(PaintField.class.getResource("zoomcur.gif")),new Point(17,17),"Zoom");
  protected Cursor textCursor = new Cursor(Cursor.TEXT_CURSOR);
  protected Cursor moveCursor = new Cursor(Cursor.HAND_CURSOR);
  protected Cursor fillCursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().createImage(PaintField.class.getResource("fillcur.gif")),new Point(17,17),"Fill");
  protected boolean isAlt = false;
  public ToolPaintPane(DevPF dpf) {
    this.dpf = dpf;
  }
  public void paint(Graphics g){
    Graphics2D g2D = (Graphics2D)g;
    if(dpf.leftScnLine.size()!=0){
      if(tool){
        g2D.setPaint(Color.lightGray);
        Point st = dpf.startPoint;
        Point cr = dpf.currentPoint;
        if(dpf.currentPoint.x > 319)dpf.currentPoint.x = 319;
        if(dpf.currentPoint.y > dpf.lines_ints)dpf.currentPoint.y = dpf.lines_ints-1;
        if(dpf.currentPoint.x < 0)dpf.currentPoint.x = 0;
        if(dpf.currentPoint.y < 0)dpf.currentPoint.y = 0;
        switch(dpf.drawType){
          case 7://Draw Rectangle
            Rectangle2D.Float box = dpf.makeRec(st,cr);
            g2D.draw(box);
            break;
          case 3:
            Line2D.Float line = new Line2D.Float();
            line.setLine(st,cr);
            g2D.draw(line);
            break;
          case 1:
            Vector dpVector = dpf.tempVector;
            int dpV = dpVector.size();
            Rectangle2D.Float tR2 = (Rectangle2D.Float)dpVector.get(0);
            Point lastP = new Point();
            lastP.x = (int)tR2.x;
            lastP.y = (int)tR2.y;
            for(int j=0;j<dpV;j++){
              Rectangle2D.Float tEnd = (Rectangle2D.Float)dpVector.get(j);
              Point lastN = new Point();
              lastN.x = (int)tEnd.x;
              lastN.y = (int)tEnd.y;
              Line2D.Float tLine2D = new Line2D.Float();
              tLine2D.setLine(lastP,lastN);
              g2D.draw(tLine2D);
              lastP = lastN;
            }
            break;
          case 8:
            Ellipse2D.Float ellipse = new Ellipse2D.Float();
            if(st.x < cr.x & st.y < cr.y ){
              ellipse.setFrame(st.x,st.y,cr.x-st.x,cr.y-st.y);
            }
            if(st.x > cr.x & st.y > cr.y ){
              ellipse.setFrame(cr.x,cr.y,st.x-cr.x,st.y-cr.y);
            }
            if(st.x < cr.x & st.y > cr.y ){
              ellipse.setFrame(st.x,cr.y,cr.x-st.x,st.y-cr.y);
            }
            if(st.x > cr.x & st.y < cr.y ){
              ellipse.setFrame(cr.x,st.y,st.x-cr.x,cr.y-st.y);
            }
            g2D.draw(ellipse);
            break;
          case 4://text
            break;
          case 5://eraser
            break;
          case 6:
            break;
          case 0:
            Vector dpVector2 = dpf.tempVector;
            int dpV2 = dpVector2.size();
            Rectangle2D.Float tR2b = (Rectangle2D.Float)dpVector2.get(0);
            Point lastP2 = new Point();
            lastP2.x = (int)tR2b.x;
            lastP2.y = (int)tR2b.y;
            for(int j=0;j<dpV2;j++){
              Rectangle2D.Float tEnd2 = (Rectangle2D.Float)dpVector2.get(j);
              Point lastN2 = new Point();
              lastN2.x = (int)tEnd2.x;
              lastN2.y = (int)tEnd2.y;
              Line2D.Float tLine2Db = new Line2D.Float();
              tLine2Db.setLine(lastP2,lastN2);
              g2D.draw(tLine2Db);
              lastP2 = lastN2;
            }
            break;
          case 9:
            if(isAlt){
              Rectangle2D.Float ebox = dpf.makeRec(st,cr);
              g2D.draw(ebox);
            }
            break;
          default:
            break;
        }
      }
    }
  }
  public void setHotCursor(int drawType){
    switch(drawType){
      case 0:
        hotCursor = moveCursor;
        break;
      case 1:
        hotCursor = pixelCursor;
        break;
      case 2:
        hotCursor = zoomCursor;
        break;
      case 3:
        hotCursor = lineCursor;
        break;
      case 4:
        hotCursor = fillCursor;
        break;
      case 5:
        hotCursor = rectangleCursor;
        break;
      case 6:
//        hotCursor = zoomCursor;
        hotCursor = textCursor;
        break;
      case 7:
        hotCursor = roundedCursor;
        break;
      case 8:
        hotCursor = ellipseCursor;
        break;
      default:
        hotCursor = moveCursor;
        break;
    }
  }
}
