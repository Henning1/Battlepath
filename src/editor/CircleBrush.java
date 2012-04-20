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
import java.util.Random;

import util.Line2D;
import util.Vector2D;
import engine.Field;
import engine.Tile;

/**
 * @author henning
 *
 */
public class CircleBrush extends SquareBrush {

	/* (non-Javadoc)
	 * @see editor.SquareBrush#editTile(engine.Field, int, int)
	 */
	@Override
	public Tile editTile(Field f, Vector2D pos, int x, int y) {
		if(pos.distance(f.getWorldPos(new Point(x,y))) < size)
			return new Tile(x,y,filltype);
		return null;
	}




}
