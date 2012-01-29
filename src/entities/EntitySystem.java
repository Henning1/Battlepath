package entities;

import java.util.ArrayList;
import java.util.Collections;

import util.SafeList;
import util.Vector2D;

public class EntitySystem {
	
	ArrayList<EntityComparator> xOrder = new ArrayList<EntityComparator>();
	

	public void arrange(SafeList<Entity> entities) {
		xOrder.clear();
		for(Entity e : entities) {
			EntityComparator ec = new EntityComparator(e,1);
			int index = Collections.binarySearch(xOrder, ec);
			if(index >= 0)
				xOrder.add(index,ec);
			else xOrder.add((-index)-1,ec);
		}
	}
	
	private EntityComparator getPivot(int dimension, double value) {
		
		EntityComparator pivot = null;
		if(dimension == 1) {
			Entity e = new Unit(new Vector2D(value,0.0), null);
			pivot = new EntityComparator(e, 1);
		}
		else if(dimension == 2) {
			Entity e = new Unit(new Vector2D(0.0,value), null);
			pivot = new EntityComparator(e, 2);
		}
		return pivot;
	}
	
	
	private int getStartIndex(ArrayList<EntityComparator> list, double value, int dimension) {
		int startindex = Collections.binarySearch(list, getPivot(dimension,value));
		if(startindex<0) startindex = -startindex-1;
		return startindex;
	}
	
	private int getEndIndex(ArrayList<EntityComparator> list, double value, int dimension) {
		int endindex = Collections.binarySearch(list, getPivot(dimension,value));
		if(endindex<0) endindex = (-endindex)-2;
		if(endindex < 0) endindex = 0;
		return endindex;
	}

	
	public ArrayList<Entity> getEntitiesInRange(Vector2D pos, double range) {
		int startindex = getStartIndex(xOrder,pos.x-range,1);
		int endindex = getEndIndex(xOrder,pos.x+range,1);
		
		ArrayList<EntityComparator> yOrder = new ArrayList<EntityComparator>();
		for(int i=startindex; i<=endindex; i++) {
			Entity e = xOrder.get(i).e;
			EntityComparator ec = new EntityComparator(e,2);
			int index = Collections.binarySearch(yOrder, ec);
			if(index >= 0) yOrder.add(index,ec);
			else yOrder.add(-index-1,ec);
		}
		
		startindex = getStartIndex(yOrder,pos.y-range,2);
		endindex = getEndIndex(yOrder,pos.y+range,2);
		
		ArrayList<Entity> result = new ArrayList<Entity>();
		for(int i=startindex; i<=endindex; i++) {
			Entity e = yOrder.get(i).e;
			if(e.pos.distance(pos) <= range)
				result.add(e);
		}
		
		return result;
	}
}
