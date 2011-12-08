package game;
import interaction.Input;
import interaction.Renderer;
import interaction.WindowUtilities;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import engine.Field;


public class Battlepath {
	static Random g = new Random();
	
	public static void main(String[] args) {

		Field f = new Field(20,20);
		randomCircles(f,40,0.15);
		
		Point2D start;
		start = new Point2D.Double(g.nextDouble(), g.nextDouble());
		
		while(f.tileAt(start) == 1)
			start = new Point2D.Double(g.nextDouble(), g.nextDouble());
		
		f.setTile(start, 2);
		
		Game game = new Game(f,start);
		Input input = new Input(game);
		Renderer renderer = new Renderer(game);
		
		/*
		long tstBefore;
		long tstAfter;

		tstBefore = System.currentTimeMillis();

		plan = planner.plan(start,goal);

		tstAfter = System.currentTimeMillis();
		System.out.println("Path calculations in: " + (tstAfter-tstBefore) + " ms");
		*/
		
		
		WindowUtilities.openInJFrame(renderer, 600, 600, "Battlepath", input);
		renderer.start();
		game.start();

	}
	
	public static void randomCircles(Field f, int n, double maxr) {
		
		
		for(int i=0; i<n; i++) {
			f.createCricle(new Point2D.Double(g.nextDouble(), g.nextDouble()), g.nextDouble()*maxr);
		}
	}
}
