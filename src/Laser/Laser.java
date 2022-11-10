package Laser;
import Level.*;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 * Laser class for creating and moving laser
 */


public class Laser implements Entity {

    private RectangleShape laserRect;                           // The image used for laser

    private float objSpeed = 0.0000008f;                   // Pixel per update. * dT for total movement


    /**
     * Laser constructor
     * @param xPos  x pos to place laser
     * @param yPos  y pos to place lasser
     * @param gameWindow    GameBoard this laser is part of
     */

    public Laser(float xPos,float yPos,GameBoard gameWindow){
        laserRect = new RectangleShape(new Vector2f(60,12));
        laserRect.setScale(gameWindow.getScale());
        laserRect.setPosition(xPos + 30 * gameWindow.getScale().x,yPos + 10 * gameWindow.getScale().y);
        laserRect.setFillColor(Color.GREEN);
    }

    /**
     * Blank constructor for classes that extend
     */
    protected Laser(){

    }

    /**
     * For drawing this laser
     * @param window GameBoard to draw on/with
     * @param delta value 0-1 representing how far between time steps
     * @param dT time step, as 1sec/tickrate
     */

    @Override
    public void draw(GameBoard window, double delta, double dT) {
        
        float newX = laserRect.getPosition().x;

        laserRect.setPosition((float)((newX * (1 - delta)) + ((newX + objSpeed * dT) * delta)), laserRect.getPosition().y);          // Set position with interpolation
    
        window.draw(laserRect);
        laserRect.setPosition(newX,laserRect.getPosition().y);                    // Move back to position before interpolation

    }


    /**
     * For updating this lasers position using speed constant
     * @param window GameBoard this laser is part of
     * @param dT time step as 1sec/tickrate
     * @return 1 if now off screen, 0 if not
     */
    @Override
    public int update(GameBoard window, double dT) {
        laserRect.move((float)(objSpeed * dT *window.getScale().x),0);  
        if(laserRect.getPosition().x > window.getSize().x){                            // Remove entity if off screen
            return 1;                     
        }
        return 0;
    }

    /**
     * Getter. This laser has now sprite so returns null
     * @return null
     */
    @Override
    public Sprite getSprite() {
        return null;
    }
    /**
     * Getter for this lasers rectangle image
     * @return The rectangle used for this lasers graphical representation 
     */
    public RectangleShape getRect() {
        return laserRect;
    }
    
}
