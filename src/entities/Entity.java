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

import game.Game;
import game.Team;
import util.Vector2D;


public abstract class Entity {
	public Vector2D pos;
	public Vector2D velocity = new Vector2D(0,0);
	public Game game;
	public Team team;
	
	public Entity(Vector2D position, Game game, Team team) {
		pos = position;
		this.game = game;
		this.team = team;
	}

	public abstract void process(double dt);
	public abstract double getRadius();

	
	public Vector2D velocityDt() {
		return velocity.scalar(game.dt);
	}
}
