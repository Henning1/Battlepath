package game;

import java.awt.geom.Point2D;

public class Projectile extends Entity {

	Game game;
	double direction;
	
	double speed = 0.5;
	
	public Projectile(Point2D position, double direction, Game game) {
		super(position);
		this.game = game;
		this.direction = direction;
	}
	
	@Override
	public void process(double dt) {
		double movex = Math.cos(direction)*speed;
		double movey = Math.sin(direction)*speed;
		pos = new Point2D.Double(
				pos.getX()+movex*dt,
				pos.getY()+movey*dt);
	}
}