package game;

import engine.GlobalInfo;
import util.Vector2D;

public class Tower extends Entity {
	public Vector2D aim = new Vector2D(0,0);
	double lastShot;
	
	public Tower(Vector2D position, Game game) {
		super(position, game);
	}

	@Override
	public void process(double dt) {
		aim = game.u.pos.subtract(pos).normalize();
		if(pos.distance(game.u.pos) < 10 && GlobalInfo.time-lastShot > 0.3) {
			game.emitShot(pos.add(aim.scalar(getRadius())), aim);
			lastShot = GlobalInfo.time;
		}
	}

	@Override
	public double getRadius() {
		return 1;
	}

}
