package engine;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/*
 * A field stores the Level information. It is separated in square Tiles.
 * (0,0) is bottom left, (1,1) is bottom right.
 * At this point a Tile with value 0 is empty  and a tile with value 1 an obstacle
 */


public class Field {
	public int tiles[][];
	public int tilesX=0;
	public int tilesY=0;
	
	public Field(int numX, int numY) {
		tiles = new int[numX][numY];
		tilesX = numX;
		tilesY = numY;	
	}
	
	public void createCricle(Point2D pos, double r) {
		for(int x=0; x<tilesX; x++) {
			for(int y=0;y<tilesY; y++) {
				if(pos.distance(getWorldPos(new Point(x,y))) < r)
					tiles[x][y] = 1;
			}
		}
		
	}
	
	//Returns the position of the center of a Tile
	public Point2D getWorldPos(Point pos) {
		return new Point2D.Double(
				(double)pos.x/(double)tilesX+tilesize()/2,
				(double)pos.y/(double)tilesY+tilesize()/2);
	}
	
	public void setTile(Point2D pos, int value) {
		Point p = tilePosAt(pos);
		tiles[p.x][p.y] = value;
	}
	
	private Point tilePosAt(Point2D pos) {
		return new Point(
				(int)(pos.getX()*(double)tilesX),
				(int)(pos.getY()*(double)tilesY));
	}
	
	public int tileAt(Point2D pos) {
		Point tile = tilePosAt(pos);
		if(tile.x>=tilesX || tile.x < 0
		 ||tile.y>=tilesY || tile.y < 0)
			return 1;
		return tiles[tile.x][tile.y];
	}
	
	public double tilesize() {
		return 1.0/(double)tilesX;
	}
	
	public ArrayList<Point2D> neighbours(Point2D pos) {
		ArrayList<Point2D> result = new ArrayList<Point2D>();
		
		Point tile = tilePosAt(pos);
		
		Point2D left,right,top,bottom;
		left = getWorldPos(new Point(tile.x-1,tile.y));
		right = getWorldPos(new Point(tile.x+1,tile.y));
		top = getWorldPos(new Point(tile.x,tile.y-1));
		bottom = getWorldPos(new Point(tile.x,tile.y+1));
		
		if(tileAt(left) != 1 && tileAt(top) != 1)
			result.add(getWorldPos(new Point(tile.x-1,tile.y-1)));
		if(tileAt(right) != 1 && tileAt(top) != 1)
			result.add(getWorldPos(new Point(tile.x+1,tile.y-1)));
		if(tileAt(left) != 1 && tileAt(bottom) != 1)
			result.add(getWorldPos(new Point(tile.x-1,tile.y+1)));
		if(tileAt(right) != 1 && tileAt(bottom) != 1)
			result.add(getWorldPos(new Point(tile.x+1,tile.y+1)));
		
		result.add(left);
		result.add(right);
		result.add(top);
		result.add(bottom);
		
		
		
		result = removeInvalidTiles(result);
		
		
		return result;
		
	}
	
	public void setTilesTo(ArrayList<Point2D> tiles, int value) {
		for(Point2D t : tiles) {
			setTile(t, value);
		}
	}
	
	public ArrayList<Point2D> removeInvalidTiles(ArrayList<Point2D> tiles) {
		for(int i=0; i<tiles.size();i++) {
			Point2D p =tiles.get(i);
			if(p.getX() < 0 || p.getX() > 1.0 ||
			   p.getY() < 0 || p.getY() > 1.0) {
				tiles.remove(i);
				i--;
			}
		}
		return tiles;
	}
	
	public ArrayList<Point2D> removeNotOnes(ArrayList<Point2D> tiles) {
		for(int i=0; i<tiles.size();i++) {
			Point2D p = tiles.get(i);
			if(this.tileAt(p) == 1) {
				tiles.remove(i);
				i--;
			}
				
		}
		return tiles;
	}
	public ArrayList<Point2D> freeneighbours(Point2D pos) {
		ArrayList<Point2D> result = neighbours(pos);
		
		result = removeNotOnes(result);
		
		return result;
	}
	
	public boolean sameTile(Point2D a, Point2D b) {
		return this.tilePosAt(a).equals(this.tilePosAt(b));
	}
	
	public void printToConsole() {
		for(int y=0; y<tilesY; y++) {
			for(int x=0;x<tilesX; x++) {
				System.out.print(tiles[x][y]);
			}
			System.out.println();
		}
		
	}
	
}
