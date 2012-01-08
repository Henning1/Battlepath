package collision;

import game.Game;

import java.util.ArrayList;

import Entities.CollisionEntity;


public class MovementSystem {
	
	private ArrayList<Move> moves;
	private Game game;
	
	public MovementSystem(Game game) {
		this.game = game;
		moves = new ArrayList<Move>();
	}
	
	public void register(Move c) {
		moves.add(c);
	}
	
	public void process(ArrayList<CollisionEntity> ces) {
		for(CollisionEntity c1 : ces) {
			Move m1 = c1.getMove();
			if(m1 == null) continue;
			
			//collision with level
			if(game.collisionSystem.collide(c1) != null) {
				c1.collide(null);
			}
			
			//collision with entities
			for(CollisionEntity c2 : ces) {
				if(c1 == c2) continue;
				Move m2 = c2.getMove();
				
				//static collision
				if(m2 == null) {
					
				}
				//dynamic collision
				else {
					m2.apply();
				}
				
				m1.apply();

				
				if(c1.pos.distance(c2.pos) < c1.getRadius() + c2.getRadius()) {
					c1.collide(c2);
					c2.collide(c1);
				}
				

			}
		}

		
	}
}
