package collision;

import util.Line2D;
import util.Vector2D;

public class CollisionPackage {
	public double t0,t1,distance,signeddistance;
	public Vector2D planeIntersectionPoint;
	public Vector2D basepoint;
	public Vector2D velocity;
	public double radius;
	public Line2D line;
	public boolean collision=false;
	
	
	
	public CollisionPackage(
			Vector2D basepoint, Vector2D velocity, double radius, Line2D line) {
		this.basepoint = basepoint;
		this.velocity = velocity;
		this.radius = radius;
		this.line = line;
	}
	
	public boolean calcIntersection() {
		signeddistance = line.signedDistance(basepoint);
		distance = Math.abs(signeddistance);
		
		double dot = line.normal().dotProduct(velocity);
		if(dot != 0) {
			t0 = (radius - signeddistance) / dot;
			t1 = (-radius - signeddistance) / dot;
		}
		else {
			if(distance <= radius) {
				t0 = 0;
				t1 = 1;
			}
			return false;
		}
		
		if(t0 < 0 || t0 > 1) return false;
		//if((t0 < 0 || t0 > 1) && (t1 < 0 || t1 > 1)) return direction;
		return true;
	}
}
