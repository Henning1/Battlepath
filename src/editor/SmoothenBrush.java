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
package editor;

import java.awt.Point;
import java.util.ArrayList;

import util.Line2D;
import util.Vector2D;
import engine.Field;
import engine.Tile;

/**
 * @author henning
 *
 */
public class SmoothenBrush extends SquareBrush {
	
	/**
	 * Set the value of the Tile
	 * 0: empty
	 * 1: square
	 * 2: triangle 
	 * 		|\
	 * 3: triangle
	 * 		|/
	 * 4: triangle
	 * 		\|
	 * 5: triangle
	 * 		/|
	 * @param value Value. 0: Free, 1: Obstacle
	 */
	
	@Override
	public Tile editTile(Field f, Vector2D pos, int x, int y) {
		if(f.tileAt(x,y).getValue() != 0) return null;
		
		boolean bottom, top, left, right;
		Tile tile = f.tileAt(x, y-1);
		bottom = tile == null || tile.getValue() == 1;
		tile = f.tileAt(x, y+1);
		top = tile == null || tile.getValue() == 1;
		tile = f.tileAt(x-1, y);
		left = tile == null || tile.getValue() == 1;
		tile = f.tileAt(x+1, y);
		right = tile == null || tile.getValue() == 1;
		
		if(left & top & !right & !bottom) {
			return new Tile(x,y,3);
		}
		else if(!left & top & right & !bottom) {
			return new Tile(x,y,4);
		}
		else if(!left & !top & right & bottom) {
			return new Tile(x,y,5);
		}
		else if(left & !top & !right & bottom) {
			return new Tile(x,y,2);
		}
		return null;
	}
}
