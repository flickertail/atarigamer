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
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SystemOutCapture extends FilterOutputStream {
   private SystemOutLogger logger;

   public SystemOutCapture(SystemOutLogger logger)   {
      super(System.out);
      this.logger=logger;
      System.setOut(new PrintStream(this));
   }

   public void write(byte[] b) throws IOException   {
      logger.logOutput(new String(b));
      out.write(b);
   }

   public void write(byte[] b,int off,int len) throws IOException   {
      logger.logOutput(new String(b,off,len));
      out.write(b,off,len);
   }

   public void write(int b) throws IOException   {
      byte[] barr={(byte)b};
      logger.logOutput(new String(barr));
      out.write(b);
   }
}
