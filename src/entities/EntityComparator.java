package entities;

public class EntityComparator implements Comparable<EntityComparator> {
	
	Entity e;
	int dimension;
	
	public EntityComparator(Entity e, int dimension) {
		this.e = e;
		this.dimension = dimension;
	}

	@Override
	public int compareTo(EntityComparator c) {

		
		if(dimension==1) {
			if(e.pos.x > c.e.pos.x) return 1;
			if(e.pos.x == c.e.pos.x) return 0;
			else return -1;
		} else if(dimension==2) {
			if(e.pos.y > c.e.pos.y) return 1;
			if(e.pos.y == c.e.pos.y) return 0;
			else return -1;
			
		}
		return 0;
	}

}
