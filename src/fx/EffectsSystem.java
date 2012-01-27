package fx;

import util.SafeList;
import util.Vector2D;

public class EffectsSystem {

	public SafeList<Particle> particles = new SafeList<Particle>();
	public SafeList<FxEntity> fxEntities = new SafeList<FxEntity>();
	
	public EffectsSystem() {
	}
	
	public void explosion(Vector2D pos, int n, double lifetime) {
		for (int j = 0;j<n;j++)
			particles.add(new Particle(pos, Vector2D.fromAngle(j*1.8, 1), (lifetime-0.2)*Math.random()+0.2, Math.random()*10, -10, this));
	}
	
	public void process(double dt) {
		for(FxEntity p : fxEntities) {
			p.process(dt);
		}
		for(Particle p : particles) {
			p.process(dt);
		}
		
		particles.applyChanges();
		fxEntities.applyChanges();
	}
	
	public void removeEntity(FxEntity e) {
		if(e instanceof Particle)
			particles.remove((Particle)e);
		else fxEntities.remove(e);
	}
	
	
}
