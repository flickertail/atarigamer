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
import java.net.*;
import javax.swing.*;

public class DThread extends Thread{
  WebBrowser wb;
  FileWriter fw;
//  BufferedWriter bw;
  File cacheFile;
  int lc;

  public DThread(WebBrowser wb) {
    this.wb = wb;
    this.lc = wb.lc;
    try{
      cacheFile = new File("cache.html");
      cacheFile.delete();
      fw = new FileWriter(cacheFile, true);
//      bw = new BufferedWriter(fw);
    }catch(IOException e){}
  }
  public void wrapUp(){
    switch(wb.lc){
      case 0:
        wb.nextStack.clear();
        break;
      case 1:
        if(wb.isLoaded){
          if(!wb.isReloading) wb.nextStack.clear();
        }else{
          JOptionPane.showMessageDialog(wb,"There is a problem loading the document that you have requested. Please, check the URL and try again.","Page Not Found Error",JOptionPane.OK_OPTION);
          if(!wb.isReloading){
            Object disObj = wb.backStack.pop();
          }
          wb.loadURL = wb.nurl;
        }
        break;
      case 2:
        if(wb.isLoaded) wb.nextStack.clear();
        break;
      case 3:
        if(wb.isLoaded){
          wb.backStack.add(wb.nurl);
        }else{
          wb.loadURL = wb.nurl;
          JOptionPane.showMessageDialog(wb,"Unable to load the requested URL.","Cannot Find Page",JOptionPane.OK_OPTION);
        }
        break;
      case 4:
        if (wb.isLoaded) {
          wb.nextStack.add(wb.nurl);
        }else {
          wb.loadURL = wb.nurl;
          JOptionPane.showMessageDialog(wb,"Unable to load the last URL. Page history will now be cleared.","Cannot Find Page",JOptionPane.OK_OPTION);
          wb.backStack.clear();
        }
        break;
      case 5:
        if (wb.isLoaded) {
          wb.backStack.add(wb.nurl);
        }
        else {
          wb.loadURL = wb.nurl;
          wb.urlText.setText(wb.loadURL.toExternalForm());
          wb.isReloading = true;
          wb.processURLTextInput();
          wb.isReloading = false;
          JOptionPane.showMessageDialog(wb,"Unable to load the last URL. Page history will now be cleared.","Cannot Find Page",JOptionPane.OK_OPTION);
        }

        break;
      default:
        break;
    }
    wb.urlText.setText(wb.loadURL.toString());
  }
  public void run(){
    boolean titleFound = false;
    String fString = wb.loadURL.getProtocol();
    wb.jep.setContentType("text/html");
    String titleString = " CGDK Web Browser - Untitled Document";
    if(!fString.startsWith("file")){
      try {
        String tString = "";
        String lString = "";
        int countDown = wb.fileL;
        try {
          wb.bfr = new BufferedReader(new InputStreamReader(wb.loadURL.openStream()));
        }catch (IOException ioe4) {}
        float fTick = (float) wb.fileL;
        fTick = fTick / 100;
        while (wb.isLoadingPage) {
          try {
            tString = wb.bfr.readLine();
            if(!titleFound){
              try {
                titleString = tString.substring(tString.indexOf("<title"));
                titleString = titleString.replaceAll("<title>","");
                titleString = titleString.replaceAll("</title>","");
                titleString = " CGDK Web Browser - "+titleString;
                System.out.println(titleString);
                titleFound = true;
              }
              catch (Exception imge) {}
            }
            int bnum = tString.getBytes().length;
            String bs = "\n";
            bnum = bnum + bs.getBytes().length;
            countDown = countDown - bnum;
            int progress = wb.fileL - countDown;
            float pg = (float) progress;
            pg = pg / fTick;
            pg = Math.round(pg);
            int setn = (int) pg;
            wb.pp.setAmount(setn);
            wb.repaint();
            lString = lString + tString;
          }catch (IOException ioe) {
            wb.pp.setAmount(0);
            wb.repaint();
            try {
              wb.bfr.close();
            }catch (IOException ioe2) {}
            wb.jep.setText(lString);
            wb.setTitle(titleString);
            wb.isLoaded = true;
            wrapUp();
            wb.isLoaded = false;
            wb.isLoadingPage = false;
          }catch (NullPointerException npe) {
            wb.pp.setAmount(0);
            wb.repaint();
            try {
              wb.bfr.close();
            }catch (IOException ioe2) {}
            System.out.println("Run1");
            System.out.println("Run2");
            wb.jep.setText(lString);
            wb.setTitle(titleString);
            System.out.println(lString);
            wb.isLoaded = true;
            wrapUp();
            wb.isLoaded = false;
            wb.isLoadingPage = false;
          }
          Thread.sleep(33L);
        }
      }catch (InterruptedException e) {
        wb.isLoaded = true;
        wrapUp();
      }
    }else{
      try{
        wb.jep.setPage(wb.loadURL);
        wb.isLoaded = true;
        wrapUp();
        wb.isLoaded = false;
      }catch(IOException ioe4){}
    }
  }
}