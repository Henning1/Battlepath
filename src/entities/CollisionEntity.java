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

import collision.Move;
import game.Game;
import util.Vector2D;

public abstract class CollisionEntity extends Entity {
	
	protected Move move;
	
	public CollisionEntity(Vector2D position, Game game) {
		super(position, game);
	}
	
	public abstract void collide(CollisionEntity e);
	public Move getMove() {
		return move;
	}
}
