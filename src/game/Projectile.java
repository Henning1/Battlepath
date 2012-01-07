package game;

import collision.Move;
import util.Vector2D;


public class Projectile extends Entity {


	public Vector2D direction;
	double speed = 50;
	
	public Projectile(Vector2D position, Vector2D direction, Game game) {
		super(position,game);
		this.direction = direction.normalize();
	}
	
	@Override
	public void process(double dt) {
		velocity = direction.scalar(speed);
		Move move = new Move(this,dt);
		move.move();
		
		if(game.collisionSystem.collide(this) != null) {
			game.particleExplosion(pos, 200);
			game.deleteList.add(this);
		} else {
			move.apply();
		}
		
		
	}

	@Override
	public double getRadius() {
		return 0.01;
	}
}