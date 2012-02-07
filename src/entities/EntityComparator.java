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

import util.Vector2D;

public class EntityComparator implements Comparable<EntityComparator> {
	
	Entity e=null;
	int dimension;
	boolean knowsEntity=true;
	Vector2D value;
	
	public EntityComparator(Entity e, int dimension) {
		this.e = e;
		this.dimension = dimension;
		value = e.pos;
	}
	
	public EntityComparator(double value, int dimension) {
		this.dimension = dimension;
		this.value = new Vector2D(value,value);
		knowsEntity = false;
	}
	
	public double getValue() {
		if(dimension == 1) {
			return value.x;
		} else if(dimension == 2) {
			return value.y;
		} else {
			return Double.NaN;
		}
	}
	
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	@Override
	public int compareTo(EntityComparator c) {
		if(getValue() > c.getValue()) return 1;
		if(getValue() == c.getValue()) return 0;
		else return -1;
	}

}
