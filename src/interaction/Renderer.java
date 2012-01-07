package interaction;

import engine.MainLoop;
import game.Game;
import game.Particle;
import game.Projectile;
import game.Tower;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
  private Vector2D offset;
  
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
  
  private void draw(Graphics g) {

    BufferedImage back = new BufferedImage((int)width,(int)height,BufferedImage.TYPE_BYTE_INDEXED);
    Graphics2D g2d = back.createGraphics();   
    graphics = g2d;
    
    offset = game.view.offset;
    
    g2d.setColor(new Color(0,0,0));
    g2d.fillRect(0, 0, (int)width, (int)height);
    
    //Tiles
    for(int x=0; x < game.field.tilesX; x++) {
    	for(int y=0; y < game.field.tilesY; y++) {
    		Color colx = new Color(200,x,y);
    		switch(game.field.tiles[x][y].getType()) {
    		case 1:
    			g2d.setColor(colx);
    			block(new Vector2D(x,y));
    			break;
    		case 2:
    			g2d.setColor(new Color(0,0,255));
    			block(new Vector2D(x,y));
    			break;
    		}
    	}
    }
    
    //Path
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
    
    //Unit
    circle(game.u.pos,game.u.getRadius(),true);
    
    //Towers
    g2d.setColor(new Color(0,0,255));
    for (int i=0;i<game.towers.size();i++) {
		Tower tow = game.towers.get(i);
		diamond(tow.pos);
    }

    //Projectiles
    g2d.setColor(new Color(100,100,255));
    for (int i=0;i<game.projectiles.size();i++) {
		Projectile proj = game.projectiles.get(i);
		if(proj.pos.distance(game.u.pos) > 1.5)
			line(proj.pos, proj.pos.subtract(proj.direction.scalar(1.5)));
		else 
			line(proj.pos, game.u.pos);
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
    
    //**************CURSOR*******************
    Point cursor = game.input.viewCursorPos;
    g2d.setColor(new Color(0,255,0));
   
    graphics.draw(new Ellipse2D.Double(
			cursor.x-0.5*tileSize,
			cursor.y-0.5*tileSize,
			0.5*tileSize*2,0.5*tileSize*2));

    g2d.drawLine((int)(cursor.x), 
    			 (int)(cursor.y-(tileSize/2)-5), 
    			 (int)(cursor.x), 
    			 (int)(cursor.y+(tileSize/2)+5));
    
    g2d.drawLine((int)(cursor.x-(tileSize/2)-5), 
			 	 (int)(cursor.y), 
			 	 (int)(cursor.x+(tileSize/2)+5),
			 	 (int)(cursor.y));
    
   
    g2d.setColor(new Color(255,255,0));
    
    String fps = Double.toString(MainLoop.framerate);
    int dot = fps.indexOf(".");
    g2d.drawString(fps.substring(0, dot+2) + " fps", 5.0f,15.0f);
    
    Graphics2D g2dpanel = (Graphics2D)g;
    g2dpanel.drawImage(back,null, 0,0);
  }
  
    private void line(Vector2D a, Vector2D b) {
    	graphics.drawLine(
    		(int)((a.x()+offset.x)*scaleFactor()), (int)((a.y()+offset.y)*scaleFactor()), 
    	   	(int)((b.x()+offset.x)*scaleFactor()), (int)((b.y()+offset.y)*scaleFactor()));
    }
    
    private void line(Line2D l) {
    	line(l.a, l.b);
    }
    
    private void diamond(Vector2D pos) {
    	double scale = scaleFactor();
    	int[] xpoints = new int[4];
    	int[] ypoints = new int[4];
    	
    	xpoints[0] = (int)((pos.x-0.5+offset.x)*scale);
    	ypoints[0] = (int)((pos.y+offset.y)*scale);
    	xpoints[1] = (int)((pos.x+offset.x)*scale);
    	ypoints[1] = (int)((pos.y-0.5+offset.y)*scale);
    	xpoints[2] = (int)((pos.x+0.5+offset.x)*scale);
    	ypoints[2] = ypoints[0];
    	xpoints[3] = xpoints[1];
    	ypoints[3] = (int)((pos.y+0.5+offset.y)*scale);
    	
    	graphics.fillPolygon(new Polygon(xpoints, ypoints, 4));
    }
    
    private void circle(Vector2D pos, double r, boolean filled) {
    	if(filled) {
    		graphics.fill(new Ellipse2D.Double(
    			(pos.x-r+offset.x)*scaleFactor(),
    			(pos.y-r+offset.y)*scaleFactor(),
    			r*scaleFactor()*2,r*scaleFactor()*2));
    	} else {
    		graphics.draw(new Ellipse2D.Double(
    				(pos.x-r+offset.x)*scaleFactor(),
        			(pos.y-r+offset.y)*scaleFactor(),
        			r*scaleFactor()*2,r*scaleFactor()*2));
    	}
    }
    
	private void block(Vector2D pos) {
		graphics.fill(new Rectangle2D.Double(
			(double)(pos.x+offset.x)*scaleFactor(),(double)(pos.y+offset.y)*scaleFactor(),scaleFactor(),scaleFactor()));
	}
	
	private double scaleFactor() {
		return tileSize * game.view.zoom;
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