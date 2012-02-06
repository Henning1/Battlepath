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
package entities;


import java.util.ArrayList;

import collision.CollisionSystem;
import collision.Move;

import util.Line2D;
import util.Vector2D;

import engine.GlobalInfo;
import game.Game;
import game.Swarm;
import game.Team;


public class Unit extends HealthEntity {

	public ArrayList<Vector2D> path;
	public Swarm swarm;
	public boolean leader=false;
	public Vector2D direction = new Vector2D(0,0);
	public double speed = 8;
	public boolean actionmode = true;
	public double lastPlan=0;
	
	public Unit(Vector2D position, Game game, Team team) {
		super(position,game, team);
		health = 300;
	}
	
	public void moveTo(Vector2D dest) {
		path = game.pathPlanner.plan(pos, dest);
	}
	
	public void setHealth(int h) {
		if(h > 100) {
			h = 100;
		}
		else if(h < 0) {
			h = 0;
		}
		
		health = h;
	}
	
	public void shoot(Vector2D direction) {
		if(GlobalInfo.time - lastShot > 0.3) {
			game.entities.add(new Projectile(pos.add(direction.scalar(getRadius())), direction, game, team));
			lastShot = GlobalInfo.time;
		}
	}
	
	public Vector2D velocityDt() {
		return velocity.scalar(game.dt);
	}
	
	public void setLeader(boolean value) {
		leader = value;		
	}
	
	public void setSwarm(Swarm swarm) {
		this.swarm = swarm;
	}
	
	public void process(double dt) {
		
		if(swarm != null && !leader) {
			
			
			Unit leader = swarm.getLeader();
			Line2D toLeader = new Line2D(pos,leader.pos);
			Vector2D vecToLeader = leader.pos.subtract(pos);
			if(vecToLeader.length() > 1) {
				if(game.collisionSystem.collideWithLevel(toLeader)) {
					if(path == null | GlobalInfo.time - lastPlan > 1) {
						path = game.pathPlanner.plan(this.pos, leader.pos);
						lastPlan = GlobalInfo.time;
					}
				} 
				else {
					path = null;
					velocity = toLeader.direction.scalar(speed);
				}
			}
			else {
				velocity = GlobalInfo.nullVector;
			}
		}
			
		if(path != null && path.size() > 0) {
			if(pos.distance(path.get(0)) < GlobalInfo.accuracy+0.1) {
				path.remove(0);
				velocity = new Vector2D(0,0);
			}
			if(path.size() > 0) {
				Vector2D dest = path.get(0);
				velocity = dest.subtract(pos).normalize().scalar(5);
			}
			
		}
		move = new Move(this,dt);
	}

	@Override
	public double getRadius() {
		return 0.4;
	}

	@Override
	public void collide(CollisionEntity e) {
		
	}


}
