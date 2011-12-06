package interaction;

import game.Game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;


public class Input implements MouseListener {

	Game g;
	Dimension size=null;
	
	public Input(Game g) {
		this.g = g;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Point pclick = arg0.getPoint();
		Point2D clickpos = new Point2D.Double(
				(double)pclick.x/(double)size.width,
				(double)pclick.y/(double)size.height);
		
		g.click(clickpos);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
