/**
 * Copyright (c) 2011-2012 Henning Funke.
 * 
 * This file is part of Battlepath.
 *
 * Battlepath is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * Battlepath is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fx;

import java.util.ArrayList;

import engine.GlobalInfo;
import game.Game;

import util.SafeList;
import util.Vector2D;

public class EffectsSystem {

	public ArrayList<Particle> particles = new ArrayList<Particle>();
	public SafeList<FxEntity> fxEntities = new SafeList<FxEntity>();
	
	Game game;
	
	public EffectsSystem(Game game) {
		this.game = game;
	}
	
	public void particleSpray(Vector2D pos, int n, double lifetime) {
		if(!game.view.getScreenRect().inside(pos, 10)) return;
		for (int j = 0;j<n;j++)
			particles.add(new Particle(pos, Vector2D.fromAngle(j*1.8, 1), (lifetime-0.2)*Math.random()+0.2, Math.random()*10, -10, this));
	}
	
	
	public void explosion(Vector2D pos) {
		particleSpray(pos, 600, 1);
		fxEntities.add(new Shockwave(this,1,pos,10));
	}
	
	public void process(double dt) {
		for(FxEntity p : fxEntities) {
			p.process(dt);
		}
		for(int i=0; i<particles.size(); i++) {
			Particle p = particles.get(i);
			if(GlobalInfo.time > p.deathtime) {
				particles.remove(i);
				i--;
			} else {
				p.process(dt);
			}
		}

		fxEntities.applyChanges();
	}
	
	public void removeEntity(FxEntity e) {
		if(e instanceof Particle) return;
		else fxEntities.remove(e);
	}
	
	
}
