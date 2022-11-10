package Obstacles;

import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import Ships.PlayerShip;

/**
 * Representation of powerups. Images on board that player can collect to add assigned powerup
 */
public class PowerUpCollectable extends Obstacle {

    private String type;

    public PowerUpCollectable(Texture image, float speedMult,Vector2f scale,String t, Vector2f speedScale) {
        super(image, speedMult,scale,speedScale);
        type = t;
    }

    /**
     * adds powerup to ship
     * @param ship to add to
     */
    public void addPowerUp(PlayerShip ship){
        ship.addPowerUp(type);

    }

    /**
     * Gets the type of this powerup. "LASER", "HYPER", "SHIELD"
     * @return string of type
     */
    public String getType(){
        return type;
    }
    
}
