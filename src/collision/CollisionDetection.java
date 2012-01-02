package collision;

import java.awt.Point;
import java.util.ArrayList;

import engine.Field;
import game.Unit;

import util.Line2D;
import util.Vector2D;

public class CollisionDetection {
	
	Field field;
	
	public CollisionDetection(Field field) {
		this.field = field;
	}
	
	
	public ArrayList<Line2D> relevantData(
			Vector2D position, Vector2D velocity, double radius) {
		
		double length = velocity.length();
		Vector2D topleft = new Vector2D(
				position.x() - length - radius, position.y() - length - radius);
		Vector2D bottomright = new Vector2D(
				position.x() + length + radius, position.y() + length + radius);
		
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
	
	public CollisionPackage collideAndSlide(Unit u, Vector2D v, int d) {
		if(d==0) return null;
		
		ArrayList<Line2D> model = relevantData(u.pos, v, u.radius);		
		
		CollisionPackage closestCollision = null;
		double howClose = Double.MAX_VALUE;
		for(Line2D line : model) {
			CollisionPackage cp = new CollisionPackage(u.pos,v,u.radius,line);
			cp.calcIntersection();
			if(cp.collision && cp.distance < howClose) {
				closestCollision = cp;
				howClose = cp.distance;
				//System.out.println(cp);
			}
		}
		double veryCloseDistance = 0.05;
		if(closestCollision != null) {
			
	
			Vector2D p = closestCollision.collisionPoint;
			Vector2D radius = p.subtract(u.pos);
			
			Line2D slidingplane = new Line2D(p, radius.orthogonal().add(p));
			
			Vector2D move = v.scalar(closestCollision.t*0.95);
			u.pos = u.pos.add(move);
			
	
			System.out.println(closestCollision);
			
			double distance = slidingplane.distance(u.pos.add(v));
			
			Vector2D newDestinationPoint = u.pos.add(v).subtract(
					slidingplane.normal.scalar(distance));
			
			collideAndSlide(u,newDestinationPoint.subtract(u.pos), d-1);
			
		
		} else {
			u.pos = u.pos.add(v);
		}
		
		return closestCollision;
	}

}
