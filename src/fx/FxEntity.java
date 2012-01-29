package fx;

import engine.GlobalInfo;
import util.Vector2D;

public class FxEntity implements Comparable<FxEntity> {
	public Vector2D pos;
	public double lifetime;
	private double life;
	public double deathtime;
	private EffectsSystem fxSystem;
	
	
	public FxEntity(EffectsSystem fxSystem, double lifetime, Vector2D pos) {
		this.pos = pos;
		this.lifetime = lifetime;
		this.fxSystem = fxSystem;
		this.life = 0;
		this.deathtime = GlobalInfo.time+lifetime;
	}
	
	public void process(double dt) {
		life += dt;
		if(life > lifetime) {
			fxSystem.removeEntity(this);
		}
	}
	
	@Override
	public int compareTo(FxEntity o) {
		if(deathtime > o.deathtime) return 1;
		else if(deathtime == o.deathtime) return 0;
		else return -1;
	}
}

