package collision;

import java.awt.Point;
import java.util.ArrayList;


import engine.Field;
import engine.Tile;
import entities.Entity;
import game.Game;

import util.Line2D;
import util.Util;
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

		field.clamp(a);
		field.clamp(b);
		
		ArrayList<Line2D> collModel = new ArrayList<Line2D>();
		
		collModel.addAll(field.getBoundingFrame());
		
		for(int x=a.x; x<=b.x;x++) {
			for(int y=a.y; y<=b.y; y++) {
				collModel.addAll(field.tiles[x][y].collisionModel);
			}
		}
		
		return collModel;
	}
	
	public boolean collideWithLevel(Line2D l) {
		ArrayList<Tile> rTiles = getTilesOn(l);
		
		for(Tile t : rTiles) {
			ArrayList<Line2D> model = t.collisionModel;
			for(Line2D edge : model) {
				Vector2D i = l.intersectionPoint(edge);
				if(l.pointInSegment(i) && edge.pointInSegment(i))
					return true;
			}
		}

		return false;
	}
	
	public ArrayList<Vector2D> collisionsWithTile(Line2D l, Tile t) {
		ArrayList<Vector2D> result = new ArrayList<Vector2D>();
		ArrayList<Line2D> model = t.collisionModel;
		for(Line2D edge : model) {
			Vector2D i = l.intersectionPoint(edge);
			result.add(i);
			
		}
		return result;
	}
	

	
	
	public ArrayList<Tile> getTilesOn(Line2D l) {
		
		ArrayList<Tile> result = new ArrayList<Tile>();
		
		//normalize line from left to right
		Line2D line = l;
		if(l.a.x > l.b.x) {
			line = new Line2D(l.b,l.a);
		}
		
		
		
		Tile current = field.tileAt(line.a);
		Tile destination = field.tileAt(line.b);
		
		while(current != destination && current != null) {
			result.add(current);
			//vertical line
			if(line.direction.y == 0) {
				current = field.tileAt(current.center.add(line.direction));
				continue;
			}
			
			//line penetrates right edge
			double y = line.yAt(current.bottomright.x);
			if(Util.isValueInBounds(current.topleft.y, y, current.bottomright.y)) {
				current = field.tileAt(current.index.x+1,current.index.y);
			} else {
				//line penetrates top edge
				if(line.a.y > line.b.y) {
					current = field.tileAt(current.index.x,current.index.y-1);
				} 
				//line penetrates bottom edge
				else {
					current = field.tileAt(current.index.x,current.index.y+1);
				}
			}
		}
		if(current == destination) result.add(current);
		
		
		
		return result;
		
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
