package game;

import util.Vector2D;


public class Particle extends Entity {

	public Vector2D direction;
	
	double speed;
	double lifetime;
	double life = 0;
	double acceleration = 10;
	public boolean destroyed = false;
	
	public Particle(Vector2D position, Vector2D direction, double lifetime, double speed) {
		super(position);
		this.direction = direction.normalize();
		this.lifetime = lifetime;
		this.speed = speed;
	}
	
	@Override
	public void process(double dt) {
		if(life + dt > lifetime) {
			destroyed = true;
		}
		else {
			life += dt;
		}
		
		if(destroyed == false) {
			pos = pos.add(direction.scalar(speed*dt));
			speed += acceleration*dt;
		}
	}
}