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
package util;

public class Rectangle2D {
	public Vector2D topleft;
	public Vector2D bottomright;
	
	public Rectangle2D(Vector2D topleft, Vector2D bottomright){
		this.topleft = topleft;
		this.bottomright = bottomright;
		
		if(topleft.x > bottomright.x) {
			this.topleft.x = bottomright.x;
			this.bottomright.x = topleft.x;
		}
		
		if(topleft.y < bottomright.y) {
			this.topleft.y = bottomright.y;
			this.bottomright.y = topleft.y;
		}
	}
	
	public double top() {return topleft.y;}
	public double bottom() {return bottomright.y;}
	public double right() {return bottomright.x;}
	public double left() {return topleft.x;}
	
	public boolean inside(Vector2D p) {
		return Util.isValueInBounds(topleft.x, p.x, bottomright.x) &&
				Util.isValueInBounds(bottomright.y, p.y, topleft.y);
	}
	
	public boolean inside(Vector2D p, double tolerance) {
		return Util.isValueInBounds(topleft.x-tolerance, p.x, bottomright.x+tolerance) &&
				Util.isValueInBounds(bottomright.y-tolerance, p.y, topleft.y+tolerance);
	}
	
	public String toString() {
		return "top: "+top()+" right: "+right()+"bottom: "+bottom()+" left:" +left();
	}
}