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
