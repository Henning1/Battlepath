package engine;

import java.awt.Point;
import java.util.ArrayList;

import util.Line2D;
import util.Vector2D;

public class Tile {
	
	public ArrayList<Line2D> collisionModel = new ArrayList<Line2D>();
	//bounding box
	public Vector2D topleft, bottomright, center;
	public Point index;
	private int type;
	
	
	public Tile(Point index, int type) {
		this.type = type;
		this.index = index;
		this.topleft = new Vector2D(index.x,index.y);
		this.bottomright = new Vector2D(index.x+1,index.y+1);
		this.center = new Vector2D((double)index.x+0.5,(double)index.y+0.5);
	
		

	}
	
	public void setType(int type) {
		this.type = type;
		collisionModel.clear();
		Vector2D bottomleft = new Vector2D(topleft.x(), bottomright.y());
		Vector2D topright = new Vector2D(bottomright.x(), topleft.y());
		if(type==1) {
			collisionModel.add(new Line2D(topleft,bottomleft));
			collisionModel.add(new Line2D(bottomleft,bottomright));
			collisionModel.add(new Line2D(bottomright, topright));
			collisionModel.add(new Line2D(topright, topleft));
		}
	}
	
	public int getType() {
		return type;
	}
	
	public String toString() {
		return Integer.toString(type);
	}
}
