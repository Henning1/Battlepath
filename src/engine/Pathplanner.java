package engine;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Pathplanner {
	
	Field field;
	Field fChecks;
	PriorityQueue<Node> fringe;
	HashMap<Point2D, Node> nodes;
	
	public Pathplanner(Field field) {
		this.field = field;
	}
	
	public ArrayList<Point2D> plan(Point2D start, Point2D goal) {
		fChecks = new Field(field.tilesX,field.tilesY);
		
		
		fringe = new PriorityQueue<Node>();
		nodes = new HashMap<Point2D, Node>();
		
		
		/*
		Node n1 = new Node(null, new Point2D.Double(0.1,0.1), 1,1);
		Node n2 = new Node(null, new Point2D.Double(0.2,0.2), 1.5,1);
		Node n3 = new Node(n1, new Point2D.Double(0.3,0.3), 1,1);
		
		fringe.add(n1);
		fringe.add(n2);
		fringe.add(n3);
		
		System.out.println(fringe.remove().getPos());
		System.out.println(fringe.remove().getPos());
		System.out.println(fringe.remove().getPos());
		*/
		
		
		
		
		
		fringe.add(new Node(null, start, h(start, goal)));
		
		Node success = astar(goal);
		
		//fChecks.printToConsole();
		
		if(success != null)
			return success.getPath();
		else return null;
		
	}
	
	
	
	public Node astar(Point2D goal) {
		int expands=0;
		while(!fringe.isEmpty()) {
			Node n = fringe.remove();
			expands++;
			//System.out.println(n + " from " + n.getPath().get(n.getPath().size()-1));
			//System.out.println(n.getPath() + "\n");
			
			ArrayList<Point2D> neighbours = field.freeneighbours(n.getPos());
			
			for(Point2D nb : neighbours) {
				Node nnew = new Node(n, nb, h(nb, goal));
				if(goalCheck(nnew, goal)) {
					System.out.println("Expanded Nodes: " + expands);
					return nnew;
				}
				
		
				
				Node old = nodes.get(nnew.getPos());
				if(old != null) {
					if(old.f > nnew.f) {
						nodes.remove(nnew.getPos());
						nodes.put(nnew.getPos(), nnew);
						fringe.remove(old);
						fringe.add(nnew);
						fChecks.setTile(nnew.getPos(), 1);
					}
				} else {
					fringe.add(nnew);
					nodes.put(nnew.getPos(), nnew);
					fChecks.setTile(nnew.getPos(), 1);
				}
			}
			
			
		}
		return null;
	}

	public boolean goalCheck(Node pos, Point2D goal) {
		return field.sameTile(pos.getPos(), goal);
	}
	
	public double h(Point2D n, Point2D goal) {
		
		if(n.equals(goal)) return 0;
		return n.distance(goal);
	}
	

	
    public class Node implements Comparable {
        private ArrayList<Point2D> moves=new ArrayList<Point2D>();
        private double f;
        private double cost;
        
        public Node(Node parent, Point2D pos, double h) {
        	if(parent != null) {
        		ArrayList<Point2D> pmoves = parent.moves;
        		moves.addAll(pmoves);
        		cost = parent.cost + pos.distance(pmoves.get(pmoves.size()-1));
        	}
        	else {
        		cost = 0;
        	}
        	moves.add(pos);
        	
        	f = h + cost;
        	
        	
        }
        
        public ArrayList<Point2D> getPath() {
        	return moves;        	        	
        }
        
        public double getCost() {
        	return cost;
        }
        
        public Point2D getPos() {
        	return moves.get(moves.size()-1);
        }
        
        @Override
        public String toString() {
        	return "Node: [" + getPos().getX() + ", " + getPos().getY() + ", f: " + f + ", l: " + this.getPath().size() + "]";
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