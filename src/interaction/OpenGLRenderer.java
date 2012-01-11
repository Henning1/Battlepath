package interaction;

import engine.MainLoop;
import game.Game;
import game.Particle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Entities.Entity;
import Entities.Projectile;
import Entities.Tower;
import Entities.Unit;

import util.Line2D;
import util.Vector2D;

public class OpenGLRenderer implements GLEventListener {

	private Game game;
	private int tileSize;
	private double scaleFactor;
	private Vector2D offset;

	public OpenGLRenderer(Game g, int tS) {
		game = g;
		tileSize = tS;
	}

	private void drawField(GL2 gl) {
		gl.glBegin(GL2.GL_QUADS);
		
		for(int x=0; x < game.field.tilesX; x++) {
			for(int y=0; y < game.field.tilesY; y++) {
				
				gl.glColor3d(1, (double)x/game.field.tilesX, (double)y/game.field.tilesY);
				
				switch(game.field.tiles[x][y].getType()) {
				case 1:
					tile(gl, new Vector2D(x+0.5,y+0.5));
					break;
				}
			}
		}

		gl.glEnd();
	}
	
	private void drawHUD(GL2 gl) {
		//Cursor
		Point cursor = game.input.viewCursorPos;
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3d(0,1,0);
		gl.glVertex2d(cursor.x, cursor.y-10);
		gl.glVertex2d(cursor.x, cursor.y+10);
		gl.glEnd();
	}
	
	private void tile(GL2 gl, Vector2D pos) {
		gl.glVertex2d((pos.x-0.5+offset.x)*scaleFactor, (pos.y+0.5+offset.y)*scaleFactor);
		gl.glVertex2d((pos.x+0.5+offset.x)*scaleFactor, (pos.y+0.5+offset.y)*scaleFactor);
		gl.glVertex2d((pos.x+0.5+offset.x)*scaleFactor, (pos.y-0.5+offset.y)*scaleFactor);
		gl.glVertex2d((pos.x-0.5+offset.x)*scaleFactor, (pos.y-0.5+offset.y)*scaleFactor);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		//Draw
		scaleFactor = tileSize * game.view.zoom;
		offset = game.view.offset;
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		
		drawField(gl);
		drawHUD(gl);
	}
	@Override
	public void dispose(GLAutoDrawable drawable) {
		//Clean up

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		//Initialize
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		//Resize window
		GL2 gl = drawable.getGL().getGL2();
		gl.glLoadIdentity();
		GLU glu = new GLU();
		gl.glViewport(0, 0, width, height);
		glu.gluOrtho2D(0, width, 0, height);
		game.view.windowSize = new Dimension(width, height);
	}



}
