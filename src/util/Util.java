package util;

public class Util {
	public static boolean doubleEquals(double d1, double d2, double epsilon) {
		return Math.abs(d1 - d2) < epsilon;
	}
	
	public static double getLowestRoot(double a, double b, double c, double maxR) {
			
		// Check if a solution exists
		double determinant = b*b - 4.0f*a*c;
		// If determinant is negative it means no solutions.
		if (determinant < 0.0f) return -1;
		// calculate the two roots: (if determinant == 0 then
		// x1==x2 but let's disregard that slight optimization)
		double sqrtD = Math.sqrt(determinant);
		double r1 = (-b - sqrtD) / (2*a);
		double r2 = (-b + sqrtD) / (2*a);
		// Sort so x1 <= x2
		if (r1 > r2) {
			double temp = r2;
			r2 = r1;
			r1 = temp;
		}
		// Get lowest root:
		if (r1 > 0 && r1 < maxR) {
			return r1;
		}
	
		// It is possible that we want x2 - this can happen
		// if x1 < 0
		if (r2 > 0 && r2 < maxR) {
			return r2;
		}
		// No (valid) solutions
		return -1;
	}
	
	
	/*	|a b|e|
	 *  |c d|f|
	 */
	public static Tuple<Double,Double> LES2x2(double a, double b, double c, double d, double e, double f) {
		double y = (e*c - a*f) / (b*c - a*d);
		double x = (e - y*b) / a;
		
		return new Tuple<Double,Double>(x,y);
		
	}
	
	
	public static double valueInBounds(double min, double value, double max) {
		if(value < min) return min;
		else if (value > max) return max;
		else return value;
	}
	
	public static boolean isValueInBounds(double limit1, double value, double limit2) {
		if(limit2 < limit1) {
			double temp = limit2;
			limit2 = limit1;
			limit1 = temp;
		}
		if(value < limit1) return false;
		if(value > limit2) return false;
		return true;
	}
	
	
	public static boolean isValueInBounds(int limit1, int value, int limit2) {
		if(limit2 < limit1) {
			int temp = limit2;
			limit2 = limit1;
			limit1 = temp;
		}
		if(value < limit1) return false;
		if(value > limit2) return false;
		return true;
	}
	
	public static double easeInOut(double time, double startValue, double endValue, double duration) {
		if(startValue > endValue) {
			double temp = endValue;
			endValue = startValue;
			startValue = temp;
		}
		time /= duration/2;
		if (time < 1) return endValue/2*time*time + startValue;
		time--;
		return -endValue/2 * (time*(time-2) - 1) + startValue;
	}
}
