package Ships;

import java.nio.file.Paths;

import Level.GameBoard;

/**
 * Implementation of a faster ship
 */
public class FastShip extends PlayerShip {
    private int capacity = 0;

    
    /**
     * Sets all value
     * @param window to draw
     * @param barSize size of ui at top
     * @param l lives
     * @param u upgrade levels
     */
    public FastShip(GameBoard window,int barSize,int l,int[] u){
        super(window,barSize,u);
        SHIP_ACCEL = 0.0000000000000020f;
        DRAG_MULTIPLIER = 0.0000000014f;

        shipSprite = loadImage(Paths.get("Images/fastShip.png"));

        shipSprite.setScale((float)0.5*window.getScale().x,(float)0.5*window.getScale().y);
        
        shipSprite.setPosition(shipSprite.getGlobalBounds().width,window.getSize().y/2);

        shipSprite.setColor(noGhost);
        lives = l + capacity;
        ammo = 3;
        fullAmmo = ammo;
        sizePerAmmo = maxAmmo.getScale().x/ammo;
    }

    /**
     * Returns extra capacity this ship has
     * @return int for this ships extra space
     */
    public int getCapacity(){
        return capacity;
    }
    
}
