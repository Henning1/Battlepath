package interaction;

import game.Game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;


public class Input implements MouseListener, MouseMotionListener {

	Game g;
	public Dimension size=null;
	
	public Input(Game g, JFrame frame) {
		this.g = g;
		frame.addMouseMotionListener(this);
		frame.addMouseListener(this);
		size = new Dimension();
		size.width = frame.getContentPane().getWidth();
		size.height = frame.getContentPane().getHeight();
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

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point p = arg0.getPoint();
		g.c = new Point2D.Double(
				(double)p.x/(double)size.width,
				(double)p.y/(double)size.height);
		
	}

}
