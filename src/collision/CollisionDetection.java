package collision;

import java.awt.Point;
import java.util.ArrayList;

import engine.Field;
import game.Unit;

import util.Line2D;
import util.Vector2D;

public class CollisionDetection {
	
	Field field;
	private double veryCloseDistance = 0.0005;
	
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
	
	public void collideAndSlide(Unit u, double dt) {
		System.out.println(u);
		System.out.println(u.velocity);
		System.out.println(u.velocity.scalar(dt));
		pCollideAndSlide(u,u.velocity.scalar(dt),3);
	}
	
	
	private void pCollideAndSlide(Unit u, Vector2D v,int d) {
		if(d==0) return;
		System.out.println(v);

		ArrayList<Line2D> model = relevantData(u.pos, v, u.radius);	
		CollisionPackage closestCollision = getClosestCollision(model,v, u);

		if(closestCollision != null) {
			//slide to obstacle and retrieve transformed velocity
			Vector2D newVelocity = slide(closestCollision,u);
			//recurse
			if(newVelocity != null)
				pCollideAndSlide(u,newVelocity,--d);

		} else {
			u.pos = u.pos.add(v);
		}
		
	}
	

	
	// returns new velocity
	private Vector2D slide(CollisionPackage closestCollision, Unit u) {
		Vector2D v = closestCollision.velocity;
		// *** Collision occured ***
		// The original destination point
		Vector2D destinationPoint = u.pos.add(v);
		Vector2D newBasePoint = u.pos;
		// only update if we are not already very close
		// and if so we only move very close to intersection..not
		// to the exact spot.
		if (closestCollision.distance>=veryCloseDistance)
		{
			Vector2D newV = v.copy();
			newV.normalize().scalar(closestCollision.distance-veryCloseDistance);
			newBasePoint = closestCollision.basepoint.add(newV);
			// Adjust polygon intersection point (so sliding
			// plane will be unaffected by the fact that we
			// move slightly less than collision tells us)
			newV.normalize();
			closestCollision.collisionPoint = 
					closestCollision.collisionPoint.subtract(newV.scalar(veryCloseDistance));
		}
		// Determine the sliding plane
		Vector2D slidePlaneOrigin =	closestCollision.collisionPoint;
		Vector2D slidePlaneNormal =
			newBasePoint.subtract(closestCollision.collisionPoint).normalize();

		Line2D slidingPlane = new Line2D(slidePlaneOrigin,slidePlaneOrigin.add(slidePlaneNormal.orthogonal()));
		// Again, sorry about formatting.. but look carefully ;)
		Vector2D newDestinationPoint = destinationPoint.subtract(
				slidingPlane.normal().scalar(slidingPlane.signedDistance(destinationPoint)));

	
		// Generate the slide vector, which will become our new
		// velocity vector for the next iteration
		Vector2D newVelocityVector = newDestinationPoint.subtract(closestCollision.collisionPoint);
		// Recurse:
		// dont recurse if the new velocity is very small
		if (newVelocityVector.length() < veryCloseDistance) {
			return null;
		}
		u.pos = newBasePoint;
		
		return newVelocityVector;
	}
	
	private CollisionPackage getClosestCollision(ArrayList<Line2D> model, Vector2D v, Unit u) {
		CollisionPackage closestCollision = null;
		double howClose = Double.MAX_VALUE;
		for(Line2D line : model) {
			CollisionPackage cp = new CollisionPackage(u.pos,v,u.radius,line);
			cp.calcIntersection();
			if(cp.collision && cp.distance < howClose) {
				closestCollision = cp;
				howClose = cp.distance;
			}
		}
		return closestCollision;
	}
	
	
}
