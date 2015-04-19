package atarigamer;

/**
 * <p>Title: Atari Gamer</p>
 * <p>Description: Atari Video Game Console Development Environment</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Atari Aaron</p>
 * @author Aaron.Bergstrom@ndsu.nodak.edu
 * @version 1.0
 */

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ExtensionFilter extends FileFilter{
  private String description;
  private String extension;
  public ExtensionFilter(String ext, String descr) {
    extension = ext.toLowerCase();
    description = descr;
  }
  public boolean accept(File file){
    return (file.isDirectory() || file.getName().toLowerCase().endsWith(extension));
  }
  public String getDescription(){
    return description;
  }
}