package game;

import util.Vector2D;


public class Projectile extends Entity {

	public Vector2D direction;
	
	double speed = 10;
	
	public Projectile(Vector2D position, Vector2D direction) {
		super(position);
		this.direction = direction.normalize();
	}
	
	@Override
	public void process(double dt) {
		pos = pos.add(direction.scalar(speed*dt));
	}
}