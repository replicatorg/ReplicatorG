package replicatorg.app.ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class GcodeSelectWindow  
{
	public static File lastdir;
	public static File go(File f)
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(f);
		JFrame chooseFramer = new JFrame("Find Gcode...");
		// Note: source for ExampleFileFilter can be found in FileChooserDemo,
		// under the demo/jfc directory in the Java 2 SDK, Standard Edition.
		GcodeFileFilter filter = new GcodeFileFilter();
		chooser.setFileFilter(filter);
		chooser.showOpenDialog(chooseFramer);
		File fr = chooser.getSelectedFile();
		if(fr != null)
			lastdir = fr.getParentFile();
		return fr;
	}
	public static File go()
	{
		JFileChooser chooser = new JFileChooser();
		if(lastdir != null)
		{
		chooser.setCurrentDirectory(lastdir);
		}
		JFrame chooseFramer = new JFrame("Find Gcode...");
		// Note: source for ExampleFileFilter can be found in FileChooserDemo,
		// under the demo/jfc directory in the Java 2 SDK, Standard Edition.
		GcodeFileFilter filter = new GcodeFileFilter();
		chooser.setFileFilter(filter);
		chooser.showOpenDialog(chooseFramer);
		File f = chooser.getSelectedFile();
		if(f != null)
			lastdir = f.getParentFile();
		return f;
	}
	public static String goString()
	{
		File result = go();
		if(result != null)
			return result.getAbsolutePath();
		return null;
	}
	public static String goString(File f)
	{
		File result = go(f);
		if(result != null)
			return result.getAbsolutePath();
		return null;
	}
}
