package entities;

import game.Game;
import util.Vector2D;

public abstract class HealthEntity extends CollisionEntity{
	
	protected double health = 100;
	
	public HealthEntity(Vector2D position, Game game) {
		super(position, game);
	}

	public double getHealth() {
		return health;
	}
	
	public void damage(double d) {
		health -= d;
		if(health <= 0) {
			game.entities.remove(this);
		}
	}
	
}
