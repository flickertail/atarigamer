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
import java.awt.geom.*;
import java.util.*;

public class PaintField extends JComponent{

protected DevPF dpf;
protected boolean left = true;
protected boolean ref = false;
protected boolean ass = false;
protected boolean back = true;
protected boolean pf = true;
protected boolean tool = true;
protected int drawType = 0;
  public PaintField(DevPF dpf) {
    this.dpf = dpf;
  }
  public void paint(Graphics g){
    Graphics2D g2D = (Graphics2D)g;
    if(dpf.leftScnLine.size()!=0){
      if(back){
        g2D.setPaint(dpf.bgColor);
        Point dpLoc = this.getLocation();
        Rectangle2D.Float bgp = new Rectangle2D.Float(dpLoc.x-80,dpLoc.y,dpLoc.x+399,dpLoc.y+dpf.lines_ints);
        g2D.fill(bgp);
        g2D.draw(bgp);
      }
      if(pf){
        dpf.clearAllCheckBoxes();
        int sht = dpf.shapeTable.size();
        int shtn = dpf.shapeTableNames.size();
        int shtt = dpf.shapeTypeTable.size();
        int shtr = dpf.shapeNode.getChildCount();
        int drawTech = 0;
        if(dpf.reflected.isSelected()) drawTech = 1;
        if(dpf.asymmetrical.isSelected()) drawTech = 2;
        int tPFLength = dpf.leftScnLine.size();
        int shapeCount = 0;
        drawType = 0;
        while(shapeCount < sht & sht != 0){
          String nameString = (String)dpf.stNames.get(shapeCount); //shapeTableNames.get(new Integer(shapeCount));
          Integer tmpInt = (Integer)dpf.shapeTypeTable.get(nameString);
          drawType = tmpInt.intValue();
          Object holdShape = new Object();
          switch(drawType){
            case 0:
              holdShape = dpf.shapeTable.get(nameString);
              dpf.setCBForBoxes((Rectangle2D.Float)holdShape);
              break;
            case 1:
              break;
            case 2:
              holdShape = dpf.shapeTable.get(nameString);
              Vector shapeVector = (Vector)holdShape;
              int svInt = shapeVector.size();
              for(int i=0;i<svInt;i++){
                dpf.setCBForBoxes((Rectangle2D.Float)shapeVector.get(i));
              }
              break;
            case 3:
              break;
            case 4:
              break;
            case 5:
              holdShape = dpf.shapeTable.get(nameString);
              dpf.removeCBForErase((Rectangle2D.Float)holdShape);
              break;
            case 6:
              break;
            case 7:
              holdShape = dpf.shapeTable.get(nameString);
              @SuppressWarnings("rawtypes")
			  Vector shapeVector2 = (Vector)holdShape;
              int svInt2 = shapeVector2.size();
              for(int i=0;i<svInt2;i++){
                dpf.removeCBForErase((Rectangle2D.Float)shapeVector2.get(i));
              }
              break;
            case 8:
              break;
            case 9:
              break;
            default:
              break;
          }
          shapeCount = shapeCount+1;
        }
        switch(drawTech){
          case 1:
//          drawReflected(drawPanel);
            for(int i=0;i<tPFLength;i++){
              PrtScnLine tpsl = (PrtScnLine)dpf.leftScnLine.get(""+i);
              Point startPt = new Point();
              Point stopPt = new Point();
              startPt.y = tpsl.startLine;
              stopPt.y = startPt.y + (tpsl.lineCount-1);
              Color tpslColor = dpf.pfDefColor;

//left pf0 draw
              if(!tpsl.isDefColor()) tpslColor = tpsl.pfColor;
              g2D.setPaint(tpslColor);
              for(int j=0; j<4;j++){
                if(tpsl.pf0_box[j].isSelected()){
                  Point value = dpf.getXValues(0,j,true,false);
                  startPt.x = value.x;
                  stopPt.x = value.y;
                  //drawScanPixel
                  Rectangle2D.Float pixDraw1 = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                  g2D.fill(pixDraw1);
                  g2D.draw(pixDraw1);
                  //end
                  value = dpf.getXValues(0,j,false,true);
                  startPt.x = value.x;
                  stopPt.x = value.y;
                  //drawScanPixel
                  Rectangle2D.Float pixDraw = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                  g2D.fill(pixDraw);
                  g2D.draw(pixDraw);
                  //end
                }
              }

              for(int k=0; k<8;k++){
                if(tpsl.pf1_box[k].isSelected()){
                  Point value = dpf.getXValues(1,k,true,false);
                  startPt.x = value.x;
                  stopPt.x = value.y;
                  //drawScanPixel
                  Rectangle2D.Float pixDraw = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                  g2D.fill(pixDraw);
                  g2D.draw(pixDraw);
                  //end
                  value = dpf.getXValues(1,k,false,true);
                  startPt.x = value.x;
                  stopPt.x = value.y;
                  //drawScanPixel
                  Rectangle2D.Float pixDraw2 = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                  g2D.fill(pixDraw2);
                  g2D.draw(pixDraw2);
                  //end
                }
              }
              for(int l=0; l<8;l++){
                if(tpsl.pf2_box[l].isSelected()){
                  Point value = dpf.getXValues(2,l,true,false);
                  startPt.x = value.x;
                  stopPt.x = value.y;
                  //drawScanPixel
                  Rectangle2D.Float pixDraw = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                  g2D.fill(pixDraw);
                  g2D.draw(pixDraw);
                  //end
                  value = dpf.getXValues(2,l,false,true);
                  startPt.x = value.x;
                  stopPt.x = value.y;
                  //drawScanPixel
                  Rectangle2D.Float pixDraw3 = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                  g2D.fill(pixDraw3);
                  g2D.draw(pixDraw3);
                  //end
                }
              }
            }
            break;
          case 2:
            //drawAssymetrical
              for(int i=0;i<tPFLength;i++){
                PrtScnLine tpsl = (PrtScnLine)dpf.leftScnLine.get(""+i);
                PrtScnLine tpsl2 = (PrtScnLine)dpf.rightScnLine.get(""+i);
                Point startPt = new Point();
                Point stopPt = new Point();
                startPt.y = tpsl.startLine;
                stopPt.y = startPt.y + (tpsl.lineCount-1);

                Point startPt2 = new Point();
                Point stopPt2 = new Point();
                startPt2.y = tpsl2.startLine;
                stopPt2.y = startPt2.y + (tpsl2.lineCount-1);

                Color tpslColor = dpf.pfDefColor;
                Color tpsl2Color = dpf.pfDefColor;
//left pf0 draw
                if(!tpsl.isDefColor()) tpslColor = tpsl.pfColor;
                if(!tpsl2.isDefColor()) tpsl2Color = tpsl2.pfColor;
                for(int j=0; j<4;j++){
                  g2D.setPaint(tpslColor);
                  if(tpsl.pf0_box[j].isSelected()){
                    Point value = dpf.getXValues(0,j,true,false);
                    startPt.x = value.x;
                    stopPt.x = value.y;
                    //drawScanPixel
                    Rectangle2D.Float pixDraw1 = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                    g2D.fill(pixDraw1);
                    g2D.draw(pixDraw1);
                    //end
                  }
                  g2D.setPaint(tpsl2Color);
                  if(tpsl2.pf0_box[j].isSelected()){
                    Point value2 = dpf.getXValues(0,j,false,false);
                    startPt2.x = value2.x;
                    stopPt2.x = value2.y;
                    //drawScanPixel
                    Rectangle2D.Float pixDraw = new Rectangle2D.Float(startPt2.x,startPt2.y,stopPt2.x-startPt2.x,stopPt2.y-startPt2.y);
                    g2D.fill(pixDraw);
                    g2D.draw(pixDraw);
                    //end
                  }
                }

                for(int k=0; k<8;k++){
                  g2D.setPaint(tpslColor);
                  if(tpsl.pf1_box[k].isSelected()){
                    Point value = dpf.getXValues(1,k,true,false);
                    startPt.x = value.x;
                    stopPt.x = value.y;
                    //drawScanPixel
                    Rectangle2D.Float pixDraw = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                    g2D.fill(pixDraw);
                    g2D.draw(pixDraw);
                    //end
                  }
                  g2D.setPaint(tpsl2Color);
                  if(tpsl2.pf1_box[k].isSelected()){
                    Point value2 = dpf.getXValues(1,k,false,false);
                    startPt2.x = value2.x;
                    stopPt2.x = value2.y;
                    //drawScanPixel
                    Rectangle2D.Float pixDraw2 = new Rectangle2D.Float(startPt2.x,startPt2.y,stopPt2.x-startPt2.x,stopPt2.y-startPt2.y);
                    g2D.fill(pixDraw2);
                    g2D.draw(pixDraw2);
                    //end
                  }
                }
                for(int l=0; l<8;l++){
                  g2D.setPaint(tpslColor);
                  if(tpsl.pf2_box[l].isSelected()){
                    Point value = dpf.getXValues(2,l,true,false);
                    startPt.x = value.x;
                    stopPt.x = value.y;
                    //drawScanPixel
                    Rectangle2D.Float pixDraw = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                    g2D.fill(pixDraw);
                    g2D.draw(pixDraw);
                    //end
                  }
                  g2D.setPaint(tpsl2Color);
                  if(tpsl2.pf2_box[l].isSelected()){
                    Point value = dpf.getXValues(2,l,false,false);
                    startPt2.x = value.x;
                    stopPt2.x = value.y;
                    //drawScanPixel
                    Rectangle2D.Float pixDraw3 = new Rectangle2D.Float(startPt2.x,startPt2.y,stopPt2.x-startPt2.x,stopPt2.y-startPt2.y);
                    g2D.fill(pixDraw3);
                    g2D.draw(pixDraw3);
                    //end
                  }
                }
              }
            break;
          default:
            for(int i=0;i<tPFLength;i++){
              PrtScnLine tpsl = (PrtScnLine)dpf.leftScnLine.get(""+i);
              Point startPt = new Point();
              Point stopPt = new Point();
              startPt.y = tpsl.startLine;
              stopPt.y = startPt.y + (tpsl.lineCount-1);
              Color tpslColor = dpf.pfDefColor;
              if(!tpsl.isDefColor()) tpslColor = tpsl.pfColor;
              g2D.setPaint(tpslColor);
              for(int j=0; j<4;j++){
                if(tpsl.pf0_box[j].isSelected()){
                  Point value = dpf.getXValues(0,j,true,false);
                  startPt.x = value.x;
                  stopPt.x = value.y;
                //drawScanPixel
                  Rectangle2D.Float pixDraw1 = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                  g2D.fill(pixDraw1);
                  g2D.draw(pixDraw1);
//end
                  value = dpf.getXValues(0,j,false,false);
                  startPt.x = value.x;
                  stopPt.x = value.y;
//drawScanPixel
                  Rectangle2D.Float pixDraw = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                  g2D.fill(pixDraw);
                  g2D.draw(pixDraw);
                }
//end
              }
              for(int k=0; k<8;k++){
                if(tpsl.pf1_box[k].isSelected()){
                  Point value = dpf.getXValues(1,k,true,false);
                  startPt.x = value.x;
                  stopPt.x = value.y;
                //drawScanPixel
                  Rectangle2D.Float pixDraw = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                  g2D.fill(pixDraw);
                  g2D.draw(pixDraw);
//end
                  value = dpf.getXValues(1,k,false,false);
                  startPt.x = value.x;
                  stopPt.x = value.y;
//drawScanPixel
                  Rectangle2D.Float pixDraw2 = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                  g2D.fill(pixDraw2);
                  g2D.draw(pixDraw2);
//end
                }
              }
              for(int l=0; l<8;l++){
                if(tpsl.pf2_box[l].isSelected()){
                  Point value = dpf.getXValues(2,l,true,false);
                  startPt.x = value.x;
                  stopPt.x = value.y;
//drawScanPixel
                  Rectangle2D.Float pixDraw = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                  g2D.fill(pixDraw);
                  g2D.draw(pixDraw);
//end
                  value = dpf.getXValues(2,l,false,false);
                  startPt.x = value.x;
                  stopPt.x = value.y;
//drawScanPixel
                  Rectangle2D.Float pixDraw3 = new Rectangle2D.Float(startPt.x,startPt.y,stopPt.x-startPt.x,stopPt.y-startPt.y);
                  g2D.fill(pixDraw3);
                  g2D.draw(pixDraw3);
                }
//end
              }
            }
            break;
        }
        //*/
      }
    }else{
      g2D.setPaint(dpf.bgColor);
      Point dpLoc = this.getLocation();
      Rectangle2D.Float bgp = new Rectangle2D.Float(dpLoc.x-80,dpLoc.y,dpLoc.x+399,dpLoc.y+192);
      g2D.fill(bgp);
      g2D.draw(bgp);
    }
  }
}
