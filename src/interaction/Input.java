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

import game.Game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import util.Vector2D;

class Remover extends TimerTask {
	int key;
	HashMap<Integer, Remover> pressedKeys;
	public Remover(int k, HashMap<Integer, Remover> pK) {
		key = k;
		pressedKeys = pK;
	}
	
	@Override
	public void run() {
		pressedKeys.remove(key);
	}
}


public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	Game g;
	public Dimension size=null;
	
	public boolean[] mouseButtonPressed = new boolean[3];
	public Point viewCursorPos = new Point(0,0);
	
	ArrayList<Character> keyBuffer;
	HashMap<Integer, Remover> pressedKeys;
	Timer timer;
	
	public Input(BFrame frame, Game g) {
		
		this.g = g;
		frame.canvas.addMouseMotionListener(this);
		frame.canvas.addMouseListener(this);
		frame.canvas.addMouseWheelListener(this);
		frame.canvas.addKeyListener(this);
		size = new Dimension();
		size.width = ((JPanel)frame.getContentPane()).getWidth();
		size.height = ((JPanel)frame.getContentPane()).getHeight();
		pressedKeys = new HashMap<Integer, Remover>();
		keyBuffer = new ArrayList<Character>();
		timer = new Timer();
	}
		
	public boolean isPressed(Integer key) {
		return pressedKeys.containsKey(key);
	}
	
	public ArrayList<Character> getKeyBuffer() {
		ArrayList<Character> retBuf = new ArrayList<Character>();
		retBuf.addAll(keyBuffer);
		keyBuffer.clear();
		return retBuf;
	}
	
	public Vector2D getCursorPos() {
		return g.view.viewToWorld(viewCursorPos);
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
		mouseButtonPressed[arg0.getButton()-1] = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouseButtonPressed[arg0.getButton()-1] = false;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		Point p = arg0.getPoint();
		viewCursorPos = p;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point p = arg0.getPoint();
		viewCursorPos = p;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int c = arg0.getKeyCode();
		if(!pressedKeys.containsKey(c))
			pressedKeys.put((Integer)c, null);
		else
		{
			try {
				pressedKeys.get(c).cancel();
			}
			catch (java.lang.NullPointerException e) {}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int c = arg0.getKeyCode();
		pressedKeys.put((Integer)c, new Remover(c, pressedKeys));
		timer.schedule(pressedKeys.get(c), 2);
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		keyBuffer.add(arg0.getKeyChar());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		keyBuffer.add(arg0.getWheelRotation() > 0 ? (char)-1 : (char)-2);
	}
}
