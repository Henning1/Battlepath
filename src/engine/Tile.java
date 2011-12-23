package engine;

import java.util.ArrayList;

import util.Line2D;
import util.Vector2D;

public class Tile {
	
	public ArrayList<Line2D> collisionModel = new ArrayList<Line2D>();
	public Vector2D topleft, bottomright;
	private int type;
	
	
	public Tile(Vector2D topleft, Vector2D bottomright, int type) {
		this.type = type;
		this.topleft = topleft;
		this.bottomright = bottomright;
	
		

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
}
