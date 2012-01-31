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

import interaction.BFrame;
import interaction.OpenGLRenderer;
import game.Game;

/**
 * Main loop that powers the game, does all timing concerning frames, framerate independent movement, etc.
 * Also calls everything that is executed every frame.
 */
public class MainLoop {
	/**Time step per game frame*/
    static double dt = 0.01;
    
    //FIXME: Redundant to GlobalInfo.time!
    /**Game time in seconds*/
    static double currentTime;
    
    private static double accumulator = 0.0;
    
    private static double fpsStart = 0.0;
    private static double fpsInterval = 0.2;
    private static int frames = 0;
    
    /**Current frame rate*/
    public static double framerate = 0.0;
    
    /**
     * Starts the infinite game loop
     * @param g Game object to use
     * @param r Renderer object to use
     * @param frame BFrame object to use
     */
    public static void startLoop(Game g, OpenGLRenderer r, BFrame frame) {
    	
    	currentTime = (double)System.currentTimeMillis() / 1000.0;
    	fpsStart = currentTime;
    	
    	while (engine.GlobalInfo.running)
        {
    		 double newTime = (double)System.currentTimeMillis() / 1000.0;
             double frameTime = newTime - currentTime;
             
             double waittime = 0.01 - frameTime;
             if(waittime > 0) {
              	try {            		 
					Thread.sleep((long) (waittime*1000));
					frameTime += waittime;
              	} catch (InterruptedException e) {
					e.printStackTrace();
				}
             }
             
             if(currentTime >= fpsStart + fpsInterval) {
            	 framerate =  frames / (currentTime - fpsStart);
            	 fpsStart = newTime;
            	 frames = 0;
             }
   
             currentTime = newTime;
             accumulator += frameTime;

             while ( accumulator >= dt )
             {
                  g.step(dt);
                  accumulator -= dt;
                  GlobalInfo.time += dt;
             }
             
             //Render
             frame.canvas.display();
             frame.setTitle("Battlepath | FPS: "+(int)framerate);
             frames++;
        }
    }
    

}
