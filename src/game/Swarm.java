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
package game;
import java.util.ArrayList;

import util.SafeList;
import util.Vector2D;

import entities.Entity;
import entities.Unit;
/**
 * @author henning
 *
 */
public class Swarm {
	
	private SafeList<Unit> units;
	private Game game;
	private Unit leader;
	
	boolean alive = true;
	
	public Swarm(ArrayList<Unit> units, Game game) {
		this.game = game;
		this.units = new SafeList<Unit>(units);
		
		for(Unit u : this.units) {
			u.leader = false;
			u.setSwarm(this);
		}
		findLeader();
	}
	
	private void findLeader() {
		switch(units.size()) {
		case 0: 
			alive = false;
			return;
		case 1:
			leader = units.get(0);
		break;
		case 2:
			leader = units.get(1);
		break;
		default:
			averagePositionLeader();
		}
		leader.leader = true;
	}

	
	private void averagePositionLeader() {
		Vector2D averagePosition=new Vector2D(0,0);
		for(Unit u : units) {
			averagePosition = averagePosition.add(u.pos);
		}
		averagePosition = averagePosition.scalar(1.0/(double)units.size());
		System.out.println(averagePosition);
		Unit closest=null;
		double minDistance = Double.MAX_VALUE;
		for(Unit u : units) {
			double distance = averagePosition.distance(u.pos);
			if(distance < minDistance) {
				minDistance = distance;
				closest = u;
			}
		}
		leader = closest;
	}
	
	public void shootAt(Vector2D pos) {
		for(Unit u : units) {
			Vector2D dir = pos.subtract(u.pos).normalize();
			u.shoot(dir);
		}
	}
	
	public Unit getLeader() {
		return leader;
	}
	
	public void process() {
		for(Unit u : units) {
			if(!u.alive) {
				units.remove(u);
			}
		}
		units.applyChanges();
		if(leader != null && !leader.alive) {
			findLeader();
		}
	}
	
}
