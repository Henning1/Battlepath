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
	
	public static double t(double time, double startValue, double endValue, double duration, Transition.type t) {		
		if(time >= duration) return endValue;
		return doMath(time, startValue, endValue, duration, t);
	}
	
	private static double doMath(double time, double startValue, double endValue, double duration, Transition.type t) {
		switch(t) {
		case EASEINOUT:
			return easeInOut(time, startValue, endValue, duration);
		case LINEAR:
			return linear(time, startValue, endValue, duration);
		}
		return 0;
	}
	
	private static double easeInOut(double a, double b, double c, double d) {
		a /= d/2;
		if (a < 1) return (c-b)/2*a*a + b;
		a--;
		return -(c-b)/2 * (a*(a-2) - 1) + b;
	}
	
	private static double linear(double a, double b, double c, double d) {
		a /= d;
		return a * (c - b) + b;
	}
}
