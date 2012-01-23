package entities;

import engine.GlobalInfo;
import game.Game;
import util.Vector2D;

public class Tower extends HealthEntity {
	public Vector2D aim = new Vector2D(0,0);
	double lastShot;
	
	public Tower(Vector2D position, Game game) {
		super(position, game);
		move = null;
	}

	@Override
	public void process(double dt) {
		aim = game.u.pos.subtract(pos).normalize();
		if(pos.distance(game.u.pos) < 20 && GlobalInfo.time-lastShot > 1) {
			game.emitShot(pos.add(aim.scalar(getRadius())), aim);
			lastShot = GlobalInfo.time;
		}
	}

	@Override
	public double getRadius() {
		return 1;
	}

	@Override
	public void collide(CollisionEntity e) {
		/*
		if(e instanceof Projectile) {
			Projectile p = (Projectile)e;
			damage(p.power);
		}*/
	}

}
