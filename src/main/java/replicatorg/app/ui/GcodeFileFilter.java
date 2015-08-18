package replicatorg.app.ui;

import java.io.File;

class GcodeFileFilter extends javax.swing.filechooser.FileFilter {

  private static final String gcode = "gcode";
  private static final String stl = "stl";
  public boolean accept(File f)
  {
    //alexpong
    String extension = "";
    String s = f.getName();
    if(s.contains("."))
    {
      int i = s.lastIndexOf('.');
      if (i > 0 &&  i < s.length() - 1) {
        extension = s.substring(i+1).toLowerCase();
      }
      if(extension.equals(gcode) || extension.equals(stl))
      {
        return true;
      }
      return false;
    }
    return true;

  }
  @Override
  public String getDescription() {
    return "gcode";
  }

}
