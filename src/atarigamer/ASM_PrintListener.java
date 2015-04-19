package atarigamer;

/**
 * <p>Title: Atari Gamer</p>
 * <p>Description: Atari Video Game Console Development Environment</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Atari Aaron</p>
 * @author Aaron.Bergstrom@ndsu.nodak.edu
 * @version 1.0
 */
import java.io.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import javax.print.event.*;

public class ASM_PrintListener implements PrintServiceAttributeListener {
  public ASM_PrintListener() {
  }
  public void attributeUpdate(PrintServiceAttributeEvent e){
    PrintServiceAttributeSet psAttSet = e.getAttributes();
    Attribute[] nattr = psAttSet.toArray();
  }
}