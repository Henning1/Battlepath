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

public class Transition {
	public enum type {
		EASEINOUT,
		LINEAR
	}
	
	private double s, e, d;
	private transitionMethod t;
	
	public Transition(double startValue, double endValue, double duration, type type) {
		s = startValue;
		e = endValue;
		d = duration;
		setType(type);
		
	}
	
	public double get(double time) {		
		if(time >= d) return e;
		//System.out.println("start: "+s+" end:"+e+"duration: "+d+"speed: "+getSpeed(time)+" type:"+t);
		return doMath(time);
	}

	private double doMath(double time) {
		return t.getValue(time, s, e, d);
	}
	
	public double getSpeed(double time) {
		return t.getDerivateValue(time, s, e, d);
	}
	
	public void setType(type type) {
		switch(type) {
		case EASEINOUT:
			t = new easeInOut();
			break;
		case LINEAR:
			t = new linear();
			break;
		}
	}
	
	public void setStartValue(double startValue) {
		s = startValue;
	}
	
	public double getStartValue() {
		return s;
	}
	
	public void setEndValue(double endValue) {
		e = endValue;
	}
	
	public double getEndValue() {
		return e;
	}
	
	public void setDuration(double duration) {
		d = duration;
	}
	
	public double getDuration() {
		return d;
	}
	
	private interface transitionMethod {
		public double getValue(double time, double startValue, double endValue, double duration);
		public double getDerivateValue(double time, double startValue, double endValue, double duration);
	}
	
	private class easeInOut implements transitionMethod {
		@Override
		public double getValue(double a, double b, double c, double d) {
			a /= d/2;
			if (a < 1) return (c-b)/2*a*a + b;
			a--;
			return -(c-b)/2 * (a*(a-2) - 1) + b;
		}

		@Override
		public double getDerivateValue(double a, double b, double c, double d) {
			a /= d/2;
			if (a < 1) return (c-b)*a;
			a--;
			return (c-b) - (c-b)*a;
		}
	}
	
	private class linear implements transitionMethod {
		@Override
		public double getValue(double a, double b, double c, double d) {
			a /= d;
			return a * (c - b) + b;
		}

		@Override
		public double getDerivateValue(double a, double b, double c, double d) {
			a /= d;
			return (c - b);
		}
		
	}
}
