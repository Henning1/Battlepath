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
package util;

import java.util.ArrayList;
import java.util.Iterator;

public class SafeList<T> implements Iterable<T> {

	int index = 0;
	protected ArrayList<T> list = new ArrayList<T>();
	protected ArrayList<T> addList = new ArrayList<T>();
	protected ArrayList<T> deleteList = new ArrayList<T>();
	
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
