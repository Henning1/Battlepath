package entities;

import game.Game;
import collision.Move;
import util.Vector2D;


public class Projectile extends CollisionEntity {


	public Vector2D direction;
	public Vector2D origin;
	public double power = 25;
	public double length = 1.3;
	double speed = 50;
	
	public Projectile(Vector2D position, Vector2D direction, Game game) {
		super(position,game);
		this.direction = direction.normalize();
		this.origin = position;
	}
	
	@Override
	public void process(double dt) {
		velocity = direction.scalar(speed);
		move = new Move(this,dt);
		move.move();
		game.particleSystem.explosion(pos, 5, 0.1);
	}

	@Override
	public double getRadius() {
		return 0.01;
	}
	
	public void collide(CollisionEntity c) {
		game.entities.remove(this);
		
		if(c instanceof HealthEntity) {
			HealthEntity h = (HealthEntity)c;
			h.damage(power);
			game.particleSystem.explosion(pos, 500, 1);
		}
		else {
			game.particleSystem.explosion(pos, 100, 1);
		}
	}
	
}