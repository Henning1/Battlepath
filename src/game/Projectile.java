package game;

import util.Vector2D;


public class Projectile extends Entity {

	Game game;
	public Vector2D direction;
	
	double speed = 1;
	
	public Projectile(Vector2D position, Vector2D direction, Game game) {
		super(position);
		this.game = game;
		this.direction = direction.normalize();
	}
	
	@Override
	public void process(double dt) {
		pos = pos.add(direction.scalar(speed*dt));
	}
}