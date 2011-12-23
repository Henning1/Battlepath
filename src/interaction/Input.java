package interaction;

import game.Game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.Vector2D;


public class Input implements KeyListener, MouseListener, MouseMotionListener {

	Game g;
	Renderer r;
	public Dimension size=null;
	
	public boolean mouse1down=false;
	public boolean mouse2down=false;
	public boolean mouse1clicked=false;
	boolean mouse2clicked=false;
	public Vector2D cursorPos=new Vector2D(0,0);
	
	public boolean keyup=false;
	public boolean keydown=false;
	public boolean keyleft=false;
	public boolean keyright=false;
	
	public Input(JFrame frame, Renderer r) {
		
		this.r = r;
		frame.getContentPane().addMouseMotionListener(this);
		frame.getContentPane().addMouseListener(this);
		frame.addKeyListener(this);
		size = new Dimension();
		size.width = ((JPanel)frame.getContentPane()).getWidth();
		size.height = ((JPanel)frame.getContentPane()).getHeight();
	}
	
	public boolean getMouse1Click() {
		if(mouse1clicked) {
			mouse1clicked = false;
			return true;
		}
		return false;
	}
	
	public boolean getMouse2Click() {
		if(mouse2clicked) {
			mouse2clicked = false;
			return true;
		}
		return false;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		mouse1clicked = true;
		mouse2clicked = true;
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
	
		if(arg0.getButton()==MouseEvent.BUTTON1) 
			mouse1down = true;
		else if(arg0.getButton()==MouseEvent.BUTTON3)
			mouse2down = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton()==MouseEvent.BUTTON1) 
			mouse1down = false;
		else if(arg0.getButton()==MouseEvent.BUTTON3)
			mouse2down = false;
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		Point p = arg0.getPoint();
		cursorPos = r.screenToWorld(p);
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point p = arg0.getPoint();
		cursorPos = r.screenToWorld(p);
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyChar() == 'w')
			keyup = true;
		if(arg0.getKeyChar() == 'a')
			keyleft = true;
		if(arg0.getKeyChar() == 's')
			keydown = true;
		if(arg0.getKeyChar() == 'd')
			keyright = true;
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyChar() == 'w')
			keyup = false;
		if(arg0.getKeyChar() == 'a')
			keyleft = false;
		if(arg0.getKeyChar() == 's')
			keydown = false;
		if(arg0.getKeyChar() == 'd')
			keyright = false;
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
