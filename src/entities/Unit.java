package entities;


import java.util.ArrayList;

import collision.CollisionSystem;

import util.Vector2D;

import engine.GlobalInfo;
import game.Game;


public class Unit extends HealthEntity {

	public ArrayList<Vector2D> path;
	public Vector2D direction = new Vector2D(0,0);
	double speed = 4;
	public boolean actionmode = true;

	
	public Unit(Vector2D position, Game game) {
		super(position,game);
		health = 500;
	}
	
	public void moveTo(Vector2D dest) {
		path = game.pathPlanner.plan(pos, dest);
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
	
	public void shoot(Vector2D direction) {
		game.addList.add(new Projectile(pos.add(direction.scalar(getRadius())), direction, game));
	}
	
	public Vector2D velocityDt() {
		return velocity.scalar(game.dt);
	}
	
	public void process(double dt) {
		
		CollisionSystem cs = game.collisionSystem;
		
		
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
		
		move = cs.collideAndSlide(this);
	}

	@Override
	public double getRadius() {
		return 0.49;
	}

	@Override
	public void collide(CollisionEntity e) {
		
	}

}
