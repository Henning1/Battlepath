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
	
	public Game(Field f, Point2D startpos) {
		this.f = f;
		this.p = new Pathplanner(f);
		u = new Unit(startpos, this);
	}
	
	
	public void leftclick(Point2D clickPos) {
		u.moveTo(clickPos);
	}
	
	public void middleclick(Point2D clickPos) {
		//Should there be a method for getting an angle from two Point2Ds?
		double direction = Math.atan((u.pos.getY()-clickPos.getY())/(u.pos.getX()-clickPos.getX()));
		u.emitProjectile(direction);
	}
	
	public void rightclick(Point2D clickPos) {
		u.pos = clickPos;
	}
	
	public void step(double dt) {
		u.process(dt);
	}
	
}
