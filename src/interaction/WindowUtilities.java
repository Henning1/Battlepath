package interaction;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/** A few utilities that simplify testing of windows in Swing.
 *  1998 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class WindowUtilities {


  /** A simplified way to see a JPanel or other Container.
   *  Pops up a JFrame with specified Container as the content pane.
   */

  public static JFrame openFrame(Dimension size) {
    JFrame frame = new JFrame("Battlepath");
    frame.setSize(size.width, size.height);
    JPanel panel = new JPanel();
    frame.setContentPane(panel);
    frame.addWindowListener(new ExitListener());
    frame.setVisible(true);
 // Transparent 16 x 16 pixel cursor image.
    BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

    // Create a new blank cursor.
    Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        cursorImg, new Point(0, 0), "blank cursor");

    // Set the blank cursor to the JFrame.
    panel.setCursor(blankCursor);

    return(frame);
  }
  
  
}