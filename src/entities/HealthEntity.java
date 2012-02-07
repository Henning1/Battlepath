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

import game.Game;
import game.HUDButton;
import game.HUDMenu;
import game.Team;
import util.Vector2D;

public abstract class HealthEntity extends CollisionEntity{
	
	protected double health = 100;
	public boolean alive = true;
	public boolean isSelected = false;
	public double lastShot = 0;
	public HUDMenu menu;
	
	public HealthEntity(Vector2D position, Game game, Team team) {
		super(position, game, team);
		menu = new HUDMenu(this, getButtons(), game);
	}

	protected abstract ArrayList<HUDButton> getButtons();
	
	public void process(double dt) {
		menu.process(dt);
	}
	
	public double getHealth() {
		return health;
	}
	
	public void damage(double d) {
		health -= d;
		if(health <= 0) {
			closeMenu();
			game.particleSystem.explosion(pos);
			game.entitySystem.remove(this);
			isSelected = false;
			alive = false;
		}
	}
	
	public void openMenu() {
		menu.open();
	}
	
	public void closeMenu() {
		menu.close();
	}
	
	public boolean isMenuOpen() {
		return menu.isOpen();
	}
	
	public boolean isMenuVisible() {
		return menu.isVisible();
	}
	
}
