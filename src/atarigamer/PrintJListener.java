package atarigamer;
/** PrintJListener.java
  * Date: May 2002
  * Author: Rajesh Ramchandani
  *         Ck Prasad
  */

import java.io.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import javax.print.event.*;

public class PrintJListener implements PrintJobListener {

        static PrintJobEvent spje = null;
        static int setype = 0;

        public void printDataTransferCompleted(PrintJobEvent pje) {
                int etype = pje.getPrintEventType();
                System.out.println("Data xfer completed !");
                this.setype = etype;
                try {
                        Thread.sleep(100);
                } catch(Exception e) {
                System.out.println(e.getMessage());
                }
        }

        public void printJobCompleted(PrintJobEvent pje) {
                System.out.println("print Job completed successfully" + pje);
                this.spje = pje;
        }

        public void printJobFailed(PrintJobEvent pje) {
                System.out.println("print job failed" + pje);
                this.spje = pje;
        }

        public void printJobNoMoreEvents(PrintJobEvent pje) {
                System.out.println("All events DONE");
                this.spje = pje;
        }

        public void printJobRequiresAttention(PrintJobEvent pje) {
                System.out.println("Print job requires attention " + pje);
                this.spje = pje;
        }

        public void printJobCanceled(PrintJobEvent pje) {
                System.out.println("Print job cancelled ");
        }

        public PrintJobEvent getSpje() {
//		System.out.println("Inside PJL spje=", + spje);
                return spje;
        }

        public int getSetype() {
                String str = Integer.toString(setype);
                System.out.println("Inside PJL setype:");
                System.out.println(str);
                return setype;
        }
}

