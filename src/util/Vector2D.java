package util;

import java.awt.geom.Point2D;

public class Vector2D {
	double x,y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(Point2D p) {
		this.x = p.getX();
		this.y = p.getY();
	}
	
	public double length() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public Vector2D normalize() {
		double l = length();
		return new Vector2D(x/l,y/l);
	}
	
	public Vector2D add(Vector2D v) {
		return new Vector2D(x+v.x,y+v.y);
	}
	
	public Vector2D sub(Vector2D v) {
		return new Vector2D(x-v.x,y-v.y);
	}
}
