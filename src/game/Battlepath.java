package game;
import interaction.Input;
import interaction.Renderer;
import interaction.WindowUtilities;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import engine.Field;
import engine.MainLoop;


public class Battlepath {
	static Random rand = new Random();
	
	public static void main(String[] args) {

		Field f = new Field(30,30);
		randomCircles(f,40,0.15);
	
		Point2D start;
		start = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
		while(f.tileAt(start) == 1)
			start = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
		
		
		JFrame frame = WindowUtilities.openFrame(700,700);
		
		Game game = new Game(f,start);
		Input input = new Input(game,frame);
		Renderer renderer = new Renderer(game,frame);

		MainLoop.startLoop(renderer, game);

	}
	
	public static void randomCircles(Field f, int n, double maxr) {
		for(int i=0; i<n; i++) {
			f.createCricle(new Point2D.Double(rand.nextDouble(), rand.nextDouble()), rand.nextDouble()*maxr);
		}
	}
}
