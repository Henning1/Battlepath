package util;

public class Line2D {
	
	public Vector2D base;
	public Vector2D a,b;
	public Vector2D direction;
	public Vector2D normal;

	
	public Line2D(Vector2D a, Vector2D b) {
		base = a;
		this.a = a;
		this.b = b;
		direction = b.subtract(a).normalize();
		normal = direction.orthogonal().normalize();
	
	}
	
	public Vector2D normal() {
		return normal;
	}
	
	public double yAt(double x) {
		double t = (x - a.x) / direction.x;
		return a.y + direction.y * t;
	}
	
	public double xAt(double y) {
		double t = (y - a.y) / direction.y;
		return a.x + direction.x * t;
	}
	
	public double signedDistance(Vector2D point) {
		return -normal.dotProduct(base.subtract(point));
	}
	
	public double distance(Vector2D point) {
		return Math.abs(signedDistance(point));
	}
	
	public boolean pointInSegment(Vector2D point) {
		return a.distance(point) + b.distance(point) <= a.distance(b) + 0.01;
	}
	
	
}
