package interaction;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

import java.awt.*;
import java.awt.image.BufferedImage;



public class BFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public GLCanvas canvas;
	
	
	public BFrame(Dimension size, OpenGLRenderer r) {
		super("Battlepath");
		
		//OpenGL initialization
		GLProfile glp = GLProfile.getDefault();
		GLProfile.initSingleton();
		GLCapabilities caps = new GLCapabilities(glp);
		canvas = new GLCanvas(caps);
		canvas.addGLEventListener(r);

		setSize(size.width, size.height);
		add(canvas);
		addWindowListener(new ExitListener());
		setVisible(true);

		//Cursor
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor");
		canvas.setCursor(blankCursor);
	}
}