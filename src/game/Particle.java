package game;

import engine.GlobalInfo;
import util.Vector2D;


public class Particle {

	public Vector2D direction;
	public Vector2D pos;
	public double speed;
	public double lifetime;
	public double life = 0;
	public double acceleration;
	public boolean destroyed = false;
	
	ParticleSystem system;
	
	public Particle(Vector2D position, Vector2D direction, double lifetime, double speed, double accel, ParticleSystem pS) {
		this.pos = position;
		this.direction = direction.normalize();
		this.lifetime = lifetime;
		this.speed = speed;
		acceleration = accel;
		system = pS;
	}
	
	public void process(double dt) {
		if(life + dt > lifetime) {
			destroyed = true;
		}
		else {
			life += dt;
		}
		
		if(!destroyed) {
			pos = pos.add(direction.scalar(speed*dt));
			speed += acceleration*dt;
		} else {
			system.deleteList.add(this);
		}
	}
}