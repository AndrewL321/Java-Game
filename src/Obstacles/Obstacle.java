package Obstacles;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import Level.*;


/**
 * Abstract class all obstacles in game should extend
 * Gives all basic methods for moving and drawing
 */
public abstract class Obstacle implements Entity{

    protected float objSpeed;                               // Window is drawn on

    protected Sprite obstacleSprite;

    /**
     * Constructor
     * @param image texture for this obstacle
     * @param speedMult speed multiplier
     * @param xScale x scale
     * @param yScaley y scale
     * @param sScale scale vector
     */
    public Obstacle(Texture image, float speedMult,Vector2f scale,Vector2f speedScale){

        obstacleSprite = new Sprite(image);
        obstacleSprite.setScale(scale);
        objSpeed = -0.00000012f * speedMult;
    }

    /**
     * Draw method for obstacle. Interpolation moves to predicted position before drawing
     * @param window Gameboard to draw with
     * @param delta how far between timesteps. percent as decimal 0-1
     * @param dT target timestep as 1sec/tickrate in milliseconds
     */
    @Override
    public void draw(GameBoard window,double delta, double dT) {
        if(obstacleSprite.getPosition().y > window.getSize().y - obstacleSprite.getGlobalBounds().height){
            obstacleSprite.setPosition(obstacleSprite.getPosition().x,window.getSize().y - obstacleSprite.getGlobalBounds().height);
        }
        float newX = obstacleSprite.getPosition().x;

        obstacleSprite.setPosition((float)((newX * (1 - delta)) + ((newX + objSpeed * dT) * delta)), obstacleSprite.getPosition().y);          // Set position with interpolation
    
        window.draw(obstacleSprite);
        obstacleSprite.setPosition(newX,obstacleSprite.getPosition().y);                    // Move back to position before interpolation
    }

    /**
     * Updates position of obstacle based off timestep * speed
     * Removes from board if off screen
     * @param window gameboard to draw with
     * @param multiplier Delta time as 1sec/tickrate in milliseconds
     */
    public int update(GameBoard window, double multiplier){
        
        obstacleSprite.move((float)(objSpeed * multiplier),0);  
        if(obstacleSprite.getPosition().x < -200){                            // Remove entity if off screen
            return 1;
        }
        return 0;
    }

    /**
     * Get sprite of this obstacle
     * @return sprite of this
     */
    public Sprite getSprite(){
        return obstacleSprite;
    }

    /**
     * get value of this obstacle
     * default 0
     * @return value of this obstacle. 0 for no value
     */
    public int getValue(){
        return 0;
    }

    /**
     * Do damage to obstacle. Default goes to 0 hp first time it takes damage
     * @return int of new hp. by default obstacle has 0 hp
     */
    public int doDamage(){
        return 0;
    }
    
}
