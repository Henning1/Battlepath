package game;

import util.Vector2D;
import java.util.ArrayList;
import collision.CollisionSystem;
import collision.Collision;
import collision.Move;
import util.Vector2D;

import engine.GlobalInfo;


public abstract class Entity {
	public Vector2D pos;
	public Vector2D velocity = new Vector2D(0,0);
	public Game game;
	
	public Entity(Vector2D position, Game game) {
		pos = position;
		this.game = game;
	}

	public abstract void process(double dt);
	public abstract double getRadius();

	
	public Vector2D velocityDt() {
		return velocity.scalar(game.dt);
	}

}
