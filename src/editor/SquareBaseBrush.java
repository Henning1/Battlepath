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
public abstract class SquareBaseBrush extends Brush {



	@Override
	public void paint(Field f, Vector2D pos) {
		ArrayList<Tile> edits = getPaint(f,pos);
		for(Tile t : edits) {
			f.tileAt(t.getIndex()).setValue(t.getValue());
		}

	}
	
	@Override
	public ArrayList<Tile> getPaint(Field f, Vector2D pos) {
		ArrayList<Tile> result = new ArrayList<Tile>();
		
		Point topleft = f.tileIndexAt(new Vector2D(pos.x-size, pos.y-size));
		Point bottomright = f.tileIndexAt(new Vector2D(pos.x+size, pos.y+size));
		f.clamp(topleft);
		f.clamp(bottomright);
		for(int x=topleft.x; x<bottomright.x; x++) {
			for(int y=topleft.y; y<bottomright.y; y++) {
				Tile t = editTile(f,pos,x,y);
				if(t==null) continue;
				if(t.getValue() != f.tileAt(x,y).getValue())
					result.add(t);
			}
		}
		
		return result;
	}
	
	public abstract Tile editTile(Field f, Vector2D pos,int x, int y);

}
