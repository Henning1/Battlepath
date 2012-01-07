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
	//TODO: Merge these
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public ArrayList<Particle> particles = new ArrayList<Particle>();
	public ArrayList<Tower> towers = new ArrayList<Tower>();
	public View view;
	public GameMode mode;
	
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
		view.process(dt);
	}
	
	public void setMode(GameMode gm) {
		mode = gm;
		switch(mode) {
		case ACTION:
			view.follow(u);
			break;
		case STRATEGY:
			view.unfollow();
			break;
		}
	}
	
	public void toggleMode() {
		if(mode == GameMode.ACTION) {
			setMode(GameMode.STRATEGY);
		}
		else if(mode == GameMode.STRATEGY) {
			setMode(GameMode.ACTION);
		}
	}
	
	public void processInput(double dt) {
		//Mouse
		
		if(input.mouseButtonPressed[0] && !lastMouseState[0]) {
			u.moveTo(input.getCursorPos());
		}
		
		if((input.mouseButtonPressed[2] && GlobalInfo.time - lastShot > 0.3)) {
			projectiles.add(
					new Projectile(u.pos, input.getCursorPos().subtract(u.pos),this));
			lastShot = GlobalInfo.time;
		}
		
		System.arraycopy(input.mouseButtonPressed, 0, lastMouseState, 0, input.mouseButtonPressed.length);
		
		//Keyboard part one (input.isPressed)
		
		if(input.isPressed(KeyBindings.MOVE_LEFT)) u.velocity.x = 1;
		else if(input.isPressed(KeyBindings.MOVE_RIGHT)) u.velocity.x = -1;
		else u.velocity.x = 0;
		
		if(input.isPressed(KeyBindings.MOVE_DOWN)) u.velocity.y = 1;
		else if(input.isPressed(KeyBindings.MOVE_UP)) u.velocity.y = -1;
		else u.velocity.y = 0;
		
		if(u.velocity.length() > 0)
			u.velocity = u.velocity.normalize().scalar(10);
		
		if(input.isPressed(KeyBindings.SCROLL_LEFT)) view.velocity.x = 20;
		else if(input.isPressed(KeyBindings.SCROLL_RIGHT)) view.velocity.x = -20;
		else view.velocity.x = 0;
		
		if(input.isPressed(KeyBindings.SCROLL_UP)) view.velocity.y = 20;
		else if(input.isPressed(KeyBindings.SCROLL_DOWN)) view.velocity.y = -20;
		else view.velocity.y = 0;
		
		//Keyboard part two (input.getKeyBuffer)
		
		for(int key : input.getKeyBuffer()) {
			switch(key) {
				case KeyBindings.ZOOM_IN:
					view.setZoom(view.zoom+0.1);
					break;
				case KeyBindings.ZOOM_OUT:
					view.setZoom(view.zoom-0.1);
					break;
				case 't':
					toggleMode();
					break;
			}
		}
		
	}
	
}
