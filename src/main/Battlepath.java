package main;
import game.Game;
import game.View;
import interaction.Input;
import interaction.Renderer;
import interaction.WindowUtilities;

import java.awt.Dimension;
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
		int fieldWidth = 200;
		int fieldHeight = 200;
		Dimension windowSize = new Dimension(1000,800);
		Field f = new Field(fieldWidth, fieldHeight);
		randomCircles(f, fieldWidth*fieldHeight/50, 3);
		Vector2D start = findStartPos(f);
		
		JFrame frame = WindowUtilities.openFrame(windowSize);
		Dimension paneSize = frame.getContentPane().getSize();

		Game game = new Game(start);
		Renderer renderer = new Renderer(game,tileSize,frame);
		
		game.field = f;
		game.input = new Input(frame, renderer, game);
		game.pathPlanner =  new Pathplanner(f);
		game.collisionSystem = new CollisionSystem(f,game);
		game.view = new View(paneSize, tileSize, game);
		
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
