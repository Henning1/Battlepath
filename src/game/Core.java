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

import interaction.Input;
import interaction.KeyBindings;

import java.util.ArrayList;
import java.util.Random;

import main.Battlepath;


import collision.CollisionSystem;
import collision.MovementSystem;

import util.Rectangle2D;
import util.SafeList;
import util.Vector2D;

import editor.EditorSession;
import engine.Field;
import engine.Pathplanner;
import entities.Entity;
import entities.EntitySystem;
import entities.Projectile;
import entities.Unit;
import fx.EffectsSystem;

/**
 * Game class, managing all computations needed for the game
 */
public class Core {
	
	//TODO: Refactor, check whether we can/should make some of these private.
	public static Field field;
	public static CollisionSystem collisionSystem;
	public static MovementSystem movementSystem;
	public static EffectsSystem particleSystem;
	public static Input input;
	public static EntitySystem entitySystem = new EntitySystem();
	public static View view;
	public static double dt;
	public static Rectangle2D selectionRect;
	public static boolean useSelectionRect=true;
	public static Session session;
	private static Session session1;
	private static Session session2;
	
	public void setView(View view) {
		Core.view = view;
	}
	
	public static void initialize(Session sessionG, Session sessionE) {
		session1 = sessionG;
		session2 = sessionE;
		session1.initialize();
		
		movementSystem = new MovementSystem();
		particleSystem = new EffectsSystem();
		Core.collisionSystem = new CollisionSystem(field);

		Core.session = sessionE;
		session.initialize();
		
		view.center(new Vector2D(field.getTilesX()/2,field.getTilesY()/2));
	}
	
	/**
	 * Method that should be called at least once for every frame, calls all process() methods of all game elements 
	 * @param dt time step that is computed. Passed to all elements.
	 */
	public static void step(double dt) {
		Core.dt = dt;
		
		input.process();
		if(view.followedEntity == null) controlCamera();
		if(useSelectionRect) processSelectionRect();
		entitySystem.arrange();
		for(int key : Core.input.getKeyBuffer()) {
			processKey(key);
			session.processKey(key);
		}
		session.processInputState(dt);		
		session.step(dt);
		
		for(Entity e : entitySystem.entities) {
			e.process(dt);
		}
		movementSystem.process(entitySystem.collisionEntities,entitySystem.units,dt);

		particleSystem.process(dt);
		view.process(dt);
		
	}
	
	public static void processKey(int key) {
		
		switch(key) {
		case KeyBindings.ZOOM_IN:
			if(input.isPressed(17)) return;
			Core.view.zoom(1.25*Core.view.getTargetZoom(), true);
			break;
		case KeyBindings.ZOOM_OUT:
			if(input.isPressed(17)) return;
			Core.view.zoom(0.8*Core.view.getTargetZoom(), true);
			break;
		case 'm':
			if(session == session1) switchTo(session2);
			else if(session == session2) switchTo(session1);
			
		}
	}
	
	public static void switchTo(Session s) {
		session = s;
		s.initialize();
	}
	
	
	public static void processSelectionRect() {
		if(input.mouseNewPress[0]) {	
			selectionRect = new Rectangle2D(input.getCursorPos(), input.getCursorPos());
		}
		
		if(!input.mouseButtonPressed[0]) {
			selectionRect = null;
		}
		
		if(input.mouseButtonHold[0]) {
			if(selectionRect != null)
				selectionRect.bottomright = input.getCursorPos();
		}
	}
	
	public static void controlCamera() {
		if(Core.input.isPressed(KeyBindings.SCROLL_LEFT)) Core.view.velocity.x = -20;
		else if(Core.input.isPressed(KeyBindings.SCROLL_RIGHT)) Core.view.velocity.x = 20;
		else Core.view.velocity.x = 0;
			
		if(Core.input.isPressed(KeyBindings.SCROLL_UP)) Core.view.velocity.y = 20;
		else if(Core.input.isPressed(KeyBindings.SCROLL_DOWN)) Core.view.velocity.y = -20;
		else Core.view.velocity.y = 0;
	}
	
	/**
	 * Produces a shot at given world position and direction
	 * @param start world position of the shot to start
	 * @param direction direction to shoot
	 */
	public static void emitShot(Vector2D start, Vector2D direction, Team team) {
		Projectile p = new Projectile(start, direction, team);
		entitySystem.add(p);
	}
}
