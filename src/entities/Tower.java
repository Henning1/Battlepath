package entities;

import java.util.ArrayList;

import engine.GlobalInfo;
import game.Game;
import util.Line2D;
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
		
		ArrayList<Unit> aims = game.getUnitsInRange(pos, 20);
		
		for(Unit u : aims) {
			if(game.collisionSystem.collideWithLevel(new Line2D(pos,u.pos))) continue;
			
			aim = u.pos.subtract(pos).normalize();
			if(pos.distance(u.pos) < 20 && GlobalInfo.time-lastShot > 1) {
				game.emitShot(pos.add(aim.scalar(getRadius())), aim);
				lastShot = GlobalInfo.time;
			}
			break;
		}
	}

	@Override
	public double getRadius() {
		return 1;
	}

	@Override
	public void collide(CollisionEntity e) {

	}

}
