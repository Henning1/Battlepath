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
		// x1==x2 but let’s disregard that slight optimization)
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
}
