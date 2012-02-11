/**
 * Copyright (c) 2011-2012 Henning Funke.
 * 
 * This file is part of Battlepath.
 *
 * Battlepath is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * Battlepath is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package util;

import java.awt.geom.Point2D;

public class Vector2D {
	public double x,y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
		
	public Vector2D(Point2D p) {
		this.x = p.getX();
		this.y = p.getY();
	}
	
	//0° is horizontal to the right. Counter-clockwise, radian!
	public static Vector2D fromAngle(double alpha, double length) {
		return new Vector2D(Math.cos(alpha)*length, Math.sin(alpha)*length);
	
	}
	
	public double x() {
		return x;
	}
	
	public double y() {
		return y;
	}
	
	public Vector2D copy() {
		return new Vector2D(x,y);
	}
	
	public double distance(Vector2D v) {
		return subtract(v).length();
	}
	
	public double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	public double squaredLength() {
		return dotProduct(this);
	}
	
	public Vector2D normalize() {
		double l = length();
		return scalar(1/l);
	}
	
	public Vector2D orthogonal() {
		return new Vector2D(-y,x);
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
	
	public Vector2D negate() {
		return new Vector2D(-x,-y);
	}
	
	public boolean equals(Object o) {
		if(o instanceof Vector2D) {
			Vector2D v = (Vector2D)o;
			return x == v.x && y == v.y;
		}
		return false;
	}
	
	public Vector2D add(double a) {
		return new Vector2D(x+a,y+a);
	}
	
	public String toString() {
		return "("+x+","+y+")";
	}

	/**
	 * @param d
	 * @return returns a Vector with both components subtracted by d
	 */
	public Vector2D subtract(double d) {
		return new Vector2D(x-d,y-d);
	}
	
	
}
