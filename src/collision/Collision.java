package collision;

import util.Line2D;
import util.Util;
import util.Vector2D;

public class Collision {
	public double t,t1,t0,distance,signeddistance;
	public Vector2D collisionPoint;
	public Vector2D basepoint;
	public Vector2D velocity;
	public Line2D line;
	public boolean collision=false;
	public boolean edge=false;
	public double dt;
	public Move move;
	
	public Collision(Move m, Line2D line) {
		this.move = m;
		this.basepoint = m.basepoint;
		this.velocity = m.v;
		this.line = line;
	}
	
	
	public boolean calcIntersection() {
	
		collision = false;
		// Is triangle front-facing to the velocity vector?
		// We only check front-facing triangles
		// (your choice of course)
		//if (trianglePlane.isFrontFacingTo(
				//colPackage->normalizedVelocity)) {
			// Get interval of plane intersection:
		double t0, t1, radius = move.e.getRadius();
		boolean embeddedInPlane = false;
		// Calculate the signed distance from sphere
		// position to triangle plane
		double signedDistToTrianglePlane =
				line.signedDistance(basepoint);
		// cache this as we're going to use it a few times below:
		double normalDotVelocity =
			line.normal.dotProduct(velocity);
		// if sphere is travelling parrallel to the plane:
		if (normalDotVelocity == 0.0f) {
			if (Math.abs(signedDistToTrianglePlane) >= radius) {
				// Sphere is not embedded in plane.
				// No collision possible:
				return collision;
			}
			else {
				// sphere is embedded in plane.
				// It intersects in the whole range [0..1]
				embeddedInPlane = true;
				t0 = 0.0;
				t1 = 1.0;
			}
		}
		else {
			// N dot D is not 0. Calculate intersection interval:
			t0=(-radius-signedDistToTrianglePlane)/normalDotVelocity;
			t1=( radius-signedDistToTrianglePlane)/normalDotVelocity;
			// Swap so t0 < t1
			if (t0 > t1) {
				double temp = t1;
				t1 = t0;
				t0 = temp;
			}
			// Check that at least one result is within range:
			if (t0 > 1.0f || t1 < 0.0f) {
				// Both t values are outside values [0,1]
				// No collision possible:
				return collision;
			}
			// Clamp to [0,1]
			if (t0 < 0.0) t0 = 0.0;
			if (t1 < 0.0) t1 = 0.0;
			if (t0 > 1.0) t0 = 1.0;
			if (t1 > 1.0) t1 = 1.0;
		}
		// OK, at this point we have two time values t0 and t1
		// between which the swept sphere intersects with the
		// triangle plane. If any collision is to occur it must
		// happen within this interval.
		double t = 1.0;
		// First we check for the easy case - collision inside
		// the triangle. If this happens it must be at time t0
		// as this is when the sphere rests on the front side
		// of the triangle plane. Note, this can only happen if
		// the sphere is not embedded in the triangle plane.
		
		if (!embeddedInPlane) {
			Vector2D planeIntersectionPoint = 
				basepoint.subtract(line.normal.scalar(radius))
				.add(velocity.scalar(t0));
				if (line.pointInSegment(planeIntersectionPoint))
				{
					edge = true;
					collision = true;
					t = t0;
					collisionPoint = planeIntersectionPoint;
				}
			}
			// if we haven't found a collision already we'll have to
			// sweep sphere against points and edges of the triangle.
			// Note: A collision inside the triangle (the check above)
			// will always happen before a vertex or edge collision!
			// This is why we can skip the swept test if the above
			// gives a collision!
			if (collision == false) {
				// some commonly used terms:
				double velocitySquaredLength = velocity.squaredLength();
				double a,b,c; // Params for equation
				double newT;
				// For each vertex or edge a quadratic equation have to
				// be solved. We parameterize this equation as
				// a*t^2 + b*t + c = 0 and below we calculate the
				// parameters a,b and c for each test.
				// Check against points:
				a = velocitySquaredLength;
				// P1
				Vector2D p1 = line.a;
				b = 2.0*(velocity.dotProduct(basepoint.subtract(p1)));
				c = (p1.subtract(basepoint)).squaredLength() - Math.pow(radius, 2);
				newT = Util.getLowestRoot(a,b,c, t);
				if(newT != -1) {
					t = newT;
					collision = true;
					collisionPoint = p1;
				}
				// P2
				Vector2D p2 = line.b;
				b = 2.0*(velocity.dotProduct(basepoint.subtract(p2)));
				c = (p2.subtract(basepoint)).squaredLength() - Math.pow(radius, 2);
				
				newT = Util.getLowestRoot(a,b,c, t);
				if(newT != -1) {
					t = newT;
					collision = true;
					collisionPoint = p2;
				}
	
				// Set result:
				if (collision == true) {
					// distance to collision: 't' is time of collision
					//distance = t*velocity.length();
					// Does this triangle qualify for the closest hit?
					// it does if it's the first hit or the closest
					// Collision information nessesary for sliding
					//colPackage->nearestDistance = distToCollision;
				}
		}
		return collision;
	}
	

	

	
	public String toString() {
		return "velocity: " + velocity + "\n"
				+ "basepoint: " + basepoint + "\n"
				+ "distance: " + distance + "\n"
				+ "signeddistance: " + signeddistance + "\n"
				+ "normal: " + line.normal() + "\n"
				+ "collision: " + collision + "\n"
				+ "intersection point" + collisionPoint + "\n"
				+ "t0 " + t0 + "\n"
				+ "t1 " + t1;
	}
	
}
