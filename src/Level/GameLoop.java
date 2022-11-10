package Level;

import java.util.Timer;
import java.util.TimerTask;

import Utility.LevelLoader;


public class GameLoop {

    
    private GameBoard window = null;
    private Boolean active; // Loop is active boolean
    private Boolean paused = false;
    private long prevTime;
    private TimerTask createEntity;
    private LevelLoader level;

    private final float TICK_RATE = 64;
    private Timer timer;



    /**
     * Game loop constructor. Stores gameboard it is used by
     * @param w Gameboard that uses this loop
     */
    public GameLoop(GameBoard w) {
        window = w;
    }

    /**
     * Run the loop until active is set false
     * Uses a fixed time step that can be adjusted by changing tick_rate
     * Commented code included that can be used for a FPS counter
     * Also has a commented way of capping fps if JSFML cap does not work
     * @param l The level that is to be played
     */
    public void run(LevelLoader l) {
        active = true;
        level = l;
        
        double delta = 0; // Time since last tick
        double frameTime = 1000000000 / TICK_RATE; // Milli second per tick
        timer = new Timer(); // Timer used to call entityTimer
        createEntity = new EntityTimer(window, window.getUiSize(),level);

        // Uncomment these for FPS counter
       //int updates = 0;
        //long lastFps = System.nanoTime();
        //int fps = 0; // Only used for fps counter



        timer.schedule(createEntity, 100,(long)( 300 / l.getDifMultiplier())); // Call eneityTimer.(run) every x time
        prevTime = System.nanoTime(); // Time of last tick

        while (active) {

            window.input();

            long curTime = System.nanoTime(); // Set to current time
            delta += (curTime - prevTime); // Calculate time since last tick
            prevTime = curTime;
           
            int loops = 0; // Used so can set max of 5 ticks per frame
            while (delta >= frameTime && loops < 5) {
                window.updateAll(frameTime);
                delta -= frameTime;
                loops++;
                //updates ++;
            }
            // System.out.println(updates);

            window.render(delta / frameTime, frameTime); // Render current state with interpolation

           // fps++;

            // *****************
            // **** FPS Counter ****
             /* if(System.nanoTime() >= lastFps + 1000000000) { System.out.println(updates +
              "    " + fps); updates = 0;fps=0; lastFps = System.nanoTime(); }
              */
            

            /*double timeTaken = System.nanoTime() - curTime;
            
            if (timeTaken < frameTime) { try { Thread.sleep((long)((frameTime -
              timeTaken) / 1000000)); } catch (InterruptedException e) { } }
             */
            
        }
        window.clear();
        timer.cancel();
        timer.purge();
        createEntity.cancel();

        timer = null;
        return;
    }

    /**
     * Cancels entity spawns when paused
     */
    public void setPaused(){
        timer.cancel();
    }
    /**
     * start new entity spawns
     */
    public void unPause(){
       
        timer = new Timer();
        createEntity = new EntityTimer(window, window.getUiSize(),level);
        timer.schedule(createEntity, 100, (long)( 300 / level.getDifMultiplier()));
        prevTime = System.nanoTime();
    }
    /**
     * Getter for is paused
     * @return true if paused
     */
    public boolean getPaused(){
        return paused;
    }
    /**
     * Swaps current pause state
     */
    public void swapPause(){
        if(paused){
            paused = false;
        }
        else{
            paused = true;
        }
    }

    /**
     * Sets this loop to be active or not
     * @param set True for active loop. False to end loop on next complete loop
     */
    public void setActive(boolean set){
        active = set;
    }
    /**
     * getter for gameboard this loop is used by
     * @return Gameboard this loop is used by
     */
    public GameBoard getWindow(){return window;}
}
