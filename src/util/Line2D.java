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

public class Line2D {
	
	public Vector2D base;
	public Vector2D a,b;
	public Vector2D direction;
	public Vector2D normal;
	public double slope;
	
	public Line2D(Vector2D a, Vector2D b) {
		base = a;
		this.a = a;
		this.b = b;
		direction = b.subtract(a).normalize();
		normal = direction.orthogonal().normalize();
		slope = calcSlope();
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
	
	private double calcSlope() {
		return (b.y - a.y) / (b.x - a.x);
	}
	
	public double yAxisIntercept() {
		return a.y - (slope*a.x);
	}
	
	public Vector2D intersectionPoint(Line2D line) {
		
		Tuple<Double,Double> params = Util.LES2x2(
				direction.x, line.direction.x, direction.y, line.direction.y,
				line.a.x-a.x, line.a.y-a.y);
		
		return a.add(direction.scalar(params.a));
		//return b.subtract(direction.scalar(-params.b));
	}
	
	public boolean pointInSegment(Vector2D point) {
		return a.distance(point) + b.distance(point) <= a.distance(b) + 0.000001;
	}
	
	
}
