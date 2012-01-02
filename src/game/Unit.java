package game;


import java.util.ArrayList;

import collision.CollisionDetection;
import collision.CollisionPackage;

import util.Vector2D;

import engine.GlobalInfo;


public class Unit extends Entity {

	Game game;
	public ArrayList<Vector2D> path;
	public Vector2D velocity = new Vector2D(0,0);
	double speed = 2;
	public double radius = 1;
	public CollisionPackage cp;
	//Unit health, 0-100
	int health = 0;
	
	public Unit(Vector2D position, Game game) {
		super(position);
		this.game = game;
		health = 100;
	}
	
	public void moveTo(Vector2D dest) {
		path = game.p.plan(pos, dest);
	}
	
	public void setHealth(int h) {
		if(h > 100) {
			h = 100;
		}
		else if(h < 0) {
			h = 0;
		}
		
		health = h;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void process(double dt) {
		
		CollisionDetection cd = new CollisionDetection(game.f);
		
		
		if(path != null && path.size() > 0) {
			if(pos.distance(path.get(0)) < GlobalInfo.accuracy) {
				path.remove(0);
				velocity = new Vector2D(0,0);
			}
			if(path.size() > 0) {
				Vector2D dest = path.get(0);
				velocity = dest.subtract(pos).normalize().scalar(speed);
			}
			
		}
		
		cp = cd.collideAndSlide(this, velocity.scalar(dt),5);
		
		//if(cp == null)	pos = pos.add(velocity.scalar(dt));
		
	}

}
