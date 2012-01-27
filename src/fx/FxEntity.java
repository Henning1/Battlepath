package fx;

import util.Vector2D;

public class FxEntity {
	public Vector2D pos;
	public double lifetime;
	private double life;
	private EffectsSystem fxSystem;
	
	public FxEntity(EffectsSystem fxSystem, double lifetime, Vector2D pos) {
		this.pos = pos;
		this.lifetime = lifetime;
		this.fxSystem = fxSystem;
		this.life = 0;
	}
	
	public void process(double dt) {
		life += dt;
		if(life > lifetime) {
			fxSystem.removeEntity(this);
		}
	}
}

