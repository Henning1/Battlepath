package game;


import java.util.ArrayList;

import util.Vector2D;

import engine.GlobalInfo;


public class Unit extends Entity {

	Game game;
	public ArrayList<Vector2D> path;
	double speed = 0.4;
	
	
	public Unit(Vector2D position, Game game) {
		super(position);
		this.game = game;
	}
	
	public void moveTo(Vector2D dest) {
		path = game.p.plan(pos, dest);
	}
	
	public void process(double dt) {
		
		if(path != null && path.size() > 0) {
			if(pos.distance(path.get(0)) < GlobalInfo.accuracy) {
				path.remove(0);
			}
			if(path.size() > 0) {
				Vector2D dest = path.get(0);
				Vector2D move = dest.subtract(pos).normalize().scalar(speed);
				
				pos = pos.add(move.scalar(dt));
			}
			
		}
		
	}

}
