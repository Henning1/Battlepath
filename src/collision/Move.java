package collision;

import engine.GlobalInfo;
import util.Line2D;
import util.Vector2D;
import game.Entity;

public class Move {
	Entity e;
	Vector2D basepoint;
	Vector2D v;
	boolean finished = false;
	
	public Move(Entity e, double dt) {
		this.e = e;
		v = e.velocity.scalar(dt);
		basepoint = e.pos;
	}
	
	// returns new velocity
	protected void slide(Collision closestCollision) {
		// *** Collision occured ***
		// The original destination point
		Vector2D destinationPoint = basepoint.add(v);
		Vector2D newBasePoint = basepoint;
		Vector2D slidePoint = closestCollision.collisionPoint;
		double veryCloseDistance = GlobalInfo.veryCloseDistance;
		// only update if we are not already very close
		// and if so we only move very close to intersection..not
		// to the exact spot.
		if (closestCollision.distance>=veryCloseDistance)
		{
			Vector2D newV = v.copy();
			newV.normalize().scalar(closestCollision.distance-veryCloseDistance);
			newBasePoint = basepoint.add(newV);
			// Adjust polygon intersection point (so sliding
			// plane will be unaffected by the fact that we
			// move slightly less than collision tells us)
			newV.normalize();
			slidePoint = closestCollision.collisionPoint.subtract(newV.scalar(veryCloseDistance));
		}
		// Determine the sliding plane
		Vector2D slidePlaneNormal =
			newBasePoint.subtract(slidePoint).normalize();

		Line2D slidingPlane = new Line2D(slidePoint,slidePoint.add(slidePlaneNormal.orthogonal()));
		// Again, sorry about formatting.. but look carefully ;)
		Vector2D newDestinationPoint = destinationPoint.subtract(
				slidingPlane.normal().scalar(slidingPlane.signedDistance(destinationPoint)));

	
		// Generate the slide vector, which will become our new
		// velocity vector for the next iteration
		v = newDestinationPoint.subtract(slidePoint);
		// Recurse:
		// dont recurse if the new velocity is very small
		if (v.length() < veryCloseDistance) {
			finished = true;
		}
		basepoint = newBasePoint;
	}
	
	public void move() {
		basepoint = basepoint.add(v);
		finished = true;
		v = new Vector2D(0,0);
	}
	
	public void apply() {
		e.pos = basepoint;
	}
	
	
}
