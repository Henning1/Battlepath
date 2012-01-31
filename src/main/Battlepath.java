package main;
import game.Game;
import game.GameMode;
import game.View;
import interaction.Input;
import interaction.OpenGLRenderer;
import interaction.BFrame;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import collision.CollisionSystem;

import util.Vector2D;

import engine.Field;
import engine.MainLoop;
import engine.Pathplanner;
import entities.Entity;
import entities.Tower;

/**
 * Main class of the Battlepath game.
 * @author Battlepath team
 * @version 0
 */
public class Battlepath {
	static Random rand = new Random();
	
	/**
	 * Main method
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		int tileSize = 20;
		int fieldWidth = 100;
		int fieldHeight = 100;
		Dimension windowSize = new Dimension(1000,800);
		Field f = new Field(fieldWidth, fieldHeight);
		randomCircles(f, fieldWidth*fieldHeight/50, 3);
		Vector2D start = findStartPos(f);

		Game game = new Game(start);
		
		BFrame frame = new BFrame(windowSize);
		Dimension paneSize = frame.getContentPane().getSize();
		
		OpenGLRenderer renderer = new OpenGLRenderer(game,tileSize,frame);
		game.field = f;
		game.input = new Input(frame, game);
		game.pathPlanner =  new Pathplanner(f);
		game.collisionSystem = new CollisionSystem(f,game);
		game.view = new View(paneSize, tileSize, game);
		game.setMode(GameMode.STRATEGY);
		game.entities.addAll(randomTowers(f, 20, game));
		game.entities.applyChanges();
		
		MainLoop.startLoop(game, renderer, frame);
	}
	/**
	 * Finds valid start position for the Unit
	 * @param f Used field
	 * @return Valid position
	 */
	public static Vector2D findStartPos(Field f) {
		Point start = new Point(rand.nextInt(f.tilesX), rand.nextInt(f.tilesY));
		while(f.tiles[start.x][start.y].getValue() == 1)
			start = new Point(rand.nextInt(f.tilesX), rand.nextInt(f.tilesY));
		return f.getWorldPos(start);
	}
	
	/**
	 * Creates random circles on a map. Currently the only map generation algorithm.
	 * @param f Used field
	 * @param n Number of circles
	 * @param maxr Maximal radius of circles
	 */
	public static void randomCircles(Field f, int n, double maxr) {
		for(int i=0; i<n; i++) {
			f.createCircle(new Vector2D(rand.nextDouble()*f.tilesX, rand.nextDouble()*f.tilesY), rand.nextDouble()*maxr);
		}
	}
	
	/**
	 * Generates randomly spread towers on a map
	 * @param f Used field
	 * @param n Number of towers
	 * @param g Used game
	 * @return List of towers
	 */
	public static ArrayList<Entity> randomTowers(Field f, int n, Game g) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		for(int i=0; i<n;i++) {
			Point tower = new Point(rand.nextInt(f.tilesX), rand.nextInt(f.tilesY));
			while(f.tiles[tower.x][tower.y].getValue() == 1)
				tower = new Point(rand.nextInt(f.tilesX), rand.nextInt(f.tilesY));
			list.add(new Tower(f.getWorldPos(tower), g));
		}
		return list;
	}
}
