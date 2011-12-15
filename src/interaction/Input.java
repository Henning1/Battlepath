package interaction;

import game.Game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.Vector2D;


public class Input implements MouseListener, MouseMotionListener {

	Game g;
	public Dimension size=null;
	int tileSize;
	
	public Input(Game g, JFrame frame, int tS) {
		
		this.g = g;
		frame.addMouseMotionListener(this);
		frame.addMouseListener(this);
		size = new Dimension();
		size.width = ((JPanel)frame.getContentPane()).getWidth();
		size.height = ((JPanel)frame.getContentPane()).getHeight();
		tileSize = tS;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
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
		Point pclick = arg0.getPoint();
		Vector2D clickpos = new Vector2D(
				(double)pclick.x/(double)tileSize,
				(double)pclick.y/(double)tileSize);
		
		if(arg0.getButton()==MouseEvent.BUTTON1) g.leftclick(clickpos);
		//else if(arg0.getButton()==MouseEvent.BUTTON2) g.middleclick(clickpos);
		else if(arg0.getButton()==MouseEvent.BUTTON3) g.rightclick(clickpos);
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		Point p = arg0.getPoint();
		g.c = new Vector2D(
				(double)p.x/(double)tileSize,
				(double)p.y/(double)tileSize);
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point p = arg0.getPoint();
		g.c = new Vector2D(
				(double)p.x/(double)tileSize,
				(double)p.y/(double)tileSize);
		
	}

}
