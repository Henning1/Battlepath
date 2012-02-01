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
import java.util.HashMap;
import java.util.PriorityQueue;

import util.Vector2D;

/**
 * Path planning class
 */
public class Pathplanner {
	
	private Field field;
	private Field fChecks;
	private PriorityQueue<Node> fringe;
	private HashMap<Integer, Node> nodes;
	
	/**
	 * @param field Field to use
	 */
	public Pathplanner(Field field) {
		this.field = field;
	}
	
	/**
	 * Plans a path from a start wold position to a goal world position
	 * @param start start position
	 * @param goal goal position
	 * @return list of needed moves go from start to goal
	 */
	public ArrayList<Vector2D> plan(Vector2D start, Vector2D goal) {
		Tile tile = field.tileAt(goal);
		if(tile == null) return null;
		if(tile.getValue() == 1) return null;
		fChecks = new Field(field.getTilesX(),field.getTilesY());
		fringe = new PriorityQueue<Node>();
		nodes = new HashMap<Integer, Node>();
	
		fringe.add(new Node(null, start, h(start, goal)));
		
		Node success = astar(goal);
		
		if(success != null)
			return success.getPath();
		else return null;
		
	}
	
	private Node astar(Vector2D goal) {
		int expands=0;
		while(!fringe.isEmpty()) {
			Node n = fringe.remove();
			expands++;
			
			ArrayList<Vector2D> neighbours = field.freeneighbours(n.getPos());
			
			for(Vector2D nb : neighbours) {
				Node nnew = new Node(n, nb, h(nb, goal));
				if(goalCheck(nnew, goal)) {
					return nnew;
				}
				
				int identifier = getIdentifier(nnew.getPos());
				Node old = nodes.get(identifier);
				if(old != null) {
					if(old.f > nnew.f) {
						nodes.remove(identifier);
						nodes.put(identifier, nnew);
						fringe.remove(old);
						fringe.add(nnew);
						fChecks.setTile(nnew.getPos(), 1);
					}
				} else {
					fringe.add(nnew);
					nodes.put(identifier, nnew);
					fChecks.setTile(nnew.getPos(), 1);
				}
			}
			
			
		}
		System.out.println("No success, expanded Nodes: " + expands);
		return null;
	}
	

	private boolean goalCheck(Node pos, Vector2D goal) {
		return field.sameTile(pos.getPos(), goal);
	}
	
	private double h(Vector2D n, Vector2D goal) {
		
		if(n.equals(goal)) return 0;
		return n.distance(goal);
	}
	
	private int getIdentifier(Vector2D tile) {
		Point index = field.tileIndexAt(tile);
		return index.y*field.getTilesX() + index.x;
	}

    private class Node implements Comparable<Object> {
        private ArrayList<Vector2D> moves=new ArrayList<Vector2D>();
        private double f;
        private double cost;
        
        public Node(Node parent, Vector2D pos, double h) {
        	if(parent != null) {
        		ArrayList<Vector2D> pmoves = parent.moves;
        		moves.addAll(pmoves);
        		cost = parent.cost + pos.distance(pmoves.get(pmoves.size()-1));
        	}
        	else {
        		cost = 0;
        	}
        	moves.add(pos);
        	
        	f = h + cost;
        }
        
        public ArrayList<Vector2D> getPath() {
        	return moves;        	        	
        }
        
        public Vector2D getPos() {
        	return moves.get(moves.size()-1);
        }
        
        @Override
        public String toString() {
        	return "Node: [" + getPos().x() + ", " + getPos().y() + ", f: " + f + ", l: " + this.getPath().size() + "]";
        }

		@Override
		public int compareTo(Object arg0) {
			if(!(arg0 instanceof Node)) return Integer.MAX_VALUE;
			Node n = (Node)arg0;
			return Double.compare(f,n.f);
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof Node) {
				Node n = (Node)o;
				if(n.f==f && n.moves.size() == moves.size())
					return true;
			}
			return false;
		}
        
        
        
    }

	
}