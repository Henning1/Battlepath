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
	
	public double signedDistance(Vector2D point) {
		return normal.dotProduct(point.subtract(base));
	}
	
	public boolean linePointInSegment(Vector2D point) {
		return a.distance(point) + b.distance(point) <= a.distance(b) + 0.05;
	}
	
	
}
