package fx;

import util.Vector2D;

public class Shockwave extends FxEntity {

	public double speed;
	public double radius = 0;
	
	public Shockwave(EffectsSystem fxSystem, double lifetime, Vector2D pos, double speed) {
		super(fxSystem, lifetime, pos);
	}
	
	public void process(double dt) {
		super.process(dt);
		radius += speed;
	}
}
