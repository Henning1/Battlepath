package interaction;
import javax.swing.*;
import java.awt.*;

/** A few utilities that simplify testing of windows in Swing.
 *  1998 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class WindowUtilities {


  /** A simplified way to see a JPanel or other Container.
   *  Pops up a JFrame with specified Container as the content pane.
   */

  public static JFrame openFrame(int width,int height) {
    JFrame frame = new JFrame("Battlepath");
    frame.setSize(width, height);
    JPanel panel = new JPanel();
    frame.setContentPane(panel);
    frame.addWindowListener(new ExitListener());
    frame.setVisible(true);
    return(frame);
  }
  
  
}