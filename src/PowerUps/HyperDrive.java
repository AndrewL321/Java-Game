package PowerUps;

import Level.GameBoard;
import Ships.PlayerShip;

/**
 * Class for hyperdrive powerup. Allows player to skip position
 */
public class HyperDrive implements PowerUp {

    private int ghostTime;
    private float skip = 500;

    /**
     * Sets the immunity time based off level unlocked for this
     * @param level level this powerup is at
     */
    public HyperDrive(int level) {
        ghostTime = 500 + (250 * level);
    }

    /**
     * Called when power up used. Teleports player by skip amount of pixels in direction they currently pressing
     * @param window gameboard this is part of
     * @param ship to apply to
     */
    @Override
    public void use(GameBoard window, PlayerShip ship) {
        skip = skip * window.getScale().x;

        if(ship.getRight()){
            ship.getSprite().setPosition(ship.getSprite().getPosition().x + skip,ship.getSprite().getPosition().y);
        }
        else if(ship.getLeft()){
            ship.getSprite().setPosition(ship.getSprite().getPosition().x - skip,ship.getSprite().getPosition().y);
        }
        else if(ship.getDown()){
            ship.getSprite().setPosition(ship.getSprite().getPosition().x,ship.getSprite().getPosition().y + skip);
        }
        else if(ship.getUp()){
            ship.getSprite().setPosition(ship.getSprite().getPosition().x,ship.getSprite().getPosition().y - skip);
        }
        else{
            ship.getSprite().setPosition(ship.getSprite().getPosition().x + skip,ship.getSprite().getPosition().y);
        }
        

        ship.shipGhost(true, ghostTime);
       
    }

    
}
