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
package collision;

import engine.GlobalInfo;
import entities.Entity;
import util.Line2D;
import util.Vector2D;

public class Move {
	Entity e;
	Vector2D basepoint;
	Vector2D v;
	boolean finished = false;
	double dt;
	
	public Move(Entity e, double dt) {
		this.dt = dt;
		this.e = e;
		v = e.velocity.scalar(dt);
		basepoint = e.pos;
	}
	
	public void modify(Vector2D m) {
		System.out.println("Before: " + v + "m: " + m);
		v = v.add(m.scalar(dt));
		System.out.println("After: " + v);
	}
	
	// returns new velocity
	protected void slide(Collision closestCollision) {
		// *** Collision occurred ***
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
	}
	
	public void apply() {
		e.pos = basepoint;
	}
	
	
}
