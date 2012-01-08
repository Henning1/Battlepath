package collision;

import java.awt.Point;
import java.util.ArrayList;

import Entities.Entity;

import engine.Field;
import game.Game;

import util.Line2D;
import util.Vector2D;

public class CollisionSystem {
	
	Field field;
	Game game;
	
	
	
	public CollisionSystem(Field field, Game game) {
		this.field = field;
		this.game = game;
	}
	
	
	public ArrayList<Line2D> relevantData(Entity e) {
		Vector2D velocity = e.velocity.scalar(game.dt);
		
		double length = velocity.length();
		Vector2D topleft = new Vector2D(
				e.pos.x() - length - e.getRadius(), e.pos.y() - length - e.getRadius());
		Vector2D bottomright = new Vector2D(
				e.pos.x() + length + e.getRadius(), e.pos.y() + length + e.getRadius());
		
		Point a = field.tileIndexAt(topleft);
		Point b = field.tileIndexAt(bottomright);
		

		movePointIntoIndexBounds(a);
		movePointIntoIndexBounds(b);
		ArrayList<Line2D> collModel = new ArrayList<Line2D>();
		
		for(int x=a.x; x<=b.x;x++) {
			for(int y=a.y; y<=b.y; y++) {
				collModel.addAll(field.tiles[x][y].collisionModel);
			}
		}
		
		return collModel;
	}
	
	private void movePointIntoIndexBounds(Point p) {
		if(p.x >= field.tilesX) p.x = field.tilesX-1;
		if(p.x < 0) p.x = 0;
		if(p.y >= field.tilesY) p.y = field.tilesY-1;
		if(p.y < 0) p.y = 0;
	}
	
	public Move collideAndSlide(Entity e) {
		Move m = new Move(e, game.dt);
		pCollideAndSlide(m,3);
		return m;
	}
	
	public Collision collide(Entity e) {
		return collide(relevantData(e), new Move(e,game.dt));
	}
	
	private void pCollideAndSlide(Move move,int d) {
		if(d==0) return;

		ArrayList<Line2D> model = relevantData(move.e);	
		Collision closestCollision = collide(model,move);

		if(closestCollision != null) {
			//slide to obstacle and retrieve transformed velocity
			move.slide(closestCollision);
			//recurse
			if(!move.finished)
				pCollideAndSlide(move,--d);
		}
		else {
			move.move();
		}
		
	}

	private Collision collide(ArrayList<Line2D> model, Move move) {
		Collision closestCollision = null;
		double howClose = Double.MAX_VALUE;
		for(Line2D line : model) {
			Collision cp = new Collision(move,line);
			cp.calcIntersection();
			if(cp.collision && cp.distance < howClose) {
				closestCollision = cp;
				howClose = cp.distance;
			}
		}
		return closestCollision;
	}
	

	
	
}
