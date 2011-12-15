package util;

import java.awt.geom.Point2D;

import engine.GlobalInfo;

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
	
	public static Vector2D fromAngle(double alpha, double length) {
		//sin(alpha) = x / length
		//x = length*sin(alpha)
		
		return new Vector2D(Math.cos(alpha)*length, Math.sin(alpha)*length);
	
	}
	
	public double x() {
		return x;
	}
	
	public double y() {
		return y;
	}
	
	public double distance(Vector2D v) {
		return subtract(v).length();
	}
	
	public double length() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public Vector2D normalize() {
		double l = length();
		return scalar(1/l);
	}
	
	public Vector2D orthogonal() {
		return new Vector2D(y,-x);
	}
	
	public Vector2D add(Vector2D v) {
		return new Vector2D(x+v.x,y+v.y);
	}
	
	public Vector2D subtract(Vector2D v) {
		return new Vector2D(x-v.x,y-v.y);
	}
	
	public Vector2D scalar(double a) {
		return new Vector2D(x*a,y*a);
	}
	
	public double dotProduct(Vector2D v) {
		return x*v.x+y*v.y;
	}
	
	public double angle() {	
		double angle = Math.atan2(y, x);
		if (angle < 0) angle += 2*Math.PI;
		return angle;
	}
	
	public boolean equals(Object o) {
		if(o instanceof Vector2D) {
			Vector2D v = (Vector2D)o;
			return Util.doubleEquals(x, v.x, GlobalInfo.accuracy)
				&& Util.doubleEquals(y, v.y, GlobalInfo.accuracy);
		}
		return false;
	}
	
	public String toString() {
		return "("+x+","+y+")";
	}
	
	
}
