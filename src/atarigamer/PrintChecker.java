package atarigamer;



/**
 * <p>Title: Atari Gamer</p>
 * <p>Description: Atari Video Game Console Development Environment</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Atari Aaron</p>
 * @author Aaron.Bergstrom@ndsu.nodak.edu
 * @version 1.0
 */

public class PrintChecker extends Thread{
  private long awhile = 100L;
  private boolean isChecking = true;
  private boolean isVisible = false;
  private PosPanel pp;
  public PrintChecker(PosPanel pp) {
    this.pp = pp;
  }

  public void run(){
    try{
      while (!pp.isVisible()) {
        sleep(awhile);
      }
    }catch(InterruptedException ie){
      pp.pea.textBottom = pp.pea.getCaret().getMagicCaretPosition().y;

    }
  }
}