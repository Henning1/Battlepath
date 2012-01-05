package game;

import interaction.Input;
import interaction.KeyBindings;

import java.util.ArrayList;

import collision.CollisionSystem;

import util.Vector2D;

import engine.Field;
import engine.GlobalInfo;
import engine.Pathplanner;


public class Game {
	
	public Field field;
	public Pathplanner pathPlanner;
	public CollisionSystem collisionSystem;
	public Input input;
	public Unit u;
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public ArrayList<Particle> particles = new ArrayList<Particle>();
	public View view;
	
	boolean[] lastMouseState = new boolean[3];
	double lastShot = 0;
	
	public double dt;
	
	public Game(Vector2D startpos) {
		u = new Unit(startpos, this);
	}
	

	
	public void step(double dt) {
		this.dt = dt;
		processInput(dt);
		
		u.process(dt);
		ArrayList<Projectile> delList = new ArrayList<Projectile>();
		for (int i=0;i<projectiles.size();i++) {
			
			Projectile proj = projectiles.get(i);
			proj.process(dt);

			if(collisionSystem.collide(proj) != null) {
				for (int j = 0;j<=200;j++)
					particles.add(new Particle(proj.pos, Vector2D.fromAngle(j*1.8, 1), Math.random()/2+0.1, Math.random()*5, this));
				delList.add(proj);
			}
		}
		projectiles.removeAll(delList);
		for (int i=0;i<particles.size();i++) {
			Particle part = particles.get(i);
			part.process(dt);
			if(part.destroyed)
				particles.remove(i);
		}
	}
	
	public void processInput(double dt) {
		//Mouse
		
		if(input.mouseButtonPressed[0] && !lastMouseState[0]) {
			u.moveTo(input.cursorPos);
		}
		
		if((input.mouseButtonPressed[2] && GlobalInfo.time - lastShot > 0.3)) {
			projectiles.add(
					new Projectile(u.pos, input.cursorPos.subtract(u.pos),this));
			lastShot = GlobalInfo.time;
		}
		
		System.arraycopy(input.mouseButtonPressed, 0, lastMouseState, 0, input.mouseButtonPressed.length);
		
		//Keyboard part one (input.isPressed)
		
		if(input.isPressed(KeyBindings.MOVE_LEFT)) u.velocity.x = 5;
		else if(input.isPressed(KeyBindings.MOVE_RIGHT)) u.velocity.x = -5;
		else u.velocity.x = 0;
		
		if(input.isPressed(KeyBindings.MOVE_DOWN)) u.velocity.y = 5;
		else if(input.isPressed(KeyBindings.MOVE_UP)) u.velocity.y = -5;
		else u.velocity.y = 0;
		
		if(input.isPressed(KeyBindings.SCROLL_LEFT)) view.moveOffset(new Vector2D(20, 0).scalar(dt));
		else if(input.isPressed(KeyBindings.SCROLL_RIGHT)) view.moveOffset(new Vector2D(-20, 0).scalar(dt));
		
		if(input.isPressed(KeyBindings.SCROLL_UP)) view.moveOffset(new Vector2D(0, 20).scalar(dt));
		else if(input.isPressed(KeyBindings.SCROLL_DOWN)) view.moveOffset(new Vector2D(0, -20).scalar(dt));
		
		//Keyboard part two (input.getKeyBuffer)
		
		for(int key : input.getKeyBuffer()) {
			if(key == KeyBindings.ZOOM_IN) {
				//
			}
			if(key == KeyBindings.ZOOM_OUT) {
				
			}
		}
		
	}
	
}
