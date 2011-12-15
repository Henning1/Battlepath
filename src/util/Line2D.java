package util;

public class Line2D {
	
	Vector2D base;
	Vector2D direction;
	Vector2D normal;
	
	public Line2D(Vector2D a, Vector2D b) {
		base = a;
		direction = b.subtract(a).normalize();
		normal = direction.orthogonal().normalize();
	}
	
	public Vector2D normal() {
		return normal;
	}
	
	public double signedDistance(Vector2D point) {
		return normal.dotProduct(point.subtract(base));
	}
	
	
}
