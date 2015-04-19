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
import javax.swing.JTextArea;
import javax.swing.text.Document;

public class Grabber {

    private static PrintStream stdout;
    private JTextArea jta;
    private Document doc;
    private Thread thread;

    public Grabber(JTextArea jta) {
        if(jta == null)
            throw new IllegalArgumentException("null");
        this.jta = jta;
        doc = jta.getDocument();
        if(stdout == null)
            stdout = System.out;
    }

    public synchronized void grab() {

        if(thread != null)
            return;

        try {
            PipedOutputStream pos = new PipedOutputStream();
            final PipedInputStream pis = new PipedInputStream(pos);
            final PrintStream redirectedStdout = new PrintStream(pos);
            System.setOut(redirectedStdout);

            thread = new Thread() {
                public void run() {
                    try {
                        int blockSize = 1024;
                        byte[] block = new byte[blockSize];
                        int bytesRead = -1;
                        String writeString = "";
                        while((bytesRead = pis.read(block)) > 0){
                          String tempString = new String(block,0,bytesRead);
                          writeString = writeString + tempString;
//                            doc.insertString(
//                                doc.getLength(),
//                                new String(block, 0, bytesRead),
//                                null
//                        );
                        }
                        jta.append(writeString);
                    }
                    catch(Exception x) {}
                    finally {
                        try {
                            pis.close();
                        }
                        catch(Exception x){x.printStackTrace();}
                        try {
                            redirectedStdout.close();
                        }
                        catch(Exception x){x.printStackTrace();}
                        System.setOut(stdout);
                    }
                }
            };
            thread.start();
        }
        catch(Exception x) {
            x.printStackTrace();
        }
    }

    public synchronized void reset() {
        if(thread == null)
            return;
        thread.interrupt();
        try {
            thread.join();
        }
        catch(InterruptedException iX) {}
        thread = null;
    }
}
