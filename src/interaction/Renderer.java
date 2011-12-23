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

import collision.CollisionDetection;

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
    
    for(int x=0; x < game.f.tilesX; x++) {
    	for(int y=0; y < game.f.tilesY; y++) {
    		switch(game.f.tiles[x][y].getType()) {
    		case 1:
    			g2d.setColor(new Color(200,0,0));
    			g2d.fill(new Rectangle2D.Double(
        				(double)x*tileSize,(double)y*tileSize,tileSize,tileSize));
    			break;
    		case 2:
    			g2d.setColor(new Color(0,0,255));
    			g2d.fill(new Rectangle2D.Double(
    					(double)x*tileSize,(double)y*tileSize,tileSize,tileSize));
    			break;
    		
    		}

    	}
    }
    

    ArrayList<Vector2D> path = game.u.path;
    g2d.setColor(new Color(0,255,0));
    if(path != null) {
    	if(path.size() > 0) {
    		g2d.drawLine((int)(game.u.pos.x()*tileSize), 
					 (int)(game.u.pos.y()*tileSize),
					 (int)(path.get(0).x()*tileSize), 
					 (int)(path.get(0).y()*tileSize));
    	}
    	for(int i=0;i<path.size()-1;i++) {
    		g2d.drawLine((int)(path.get(i).x()*tileSize), 
    					 (int)(path.get(i).y()*tileSize),
    					 (int)(path.get(i+1).x()*tileSize), 
    					 (int)(path.get(i+1).y()*tileSize));
    	}
    }
    
    g2d.fill(new Ellipse2D.Double((game.u.pos.x()*tileSize)-(tileSize/4),
    							  (game.u.pos.y()*tileSize)-(tileSize/4),
    							  tileSize/2,tileSize/2));
    
    //Projectiles
    g2d.setColor(new Color(0,0,255));
    for (int i=0;i<game.projectiles.size();i++) {
		Projectile proj = game.projectiles.get(i);
    	line(proj.pos, proj.pos.add(proj.direction.scalar(0.5)));
    }
    
    //Particles;
    g2d.setColor(new Color(255,255,255));
    for (int i=0;i<game.particles.size();i++) {
		Particle part = game.particles.get(i);
		if(part.destroyed == false)
			line(part.pos, part.pos.add(part.direction.scalar(0.1)));
    }
    
    
    Vector2D cursor = game.input.cursorPos;
    
    g2d.setColor(new Color(0,255,0));
    g2d.draw(new Ellipse2D.Double(
    		cursor.x()*tileSize-(tileSize/2),
    		cursor.y()*tileSize-(tileSize/2),
			  tileSize,tileSize));
    
    g2d.drawLine((int)(cursor.x()*tileSize), 
    			 (int)(cursor.y()*tileSize-(tileSize/2)-5), 
    			 (int)(cursor.x()*tileSize), 
    			 (int)(cursor.y()*tileSize+(tileSize/2)+5));
    
    g2d.drawLine((int)(cursor.x()*tileSize-(tileSize/2)-5), 
			 	 (int)(cursor.y()*tileSize), 
			 	 (int)(cursor.x()*tileSize+(tileSize/2)+5),
			 	 (int)(cursor.y()*tileSize));
   
    g2d.setColor(new Color(255,255,0));
    CollisionDetection cd = new CollisionDetection(game.f);
    ArrayList<Line2D> data = cd.relevantData(game.u.pos, game.u.velocity, 0.25);
    for(Line2D l : data) {
    	line(l);
    	Vector2D middle = new Vector2D((l.a.x+l.b.x)/2,(l.a.y+l.b.y)/2);
    	line(middle, middle.add(l.normal().scalar(0.25)));
    }
    
    
    Line2D line = cd.collideAndSlide(game.u.pos, game.u.velocity, 0.25);
    if(line != null) collisionLine = line;
    if(collisionLine != null) {
    	g2d.setColor(new Color(0,0,255));
    	line(collisionLine);
    }
    /*if(cpoints!=null) {
    for(Vector2D p : cpoints) {
		g2d.fill(new Rectangle2D.Double(
				(double)p.x*tileSize,(double)p.y*tileSize,tileSize/4,tileSize/4));
    }}*/
    
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

  public int gt(int x, int y) {
	  try{
		  return game.f.tiles[x][y].getType();
	  }
	  catch(Exception e)
	  {
		  return 1;
	  }
  }
 


}