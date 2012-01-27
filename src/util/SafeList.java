package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SafeList<T> implements Iterable<T> {

	int index = 0;
	private ArrayList<T> list = new ArrayList<T>();
	private ArrayList<T> addList = new ArrayList<T>();
	private ArrayList<T> deleteList = new ArrayList<T>();
	
	public SafeList() {

	}
	
	public boolean applyChanges() {
		boolean success;
		success = list.removeAll(deleteList);
		deleteList.clear();
		success = success & list.addAll(addList);
		addList.clear();
		return success;
		
	}
	
	public boolean add(T e) {
		return addList.add(e);
	}
	
	public boolean addAll(ArrayList<T> es) {
		return addList.addAll(es);
	}
	
	public void remove(T e) {
		deleteList.add(e);
	}
	
	public T get(int i) {
		return list.get(i);
	}

	public int size() {
		return list.size();
	}
	
	@Override
	public Iterator<T> iterator() {
		return new LifetimeListIterator(this);
	}

	
	
    private class LifetimeListIterator implements Iterator<T> {
    	
    	SafeList<T> lts;
    	int count=0;
    	
    	public LifetimeListIterator(SafeList<T> lts) {
    		this.lts = lts;
    	}
    	
		@Override
		public boolean hasNext() {
			return count < lts.size();
		}

		@Override
		public T next() {
			return (T) lts.get(count++);
			
		}

		@Override
		public void remove() {
		}

    }
}
