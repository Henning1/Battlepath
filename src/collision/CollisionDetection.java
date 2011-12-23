package collision;

import java.awt.Point;
import java.util.ArrayList;

import engine.Field;

import util.Line2D;
import util.Vector2D;

public class CollisionDetection {
	
	Field field;
	
	public CollisionDetection(Field field) {
		this.field = field;
	}
	
	
	public ArrayList<Line2D> relevantData(
			Vector2D position, Vector2D velocity, double radius) {
		
		double length = velocity.length();
		Vector2D topleft = new Vector2D(
				position.x() - length - radius, position.y() - length - radius);
		Vector2D bottomright = new Vector2D(
				position.x() + length + radius, position.y() + length + radius);
		
		Point a = field.tileIndexAt(topleft);
		Point b = field.tileIndexAt(bottomright);
		

		movePointIntoIndexBounds(a);
		movePointIntoIndexBounds(b);
		ArrayList<Line2D> collModel = new ArrayList<Line2D>();
		
		for(int x=a.x; x<=b.x;x++) {
			for(int y=a.y; y<=b.y; y++) {
				collModel.addAll(field.tiles[x][y].collisionModel);
			}
		}
		
		return collModel;
	}
	
	private void movePointIntoIndexBounds(Point p) {
		if(p.x >= field.tilesX) p.x = field.tilesX-1;
		if(p.x < 0) p.x = 0;
		if(p.y >= field.tilesY) p.y = field.tilesY-1;
		if(p.y < 0) p.y = 0;
	}
	
	public Line2D collideAndSlide(
			Vector2D position, Vector2D velocity, double radius) {
		
		
		
		ArrayList<Line2D> model = relevantData(position, velocity, radius);
		
		ArrayList<Vector2D> points = new ArrayList<Vector2D>();
		
		CollisionPackage closestCollision = null;
		

		
		
		return null; //collisionLine;
	}
}
