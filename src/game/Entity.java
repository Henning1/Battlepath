package game;

import util.Vector2D;


public abstract class Entity {
	public Vector2D pos;
	
	public Entity(Vector2D position) {
		pos = position;
	}

	public abstract void process(double dt);
}
