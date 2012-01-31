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
import game.Game;

import java.util.ArrayList;



public class MovementSystem {
	
	private ArrayList<Move> moves;
	private Game game;
	
	public MovementSystem(Game game) {
		this.game = game;
		moves = new ArrayList<Move>();
	}
	
	public void register(Move c) {
		moves.add(c);
	}
	
	public void process(ArrayList<CollisionEntity> ces) {
		for(CollisionEntity c1 : ces) {
			Move m1 = c1.getMove();
			if(m1 != null) m1.apply();
		}
		
		
		for(CollisionEntity c1 : ces) {
			Move m1 = c1.getMove();
			if(m1 == null) continue;
			
			//collision with level
			if(game.collisionSystem.collide(c1) != null) {
				c1.collide(null);
			}
			
			//collision with entities

			ArrayList<CollisionEntity> cesInRange = game.entitySystem.collisionEntitiesInRange(c1.pos, 3.0);
			
			for(CollisionEntity c2 : cesInRange) {
				
				if(c1 == c2) continue;
				/*
				Move m2 = c2.getMove();
				
				//static collision
				if(m2 == null) {
					
				}
				//dynamic collision
				else {
					m2.apply();
				}
				
				m1.apply();
				*/
				
				
				if(c1.pos.distance(c2.pos) < c1.getRadius() + c2.getRadius()) {
					c1.collide(c2);
					c2.collide(c1);
				}
				

			}
		}

		
	}
}
