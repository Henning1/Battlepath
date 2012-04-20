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

import java.util.ArrayList;

import engine.GlobalInfo;
import game.Core;
import game.HUDButton;
import game.Team;
import util.Line2D;
import util.Vector2D;

public class Tower extends HealthEntity {
	public Vector2D aim = new Vector2D(0,0);
	double lastShot;
	
	public Tower(Vector2D position, Team team) {
		super(position, team);
		move = null;
		health = 300;
	}

	@Override
	public void process(double dt) {
		super.process(dt);
		/*
		ArrayList<Unit> aims = game.getUnitsInRange(pos, 20);
		
		for(Unit u : aims) {
			if(game.collisionSystem.collideWithLevel(new Line2D(pos,u.pos))) continue;
			
			aim = u.pos.subtract(pos).normalize();
			if(pos.distance(u.pos) < 20 && GlobalInfo.time-lastShot > 1) {
				game.emitShot(pos.add(aim.scalar(getRadius())), aim);
				lastShot = GlobalInfo.time;
			}
			break;
		}*/
		
		ArrayList<Entity> aims = Core.entitySystem.entitiesInRange(pos, 20);
		
		for(Entity u : aims) {
			if(!(u instanceof Unit) || u.team == team) continue;			
			if(Core.collisionSystem.collideWithLevel(new Line2D(pos,u.pos))) continue;
			
			aim = u.pos.subtract(pos).normalize();
			if(pos.distance(u.pos) < 20 && GlobalInfo.time-lastShot > 0.5) {
				Core.emitShot(pos.add(aim.scalar(getRadius())), aim, this.team);
				lastShot = GlobalInfo.time;
			}
			break;
		}
	}

	@Override
	public double getRadius() {
		return 1;
	}

	@Override
	public void collide(CollisionEntity e) {

	}

	@Override
	protected ArrayList<HUDButton> getButtons() {
		return new ArrayList<HUDButton>();
	}

}
