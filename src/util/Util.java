package util;

public class Util {
	public static boolean doubleEquals(double d1, double d2, double epsilon) {
		return Math.abs(d1 - d2) < epsilon;
	}
}
