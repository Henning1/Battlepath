package interaction;

import engine.MainLoop;
import game.Game;
import game.Particle;
import game.Projectile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import collision.CollisionSystem;
import collision.Collision;

import util.Line2D;
import util.Vector2D;

public class Renderer {
  
	

  public JPanel panel;
  private Game game;
  int tileSize;
 
  Line2D collisionLine;
  
  //This variable has a special function
  //it is only valid during the execution if render()
  //and provides access to the g2d object to the
  //classes draw functions
  private Graphics2D graphics;
  
  private double width, height;
  
  public Renderer(Game g, int tS, JFrame frame) {
	  this.game = g;
	  this.panel = (JPanel) frame.getContentPane();
	  width = (double)panel.getWidth();
	  height = (double)panel.getHeight();
	  tileSize = tS;
  }

  
  public void render() {
	  draw(panel.getGraphics());
  }
  
  public Vector2D screenToWorld(Point p) {
	  return new Vector2D(
			  (double)p.x / (double)tileSize, 
			  (double)p.y / (double)tileSize);
  }
  
  private void draw(Graphics g) {

    BufferedImage back = new BufferedImage((int)width,(int)height,BufferedImage.TYPE_BYTE_INDEXED);
    Graphics2D g2d = back.createGraphics();   
    graphics = g2d;
    
    g2d.setColor(new Color(0,0,0));
    g2d.fillRect(0, 0, (int)width, (int)height);
    
    for(int x=0; x < game.field.tilesX; x++) {
    	for(int y=0; y < game.field.tilesY; y++) {
    		switch(game.field.tiles[x][y].getType()) {
    		case 1:
    			g2d.setColor(new Color(200,0,0));
    			block(new Vector2D(x,y));
    			break;
    		case 2:
    			g2d.setColor(new Color(0,0,255));
    			block(new Vector2D(x,y));
    			break;
    		}
    	}
    }
    
    ArrayList<Vector2D> path = game.u.path;
    g2d.setColor(new Color(0,255,0));
    if(path != null) {
    	if(path.size() > 0) {
    		line(game.u.pos,path.get(0));
    	}
    	for(int i=0;i<path.size()-1;i++) {
    		line(path.get(i),path.get(i+1));
    	}
    }
    
    circle(game.u.pos,game.u.getRadius(),true);

    //Projectiles
    g2d.setColor(new Color(100,100,255));
    for (int i=0;i<game.projectiles.size();i++) {
		Projectile proj = game.projectiles.get(i);
    	line(proj.pos, proj.pos.subtract(proj.direction.scalar(1.5)));
    }
    
    //Particles;
    for (int i=0;i<game.particles.size();i++) {
		Particle part = game.particles.get(i);
		if(!part.destroyed) {
			int c = 350 -(int) (part.life/part.lifetime*255);
			c = c % 256;
			//g2d.setColor(new Color(255,255,255,c));
			g2d.setColor(new Color(255,255,255));
			line(part.pos, part.pos.subtract(part.direction.scalar(0.1)));
		}
    }
    
    
    Vector2D cursor = game.input.cursorPos;
    
    g2d.setColor(new Color(0,255,0));
    circle(cursor,0.5,false);

    g2d.drawLine((int)(cursor.x()*tileSize), 
    			 (int)(cursor.y()*tileSize-(tileSize/2)-5), 
    			 (int)(cursor.x()*tileSize), 
    			 (int)(cursor.y()*tileSize+(tileSize/2)+5));
    
    g2d.drawLine((int)(cursor.x()*tileSize-(tileSize/2)-5), 
			 	 (int)(cursor.y()*tileSize), 
			 	 (int)(cursor.x()*tileSize+(tileSize/2)+5),
			 	 (int)(cursor.y()*tileSize));
   
    g2d.setColor(new Color(255,255,0));
    
    String fps = Double.toString(MainLoop.framerate);
    int dot = fps.indexOf(".");
    g2d.drawString(fps.substring(0, dot+2) + " fps", 5.0f,15.0f);
    
    Graphics2D g2dpanel = (Graphics2D)g;
    g2dpanel.drawImage(back,null, 0,0);
  }
  
    private void line(Vector2D a, Vector2D b) {
    	graphics.drawLine(
    		 (int)(a.x()*tileSize), (int)(a.y()*tileSize), 
   			 (int)(b.x()*tileSize), (int)(b.y()*tileSize));
    }
    
    private void line(Line2D l) {
    	line(l.a, l.b);
    }
    
    private void circle(Vector2D pos, double r, boolean filled) {
    	if(filled) {
    		graphics.fill(new Ellipse2D.Double(
    			(pos.x-r)*tileSize,
    			(pos.y-r)*tileSize,
    			r*tileSize*2,r*tileSize*2));
    	} else {
    		graphics.draw(new Ellipse2D.Double(
        			(pos.x-r)*tileSize,
        			(pos.y-r)*tileSize,
        			r*tileSize*2,r*tileSize*2));
    	}
    }
    
	private void block(Vector2D pos) {
		graphics.fill(new Rectangle2D.Double(
			(double)pos.x*tileSize,(double)pos.y*tileSize,tileSize,tileSize));
	}

  public int gt(int x, int y) {
	  try{
		  return game.field.tiles[x][y].getType();
	  }
	  catch(Exception e)
	  {
		  return 1;
	  }
  }
 


}