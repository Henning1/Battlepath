package engine;

import interaction.Renderer;
import game.Game;

public class MainLoop {
    static double t = 0.0;
    static double dt = 0.01;

    static double currentTime;
    static double accumulator = 0.0;
    
    private static double fpsStart = 0.0;
    private static double fpsInterval = 0.2;
    private static int frames = 0;
    public static double framerate = 0.0;
    
    
    
    
    public static void startLoop(Renderer r, Game g) {
    	
    	currentTime = (double)System.currentTimeMillis() / 1000.0;
    	fpsStart = currentTime;
    	
    	while (engine.GlobalInfo.running)
        {
    		 double newTime = (double)System.currentTimeMillis() / 1000.0;
             double frameTime = newTime - currentTime;
             
             if(currentTime >= fpsStart + fpsInterval) {
            	 framerate =  frames / (currentTime - fpsStart);
            	 fpsStart = newTime;
            	 frames = 0;
             }
             /*
             frames 
             	/ 
             interval
             
             120
              /
             0.5
             */
             currentTime = newTime;
             accumulator += frameTime;


             while ( accumulator >= dt )
             {
                  g.step(dt);
                  accumulator -= dt;
                  t += dt;
             }

             r.render();
             frames++;
        }
    }
    

}
