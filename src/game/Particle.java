package game;

import util.Vector2D;


public class Particle extends Entity {

	public Vector2D direction;
	
	public double speed;
	public double lifetime;
	public double life = 0;
	public double acceleration = 10;
	public boolean destroyed = false;
	
	public Particle(Vector2D position, Vector2D direction, double lifetime, double speed, Game game) {
		super(position,game);
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

	@Override
	public double getRadius() {
		return 0.01;
	}
}