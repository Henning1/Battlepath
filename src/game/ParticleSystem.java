package game;

import java.util.ArrayList;

import util.Vector2D;

public class ParticleSystem {

	public ArrayList<Particle> particles;
	ArrayList<Particle> addList;
	ArrayList<Particle> deleteList;
	
	public ParticleSystem() {
		particles = new ArrayList<Particle>();
		addList = new ArrayList<Particle>();
		deleteList = new ArrayList<Particle>();
	}
	
	public void explosion(Vector2D pos, int n, double lifetime) {
		for (int j = 0;j<n;j++)
			addList.add(new Particle(pos, Vector2D.fromAngle(j*1.8, 1), (lifetime-0.2)*Math.random()+0.2, Math.random()*10, -10, this));
	}
	
	public void process(double dt) {
		for(Particle p : particles) {
			p.process(dt);
		}
		
		particles.removeAll(deleteList);
		deleteList.clear();
		particles.addAll(addList);
		addList.clear();
	}
}
