package main;
import game.Game;
import interaction.Input;
import interaction.Renderer;
import interaction.WindowUtilities;

import java.util.Random;

import javax.swing.JFrame;

import collision.CollisionSystem;

import util.Vector2D;

import engine.Field;
import engine.MainLoop;
import engine.Pathplanner;


public class Battlepath {
	static Random rand = new Random();
	
	public static void main(String[] args) {
		int tileSize = 20;
		int fieldWidth = 60;
		int fieldHeight = 30;
		Field f = new Field(fieldWidth, fieldHeight);
		randomCircles(f, fieldWidth*fieldHeight/50, 3);
		Vector2D start = findStartPos(f);
		
		JFrame frame = WindowUtilities.openFrame(f.tilesX*tileSize,f.tilesY*tileSize);

		Game game = new Game(start);
		Renderer renderer = new Renderer(game,tileSize,frame);
		
		game.field = f;
		game.input = new Input(frame, renderer);
		game.pathPlanner =  new Pathplanner(f);
		game.collisionSystem = new CollisionSystem(f,game);
		
		MainLoop.startLoop(renderer, game);

	}
	
	public static Vector2D findStartPos(Field f) {
		Vector2D start;
		start = new Vector2D(rand.nextDouble()*f.tilesX, rand.nextDouble()*f.tilesY);
		while(f.tileValueAt(start) == 1)
			start = new Vector2D(rand.nextDouble()*f.tilesX, rand.nextDouble()*f.tilesY);
		return start;
	}
	
	public static void randomCircles(Field f, int n, double maxr) {
		for(int i=0; i<n; i++) {
			f.createCricle(new Vector2D(rand.nextDouble()*f.tilesX, rand.nextDouble()*f.tilesY), rand.nextDouble()*maxr);
		}
	}
}
