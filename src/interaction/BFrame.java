/**
 * Copyright (c) 2011-2012 Henning Funke.
 * 
 * This file is part of Battlepath.
 *
 * Battlepath is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * Battlepath is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package interaction;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import java.awt.*;
import java.awt.image.BufferedImage;


public class BFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public GLCanvas canvas;
	
	
	public BFrame(Dimension size) {
		super("Battlepath");
		
		//OpenGL initialization
		GLProfile glp = GLProfile.getDefault();
		GLProfile.initSingleton();
		GLCapabilities caps = new GLCapabilities(glp);
		canvas = new GLCanvas(caps);

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