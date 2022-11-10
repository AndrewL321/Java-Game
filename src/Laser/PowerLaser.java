package Laser;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

import Level.GameBoard;
import Ships.PlayerShip;

/**
 * PowerLaser class used to create a more powerful laser for powerup
 */

public class PowerLaser extends Laser{

    private RectangleShape laserRect;       // Image used for laser
    private PlayerShip ship;                // Ship that is using this laser


    /**
     * Constructor creates a cyan rectangle shape for image and sets it to ships position
     * @param gameWindow board this is part of
     * @param pShip ship that used this laser
     */
    public PowerLaser(GameBoard gameWindow,PlayerShip pShip){
        ship = pShip;
        laserRect = new RectangleShape(new Vector2f(gameWindow.getSize().x,12));
        laserRect.setScale(gameWindow.getScale());
        laserRect.setPosition(pShip.getSprite().getPosition().x + 30 * gameWindow.getScale().x,pShip.getSprite().getPosition().y + 20 * gameWindow.getScale().y);
        laserRect.setFillColor(Color.CYAN);
    }

    /**
     * draws the laser
     * @param window board to draw on
     * @param delta not used
     * @param dT not used
     */
    @Override
    public void draw(GameBoard window, double delta, double dT) {
        window.draw(laserRect);
    }
    /**
     * Updates this lasers position to that of ship
     * @param window to draw on
     * @param dT unused
     */
    @Override
    public int update(GameBoard window, double dT) {
        laserRect.setPosition(ship.getSprite().getPosition().x + 30 * window.getScale().x,ship.getSprite().getPosition().y + 20 * window.getScale().y);
            return 0;
    }
    /**
     * Returns null no sprite for this
     */
    @Override
    public Sprite getSprite() {
        return null;
    }

    /**
     * Returns the rectangle used to show this laser
     */
    @Override
    public RectangleShape getRect() {
        return laserRect;
    }
    
}
