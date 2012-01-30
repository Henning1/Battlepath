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
