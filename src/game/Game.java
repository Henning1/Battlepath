package game;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import engine.Field;
import engine.Pathplanner;


public class Game extends Thread {
	
	public Field f;
	Pathplanner p;
	//Point2D pos;
	public Unit u;
	
	public Game(Field f, Point2D startpos) {
		this.f = f;
		this.p = new Pathplanner(f);
		//this.pos = startpos;
		u = new Unit(startpos, this);
	}
	
	
	public void click(Point2D clickPos) {
		u.moveTo(clickPos);
		/*
		ArrayList<Point2D> path = p.plan(pos, clickPos);
		if(path != null) {
			f.setTilesTo(path, 2);
			pos = clickPos;
		}*/
	}
	
	  public void run() {
	      while(true) {
	    	  u.process();
	    	  try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	      } 

	  }
	
}
