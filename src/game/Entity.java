package game;
import java.awt.geom.Point2D;


public abstract class Entity {
	public Point2D pos;
	
	public Entity(Point2D position) {
		pos = position;
	}

	public abstract void process(double dt);
}
