package game;

import util.Vector2D;

public class Tower extends Entity {
	
	public Tower(Vector2D position, Game game) {
		super(position, game);
	}

	@Override
	public void process(double dt) {
		

	}

	@Override
	public double getRadius() {
		return 1;
	}

}
