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

import java.util.ArrayList;
import engine.Field;
import engine.Tile;
import util.Line2D;
import util.Vector2D;

/**
 * @author henning
 *
 */
public abstract class Brush {
	public abstract ArrayList<Tile> getPaint(Field f, Vector2D pos);
	public abstract void paint(Field f, Vector2D pos);
	public int filltype=1;
	public double size=3;
}
