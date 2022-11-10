package Level;

import java.util.Random;
import java.util.TimerTask; 
import java.lang.Math; 
import Obstacles.*;
import Utility.LevelLoader;



/**
 * Class that generates a random number and uses it to determine whether something should be spawned.
 * Should be used with timer event
 */

public class EntityTimer extends TimerTask{

    private GameBoard window;       // Window for obstacle to be drawn too

    private Random ranNum;          // Random num used to determine if something should be spawned

    private ObstacleBuilder builder;

    private int uiSize;

    private float difficulty;
    private float size;

    /**
     * Constructor gens a random number that will be used as seed
     * @param gameWindow gameboard to use
     * @param topBarSize the size of the area that objects should not be spawned on
     * @param level the current level. Used to get difficulty
     */
    public EntityTimer(GameBoard gameWindow, int topBarSize,LevelLoader level){

        int seed =(int) (Math.random()* 20000);     // Seed number used for random number generator
                                                    // Sequence of numbers generated from seed will be identical
        ranNum = new Random(seed);                  // Generate number using seed

        //System.out.println("Debug ---- Seed = " + seed);    // Print out seed for debugging

        window = gameWindow; 
        
        size = (window.getSize().y - uiSize); // Spawnable area;
      

        uiSize = topBarSize;
        difficulty = level.getDifMultiplier();
        builder = new ObstacleBuilder(difficulty);    
    }

    /**
     * Method that can be called on timer, chance to spawn entity each time. Creates entity and adds to gameboard
     */
    @Override
    public void run() {

        float test = ranNum.nextFloat();          // Generate next number
        
        if( test <= .14){          // Spawn asteroid if between these numbers
            // Add asteroid to arraylist of all drawables
            Obstacle temp = builder.getObstacle("ASTEROID", 1,window.getScale());
            temp.getSprite().setPosition(window.getSize().x,(int)(ranNum.nextFloat() *size) + uiSize);
            window.addObstacle(temp);
        }
        else if(test <= .25){
            Obstacle temp = builder.getObstacle("ASTEROID", 2,window.getScale());
            temp.getSprite().setPosition(window.getSize().x,(int)(ranNum.nextFloat() *size) + uiSize);
            window.addObstacle(temp);
        }
        else if(test <= .35){
            Obstacle temp = builder.getObstacle("ASTEROID", 3,window.getScale());
            temp.getSprite().setPosition(window.getSize().x,(int)(ranNum.nextFloat() *size) + uiSize);
            window.addObstacle(temp);
        }
        else if(test <= .4){
            Obstacle temp = builder.getObstacle("ASTEROID", 4,window.getScale());
            temp.getSprite().setPosition(window.getSize().x,(int)(ranNum.nextFloat() *size) + uiSize);
            window.addObstacle(temp);
        }
        else if(test <= .44){
            Obstacle temp = builder.getObstacle("COIN", 1,window.getScale());
            temp.getSprite().setPosition(window.getSize().x,(int)(ranNum.nextFloat() *size) + uiSize);
            window.addCollectable(temp);
        }
        else if(test <= .47){
            Obstacle temp = builder.getObstacle("COIN", 2,window.getScale());
            temp.getSprite().setPosition(window.getSize().x,(int)(ranNum.nextFloat() *size) + uiSize);
            window.addCollectable(temp);
        }
        else if(test <= .48){
            Obstacle temp = builder.getObstacle("COIN", 3,window.getScale());
            temp.getSprite().setPosition(window.getSize().x,(int)(ranNum.nextFloat() *size) + uiSize);
            window.addCollectable(temp);
        }
        else if(test <= .50){
            Obstacle temp = builder.getObstacle("POWERUP", 1,window.getScale());
            temp.getSprite().setPosition(window.getSize().x,(int)(ranNum.nextFloat() *size) + uiSize);
            window.addCollectable(temp);
        }
        else if(test <= .51){
            Obstacle temp = builder.getObstacle("POWERUP", 2,window.getScale());
            temp.getSprite().setPosition(window.getSize().x,(int)(ranNum.nextFloat() *size) + uiSize);
            window.addCollectable(temp);
        }
        else if(test <= .52){
            Obstacle temp = builder.getObstacle("POWERUP", 3,window.getScale());
            temp.getSprite().setPosition(window.getSize().x,(int)(ranNum.nextFloat() *size) + uiSize);
            window.addCollectable(temp);
        }
        else if(window.getThief()>0 && test <= (.52 + (0.005 * window.getThief()))){
            Obstacle temp = builder.getObstacle("THIEF", 1, window.getScale());
            temp.getSprite().setPosition(window.getSize().x,(int)(window.getSize().y/2));
            window.addObstacle(temp);
            window.removeOneThief();; 
        }
        return;

    }

}