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
package entities;

import game.Core;
import game.Team;
import collision.Move;
import util.Vector2D;


public class Projectile extends CollisionEntity {


	public Vector2D direction;
	public Vector2D origin;
	public double power = 25;
	public double length = 1.3;
	double speed = 50;
	
	public Projectile(Vector2D position, Vector2D direction, Team team) {
		super(position, team);
		this.direction = direction.normalize();
		this.origin = position;
	}
	


	@Override
	public void process(double dt) {
		velocity = direction.scalar(speed);
		move = new Move(this,dt);
		Core.particleSystem.particleSpray(pos, 1, 0.5);
	}

	@Override
	public double getRadius() {
		return 0.01;
	}
	
	public void collide(CollisionEntity c) {
		
		if(c instanceof HealthEntity) {
			if(c.team != this.team) {
				HealthEntity h = (HealthEntity)c;
				h.damage(power);
				Core.particleSystem.particleSpray(pos, 500, 1);
				Core.entitySystem.remove(this);
			}
		}
		else if(c == null) {
			
			Core.particleSystem.particleSpray(pos, 100, 1);
			Core.entitySystem.remove(this);
		}
	}
	
}