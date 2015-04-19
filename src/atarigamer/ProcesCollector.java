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

public class ProcesCollector extends Thread implements Runnable{
  protected Process prc;
  protected GrDesktop grd;
//  protected BufferedInputStream br;
  protected BufferedReader br;
  protected boolean runIt = true;
  String tString = "";

  public ProcesCollector(Process prc, GrDesktop grd) {
    this.prc = prc;
    this.grd = grd;
    br = new BufferedReader(new InputStreamReader(prc.getInputStream()));
//    br = new BufferedInputStream(prc.getInputStream());
  }

  public void run(){
    try{
      while(runIt){
        Thread.sleep(100);
        tString = br.readLine();
        if(tString == null) {
          runIt = false;
        }else{
          tString = "\n" + tString;
          grd.devProject.opTA.append(tString);
          if (prc == null) {
            runIt = false;
          }
        }
      }
    }catch(InterruptedException e){
      try{
        br.close();
        runIt = false;
      }catch(IOException io2){}
      runIt = false;
    }catch(IOException io1){}
  }
}