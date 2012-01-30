package fx;

import util.Vector2D;

public class Particle extends FxEntity {

	public Vector2D direction;
	public double acceleration;
	public double speed;
	EffectsSystem system;
	
	public Particle(Vector2D position, Vector2D direction, double lifetime, double speed, double accel, EffectsSystem pS) {
		super(pS,lifetime,position);
		this.direction = direction.normalize();
		this.speed = speed;
		acceleration = accel;
	}
	
	public void process(double dt) {
		super.process(dt);
		pos = pos.add(direction.scalar(speed*dt));
		speed += acceleration*dt;
	}


}