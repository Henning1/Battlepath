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

import util.Vector2D;
import entities.HealthEntity;

/**
 * HUD menu around units and towers
 */
public class HUDMenu {
	private HealthEntity entity;
	private double animationState;
	private int animationDirection; //1: Open //-1: Close
	private double animationLength = 0.2;
	private Game game;
	public ArrayList<HUDButton> buttons = new ArrayList<HUDButton>();
	
	public HUDMenu(HealthEntity e, ArrayList<HUDButton> b, Game g) {
		entity = e;
		buttons = b;
		game = g;
		int i = 0;
		for(HUDButton butt : buttons) {
			butt.direction = Vector2D.fromAngle(((double)i/(buttons.size()))*Math.PI*2, 1);
			i++;
		}
	}
	
	public void open() {
		animationDirection = 1;
	}
	
	public void close() {
		animationDirection = -1;
	}
	
	public boolean isOpen() {
		return animationState >= 1;
	}
	
	public boolean isVisible() {
		return animationState > 0;
	}
	
	public void process(double dt) {
		//TODO: Make animations nice
		if(animationDirection == 1 && animationState < 1) {
			for(int i = 0;i<buttons.size();i++) {
				HUDButton b = buttons.get(i);
				double x = animationState*2;
				b.position = game.view.worldToViewGL(entity.pos.add(b.direction.scalar(x)));
			}
			animationState += dt/animationLength;
		}
		else if(animationDirection == -1 && animationState > 0) {
			for(int i = 0;i<buttons.size();i++) {
				HUDButton b = buttons.get(i);
				double x = animationState*2;
				b.position = game.view.worldToViewGL(entity.pos.add(b.direction.scalar(x)));
			}
			animationState -= dt/animationLength;
		
		}
		else if(isVisible()){
			for(int i = 0;i<buttons.size();i++) {
				HUDButton b = buttons.get(i);
				b.position = game.view.worldToViewGL(entity.pos.add(b.direction.scalar(2)));
				//Process buttons
				b.process(dt);
			}
		}
	}
}
