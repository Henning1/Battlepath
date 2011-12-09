package game;


import java.awt.geom.Point2D;
import java.util.ArrayList;

import engine.GlobalInfo;


public class Unit extends Entity {

	Game game;
	public ArrayList<Point2D> path;
	double speed = 0.3;
	
	
	public Unit(Point2D position, Game game) {
		super(position);
		this.game = game;
	}
	
	public void moveTo(Point2D dest) {
		path = game.p.plan(pos, dest);
	}
	
	public void process(double dt) {
		
		if(path != null && path.size() > 0) {
			if(pos.distance(path.get(0)) < GlobalInfo.accuracy) {
				path.remove(0);
			}
			if(path.size() > 0) {
				Point2D dest = path.get(0);
				double distance = pos.distance(dest);
				Point2D moveNormalized = new Point2D.Double(
						(dest.getX()-pos.getX())/distance,
						(dest.getY()-pos.getY())/distance);
				
				double movex = moveNormalized.getX()*speed;
				double movey = moveNormalized.getY()*speed;
				
				pos = new Point2D.Double(
						pos.getX()+movex*dt,
						pos.getY()+movey*dt);
				
			}
			
		}
		
	}

}
