package replicatorg.app.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import java.util.ArrayList;

/**
 * Buffer updates to the console and output them in batches. For info, see:
 * http://java.sun.com/products/jfc/tsc/articles/text/element_buffer and
 * http://javatechniques.com/public/java/docs/gui/jtextpane-speed-part2.html
 * appendString() is called from multiple threads, and insertAll from the swing
 * event thread, so they need to be synchronized
 */
class BufferedStyledDocument extends DefaultStyledDocument {
  ArrayList<ElementSpec> elements = new ArrayList<ElementSpec>();

  int maxLineLength, maxLineCount;

  int currentLineLength = 0;

  boolean needLineBreak = false;

  boolean hasAppendage = false;

  public BufferedStyledDocument(int maxLineLength, int maxLineCount) {
    this.maxLineLength = maxLineLength;
    this.maxLineCount = maxLineCount;
  }

  /** buffer a string for insertion at the end of the DefaultStyledDocument */
  public synchronized void appendString(String str, AttributeSet a) {
    // do this so that it's only updated when needed (otherwise console
    // updates every 250 ms when an app isn't even running.. see bug 180)
    hasAppendage = true;

    // process each line of the string
    while (str.length() > 0) {
      // newlines within an element have (almost) no effect, so we need to
      // replace them with proper paragraph breaks (start and end tags)
      if (needLineBreak || currentLineLength > maxLineLength) {
        elements.add(new ElementSpec(a, ElementSpec.EndTagType));
        elements.add(new ElementSpec(a, ElementSpec.StartTagType));
        currentLineLength = 0;
      }

      if (str.indexOf('\n') == -1) {
        elements.add(new ElementSpec(a, ElementSpec.ContentType, str
          .toCharArray(), 0, str.length()));
        currentLineLength += str.length();
        needLineBreak = false;
        str = str.substring(str.length()); // eat the string
      } else {
        elements.add(new ElementSpec(a, ElementSpec.ContentType, str
          .toCharArray(), 0, str.indexOf('\n') + 1));
        needLineBreak = true;
        str = str.substring(str.indexOf('\n') + 1); // eat the line
      }
    }
  }

  /** insert the buffered strings */
  public synchronized void insertAll() {
    ElementSpec[] elementArray = new ElementSpec[elements.size()];
    elements.toArray(elementArray);

    try {
      // check how many lines have been used so far
      // if too many, shave off a few lines from the beginning
      Element element = super.getDefaultRootElement();
      int lineCount = element.getElementCount();
      int overage = lineCount - maxLineCount;
      if (overage > 0) {
        // if 1200 lines, and 1000 lines is max,
        // find the position of the end of the 200th line
        // systemOut.println("overage is " + overage);
        Element lineElement = element.getElement(overage);
        if (lineElement == null)
          return; // do nuthin

        int endOffset = lineElement.getEndOffset();
        // remove to the end of the 200th line
        super.remove(0, endOffset);
      }
      super.insert(super.getLength(), elementArray);

    } catch (BadLocationException e) {
      // ignore the error otherwise this will cause an infinite loop
      // maybe not a good idea in the long run?
    }
    elements.clear();
    hasAppendage = false;
  }
}
