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

