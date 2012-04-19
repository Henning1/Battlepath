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
package engine;

import java.awt.Point;
import java.util.ArrayList;

import util.Line2D;
import util.Vector2D;

/**
 * Class representing one tile on the Field.
 */
public class Tile {
	
	private ArrayList<Line2D> model = new ArrayList<Line2D>();
	//bounding box
	private Vector2D topleft, bottomright, center;
	private Point index;
	private int value;
	
	/* value codes:
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
	 */
	
	
	/**
	 * @param index Field index of the Tile
	 * @param value Value of the Tile (0: Free, 1: Obstacle)
	 */
	public Tile(Point index, int value) {
		this.value = value;
		this.index = index;
		this.topleft = new Vector2D(index.x,index.y+1);
		this.bottomright = new Vector2D(index.x+1,index.y);
		this.center = new Vector2D((double)index.x+0.5,(double)index.y+0.5);
	}
	
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
	public void setValue(int value) {
		this.value = value;
		model.clear();
		Vector2D bottomleft = new Vector2D(topleft.x(), bottomright.y());
		Vector2D topright = new Vector2D(bottomright.x(), topleft.y());
		if(value==1) {
			model.add(new Line2D(topleft,topright));
			model.add(new Line2D(topright,bottomright));
			model.add(new Line2D(bottomright,bottomleft));
			model.add(new Line2D(bottomleft,topleft));
		}
		if(value==2) {
			model.add(new Line2D(topleft,bottomright));
			model.add(new Line2D(bottomright,bottomleft));
			model.add(new Line2D(bottomleft,topleft));
		}
		if(value==3) {
			model.add(new Line2D(topleft,topright));
			model.add(new Line2D(topright,bottomleft));
			model.add(new Line2D(bottomleft,topleft));
		}
		if(value==4) {
			model.add(new Line2D(topleft,topright));
			model.add(new Line2D(topright,bottomright));
			model.add(new Line2D(bottomright,topleft));
		}
		if(value==5) {
			model.add(new Line2D(topright,bottomright));
			model.add(new Line2D(bottomright,bottomleft));
			model.add(new Line2D(bottomleft,topright));
		}
	}
	
	public int getValue() {
		return value;
	}
	
	public String toString() {
		return Integer.toString(value);
	}

	public Vector2D getCenter() {
		return center;
	}

	public Point getIndex() {
		return index;
	}

	public Vector2D getTopleft() {
		return topleft;
	}

	public Vector2D getBottomright() {
		return bottomright;
	}

	public ArrayList<Line2D> getCollisionModel() {
		return model;
	}
}
