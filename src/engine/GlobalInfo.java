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
package engine;

import util.Vector2D;

/**
 * Class with general information that may concern all other classes of the game.
 */
public class GlobalInfo {
	/**Indicates whether the game is running*/
	public static boolean running = true;
	/**Current time since start, in seconds*/
	public static double time = 0.0;
	/**Null vector*/
	public static Vector2D nullVector = new Vector2D(0,0);
	/**Accuracy*/
	public static double accuracy = 0.02;
	/**How close to go to a line in collision detection*/
	public static double veryCloseDistance = 0.0005;
	
}
