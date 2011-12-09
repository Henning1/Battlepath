package game;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import engine.Field;
import engine.Pathplanner;


public class Game {
	
	public Field f;
	Pathplanner p;
	public Unit u;
	public Point2D c = new Point2D.Double(0,0);

	public long frameTime=0;
	
	public Game(Field f, Point2D startpos) {
		this.f = f;
		this.p = new Pathplanner(f);
		u = new Unit(startpos, this);
	}
	
	
	public void click(Point2D clickPos) {
		u.moveTo(clickPos);
	}
	
	public void step(double dt) {
		u.process(dt);
	}
	
}
