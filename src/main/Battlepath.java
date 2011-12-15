package main;
import game.Game;
import interaction.Input;
import interaction.Renderer;
import interaction.WindowUtilities;

import java.util.Random;

import javax.swing.JFrame;

import util.Line2D;
import util.Vector2D;

import engine.Field;
import engine.MainLoop;


public class Battlepath {
	static Random rand = new Random();
	
	public static void main(String[] args) {
		
		
		Vector2D a = new Vector2D(0,0);
		Vector2D b = new Vector2D(0,1);
		Vector2D c = new Vector2D(1,1);
		Line2D l = new Line2D(a, b);
		System.out.println(l.signedDistance(c));
		
		
		Field f = new Field(30,30);
		randomCircles(f,40,0.15);
	
		Vector2D start;
		start = new Vector2D(rand.nextDouble(), rand.nextDouble());
		while(f.tileValueAt(start) == 1)
			start = new Vector2D(rand.nextDouble(), rand.nextDouble());
		
		
		JFrame frame = WindowUtilities.openFrame(700,700);
		
		Game game = new Game(f,start);
		new Input(game,frame);
		Renderer renderer = new Renderer(game,frame);

		MainLoop.startLoop(renderer, game);

	}
	
	public static void randomCircles(Field f, int n, double maxr) {
		for(int i=0; i<n; i++) {
			f.createCricle(new Vector2D(rand.nextDouble(), rand.nextDouble()), rand.nextDouble()*maxr);
		}
	}
}
