package entities;

import collision.Move;
import game.Game;
import util.Vector2D;

public abstract class CollisionEntity extends Entity {
	
	protected Move move;
	
	public CollisionEntity(Vector2D position, Game game) {
		super(position, game);
	}
	
	public abstract void collide(CollisionEntity e);
	public Move getMove() {
		return move;
	}
}
