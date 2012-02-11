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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
		Tuple<Double, Double> xs = new Tuple<Double,Double>();
		xs.a = (e*d - f*b) / (a*d - c*b);
		xs.b = (a*f - e*c) / (a*d - b*c);
		return xs;
		
		/*
		double x,y;
		y = (e*c - a*f) / (b*c - a*d);
		if(a != 0)
			x = (e - y*b) / a;
		else x = (f - d*y) / d;
		return new Tuple<Double,Double>(x,y);
		*/
	}
	
	
	public static double valueInBounds(double min, double value, double max) {
		if(max < min) {
			double temp = max;
			max = min;
			min = temp;
		}
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
	
	public static byte[] readFile(String filename) throws IOException {
        File file = new File(filename);
        long length = file.length();
		
		byte[] buffer = new byte[(int)length];
        
        FileInputStream in = null;
        try {
            in = new FileInputStream(filename);
            in.read(buffer);

        } finally {
            if (in != null) {
                in.close();
            }
        }
        return buffer;
	}
	
	public static void writeFile(String filename, byte[] buffer) throws IOException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            out.write(buffer);

        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
