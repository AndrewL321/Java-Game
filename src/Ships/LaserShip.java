package Ships;

import java.nio.file.Paths;

import Level.GameBoard;

/**
 * Implementation of ship with more ammo
 */
public class LaserShip extends PlayerShip {
    private int capacity = 0;

    
    /**
     * Sets all value
     * @param window to draw
     * @param barSize size of ui at top
     * @param l lives
     * @param u upgrade levels
     */
    public LaserShip(GameBoard window,int barSize,int l,int[] u){
        super(window,barSize,u);
        SHIP_ACCEL = 0.0000000000000016f;
        DRAG_MULTIPLIER = 0.0000000018f;

        shipSprite = loadImage(Paths.get("Images/laserShip.png"));

        shipSprite.setScale((float)0.6*window.getScale().x,(float)0.6*window.getScale().y);
        
        shipSprite.setPosition(shipSprite.getGlobalBounds().width,window.getSize().y/2);

        shipSprite.setColor(noGhost);
        lives = l + capacity;
        ammo = 5;
        fullAmmo = ammo;
        sizePerAmmo = maxAmmo.getScale().x/ammo;
    }


     /**
     * Returns extra capacity this ship has
     * @return int for this ships extra space
     */
    @Override
    public int getCapacity() {
        return capacity;
    }
    
}
