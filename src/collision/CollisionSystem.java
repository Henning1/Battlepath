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
package collision;

import java.awt.Point;
import java.util.ArrayList;


import engine.Field;
import engine.GlobalInfo;
import engine.Tile;
import game.Game;

import util.Line2D;
import util.Util;
import util.Vector2D;

public class CollisionSystem {
	
	Field field;
	Game game;
	
	public CollisionSystem(Field field, Game game) {
		this.field = field;
		this.game = game;
	}
	
	
	public boolean collideWithLevel(Line2D l) {
		ArrayList<Tile> rTiles = getTilesOn(l);
		
		for(Tile t : rTiles) {
			ArrayList<Line2D> model = t.getCollisionModel();
			for(Line2D edge : model) {
				Vector2D i = l.intersectionPoint(edge);
				if(l.pointInSegment(i) && edge.pointInSegment(i))
					return true;
			}
		}

		return false;
	}

	public ArrayList<Tile> getTilesOn(Line2D l) {
		
		ArrayList<Tile> result = new ArrayList<Tile>();
		
		//normalize line from left to right
		Line2D line = l;
		if(l.a.x > l.b.x) {
			line = new Line2D(l.b,l.a);
		}
		
		Tile current = field.tileAt(line.a);
		Tile destination = field.tileAt(line.b);
		
		while(current != destination && current != null) {
			result.add(current);
			//vertical line
			if(line.direction.y == 0) {
				current = field.tileAt(current.getCenter().add(line.direction));
				continue;
			}
			
			//line penetrates right edge
			double y = line.yAt(current.getBottomright().x);
			if(Util.isValueInBounds(current.getTopleft().y, y, current.getBottomright().y)) {
				current = field.tileAt(current.getIndex().x+1,current.getIndex().y);
			} else {
				//line penetrates top edge
				if(line.a.y > line.b.y) {
					current = field.tileAt(current.getIndex().x,current.getIndex().y-1);
				} 
				//line penetrates bottom edge
				else {
					current = field.tileAt(current.getIndex().x,current.getIndex().y+1);
				}
			}
		}
		if(current == destination) result.add(current);

		return result;
		
	}
	
	public ArrayList<Line2D> relevantData(Move m) {
		Vector2D velocity = m.v;
		
		double length = velocity.length();
		Vector2D topleft = m.basepoint.subtract(m.e.getRadius()+length*2);
		Vector2D bottomright = m.basepoint.add(m.e.getRadius()+length*2);
		
		Point a = field.tileIndexAt(topleft);
		Point b = field.tileIndexAt(bottomright);

		field.clamp(a);
		field.clamp(b);
		
		ArrayList<Line2D> collModel = new ArrayList<Line2D>();
		
		collModel.addAll(field.getBoundingFrame());
		
		Tile[][] tiles = field.getTiles();
		
		for(int x=a.x; x<=b.x;x++) {
			for(int y=a.y; y<=b.y; y++) {
				collModel.addAll(tiles[x][y].getCollisionModel());
			}
		}
		
		return collModel;
	}
	
	public void collideAndSlide(Move m) {
		if(m.v.equals(GlobalInfo.nullVector)) return;
		pCollideAndSlide(m,3);
	}
	
	private void pCollideAndSlide(Move m,int d) {
		if(d==0) return;
		
		ArrayList<Line2D> model = relevantData(m);	
		Collision closestCollision = collide(model,m);

		if(closestCollision != null) {
			System.out.println(closestCollision);
			//slide to obstacle
			m.slide(closestCollision);
			//recurse
			if(!m.finished)
				pCollideAndSlide(m,--d);
		}		
	}

	public Collision collide(Move m) {
		if(m == null) return null;
		return collide(relevantData(m), m);
	}
	
	private Collision collide(ArrayList<Line2D> model, Move m) {
		if(m == null) return null;
		Collision closestCollision = null;
		double howClose = Double.MAX_VALUE;
		for(Line2D line : model) {
			Collision cp = new Collision(m,line);
			cp.calcIntersection();
			if(cp.collision && cp.distance < howClose) {
				closestCollision = cp;
				howClose = cp.distance;
			}
		}
		return closestCollision;
	}
}
