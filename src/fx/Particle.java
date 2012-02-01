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
package fx;

import util.Vector2D;

public class Particle extends FxEntity {

	public Vector2D direction;
	public double acceleration;
	public double speed;
	EffectsSystem system;
	
	public Particle(Vector2D position, Vector2D direction, double lifetime, double speed, double accel, EffectsSystem pS) {
		super(pS,lifetime,position);
		this.direction = direction.normalize();
		this.speed = speed;
		acceleration = accel;
	}
	
	public void process(double dt) {
		super.process(dt);
		pos = pos.add(direction.scalar(speed*dt));
		speed += acceleration*dt;
	}


}