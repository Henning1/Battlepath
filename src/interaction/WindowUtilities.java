package interaction;
import javax.swing.*;
import java.awt.*;

/** A few utilities that simplify testing of windows in Swing.
 *  1998 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class WindowUtilities {

  /** Tell system to use native look and feel, as in previous
   *  releases. Metal (Java) LAF is the default otherwise.
   */

  public static void setNativeLookAndFeel() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch(Exception e) {
      System.out.println("Error setting native LAF: " + e);
    }
  }

  /** A simplified way to see a JPanel or other Container.
   *  Pops up a JFrame with specified Container as the content pane.
   */

  public static JFrame openInJFrame(Renderer r,
                                    int width,
                                    int height,
                                    String title, Input i) {
    JFrame frame = new JFrame(title);
    frame.setSize(width, height);
    JPanel panel = new JPanel();
    frame.setContentPane(panel);
    r.panel = panel;
    frame.addWindowListener(new ExitListener());
    panel.addMouseListener(i);
    panel.addMouseMotionListener(i);
    frame.setVisible(true);
    i.size = panel.size();
    return(frame);
  }
}