package atarigamer;

/**
 * <p>Title: Atari Gamer</p>
 * <p>Description: Atari Video Game Console Development Environment</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Atari Aaron</p>
 * @author Aaron.Bergstrom@ndsu.nodak.edu
 * @version 1.0
 */

import java.lang.*;
import java.io.*;

public class PThread extends Thread{
  WebBrowser wb;
  public PThread(WebBrowser wb) {
    this.wb = wb;
  }
  public void run(){
    try{
      wb.jep.setPage(wb.loadURL);
    }catch(IOException e){}
  }
}
