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

import entities.CollisionEntity;
import entities.Unit;
import game.Game;

import java.util.ArrayList;

import util.Vector2D;



public class MovementSystem {
	
	private Game game;
	
	public MovementSystem(Game game) {
		this.game = game;
	}
	
	
	private void repellUnits(ArrayList<Unit> units, double dt) {
		
		for(Unit u1 : units) {
			Move m1 = u1.getMove();
			if(u1.leader) continue;
			for(Unit u2 : game.entitySystem.unitsInRange(u1.pos, 5)) {
				if(u1 == u2) continue;
				//Move m2 = u2.getMove();
				Vector2D aToB = u1.pos.subtract(u2.pos);
				double distance = aToB.length();
				//double force = Math.sqrt((Math.exp(-distance+1)));
				double force = Math.exp(-distance+1)*4;
				m1.modify(aToB.normalize().scalar(force));
			}

			
		}
	}
	
	public void collideUnitsWithLevel(ArrayList<Unit> units, double dt) {
		for(Unit u : units) {
			game.collisionSystem.collideAndSlide(u.getMove());			
		}
	}
	
	public void process(ArrayList<CollisionEntity> ces, ArrayList<Unit> units, double dt) {
		
		repellUnits(units,dt);
		collideUnitsWithLevel(units,dt);		

		for(CollisionEntity c : ces) {
			Move move = c.getMove();
			if(move != null) {
				move.apply();
			}
		}
		
		for(CollisionEntity c1 : ces) {
			//collision with entities
			ArrayList<CollisionEntity> cesInRange = game.entitySystem.collisionEntitiesInRange(c1.pos, 3.0);		
			if(game.collisionSystem.collide(c1.getMove()) != null) {
				c1.collide(null);
			}
			for(CollisionEntity c2 : cesInRange) {
				if(c1 == c2) continue;
				
				if(c1.pos.distance(c2.pos) < c1.getRadius() + c2.getRadius()) {
					c1.collide(c2);
				}
			}
		}
	}
}
