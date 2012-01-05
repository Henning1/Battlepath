package game;

import java.awt.Dimension;
import java.awt.Point;

import util.Vector2D;

public class View {
	Dimension windowSize;
	Game game;
	
	public Vector2D offset;
	int tileSize;
	
	public View(Dimension size, int tileSize, Game g) {
		windowSize = size;
		offset = new Vector2D(0,0);
		this.tileSize = tileSize;
		game = g;
	}
	
	public Vector2D viewToWorld(Point viewPos) {
		Vector2D v = new Vector2D(viewPos.x/(double)tileSize, viewPos.y/(double)tileSize);
		return v.subtract(offset);
	}
	
	public void moveOffset(Vector2D move) {
		double maxOffsX = -game.field.tilesX+(windowSize.width/tileSize);
		double maxOffsY = -game.field.tilesY+(windowSize.height/tileSize);
		
		offset = offset.add(move);
		if(offset.x >= 0)
			offset.x = 0;
		if(offset.x <= maxOffsX)
			offset.x = maxOffsX;
		
		if(offset.y >= 0)
			offset.y = 0;
		if(offset.y <= maxOffsY)
			offset.y = maxOffsY;
		
	}

}
