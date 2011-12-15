package game;

import util.Vector2D;


public class Projectile extends Entity {

	Game game;
	public Vector2D direction;
	
	double speed = 0;
	
	public Projectile(Vector2D position, Vector2D direction, Game game) {
		super(position);
		this.game = game;
		this.direction = direction.normalize();
		speed = 1 * game.tileSize;
	}
	
	@Override
	public void process(double dt) {
		pos = pos.add(direction.scalar(speed*dt));
	}
}