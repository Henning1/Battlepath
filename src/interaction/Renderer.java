package interaction;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Renderer extends Thread {
  
	

  public JPanel panel;
  private Game game;
  
  public Renderer(Game g) {
	  this.game = g;
  }
  
  public void run() {
      while(true) {
    	  draw(panel.getGraphics());
    	  try {
			sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
      } 

  }
  
  public void draw(Graphics g) {
    //clear(g);
    
	//Color pathColor = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
	BufferedImage back = new BufferedImage(panel.size().width,panel.size().height,BufferedImage.TYPE_BYTE_INDEXED);
	
    Graphics2D g2d = back.createGraphics();
    
    g2d.setColor(new Color(255,255,255));
    g.fillRect(0, 0, panel.size().width, panel.size().height);
    
    double size = (double)panel.size().width/(double)game.f.tilesX;
    
    
    for(int x=0;x<game.f.tilesX;x++) {
    	
    	
    	
    	for(int y=0; y<game.f.tilesY;y++) {
    		switch(game.f.tiles[x][y]) {
    		case 1:
    			/*
    			ArrayList<Point2D >nbs =
    				f.neighbours(f.getWorldPos(new Point(x,y)));
 
    			int ns_one = 0;
    			for(Point2D n : nbs) {
    				if(f.tileAt(n) != 1) ns_one++;
    			}
    			
    			if(ns_one == 2) {
    				if(gt(x-1,y) != 1 && gt(x,y-1) != 1) {
    					int[] xPoints = {(int) (x*size),(int) (x*size+size)+1,(int) (x*size+size)+1};
    					int[] yPoints = {(int) (y*size+size)+1,(int) (y*size+size),(int) (y*size)};
    					g.fillPolygon(xPoints, yPoints, 3);
    					
    				}
    			}
    			
    			else {
    			*/
    			g2d.setColor(new Color(255,0,0));
    			g2d.fill(new Rectangle2D.Double(
        				x*size,y*size,size,size));
    			
    			
    			break;
    		case 2:
    			g2d.setColor(new Color(0,0,255));
    			g2d.fill(new Rectangle2D.Double(
        				x*size,y*size,size,size));
    			break;
    		
    		}

    	}
    }
    
    double width = (double)panel.size().width;
    ArrayList<Point2D> path = game.u.path;
    g2d.setColor(new Color(0,255,0));
    if(path != null) {
    	if(path.size() > 0) {
    		g2d.drawLine((int)(game.u.pos.getX()*width), 
					 (int)(game.u.pos.getY()*width),
					 (int)(path.get(0).getX()*width), 
					 (int)(path.get(0).getY()*width));
    	}
    	for(int i=0;i<path.size()-1;i++) {
    		g2d.drawLine((int)(path.get(i).getX()*width), 
    					 (int)(path.get(i).getY()*width),
    					 (int)(path.get(i+1).getX()*width), 
    					 (int)(path.get(i+1).getY()*width));
    	}
    }
    
    //g2d.setColor(new Color(255,0,0));
    g2d.fill(new Ellipse2D.Double(game.u.pos.getX()*width-(size/2),
    							  game.u.pos.getY()*width-(size/2),
    							  size,size));


    Graphics2D g2dpanel = (Graphics2D)g;
    g2dpanel.drawImage(back,null, 0,0);
    
  }

  public int gt(int x, int y) {
	  try{
		  return game.f.tiles[x][y];
	  }
	  catch(Exception e)
	  {
		  return 1;
	  }
  }
 


}