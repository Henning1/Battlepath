package engine;

import interaction.Renderer;
import game.Game;

public class MainLoop {
    static double t = 0.0;
    static double dt = 0.01;

    static double currentTime;
    static double accumulator = 0.0;

    public static void startLoop(Renderer r, Game g) {
    	
    	currentTime = (double)System.currentTimeMillis() / 1000.0;
    	
    	while (engine.GlobalInfo.running)
        {
             double newTime = (double)System.currentTimeMillis() / 1000.0;
             double frameTime = newTime - currentTime;
             currentTime = newTime;

             accumulator += frameTime;

             //System.out.println(accumulator);
             
             while ( accumulator >= dt )
             {
                  g.step(dt);
                  accumulator -= dt;
                  t += dt;
             }

             r.render();
        }
    }
    

}
