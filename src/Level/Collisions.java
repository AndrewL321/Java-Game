package Level;
import Ships.*;
import Laser.Laser;

import java.util.ArrayList;

import Utility.GameSound;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Sprite;
import Obstacles.*;

/**
 * Class used to detect collisions between various types of objects
 * Class is singleton only 1 can exist
 */


public class Collisions {

    private static Collisions instance = null;  //instance
    private GameSound gameSound = new GameSound();
    /**
     * Private constructor, use getInstance()
     */
    private Collisions(){

    }

    /**
     * Creates instance if none exist, returns an instance
     * @return Instance of this class
     */
    public static Collisions getInstance(){
        if(instance == null){
            instance = new Collisions();
        }
        return instance;
    }

    /**
     * Test collision between ship and obstacle, shrinks rect for test and removes obstacle if collision
     * @param ship ship to test
     * @param entities obstacles to test, arraylist
     * @return null if no collison, int of index for obstacles collided
     */
    public Integer shipCollision(PlayerShip ship, ArrayList<Obstacle> entities) {

        for (int i = 0; i < entities.size(); i++) {

            Sprite shipSprite = ship.getSprite();
            Sprite entitySprite  = entities.get(i).getSprite();

            if (shipSprite.getGlobalBounds().intersection(entitySprite.getGlobalBounds())  != null){
                if(shrink(shipSprite).intersection(shrink(entitySprite))!= null){
                    entities.remove(i);
                    return i;
                }
            }        
        }
        return null;
    }

    /**
     * Collision between ship and coin test, shrinks rect for test
     * @param ship ship to test
     * @param coin coin to test
     * @return 1 if collision, null if not
     */
    public Integer coinCollision(PlayerShip ship, Obstacle coin) {

        if ((ship.getSprite().getGlobalBounds().intersection(coin.getSprite().getGlobalBounds()) != null)){
            if(shrink(ship.getSprite()).intersection(shrink(coin.getSprite()))!= null) {
                gameSound.Sound("coinPickUp", 10.f);
                return 1;
            }   
        }
        return null;
    }

    /**
     * Laser and obstacle collison
     * @param laser laser to test
     * @param asteroid obsatcle to test
     * @return 1 if collison, null if none
     */
    public Integer laserCollision(Laser laser, Obstacle asteroid) {

        if ((laser.getRect().getGlobalBounds().intersection(asteroid.getSprite().getGlobalBounds()) != null)){
            if(shrinkRect(laser.getRect()).intersection(shrink(asteroid.getSprite()))!= null){
                return 1;
            }
        }
        return null;
    }

    /**
     * Shrinks by 80%
     * @param toShrink sprite to shrink
     * @return shrunken rect
     */
    private FloatRect shrink(Sprite toShrink){
        return new FloatRect(toShrink.getPosition().x,toShrink.getPosition().y,toShrink.getGlobalBounds().width * 0.8f,toShrink.getGlobalBounds().height *0.8f);
    }

    /**
     * Shrinks by 80%
     * @param toShrink rect to shrink
     * @return shrunken rect
     */
    private FloatRect shrinkRect(RectangleShape toShrink){
        return new FloatRect(toShrink.getPosition().x,toShrink.getPosition().y,toShrink.getGlobalBounds().width * 0.8f,toShrink.getGlobalBounds().height *0.8f);
    }
}

