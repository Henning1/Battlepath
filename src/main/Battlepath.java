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

import javax.swing.JFrame;

import Entities.Tower;

import collision.CollisionSystem;

import util.Vector2D;

import engine.Field;
import engine.MainLoop;
import engine.Pathplanner;


public class Battlepath {
	static Random rand = new Random();
	
	public static void main(String[] args) {
		int tileSize = 20;
		int fieldWidth = 100;
		int fieldHeight = 100;
		Dimension windowSize = new Dimension(1000,800);
		Field f = new Field(fieldWidth, fieldHeight);
		randomCircles(f, fieldWidth*fieldHeight/50, 3);
		Vector2D start = findStartPos(f);
		

		Game game = new Game(start);
		OpenGLRenderer renderer = new OpenGLRenderer(game,tileSize);
		
		BFrame frame = new BFrame(windowSize, renderer);
		Dimension paneSize = frame.getContentPane().getSize();
		
		game.field = f;
		game.input = new Input(frame, game);
		game.pathPlanner =  new Pathplanner(f);
		game.collisionSystem = new CollisionSystem(f,game);
		game.view = new View(paneSize, tileSize, game);
		game.setMode(GameMode.ACTION);
		game.entities.addAll(randomTowers(f, fieldWidth*fieldHeight/200, game));
		
		MainLoop.startLoop(game, renderer, frame);
	}
	
	public static Vector2D findStartPos(Field f) {
		Point start = new Point(rand.nextInt(f.tilesX), rand.nextInt(f.tilesY));
		while(f.tiles[start.x][start.y].getType() == 1)
			start = new Point(rand.nextInt(f.tilesX), rand.nextInt(f.tilesY));
		return f.getWorldPos(start);
	}
	
	public static void randomCircles(Field f, int n, double maxr) {
		for(int i=0; i<n; i++) {
			f.createCricle(new Vector2D(rand.nextDouble()*f.tilesX, rand.nextDouble()*f.tilesY), rand.nextDouble()*maxr);
		}
	}
	
	public static ArrayList<Tower> randomTowers(Field f, int n, Game g) {
		ArrayList<Tower> list = new ArrayList<Tower>();
		for(int i=0; i<n;i++) {
			Point tower = new Point(rand.nextInt(f.tilesX), rand.nextInt(f.tilesY));
			while(f.tiles[tower.x][tower.y].getType() == 1)
				tower = new Point(rand.nextInt(f.tilesX), rand.nextInt(f.tilesY));
			list.add(new Tower(f.getWorldPos(tower), g));
		}
		return list;
	}
}
