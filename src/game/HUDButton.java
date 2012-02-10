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
package game;

import java.awt.Point;

import util.Vector2D;

public class HUDButton {
	public Point position;
	public Vector2D direction;
	public boolean mouseOver = false;
	private Game game;
	
	public HUDButton(Game g) {
		game = g;
	}
	
	public double getRadius() {
		return 15;
	}
	
	public void process(double dt) {
		Point cursor = (Point)game.input.viewCursorPos.clone();
		cursor.y = game.view.windowSize.height - cursor.y;
		if(cursor.distance(position) < getRadius()) {
			mouseOver = true;
		}
		else if(mouseOver == true) {
			mouseOver = false;
		}
	}
}
